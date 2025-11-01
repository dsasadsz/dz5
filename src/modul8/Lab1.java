package modul8;
import java.util.Locale;

interface IBeverage {
    double getCost();
    String getDescription();
}



class Coffee implements IBeverage {
    @Override
    public double getCost() {
        return 50.0;
    }

    @Override
    public String getDescription() {
        return "Coffee";
    }
}




abstract class BeverageDecorator implements IBeverage {
    protected final IBeverage beverage;

    public BeverageDecorator(IBeverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double getCost() {
        return beverage.getCost();
    }

    @Override
    public String getDescription() {
        return beverage.getDescription();
    }
}




class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 10.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Milk";
    }
}

class SugarDecorator extends BeverageDecorator {
    public SugarDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 5.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Sugar";
    }
}

class ChocolateDecorator extends BeverageDecorator {
    public ChocolateDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 15.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Chocolate";
    }
}

class VanillaDecorator extends BeverageDecorator {
    public VanillaDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 12.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Vanilla";
    }
}

class CinnamonDecorator extends BeverageDecorator {
    public CinnamonDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 8.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Cinnamon";
    }
}






public class Lab1 {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        IBeverage beverage = new Coffee();
        print(beverage);

        beverage = new MilkDecorator(beverage);
        print(beverage);

        beverage = new SugarDecorator(beverage);
        print(beverage);

        beverage = new ChocolateDecorator(beverage);
        print(beverage);



        IBeverage special = new Coffee();
        special = new VanillaDecorator(special);
        special = new MilkDecorator(special);
        special = new CinnamonDecorator(special);
        print(special);




        IBeverage doubleMilk = new Coffee();
        doubleMilk = new MilkDecorator(doubleMilk);
        doubleMilk = new MilkDecorator(doubleMilk);
        print(doubleMilk);
    }

    private static void print(IBeverage b) {
        System.out.printf("%s : %.2f%n", b.getDescription(), b.getCost());
    }
}
