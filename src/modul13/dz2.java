package modul13;





// ITicketVendingMachineState
 interface IState {
    void selT(TVM machine); // SelectTicket
    void insM(TVM machine, double amount); // InsertMoney
    void disT(TVM machine); // DispenseTicket
    void canT(TVM machine); // CancelTransaction
}

//  TicketVendingMachine
 class TVM {
    // CurrentState
    private IState cS;
    // CurrentBalance
    private double cB = 0.0;
    // TicketPrice
    private final double tP = 5.00;

    // Ссылки на конкретные состояния
    public final IdleState Idle;
    public final WaitMState WaitM; // WaitingForMoneyState
    public final MoneyRState MoneyR; // MoneyReceivedState
    public final DispTState DispT; // TicketDispensedState
    public final CanTState CanT; // TransactionCanceledState

    public TVM() {
        // Инициализация состояний
        Idle = new IdleState(this);
        WaitM = new WaitMState(this);
        MoneyR = new MoneyRState(this);
        DispT = new DispTState(this);
        CanT = new CanTState(this);

        // Установка начального состояния
        cS = Idle;
        System.out.println("Автомат инициализирован. Начальное состояние: " + cS.getClass().getSimpleName());
    }

    // SetState
    public void setS(IState newState) {
        cS = newState;
        System.out.println("--> Изменение состояния на: " + cS.getClass().getSimpleName());
    }

    // AddMoney
    public void addM(double amount) {
        cB += amount;
    }

    // ResetBalance (Сбросить баланс)
    public void resB() {
        cB = 0.0;
    }

    // GetCurrentBalance (Получить текущий баланс)
    public double getCB() {
        return cB;
    }

    // GetTicketPrice (Получить цену билета)
    public double getTP() {
        return tP;
    }

    // Методы-триггеры (делегирование)
    public void selT() { cS.selT(this); }
    public void insM(double amount) { cS.insM(this, amount); }
    public void disT() { cS.disT(this); }
    public void canT() { cS.canT(this); }
}



//  Состояние ожидания
 class IdleState implements IState {
    private final TVM machine;

    public IdleState(TVM machine) {
        this.machine = machine;
    }

    @Override
    public void selT(TVM machine) {
        System.out.println("Билет выбран. Внесите средства.");
        machine.setS(machine.WaitM);
    }

    @Override
    public void insM(TVM machine, double amount) {
        System.out.println("Сначала выберите билет!");
    }

    @Override
    public void disT(TVM machine) { System.out.println("Ошибка: Невозможно выдать билет."); }
    @Override
    public void canT(TVM machine) { System.out.println("Нет активной транзакции."); }
}


// WaitingForMoneyState
 class WaitMState implements IState {
    private final TVM machine;

    public WaitMState(TVM machine) {
        this.machine = machine;
    }

    @Override
    public void selT(TVM machine) {
        System.out.println("Билет уже выбран.");
    }

    @Override
    public void insM(TVM machine, double amount) {
        machine.addM(amount);
        System.out.printf("Внесено: %.2f. Цена: %.2f%n", machine.getCB(), machine.getTP());

        if (machine.getCB() >= machine.getTP()) {
            machine.setS(machine.MoneyR);
        }
    }

    @Override
    public void canT(TVM machine) {
        System.out.printf("Отмена: Возврат %.2f.%n", machine.getCB());
        machine.resB();
        machine.setS(machine.CanT);
    }

    @Override
    public void disT(TVM machine) { System.out.println("Ошибка: Недостаточно средств."); }
}






//  MoneyReceivedState
 class MoneyRState implements IState {
    private final TVM machine;

    public MoneyRState(TVM machine) {
        this.machine = machine;
    }

    @Override
    public void selT(TVM machine) { System.out.println("Билет выбран. Выполняется выдача."); }

    @Override
    public void insM(TVM machine, double amount) {
        machine.addM(amount);
        System.out.printf("Излишняя сумма принята. Новый баланс: %.2f.%n", machine.getCB());
    }

    @Override
    public void disT(TVM machine) {
        double change = machine.getCB() - machine.getTP();
        System.out.printf("Выдан билет! Сдача: %.2f%n", change);
        machine.resB();
        machine.setS(machine.DispT);
    }

    @Override
    public void canT(TVM machine) {
        System.out.printf("Отмена: Возврат %.2f.%n", machine.getCB());
        machine.resB();
        machine.setS(machine.CanT);
    }
}



//  TicketDispensedState
 class DispTState implements IState {
    private final TVM machine;

    public DispTState(TVM machine) {
        this.machine = machine;
    }
    // После выдачи билет автомат автоматически переходит в Idle для следующего клиента.
    @Override
    public void selT(TVM machine) { machine.setS(machine.Idle); machine.selT(); }
    @Override
    public void insM(TVM machine, double amount) { machine.setS(machine.Idle); machine.insM(amount); }
    @Override
    public void disT(TVM machine) { System.out.println("Ошибка: Билет уже выдан."); }
    @Override
    public void canT(TVM machine) { System.out.println("Ошибка: Транзакция завершена."); }
}

//  TransactionCanceledState
class CanTState implements IState {
    private final TVM machine;

    public CanTState(TVM machine) {
        this.machine = machine;
    }
    // После отмены транзакции автомат автоматически переходит в Idle.
    @Override
    public void selT(TVM machine) { machine.setS(machine.Idle); machine.selT(); }
    @Override
    public void insM(TVM machine, double amount) { machine.setS(machine.Idle); machine.insM(amount); }
    @Override
    public void disT(TVM machine) { System.out.println("Ошибка: Транзакция отменена."); }
    @Override
    public void canT(TVM machine) { System.out.println("Ошибка: Транзакция уже отменена."); }

}













public class dz2 {
    public static void main(String[] args) {
        TVM machine = new TVM(); // [Idle]

        System.out.println("\n--- Сценарий 1: Успешная покупка (5.00) ---");
        machine.selT();           // [WaitM]
        machine.insM(3.00);       // [WaitM]
        machine.insM(2.50);       // [MoneyR] (Баланс 5.50)
        machine.disT();           // [DispT] (Сдача 0.50)
        machine.selT();           // [DispT] -> [Idle] -> [WaitM]

        System.out.println("\n--- Сценарий 2: Отмена транзакции ---");
        machine.insM(4.50);       // [WaitM] (Баланс 4.50)
        machine.canT();           // [CanT] (Возврат 4.50)
        machine.insM(10.00);      // [CanT] -> [Idle] -> [WaitM] (Баланс 10.00)
        machine.disT();           // [MoneyR] -> [DispT]
    }
}