package modul9;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//  FileSystemComponent
interface FSComp {
    long getSize();
    void display(String ind);
    String getName();
}

// File
class F implements FSComp {
    private final String name;
    private final long size;

    public F(String name, long size) {
        this.name = name;
        this.size = Math.max(0, size);
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void display(String ind) {
        System.out.printf("%s- %s (файл, %d байт)%n", ind, name, size);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof F)) return false;
        F other = (F) o;
        return size == other.size && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size);
    }
}

//   Directory
class D implements FSComp {
    private final String name;
    private final List<FSComp> kids = new ArrayList<>();

    public D(String name) {
        this.name = name;
    }

    public boolean add(FSComp c) {
        if (c == null) {
            System.out.println("❗ Нельзя добавить пустой объект");
            return false;
        }
        if (kids.contains(c)) {
            System.out.println("⚠️ Уже есть: " + c.getName() + " в " + name);
            return false;
        }
        if (c instanceof D && ((D) c).containsAncestor(this)) {
            System.out.println("⛔ Нельзя вложить " + c.getName() + " в самого себя");
            return false;
        }
        kids.add(c);
        System.out.println("✅ Добавлено в " + name + ": " + c.getName());
        return true;
    }

    public boolean remove(FSComp c) {
        if (!kids.contains(c)) {
            System.out.println(" Не найдено: " + c.getName() + " в " + name);
            return false;
        }
        kids.remove(c);
        System.out.println("🗑️ Удалено из " + name + ": " + c.getName());
        return true;
    }

    @Override
    public long getSize() {
        long sum = 0;
        for (FSComp c : kids) sum += c.getSize();
        return sum;
    }

    @Override
    public void display(String ind) {
        System.out.printf("%s+ %s (папка, %d байт)%n", ind, name, getSize());
        String next = ind + "    ";
        for (FSComp c : kids) {
            c.display(next);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    private boolean containsAncestor(D possibleParent) {
        if (this == possibleParent) return true;
        for (FSComp c : kids) {
            if (c instanceof D && ((D) c).containsAncestor(possibleParent))
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof D)) return false;
        D other = (D) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}




public class Dz2 {
    public static void main(String[] args) {
        D root = new D("root");
        D src = new D("src");
        D res = new D("resources");
        D docs = new D("docs");

        F main = new F("Main.java", 2000);
        F util = new F("Utils.java", 1000);
        F logo = new F("logo.png", 15000);
        F readme = new F("README.md", 500);



        root.add(src);
        root.add(res);
        root.add(docs);
        root.add(readme);

        src.add(main);
        src.add(util);



        D img = new D("face");
        res.add(img);
        img.add(logo);
        img.add(new F("myphoto.svg", 4000));

        System.out.println("\n📂 Структура файловой системы:");
        root.display("");




        System.out.println("\n Размеры:");
        System.out.println("root: " + root.getSize());
        System.out.println("src: " + src.getSize());
        System.out.println("res: " + res.getSize());

        System.out.println("\n🗑️ Удаляем logo.png...");
        res.remove(logo);

        System.out.println("\n После удаления:");
        root.display("");
        System.out.println("\nroot total: " + root.getSize());
    }
}
