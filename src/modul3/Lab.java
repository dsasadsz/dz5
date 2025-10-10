package modul3;
import java.util.List;
import java.util.Arrays;




class Item {
    private  String name;
    private double price;

    public Item(String name, double price){
        this.name = name;
        this.price = price;
    }

    public double getPrice (){
        return price;
    }
}


class Invoice {
     int id;
     List<Item> items;
     double taxRate;

    public Invoice(int id, List<Item> items, double taxRate) {
    this.id =id;
    this.items = items;
    this.taxRate = taxRate;
    }

    public int getId() { return id;}
    public List<Item> getItems() { return items;}
    public double getTaxRate() {return taxRate;}

}


class InvoiceCalculator {
    public double calculateTotal(Invoice invoice){
        double subTotal = 0;
        for (Item item : invoice.getItems()) {
            subTotal+= item.getPrice();
        }
        return subTotal + (subTotal * invoice.getTaxRate());
    }
}


class InvoiceRepository {
    public void saveToDatabase(Invoice invoice) {
        System.out.println("Сохранение счета-фактуры в базу: ID = " + invoice.getId());
    }
}













//2


interface DiscountStrategy {
    double applyDiscount(double amount);
}


class RegularDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        return amount;
    }
}

class SilverDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.9; // 10%
    }
}

class GoldDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.8; // 20%
    }
}


class DiscountCalculator {
    private final DiscountStrategy discountStrategy;

    public DiscountCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculate(double amount) {
        return discountStrategy.applyDiscount(amount);
    }
}







//3

interface IWorkable {
    void work();
}

interface IEatable {
    void eat();
}
interface ISleepable  {
    void sleep();
}



 class HumanWorker implements IWorkable, IEatable, ISleepable {

    @Override
    public void work() {
        System.out.println("Человек работает");
    }

    @Override
    public void eat() {
        System.out.println("Человек ест");
    }

    @Override
    public void sleep() {
        System.out.println("Человек спит.");
    }
}

 class RobotWorker implements IWorkable {

    @Override
    public void work() {
        System.out.println("Робот работает.");
    }
}








//4


interface MessageService {
    void sendMessage(String message);
}




class EmailService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Отправка Email: " + message);
    }
}

class SmsService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Отправка SMS: " + message);
    }
}





class Notification {
    private final MessageService messageService;

    public Notification(MessageService messageService) {
        this.messageService = messageService;
    }

    public void send(String message) {
        messageService.sendMessage(message);
    }
}

