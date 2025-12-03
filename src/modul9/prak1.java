package modul9;

import java.util.*;

// RoomBookingSystem
class RB {
    private final Map<Integer, String> bookings = new HashMap<>(); // roomId -> guestName
    private final int totalRooms;

    public RB(int totalRooms) { this.totalRooms = totalRooms; }

    public boolean isAvailable(int roomId) {
        if (roomId < 1 || roomId > totalRooms) return false;
        return !bookings.containsKey(roomId);
    }

    public Optional<Integer> findAnyAvailable() {
        for (int i = 1; i <= totalRooms; i++) if (!bookings.containsKey(i)) return Optional.of(i);
        return Optional.empty();
    }

    public boolean bookRoom(int roomId, String guest) {
        if (!isAvailable(roomId)) return false;
        bookings.put(roomId, guest);
        System.out.println("RB: Комната #" + roomId + " забронирована для " + guest + ".");
        return true;
    }

    public Optional<Integer> bookAny(String guest) {
        Optional<Integer> opt = findAnyAvailable();
        if (opt.isPresent()) {
            int id = opt.get();
            bookings.put(id, guest);
            System.out.println("RB: Найдена и забронирована комната #" + id + " для " + guest + ".");
            return Optional.of(id);
        } else {
            System.out.println("RB: Свободных комнат нет.");
            return Optional.empty();
        }
    }

    public boolean cancelBooking(int roomId, String guest) {
        if (bookings.getOrDefault(roomId, "").equals(guest)) {
            bookings.remove(roomId);
            System.out.println("RB: Бронирование комнаты #" + roomId + " отменено для " + guest + ".");
            return true;
        }
        System.out.println("RB: Отмена не выполнена — либо комната не занята, либо другой гость.");
        return false;
    }

    public void listBookings() {
        System.out.println("RB: Текущие брони:");
        if (bookings.isEmpty()) System.out.println("  — пусто —");
        bookings.forEach((k, v) -> System.out.println("  Комната #" + k + " -> " + v));
    }
}

// RestaurantSystem
class RS {
    private final List<String> reservations = new ArrayList<>();

    public boolean reserveTable(String guest, int people, String time) {
        String rec = guest + " | " + people + " чел. | " + time;
        reservations.add(rec);
        System.out.println("RS: Зарезервирован стол: " + rec);
        return true;
    }

    public boolean orderFood(int roomId, String dish) {
        System.out.println("RS: Заказ для номера #" + roomId + ": " + dish + " (в пути к гостю).");
        return true;
    }

    public boolean cancelReservation(String guest, String time) {
        Optional<String> found = reservations.stream().filter(r -> r.startsWith(guest + " |") && r.endsWith(time)).findFirst();
        if (found.isPresent()) {
            reservations.remove(found.get());
            System.out.println("RS: Бронь удалена: " + found.get());
            return true;
        }
        System.out.println("RS: Бронь не найдена для " + guest + " в " + time);
        return false;
    }

    public void listReservations() {
        System.out.println("RS: Текущие брони в ресторане:");
        if (reservations.isEmpty()) System.out.println("  — пусто —");
        reservations.forEach(r -> System.out.println("  " + r));
    }
}

// EventManagementSystem
class EV {
    private final List<String> events = new ArrayList<>();

    public boolean bookHall(String eventName, int attendees, String date) {
        String rec = eventName + " | " + attendees + " участников | " + date;
        events.add(rec);
        System.out.println("EV: Забронирован зал для мероприятия: " + rec);
        return true;
    }

    public boolean orderEquipment(String eventName, List<String> equipment) {
        System.out.println("EV: Для '" + eventName + "' заказано оборудование: " + String.join(", ", equipment));
        return true;
    }

    public boolean cancelEvent(String eventName, String date) {
        Optional<String> found = events.stream().filter(e -> e.startsWith(eventName + " |") && e.endsWith(date)).findFirst();
        if (found.isPresent()) {
            events.remove(found.get());
            System.out.println("EV: Мероприятие отменено: " + found.get());
            return true;
        }
        System.out.println("EV: Мероприятие не найдено: " + eventName + " на " + date);
        return false;
    }

    public void listEvents() {
        System.out.println("EV: Запланированные мероприятия:");
        if (events.isEmpty()) System.out.println("  — пусто —");
        events.forEach(e -> System.out.println("  " + e));
    }
}

// CleaningService
class CL {
    private final Map<Integer, String> schedule = new HashMap<>(); // roomId -> date/time/worker

    public boolean scheduleCleaning(int roomId, String when) {
        schedule.put(roomId, when);
        System.out.println("CL: Запланирована уборка номера #" + roomId + " на " + when);
        return true;
    }

    public boolean performCleaning(int roomId) {
        if (schedule.containsKey(roomId)) {
            System.out.println("CL: Уборка выполнена в номере #" + roomId + " (ранее запланировано: " + schedule.get(roomId) + ").");
            schedule.remove(roomId);
            return true;
        }
        System.out.println("CL: Срочная уборка номера #" + roomId + " выполнена (не было расписания).");
        return true;
    }

