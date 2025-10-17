package modul6;
import java.util.Scanner;

enum ServiceClass {
    ECONOMY, BUSINESS
}

class TravelRequest {
    double distanceKm;
    ServiceClass serviceClass;
    int adults;
    int children;
    int seniors;
    int baggageCount;
    boolean groupBooking;
    double extraServicesFee;

    public TravelRequest(double distanceKm, ServiceClass serviceClass, int adults, int children, int seniors,
                         int baggageCount, boolean groupBooking, double extraServicesFee) {
        this.distanceKm = distanceKm;
        this.serviceClass = serviceClass;
        this.adults = adults;
        this.children = children;
        this.seniors = seniors;
        this.baggageCount = baggageCount;
        this.groupBooking = groupBooking;
        this.extraServicesFee = extraServicesFee;
    }

    public int totalPassengers() {
        return adults + children + seniors;
    }
}

interface ICostCalculationStrategy {
    double calculateCost(TravelRequest request) throws IllegalArgumentException;
    String name();
}










class PlaneCostStrategy implements ICostCalculationStrategy {
    private final double baseRatePerKm = 0.12;
    private final double businessMultiplier = 1.7;
    private final double airportTaxRate = 0.07;
    private final double fuelSurchargePerPassenger = 50.0;
    private final double baggageFeePerPiece = 30.0;

    @Override
    public double calculateCost(TravelRequest r) {
        if (r.distanceKm <= 0) throw new IllegalArgumentException("Distance must be positive.");
        if (r.totalPassengers() <= 0) throw new IllegalArgumentException("At least one passenger required.");

        int pax = r.totalPassengers();
        double classMult = (r.serviceClass == ServiceClass.BUSINESS) ? businessMultiplier : 1.0;
        double passengerBase = baseRatePerKm * r.distanceKm * classMult * pax;
        double baggageFees = r.baggageCount * baggageFeePerPiece;
        double extras = r.extraServicesFee;
        double subtotal = passengerBase + baggageFees + extras + fuelSurchargePerPassenger * pax;
        double groupDiscount = r.groupBooking && pax >= 5 ? 0.10 * subtotal : 0.0;
        double afterGroup = subtotal - groupDiscount;
        double airportTax = afterGroup * airportTaxRate;
        double totalBeforeSpecialDiscounts = afterGroup + airportTax;
        double childrenShare = r.children * baseRatePerKm * r.distanceKm * classMult;
        double seniorsShare = r.seniors * baseRatePerKm * r.distanceKm * classMult;
        double childrenDiscount = 0.5 * childrenShare;
        double seniorsDiscount = 0.3 * seniorsShare;
        double total = totalBeforeSpecialDiscounts - childrenDiscount - seniorsDiscount;
        return Math.round(total * 100.0) / 100.0;
    }

    @Override
    public String name() { return "Plane"; }
}

class TrainCostStrategy implements ICostCalculationStrategy {
    private final double baseRatePerKm = 0.045;
    private final double businessMultiplier = 1.25;
    private final double baggageFeePerPiece = 10.0;
    private final double serviceFeeRate = 0.03;

    @Override
    public double calculateCost(TravelRequest r) {
        if (r.distanceKm <= 0) throw new IllegalArgumentException("Distance must be positive.");
        if (r.totalPassengers() <= 0) throw new IllegalArgumentException("At least one passenger required.");

        int pax = r.totalPassengers();
        double classMult = (r.serviceClass == ServiceClass.BUSINESS) ? businessMultiplier : 1.0;
        double passengerBase = baseRatePerKm * r.distanceKm * classMult * pax;
        double baggageFees = r.baggageCount * baggageFeePerPiece;
        double extras = r.extraServicesFee;
        double subtotal = passengerBase + baggageFees + extras;
        double childrenShare = r.children * baseRatePerKm * r.distanceKm * classMult;
        double seniorsShare = r.seniors * baseRatePerKm * r.distanceKm * classMult;
        double childrenDiscount = 0.5 * childrenShare;
        double seniorsDiscount = 0.3 * seniorsShare;
        double afterSpecial = subtotal - childrenDiscount - seniorsDiscount;
        double serviceFee = afterSpecial * serviceFeeRate;
        double groupDiscount = r.groupBooking && pax >= 10 ? 0.08 * afterSpecial : 0.0;
        double total = afterSpecial + serviceFee - groupDiscount;
        return Math.round(total * 100.0) / 100.0;
    }

