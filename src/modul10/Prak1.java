//package modul10;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//
//
//class Product {
//    private int id;
//    private String name;
//    private double price;
//
//    public Product(int id, String name, double price) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//    }
//
//    public int getId() { return id; }
//    public String getName() { return name; }
//    public double getPrice() { return price; }
//    public void setName(String name) { this.name = name; }
//    public void setPrice(double price) { this.price = price; }
//
//    @Override
//    public String toString() {
//        return String.format("ID: %d | %s | %.2f rub.", id, name, price);
//    }
//}
//
//class Order {
//    private int id;
//    private String customerName;
//    private List<Product> products;
//    private String status;
//
//    public Order(int id, String customerName, List<Product> products) {
//        this.id = id;
//        this.customerName = customerName;
//        this.products = new ArrayList<>(products);
//        this.status = "Обрабатывается"; // Статус по умолчанию
//    }
//
//    public int getId() { return id; }
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//
//    @Override
//    public String toString() {
//        return "Заказ #" + id + " (" + customerName + "): " + products.size() + " товаров. Статус: " + status;
//    }
//}
//
//class User {
//    protected String name;
//    protected StoreData store; // Ссылка на "базу данных" магазина
//
//    public User(String name, StoreData store) {
//        this.name = name;
//        this.store = store;
//    }
//
//    public String getName() { return name; }
//}
//
//
//
////БД
//class StoreData {
//    public List<Product> products = new ArrayList<>();
//    public List<Order> orders = new ArrayList<>();
//    public List<User> users = new ArrayList<>();
//    private int orderCounter = 1;
//
//
//    public void createOrder(String customerName, List<Product> cartItems) {
//        Order newOrder = new Order(orderCounter++, customerName, cartItems);
//        orders.add(newOrder);
//        System.out.println("Система: Заказ #" + newOrder.getId() + " успешно создан.");
//    }
//}
//
//
////актор
//
//
////сатып аллушы/ клиент
//class Buyer extends User {
//    private List<Product> cart = new ArrayList<>();
//
//    public Buyer(String name, StoreData store) {
//        super(name, store);
//    }
//
//    public void viewProducts() {
//        System.out.println("\n--- " + name + ": Просмотр товаров ---");
//        for (Product p : store.products) {
//            System.out.println(p);
//        }
//    }
//
//    public void addToCart(int productId) {
//        Optional<Product> prod = store.products.stream().filter(p -> p.getId() == productId).findFirst();
//        if (prod.isPresent()) {
//            cart.add(prod.get());
//            System.out.println(name + ": Товар '" + prod.get().getName() + "' добавлен в корзину.");
//        } else {
//            System.out.println(name + ": Товар с ID " + productId + " не найден.");
//        }
//    }
//
//    public void checkout() {
//        if (cart.isEmpty()) {
//            System.out.println(name + ": Корзина пуста.");
//            return;
//        }
//        System.out.println(name + ": Оформление заказа...");
//        store.createOrder(this.name, cart);
//        cart.clear();
//    }
//
//    public void trackOrder(int orderId) {
//        System.out.println("\n--- " + name + ": Проверка статуса заказа #" + orderId + " ---");
//        Optional<Order> order = store.orders.stream().filter(o -> o.getId() == orderId).findFirst();
//        if (order.isPresent()) {
//            System.out.println("Статус заказа: " + order.get().getStatus());
//        } else {
//            System.out.println("Заказ не найден.");
//        }
//    }
//}
//
//// сатушы
//class Seller extends User {
//    public Seller(String name, StoreData store) {
//        super(name, store);
//    }
//
//    public void addProduct(int id, String name, double price) {
//        store.products.add(new Product(id, name, price));
//        System.out.println("Продавец " + this.name + ": Добавил товар '" + name + "'.");
//    }
//
//    public void updateProductPrice(int id, double newPrice) {
//        Optional<Product> prod = store.products.stream().filter(p -> p.getId() == id).findFirst();
//        if (prod.isPresent()) {
//            prod.get().setPrice(newPrice);
//            System.out.println("Продавец " + this.name + ": Цена товара ID " + id + " обновлена.");
//        }
//    }
//
//    public void viewOrders() {
//        System.out.println("\n--- Продавец " + name + ": Список заказов ---");
//        for (Order o : store.orders) {
//            System.out.println(o);
//        }
//    }
//}
//
//
//
//// админ
//class Admin extends User {
//    public Admin(String name, StoreData store) {
//        super(name, store);
//    }
//
//    public void removeUser(String userName) {
//        store.users.removeIf(u -> u.getName().equals(userName));
//        System.out.println("Админ " + this.name + ": Пользователь " + userName + " удален.");
//    }
//
//    public void removeProduct(int id) {
//        store.products.removeIf(p -> p.getId() == id);
//        System.out.println("Админ " + this.name + ": Товар ID " + id + " удален из магазина.");
//    }
//
//    public void updateOrderStatus(int orderId, String status) {
//        Optional<Order> order = store.orders.stream().filter(o -> o.getId() == orderId).findFirst();
//        if (order.isPresent()) {
//            order.get().setStatus(status);
//            System.out.println("Админ " + this.name + ": Статус заказа #" + orderId + " изменен на '" + status + "'.");
//        }
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
//public class Prak1 {
//    public static void main(String[] args) {
//        StoreData store = new StoreData();
//
//        Seller seller = new Seller("Иван", store);
//        Buyer buyer = new Buyer("Анна", store);
//        Admin admin = new Admin("Сергей Петрович", store);
//
//        store.users.add(seller);
//        store.users.add(buyer);
//        store.users.add(admin);
//
//        System.out.println("Продавец продукт қосады ");
//        seller.addProduct(101, "Ноутбук", 50000);
//        seller.addProduct(102, "Мышь", 1500);
//        seller.addProduct(103, "Клавиатура", 3000);
//        seller.updateProductPrice(102, 1200); // Скидка
//
//        System.out.println("\n клиент покупка жасайд");
//        buyer.viewProducts();
//        buyer.addToCart(101); // Берет ноутбук
//        buyer.addToCart(102); // Берет мышь
//        buyer.checkout();     // Оформляет заказ (получит ID 1)
//
//        System.out.println("\n сатушы продукт тексереді клиенттің");
//        seller.viewOrders();
//
//        System.out.println("\n Админ система өзгерттед");
//        admin.updateOrderStatus(1, "Отправлен"); // Меняет статус заказа Анны
//        admin.removeProduct(103); // Удаляет клавиатуру, бітті товар
//
//        System.out.println("\n клиент тексеру сатусың");
//        buyer.trackOrder(1);
//        buyer.viewProducts();
//    }
//}