    public boolean cancelCleaning(int roomId) {
        if (schedule.remove(roomId) != null) {
            System.out.println("CL: Расписание уборки для номера #" + roomId + " отменено.");
            return true;
        }
        System.out.println("CL: Нечего отменять для номера #" + roomId);
        return false;
    }

    public void listSchedule() {
        System.out.println("CL: Расписание уборок:");
        if (schedule.isEmpty()) System.out.println("  — пусто —");
        schedule.forEach((r, t) -> System.out.println("  Номер #" + r + " -> " + t));
    }
}


// TaxiService
class TX {
    public void callTaxi(String guest, String pickup) {
        System.out.println("TX: Такси вызвано для " + guest + " (адрес/место: " + pickup + ").");
    }
}






// HotelFacade
class HF {
    private final RB rb;
    private final RS rs;
    private final EV ev;
    private final CL cl;
    private final TX tx;

    public HF(RB rb, RS rs, EV ev, CL cl, TX tx) {
        this.rb = rb; this.rs = rs; this.ev = ev; this.cl = cl; this.tx = tx;
    }

    public Optional<Integer> bookRoomWithServices(String guest, boolean needDinner, String dinnerDish, boolean needCleaning, String cleaningTime) {
        System.out.println("\nHF: Сценарий — бронирование номера с услугами для " + guest);
        Optional<Integer> optRoom = rb.bookAny(guest);
        if (optRoom.isEmpty()) return Optional.empty();
        int roomId = optRoom.get();

        if (needDinner) {
            rs.orderFood(roomId, dinnerDish);
        }
        if (needCleaning) {
            cl.scheduleCleaning(roomId, cleaningTime);
        }
        System.out.println("HF: Бронирование с услугами завершено для " + guest + " (комната #" + roomId + ").");
        return Optional.of(roomId);
    }

    public boolean organizeEvent(String eventName, int attendees, String date, List<String> equipment, List<String> participantNames) {
        System.out.println("\nHF: Сценарий — организация мероприятия: " + eventName);
        ev.bookHall(eventName, attendees, date);
        ev.orderEquipment(eventName, equipment);
        // Бронируем номера для участников
        for (String p : participantNames) {
            Optional<Integer> r = rb.bookAny(p);
            r.ifPresent(roomId -> {
                cl.scheduleCleaning(roomId, date + " (до заселения)");
            });
        }
        System.out.println("HF: Мероприятие '" + eventName + "' организовано на " + date + ".");
        return true;
    }

    public boolean bookTableWithTaxi(String guest, int people, String time, String pickup) {
        System.out.println("\nHF: Сценарий — бронь стола с такси для " + guest);
        boolean ok = rs.reserveTable(guest, people, time);
        if (ok) tx.callTaxi(guest, pickup);
        System.out.println("HF: Бронь стола оформлена для " + guest + " на " + time);
        return ok;
    }

    public boolean cancelRoomBooking(int roomId, String guest) {
        System.out.println("\nHF: Сценарий — отмена брони номера #" + roomId + " для " + guest);
        boolean ok = rb.cancelBooking(roomId, guest);
        if (ok) {
            cl.cancelCleaning(roomId);
        }
        return ok;
    }

    public boolean cancelEvent(String eventName, String date) {
        System.out.println("\nHF: Сценарий — отмена мероприятия '" + eventName + "' на " + date);
        return ev.cancelEvent(eventName, date);
    }

    public boolean requestCleaningNow(int roomId) {
        System.out.println("\nHF: Сценарий — срочная уборка номера #" + roomId);
        return cl.performCleaning(roomId);
    }

    public void statusReport() {
        System.out.println("\nHF: Отчёт состояния подсистем:");
        rb.listBookings();
        rs.listReservations();
        ev.listEvents();
        cl.listSchedule();
    }
}










public class prak1 {
    public static void main(String[] args) {
        RB rooms = new RB(5);       // 5 комнат в отеле
        RS rest = new RS();
        EV events = new EV();
        CL clean = new CL();
        TX taxi = new TX();

        HF hotel = new HF(rooms, rest, events, clean, taxi);

        // Сценарий 1: гость бронирует комнату + ужин + уборка
        Optional<Integer> r1 = hotel.bookRoomWithServices("Алексей", true, "Ужин: сет из морепродуктов", true, "10:00 завтра");
        // Сценарий 2: организация мероприятия
        List<String> equip = Arrays.asList("Проектор", "Микрофоны", "Сцена");
        List<String> participants = Arrays.asList("Ирина", "Павел", "Мария");
        hotel.organizeEvent("Конференция DevDay", 50, "2025-12-01", equip, participants);
        // Сценарий 3: бронь стола + такси
        hotel.bookTableWithTaxi("Ольга", 4, "19:00", "Лобби отеля");

        // Просмотр статуса
        hotel.statusReport();

        // Отмена брони и срочная уборка
        r1.ifPresent(roomId -> hotel.cancelRoomBooking(roomId, "Алексей"));
        hotel.requestCleaningNow(2); // срочная уборка номера 2

        // Отмена мероприятия
        hotel.cancelEvent("Конференция DevDay", "2025-12-01");

        // Финальный статус
        hotel.statusReport();
    }
}
