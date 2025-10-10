package modul4;
import java.util.Scanner;


interface IVehicle {
    void Drive();
    void Refuel();
}

class Car implements IVehicle {
    private String brand;
    private String model;
    private String fuelType;

    public Car(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }

    @Override
    public void Drive() {
        System.out.println("Машина " + brand + " " + model + " едет на " + fuelType);
    }

    @Override
    public void Refuel() {
        System.out.println("Машина " + brand + " заправляется " + fuelType);
    }
}

class Motorcycle implements IVehicle {
    private String type;
    private int engineVolume;

    public Motorcycle(String type, int engineVolume) {
        this.type = type;
        this.engineVolume = engineVolume;
    }

    @Override
    public void Drive() {
        System.out.println("Мотоцикл (" + type + ") с объемом двигателя " + engineVolume + " едет!");
    }

    @Override
    public void Refuel() {
        System.out.println("Мотоцикл (" + type + ") заправляется бензином");
    }
}

class Truck implements IVehicle {
    private int capacity;
    private int axles;

    public Truck(int capacity, int axles) {
        this.capacity = capacity;
        this.axles = axles;
    }

    @Override
    public void Drive() {
        System.out.println("Грузовик с грузоподъемностью " + capacity + " кг и " + axles + "  едет!");
    }

    @Override
    public void Refuel() {
        System.out.println("Грузовик заправляется дизелем");
    }
}

class Bus implements IVehicle {
    private int seats;
    private String route;

    public Bus(int seats, String route) {
        this.seats = seats;
        this.route = route;
    }

    @Override
    public void Drive() {
        System.out.println("Автобус на " + seats + " мест едет по маршруту " + route);
    }

    @Override
    public void Refuel() {
        System.out.println("Автобус заправляется дизельным топливом");
    }
}












abstract class VehicleFactory {
    public abstract IVehicle CreateVehicle();
}

class CarFactory extends VehicleFactory {
    private String brand;
    private String model;
    private String fuelType;

    public CarFactory(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }

    @Override
    public IVehicle CreateVehicle() {
        return new Car(brand, model, fuelType);
    }
}

class MotorcycleFactory extends VehicleFactory {
    private String type;
    private int engineVolume;

    public MotorcycleFactory(String type, int engineVolume) {
        this.type = type;
        this.engineVolume = engineVolume;
    }

    @Override
    public IVehicle CreateVehicle() {
        return new Motorcycle(type, engineVolume);
    }
}

class TruckFactory extends VehicleFactory {
    private int capacity;
    private int axles;

    public TruckFactory(int capacity, int axles) {
        this.capacity = capacity;
        this.axles = axles;
    }

    @Override
    public IVehicle CreateVehicle() {
        return new Truck(capacity, axles);
    }
}

class BusFactory extends VehicleFactory {
    private int seats;
    private String route;

    public BusFactory(int seats, String route) {
        this.seats = seats;
        this.route = route;
    }

    @Override
    public IVehicle CreateVehicle() {
        return new Bus(seats, route);
    }
}














public class DZ {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите транспорт car (1)      motorcycle (2)     truck (3)   bus (3): ");
        String choice = scanner.nextLine().toLowerCase();

        VehicleFactory factory = null;

        switch (choice) {
            case "1":
                System.out.print("Введите марку: ");
                String brand = scanner.nextLine();
                System.out.print("Введите модель: ");
                String model = scanner.nextLine();
                System.out.print("Введите тип топлива (бензин/дизель): ");
                String fuel = scanner.nextLine();
                factory = new CarFactory(brand, model, fuel);
                break;

            case "2":
                System.out.print("Введите тип мотоцикла (спортивный/туристический): ");
                String type = scanner.nextLine();
                System.out.print("Введите объем двигателя: ");
                int volume = scanner.nextInt();
                factory = new MotorcycleFactory(type, volume);
                break;

            case "3":
                System.out.print("Введите грузоподъемность (кг): ");
                int capacity = scanner.nextInt();
                System.out.print("Введите количество осей: ");
                int axles = scanner.nextInt();
                factory = new TruckFactory(capacity, axles);
                break;

            case "4":
                System.out.print("Введите количество мест: ");
                int seats = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Введите маршрут: ");
                String route = scanner.nextLine();
                factory = new BusFactory(seats, route);
                break;

            default:
                System.out.println("Неизвестный тип транспорта!");
                return;
        }

        IVehicle vehicle = factory.CreateVehicle();
        vehicle.Drive();
        vehicle.Refuel();
    }
}
