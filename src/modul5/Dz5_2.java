package modul5;


class Report {
    private String header;
    private String content;
    private String footer;

    public void setHeader(String header) { this.header = header; }
    public void setContent(String content) { this.content = content; }
    public void setFooter(String footer) { this.footer = footer; }

    public String getHeader() { return header; }
    public String getContent() { return content; }
    public String getFooter() { return footer; }



    @Override
    public String toString() {
        return header + "\n" + content + "\n" + footer;
    }
}

interface IReportBuilder {
    void setHeader(String header);
    void setContent(String content);
    void setFooter(String footer);
    Report getReport();
}

class TextReportBuilder implements IReportBuilder {
    private Report report;

    public TextReportBuilder() {
        report = new Report();
    }
    @Override
    public void setHeader(String header) {
        report.setHeader("=== " + header + " ===");
    }
    @Override
    public void setContent(String content) {
        report.setContent(content);
    }
    @Override
    public void setFooter(String footer) {
        report.setFooter("--- " + footer + " ---");
    }
    @Override
    public Report getReport() {
        return report;
    }
}

class HtmlReportBuilder implements IReportBuilder {
    private Report report;

    public HtmlReportBuilder() {
        report = new Report();
    }


    @Override
    public void setHeader(String header) {
        report.setHeader("<h1>" + header + "</h1>");
    }
    @Override
    public void setContent(String content) {
        report.setContent("<p>" + content + "</p>");
    }
    @Override
    public void setFooter(String footer) {
        report.setFooter("<footer>" + footer + "</footer>");
    }
    @Override
    public Report getReport() {
        return report;
    }


}

class ReportDirector {
    public void constructReport(IReportBuilder builder) {
        builder.setHeader("Отчёт по продажам");
        builder.setContent("оприсаниек.");
        builder.setFooter("Конец отчёта");
    }
}

public class Dz5_2 {
    public static void main(String[] args) {
        ReportDirector director = new ReportDirector();

        TextReportBuilder textBuilder = new TextReportBuilder();
        director.constructReport(textBuilder);
        Report textReport = textBuilder.getReport();
        System.out.println("Текстовый отчёт:\n" + textReport);
        System.out.println();




        HtmlReportBuilder htmlBuilder = new HtmlReportBuilder();
        director.constructReport(htmlBuilder);
        Report htmlReport = htmlBuilder.getReport();
        System.out.println("HTML отчёт:\n" + htmlReport);
    }
}
