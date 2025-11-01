package modul8;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


interface IReport {
    ReportData generateData();
    default String generate() {
        return generateData().toPrettyString();
    }
}

class ReportData {
    private final List<String> headers;
    private final List<List<String>> rows;

    public ReportData(List<String> headers, List<List<String>> rows) {
        this.headers = new ArrayList<>(headers);
        this.rows = new ArrayList<>(rows);
    }

    public List<String> getHeaders() { return headers; }
    public List<List<String>> getRows() { return rows; }

    public ReportData copy() {
        List<List<String>> rowsCopy = rows.stream()
                .map(ArrayList::new)
                .collect(Collectors.toList());
        return new ReportData(new ArrayList<>(headers), rowsCopy);
    }

    public String toPrettyString() {
        int cols = headers.size();
        int[] widths = new int[cols];
        for (int i = 0; i < cols; i++) widths[i] = headers.get(i).length();
        for (List<String> row : rows) {
            for (int i = 0; i < cols; i++) {
                String c = i < row.size() ? row.get(i) : "";
                widths[i] = Math.max(widths[i], c.length());
            }
        }
        StringBuilder sb = new StringBuilder();
        // header
        for (int i = 0; i < cols; i++) {
            sb.append(pad(headers.get(i), widths[i])).append(i == cols - 1 ? "\n" : " | ");
        }
        // separator
        for (int i = 0; i < cols; i++) {
            sb.append(repeat("-", widths[i])).append(i == cols - 1 ? "\n" : "-+-");
        }
        // rows
        for (List<String> row : rows) {
            for (int i = 0; i < cols; i++) {
                String c = i < row.size() ? row.get(i) : "";
                sb.append(pad(c, widths[i])).append(i == cols - 1 ? "\n" : " | ");
            }
        }
        return sb.toString();
    }

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append(headers.stream().map(ReportData::escapeCsv).collect(Collectors.joining(","))).append("\n");
        for (List<String> row : rows) {
            sb.append(row.stream().map(ReportData::escapeCsv).collect(Collectors.joining(","))).append("\n");
        }
        return sb.toString();
    }

    private static String escapeCsv(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private static String pad(String s, int w) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < w) sb.append(' ');
        return sb.toString();
    }

    private static String repeat(String s, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) sb.append(s);
        return sb.toString();
    }
}

class SalesReport implements IReport {
    private static final DateTimeFormatter DF = DateTimeFormatter.ISO_DATE;

    @Override
    public ReportData generateData() {
        List<String> headers = Arrays.asList("id", "date", "amount", "customer");
        List<List<String>> rows = new ArrayList<>();

        // фиктивные данные продаж
        rows.add(row(1, LocalDate.now().minusDays(5), 1200.50, "Alice"));
        rows.add(row(2, LocalDate.now().minusDays(3), 2500.00, "Bob"));
        rows.add(row(3, LocalDate.now().minusDays(2), 75.25, "Charlie"));
        rows.add(row(4, LocalDate.now().minusDays(1), 560.00, "Diana"));
        rows.add(row(5, LocalDate.now(), 1999.99, "Eve"));
        return new ReportData(headers, rows);
    }

    private List<String> row(int id, LocalDate date, double amount, String customer) {
        return Arrays.asList(
                String.valueOf(id),
                date.format(DF),
                String.format(Locale.US, "%.2f", amount),
                customer
        );
    }
}

class UserReport implements IReport {
    @Override
    public ReportData generateData() {
        List<String> headers = Arrays.asList("userId", "name", "email", "registered");
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("1", "Alice", "alice@example.com", "2023-02-10"));
        rows.add(Arrays.asList("2", "Bob", "bob@example.com", "2022-11-04"));
        rows.add(Arrays.asList("3", "Charlie", "charlie@example.com", "2024-01-15"));
        rows.add(Arrays.asList("4", "Diana", "diana@example.com", "2021-08-20"));
        return new ReportData(headers, rows);
    }
}


//хранит ссылку
abstract class ReportDecorator implements IReport {
    protected final IReport inner;
    public ReportDecorator(IReport inner) { this.inner = inner; }
    @Override
    public ReportData generateData() { return inner.generateData(); }
}

class DateFilterDecorator extends ReportDecorator {
    private final LocalDate from;
    private final LocalDate to;
    private final String dateColumnName;

    public DateFilterDecorator(IReport inner, LocalDate from, LocalDate to, String dateColumnName) {
        super(inner);
        this.from = from;
        this.to = to;
        this.dateColumnName = dateColumnName;
    }

