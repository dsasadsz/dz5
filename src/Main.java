


class Empl {
     String name;
     int id;
     String posi;

    public Empl(String name, int id, String posi) {
        this.name = name;
        this.id = id;
        this.posi = posi;
    }

    public double cSalary() {
        return 0.0;
    }

    public void inf() {
        System.out.println( id + ", " + name + ", " + posi + ", " + cSalary());
    }
}





class Worker extends Empl {
     double hStavka;
     int hWorked;

    public Worker(String name, int id, String posi, double hStavka, int hWorked) {
        super(name, id, posi);
        this.hStavka = hStavka;
        this.hWorked = hWorked;
    }

    public double cSalary() {
        return hStavka * hWorked;
    }
}






class Manager extends Empl {
     double fixSalary;
     double bonus;

    public Manager(String name, int id, String posi, double fixSalary, double bonus) {
        super(name, id, posi);
        this.fixSalary = fixSalary;
        this.bonus = bonus;
    }

    public double cSalary() {
        return fixSalary + bonus;
    }
}






public class Main {
    public static void main(String[] args) {

        Empl emp1 = new Worker("Марат", 0015, "Рабочий", 500, 160);
        Empl emp2 = new Worker("Қайрат", 0032, "Рабочий", 600, 150);
        Empl emp3 = new Manager("Азат", 001, "Менеджер", 50000, 10000);
        Empl emp4 = new Manager("Сәуле", 002, "Менеджер", 60000, 15000);

        Empl[] E = {emp1, emp2, emp4, emp3};

        for (Empl e : E) {
            e.inf();
        }
    }
}