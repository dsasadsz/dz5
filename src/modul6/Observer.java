package modul6;
import java.util.ArrayList;
import java.util.List;



//Наблюдатель
interface IObserver {
    void update(float temperature);
    String getName();
}

interface ISubject {
    void registerObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers();
}


//списки наблюдателей и  now данные о плшлдде     -   издатель
class WeatherStation implements ISubject {
    private final List<IObserver> observers = new ArrayList<>();
    private float temperature;

    @Override
    public synchronized void registerObserver(IObserver observer) {
        if (observer == null) return;
        if (!observers.contains(observer)) observers.add(observer);
    }

    @Override
    public synchronized void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        List<IObserver> snapshot; //копия наблюдателей
        synchronized (this) {
            snapshot = new ArrayList<>(observers);
        }
        for (IObserver obs : snapshot) {
            try {
                obs.update(temperature);
            } catch (Exception e) {
                System.out.println("Ошибка уведомления " + obs.getName() + ": " + e.getMessage());
            }
        }
    }

    public void setTemperature(float newTemperature) {
        if (Float.isNaN(newTemperature) || Float.isInfinite(newTemperature)) {
            System.out.println("Некорректное значение температуры.");
            return;
        }
        temperature = newTemperature;
        System.out.println("Станция: температура изменена на " + temperature + "°C");
        notifyObservers();   // уведомляет всех
    }
}




//подписчики  - наблюдаетль
class WeatherDisplay implements IObserver {
    private final String name;
    public WeatherDisplay(String name) { this.name = name; }
    @Override
    public void update(float temperature) {
        System.out.println(name + " показывает новую температуру: " + temperature + "°C");
    }
    @Override
    public String getName() { return name; }
}

class EmailAlert implements IObserver {
    private final String name;
    public EmailAlert(String name) { this.name = name; }
    @Override
    public void update(float temperature) {
        System.out.println(name + "  уведомление: текущая температура " + temperature + "°C");
    }
    @Override
    public String getName() { return name; }
}









public class Observer {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();

        WeatherDisplay mobile = new WeatherDisplay("Мобильное приложение");
        WeatherDisplay billboard = new WeatherDisplay("Электронное табло");
        EmailAlert email = new EmailAlert("EmailService");

        station.registerObserver(mobile);
        station.registerObserver(billboard);
        station.registerObserver(email);

        station.setTemperature(25.0f);
        station.setTemperature(30.0f);

        station.removeObserver(billboard);

        station.setTemperature(28.0f);
    }
}
