package dz2;

import java.util.ArrayList;
import java.util.Scanner;



class Book {
    private String title;
    private String author;
    private String isbn;
    private int copies;

    public Book(String title, String author, String isbn, int copies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.copies = copies;
    }

    public String getTitle() { return title; }

    public String getIsbn() { return isbn; }

    public int getCopies() { return copies; }

    public void addCopies(int count) {
        this.copies += count;
    }

    public boolean borrowBook() {
        if (copies > 0) {
            copies--;
            return true;
        }
        return false;
    }

    public void returnBook() {
        copies++;
    }

    @Override
    public String toString() {
        return title + " (" + author + "), ISBN: " + isbn + ", Экземпляров: " + copies;
    }
}

class Reader {
    private String name;
    private int readerId;

    public Reader(String name, int readerId) {
        this.name = name;
        this.readerId = readerId;
    }

    public String getName() {
        return name;
    }

    public int getReaderId() {
        return readerId;
    }

    @Override
    public String toString() {
        return "Читатель: " + name + " (ID: " + readerId + ")";
    }
}

class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Reader> readers = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Книга добавлена: " + book);
    }

    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
        System.out.println("Книга с ISBN " + isbn + " удалена.");
    }

    public void registerReader(Reader reader) {
        readers.add(reader);
        System.out.println("Зарегистрирован " + reader);
    }

    public void removeReader(int readerId) {
        readers.removeIf(r -> r.getReaderId() == readerId);
        System.out.println("Читатель с ID " + readerId + " удалён.");
    }

    public void borrowBook(String isbn, int readerId) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.borrowBook()) {
                    System.out.println("Книга \"" + book.getTitle() + "\" выдана читателю ID " + readerId);
                } else {
                    System.out.println("Нет доступных экземпляров книги \"" + book.getTitle() + "\".");
                }
                return;
            }
        }
        System.out.println("Книга с ISBN " + isbn + " не найдена.");
    }

    public void returnBook(String isbn, int readerId) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.returnBook();
                System.out.println("Книга \"" + book.getTitle() + "\" возвращена читателем ID " + readerId);
                return;
            }
        }
        System.out.println("Книга с ISBN " + isbn + " не найдена.");
    }

    public void showBooks() {
        System.out.println("Список книг в библиотеке:");
        for (Book book : books) {
            System.out.println(" - " + book);
        }
    }

    public void showReaders() {
        System.out.println("Список читателей:");
        for (Reader reader : readers) {
            System.out.println(" - " + reader);
        }
    }
}




class Validator {

    // Пров строки
    public static boolean validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("Ошибка: поле \"" + fieldName + "\" не может быть пустым!");
            return false;
        }
        return true;
    }

    // Пров id
    public static boolean validateId(int id) {
        if (id <= 0) {
            System.out.println("Ошибка: ID должен быть положительным числом!");
            return false;
        }
        return true;
    }

    // пров количества книг
    public static boolean validateCopies(int copies) {
        if (copies < 0) {
            System.out.println("Ошибка: количество экземпляров не может быть отрицательным!");
            return false;
        }
        return true;
    }
}





public class main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Меню библиотеки ===");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Удалить книгу");
            System.out.println("3. Показать книги");
            System.out.println("4. Зарегистрировать читателя");
            System.out.println("5. Удалить читателя");
            System.out.println("6. Показать читателей");
            System.out.println("7. Выдать книгу");
            System.out.println("8. Вернуть книгу");
            System.out.println("0. break");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите название: ");
                    String title = scanner.nextLine();
                    if (!Validator.validateString(title, "Название")) break;

                    System.out.print("Введите автора: ");
                    String author = scanner.nextLine();
                    if (!Validator.validateString(author, "Автор")) break;

                    System.out.print("Введите ISBN: ");
                    String isbn = scanner.nextLine();
                    if (!Validator.validateString(isbn, "ISBN")) break;

                    System.out.print("Введите количество экземпляров: ");
                    int copies = scanner.nextInt();
                    if (!Validator.validateCopies(copies)) break;

                    library.addBook(new Book(title, author, isbn, copies));
                    break;

                case 2:
                    System.out.print("Введите ISBN книги для удаления: ");
                    String isbnRemove = scanner.nextLine();
                    if (!Validator.validateString(isbnRemove, "ISBN")) break;
                    library.removeBook(isbnRemove);
                    break;

                case 3:
                    library.showBooks();
                    break;

                case 4:
                    System.out.print("Введите имя читателя: ");
                    String name = scanner.nextLine();
                    if (!Validator.validateString(name, "Имя")) break;

                    System.out.print("Введите ID читателя: ");
                    int id = scanner.nextInt();
                    if (!Validator.validateId(id)) break;

                    library.registerReader(new Reader(name, id));
                    break;

                case 5:
                    System.out.print("Введите ID читателя для удаления: ");
                    int removeId = scanner.nextInt();
                    if (!Validator.validateId(removeId)) break;
                    library.removeReader(removeId);
                    break;

                case 6:
                    library.showReaders();
                    break;

                case 7:
                    System.out.print("Введите ISBN книги: ");
                    String borrowIsbn = scanner.nextLine();
                    if (!Validator.validateString(borrowIsbn, "ISBN")) break;

                    System.out.print("Введите ID читателя: ");
                    int borrowId = scanner.nextInt();
                    if (!Validator.validateId(borrowId)) break;

                    library.borrowBook(borrowIsbn, borrowId);
                    break;

                case 8:
                    System.out.print("Введите ISBN книги: ");
                    String returnIsbn = scanner.nextLine();
                    if (!Validator.validateString(returnIsbn, "ISBN")) break;

                    System.out.print("Введите ID читателя: ");
                    int returnId = scanner.nextInt();
                    if (!Validator.validateId(returnId)) break;

                    library.returnBook(returnIsbn, returnId);
                    break;

                case 0:
                    System.out.println("Выход из программы...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
