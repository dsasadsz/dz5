package modul7;
import java.util.Scanner;

abstract class Bev {
    final void prepare() {
        if (useCustomSeq()) {
            prepareCustom();
            return;
        }
        boil();
        brew();
        pour();
        if (customerWantsCondiments()) addCond();
    }

    protected void boil() { System.out.println("Кипячение воды"); }
    protected abstract void brew();
    protected void pour() { System.out.println("Налить в чашку"); }
    protected abstract void addCond();
    protected boolean customerWantsCondiments() { return true; }
    protected boolean useCustomSeq() { return false; }
    protected void prepareCustom() { prepare(); }
}

class Tea extends Bev {
    protected void brew() { System.out.println("Заваривание чая"); }
    protected void addCond() { System.out.println("Добавить лимон/мёд"); }
}

class Cof extends Bev {
    protected void brew() { System.out.println("Заваривание кофе"); }
    protected void addCond() { System.out.println("Добавить сахар/молоко"); }

    protected boolean customerWantsCondiments() {
        Scanner sc = new Scanner(System.in);
        String ans = "";
        while (true) {
            System.out.print("Добавлять ли добавки в кофе? (y/n): ");
            if (!sc.hasNextLine()) continue;
            ans = sc.nextLine().trim().toLowerCase();
            if (ans.equals("y") || ans.equals("n")) break;
            System.out.println("Неверный ввод. Введите 'y' или 'n'.");
        }
        return ans.equals("y");
    }
}

class Choc extends Bev {
    protected boolean useCustomSeq() { return true; }

    protected void prepareCustom() {
        heatMilk();
        mixChocolate();
        pour();
        addToppings();
    }

    protected void brew() { System.out.println("Создание шоколадной основы"); }
    protected void addCond() { System.out.println("Добавить маршмеллоу"); }

    private void heatMilk() { System.out.println("Нагрев молока"); }
    private void mixChocolate() { System.out.println("Смешать какао и сахар"); }
    private void addToppings() { System.out.println("Посыпать какао/маршмеллоу"); }
}

public class TemplateMethod {
    public static void main(String[] args) {
        System.out.println("--- Чай ---");
        Bev t = new Tea();
        t.prepare();

        System.out.println("\n--- Кофе ---");
        Bev c = new Cof();
        c.prepare();

        System.out.println("\n--- Горячий шоколад (изменённая последовательность) ---");
        Bev ch = new Choc();
        ch.prepare();
    }
}
