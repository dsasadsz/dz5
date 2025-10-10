//package modul4;
//import java.util.Scanner;
//
//
//interface Document {
//    void Open();
//}
//
//class Report implements Document {
//    @Override
//    public void Open() {
//        System.out.println("Открывается отчет.");
//    }
//}
//
//class Resume implements Document {
//    @Override
//    public void Open() {
//        System.out.println("Открывается резюме.");
//    }
//}
//
//class Letter implements Document {
//    @Override
//    public void Open() {
//        System.out.println("Открывается письмо.");
//    }
//}
//
//class Invoice implements Document {
//    @Override
//    public void Open() {
//        System.out.println("Открывается счет-фактура.");
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//abstract class DocumentCreator {
//    public abstract Document CreateDocument();
//}
//
//class ReportCreator extends DocumentCreator {
//    @Override
//    public Document CreateDocument() {
//        return new Report();
//    }
//}
//
//class ResumeCreator extends DocumentCreator {
//    @Override
//    public Document CreateDocument() {
//        return new Resume();
//    }
//}
//
//class LetterCreator extends DocumentCreator {
//    @Override
//    public Document CreateDocument() {
//        return new Letter();
//    }
//}
//
//class InvoiceCreator extends DocumentCreator {
//    @Override
//    public Document CreateDocument() {
//        return new Invoice();
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//public class Praktika {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("Выберите тип документа: report(1);   resume(2);    letter(3);     invoice(4)");
//        String c = sc.nextLine().toLowerCase();
//
//        DocumentCreator creator;
//
//        switch (c) {
//            case "report":
//                creator = new ReportCreator();
//                break;
//            case "resume":
//                creator = new ResumeCreator();
//                break;
//            case "letter":
//                creator = new LetterCreator();
//                break;
//            case "invoice":
//                creator = new InvoiceCreator();
//                break;
//            default:
//                System.out.println("Rror!");
//                return;
//        }
//
//        Document doc = creator.CreateDocument();
//        doc.Open();
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        DocumentCreator creator = new ReportCreator();
//        Document doc1 = creator.CreateDocument();
//        Document doc = creator.CreateDocument();
//        doc.Open();
//
//
//
////нет
//        Document report = new Report();
//        report.Open();
//
//        Document resume = new Resume();
//        resume.Open();
//
//        Document letter = new Letter();
//        letter.Open();
//
//
//
//
//
//
////без
//        Document doc1 = new Report();
//        Document doc2 = new Resume();
//
//
//
//        Document doc3 = new Invoice();
//
//        doc1.Open();
//        doc2.Open();
//        doc3.Open();
//
//
//
//
//
//
////с
//        DocumentCreator creator;
//
//        creator = new ReportCreator();
//        Document doc1 = creator.CreateDocument();
//
//        creator = new ResumeCreator();
//        Document doc2 = creator.CreateDocument();
//
//        creator = new InvoiceCreator();
//        Document doc3 = creator.CreateDocument();
//
//        doc1.Open();
//        doc2.Open();
//        doc3.Open();
//
//
//
//
//
//
//
//    }
//}
