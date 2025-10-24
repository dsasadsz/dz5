//package modul7;
//import java.util.ArrayList;
//import java.util.Deque;
//import java.util.LinkedList;
//import java.util.List;
//
//
//
//
//interface ICommand {
//    void execute(); //орындау
//    void undo(); //отмена
//}
//
//
//
////команда алады
//class Light {
//    private final String name;
//    private boolean on = false;
//    public Light(String name) { this.name = name; }
//    public void on() { on = true; System.out.println(name + ": свет включён"); }
//    public void off() { on = false; System.out.println(name + ": свет выключен"); }
//    public boolean isOn() { return on; }
//    public String getName() { return name; }
//}
//
//class Television {
//    private final String name;
//    private boolean on = false;
//    public Television(String name) { this.name = name; }
//    public void on() { on = true; System.out.println(name + ": телевизор включён"); }
//    public void off() { on = false; System.out.println(name + ": телевизор выключен"); }
//    public boolean isOn() { return on; }
//}
//
//class AirConditioner {
//    private final String name;
//    private boolean on = false;
//    private int temp = 24;
//    public AirConditioner(String name) { this.name = name; }
//    public void on() { on = true; System.out.println(name + ": кондиционер включён"); }
//    public void off() { on = false; System.out.println(name + ": кондиционер выключен"); }
//    public void setTemperature(int t) { temp = t; System.out.println(name + ": температура установлена " + temp + "°C"); }
//    public boolean isOn() { return on; }
//}
//
//
//
//
//
////кімге не жіберу керек
//class LightOnCommand implements ICommand {
//    private final Light light;
//    public LightOnCommand(Light light) { this.light = light; }
//    public void execute() { light.on(); }
//    public void undo() { light.off(); }
//}
//
//class LightOffCommand implements ICommand {
//    private final Light light;
//    public LightOffCommand(Light light) { this.light = light; }
//    public void execute() { light.off(); }
//    public void undo() { light.on(); }
//}
//
//
//
//class TvOnCommand implements ICommand {
//    private final Television tv;
//    public TvOnCommand(Television tv) { this.tv = tv; }
//    public void execute() { tv.on(); }
//    public void undo() { tv.off(); }
//}
//
//class TvOffCommand implements ICommand {
//    private final Television tv;
//    public TvOffCommand(Television tv) { this.tv = tv; }
//    public void execute() { tv.off(); }
//    public void undo() { tv.on(); }
//}
//
//
//
//class AcOnCommand implements ICommand {
//    private final AirConditioner ac;
//    public AcOnCommand(AirConditioner ac) { this.ac = ac; }
//    public void execute() { ac.on(); }
//    public void undo() { ac.off(); }
//}
//
//class AcOffCommand implements ICommand {
//    private final AirConditioner ac;
//    public AcOffCommand(AirConditioner ac) { this.ac = ac; }
//    public void execute() { ac.off(); }
//    public void undo() { ac.on(); }
//}
//
//
//class SetAcTempCommand implements ICommand {
//    private final AirConditioner ac;
//    private final int newTemp;
//    private int prevTemp;
//    public SetAcTempCommand(AirConditioner ac, int temp) { this.ac = ac; this.newTemp = temp; }
//    public void execute() { prevTemp = 24; ac.setTemperature(newTemp); }
//    public void undo() { ac.setTemperature(prevTemp); }
//}
//
//class MacroCommand implements ICommand {
//    private final List<ICommand> commands = new ArrayList<>();
//    public MacroCommand(List<ICommand> cmds) { if (cmds != null) commands.addAll(cmds); }
//    public void execute() { for (ICommand c : commands) c.execute(); }
//    public void undo() { for (int i = commands.size() - 1; i >= 0; i--) commands.get(i).undo(); }
//}
//
//
//
//
//
////pult Жіберуші
//class RemoteControl {
//    private ICommand onCommand;
//    private ICommand offCommand;
//    private final Deque<ICommand> history = new LinkedList<>();
//    public void setCommands(ICommand onCmd, ICommand offCmd) { onCommand = onCmd; offCommand = offCmd; }
//    public void pressOnButton() {
//        if (onCommand == null) { System.out.println("Кнопка 'Вкл' не назначена"); return; }
//        onCommand.execute();
//        history.push(onCommand);
//    }
//    public void pressOffButton() {
//        if (offCommand == null) { System.out.println("Кнопка 'Выкл' не назначена"); return; }
//        offCommand.execute();
//        history.push(offCommand);
//    }
//    public void pressUndoButton() {
//        if (history.isEmpty()) { System.out.println("Нечего отменять"); return; }
//        ICommand last = history.pop();
//        last.undo();
//    }
//    public void logHistory() {
//        System.out.println("История (последние сверху): ");
//        for (ICommand c : history) System.out.println("  - " + c.getClass().getSimpleName());
//    }
//}
//
//public class Lab1 {
//    public static void main(String[] args) {
//        Light living = new Light("Гостиная");
//        Light kitchen = new Light("Кухня");
//        Television tv = new Television("Зал ТВ");
//        AirConditioner ac = new AirConditioner("Спальня AC");
//
//        LightOnCommand livingOn = new LightOnCommand(living);
//        LightOffCommand livingOff = new LightOffCommand(living);
//        LightOnCommand kitchenOn = new LightOnCommand(kitchen);
//        LightOffCommand kitchenOff = new LightOffCommand(kitchen);
//
//        TvOnCommand tvOn = new TvOnCommand(tv);
//        TvOffCommand tvOff = new TvOffCommand(tv);
//
//        AcOnCommand acOn = new AcOnCommand(ac);
//        AcOffCommand acOff = new AcOffCommand(ac);
//        SetAcTempCommand acTemp = new SetAcTempCommand(ac, 22);
//
//        RemoteControl remote = new RemoteControl();
//
//        remote.setCommands(livingOn, livingOff);
//        remote.pressOnButton();
//        remote.pressOffButton();
//        remote.pressUndoButton();
//
//        List<ICommand> allOn = new ArrayList<>();
//        allOn.add(livingOn);
//        allOn.add(kitchenOn);
//        allOn.add(tvOn);
//        allOn.add(acOn);
//        MacroCommand allOnMacro = new MacroCommand(allOn);
//
//        List<ICommand> allOff = new ArrayList<>();
//        allOff.add(livingOff);
//        allOff.add(kitchenOff);
//        allOff.add(tvOff);
//        allOff.add(acOff);
//        MacroCommand allOffMacro = new MacroCommand(allOff);
//
//        remote.setCommands(allOnMacro, allOffMacro);
//        remote.pressOnButton();
//        remote.pressOffButton();
//        remote.pressUndoButton();
//
//        remote.setCommands(tvOn, tvOff);
//        remote.pressOnButton();
//        remote.pressUndoButton();
//
//        remote.setCommands(acOn, acOff);
//        remote.pressOnButton();
//        remote.pressOnButton();
//        remote.pressOffButton();
//        remote.pressUndoButton();
//
//        remote.setCommands(acTemp, null);
//        remote.pressOnButton();
//        remote.pressUndoButton();
//
//        remote.logHistory();
//    }
//}
