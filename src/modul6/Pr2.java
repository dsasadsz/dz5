package modul6;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

interface StockObserver {
    String getName();
    void onPriceChanged(String symbol, double newPrice);
}

class StockExchange {
    private final ConcurrentMap<String, Double> prices = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, CopyOnWriteArrayList<Subscription>> subscribers = new ConcurrentHashMap<>();
    private final ConcurrentMap<StockObserver, AtomicInteger> notificationCounts = new ConcurrentHashMap<>();
    private final ExecutorService notifier = Executors.newCachedThreadPool();
    private final PrintWriter logger;

    StockExchange(String logFile) throws IOException {
        this.logger = new PrintWriter(new FileWriter(logFile, true), true);
    }

    void addOrUpdateStock(String symbol, double price) {
        prices.put(symbol, price);
        subscribers.putIfAbsent(symbol, new CopyOnWriteArrayList<>());
        log("Stock added/updated: " + symbol + " = " + price);
    }

    OptionalDouble getPrice(String symbol) {
        Double p = prices.get(symbol);
        return p == null ? OptionalDouble.empty() : OptionalDouble.of(p);
    }

    void subscribe(String symbol, StockObserver observer) {
        subscribe(symbol, observer, null);
    }

    void subscribe(String symbol, StockObserver observer, Predicate<Double> filter) {
        subscribers.putIfAbsent(symbol, new CopyOnWriteArrayList<>());
        subscribers.get(symbol).add(new Subscription(observer, filter));
        notificationCounts.putIfAbsent(observer, new AtomicInteger(0));
        log(observer.getName() + " subscribed to " + symbol + (filter == null ? "" : " (filtered)"));
    }

    void unsubscribe(String symbol, StockObserver observer) {
        var list = subscribers.get(symbol);
        if (list != null) {
            list.removeIf(s -> s.observer.equals(observer));
            log(observer.getName() + " unsubscribed from " + symbol);
        }
    }

    void unsubscribeAll(StockObserver observer) {
        for (var entry : subscribers.entrySet()) {
            entry.getValue().removeIf(s -> s.observer.equals(observer));
        }
        log(observer.getName() + " unsubscribed from all");
    }

    void updatePrice(String symbol, double newPrice) {
        prices.put(symbol, newPrice);
        log("Price updated: " + symbol + " -> " + newPrice);
        var list = subscribers.get(symbol);
        if (list == null || list.isEmpty()) return;
        for (var sub : list) {
            boolean pass;
            try {
                pass = sub.filter == null || sub.filter.test(newPrice);
            } catch (Exception ex) {
                log("Filter error for " + sub.observer.getName() + " on " + symbol + ": " + ex.getMessage());
                pass = false;
            }
            if (!pass) continue;
            notifier.execute(() -> {
                try {
                    sub.observer.onPriceChanged(symbol, newPrice);
                    notificationCounts.computeIfAbsent(sub.observer, k -> new AtomicInteger(0)).incrementAndGet();
                } catch (Exception ex) {
                    log("Notify error for " + sub.observer.getName() + ": " + ex.getMessage());
                }
            });
        }
    }

    Map<String, List<String>> subscriptionsReport() {
        Map<String, List<String>> report = new HashMap<>();
        for (var e : subscribers.entrySet()) {
            var names = new ArrayList<String>();
            for (var s : e.getValue()) names.add(s.observer.getName() + (s.filter == null ? "" : " (filtered)"));
            report.put(e.getKey(), names);
        }
        return report;
    }

    Map<String, Integer> notificationCountsSnapshot() {
        Map<String, Integer> out = new HashMap<>();
        for (var e : notificationCounts.entrySet()) out.put(e.getKey().getName(), e.getValue().get());
        return out;
    }

    void shutdown() {
        notifier.shutdown();
        try { if (!notifier.awaitTermination(2, TimeUnit.SECONDS)) notifier.shutdownNow(); } catch (InterruptedException ignored) {}
        logger.close();
    }

    private void log(String msg) {
        String line = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] " + msg;
        try { logger.println(line); } catch (Exception ignored) {}
    }

    private static class Subscription {
        final StockObserver observer;
        final Predicate<Double> filter;
        Subscription(StockObserver o, Predicate<Double> f) { observer = o; filter = f; }
    }
}

