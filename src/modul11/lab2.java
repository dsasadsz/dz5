package modul11;

import java.util.UUID;

class Student {
    private String name;
    private String email;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public void receiveEmail(String subject, String body) {
        System.out.println(">>> EMAIL to " + email + ": [" + subject + "] " + body);
    }
}





class CertificationSystem {

    private static final int PASSING_SCORE = 75;

    public void processCourseCompletion(Student student, int finalScore) {
        System.out.println("--- Анализ результатов для студента: " + student.getName() + " ---");

        if (finalScore >= PASSING_SCORE) {
            handleSuccess(student);
        } else {
            handleFailure(student, finalScore);
        }
    }

    private void handleSuccess(Student student) {

        Thread dbThread = new Thread(() -> {
            saveToDatabase(student);
        });

        Thread certThread = new Thread(() -> {
            String certId = generateCertificate(student);
            student.receiveEmail("Ваш сертификат", "Поздравляем! Ваш сертификат ID: " + certId);
        });

        dbThread.start();
        certThread.start();

        try {
            dbThread.join();
            certThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleFailure(Student student, int score) {
        System.out.println("Статус: Не сдал.");
        student.receiveEmail("Пересдача теста",
                "К сожалению, вы набрали " + score + " баллов. Требуется минимум " + PASSING_SCORE);
    }

    private void saveToDatabase(Student student) {
        System.out.println("[DB] Обновление статуса: Курс завершен для " + student.getName());
        try { Thread.sleep(100); } catch (InterruptedException e) {} // Имитация задержки
    }

    private String generateCertificate(Student student) {
        String id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        System.out.println("[System] Генерация PDF сертификата " + id + "...");
        return id;
    }
}















public class lab2 {
    public static void main(String[] args) {
        CertificationSystem system = new CertificationSystem();

        Student s1 = new Student("Иван Иванов", "ivan@test.com");
        Student s2 = new Student("Петр Петров", "petr@test.com");

        //  Успешная сдача
        system.processCourseCompletion(s1, 85);

        System.out.println();

        //  Неудачная сдача
        system.processCourseCompletion(s2, 60);
    }
}