package modul6;

import java.util.Scanner;

interface IShippingStrategy {
    double calculateShippingCost(double weightKg, double distanceKm);
}




class StandardShippingStrategy implements IShippingStrategy {
    @Override
    public double calculateShippingCost(double weightKg, double distanceKm) {
        validate(weightKg, distanceKm);
        return weightKg * 0.5 + distanceKm * 0.1;
    }

    private void validate(double w, double d) {
        if (w < 0 || d < 0) throw new IllegalArgumentException("Вес и расстояние должны быть >= 0");
    }
}


class ExpressShippingStrategy implements IShippingStrategy {
    @Override
    public double calculateShippingCost(double weightKg, double distanceKm) {
        validate(weightKg, distanceKm);
        return (weightKg * 0.75 + distanceKm * 0.2) + 10.0;
    }

    private void validate(double w, double d) {
        if (w < 0 || d < 0) throw new IllegalArgumentException("Вес и расстояние должны быть >= 0");
    }
}

class InternationalShippingStrategy implements IShippingStrategy {
    @Override
    public double calculateShippingCost(double weightKg, double distanceKm) {
        validate(weightKg, distanceKm);
        return weightKg * 1.0 + distanceKm * 0.5 + 15.0;
    }

    private void validate(double w, double d) {
        if (w < 0 || d < 0) throw new IllegalArgumentException("Вес и расстояние должны быть >= 0");
    }
}

class NightShippingStrategy implements IShippingStrategy {
    private final double extraFee;
    public NightShippingStrategy(double extraFee) { this.extraFee = extraFee; }
    @Override
    public double calculateShippingCost(double weightKg, double distanceKm) {
        validate(weightKg, distanceKm);
        double base = weightKg * 0.6 + distanceKm * 0.15;
        return base + extraFee;
    }
    private void validate(double w, double d) {
        if (w < 0 || d < 0) throw new IllegalArgumentException("Вес и расстояние должны быть >= 0");
    }
}

class DeliveryContext {
    private IShippingStrategy strategy;
    public void setShippingStrategy(IShippingStrategy strategy) { this.strategy = strategy; }
    public double calculateCost(double weightKg, double distanceKm) {
        if (strategy == null) throw new IllegalStateException("Стратегия доставки не установлена.");
        return strategy.calculateShippingCost(weightKg, distanceKm);
    }
}







public class Strategy {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DeliveryContext ctx = new DeliveryContext();

        System.out.println("Выберите тип доставки: 1-Стандартная 2-Экспресс 3-Международная 4-Ночная");
        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1": ctx.setShippingStrategy(new StandardShippingStrategy()); break;
            case "2": ctx.setShippingStrategy(new ExpressShippingStrategy()); break;
            case "3": ctx.setShippingStrategy(new InternationalShippingStrategy()); break;
            case "4": ctx.setShippingStrategy(new NightShippingStrategy(20.0)); break;
            default:
                System.out.println("Неверный выбор."); sc.close(); return;
        }

        try {
            System.out.print("Введите вес посылки (кг): ");
            double weight = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Введите расстояние (км): ");
            double distance = Double.parseDouble(sc.nextLine().trim());

            if (weight < 0 || distance < 0) {
                System.out.println("Ошибка: отрицательное значение");
            } else {
                double cost = ctx.calculateCost(weight, distance);
                System.out.printf("Стоимость доставки: %.2f%n", cost);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Ошибка: введите числовые значения.");
        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        } finally {
            sc.close();
        }
    }
}




//егер блмаса
//if (choice.equals("1")) { // стандартная доставка
//cost = weight * 0.5 + distance * 0.1;
//        }
//        else if (choice.equals("2")) { // экспресс
//cost = (weight * 0.75 + distance * 0.2) + 10.0;
//        }
//        else if (choice.equals("3")) { // международная
//cost = weight * 1.0 + distance * 0.5 + 15.0;
//        }
//        else if (choice.equals("4")) { // ночная
//cost = (weight * 0.6 + distance * 0.15) + 20.0;
//        }
//        else {
//        System.out.println("Неверный выбор!");
//            return;
//                    }
//