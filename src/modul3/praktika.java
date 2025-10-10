//package modul3;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//
// interface IPayment {
//    void processPayment(double amount);
//}
//
// interface IDelivery {
//    void deliverOrder(Order order);
//}
// interface INotification {
//    void sendNotification(String message);
//}
//
// interface IDiscount {
//    double apply(double price);
//}
//
//
//
// class CreditCardPayment implements IPayment {
//    @Override
//    public void processPayment(double amount) {
//        System.out.println("Processing credit card payment of $" + String.format("%.2f", amount));
//    }
//}
//
// class PayPalPayment implements IPayment {
//    @Override
//    public void processPayment(double amount) {
//        System.out.println("Processing PayPal payment of $" + String.format("%.2f", amount));
//    }
//}
//
// class BankTransferPayment implements IPayment {
//    @Override
//    public void processPayment(double amount) {
//        System.out.println("Processing bank transfer payment of $" + String.format("%.2f", amount));
//    }
//}
//
//
//
// class CourierDelivery implements IDelivery {
//    @Override
//    public void deliverOrder(Order order) {
//        System.out.println("Order " + order.getOrderId() + " is being delivered by courier.");
//    }
//}
//
// class PostDelivery implements IDelivery {
//    @Override
//    public void deliverOrder(Order order) {
//        System.out.println("Order " + order.getOrderId() + " has been shipped via post.");
//    }
//}
//
// class PickUpPointDelivery implements IDelivery {
//    @Override
//    public void deliverOrder(Order order) {
//        System.out.println("Order " + order.getOrderId() + " is ready for pickup at the designated point.");
//    }
//}
//
//
//
//
// class EmailNotification implements INotification {
//    @Override
//    public void sendNotification(String message) {
//        System.out.println("Sending Email: " + message);
//    }
//}
//
// class SmsNotification implements INotification {
//    @Override
//    public void sendNotification(String message) {
//        System.out.println("Sending SMS: " + message);
//    }
//}
//
//
//
//
// class PercentageDiscount implements IDiscount {
//    private final double percentage;
//
//    public PercentageDiscount(double percentage) {
//        this.percentage = percentage;
//    }
//
//    @Override
//    public double apply(double price) {
//        return price * (1 - percentage / 100);
//    }
//}
//
// class FixedAmountDiscount implements IDiscount {
//    private final double amount;
//
//    public FixedAmountDiscount(double amount) {
//        this.amount = amount;
//    }
//
//    @Override
//    public double apply(double price) {
//        return Math.max(0, price - amount);
//    }
//}
//
//
//
//
// class Product {
//    private String name;
//    private double price;
//
//    public Product(String name, double price) {
//        this.name = name;
//        this.price = price;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public String getName() {
//        return name;
//    }
//}
//
// class Order {
//    private final String orderId = UUID.randomUUID().toString();
//    private final List<Product> items = new ArrayList<>();
//
//    public void addItem(Product item) {
//        items.add(item);
//    }
//
//    public double getTotalPrice() {
//        return items.stream().mapToDouble(Product::getPrice).sum();
//    }
//
//    public String getOrderId() {
//        return orderId;
//    }
//}
//
//
//
//
// class DiscountCalculator {
//    private final List<IDiscount> discounts = new ArrayList<>();
//
//    public void addDiscount(IDiscount discount) {
//        discounts.add(discount);
//    }
//
//    public double calculate(double price) {
//        double discountedPrice = price;
//        for (IDiscount discount : discounts) {
//            discountedPrice = discount.apply(discountedPrice);
//        }
//        return discountedPrice;
//    }
//}
//
//
//
//
// class OrderProcessor {
//    private final IPayment paymentService;
//    private final IDelivery deliveryService;
//    private final INotification notificationService;
//    private final DiscountCalculator discountCalculator;
//
//    public OrderProcessor(IPayment paymentService, IDelivery deliveryService, INotification notificationService, DiscountCalculator discountCalculator) {
//        this.paymentService = paymentService;
//        this.deliveryService = deliveryService;
//        this.notificationService = notificationService;
//        this.discountCalculator = discountCalculator;
//    }
//
//    public void process(Order order) {
//        //  Calculate final price
//        double initialPrice = order.getTotalPrice();
//        double finalPrice = discountCalculator.calculate(initialPrice);
//        System.out.println("Initial price: $" + String.format("%.2f", initialPrice));
//        System.out.println("Price after discounts: $" + String.format("%.2f", finalPrice));
//
//        // Process payment
//        paymentService.processPayment(finalPrice);
//        notificationService.sendNotification("Your payment for order " + order.getOrderId() + " was successful.");
//
//        // Arrange delivery
//        deliveryService.deliverOrder(order);
//        notificationService.sendNotification("Your order " + order.getOrderId() + " has been shipped.");
//    }
//}
//
//
//
//
//
//
//
//public class praktika {
//    public static void main(String[] args) {
//        IPayment payment = new CreditCardPayment();
//        IDelivery delivery = new CourierDelivery();
//        INotification notification = new EmailNotification();
//
//        // 2. discount calculator
//        DiscountCalculator calculator = new DiscountCalculator();
//        calculator.addDiscount(new PercentageDiscount(10)); // 10% discount
//        calculator.addDiscount(new FixedAmountDiscount(5));  // $5 off after percentage
//
//        // 3. Create the order and add products
//        Order order = new Order();
//        order.addItem(new Product("Laptop", 1200.00));
//        order.addItem(new Product("Mouse", 25.00));
//
//        // 4. мэйн процесс
//        OrderProcessor processor = new OrderProcessor(payment, delivery, notification, calculator);
//
//        // 5. Process the order
//        System.out.println("--- Processing a new order ---");
//        processor.process(order);
//        System.out.println("----------------------------");
//
//        // --- LSP Example: Swap implementations easily ---
//        System.out.println("\n--- Processing another order with different services ---");
//        Order secondOrder = new Order();
//        secondOrder.addItem(new Product("Book", 30.00));
//
//        // Use different services without changing the OrderProcessor
//        OrderProcessor secondProcessor = new OrderProcessor(
//                new PayPalPayment(),
//                new PostDelivery(),
//                new SmsNotification(),
//                new DiscountCalculator()
//        );
//        secondProcessor.process(secondOrder);
//        System.out.println("----------------------------------------------------");
//    }
//}