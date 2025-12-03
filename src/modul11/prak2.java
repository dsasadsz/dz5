package modul11;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class Warehouse {
    private final Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {
        stock.put("SKU-100", 0);
        stock.put("SKU-200", 5);
    }

    public boolean checkAvailability(Map<String, Integer> items) {
        System.out.println("-> [Склад]: Проверка наличия товаров...");
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String sku = entry.getKey();
            int requiredQty = entry.getValue();
            int currentStock = stock.getOrDefault(sku, 0);

            if (currentStock < requiredQty) {
                System.out.printf("   [Склад]: Товар %s недоступен (нужно: %d, есть: %d)\n", sku, requiredQty, currentStock);
                return false;
            }
        }
        System.out.println("<- [Склад]: Все товары доступны.");
        return true;
    }

    public void notifyForAssembly(String orderId) {
        System.out.printf("-> [Склад]: Уведомление получено. Начинаем сборку заказа %s.\n", orderId);
    }
}

class PaymentGateway {
    private final Random random = new Random();

    public boolean processTransaction(double amount) {
        System.out.println("-> [Платежный шлюз]: Обработка транзакции на сумму: " + amount);
        boolean success = random.nextDouble() < 0.8;

        if (success) {
            System.out.println("<- [Платежный шлюз]: Успех.");
        } else {
            System.out.println("<- [Платежный шлюз]: Отказ.");
        }
        return success;
    }
}

class OrderSystem {
    private final Warehouse warehouse;
    private final PaymentGateway gateway;
    private int orderCounter = 1000;

    public OrderSystem(Warehouse warehouse, PaymentGateway gateway) {
        this.warehouse = warehouse;
        this.gateway = gateway;
    }

    public void createOrder(String user, Map<String, Integer> items, String paymentMethod, double totalAmount) {
        String orderId = "ORD-" + (++orderCounter);
        System.out.println("\n===== НАЧАЛО ПРОЦЕССА: Заказ " + orderId + " от " + user + " =====");

        if (!warehouse.checkAvailability(items)) {
            System.out.println("<< [Пользователь]: Уведомление: Не все товары в наличии. Измените корзину.");
            return;
        }

        System.out.println(">> [Пользователь]: Заказ подтвержден. Выбран способ оплаты: " + paymentMethod);

        if (paymentMethod.equalsIgnoreCase("Онлайн")) {
            if (gateway.processTransaction(totalAmount)) {
                System.out.println("-> [Система]: Активировать заказ. Заказ успешно оплачен.");
                warehouse.notifyForAssembly(orderId);
            } else {
                System.out.println("<< [Пользователь]: Уведомление: Ошибка оплаты. Повторите.");
                return;
            }
        } else if (paymentMethod.equalsIgnoreCase("При доставке")) {
            System.out.println("-> [Система]: Установка статуса 'Ожидает оплаты при получении'.");
            warehouse.notifyForAssembly(orderId);
        } else {
            System.out.println("<< [Пользователь]: Неизвестный метод оплаты.");
        }

        System.out.println("===== КОНЕЦ ПРОЦЕССА: Заказ " + orderId + " обработан =====");
    }
}

public class prak2 {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        PaymentGateway gateway = new PaymentGateway();
        OrderSystem system = new OrderSystem(warehouse, gateway);

        // Успешный онлайн-заказ
        Map<String, Integer> items1 = new HashMap<>();
        items1.put("SKU-200", 1);
        system.createOrder("Клиент А", items1, "Онлайн", 49.99);

        // Товары недоступны
        Map<String, Integer> items2 = new HashMap<>();
        items2.put("SKU-100", 1);
        system.createOrder("Клиент Б", items2, "При доставке", 120.00);

        //Успешный заказ "При доставке"
        Map<String, Integer> items3 = new HashMap<>();
        items3.put("SKU-200", 2);
        system.createOrder("Клиент В", items3, "При доставке", 99.98);
    }
}
