package modul9;

class Aud { // AudioSystem
    private boolean on = false;
    private int volume = 50;
    public void turnOn() {
        on = true;
        System.out.println("Аудиосистема пробуждена: динамики готовы передать звук.");
    }
    public void setVolume(int level) {
        volume = Math.max(0, Math.min(100, level));
        System.out.println("Громкость установлена: " + volume + "%.");
    }
    public void turnOff() {
        on = false;
        System.out.println("Аудиосистема утихла: динамики в спящем режиме.");
    }
    public boolean isOn() { return on; }
}

class Vid { // VideoProjector
    private boolean on = false;
    private String resolution = "HD";
    public void turnOn() {
        on = true;
        System.out.println("Проектор запущен: лампа прогрета, изображение готово.");
    }
    public void setResolution(String res) {
        resolution = res;
        System.out.println("Разрешение проектора: " + resolution + ".");
    }
    public void turnOff() {
        on = false;
        System.out.println("Проектор выключается: экран гаснет.");
    }
    public boolean isOn() { return on; }
}

class Lgt { // LightingSystem
    private boolean on = false;
    private int brightness = 100;
    public void turnOn() {
        on = true;
        System.out.println("Освещение включено: атмосфера подготовлена.");
    }
    public void setBrightness(int level) {
        brightness = Math.max(0, Math.min(100, level));
        System.out.println("Яркость света: " + brightness + "%.");
    }
    public void turnOff() {
        on = false;
        System.out.println("Освещение выключено: темнота и покой.");
    }
    public boolean isOn() { return on; }
}







class HTFacade { // HomeTheaterFacade
    private final Aud aud;
    private final Vid vid;
    private final Lgt lgt;

    public HTFacade(Aud aud, Vid vid, Lgt lgt) {
        this.aud = aud;
        this.vid = vid;
        this.lgt = lgt;
    }

    public void startMovie(String title) {
        System.out.println("\n   Подготовка к сеансу: \"" + title + "\" ---");
        if (!lgt.isOn()) lgt.turnOn();
        lgt.setBrightness(20);
        if (!aud.isOn()) aud.turnOn();
        aud.setVolume(30);
        if (!vid.isOn()) vid.turnOn();
        vid.setResolution("Full HD");
        System.out.println("Фильм запущен: приятного просмотра!");
    }

    public void endMovie() {
        System.out.println("\n   Завершение сеанса   ");
        if (vid.isOn()) vid.turnOff();
        if (aud.isOn()) aud.turnOff();
        if (lgt.isOn()) lgt.turnOff();
        System.out.println("Сеанс завершён. Устройства безопасно выключены.");
    }

    public void startMusicMode(String source) {
        System.out.println("\n    Музыкальный режим (источник: " + source + ") ---");
        if (!aud.isOn()) aud.turnOn();
        aud.setVolume(40);
        if (!lgt.isOn()) lgt.turnOn();
        lgt.setBrightness(50);
        if (vid.isOn()) vid.turnOff();
        System.out.println("Воспроизведение музыки началось.");
    }

    public void setVolume(int level) {
        System.out.println("\n   Фасад: установка громкости ");
        if (!aud.isOn()) aud.turnOn();
        aud.setVolume(level);
    }

    public void shutdownAll() {
        System.out.println("\n   Фасад: полное выключение всех подсистем ");
        if (vid.isOn()) vid.turnOff();
        if (aud.isOn()) aud.turnOff();
        if (lgt.isOn()) lgt.turnOff();
        System.out.println("Весь домашний кинотеатр выключен.");
    }
}








public class Lab1 {
    public static void main(String[] args) {
        Aud audio = new Aud();
        Vid projector = new Vid();
        Lgt lights = new Lgt();

        HTFacade home = new HTFacade(audio, projector, lights);

        home.startMovie("Шал");
        home.setVolume(35);
        home.startMusicMode("Play;ist");
        home.startMovie("Келинка Сабина");
        home.endMovie();
        home.shutdownAll();
    }
}
