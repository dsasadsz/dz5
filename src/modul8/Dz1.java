package modul8;


interface Bev { // Beverage
    String desc(); // description
    double cost();
}

class Esp implements Bev { // Espresso
    @Override
    public String desc() { return "Espresso"; }
    @Override
    public double cost() { return 80.0; }
}

class Tea implements Bev { // Tea
    @Override
    public String desc() { return "Tea"; }
    @Override
    public double cost() { return 40.0; }
}

class Lat implements Bev { // Latte
    @Override
    public String desc() { return "Latte"; }
    @Override
    public double cost() { return 120.0; }
}

class Moc implements Bev { // Mocha
    @Override
    public String desc() { return "Mocha"; }
    @Override
    public double cost() { return 140.0; }
}


abstract class Dec implements Bev { // BeverageDecorator
    protected final Bev inner;
    public Dec(Bev inner) { this.inner = inner; }
    @Override public String desc() { return inner.desc(); }
    @Override public double cost() { return inner.cost(); }
}




class Milk extends Dec { // Milk (полное: Milk)
    public Milk(Bev inner) { super(inner); }
    @Override public String desc() { return inner.desc() + ", +Milk"; }
    @Override public double cost() { return inner.cost() + 15.0; }
}

class Sug extends Dec { // Sugar (полное: Sugar)
    public Sug(Bev inner) { super(inner); }
    @Override public String desc() { return inner.desc() + ", +Sugar"; }
    @Override public double cost() { return inner.cost() + 5.0; }
}

class Whip extends Dec { // WhippedCream (полное: WhippedCream)
    public Whip(Bev inner) { super(inner); }
    @Override public String desc() { return inner.desc() + ", +Whip"; }
    @Override public double cost() { return inner.cost() + 25.0; }
}

class Syr extends Dec { // Syrup (полное: Syrup)
    private final String flavor;
    public Syr(Bev inner, String flavor) { super(inner); this.flavor = flavor; }
    @Override public String desc() { return inner.desc() + ", +Syrup(" + flavor + ")"; }
    @Override public double cost() { return inner.cost() + 12.0; }
}

class Van extends Dec { // VanillaShot (полное: VanillaShot)
    public Van(Bev inner) { super(inner); }
    @Override public String desc() { return inner.desc() + ", +Vanilla"; }
    @Override public double cost() { return inner.cost() + 18.0; }
}

class ExtraEsp extends Dec { // ExtraEspressoShot (полное: ExtraEspressoShot)
    public ExtraEsp(Bev inner) { super(inner); }
    @Override public String desc() { return inner.desc() + ", +ExtraShot"; }
    @Override public double cost() { return inner.cost() + 30.0; }
}





class B { // Builder
    public static Bev e() { return new Esp(); }
    public static Bev t() { return new Tea(); }
    public static Bev l() { return new Lat(); }
    public static Bev m() { return new Moc(); }

    public static Bev milk(Bev b) { return new Milk(b); }
    public static Bev sugar(Bev b) { return new Sug(b); }
    public static Bev whip(Bev b) { return new Whip(b); }
    public static Bev syrup(Bev b, String f) { return new Syr(b, f); }
    public static Bev van(Bev b) { return new Van(b); }
    public static Bev shot(Bev b) { return new ExtraEsp(b); }
}







public class Dz1 {
    public static void main(String[] args) {
        Bev b1 = B.milk(B.sugar(B.e())); // Espresso + Sugar + Milk
        print(b1);

        Bev b2 = B.whip(B.van(B.l())); // Latte + Vanilla + Whip
        print(b2);

        Bev b3 = B.syrup(B.shot(B.m()), "Caramel"); // Mocha + ExtraShot + Caramel syrup
        print(b3);

        Bev b4 = B.sugar(B.sugar(B.milk(B.t()))); // Tea double sugar + milk
        print(b4);

        Bev b5 = B.shot(B.shot(B.shot(B.e()))); // Triple espresso shot
        print(b5);


        //динамическое добавление декораторов в цикле
        Bev base = B.e();
        String[] adds = {"milk", "sugar", "caramel", "shot", "vanilla"};
        for (String a : adds) {
            switch (a) {
                case "milk": base = B.milk(base); break;
                case "sugar": base = B.sugar(base); break;
                case "caramel": base = B.syrup(base, "Caramel"); break;
                case "shot": base = B.shot(base); break;
                case "vanilla": base = B.van(base); break;
            }
        }
        print(base);
    }

    private static void print(Bev b) {
        System.out.printf("%-50s : %6.2f%n", b.desc(), b.cost());
    }
}
