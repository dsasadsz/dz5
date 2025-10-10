//package modul3;
//
////1
//
//public class Order {
//    private String productName;
//    private int quantity;
//    private double price;
//
//    public Order(String productName, int quantity, double price) {
//        this.productName = productName;
//        this.quantity = quantity;
//        this.price = price;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//}
//
//
//
//
////расчёт стоимости
//class PriceCalculator {
//    public double calculateTotalPrice(Order order) {
//        return order.getQuantity() * order.getPrice() * 0.9; // скидка 10%
//    }
//}
//
//// платеж
//class PaymentProcessor {
//    public void processPayment(String paymentDetails) {
//        System.out.println("Payment processed using: " + paymentDetails);
//    }
//}
//
//
//class NotificationService {
//    public void sendConfirmationEmail(String email) {
//        System.out.println("Confirmation email sent to: " + email);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
////2
// class Employee {
//    private String name;
//    private double baseSalary;
//
//    public Employee(String name, double baseSalary) {
//        this.name = name;
//        this.baseSalary = baseSalary;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public double getBaseSalary() {
//        return baseSalary;
//    }
//}
//
//interface SalaryCalculator {
//    double calculateSalary(Employee employee);
//}
//
//class PermanentEmployeeSalaryCalculator implements SalaryCalculator {
//    @Override
//    public double calculateSalary(Employee employee) {
//        return employee.getBaseSalary() * 1.2; // +20% бонус
//    }
//}
//
//class ContractEmployeeSalaryCalculator implements SalaryCalculator {
//    @Override
//    public double calculateSalary(Employee employee) {
//        return employee.getBaseSalary() * 1.1; // +10% бонус
//    }
//}
//
//class InternEmployeeSalaryCalculator implements SalaryCalculator {
//    @Override
//    public double calculateSalary(Employee employee) {
//        return employee.getBaseSalary() * 0.8; // 80%
//    }
//}
//
//class FreelancerEmployeeSalaryCalculator implements SalaryCalculator {
//    @Override
//    public double calculateSalary(Employee employee) {
//        return employee.getBaseSalary() * 0.9; // 90%
//    }
//}
//
//class EmployeeSalaryService {
//    private SalaryCalculator calculator;
//
//    public EmployeeSalaryService(SalaryCalculator calculator) {
//        this.calculator = calculator;
//    }
//
//    public double getSalary(Employee employee) {
//        return calculator.calculateSalary(employee);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////3
//interface Printer {
//    void print(String content);
//}
//
//interface Scanner {
//    void scan(String content);
//}
//
//interface Fax {
//    void fax(String content);
//}
//
//class AllInOnePrinter implements Printer, Scanner, Fax {
//    @Override
//    public void print(String content) {
//        System.out.println("Printing: " + content);
//    }
//
//    @Override
//    public void scan(String content) {
//        System.out.println("Scanning: " + content);
//    }
//
//    @Override
//    public void fax(String content) {
//        System.out.println("Faxing: " + content);
//    }
//}
//
//
//
//class BasicPrinter implements Printer {
//    @Override
//    public void print(String content) {
//        System.out.println("Printing: " + content);
//    }
//}
//
//
//
//class PrintAndScanPrinter implements Printer, Scanner {
//    @Override
//    public void print(String content) {
//        System.out.println("Printing: " + content);
//    }
//
//    @Override
//    public void scan(String content) {
//        System.out.println("Scanning: " + content);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////4
//interface MessageSender {
//    void send(String message);
//}
//
//class EmailSender implements MessageSender {
//    @Override
//    public void send(String message) {
//        System.out.println("Email sent: " + message);
//    }
//}
//
//class SmsSender implements MessageSender {
//    @Override
//    public void send(String message) {
//        System.out.println("SMS sent: " + message);
//    }
//}
//
//class NotificationService {
//    private MessageSender sender;
//
//    public NotificationService(MessageSender sender) {
//        this.sender = sender;
//    }
//
//    public void sendNotification(String message) {
//        sender.send(message);
//    }
//}
//
