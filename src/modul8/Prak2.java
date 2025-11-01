package modul8;
import java.util.*;


interface IInternalDeliveryService {
    String deliverOrder(String orderId);
    String getDeliveryStatus(String orderId);
    double calculateDeliveryCost(String orderId);
}

class InternalDeliveryService implements IInternalDeliveryService {
    private final Map<String, String> registry = new HashMap<>(); // orderId -> internalTracking
    private final Random rnd = new Random();

    @Override
    public synchronized String deliverOrder(String orderId) {
        String tracking = "INT-" + Math.abs(orderId.hashCode()) + "-" + (1000 + rnd.nextInt(9000));
        registry.put(orderId, tracking);
        System.out.println("[Internal] Order " + orderId + " scheduled, tracking=" + tracking);
        return tracking;
    }

    @Override
    public synchronized String getDeliveryStatus(String orderId) {
        String t = registry.get(orderId);
        if (t == null) return "[Internal] No such order";
        String[] statuses = {"PENDING", "PICKED_UP", "IN_TRANSIT", "DELIVERED"};
        int idx = Math.abs(orderId.hashCode()) % statuses.length;
        return "[Internal] " + statuses[idx];
    }

    @Override
    public double calculateDeliveryCost(String orderId) {
        double base = 500.0;
        double mod = Math.abs(orderId.hashCode() % 1000) / 10.0;
        double cost = base + mod;
        System.out.printf("[Internal] Cost estimate for %s = %.2f%n", orderId, cost);
        return cost;
    }
}






// адаптер
class ExternalLogisticsServiceA {   //целочисленный itemId и shipmentId
    private final Map<Integer, Integer> shipments = new HashMap<>(); // itemId -> shipmentId
    private final Random rnd = new Random();

    public int shipItem(int itemId) {
        int shipmentId = 100000 + rnd.nextInt(900000);
        shipments.put(itemId, shipmentId);
        System.out.println("[ExtA] shipItem called for itemId=" + itemId + " => shipmentId=" + shipmentId);
        return shipmentId;
    }

    public String trackShipment(int shipmentId) {
        String[] s = {"QUEUED", "DISPATCHED", "OUT_FOR_DELIVERY", "DELIVERED"};
        return "[ExtA] " + s[shipmentId % s.length];
    }

    public double estimateShipping(int itemId) {
        return 300.0 + (itemId % 50) * 2.5;
    }
}

class LogisticsAdapterA implements IInternalDeliveryService {
    private final ExternalLogisticsServiceA adaptee;
    private final Map<String, Integer> map = new HashMap<>(); // orderId -> shipmentId

