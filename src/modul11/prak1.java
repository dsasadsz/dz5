package modul11;

import java.util.Scanner;

class Book {
    private String title;
    private boolean isAvailable;

    public Book(String title, boolean isAvailable) {
        this.title = title;
        this.isAvailable = isAvailable;
    }

    public String getTitle() { return title; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}

class Reader {
    private String name;
    private boolean hasTicket;

    public Reader(String name, boolean hasTicket) {
        this.name = name;
        this.hasTicket = hasTicket;
    }

    public String getName() { return name; }
    public boolean hasTicket() { return hasTicket; }
    public void setHasTicket(boolean hasTicket) { this.hasTicket = hasTicket; }
}

class LibrarySystem {

    public void processLibraryVisit(Reader reader, Book... requestedBooks) {
        System.out.println("\n--- Начало процесса: " + reader.getName() + " пришел в библиотеку ---");

        if (!checkTicket(reader)) {
            boolean wantsToRegister = askToRegister();
            if (wantsToRegister) {
                issueNewTicket(reader);
            } else {
                System.out.println("❌ Читатель отказался от оформления. Конец процесса.");
                return;
            }
        }

        boolean bookIssued = false;

        for (Book book : requestedBooks) {
            System.out.println("📖 Читатель выбрал книгу: \"" + book.getTitle() + "\"");

            if (checkBookAvailability(book)) {
                registerLoan(reader, book);
                bookIssued = true;
                break;
            } else {
                System.out.println("⚠️ Система: Книга недоступна. Предлагаем выбрать другую...");
            }
        }

        if (!bookIssued) {
            System.out.println("🏁 Читатель не нашел подходящую книгу и ушел.");
        }
    }

    private boolean checkTicket(Reader reader) {
        System.out.print("[Действие] Проверка билета... ");
        if (reader.hasTicket()) {
            System.out.println("✅ Билет есть.");
            return true;
        } else {
            System.out.println("⛔ Билета НЕТ.");
            return false;
        }
    }

    private boolean askToRegister() {
        System.out.println("❓ Система: Хотите оформить билет? (Да/Нет)");
        return true;
    }

    private void issueNewTicket(Reader reader) {
        System.out.println("📝 [Действие] Оформление анкеты... Выдача нового билета.");
        reader.setHasTicket(true);
    }

    private boolean checkBookAvailability(Book book) {
        return book.isAvailable();
    }

    private void registerLoan(Reader reader, Book book) {
        System.out.println("💾 [Система] Регистрация выдачи книги \"" + book.getTitle() + "\" читателю " + reader.getName());
        book.setAvailable(false);
        System.out.println("🎉 [Действие] Читатель получил книгу. Конец процесса.");
    }
}

public class prak1 {
    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();

        Book warAndPeace = new Book("Война и мир", false);
        Book javaGuide = new Book("Изучаем Java", true);

        Reader student = new Reader("Алексей", false);

        library.processLibraryVisit(student, warAndPeace, javaGuide);

        Reader professor = new Reader("Профессор Мориарти", true);
        library.processLibraryVisit(professor, javaGuide);
    }
}