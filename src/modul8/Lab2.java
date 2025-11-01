//package modul8;
//
//import java.util.Locale;
//import java.util.Scanner;
//
//
////система
//interface IPaymentProcessor {
//    void processPayment(double amount);
//    void refundPayment(double amount);
//}
//
//class InternalPaymentProcessor implements IPaymentProcessor {
//    @Override
//    public void processPayment(double amount) {
//        System.out.printf("Processing payment of %.2f via internal system.%n", amount);
//    }
//
//    @Override
//    public void refundPayment(double amount) {
//        System.out.printf("Refunding payment of %.2f via internal system.%n", amount);
//    }
//}
//
//
////система несовместимая
//class ExternalPaymentSystemA {
//    public void makePayment(double amount) {
//        System.out.printf("Making payment of %.2f via External Payment System A.%n", amount);
//    }
//
//    public void makeRefund(double amount) {
//        System.out.printf("Making refund of %.2f via External Payment System A.%n", amount);
//    }
//}
//
//class ExternalPaymentSystemB {
//    public void sendPayment(double amount) {
//        System.out.printf("Sending payment of %.2f via External Payment System B.%n", amount);
//    }
//
//    public void processRefund(double amount) {
//        System.out.printf("Processing refund of %.2f via External Payment System B.%n", amount);
//    }
//}
//
//
//
////fадаптерлер
//class PaymentAdapterA implements IPaymentProcessor {
//    private final ExternalPaymentSystemA adaptee;
//
//    public PaymentAdapterA(ExternalPaymentSystemA adaptee) {
//        this.adaptee = adaptee;
//    }
//
//    @Override
//    public void processPayment(double amount) {
//        adaptee.makePayment(amount);
//    }
//
//    @Override
//    public void refundPayment(double amount) {
//        adaptee.makeRefund(amount);
//    }
//}
//
//class PaymentAdapterB implements IPaymentProcessor {
//    private final ExternalPaymentSystemB adaptee;
//
//    public PaymentAdapterB(ExternalPaymentSystemB adaptee) {
//        this.adaptee = adaptee;
//    }
//
//    @Override
//    public void processPayment(double amount) {
//        adaptee.sendPayment(amount);
//    }
//
//    @Override
//    public void refundPayment(double amount) {
//        adaptee.processRefund(amount);
//    }
//}
//
//
////логика
//class PaymentSelector {
//    private final IPaymentProcessor internalProcessor;
//    private final IPaymentProcessor adapterA;
//    private final IPaymentProcessor adapterB;
//
//    public PaymentSelector(IPaymentProcessor internalProcessor, IPaymentProcessor adapterA, IPaymentProcessor adapterB) {
//        this.internalProcessor = internalProcessor;
//        this.adapterA = adapterA;
//        this.adapterB = adapterB;
//    }
//
//    public IPaymentProcessor selectByCurrency(String currency) {
//        if (currency == null) return internalProcessor;
//        currency = currency.toUpperCase(Locale.ROOT).trim();
//        switch (currency) {
//            case "KZT":
//            case "UZS":
//                return internalProcessor;
//            case "USD":
//            case "EUR":
//                return adapterA;
//            case "BTC":
//            case "ETH":
//                return adapterB;
//            default:
//                return internalProcessor;
//        }
//    }
//
//    public IPaymentProcessor selectByRegion(String region) {
//        if (region == null) return internalProcessor;
//        region = region.toLowerCase(Locale.ROOT).trim();
//        if (region.contains("eu")) return adapterA;
//        if (region.contains("crypto")) return adapterB;
//        return internalProcessor;
//    }
//}
//
//
//
//
//
//
//
//public class Lab2 {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        IPaymentProcessor internal = new InternalPaymentProcessor();
//        IPaymentProcessor adapterA = new PaymentAdapterA(new ExternalPaymentSystemA());
//        IPaymentProcessor adapterB = new PaymentAdapterB(new ExternalPaymentSystemB());
//
//        PaymentSelector selector = new PaymentSelector(internal, adapterA, adapterB);
//
//        System.out.println("Выберите сценарий:");
//        System.out.println("1 - демонстрация фиксированных вызовов");
//        System.out.println("2 - выбор по валюте");
//        System.out.println("3 - выбор по региону");
//        System.out.print("Ваш выбор: ");
//        String choice = sc.nextLine().trim();
//
//        if ("1".equals(choice)) {
//            System.out.println("\n-- Internal --");
//            internal.processPayment(100.0);
//            internal.refundPayment(50.0);
//
//            System.out.println("\n-- External A via adapter --");
//            adapterA.processPayment(200.0);
//            adapterA.refundPayment(100.0);
//
//            System.out.println("\n-- External B via adapter --");
//            adapterB.processPayment(300.0);
//            adapterB.refundPayment(150.0);
//        } else if ("2".equals(choice)) {
//            System.out.print("Введите валюту (например, KZT, USD, BTC): ");
//            String currency = sc.nextLine().trim();
//            IPaymentProcessor proc = selector.selectByCurrency(currency);
//            System.out.print("Введите сумму: ");
//            double amount = parseDoubleSafe(sc.nextLine());
//            proc.processPayment(amount);
//            proc.refundPayment(amount / 2);
//        } else if ("3".equals(choice)) {
//            System.out.print("Введите регион (например, EU, crypto, KZ): ");
//            String region = sc.nextLine().trim();
//            IPaymentProcessor proc = selector.selectByRegion(region);
//            System.out.print("Введите сумму: ");
//            double amount = parseDoubleSafe(sc.nextLine());
//            proc.processPayment(amount);
//            proc.refundPayment(10.0);
//        } else {
//            System.out.println("Неверный выбор. Завершение.");
//        }
//
//        sc.close();
//    }
//
//    private static double parseDoubleSafe(String s) {
//        try {
//            return Double.parseDouble(s.trim());
//        } catch (Exception e) {
//            return 0.0;
//        }
//    }
//}
//
