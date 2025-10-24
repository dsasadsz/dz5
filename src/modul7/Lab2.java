//package modul7;
//
//abstract class Beverage {
//    public final void prepareRecipe() {
//        boilWater();
//        brew();
//        pourInCup();
//        addCondiments();
//    }
//
//    private void boilWater() {
//        System.out.println("Кипячение воды...");
//    }
//
//    private void pourInCup() {
//        System.out.println("Наливание в чашку...");
//    }
//
//    protected abstract void brew();
//    protected abstract void addCondiments();
//}
//
//class Tea extends Beverage {
//    protected void brew() {
//        System.out.println("Заваривание чая...");
//    }
//
//    protected void addCondiments() {
//        System.out.println("Добавление лимона...");
//    }
//}
//
//class Coffee extends Beverage {
//    protected void brew() {
//        System.out.println("Заваривание кофе...");
//    }
//
//    protected void addCondiments() {
//        System.out.println("Добавление сахара и молока...");
//    }
//}
//
//class HotChocolate extends Beverage {
//    protected void brew() {
//        System.out.println("Приготовление какао...");
//    }
//
//    protected void addCondiments() {
//        System.out.println("Добавление маршмеллоу...");
//    }
//}
//
//public class Lab2 {
//    public static void main(String[] args) {
//        Beverage tea = new Tea();
//        System.out.println("Приготовление чая:");
//        tea.prepareRecipe();
//
//        System.out.println();
//
//        Beverage coffee = new Coffee();
//        System.out.println("Приготовление кофе:");
//        coffee.prepareRecipe();
//
//        System.out.println();
//
//        Beverage chocolate = new HotChocolate();
//        System.out.println("Приготовление горячего шоколада:");
//        chocolate.prepareRecipe();
//    }
//}
