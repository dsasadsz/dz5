//package modul10;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//class Room {
//    private final int number;
//    private String type;
//    private double price;
//    private boolean isAvailable;
//
//    public Room(int number, String type, double price) {
//        this.number = number;
//        this.type = type;
//        this.price = price;
//        this.isAvailable = true;
//    }
//
//    public int getNumber() { return number; }
//    public String getType() { return type; }
//    public double getPrice() { return price; }
//    public boolean isAvailable() { return isAvailable; }
//
//    public void setAvailable(boolean available) { isAvailable = available; }
//
//    @Override
//    public String toString() {
//        return "Номер " + number + " (" + type +
//                "), Стоимость: " + String.format("%.2f ₸", price) +
//                ", Свободен: " + (isAvailable ? "Да" : "Нет");
//    }
//}
//
//class Booking {
//    private static int nextBookingId = 1000;
//    private final int bookingId;
//    private final int roomNumber;
//    private final String guestName;
//    private final LocalDate checkInDate;
//    private final LocalDate checkOutDate;
//
//    public Booking(int roomNumber, String guestName, LocalDate checkIn, LocalDate checkOut) {
//        this.bookingId = nextBookingId++;
//        this.roomNumber = roomNumber;
//        this.guestName = guestName;
//        this.checkInDate = checkIn;
//        this.checkOutDate = checkOut;
//    }
//
//    public int getBookingId() { return bookingId; }
//    public int getRoomNumber() { return roomNumber; }
//    public String getGuestName() { return guestName; }
//    public LocalDate getCheckInDate() { return checkInDate; }
//    public LocalDate getCheckOutDate() { return checkOutDate; }
//
//    @Override
//    public String toString() {
//        return "Бронь ID: " + bookingId +
//                ", Номер: " + roomNumber +
//                ", Гость: " + guestName +
//                ", Заезд: " + checkInDate +
//                ", Выезд: " + checkOutDate;
//    }
//}
//
//class Hotel {
//    private final List<Room> rooms = new ArrayList<>();
//    private final List<Booking> bookings = new ArrayList<>();
//
//    public Hotel() {
//        // Инициализация номеров
//        rooms.add(new Room(101, "Қонақ үй (Стандарт)", 50.00));
//        rooms.add(new Room(102, "Отбасылық (Семейный)", 75.00));
//        rooms.add(new Room(201, "Сұлтан Люкс", 150.00));
//    }
//
//    // 1
//    public List<Room> viewAvailableRooms() {
//        List<Room> availableRooms = new ArrayList<>();
//        for (Room room : rooms) {
//            if (room.isAvailable()) {
//                availableRooms.add(room);
//            }
//        }
//        return availableRooms;
//    }
//
//    // 2
//    public Optional<Booking> bookRoom(int roomNumber, String guestName, LocalDate checkIn, LocalDate checkOut) {
//        Optional<Room> roomOpt = rooms.stream()
//                .filter(r -> r.getNumber() == roomNumber)
//                .findFirst();
//
//        if (roomOpt.isEmpty()) {
//            System.out.println("Неудача! Номер " + roomNumber + " не найден.");
//            return Optional.empty();
//        }
//
//        Room room = roomOpt.get();
//
//        if (!room.isAvailable()) {
//            System.out.println("Стоп! Номер " + roomNumber + " уже занят.");
//            return Optional.empty();
//        }
//
//        room.setAvailable(false);
//        Booking newBooking = new Booking(roomNumber, guestName, checkIn, checkOut);
//        bookings.add(newBooking);
//        System.out.println("Успех! Номер " + roomNumber + " зарезервирован для " + guestName + ". Ваш код брони: " + newBooking.getBookingId());
//        return Optional.of(newBooking);
//    }
//
//
//
//
//    // 3
//    public boolean cancelBooking(int bookingId) {
//        Optional<Booking> bookingOpt = bookings.stream()
//                .filter(b -> b.getBookingId() == bookingId)
//                .findFirst();
//
//        if (bookingOpt.isEmpty()) {
//            System.out.println("Ошибка отмены. Бронь с ID " + bookingId + " не найдена.");
//            return false;
//        }
//
//        Booking booking = bookingOpt.get();
//
//        Optional<Room> roomOpt = rooms.stream()
//                .filter(r -> r.getNumber() == booking.getRoomNumber())
//                .findFirst();
//
//        roomOpt.ifPresent(room -> room.setAvailable(true));
//
//        bookings.remove(booking);
//        System.out.println("Отменено. Бронирование ID " + bookingId + " удалено. Номер " + booking.getRoomNumber() + " снова свободен.");
//        return true;
//    }
//
//
//
//    // 4
//    public void addRoom(int number, String type, double price) {
//        if (rooms.stream().anyMatch(r -> r.getNumber() == number)) {
//            System.out.println("Администратор: Номер " + number + " уже существует.");
//            return;
//        }
//
//        rooms.add(new Room(number, type, price));
//        System.out.println("Администратор: Добавлен новый номер " + number + " (" + type + ").");
//    }
//
//
//
//    // 5
//    public boolean removeRoom(int number) {
//        Optional<Room> roomToRemoveOpt = rooms.stream()
//                .filter(r -> r.getNumber() == number)
//                .findFirst();
//
//        if (roomToRemoveOpt.isEmpty()) {
//            System.out.println("Администратор: Номер " + number + " не найден.");
//            return false;
//        }
//
//        Room roomToRemove = roomToRemoveOpt.get();
//
//        if (!roomToRemove.isAvailable()) {
//            System.out.println("Администратор: Номер " + number + " занят! Его нельзя удалить.");
//            return false;
//        }
//
//        rooms.remove(roomToRemove);
//        System.out.println("Администратор: Номер " + number + " удален из системы.");
//        return true;
//    }
//}
//
//
//
//class Guest {
//    private final Hotel hotel;
//    private final String name;
//
//    public Guest(String name, Hotel hotel) {
//        this.name = name;
//        this.hotel = hotel;
//    }
//
//    public void viewRooms() {
//        System.out.println("\nГость " + name + " ищет свободные номера:");
//        List<Room> available = hotel.viewAvailableRooms();
//        if (available.isEmpty()) {
//            System.out.println("Все номера заняты.");
//            return;
//        }
//
//        for (Room room : available) {
//            System.out.println(room);
//        }
//    }
//
//    public Optional<Booking> bookRoom(int roomNumber, LocalDate checkIn, LocalDate checkOut) {
//        System.out.println("\nГость " + name + " предпринимает попытку бронирования номера " + roomNumber + "...");
//        return hotel.bookRoom(roomNumber, this.name, checkIn, checkOut);
//    }
//
//    public void cancelBooking(int bookingId) {
//        System.out.println("\nГость " + name + " пытается отменить бронь ID " + bookingId + "...");
//        hotel.cancelBooking(bookingId);
//    }
//}
//
//
//
//class Admin {
//    private final Hotel hotel;
//
//    public Admin(Hotel hotel) {
//        this.hotel = hotel;
//    }
//
//    public void addNewRoom(int number, String type, double price) {
//        System.out.println("\nАдминистратор: Добавляет новый номер " + number + ".");
//        hotel.addRoom(number, type, price);
//    }
//
//    public void deleteRoom(int number) {
//        System.out.println("\nАдминистратор: Удаление номера " + number + ".");
//        hotel.removeRoom(number);
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
//
//
//public class Lab1 {
//    public static void main(String[] args) {
//        System.out.println("Система Бронирования Номеров в Гостинице \"Nomad Palace\"");
//        Hotel hotel = new Hotel();
//        Guest guest1 = new Guest("Айдар", hotel);
//        Admin admin = new Admin(hotel);
//        LocalDate today = LocalDate.now();
//
//        System.out.println("\n  Гость ");
//        System.out.println("");
//
//        guest1.viewRooms();
//        //Бронирование
//        Optional<Booking> booking1Opt = guest1.bookRoom(101, today.plusDays(1), today.plusDays(3));
//
//        guest1.viewRooms();
//
//        //  Попытка забронировать занятый
//        Guest guest2 = new Guest("Асель", hotel);
//        guest2.bookRoom(101, today.plusDays(5), today.plusDays(7));
//
//        //  Отмена бронирования
//        if (booking1Opt.isPresent()) {
//            guest1.cancelBooking(booking1Opt.get().getBookingId());
//        }
//
//        // Проверка снова
//        guest1.viewRooms();
//
//
//        System.out.println("\nРАЗДЕЛ: ДЕЙСТВИЯ АДМИНИСТРАТОРА");
//        System.out.println("------------------------------------");
//
//        // Администратор: Добавление нового номера
//        admin.addNewRoom(301, "Ханский Люкс", 500.00);
//
//        // Проверка: Номер 301 доступен для бронирования
//        guest1.viewRooms();
//
//        // Администратор: Удаление номера (успешно)
//        admin.deleteRoom(102);
//
//        // Администратор: Попытка удалить номер, который занят
//        Optional<Booking> tempBookingOpt = guest2.bookRoom(201, today.plusDays(10), today.plusDays(12));
//        admin.deleteRoom(201); // Ожидается ошибка
//        if (tempBookingOpt.isPresent()) {
//            guest2.cancelBooking(tempBookingOpt.get().getBookingId());
//        }
//
//        // Администратор: Повторное удаление
//        admin.deleteRoom(201);
//
//        System.out.println("\nРАЗДЕЛ: ИТОГОВАЯ ПРОВЕРКА");
//        System.out.println("------------------------------------");
//        guest1.viewRooms();
//    }
//}
