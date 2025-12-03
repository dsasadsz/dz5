//package modul10;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
////
//class Book {
//    private String title;
//    private String author;
//    private String isbn;
//    private boolean isAvailable;
//
//    public Book(String title, String author, String isbn) {
//        this.title = title;
//        this.author = author;
//        this.isbn = isbn;
//        this.isAvailable = true;
//    }
//
//    public void markAsLoaned() {
//        this.isAvailable = false;
//    }
//
//    public void markAsAvailable() {
//        this.isAvailable = true;
//    }
//
//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("'%s' (%s) [ISBN: %s]", title, author, isbn);
//    }
//}
//
//
//
//
//// Выдача
//class Loan {
//    private Book book;
//    //Ассоциация
//    private Reader reader;
//    private LocalDate loanDate;
//    private LocalDate returnDate;
//
//    public Loan(Book book, Reader reader) {
//        this.book = book;
//        this.reader = reader;
//        this.loanDate = LocalDate.now();
//        this.returnDate = loanDate.plusDays(14); // Выдача на 2 недели
//    }
//
//    public void issueLoan() {
//        System.out.println("ЗАПИСЬ О ВЫДАЧЕ: Книга " + book.getTitle() +
//                " выдана читателю " + reader.getName() +
//                " до " + returnDate + ".");
//    }
//
//    public void completeLoan() {
//        System.out.println("ЗАПИСЬ О ВОЗВРАТЕ: Книга " + book.getTitle() +
//                " возвращена читателем " + reader.getName() + ".");
//    }
//
//    public Book getBook() {
//        return book;
//    }
//
//    public Reader getReader() {
//        return reader;
//    }
//}
//
//
//
//
//// База давнных
//class Library {
//    private List<Book> books = new ArrayList<>();   //Композиция қатты связь
//    private List<Loan> activeLoans = new ArrayList<>();
//
//    public void addBookToCatalog(Book book) {
//        books.add(book);
//    }
//
//    public void removeBookFromCatalog(Book book) {
//        books.remove(book);
//    }
//
//    public void registerLoan(Loan loan) {
//        activeLoans.add(loan);
//    }
//
//    public void closeLoan(Loan loan) {
//        activeLoans.remove(loan);
//    }
//
//    public List<Loan> getActiveLoans() {
//        return activeLoans;
//    }
//
//    public List<Book> getBooks() {
//        return books;
//    }
//}
//
//
//
//
//
////  АКТОРОs
//
////  Читатель
//class Reader {
//    private int id;
//    private String name;
//    private String email;
//
//    public Reader(int id, String name, String email) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void borrowBook(Book book, Library library) {    //Зависимость
//        if (book.isAvailable()) {
//            book.markAsLoaned();
//            Loan newLoan = new Loan(book, this);
//            library.registerLoan(newLoan);
//            newLoan.issueLoan();
//        } else {
//            System.out.println("ОШИБКА: Книга " + book.getTitle() + " сейчас недоступна.");
//        }
//    }
//
//    public void returnBook(Book book, Library library) {
//        // Іздеу активную выдачу для этой книги и этого читателя
//        Optional<Loan> loanOpt = library.getActiveLoans().stream()
//                .filter(l -> l.getBook().equals(book) && l.getReader().equals(this))
//                .findFirst();
//
//        if (loanOpt.isPresent()) {
//            Loan loan = loanOpt.get();
//            book.markAsAvailable();
//            loan.completeLoan();
//            library.closeLoan(loan);
//        } else {
//            System.out.println("ОШИБКА: У читателя нет активной выдачи на книгу " + book.getTitle());
//        }
//    }
//}
//
////  Библиотекарь
//class Librarian {
//    private int id;
//    private String name;
//    private String position;
//
//    public Librarian(int id, String name, String position) {
//        this.id = id;
//        this.name = name;
//        this.position = position;
//    }
//
//    public void addBook(Book book, Library library) {
//        library.addBookToCatalog(book);
//        System.out.println("БИБЛИОТЕКАРЬ " + name + ": Добавлена книга " + book);
//    }
//
//    public void removeBook(Book book, Library library) {
//        if (library.getBooks().contains(book)) {
//            library.removeBookFromCatalog(book);
//            System.out.println("БИБЛИОТЕКАРЬ " + name + ": Списана книга " + book.getTitle());
//        } else {
//            System.out.println("ОШИБКА: Книга не найдена в каталоге.");
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
//
//public class Lab2 {
//    public static void main(String[] args) {
//        System.out.println(" проввв");
//
//        // библиотек жасаймыз - хранилище
//        Library library = new Library();
//
//        // библиотекарь жасаймыз
//        Librarian librarian = new Librarian(1, "Динара", "Старший библиотекарь");
//
//        // 3. Библиотекарь добавляет книги
//        System.out.println("\n Пополнение фонда ");
//        Book book1 = new Book("Путь Абая", "Мухтар Ауэзов", "978-1");
//        Book book2 = new Book("Слова назидания", "Абай Кунанбаев", "978-2");
//        Book book3 = new Book("Кочевники", "Ильяс Есенберлин", "978-3");
//
//        librarian.addBook(book1, library);
//        librarian.addBook(book2, library);
//        librarian.addBook(book3, library);
//
//        //  читатель жасаймыз
//        Reader reader = new Reader(101, "Алихан", "alikhan@mail.kz");
//
//        // Читатель алады кітап
//        System.out.println("\n : Выдача книг ");
//        reader.borrowBook(book1, library); // Успешно
//        reader.borrowBook(book1, library); // Ошибка (уже занята)
//
//        // Читатель возвращает книгу
//        System.out.println("\n   Возврат книг ");
//        reader.returnBook(book1, library);
//
//        // Библиотекарь удаляет книгу
//        System.out.println("\n Списание книг ");
//        librarian.removeBook(book3, library);
//
//        System.out.println("\n Брейккк");
//    }
//}
