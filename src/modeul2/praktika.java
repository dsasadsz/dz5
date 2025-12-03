//import java.util.ArrayList;
//import java.util.List;
//
//class User {
//    private String name, email, role;
//
//    public User(String name, String email, String role) {
//        this.name = name;
//        this.email = email;
//        this.role = role;
//    }
//
//    public String getName() { return name; }
//    public String getEmail() { return email; }
//    public String getRole() { return role; }
//
//    public void setName(String name) { this.name = name; }
//    public void setEmail(String email) { this.email = email; }
//    public void setRole(String role) { this.role = role; }
//
//    public String toString() {
//        return name + " (" + role + ") - " + email;
//    }
//}
//
//
//
//
//class UserManager {
//    private List<User> users = new ArrayList<>();
//
//    public void addUser(User user) {
//        users.add(user);
//    }
//
//    public void removeUser(String email) {
//        users.removeIf(u -> u.getEmail().equals(email));
//    }
//
//    public void updateUser(String email, String newName, String newEmail, String newRole) {
//        for (User u : users) {
//            if (u.getEmail().equals(email)) {
//                applyUpdates(u, newName, newEmail, newRole);
//                break;
//            }
//        }
//    }
//
//    private void applyUpdates(User user, String name, String email, String role) {
//        if (name != null) user.setName(name); if (email != null) user.setEmail(email); if (role != null) user.setRole(role);
//    }
//
//
//
//    public void printUsers() {
//        for (User u : users) {
//            System.out.println(u);
//        }
//    }
//}
//
//
//public class praktika {
//    public static void main(String[] args) {
//        UserManager manager = new UserManager();
//
//        User user1 = new User("Alex", "alex@irrpror.com", "Admin");
//        User user2 = new User("Qairat", "qairrat@irriri.com", "User");
//
//
//        manager.addUser(user1);
//        manager.addUser(user2);
//
//        System.out.println("Список юзеров:");
//        manager.printUsers();
//
//        System.out.println("\nАпдейт:");
//        manager.updateUser("qairrat@irriri.com", "Kairat", "qairrat@irriri.com", "Admin");
//        manager.printUsers();
//
//        System.out.println("\nУдаляем Alex :");
//        manager.removeUser("alex@irrpror.com");
//        manager.printUsers();
//    }
//}
//
//
//
//
//
////
////public class praktika {
////    public static void main(String[] args) {
////        Scanner scanner = new Scanner(System.in);
////        UserManager manager = new UserManager();
////
////        System.out.println("Система управления пользователями");
////        System.out.println("Команды: add, update, remove, list, exit");
////
////        while (true) {
////            System.out.print("\nВведите команду: ");
////            String command = scanner.nextLine().trim().toLowerCase();
////
////            switch (command) {
////                case "add":
////                    System.out.print("Имя: ");
////                    String name = scanner.nextLine();
////                    System.out.print("Email: ");
////                    String email = scanner.nextLine();
////                    System.out.print("Роль: ");
////                    String role = scanner.nextLine();
////                    manager.addUser(new User(name, email, role));
////                    System.out.println("Пользователь добавлен.");
////                    break;
////
////                case "update":
////                    System.out.print("Email пользователя для обновления: ");
////                    String oldEmail = scanner.nextLine();
////                    System.out.print("Новое имя (Enter, если без изменений): ");
////                    String newName = scanner.nextLine();
////                    System.out.print("Новый email (Enter, если без изменений): ");
////                    String newEmail = scanner.nextLine();
////                    System.out.print("Новая роль (Enter, если без изменений): ");
////                    String newRole = scanner.nextLine();
////                    manager.updateUser(oldEmail, newName, newEmail, newRole);
////                    System.out.println("Пользователь обновлён.");
////                    break;
////
////                case "remove":
////                    System.out.print("Email пользователя для удаления: ");
////                    String removeEmail = scanner.nextLine();
////                    manager.removeUser(removeEmail);
////                    System.out.println("Пользователь удалён.");
////                    break;
////
////                case "list":
////                    System.out.println("Список пользователей:");
////                    manager.printUsers();
////                    break;
////
////                case "exit":
////                    System.out.println("Выход из программы.");
////                    scanner.close();
////                    return;
////
////                default:
////                    System.out.println("Неизвестная команда.");
////            }
////        }
////    }
////}
