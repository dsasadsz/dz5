package modul8;
import java.util.Arrays;
import java.util.List;



interface IPaymentProcessor {
    void processPayment(double amount);
}

class PayPalPaymentProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.printf("[PayPal] Processing payment: %.2f%n", amount);
        System.out.println("[PayPal] Payment successful.");
    }
}


class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.printf("[Stripe] Making transaction for: %.2f%n", totalAmount);
        System.out.println("[Stripe] Transaction completed.");
    }
}

class StripePaymentAdapter implements IPaymentProcessor {
    private final StripePaymentService stripe;

    public StripePaymentAdapter(StripePaymentService stripe) {
        this.stripe = stripe;
    }

    @Override
    public void processPayment(double amount) {
        stripe.makeTransaction(amount);
    }
}



class FastPayService {
    private final String apiKey;

    public FastPayService(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean payAmount(double amount, String currency) {
        System.out.printf("[FastPay] payAmount called (apiKey=%s): %.2f %s%n", apiKey, amount, currency);
        System.out.println("[FastPay] Payment accepted.");
        return true;
    }
}



class FastPayAdapter implements IPaymentProcessor {
    private final FastPayService fastPay;
    private final String currency;

    public FastPayAdapter(FastPayService fastPay, String currency) {
        this.fastPay = fastPay;
        this.currency = currency;
    }

    @Override
    public void processPayment(double amount) {
        fastPay.payAmount(amount, currency);
    }
}




public class Dz2 {
    public static void main(String[] args) {
        IPaymentProcessor paypal = new PayPalPaymentProcessor();
        IPaymentProcessor stripe = new StripePaymentAdapter(new StripePaymentService());
        IPaymentProcessor fastpay = new FastPayAdapter(new FastPayService("APIKEY-XYZ"), "USD");

        List<IPaymentProcessor> processors = Arrays.asList(paypal, stripe, fastpay);

        double[] amounts = {49.99, 120.00, 7.5};
        for (int i = 0; i < processors.size(); i++) {
            IPaymentProcessor p = processors.get(i);
            double amt = amounts[i % amounts.length];
            System.out.println("---- Используем процессор: " + p.getClass().getSimpleName() + " ----");
            p.processPayment(amt);
            System.out.println();
        }
    }
}