    public LogisticsAdapterA(ExternalLogisticsServiceA adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public synchronized String deliverOrder(String orderId) {
        try {
            int itemId = mapOrderToItem(orderId);
            int shipmentId = adaptee.shipItem(itemId);
            map.put(orderId, shipmentId);
            String tracking = "A-" + shipmentId;
            System.out.println("[AdapterA] Mapped order " + orderId + " to item " + itemId + ", tracking=" + tracking);
            return tracking;
        } catch (Exception e) {
            System.out.println("[AdapterA] Error delivering order " + orderId + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public synchronized String getDeliveryStatus(String orderId) {
        Integer shipmentId = map.get(orderId);
        if (shipmentId == null) return "[AdapterA] Order not found";
        try {
            return adaptee.trackShipment(shipmentId);
        } catch (Exception e) {
            return "[AdapterA] Error tracking: " + e.getMessage();
        }
    }

    @Override
    public double calculateDeliveryCost(String orderId) {
        try {
            int itemId = mapOrderToItem(orderId);
            double cost = adaptee.estimateShipping(itemId);
            System.out.printf("[AdapterA] Cost estimate for %s = %.2f%n", orderId, cost);
            return cost;
        } catch (Exception e) {
            System.out.println("[AdapterA] Error estimating cost: " + e.getMessage());
            return Double.NaN;
        }
    }

    private int mapOrderToItem(String orderId) {
        try {
            return Integer.parseInt(orderId);
        } catch (NumberFormatException ex) {
            return Math.abs(orderId.hashCode() % 10000);
        }
    }
}







// адаптер Б
class ExternalLogisticsServiceB {  //строковыйpackageInfo и трекинг-кодом.
    private final Map<String, String> packages = new HashMap<>(); // packageInfo -> trackingCode
    private final Random rnd = new Random();

    public String sendPackage(String packageInfo) {
        String code = "B-" + UUID.randomUUID().toString().substring(0, 8);
        packages.put(code, packageInfo);
        System.out.println("[ExtB] sendPackage called, tracking=" + code + ", info=" + packageInfo);
        return code;
    }

    public String checkPackageStatus(String trackingCode) {
        String[] s = {"INIT", "IN_TRANSIT", "NEAR_DEST", "DELIVERED"};
        return "[ExtB] " + s[Math.abs(trackingCode.hashCode()) % s.length];
    }

    public double priceForPackage(String packageInfo) {
        return 200.0 + (packageInfo.length() % 10) * 7.5;
    }
}

class LogisticsAdapterB implements IInternalDeliveryService {
    private final ExternalLogisticsServiceB adaptee;
    private final Map<String, String> map = new HashMap<>(); // orderId -> trackingCode

    public LogisticsAdapterB(ExternalLogisticsServiceB adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public synchronized String deliverOrder(String orderId) {
        try {
            String pkgInfo = "order:" + orderId + ";weight:" + (Math.abs(orderId.hashCode()) % 20 + 1);
            String tracking = adaptee.sendPackage(pkgInfo);
            map.put(orderId, tracking);
            System.out.println("[AdapterB] Delivered order " + orderId + ", tracking=" + tracking);
            return tracking;
        } catch (Exception e) {
            System.out.println("[AdapterB] Error delivering order " + orderId + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public synchronized String getDeliveryStatus(String orderId) {
        String code = map.get(orderId);
        if (code == null) return "[AdapterB] Order not found";
        try {
            return adaptee.checkPackageStatus(code);
        } catch (Exception e) {
            return "[AdapterB] Error checking status: " + e.getMessage();
        }
    }

    @Override
    public double calculateDeliveryCost(String orderId) {
        String pkgInfo = "order:" + orderId;
        try {
            double cost = adaptee.priceForPackage(pkgInfo);
            System.out.printf("[AdapterB] Cost estimate for %s = %.2f%n", orderId, cost);
            return cost;
        } catch (Exception e) {
            System.out.println("[AdapterB] Error estimating cost: " + e.getMessage());
            return Double.NaN;
        }
    }
}






// адаптер С
class ExternalLogisticsServiceC {   //использует UUID и карту параметров
    public UUID createDelivery(Map<String, String> payload) {
        UUID id = UUID.randomUUID();
        System.out.println("[ExtC] createDelivery: id=" + id + " payload=" + payload);
        return id;
    }

    public String getStatus(UUID id) {
        String[] s = {"RECEIVED", "PROCESSING", "SHIPPED", "DELIVERED"};
        return "[ExtC] " + s[Math.abs(id.hashCode()) % s.length];
    }

    public double estimate(Map<String, String> payload) {
        int len = payload.values().stream().mapToInt(String::length).sum();
        return 150.0 + (len % 50) * 3.1;
    }
}

class LogisticsAdapterC implements IInternalDeliveryService {
    private final ExternalLogisticsServiceC adaptee;
    private final Map<String, UUID> map = new HashMap<>();

    public LogisticsAdapterC(ExternalLogisticsServiceC adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public synchronized String deliverOrder(String orderId) {
        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("order", orderId);
            payload.put("items", "1"); // simulated
            payload.put("notes", "auto");
            UUID id = adaptee.createDelivery(payload);
            map.put(orderId, id);
            String tracking = "C-" + id.toString();
            System.out.println("[AdapterC] Order " + orderId + " -> tracking " + tracking);
            return tracking;
        } catch (Exception e) {
            System.out.println("[AdapterC] Error delivering: " + e.getMessage());
            return null;
        }
    }

    @Override
    public synchronized String getDeliveryStatus(String orderId) {
        UUID id = map.get(orderId);
        if (id == null) return "[AdapterC] Order not found";
        try {
            return adaptee.getStatus(id);
        } catch (Exception e) {
            return "[AdapterC] Error getting status: " + e.getMessage();
        }
    }
    @Override
    public double calculateDeliveryCost(String orderId) {
        Map<String, String> payload = new HashMap<>();
        payload.put("order", orderId);
        payload.put("items", "1");
        try {
            double cost = adaptee.estimate(payload);
            System.out.printf("[AdapterC] Cost estimate for %s = %.2f%n", orderId, cost);
            return cost;
        } catch (Exception e) {
            System.out.println("[AdapterC] Error estimating cost: " + e.getMessage());
            return Double.NaN;
        }
    }
}






//керектісің шақырат
class DeliveryServiceFactory {
    public enum ServiceType { INTERNAL, EXTERNAL_A, EXTERNAL_B, EXTERNAL_C }

    public static IInternalDeliveryService getService(ServiceType type) {
        switch (type) {
            case INTERNAL:
                return new InternalDeliveryService();
            case EXTERNAL_A:
                return new LogisticsAdapterA(new ExternalLogisticsServiceA());
            case EXTERNAL_B:
                return new LogisticsAdapterB(new ExternalLogisticsServiceB());
            case EXTERNAL_C:
                return new LogisticsAdapterC(new ExternalLogisticsServiceC());
            default:
                throw new IllegalArgumentException("Unknown service type: " + type);
        }
    }
}



public class Prak2 {
    public static void main(String[] args) {
        IInternalDeliveryService internal = DeliveryServiceFactory.getService(DeliveryServiceFactory.ServiceType.INTERNAL);
        IInternalDeliveryService extA = DeliveryServiceFactory.getService(DeliveryServiceFactory.ServiceType.EXTERNAL_A);
        IInternalDeliveryService extB = DeliveryServiceFactory.getService(DeliveryServiceFactory.ServiceType.EXTERNAL_B);
        IInternalDeliveryService extC = DeliveryServiceFactory.getService(DeliveryServiceFactory.ServiceType.EXTERNAL_C);

        System.out.println("=== Используется внутренняя служба доставки ===");
        String t1 = internal.deliverOrder("1001");
        System.out.println("Статус доставки: " + internal.getDeliveryStatus("1001"));
        internal.calculateDeliveryCost("1001");

        System.out.println("\n=== Используется внешняя служба A ===");
        String t2 = extA.deliverOrder("2002");
        System.out.println("Статус доставки: " + extA.getDeliveryStatus("2002"));
        extA.calculateDeliveryCost("2002");

        System.out.println("\n=== Используется внешняя служба B ===");
        String t3 = extB.deliverOrder("ORD-XYZ");
        System.out.println("Статус доставки: " + extB.getDeliveryStatus("ORD-XYZ"));
        extB.calculateDeliveryCost("ORD-XYZ");

        System.out.println("\n=== Используется внешняя служба C ===");
        String t4 = extC.deliverOrder("CUST-555");
        System.out.println("Статус доставки: " + extC.getDeliveryStatus("CUST-555"));
        extC.calculateDeliveryCost("CUST-555");

        System.out.println("\n=== Демонстрация: выбор службы доставки в зависимости от региона ===");
        IInternalDeliveryService chosen = chooseServiceByRegion("EU");
        String orderId = "DYN-1";
        chosen.deliverOrder(orderId);
        System.out.println("Статус выбранной доставки: " + chosen.getDeliveryStatus(orderId));
        chosen.calculateDeliveryCost(orderId);
    }

    private static IInternalDeliveryService chooseServiceByRegion(String region) {
        if (region == null) return DeliveryServiceFactory.getService(DeliveryServiceFactory.ServiceType.INTERNAL);
        region = region.toLowerCase();
        if (region.contains("eu")) return DeliveryServiceFactory.getService(DeliveryServiceFactory.ServiceType.EXTERNAL_A);
        if (region.contains("asia")) return DeliveryServiceFactory.getService(DeliveryServiceFactory.ServiceType.EXTERNAL_B);
        if (region.contains("global")) return DeliveryServiceFactory.getService(DeliveryServiceFactory.ServiceType.EXTERNAL_C);
        return DeliveryServiceFactory.getService(DeliveryServiceFactory.ServiceType.INTERNAL);
    }
}
