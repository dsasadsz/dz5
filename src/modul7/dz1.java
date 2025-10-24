package modul7;
import java.util.*;

interface Cmd {
    void exec();
    void undo();
}

class Light {
    void on() { System.out.println("Свет включен"); }
    void off() { System.out.println("Свет выключен"); }
}

class Door {
    void open() { System.out.println("Дверь открыта"); }
    void close() { System.out.println("Дверь закрыта"); }
}

class Thermo {
    int t = 22;
    void up() { t++; System.out.println("Температура: " + t); }
    void down() { t--; System.out.println("Температура: " + t); }
}

class TV {
    boolean on = false;
    void power() {
        on = !on;
        System.out.println(on ? "Телевизор включен" : "Телевизор выключен");
    }
}

class CLightOn implements Cmd {
    Light l;
    CLightOn(Light l){ this.l = l; }
    public void exec(){ l.on(); }
    public void undo(){ l.off(); }
}

class CLightOff implements Cmd {
    Light l;
    CLightOff(Light l){ this.l = l; }
    public void exec(){ l.off(); }
    public void undo(){ l.on(); }
}

class CDoorOpen implements Cmd {
    Door d;
    CDoorOpen(Door d){ this.d = d; }
    public void exec(){ d.open(); }
    public void undo(){ d.close(); }
}

class CDoorClose implements Cmd {
    Door d;
    CDoorClose(Door d){ this.d = d; }
    public void exec(){ d.close(); }
    public void undo(){ d.open(); }
}

class CTempUp implements Cmd {
    Thermo th;
    CTempUp(Thermo th){ this.th = th; }
    public void exec(){ th.up(); }
    public void undo(){ th.down(); }
}

class CTempDown implements Cmd {
    Thermo th;
    CTempDown(Thermo th){ this.th = th; }
    public void exec(){ th.down(); }
    public void undo(){ th.up(); }
}

class CTVPower implements Cmd {
    TV tv;
    CTVPower(TV tv){ this.tv = tv; }
    public void exec(){ tv.power(); }
    public void undo(){ tv.power(); }
}

class Hub {
    Stack<Cmd> hist = new Stack<>();
    void run(Cmd c){ c.exec(); hist.push(c); }
    void undo(){
        if(hist.isEmpty()) System.out.println("Нет команд для отмены");
        else hist.pop().undo();
    }
}

public class dz1 {
    public static void main(String[] a){
        Light l = new Light();
        Door d = new Door();
        Thermo t = new Thermo();
        TV tv = new TV();
        Hub h = new Hub();

        h.run(new CLightOn(l));
        h.run(new CDoorOpen(d));
        h.run(new CTempUp(t));
        h.run(new CTVPower(tv));
        h.undo();
        h.undo();
        h.undo();
        h.undo();
        h.undo();
    }
}

