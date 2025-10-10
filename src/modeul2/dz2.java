//package modeul2;
//
//
//
//
////1
//class Logger {
//    public void log(String level, String message) {
//        System.out.println(level + ": " + message);
//    }
//}
//
//
//
////2
//class Config {
//     static String CONNECTION_STRING = "Server=myServer;Database=myDb;User Id=myUser;Password=myPass;";
//}
//
//class DatabaseService {
//    public void connect() {
//        System.out.println("Подключение к базе: " + Config.CONNECTION_STRING);
//        // Логика подключения
//    }
//}
//
//class LoggingService {
//    public void log(String message) {
//        System.out.println("Лог в базу: " + Config.CONNECTION_STRING + " | " + message);
//        // Логика записи лога
//    }
//}
//
//
//
//
//
////3
//public void processNumbers(int[] numbers) {
//    if (numbers == null || numbers.length == 0) return;
//
//    for (int number : numbers) {
//        if (number > 0) {
//            System.out.println(number);
//        }
//    }
//}
//
//
//
////4
//
//public void printPositiveNumbers(int[] numbers) {
//    for (int number : numbers) {
//        if (number > 0) {
//            System.out.println(number);
//        }
//    }
//}
//
//
//
//
////5
//public int divide(int a, int b) {
//    if (b == 0) {
//        return 0;
//    }
//    return a / b;
//}
//
//
//
////6
//class UserRepository {
//    public void save(User user) {
//        System.out.println("Сохранение пользователя в базу: " + user.getName());
//    }
//}
//
//class EmailService {
//    public void sendEmail(User user, String message) {
//        System.out.println("Отправка письма на " + user.getEmail() + ": " + message);
//    }
//}
//
//class LabelPrinter {
//    public void printAddressLabel(User user) {
//        System.out.println("Адресный ярлык: " + user.getAddress());
//    }
//}
//
//
//
////7
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.io.IOException;
//
//class FileReader {
//    public String readFile(String filePath) throws IOException {
//        return new String(Files.readAllBytes(Paths.get(filePath)));
//    }
//}
//
//
//
//
//
//
////8
//class ReportGenerator {
//    public void generateReport(String format) {
//        System.out.println("Генерация отчета в формате: " + format);
//        // Логика для нужного формата
//    }
//}
//
