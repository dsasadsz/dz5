import java.util.ArrayList;
import java.util.List;



abstract class Vehicle {
     String mark;
     String model;
     int year;

    public Vehicle(String mark, String model, int year) {
        this.mark = mark;
        this.model = model;
        this.year = year;
    }

    public void startEngine() {
        System.out.println(mark + " " + model + " завел двигатель.");
    }

    public void stopEngine() {
        System.out.println(mark + " " + model + " остановил двигатель.");
    }

    public abstract void info();
}









class Car extends Vehicle {
     int doors;
     String transmission;

    public Car(String mark, String model, int year, int doors, String transmission) {
        super(mark, model, year);
        this.doors = doors;
        this.transmission = transmission;
    }


    public void info() {
        System.out.println("Автомобиль: " + mark + " " + model + " (" + year + "), дверей: " + doors + ", трансмиссия: " + transmission);
    }
}







class Motorcycle extends Vehicle {
     String kuzovT;
     boolean hasBox;

    public Motorcycle(String mark, String model, int year, String kuzovT, boolean hasBox) {
        super(mark, model, year);
        this.kuzovT = kuzovT;
        this.hasBox = hasBox;
    }

    public void info() {
        System.out.println("Мотоцикл: " + mark + " " + model + " (" + year + "), тип: " + kuzovT + ", багажник: " + (hasBox ? "есть" : "нет"));
    }
}





class Garage {
     List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
        System.out.println(v.mark + " " + v.model + " добавлен в гараж.");
    }

    public void removeV(Vehicle v) {
        vehicles.remove(v);
        System.out.println(v.mark + " " + v.model + " удален из гаража.");
    }

    public void showVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("Гараж пуст.");
        } else {
            System.out.println("Транспортные средства в гараже:");
            for (Vehicle v : vehicles) {
                v.info();
            }
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}







class Fleet {
     List<Garage> garages = new ArrayList<>();

    public void addGarage(Garage g) {
        garages.add(g);
        System.out.println("Гараж добавлен в автопарк.");
    }

    public void removeGarage(Garage g) {
        garages.remove(g);
        System.out.println("Гараж удален из автопарка.");
    }

    public void showFleet() {
        if (garages.isEmpty()) {
            System.out.println("Автопарк пуст.");
        } else {
            System.out.println("Автопарк содержит " + garages.size() + " гаражей:");
            for (int i = 0; i < garages.size(); i++) {
                System.out.println("Гараж " + (i + 1) + ":");
                garages.get(i).showVehicles();
            }
        }
    }

    public void findVehicle(String model) {
        for (Garage g : garages) {
            for (Vehicle v : g.getVehicles()) {
                if (v.model.equalsIgnoreCase(model)) {
                    System.out.println("Найден транспорт: ");
                    v.info();
                    return;
                }
            }
        }
        System.out.println("Транспорт с моделью " + model + " не найден.");
    }
}












public class Prov {
    public static void main(String[] args) {
        Vehicle c1 = new Car("Changan ", "Uni-V I", 2021, 4, "Автомат"),
                c2 = new Car("BMW", "X5", 2019, 5, "Механика");

        Vehicle moto1 = new Motorcycle("Yamaha", "R1", 2021, "Спорт", false),
                moto2 = new Motorcycle("Mangust ", "YX250-C5B", 2021, "Стрит", true);




        Garage g1 = new Garage(),
                g2 = new Garage();
        System.out.println(" ");


        g1.addVehicle(c1);
        g1.addVehicle(moto1);
        g2.addVehicle(c2);
        g2.addVehicle(moto2);

        System.out.println(" ");



        Fleet f = new Fleet();
        f.addGarage(g1);
        f.addGarage(g2);
        System.out.println(" ");



        f.showFleet();
        System.out.println(" ");


        f.findVehicle("Camry");
        System.out.println(" ");


        g1.removeV(moto1);
        f.showFleet();
        System.out.println(" ");

    }
}