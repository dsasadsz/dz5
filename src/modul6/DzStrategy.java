package modul6;
import java.util.Scanner;



interface Strategyy {
    void pay(double amount);
}

class PContext {
    private Strategyy strategy;

    public void sStrategy(Strategyy strategy) {     //set strategy
        this.strategy = strategy;
    }

    public void ePay(double amount) {      ///error pay
        if (strategy == null) {
            System.out.println("[!] Ошибка: способ оплаты не выбран!");
        } else {
            strategy.pay(amount);
        }
    }

    public static void zagruzka(String msg) {
        System.out.print(msg + " ");
        for (int i = 0; i < 3; i++) {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
            System.out.print(".");
        }
        System.out.println();
    }
}




class CardP implements Strategyy {
    @Override
    public void pay(double amount) {
        PContext.zagruzka("Проверка банковских данных");
        System.out.printf("[OK] Оплата картой успешно выполнена! Сумма: %.2f KZT%n", amount);
    }
}

class PayPalP implements Strategyy {
    @Override
    public void pay(double amount) {
        PContext.zagruzka("Подключение к аккаунту PayPal");
        System.out.printf("[OK] Оплата через PayPal завершена! Сумма: %.2f KZT%n", amount);
    }
}

class CryptoP implements Strategyy {
    @Override
    public void pay(double amount) {
        PContext.zagruzka("Проверка блокчейн-транзакции");
        System.out.printf("[OK] Криптовалютный перевод выполнен! Сумма: %.2f KZT%n", amount);
    }
}





public class DzStrategy {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PContext ctx = new PContext();

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║        СИСТЕМА ОПЛАТЫ (Strategy)         ║");
        System.out.println("╚══════════════════════════════════════════╝");

        System.out.println("Выберите способ оплаты:");
        System.out.println("[1] Банковская карта,     [2] PayPal,    [3] Криптовалюта ");
        System.out.print(" Ваш выбор => ");
        String c = sc.nextLine();

        switch (c) {
            case "1": ctx.sStrategy(new CardP()); break;
            case "2": ctx.sStrategy(new PayPalP()); break;
            case "3": ctx.sStrategy(new CryptoP()); break;
            default:
                System.out.println("[X] Неверный выбор, операция отменена.");
                sc.close();
                return;
        }

        System.out.print("Введите сумму оплаты: ");
        double a = Double.parseDouble(sc.nextLine());

        ctx.ePay(a);
        System.out.println("");

        System.out.println("===========================================");
        System.out.println("Спасибо, что воспользовались нашей системой!  ╰(▔∀▔)╯ " );
        System.out.println("===========================================");
        sc.close();
    }
}