    @Override
    public ReportData generateData() {
        ReportData base = inner.generateData().copy();
        int idx = base.getHeaders().indexOf(dateColumnName);
        if (idx == -1) return base;
        List<List<String>> filtered = base.getRows().stream()
                .filter(row -> {
                    if (idx >= row.size()) return false;
                    try {
                        LocalDate d = LocalDate.parse(row.get(idx));
                        if (from != null && d.isBefore(from)) return false;
                        if (to != null && d.isAfter(to)) return false;
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
        return new ReportData(base.getHeaders(), filtered);
    }
}

class MinAmountFilterDecorator extends ReportDecorator {
    private final double minAmount;
    private final String amountColumnName;

    public MinAmountFilterDecorator(IReport inner, double minAmount, String amountColumnName) {
        super(inner);
        this.minAmount = minAmount;
        this.amountColumnName = amountColumnName;
    }

    @Override
    public ReportData generateData() {
        ReportData base = inner.generateData().copy();
        int idx = base.getHeaders().indexOf(amountColumnName);
        if (idx == -1) return base;
        List<List<String>> filtered = base.getRows().stream()
                .filter(row -> {
                    if (idx >= row.size()) return false;
                    try {
                        double v = Double.parseDouble(row.get(idx));
                        return v >= minAmount;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
        return new ReportData(base.getHeaders(), filtered);
    }
}

class SortingDecorator extends ReportDecorator {
    private final String columnName;
    private final boolean ascending;

    public SortingDecorator(IReport inner, String columnName, boolean ascending) {
        super(inner);
        this.columnName = columnName;
        this.ascending = ascending;
    }

    @Override
    public ReportData generateData() {
        ReportData base = inner.generateData().copy();
        int idx = base.getHeaders().indexOf(columnName);
        if (idx == -1) return base;
        List<List<String>> rows = new ArrayList<>(base.getRows());
        rows.sort((a, b) -> {
            String va = idx < a.size() ? a.get(idx) : "";
            String vb = idx < b.size() ? b.get(idx) : "";
            // попытка сравнить как число, затем как дату, иначе как строка
            Double na = parseDoubleSafe(va);
            Double nb = parseDoubleSafe(vb);
            if (na != null && nb != null) return ascending ? na.compareTo(nb) : nb.compareTo(na);
            LocalDate da = parseDateSafe(va);
            LocalDate db = parseDateSafe(vb);
            if (da != null && db != null) return ascending ? da.compareTo(db) : db.compareTo(da);
            return ascending ? va.compareToIgnoreCase(vb) : vb.compareToIgnoreCase(va);
        });
        return new ReportData(base.getHeaders(), rows);
    }

    private static Double parseDoubleSafe(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return null; }
    }
    private static LocalDate parseDateSafe(String s) {
        try { return LocalDate.parse(s); } catch (Exception e) { return null; }
    }
}

//Экспорт
class CsvExportDecorator extends ReportDecorator {
    public CsvExportDecorator(IReport inner) { super(inner); }

    @Override
    public ReportData generateData() { return inner.generateData(); }

    @Override
    public String generate() {
        return inner.generateData().toCsv();
    }
}

class PdfExportDecorator extends ReportDecorator {
    public PdfExportDecorator(IReport inner) { super(inner); }

    @Override
    public ReportData generateData() { return inner.generateData(); }

    @Override
    public String generate() {
        // Здесь — имитация PDF-экспорта: в реальном приложении нужно использовать библиотеку iText/Apache PDFBox и т.д.
        String content = inner.generate();
        String header = "=== PDF REPORT (simulated) ===\n";
        String footer = "\n=== END PDF ===";
        return header + content + footer;
    }
}





public class Prak1 {
    public static void main(String[] args) {
        System.out.println("=== Sales report, filtered by date, sorted by amount desc, exported CSV ===");
        IReport sales = new SalesReport();
        LocalDate from = LocalDate.now().minusDays(3);
        LocalDate to = LocalDate.now();
        IReport decorated = new DateFilterDecorator(sales, from, to, "date");
        decorated = new SortingDecorator(decorated, "amount", false); // сортировать по amount desc
        decorated = new CsvExportDecorator(decorated);
        System.out.println(decorated.generate());

        System.out.println("\n=== Sales report, min amount 500, pretty printed ===");
        IReport sales2 = new SalesReport();
        sales2 = new MinAmountFilterDecorator(sales2, 500.0, "amount");
        sales2 = new SortingDecorator(sales2, "date", true);
        System.out.println(sales2.generate());

        System.out.println("\n=== User report, PDF simulated ===");
        IReport users = new UserReport();
        IReport usersPdf = new PdfExportDecorator(users);
        System.out.println(usersPdf.generate());

        System.out.println("\n=== Dynamic example: build chain based on 'user request' ===");
        Map<String, String> userRequest = new HashMap<>();
        userRequest.put("report", "sales");
        userRequest.put("from", LocalDate.now().minusDays(4).toString());
        userRequest.put("to", LocalDate.now().toString());
        userRequest.put("minAmount", "1000");
        userRequest.put("sort", "amount");
        userRequest.put("export", "pdf");

        IReport chosen = "sales".equalsIgnoreCase(userRequest.get("report")) ? new SalesReport() : new UserReport();
        // apply date filter
        LocalDate f = LocalDate.parse(userRequest.get("from"));
        LocalDate t = LocalDate.parse(userRequest.get("to"));
        chosen = new DateFilterDecorator(chosen, f, t, "date");
        // apply min amount
        chosen = new MinAmountFilterDecorator(chosen, Double.parseDouble(userRequest.get("minAmount")), "amount");
        // sorting
        chosen = new SortingDecorator(chosen, userRequest.get("sort"), false);
        // export
        if ("csv".equalsIgnoreCase(userRequest.get("export"))) chosen = new CsvExportDecorator(chosen);
        else if ("pdf".equalsIgnoreCase(userRequest.get("export"))) chosen = new PdfExportDecorator(chosen);

        System.out.println(chosen.generate());
    }
}