    @Override
    public String name() { return "Train"; }
}

class BusCostStrategy implements ICostCalculationStrategy {
    private final double baseRatePerKm = 0.028;
    private final double vipMultiplier = 1.3;
    private final double baggageFeePerPiece = 5.0;
    private final double regionalTaxRate = 0.02;

    @Override
    public double calculateCost(TravelRequest r) {
        if (r.distanceKm <= 0) throw new IllegalArgumentException("Distance must be positive.");
        if (r.totalPassengers() <= 0) throw new IllegalArgumentException("At least one passenger required.");

        int pax = r.totalPassengers();
        double classMult = (r.serviceClass == ServiceClass.BUSINESS) ? vipMultiplier : 1.0;
        double passengerBase = baseRatePerKm * r.distanceKm * classMult * pax;
        double baggageFees = r.baggageCount * baggageFeePerPiece;
        double subtotal = passengerBase + baggageFees + r.extraServicesFee;
        double childrenShare = r.children * baseRatePerKm * r.distanceKm * classMult;
        double seniorsShare = r.seniors * baseRatePerKm * r.distanceKm * classMult;
        double childrenDiscount = 0.5 * childrenShare;
        double seniorsDiscount = 0.3 * seniorsShare;
        double afterSpecial = subtotal - childrenDiscount - seniorsDiscount;
        double tax = afterSpecial * regionalTaxRate;
        double groupDiscount = r.groupBooking && pax >= 6 ? 0.05 * afterSpecial : 0.0;
        double total = afterSpecial + tax - groupDiscount;
        return Math.round(total * 100.0) / 100.0;
    }

    @Override
    public String name() { return "Bus"; }
}

class TravelBookingContext {
    private ICostCalculationStrategy strategy;

    public TravelBookingContext(ICostCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ICostCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateTotal(TravelRequest request) {
        if (strategy == null) throw new IllegalStateException("No pricing strategy set.");
        return strategy.calculateCost(request);
    }

    public String currentStrategyName() {
        return strategy == null ? "none" : strategy.name();
    }
}















public class Pr1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TravelBookingContext context = new TravelBookingContext(null);

        System.out.println("=== Система бронирования путешествий ===");

        try {
            System.out.println("Выберите транспорт:  Plane(1);    Train(2);    Bus(3)");
            String tchoice = sc.nextLine().trim();
            switch (tchoice) {
                case "1": context.setStrategy(new PlaneCostStrategy()); break;
                case "2": context.setStrategy(new TrainCostStrategy()); break;
                case "3": context.setStrategy(new BusCostStrategy()); break;
                default:
                    System.out.println("Ошибкаа!!");
                    return;
            }

            System.out.print("Введите расстояние в км: ");
            double distance = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Класс обслуживания (economy/business): ");
            String cls = sc.nextLine().trim().toLowerCase();
            ServiceClass serviceClass = cls.startsWith("b") ? ServiceClass.BUSINESS : ServiceClass.ECONOMY;

            System.out.print("Количество взрослых: ");
            int adults = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Количество детей: ");
            int children = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Количество пенсионеров: ");
            int seniors = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Количество багажа: ");
            int baggage = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Групповая бронь? (yes/no): ");
            boolean group = sc.nextLine().trim().equalsIgnoreCase("yes");
            System.out.print("Дополнительные услуги (сумма в тг): ");
            double extras = Double.parseDouble(sc.nextLine().trim());

            TravelRequest request = new TravelRequest(distance, serviceClass, adults, children, seniors, baggage, group, extras);

            System.out.println("\nИспользуемая стратегия: " + context.currentStrategyName());
            double total = context.calculateTotal(request);
            System.out.println("Итоговая стоимость: " + total + " тг");
            int pax = request.totalPassengers();
            if (pax > 0) {
                System.out.println("Средняя стоимость на пассажира: " + Math.round((total / pax) * 100.0) / 100.0 + " тг");
            }

        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        } finally {
            sc.close();
        }

        System.out.println("Завершено.");
    }
}
