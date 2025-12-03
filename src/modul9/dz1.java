//package modul9;
//
//class TV { // Television
//    private boolean on = false;
//    private String channel = "1";
//    public void on() { on = true; System.out.println("Телевизор просыпается. Экран ожил."); }
//    public void off() { on = false; System.out.println("Телевизор уходит в спящий режим."); }
//    public void setChannel(String ch) { channel = ch; System.out.println("На экране канал №" + channel); }
//    public boolean isOn() { return on; }
//}
//
//class Aud { // AudioSystem
//    private boolean on = false;
//    private int vol = 20;
//    public void on() { on = true; System.out.println("Аудиосистема прогревается и готова к работе."); }
//    public void off() { on = false; System.out.println("Аудиосистема выключена, тишина в комнате."); }
//    public void setVol(int v) {
//        vol = Math.max(0, Math.min(100, v));
//        System.out.println("Громкость установлена на " + vol + "% мощности.");
//    }
//    public void mute() { vol = 0; System.out.println("Аудио выключено. Полная тишина."); }
//    public boolean isOn() { return on; }
//}
//
//class DVD { // DVDPlayer
//    private boolean on = false;
//    private boolean playing = false;
//    public void on() { on = true; System.out.println("DVD-проигрыватель включён. Диск готов."); }
//    public void off() { playing = false; on = false; System.out.println("DVD-проигрыватель завершил работу."); }
//    public void play(String title) {
//        if (!on) on();
//        playing = true;
//        System.out.println("Начинается фильм: \"" + title + "\". Приятного просмотра.");
//    }
//    public void pause() { if (playing) { playing = false; System.out.println("Пауза. Можно налить чай."); } }
//    public void stop() { if (playing) { playing = false; System.out.println("Фильм остановлен."); } }
//    public boolean isOn() { return on; }
//}
//
//class GC { // GameConsole
//    private boolean on = false;
//    public void on() { on = true; System.out.println("Игровая консоль загружается. Подготовка к запуску."); }
//    public void off() { on = false; System.out.println("Консоль завершила сеанс."); }
//    public void launch(String game) {
//        if (!on) on();
//        System.out.println("Запускается игра: \"" + game + "\". Удачи в прохождении!");
//    }
//    public boolean isOn() { return on; }
//}
//
//
//
//
//
//
//// Facade — HomeTheaterFacade
//class HTF {
//    private final TV tv;
//    private final Aud aud;
//    private final DVD dvd;
//    private final GC gc;
//
//    public HTF(TV tv, Aud aud, DVD dvd, GC gc) {
//        this.tv = tv; this.aud = aud; this.dvd = dvd; this.gc = gc;
//    }
//
//    public void watchMovie(String title, String channel) {
//        System.out.println("\n Режим: Киносеанс");
//        if (!tv.isOn()) tv.on();
//        tv.setChannel(channel);
//        if (!aud.isOn()) aud.on();
//        aud.setVol(30);
//        if (!dvd.isOn()) dvd.on();
//        dvd.play(title);
//        if (gc.isOn()) gc.off();
//    }
//
//    public void stopMovie() {
//        System.out.println("\n Завершение киносеанса");
//        dvd.stop();
//        dvd.off();
//        if (aud.isOn()) aud.mute();
//        if (tv.isOn()) tv.off();
//    }
//
//    public void playGame(String game) {
//        System.out.println("\n=== Режим: Игра ===");
//        if (!tv.isOn()) tv.on();
//        tv.setChannel("HDMI-Game");
//        if (!aud.isOn()) aud.on();
//        aud.setVol(45);
//        if (!gc.isOn()) gc.on();
//        gc.launch(game);
//        if (dvd.isOn()) { dvd.stop(); dvd.off(); }
//    }
//
//    public void listenMusic(String source) {
//        System.out.println("\n=== Режим: Музыка ===");
//        if (!aud.isOn()) aud.on();
//        aud.setVol(25);
//        System.out.println("Источник музыки: " + source);
//        if (!tv.isOn()) tv.on();
//        tv.setChannel("AUDIO-OUT");
//        if (dvd.isOn()) dvd.off();
//        if (gc.isOn()) gc.off();
//    }
//
//    public void setVolume(int v) {
//        System.out.println("\n  Настройка громкости ");
//        if (!aud.isOn()) aud.on();
//        aud.setVol(v);
//    }
//
//    public void shutdownAll() {
//        System.out.println("\n  Завершение работы всех устройств ");
//        if (dvd.isOn()) { dvd.stop(); dvd.off(); }
//        if (gc.isOn()) gc.off();
//        if (aud.isOn()) aud.off();
//        if (tv.isOn()) tv.off();
//        System.out.println("Все системы безопасно выключены. Конец сеанса.");
//    }
//}
//
//
//
//
//
//
//
//public class dz1 {
//    public static void main(String[] args) {
//        TV tv = new TV();       // Television
//        Aud aud = new Aud();    // AudioSystem
//        DVD dvd = new DVD();    // DVDPlayer
//        GC gc = new GC();       // GameConsole
//
//        HTF htf = new HTF(tv, aud, dvd, gc); // HomeTheaterFacade
//
//        htf.watchMovie("Шал", "5");
//        htf.setVolume(35);
//        htf.listenMusic("Spotify Premium");
//        htf.playGame("Atomic Heart");
//        htf.stopMovie();
//        htf.shutdownAll();
//    }
//}
