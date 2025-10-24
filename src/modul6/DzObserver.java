package modul6;
import java.util.*;

interface Obs {
    void upd(double rate);
    String getN();
}

interface Subj {
    void add(Obs o);
    void rm(Obs o);
    void notifyAllObs();
}



// субъект
class ObmenVal implements Subj {       //обмен валют
    private final List<Obs> obs = new ArrayList<>();
    private double usd;

    @Override
    public void add(Obs o) { if (!obs.contains(o)) obs.add(o); }
    @Override
    public void rm(Obs o) { obs.remove(o); }

    @Override
    public void notifyAllObs() {
        for (Obs o : obs) o.upd(usd);
    }

    public void setRate(double r) {
        if (r <= 0) { System.out.println("Неверный курс."); return; }
        usd = r;
        System.out.println("➡ Курс USD обновлён: " + usd + "₸");
        notifyAllObs();
    }
}





class Disp implements Obs {
    private final String n;
    public Disp(String n) {
        this.n = n;

    }
    public void upd(double r)
    {
        System.out.println(n + ": новый курс $" + r);
    }
    public String getN() {
        return n;
    }
}

class Trade implements Obs {
    private final String n;
    public Trade(String n) { this.n = n; }
    public void upd(double r) {
        if (r > 500) System.out.println(n + ": продаю доллар по " + r);
        else System.out.println(n + ": покупаю доллар по " + r);
    }
    public String getN() { return n; }
}

class Sms implements Obs {
    private final String n;
    public Sms(String n) { this.n = n; }
    public void upd(double r) { System.out.println(n + ": SMS → курс $" + r + "₸"); }
    public String getN() { return n; }
}







public class DzObserver {
    public static void main(String[] args) {
        ObmenVal ex = new ObmenVal();
        Obs d1 = new Disp("📊 Табло 📊");
        Obs t1 = new Trade("💼 Трейдер 💼");
        Obs s1 = new Sms("📱 SMS 📱");

        ex.add(d1); ex.add(t1); ex.add(s1);

        ex.setRate(470);
        System.out.println();

        ex.setRate(520);


        ex.rm(d1);
        System.out.println();

        ex.setRate(480);
    }
}
