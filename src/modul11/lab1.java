package modul11;

import java.util.HashMap;
import java.util.Map;

class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public double getPrice() { return price; }
}

class Order {
    private int orderId;
    private Map<Product, Integer> items = new HashMap<>();
    private boolean isConfirmed = false;

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public void addItem(Product product, int quantity) {
        items.put(product, quantity);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public int getOrderId() {
        return orderId;
    }
}

class WarehouseSystem {
    private Map<String, Integer> stockDatabase = new HashMap<>();

    public WarehouseSystem() {
        stockDatabase.put("PROD-001", 10); // Ноутбук
        stockDatabase.put("PROD-002", 0);  // Мышь (нет в наличии)
        stockDatabase.put("PROD-003", 5);  // Клавиатура
    }

 //автоматты тексеру товар
    public boolean checkAvailability(Order order) {
        System.out.println("--- Начало проверки наличия для заказа #" + order.getOrderId() + " ---");
        boolean allAvailable = true;

        for (Map.Entry<Product, Integer> entry : order.getItems().entrySet()) {
            Product product = entry.getKey();
            int requestedQty = entry.getValue();
            int stockQty = stockDatabase.getOrDefault(product.getId(), 0);

            if (stockQty >= requestedQty) {
                System.out.printf("✔ Товар '%s' доступен (Запрос: %d, Остаток: %d)\n",
                        product.getName(), requestedQty, stockQty);
            } else {
                System.out.printf("✘ Товар '%s' НЕДОСТУПЕН (Запрос: %d, Остаток: %d)\n",
                        product.getName(), requestedQty, stockQty);
                allAvailable = false;
            }
        }
        return allAvailable;
    }

    public void reserveStock(Order order) {
        for (Map.Entry<Product, Integer> entry : order.getItems().entrySet()) {
            String prodId = entry.getKey().getId();
            int currentStock = stockDatabase.get(prodId);
            stockDatabase.put(prodId, currentStock - entry.getValue());
        }
        System.out.println(">>> Товары зарезервированы на складе.");
    }
}








public class lab1 {
    public static void main(String[] args) {
        WarehouseSystem warehouse = new WarehouseSystem();

        Product laptop = new Product("PROD-001", "Ноутбук Gaming X", 1500.0);
        Product mouse = new Product("PROD-002", "Беспроводная мышь", 25.0);

        System.out.println("\n пров 1: Клиент создает валидный заказ ");
        Order order1 = new Order(101);
        order1.addItem(laptop, 1); // Заказываем 1 ноутбук (на складе 10)

        processOrder(order1, warehouse);

        System.out.println("\n пров 2: Клиент заказывает отсутствующий товар ");
        Order order2 = new Order(102);
        order2.addItem(mouse, 1); // Заказываем мышь (на складе 0)

        processOrder(order2, warehouse);
    }


    public static void processOrder(Order order, WarehouseSystem warehouse) {
        if (warehouse.checkAvailability(order)) {
            warehouse.reserveStock(order);
            order.setConfirmed(true);
            System.out.println(" Заказ #" + order.getOrderId() + " подтвержден. Переход к оплате...");
        } else {
            System.out.println(" Заказ #" + order.getOrderId() + " не может быть сформирован. Отправка уведомления клиенту: 'Измените заказ'.");
        }
    }
}
