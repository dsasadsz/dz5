package modul5;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class ConfigurationManager {
    private static ConfigurationManager instance;
    private static final Lock lock = new ReentrantLock();
    private Map<String, String> settings = new HashMap<>();

    private ConfigurationManager() {}

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void loadSettingsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) settings.put(parts[0].trim(), parts[1].trim());
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки файла настроек: " + e.getMessage());
        }
    }

    public void saveSettingsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry : settings.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка сохранения файлaa: " + e.getMessage());
        }
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    public String getSetting(String key) {
        return settings.get(key);
    }

    public void showAllSettings() {
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}

public class dz5 {
    public static void main(String[] args) {
        ConfigurationManager config1 = ConfigurationManager.getInstance();
        config1.setSetting("theme", "dark");
        config1.setSetting("language", "ru");
        config1.saveSettingsToFile("conf.txt");
        ConfigurationManager config2 = ConfigurationManager.getInstance();


        System.out.println("Пров: " + (config1 == config2));

        config2.loadSettingsFromFile("conf.txt");
        config2.showAllSettings();
    }
}
