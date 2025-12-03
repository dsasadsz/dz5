package modul9;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



// Comp — Component
abstract class Comp { // FileSystemComponent
    protected final String name;
    public Comp(String name) { this.name = Objects.requireNonNull(name); }
    public String getName() { return name; }

    public abstract void display(int depth);

    public void add(Comp c) { throw new UnsupportedOperationException("add not supported"); }
    public void remove(Comp c) { throw new UnsupportedOperationException("remove not supported"); }
    public Comp getChild(int i) { throw new UnsupportedOperationException("getChild not supported"); }
}

// File
class F extends Comp { // File
    private final long size; // байты

    public F(String name, long size) {
        super(name);
        this.size = Math.max(0, size);
    }

    public long getSize() { return size; }

    @Override
    public void display(int depth) {
        String ind = " ".repeat(Math.max(0, depth));
        System.out.printf("%s- %s (file, %d bytes)%n", ind, name, size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof F)) return false;
        F other = (F) o;
        return size == other.size && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size);
    }
}





// Directory
class Dir extends Comp {
    private final List<Comp> kids = new ArrayList<>();

    public Dir(String name) { super(name); }

    @Override
    public void add(Comp c) {
        if (c == null) {
            System.out.println("Нельзя добавить null в директорию " + name);
            return;
        }
        if (containsAncestor(c)) {
            System.out.println("Запрещено добавлять родителя/предка внутрь потомка: " + c.getName());
            return;
        }
        if (kids.contains(c)) {
            System.out.println("В директории '" + name + "' уже есть компонент: " + c.getName());
            return;
        }
        kids.add(c);
        System.out.println("Добавлено в " + name + ": " + c.getName());
    }

    @Override
    public void remove(Comp c) {
        if (c == null) return;
        if (kids.remove(c)) {
            System.out.println("Удалено из " + name + ": " + c.getName());
        } else {
            System.out.println("Элемент не найден в " + name + ": " + c.getName());
        }
    }

    @Override
    public Comp getChild(int i) {
        return kids.get(i);
    }

    public long getSize() {
        long sum = 0;
        for (Comp c : kids) {
            if (c instanceof F) sum += ((F) c).getSize();
            else if (c instanceof Dir) sum += ((Dir) c).getSize();
        }
        return sum;
    }

    @Override
    public void display(int depth)  {   //depth - пробел
        String ind = " ".repeat(Math.max(0, depth));
        System.out.printf("%s+ %s (dir) — total: %d bytes%n", ind, name, getSize());
        String next = ind + "    ";
        for (Comp c : kids) c.display(depth + 4);  //глубина жасау же толтыру
    }

    // проверка
    private boolean containsAncestor(Comp candidate) {
        if (!(candidate instanceof Dir)) return false;
        Dir d = (Dir) candidate;
        if (d == this) return true;
        for (Comp child : d.kids) {
            if (child instanceof Dir && ((Dir) child).containsAncestor(this)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dir)) return false;
        Dir other = (Dir) o;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}






public class Lab2 {
    public static void main(String[] args) {
        Dir root = new Dir("root");
        Dir proj = new Dir("project");
        Dir conf = new Dir("config");
        Dir assets = new Dir("assets");

        F readme = new F("README.md", 512);
        F main = new F("Main.java", 4096);
        F utils = new F("Utils.java", 2048);
        F logo = new F("logo.png", 16384);
        F style = new F("style.css", 1024);

        root.add(proj);
        root.add(new F("license.txt", 1024));
        root.add(readme);

        proj.add(main);
        proj.add(utils);
        proj.add(conf);

        conf.add(new F("app.properties", 256));
        conf.add(new F("db.conf", 128));

        proj.add(assets);
        assets.add(logo);
        assets.add(style);
        assets.add(new Dir("icons"));          // пустойй

        System.out.println("\n   Файловая иерархия ");
        root.display(0);

        System.out.println("\n  Размеры");
        System.out.printf("root total: %d bytes%n", root.getSize());
        System.out.printf("project total: %d bytes%n", proj.getSize());
        System.out.printf("assets total: %d bytes%n", assets.getSize());

        System.out.println("\n Операции: удаление и проба дубликата ");
        assets.remove(style);                   // удалить style.css
        proj.add(utils);                        // попытка добавить duplicate utils

        System.out.println("\n   Обновлённая иерархия ");
        root.display(0);
        System.out.printf("%nroot total (updated): %d bytes%n", root.getSize());
    }
}
