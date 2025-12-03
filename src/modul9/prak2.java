import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

abstract class OrganizationComponent {
    protected final String name;

    public OrganizationComponent(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return name;
    }

    public abstract void display(int depth);

    public void add(OrganizationComponent comp) {
        throw new UnsupportedOperationException("add not supported");
    }
    public void remove(OrganizationComponent comp) {
        throw new UnsupportedOperationException("remove not supported");
    }

    public abstract double getBudget();
    public abstract int getEmployeeCount();
    public abstract Optional<Employee> findEmployeeByName(String employeeName);
    public abstract List<Employee> listAllEmployees();
}




class Employee extends OrganizationComponent {
    private String position;
    private double salary;
    private boolean isContractor;

    public Employee(String name, String position, double salary, boolean isContractor) {
        super(name);
        this.position = position;
        this.salary = Math.max(0.0, salary);
        this.isContractor = isContractor;
    }

    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public boolean isContractor() { return isContractor; }

    public void setPosition(String position) { this.position = position; }
    public void setSalary(double salary) { this.salary = Math.max(0.0, salary); }
    public void setContractor(boolean contractor) { this.isContractor = contractor; }

    @Override
    public void display(int depth) {
        String ind = " ".repeat(Math.max(0, depth));
        String type = isContractor ? "contractor" : "employee";
        System.out.printf("%s- %s (%s, %s) : %.2f%n", ind, name, position, type, salary);
    }

    @Override
    public double getBudget() {
        return isContractor ? 0.0 : salary;
    }

    @Override
    public int getEmployeeCount() {
        return isContractor ? 0 : 1;
    }

    @Override
    public Optional<Employee> findEmployeeByName(String employeeName) {
        if (this.name.equalsIgnoreCase(employeeName)) return Optional.of(this);
        return Optional.empty();
    }

    @Override
    public List<Employee> listAllEmployees() {
        List<Employee> list = new ArrayList<>();
        if (!isContractor) list.add(this); // включаем только штатных в общий список по умолчанию
        else list.add(this); // если нужно включать контракторов в список — включим их тоже
        return list;
    }
}




class Department extends OrganizationComponent {
    private final List<OrganizationComponent> children = new ArrayList<>();
    private String managerName;

    public Department(String name) {
        super(name);
    }

    public Department(String name, String managerName) {
        super(name);
        this.managerName = managerName;
    }

    public void setManagerName(String managerName) { this.managerName = managerName; }
    public String getManagerName() { return managerName; }

    @Override
    public void add(OrganizationComponent comp) {
        if (comp == null) return;
        if (children.contains(comp)) {
            System.out.println("В отделе '" + name + "' уже есть компонент: " + comp.getName());
            return;
        }
        children.add(comp);
    }

    @Override
    public void remove(OrganizationComponent comp) {
        children.remove(comp);
    }

    @Override
    public void display(int depth) {
        String ind = " ".repeat(Math.max(0, depth));
        System.out.printf("%s+ %s (department) — manager: %s, budget: %.2f, staff: %d%n",
                ind, name, managerName == null ? "-" : managerName, getBudget(), getEmployeeCount());
        for (OrganizationComponent c : children) {
            c.display(depth + 4);
        }
    }

    @Override
    public double getBudget() {
        double sum = 0.0;
        for (OrganizationComponent c : children) sum += c.getBudget();
        return sum;
    }

    @Override
    public int getEmployeeCount() {
        int cnt = 0;
        for (OrganizationComponent c : children) cnt += c.getEmployeeCount();
        return cnt;
    }

    @Override
    public Optional<Employee> findEmployeeByName(String employeeName) {
        for (OrganizationComponent c : children) {
            Optional<Employee> found = c.findEmployeeByName(employeeName);
            if (found.isPresent()) return found;
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> listAllEmployees() {
        List<Employee> result = new ArrayList<>();
        for (OrganizationComponent c : children) result.addAll(c.listAllEmployees());
        return result;
    }
}






public class prak2 {
    public static void main(String[] args) {
        Department corp = new Department("Acme Corp", "Генеральный директор");

        Department dev = new Department("Development", "Иван Иванов");
        Department backend = new Department("Backend Team", "Мария Петрова");
        Department frontend = new Department("Frontend Team", "Олег Сидоров");
        Department hr = new Department("HR", "Анна Смирнова");

        Employee e1 = new Employee("Алексей", "Senior Backend", 300000, false);
        Employee e2 = new Employee("Виктор", "Junior Backend", 120000, false);
        Employee e3 = new Employee("Светлана", "Frontend Lead", 250000, false);
        Employee e4 = new Employee("Дмитрий", "UI Designer", 150000, true); // contractor
        Employee e5 = new Employee("Елена", "HR Specialist", 100000, false);

        backend.add(e1);
        backend.add(e2);

        frontend.add(e3);
        frontend.add(e4);

        dev.add(backend);
        dev.add(frontend);

        hr.add(e5);

        corp.add(dev);
        corp.add(hr);

        // ещё отдельный отдел с сотрудником
        Department sales = new Department("Sales", "Надежда");
        sales.add(new Employee("Игорь", "Sales Manager", 200000, false));
        corp.add(sales);

        System.out.println("=== Структура компании ===");
        corp.display(0);

        System.out.println("\n=== Сводка ===");
        System.out.printf("Общий бюджет компании: %.2f%n", corp.getBudget());
        System.out.printf("Общий штат сотрудников (без контракторов): %d%n", corp.getEmployeeCount());

        System.out.println("\n=== Поиск сотрудника 'Светлана' ===");
        corp.findEmployeeByName("Светлана").ifPresentOrElse(
                emp -> System.out.printf("Найден: %s, должность: %s, зарплата: %.2f, contractor: %b%n",
                        emp.getName(), emp.getPosition(), emp.getSalary(), emp.isContractor()),
                () -> System.out.println("Сотрудник не найден.")
        );

        System.out.println("\n=== Список всех сотрудников в отделе Development ===");
        List<Employee> devStaff = dev.listAllEmployees();
        devStaff.forEach(emp -> System.out.printf("  %s — %s — %.2f (contractor: %b)%n",
                emp.getName(), emp.getPosition(), emp.getSalary(), emp.isContractor()));

        System.out.println("\n=== Меняем зарплату Виктору (Junior Backend) ===");
        corp.findEmployeeByName("Виктор").ifPresent(emp -> {
            System.out.printf("Старый оклад: %.2f%n", emp.getSalary());
            emp.setSalary(140000);
            System.out.printf("Новый оклад: %.2f%n", emp.getSalary());
        });

        System.out.println("\n=== Обновлённая сводка после изменения зарплаты ===");
        System.out.printf("Общий бюджет компании: %.2f%n", corp.getBudget());

        System.out.println("\n=== Поиск и отображение контракторов отдельно ===");
        List<Employee> all = corp.listAllEmployees();
        all.stream().filter(Employee::isContractor).forEach(emp ->
                System.out.printf("  contractor: %s — %s — %.2f%n", emp.getName(), emp.getPosition(), emp.getSalary())
        );

        System.out.println("\n=== Показать только штатных сотрудников (без контракторов) для HR ===");
        hr.listAllEmployees().stream().filter(e -> !e.isContractor()).forEach(e ->
                System.out.printf("  %s — %s — %.2f%n", e.getName(), e.getPosition(), e.getSalary())
        );
    }
}
