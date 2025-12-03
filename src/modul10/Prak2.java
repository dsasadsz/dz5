package modul10;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) { super(message); }
}

class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) { super(message); }
}

interface IDataStorage {
    void saveLog(String message);
}

class FileStorage implements IDataStorage {
    private String filename = "library_log.txt";

    @Override
    public void saveLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(LocalDate.now() + ": " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}

abstract class User {
    protected int id;
    protected String name;
    protected String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + name + " (" + email + ")";
    }
}


class Author {
    private String name;

    public Author(String name) { this.name = name; }

    @Override
    public String toString() { return name; }
}

class Book {
    private String title;
    private String isbn;
    private List<Author> authors;
    private boolean isAvailable;

    public Book(String title, String isbn, List<Author> authors) {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.isAvailable = true;
    }

    public String getTitle() { return title; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

    @Override
    public String toString() {
        return String.format("'%s' (ISBN: %s)", title, isbn);
    }
}

class Loan {
    private Book book;
    private Reader reader;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean isActive;

    public Loan(Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
        this.loanDate = LocalDate.now();
        this.isActive = true;
    }

    public void completeLoan() {
        this.returnDate = LocalDate.now();
        this.isActive = false;
    }

    public boolean isActive() { return isActive; }
    public Book getBook() { return book; }
    public Reader getReader() { return reader; }
}


class Reader extends User {
    public Reader(int id, String name, String email) {
        super(id, name, email);
    }

    // Читатель инициирует процесс, но выполняет его Библиотека
    public void requestLoan(Library library, String bookTitle) {
        try {
            library.issueLoan(this, bookTitle);
        } catch (BookNotAvailableException e) {
            System.out.println("Ошибка выдачи: " + e.getMessage());
        }
    }
}

class Librarian extends User {
    public Librarian(int id, String name, String email) {
        super(id, name, email);
    }

    public void addNewBook(Library library, Book book) {
        library.addBook(book);
        System.out.println("Библиотекарь " + name + " добавил книгу: " + book.getTitle());
    }
}


class Library {
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();
    private IDataStorage storage; // Зависимость от абстракции

    public Library(IDataStorage storage) {
        this.storage = storage;
    }

    public void addBook(Book book) {
        books.add(book);
        storage.saveLog("Добавлена книга: " + book.getTitle());
    }

    public Optional<Book> findBook(String title) {
        return books.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public void registerUser(User user) {
        users.add(user);
        storage.saveLog("Зарегистрирован пользователь: " + user.getName());
    }

    public void issueLoan(Reader reader, String bookTitle) throws BookNotAvailableException {
        Optional<Book> bookOpt = findBook(bookTitle);

        if (bookOpt.isEmpty()) {
            throw new BookNotAvailableException("Книга '" + bookTitle + "' не найдена в каталоге.");
        }

        Book book = bookOpt.get();
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Книга '" + bookTitle + "' уже выдана другому читателю.");
        }

        // Транзакция выдачи
        book.setAvailable(false);
        Loan loan = new Loan(book, reader);
        loans.add(loan);

        String msg = "Книга '" + book.getTitle() + "' выдана читателю " + reader.getName();
        System.out.println("УСПЕХ: " + msg);
        storage.saveLog(msg);
    }

    public void returnBook(Reader reader, String bookTitle) {
        Optional<Loan> loanOpt = loans.stream()
                .filter(l -> l.isActive() &&
                        l.getReader().equals(reader) &&
                        l.getBook().getTitle().equalsIgnoreCase(bookTitle))
                .findFirst();

        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            loan.completeLoan();
            loan.getBook().setAvailable(true);

            String msg = "Книга '" + bookTitle + "' возвращена читателем " + reader.getName();
            System.out.println("УСПЕХ: " + msg);
            storage.saveLog(msg);
        } else {
            System.out.println("ОШИБКА: Активная выдача не найдена.");
        }
    }

    // --- Отчеты (делегирование ReportService или внутренний метод) ---
    public void printLibraryStats() {
        System.out.println("\n--- ОТЧЕТ ПО БИБЛИОТЕКЕ ---");
        System.out.println("Всего книг: " + books.size());
        System.out.println("Активных выдач: " + loans.stream().filter(Loan::isActive).count());
        System.out.println("Пользователей: " + users.size());
        System.out.println("---------------------------");
    }
}







public class Prak2 {
    public static void main(String[] args) {
        IDataStorage fileStorage = new FileStorage();
        Library library = new Library(fileStorage);

        List<Author> authors1 = Arrays.asList(new Author("Джоан Роулинг"));
        Book book1 = new Book("Гарри Поттер", "ISBN-001", authors1);

        List<Author> authors2 = Arrays.asList(new Author("Стивен Кинг"));
        Book book2 = new Book("Сияние", "ISBN-002", authors2);

        Librarian admin = new Librarian(1, "Анна Ивановна", "anna@lib.org");
        Reader reader1 = new Reader(101, "Алексей", "alex@mail.com");
        Reader reader2 = new Reader(102, "Мария", "maria@mail.com");

        library.registerUser(admin);
        library.registerUser(reader1);
        library.registerUser(reader2);

        System.out.println("\n--- Действия Библиотекаря ---");
        admin.addNewBook(library, book1);
        admin.addNewBook(library, book2);

        System.out.println("\n--- Выдача книг ---");
        reader1.requestLoan(library, "Гарри Поттер");

        System.out.println("\n--- Попытка взять занятую книгу ---");
        reader2.requestLoan(library, "Гарри Поттер"); // Должна быть ошибка

        reader1.requestLoan(library, "Властелин Колец"); // Должна быть ошибка

        System.out.println("\n--- Возврат книги ---");
        library.returnBook(reader1, "Гарри Поттер");

        System.out.println("\n--- Повторная выдача ---");
        reader2.requestLoan(library, "Гарри Поттер"); // Теперь должно получиться

        library.printLibraryStats();
    }
}