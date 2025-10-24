//package modul7;
//import java.util.*;
////Посредник
//interface IMediator {
//    void registerColleague(Colleague colleague);
//    void unregisterColleague(Colleague colleague);
//    void sendMessage(String message, Colleague sender);
//    void sendPrivateMessage(String message, Colleague sender, String recipientName);
//    void createGroup(String groupName, Collection<String> memberNames);
//    void addToGroup(String groupName, String memberName);
//    void removeFromGroup(String groupName, String memberName);
//    void sendGroupMessage(String groupName, String message, Colleague sender);
//    List<String> getLog();
//}
//
//abstract class Colleague {
//    protected IMediator mediator;
//    protected String name;
//    public Colleague(IMediator mediator, String name) {
//        this.mediator = mediator;
//        this.name = name;
//    }
//    public String getName() { return name; }
//    public void send(String message) { mediator.sendMessage(message, this); }
//    public void sendPrivate(String message, String recipientName) { mediator.sendPrivateMessage(message, this, recipientName); }
//    public void sendToGroup(String groupName, String message) { mediator.sendGroupMessage(groupName, message, this); }
//    public abstract void receiveMessage(String from, String message);
//    public abstract void receivePrivate(String from, String message);
//}
//
//class User extends Colleague {
//    public User(IMediator mediator, String name) { super(mediator, name); }
//    @Override
//    public void receiveMessage(String from, String message) {
//        System.out.println("[" + name + "] получает от " + from + ": " + message);
//    }
//    @Override
//    public void receivePrivate(String from, String message) {
//        System.out.println("[" + name + "] ЛИЧНОЕ от " + from + ": " + message);
//    }
//}
//
//
////басты посредник
//class ChatMediator implements IMediator {
//    private final Map<String, Colleague> colleagues = new HashMap<>();
//    private final Map<String, Set<String>> groups = new HashMap<>();
//    private final List<String> log = new ArrayList<>();
//
//    @Override
//    public synchronized void registerColleague(Colleague colleague) {
//        if (colleague == null || colleague.getName() == null) return;
//        if (!colleagues.containsKey(colleague.getName())) {
//            colleagues.put(colleague.getName(), colleague);
//            addLog("[SYSTEM] " + colleague.getName() + " зарегистрирован(а)");
//        }
//    }
//
//    @Override
//    public synchronized void unregisterColleague(Colleague colleague) {
//        if (colleague == null) return;
//        if (colleagues.remove(colleague.getName()) != null) {
//            for (Set<String> members : groups.values()) members.remove(colleague.getName());
//            addLog("[SYSTEM] " + colleague.getName() + " отключён(а)");
//        }
//    }
//
//    @Override
//    public synchronized void sendMessage(String message, Colleague sender) {
//        if (!isRegistered(sender)) { System.out.println("Ошибка: отправитель не зарегистрирован."); return; }
//        addLog("[BROADCAST] " + sender.getName() + ": " + message);
//        for (Colleague c : colleagues.values()) {
//            if (!c.getName().equalsIgnoreCase(sender.getName())) c.receiveMessage(sender.getName(), message);
//        }
//    }
//
//    @Override
//    public synchronized void sendPrivateMessage(String message, Colleague sender, String recipientName) {
//        if (!isRegistered(sender)) { System.out.println("Ошибка: отправитель не зарегистрирован."); return; }
//        if (recipientName == null || recipientName.isEmpty()) { System.out.println("Ошибка: получатель не указан."); return; }
//        Colleague r = colleagues.get(recipientName);
//        if (r == null) { System.out.println("Ошибка: получатель '" + recipientName + "' не найден."); return; }
//        addLog("[PRIVATE] " + sender.getName() + " -> " + recipientName + ": " + message);
//        r.receivePrivate(sender.getName(), message);
//    }
//
//    @Override
//    public synchronized void createGroup(String groupName, Collection<String> memberNames) {
//        if (groupName == null || groupName.isEmpty()) return;
//        groups.putIfAbsent(groupName, new HashSet<>());
//        if (memberNames != null) {
//            for (String m : memberNames) if (colleagues.containsKey(m)) groups.get(groupName).add(m);
//        }
//        addLog("[SYSTEM] Группа '" + groupName + "' создана");
//    }
//
//    @Override
//    public synchronized void addToGroup(String groupName, String memberName) {
//        if (groupName == null || memberName == null) return;
//        groups.putIfAbsent(groupName, new HashSet<>());
//        if (colleagues.containsKey(memberName)) groups.get(groupName).add(memberName);
//        addLog("[SYSTEM] " + memberName + " добавлен(а) в группу '" + groupName + "'");
//    }
//
//    @Override
//    public synchronized void removeFromGroup(String groupName, String memberName) {
//        if (groupName == null || memberName == null) return;
//        Set<String> members = groups.get(groupName);
//        if (members != null) members.remove(memberName);
//        addLog("[SYSTEM] " + memberName + " удалён(а) из группы '" + groupName + "'");
//    }
//
//    @Override
//    public synchronized void sendGroupMessage(String groupName, String message, Colleague sender) {
//        if (!isRegistered(sender)) { System.out.println("Ошибка: отправитель не зарегистрирован."); return; }
//        Set<String> members = groups.get(groupName);
//        if (members == null) { System.out.println("Ошибка: группа '" + groupName + "' не найдена."); return; }
//        addLog("[GROUP:" + groupName + "] " + sender.getName() + ": " + message);
//        for (String memberName : members) {
//            if (!memberName.equalsIgnoreCase(sender.getName())) {
//                Colleague c = colleagues.get(memberName);
//                if (c != null) c.receiveMessage(sender.getName() + " (группа:" + groupName + ")", message);
//            }
//        }
//    }
//
//    @Override
//    public synchronized List<String> getLog() {
//        return new ArrayList<>(log);
//    }
//
//    private boolean isRegistered(Colleague c) {
//        return c != null && colleagues.containsKey(c.getName());
//    }
//
//    private void addLog(String entry) {
//        log.add("[" + new Date() + "] " + entry);
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
//public class Lab3 {
//    public static void main(String[] args) {
//        ChatMediator mediator = new ChatMediator();
//
//        User alice = new User(mediator, "Alice");
//        User bob = new User(mediator, "Bob");
//        User charlie = new User(mediator, "Charlie");
//
//        mediator.registerColleague(alice);
//        mediator.registerColleague(bob);
//        mediator.registerColleague(charlie);
//
//        alice.send("Hello everyone!");
//        bob.sendPrivate("Hi Alice, how are you?", "Alice");
//
//        mediator.createGroup("Team", Arrays.asList("Alice", "Bob"));
//        charlie.sendToGroup("Team", "Can I join?");
//        mediator.addToGroup("Team", "Charlie");
//        alice.sendToGroup("Team", "Welcome to the team!");
//
//        mediator.unregisterColleague(bob);
//        alice.send("Bob left, is he gone?");
//
//        System.out.println();
//        System.out.println("=== Log ===");
//        for (String line : mediator.getLog()) System.out.println(line);
//    }
//}
