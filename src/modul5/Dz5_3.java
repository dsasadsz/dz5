package modul5;
import java.util.ArrayList;
import java.util.List;




interface IPrototype {
    IPrototype clone();
}

class Product implements IPrototype {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public IPrototype clone() {
        return new Product(name, price, quantity);
    }

    @Override
    public String toString() {
        return name + " — " + price + " тг x " + quantity;
    }
}



class Discount implements IPrototype {
    private String name;
    private double percent;

    public Discount(String name, double percent) {
        this.name = name;
        this.percent = percent;
    }

    public String getName() { return name; }
    public double getPercent() { return percent; }

    @Override
    public IPrototype clone() {
        return new Discount(name, percent);
    }

    @Override
    public String toString() {
        return name + " (" + percent + "%)";
    }
}



class Order implements IPrototype {
    private List<Product> products = new ArrayList<>();
    private List<Discount> discounts = new ArrayList<>();
    private double deliveryCost;
    private String paymentMethod;

    public void addProduct(Product p) { products.add(p); }
    public void addDiscount(Discount d) { discounts.add(d); }
    public void setDeliveryCost(double deliveryCost) { this.deliveryCost = deliveryCost; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    @Override
    public IPrototype clone() {
        Order copy = new Order();
        for (Product p : products) {
            copy.addProduct((Product) p.clone());
        }
        for (Discount d : discounts) {
            copy.addDiscount((Discount) d.clone());
        }
        copy.setDeliveryCost(deliveryCost);
        copy.setPaymentMethod(paymentMethod);
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Товары:\n");
        for (Product p : products) sb.append("  ").append(p).append("\n");
        sb.append("Доставка: ").append(deliveryCost).append(" тг\n");
        sb.append("Скидки:\n");
        for (Discount d : discounts) sb.append("  ").append(d).append("\n");
        sb.append("Метод оплаты: ").append(paymentMethod).append("\n");
        return sb.toString();
    }
}






public class Dz5_3 {
    public static void main(String[] args) {
        Order baseOrder = new Order();
        baseOrder.addProduct(new Product("Ноутбук", 350000, 1));
        baseOrder.addProduct(new Product("Мышь", 5000, 2));
        baseOrder.addDiscount(new Discount("Весенняя распродажа", 10));
        baseOrder.setDeliveryCost(2000);
        baseOrder.setPaymentMethod("Карта");

        System.out.println("Исходный заказ:\n" + baseOrder);

        Order clonedOrder = (Order) baseOrder.clone();
        clonedOrder.addProduct(new Product("Коврик для мыши", 2000, 1));
        clonedOrder.setPaymentMethod("НАЛИЧные");

        System.out.println("Клонированный заказ:\n" + clonedOrder);
    }
}