class Trader implements StockObserver {
    private final String name;
    Trader(String name) { this.name = name; }
    public String getName() { return name; }
    public void onPriceChanged(String symbol, double newPrice) {
        System.out.println(name + " received update: " + symbol + " -> " + newPrice);
    }
}

class TradingRobot implements StockObserver {
    private final String name;
    private final double buyBelow;
    private final double sellAbove;
    private final Map<String, Integer> positions = new ConcurrentHashMap<>();

    TradingRobot(String name, double buyBelow, double sellAbove) {
        this.name = name; this.buyBelow = buyBelow; this.sellAbove = sellAbove;
    }

    public String getName() { return name; }

    public void onPriceChanged(String symbol, double newPrice) {
        positions.putIfAbsent(symbol, 0);
        if (newPrice <= buyBelow) {
            positions.compute(symbol, (s, p) -> (p == null ? 0 : p) + 1);
            System.out.println(name + " bought 1 " + symbol + " at " + newPrice + ". Pos=" + positions.get(symbol));
        } else if (newPrice >= sellAbove && positions.get(symbol) > 0) {
            positions.computeIfPresent(symbol, (s, p) -> p - 1);
            System.out.println(name + " sold 1 " + symbol + " at " + newPrice + ". Pos=" + positions.get(symbol));
        } else {
            System.out.println(name + " observed " + symbol + " at " + newPrice + ". No action.");
        }
    }

    int getPosition(String symbol) { return positions.getOrDefault(symbol, 0); }
}

class EmailNotifier implements StockObserver {
    private final String name;
    EmailNotifier(String name) { this.name = name; }
    public String getName() { return name; }
    public void onPriceChanged(String symbol, double newPrice) {
        try { Thread.sleep(150); } catch (InterruptedException ignored) {}
        System.out.println(name + " sent email: " + symbol + " -> " + newPrice);
    }
}

public class Pr2 {
    public static void main(String[] args) throws Exception {
        StockExchange exchange = new StockExchange("exchange.log");

        exchange.addOrUpdateStock("AAPL", 150.0);
        exchange.addOrUpdateStock("GOOGL", 2800.0);
        exchange.addOrUpdateStock("TSLA", 700.0);

        Trader alice = new Trader("Alice");
        Trader bob = new Trader("Bob");
        TradingRobot robot = new TradingRobot("Robot-1", 140.0, 160.0);
        EmailNotifier mailer = new EmailNotifier("EmailService");

        exchange.subscribe("AAPL", alice);
        exchange.subscribe("AAPL", robot, price -> price <= 200.0);
        exchange.subscribe("GOOGL", bob);
        exchange.subscribe("TSLA", alice, price -> price >= 650.0);
        exchange.subscribe("TSLA", mailer);

        ScheduledExecutorService sim = Executors.newSingleThreadScheduledExecutor();
        Random rnd = new Random();
        String[] symbols = new String[] { "AAPL", "GOOGL", "TSLA" };

        Runnable updater = () -> {
            String sym = symbols[rnd.nextInt(symbols.length)];
            exchange.getPrice(sym).ifPresent(current -> {
                double variation = (rnd.nextDouble() - 0.5) * 10.0;
                double newPrice = Math.round(Math.max(0.01, current + variation) * 100.0) / 100.0;
                exchange.updatePrice(sym, newPrice);
            });
        };

        sim.scheduleAtFixedRate(updater, 0, 800, TimeUnit.MILLISECONDS);

        System.out.println("Simulation running. Press Enter to stop and print reports.");
        System.in.read();

        sim.shutdownNow();
        exchange.shutdown();

        System.out.println("\nSubscriptions report:");
        var subs = exchange.subscriptionsReport();
        for (var e : subs.entrySet()) {
            System.out.println(e.getKey() + ": " + String.join(", ", e.getValue()));
        }

        System.out.println("\nNotification counts:");
        var counts = exchange.notificationCountsSnapshot();
        counts.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\nRobot positions (TSLA): " + robot.getPosition("TSLA"));
        System.out.println("Done.");
    }
}
