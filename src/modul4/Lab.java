//package modul4;
//
//import java.util.Scanner;
//
//interface Transport {
//    void Move();
//    void FuelUp();
//    void Info();
//}
//
//class Car implements Transport {
//    private String model;
//    private int speed;
//
//    public Car(String model, int speed) {
//        this.model = model;
//        this.speed = speed;
//    }
//
//    @Override
//    public void Move() {
//        System.out.println("Автомобиль едет со скоростью " + speed + " км/ч.");
//    }
//
//    @Override
//    public void FuelUp() {
//        System.out.println("Автомобиль заправляется бензином.");
//    }
//
//    @Override
//    public void Info() {
//        System.out.println("Модель автомобиля: " + model);
//    }
//}
//
//class Motorcycle implements Transport {
//    private String model;
//    private int speed;
//
//    public Motorcycle(String model, int speed) {
//        this.model = model;
//        this.speed = speed;
//    }
//
//    @Override
//    public void Move() {
//        System.out.println("Мотоцикл мчится со скоростью " + speed + " км/ч.");
//    }
//
//    @Override
//    public void FuelUp() {
//        System.out.println("Мотоцикл заправляется бензином.");
//    }
//
//    @Override
//    public void Info() {
//        System.out.println("Модель мотоцикла: " + model);
//    }
//}
//
//class Plane implements Transport {
//    private String model;
//    private int speed;
//
//    public Plane(String model, int speed) {
//        this.model = model;
//        this.speed = speed;
//    }
//
//    @Override
//    public void Move() {
//        System.out.println("Самолет летит со скоростью " + speed + " км/ч.");
//    }
//
//    @Override
//    public void FuelUp() {
//        System.out.println("Самолет заправляется авиационным керосином.");
//    }
//
//    @Override
//    public void Info() {
//        System.out.println("Модель самолета: " + model);
//    }
//}
//
//class Bicycle implements Transport {
//    private String model;
//    private int speed;
//
//    public Bicycle(String model, int speed) {
//        this.model = model;
//        this.speed = speed;
//    }
//
//    @Override
//    public void Move() {
//        System.out.println("Велосипед движется со скоростью " + speed + "км/ч.");
//    }
//
//    @Override
//    public void FuelUp() {
//        System.out.println("Чувак выпевает водички)).");
//    }
//
//    @Override
//    public void Info() {
//        System.out.println("Модель велосипеда: " + model);
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
//abstract class TransportFactory {
//    public abstract Transport createTransport(String model, int speed);
//}
//
//class CarFactory extends TransportFactory {
//    @Override
//    public Transport createTransport(String model, int speed) {
//        return new Car(model, speed);
//    }
//}
//
//class MotorcycleFactory extends TransportFactory {
//    @Override
//    public Transport createTransport(String model, int speed) {
//        return new Motorcycle(model, speed);
//    }
//}
//
//class PlaneFactory extends TransportFactory {
//    @Override
//    public Transport createTransport(String model, int speed) {
//        return new Plane(model, speed);
//    }
//}
//
//class BicycleFactory extends TransportFactory {
//    @Override
//    public Transport createTransport(String model, int speed) {
//        return new Bicycle(model, speed);
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
//public class Lab {
//    public static void main(String[] args) {
//        Scanner s = new Scanner(System.in);
//
//        System.out.println("Выберите тип транспорта: car (1);  motorcycle (2);  plane (3);   bicycle (4): ");
//        String type = s.nextLine();
//
//        System.out.println("Введите модель: ");
//        String model = s.nextLine();
//
//        System.out.println("Введите скорость: ");
//        int speed = s.nextInt();
//
//        TransportFactory factory = null;
//
//        switch (type.toLowerCase()) {
//            case "1":
//                factory = new CarFactory();
//                break;
//            case "2":
//                factory = new MotorcycleFactory();
//                break;
//            case "3":
//                factory = new PlaneFactory();
//                break;
//            case "4":
//                factory = new BicycleFactory();
//                break;
//            default:
//                System.out.println("Эрор!");
//                return;
//        }
//
//        Transport transport = factory.createTransport(model, speed);
//
//        transport.Info();
//        transport.Move();
//        transport.FuelUp();
//    }
//}
