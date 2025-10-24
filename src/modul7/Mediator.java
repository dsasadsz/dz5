package modul7;
import java.util.*;

interface IMed {
    void register(U u);
    void leave(U u);
    void send(String msg, U from);
    void sendPrivate(String msg, U from, String toName);
}

class Room implements IMed {
    private final Map<String, U> parts = new LinkedHashMap<>();

    public void register(U u) {
        if (u == null || u.name == null) return;
        parts.put(u.name, u);
        u.setMed(this);
        broadcastNotif(u.name + " присоединился к чату", u.name);
    }

    public void leave(U u) {
        if (u == null || u.name == null) return;
        if (parts.remove(u.name) != null) {
            u.setMed(null);
            broadcastNotif(u.name + " покинул чат", u.name);
        }
    }

    public void send(String msg, U from) {
        if (!isIn(from)) { System.out.println("Ошибка: Пользователь не в чате -> " + nameOf(from)); return; }
        for (U u : parts.values()) {
            if (!u.name.equals(from.name)) u.receive(from.name, msg);
        }
    }

    public void sendPrivate(String msg, U from, String toName) {
        if (!isIn(from)) { System.out.println("Ошибка: Пользователь не в чате -> " + nameOf(from)); return; }
        U to = parts.get(toName);
        if (to == null) { System.out.println("Ошибка: Получатель не найден -> " + toName); return; }
        to.receivePrivate(from.name, msg);
    }

    private boolean isIn(U u) { return u != null && u.name != null && parts.containsKey(u.name); }
    private String nameOf(U u) { return (u==null||u.name==null)?"<unknown>":u.name; }

    private void broadcastNotif(String note, String except) {
        for (U u : parts.values()) if (!u.name.equals(except)) u.notify(note);
    }
}

class U {
    protected String name;
    protected IMed med;
    U(String n){ name = n; }
    void setMed(IMed m){ med = m; }
    void join(IMed m){ m.register(this); }
    void leave(){ if (med!=null) med.leave(this); }
    void send(String msg){ if (med!=null) med.send(msg,this); else System.out.println("Ошибка: не в чате -> " + name); }
    void sendTo(String msg, String to){ if (med!=null) med.sendPrivate(msg,this,to); else System.out.println("Ошибка: не в чате -> " + name); }
    void receive(String from, String msg){ System.out.println(name + " <- " + from + ": " + msg); }
    void receivePrivate(String from, String msg){ System.out.println(name + " [личное] <- " + from + ": " + msg); }
    void notify(String note){ System.out.println(name + " [уведомление]: " + note); }
}

class Admin extends U {
    Admin(String n){ super(n); }
    @Override
    void receive(String from, String msg){ System.out.println("[ADM] " + name + " <- " + from + ": " + msg); }
}

public class Mediator {
    public static void main(String[] args){
        Room r = new Room();

        U a = new U("Alice");
        U b = new U("Bob");
        U c = new U("Charlie");
        Admin adm = new Admin("Zed");

        a.join(r);
        b.join(r);

        a.send("Привет всем");
        b.sendTo("Привет, Alice!", "Alice");

        U guest = new U("Guest");
        guest.send("Я тут!");

        c.join(r);
        b.leave();
        b.send("Это сообщение от вышедшего");

        adm.join(r);
        adm.send("Я админ, всем привет");

        r.sendPrivate("Личное админу", a, "Zed"); // ✅ исправлено

        a.sendTo("Личное Zed", "Zed");
        r.sendPrivate("Кому-то", a, "NoOne");
    }
}

