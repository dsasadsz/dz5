package modeul2;




//1  Don't Repeat Yourself
class OrderServicee {

    public void createOrder(String productName, int quantity, double price) {
        printOrder("created", productName, quantity, price);
    }


    public void updateOrder(String productName, int quantity, double price) {
        printOrder("updated", productName, quantity, price);
    }


    private void printOrder(String action, String productName, int quantity, double price) {
        double totalPrice = quantity * price;
        System.out.println("Order for " + productName + " " + action + ". Total: " + totalPrice);
    }

}




//2

class Vehicle {
    private String type;

    public Vehicle(String type) {
        this.type = type;
    }

    public void start() {
        System.out.println(type + " is starting");
    }

    public void stop() {
        System.out.println(type + " is stopping");
    }
}


class Car extends Vehicle {
    public Car() {
        super("Car");
    }
}

class Truck extends Vehicle {
    public Truck() {
        super("Truck");
    }
}










//3 Keep it short and simple
class Calculator {
    public void performOperation(int a, int b, String operation) {
        if (operation.equals("+"))
            System.out.println(a + b);
        else if (operation.equals("-"))
            System.out.println(a - b);
        else if (operation.equals("*"))
            System.out.println(a * b);
        else if (operation.equals("/")) {
            if (b != 0)
                System.out.println(a / b);
        }
    }
}



//4

class Utility {
    public void doSomething() {
        System.out.println("Doing something...");
    }
}

class Client {
    public void execute() {
        Utility util = new Utility();
        util.doSomething();
    }
}





//5 You Aren't Gonna Need It

class Circle {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double calculateArea() {
        return 3.14 * radius * radius;
    }
}

class Square {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    public double calculateArea() {
        return side * side;
    }
}







//6
class MathOperations {
    public int add(int a, int b) {
        return a + b;
    }
}
