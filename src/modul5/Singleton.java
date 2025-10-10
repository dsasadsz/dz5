package modul5;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


//только один экземпляр (объект)
enum LogLevel {
    INFO,
    WARNING,
    ERROR
}

class Logger {
    private static volatile Logger instance;   //volatile  - Потокобезопасность
    private static final Object lock = new Object();

    private LogLevel currentLevel = LogLevel.INFO;
    private String logFilePath = "logs.txt";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Logger() {}   //запретка  new Logger(). вместо нее Logger.getInstance()


    public static Logger getInstance() {
        if (instance == null) {
            synchronized (lock) {   //Потокобезопасность
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void setLogLevel(LogLevel level) {
        currentLevel = level;
        log("Уровень логирования изменён на: " + level, LogLevel.INFO);
    }

    public void setLogFilePath(String path) {
        logFilePath = path;
        log("Путь к файлу логов изменён на: " + path, LogLevel.INFO);
    }

    public void log(String message, LogLevel level) {
        if (level.ordinal() < currentLevel.ordinal()) return;

        synchronized (lock) {
            try (FileWriter writer = new FileWriter(logFilePath, true)) {
                String timestamp = dateFormat.format(new Date());
                writer.write("[" + timestamp + "] [" + level + "] " + message + "\n");
            } catch (IOException e) {
                System.out.println("Ошибка при записи в лог: " + e.getMessage());
            }
        }
    }

    public void readLogs() {
        synchronized (lock) {
            try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
                System.out.println("\n=== Содержимое файла логов ===");
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("=== Конец логов ===\n");
            } catch (IOException e) {
                System.out.println("Ошибка при чтении логов: " + e.getMessage());
            }
        }
    }
}







public class Singleton {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.setLogLevel(LogLevel.INFO);
        logger.setLogFilePath("app_logs.txt");

        Thread thread1 = new Thread(() -> {
            Logger log = Logger.getInstance();
            for (int i = 0; i < 5; i++) {
                log.log("Поток 1 — сообщение " + i, LogLevel.INFO);
            }
        });

        Thread thread2 = new Thread(() -> {
            Logger log = Logger.getInstance();
            for (int i = 0; i < 5; i++) {
                log.log("Поток 2 — предупреждение " + i, LogLevel.WARNING);
            }
        });

        Thread thread3 = new Thread(() -> {
            Logger log = Logger.getInstance();
            for (int i = 0; i < 5; i++) {
                log.log("Поток 3 — ошибка " + i, LogLevel.ERROR);
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.readLogs();
    }
}
