import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

// ============================================================
// ROOM CLASS
// ============================================================

class Room {
    private int roomNumber;
    private String category;
    private double pricePerNight;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber +
                " | Category: " + category +
                " | Price/Night: Rs. " + pricePerNight;
    }

    // Convert object to file format
    public String toFileString() {
        return roomNumber + "," + category + "," + pricePerNight;
    }

    // Create Room from file data
    public static Room fromFileString(String line) {
        String[] data = line.split(",");

        int roomNumber = Integer.parseInt(data[0]);
        String category = data[1];
        double price = Double.parseDouble(data[2]);

        return new Room(roomNumber, category, price);
    }
}


// ============================================================
// BOOKING CLASS
// ============================================================

class Booking {
    private int bookingId;
    private String customerName;
    private String phoneNumber;
    private int roomNumber;
    private String roomCategory;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalAmount;
    private String paymentStatus;
    private String bookingStatus;

    public Booking(int bookingId,
                   String customerName,
                   String phoneNumber,
                   int roomNumber,
                   String roomCategory,
                   LocalDate checkIn,
                   LocalDate checkOut,
                   double totalAmount,
                   String paymentStatus,
                   String bookingStatus) {

        this.bookingId = bookingId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.roomNumber = roomNumber;
        this.roomCategory = roomCategory;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Convert booking to file format
    public String toFileString() {
        return bookingId + "|" +
                customerName + "|" +
                phoneNumber + "|" +
                roomNumber + "|" +
                roomCategory + "|" +
                checkIn + "|" +
                checkOut + "|" +
                totalAmount + "|" +
                paymentStatus + "|" +
                bookingStatus;
    }

    // Create booking from file data
    public static Booking fromFileString(String line) {
        String[] data = line.split("\\|");

        return new Booking(
                Integer.parseInt(data[0]),
                data[1],
                data[2],
                Integer.parseInt(data[3]),
                data[4],
                LocalDate.parse(data[5]),
                LocalDate.parse(data[6]),
                Double.parseDouble(data[7]),
                data[8],
                data[9]
        );
    }

    public void displayDetails() {
        System.out.println("\n======================================");
        System.out.println("         BOOKING DETAILS");
        System.out.println("======================================");
        System.out.println("Booking ID       : " + bookingId);
        System.out.println("Customer Name    : " + customerName);
        System.out.println("Phone Number     : " + phoneNumber);
        System.out.println("Room Number      : " + roomNumber);
        System.out.println("Room Category    : " + roomCategory);
        System.out.println("Check-In Date    : " + checkIn);
        System.out.println("Check-Out Date   : " + checkOut);
        System.out.println("Total Amount     : Rs. " + totalAmount);
        System.out.println("Payment Status   : " + paymentStatus);
        System.out.println("Booking Status   : " + bookingStatus);
        System.out.println("======================================");
    }
}


// ============================================================
// PAYMENT CLASS
// ============================================================

class Payment {

    // Payment simulation
    public static boolean processPayment(double amount) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n========== PAYMENT ==========");
        System.out.println("Amount to Pay: Rs. " + amount);

        System.out.println("Select Payment Method:");
        System.out.println("1. Credit/Debit Card");
        System.out.println("2. UPI");
        System.out.println("3. Cash");

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {

            case 1:
                System.out.print("Enter card number: ");
                String card = scanner.nextLine();

                if (card.length() >= 4) {
                    System.out.println("Processing card payment...");
                    System.out.println("Payment successful!");
                    return true;
                } else {
                    System.out.println("Invalid card number.");
                    return false;
                }

            case 2:
                System.out.print("Enter UPI ID: ");
                String upi = scanner.nextLine();

                if (!upi.isEmpty()) {
                    System.out.println("Processing UPI payment...");
                    System.out.println("Payment successful!");
                    return true;
                } else {
                    System.out.println("Invalid UPI ID.");
                    return false;
                }

            case 3:
                System.out.println("Cash payment selected.");
                System.out.println("Payment successful!");
                return true;

            default:
                System.out.println("Invalid payment method.");
                return false;
        }
    }
}


// ============================================================
// FILE MANAGER CLASS
// ============================================================

class FileManager {

    private static final String ROOM_FILE = "rooms.txt";
    private static final String BOOKING_FILE = "bookings.txt";

    // --------------------------------------------------------
    // Save rooms
    // --------------------------------------------------------

    public static void saveRooms(List<Room> rooms) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(ROOM_FILE))) {

            for (Room room : rooms) {
                writer.write(room.toFileString());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving rooms: " + e.getMessage());
        }
    }


    // --------------------------------------------------------
    // Load rooms
    // --------------------------------------------------------

    public static List<Room> loadRooms() {

        List<Room> rooms = new ArrayList<>();

        File file = new File(ROOM_FILE);

        if (!file.exists()) {
            return rooms;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(ROOM_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    rooms.add(Room.fromFileString(line));
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading rooms: " + e.getMessage());
        }

        return rooms;
    }


    // --------------------------------------------------------
    // Save bookings
    // --------------------------------------------------------

    public static void saveBookings(List<Booking> bookings) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(BOOKING_FILE))) {

            for (Booking booking : bookings) {

                writer.write(booking.toFileString());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }


    // --------------------------------------------------------
    // Load bookings
    // --------------------------------------------------------

    public static List<Booking> loadBookings() {

        List<Booking> bookings = new ArrayList<>();

        File file = new File(BOOKING_FILE);

        if (!file.exists()) {
            return bookings;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(BOOKING_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    bookings.add(Booking.fromFileString(line));
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }

        return bookings;
    }
}


// ============================================================
// HOTEL CLASS
// ============================================================

class Hotel {

    private List<Room> rooms;
    private List<Booking> bookings;

    private int nextBookingId = 1001;

    public Hotel() {

        rooms = FileManager.loadRooms();
        bookings = FileManager.loadBookings();

        // If rooms file is empty, create default rooms
        if (rooms.isEmpty()) {
            initializeRooms();
            FileManager.saveRooms(rooms);
        }

        // Find next booking ID
        for (Booking booking : bookings) {
            if (booking.getBookingId() >= nextBookingId) {
                nextBookingId = booking.getBookingId() + 1;
            }
        }
    }


    // --------------------------------------------------------
    // Initialize default rooms
    // --------------------------------------------------------

    private void initializeRooms() {

        // Standard rooms
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(103, "Standard", 1500));
        rooms.add(new Room(104, "Standard", 1500));

        // Deluxe rooms
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(203, "Deluxe", 2500));

        // Suite rooms
        rooms.add(new Room(301, "Suite", 4500));
        rooms.add(new Room(302, "Suite", 4500));
    }


    // --------------------------------------------------------
    // Search rooms
    // --------------------------------------------------------

    public void searchRooms(String category,
                            LocalDate checkIn,
                            LocalDate checkOut) {

        System.out.println("\n========== AVAILABLE ROOMS ==========");

        boolean found = false;

        for (Room room : rooms) {

            // Category filter
            if (!category.equalsIgnoreCase("All") &&
                    !room.getCategory().equalsIgnoreCase(category)) {
                continue;
            }

            // Availability check
            if (isRoomAvailable(room.getRoomNumber(),
                    checkIn,
                    checkOut)) {

                System.out.println(room);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available for the selected dates.");
        }
    }


    // --------------------------------------------------------
    // Check room availability
    // --------------------------------------------------------

    public boolean isRoomAvailable(int roomNumber,
                                   LocalDate requestedCheckIn,
                                   LocalDate requestedCheckOut) {

        for (Booking booking : bookings) {

            if (booking.getRoomNumber() != roomNumber) {
                continue;
            }

            // Ignore cancelled bookings
            if (booking.getBookingStatus()
                    .equalsIgnoreCase("CANCELLED")) {
                continue;
            }

            LocalDate existingCheckIn = booking.getCheckIn();
            LocalDate existingCheckOut = booking.getCheckOut();

            /*
             * Date overlap condition:
             *
             * Requested check-in < existing check-out
             * AND
             * Requested check-out > existing check-in
             */
            boolean overlap =
                    requestedCheckIn.isBefore(existingCheckOut)
                            &&
                    requestedCheckOut.isAfter(existingCheckIn);

            if (overlap) {
                return false;
            }
        }

        return true;
    }


    // --------------------------------------------------------
    // Find room
    // --------------------------------------------------------

    public Room findRoom(int roomNumber) {

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }


    // --------------------------------------------------------
    // Make booking
    // --------------------------------------------------------

    public void makeBooking(Scanner scanner) {

        System.out.println("\n========== MAKE RESERVATION ==========");

        System.out.print("Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        LocalDate checkIn = readDate(scanner, "Check-In Date (YYYY-MM-DD): ");
        LocalDate checkOut = readDate(scanner, "Check-Out Date (YYYY-MM-DD): ");

        // Validate dates
        if (!checkOut.isAfter(checkIn)) {

            System.out.println(
                    "Check-out date must be after check-in date."
            );

            return;
        }

        System.out.println("\nSelect Room Category:");
        System.out.println("1. Standard");
        System.out.println("2. Deluxe");
        System.out.println("3. Suite");

        System.out.print("Enter choice: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        String category;

        switch (categoryChoice) {

            case 1:
                category = "Standard";
                break;

            case 2:
                category = "Deluxe";
                break;

            case 3:
                category = "Suite";
                break;

            default:
                System.out.println("Invalid category.");
                return;
        }

        // Search available rooms
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {

            if (room.getCategory().equalsIgnoreCase(category)
                    &&
                    isRoomAvailable(
                            room.getRoomNumber(),
                            checkIn,
                            checkOut)) {

                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()) {

            System.out.println(
                    "No rooms available in " + category +
                    " category for these dates."
            );

            return;
        }

        System.out.println("\nAvailable Rooms:");

        for (Room room : availableRooms) {
            System.out.println(room);
        }

        System.out.print("\nEnter Room Number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        Room selectedRoom = findRoom(roomNumber);

        if (selectedRoom == null) {

            System.out.println("Room does not exist.");
            return;
        }

        if (!selectedRoom.getCategory()
                .equalsIgnoreCase(category)) {

            System.out.println(
                    "Selected room does not belong to the chosen category."
            );

            return;
        }

        if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {

            System.out.println(
                    "Sorry, this room is no longer available."
            );

            return;
        }

        // Calculate number of nights
        long nights =
                ChronoUnit.DAYS.between(checkIn, checkOut);

        double totalAmount =
                nights * selectedRoom.getPricePerNight();

        System.out.println("\n========== BOOKING SUMMARY ==========");
        System.out.println("Customer      : " + name);
        System.out.println("Room          : " + roomNumber);
        System.out.println("Category      : " + category);
        System.out.println("Check-In      : " + checkIn);
        System.out.println("Check-Out     : " + checkOut);
        System.out.println("Number Nights : " + nights);
        System.out.println("Price/Night    : Rs. "
                + selectedRoom.getPricePerNight());
        System.out.println("Total Amount  : Rs. " + totalAmount);

        System.out.print("\nProceed to payment? (Y/N): ");
        String confirm = scanner.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {

            System.out.println("Booking cancelled.");
            return;
        }

        // Payment simulation
        boolean paymentSuccessful =
                Payment.processPayment(totalAmount);

        if (!paymentSuccessful) {

            System.out.println(
                    "Payment failed. Booking was not created."
            );

            return;
        }

        // Create booking
        Booking booking = new Booking(
                nextBookingId++,
                name,
                phone,
                roomNumber,
                category,
                checkIn,
                checkOut,
                totalAmount,
                "PAID",
                "CONFIRMED"
        );

        bookings.add(booking);

        // Save booking to file
        FileManager.saveBookings(bookings);

        System.out.println("\n====================================");
        System.out.println("     BOOKING SUCCESSFUL!");
        System.out.println("     Booking ID: " +
                booking.getBookingId());
        System.out.println("====================================");
    }


    // --------------------------------------------------------
    // Cancel booking
    // --------------------------------------------------------

    public void cancelBooking(Scanner scanner) {

        System.out.println("\n========== CANCEL RESERVATION ==========");

        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        Booking booking = findBooking(bookingId);

        if (booking == null) {

            System.out.println("Booking not found.");
            return;
        }

        if (booking.getBookingStatus()
                .equalsIgnoreCase("CANCELLED")) {

            System.out.println("Booking is already cancelled.");
            return;
        }

        booking.setBookingStatus("CANCELLED");

        // Simulate refund
        if (booking.getPaymentStatus()
                .equalsIgnoreCase("PAID")) {

            booking.setPaymentStatus("REFUNDED");

            System.out.println(
                    "Payment refund simulated successfully."
            );
        }

        FileManager.saveBookings(bookings);

        System.out.println(
                "Booking " + bookingId +
                " has been cancelled successfully."
        );
    }


    // --------------------------------------------------------
    // Find booking
    // --------------------------------------------------------

    private Booking findBooking(int bookingId) {

        for (Booking booking : bookings) {

            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }

        return null;
    }


    // --------------------------------------------------------
    // View booking details
    // --------------------------------------------------------

    public void viewBooking(Scanner scanner) {

        System.out.println("\n========== VIEW BOOKING ==========");

        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        Booking booking = findBooking(bookingId);

        if (booking == null) {

            System.out.println("Booking not found.");
            return;
        }

        booking.displayDetails();
    }


    // --------------------------------------------------------
    // View all bookings
    // --------------------------------------------------------

    public void viewAllBookings() {

        System.out.println("\n========== ALL BOOKINGS ==========");

        if (bookings.isEmpty()) {

            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings) {

            System.out.println(
                    "ID: " + booking.getBookingId() +
                    " | Customer: " + booking.getCustomerName() +
                    " | Room: " + booking.getRoomNumber() +
                    " | Check-In: " + booking.getCheckIn() +
                    " | Check-Out: " + booking.getCheckOut() +
                    " | Status: " + booking.getBookingStatus()
            );
        }
    }


    // --------------------------------------------------------
    // Read date safely
    // --------------------------------------------------------

    private LocalDate readDate(Scanner scanner, String message) {

        while (true) {

            try {

                System.out.print(message);

                String input = scanner.nextLine();

                return LocalDate.parse(input);

            } catch (Exception e) {

                System.out.println(
                        "Invalid date. Please use YYYY-MM-DD."
                );
            }
        }
    }
}


// ============================================================
// MAIN CLASS
// ============================================================

public class HotelReservationSystem {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Hotel hotel = new Hotel();

        while (true) {

            System.out.println("\n");
            System.out.println("==========================================");
            System.out.println("       HOTEL RESERVATION SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. View All Bookings");
            System.out.println("6. Exit");
            System.out.println("==========================================");

            System.out.print("Enter your choice: ");

            int choice;

            try {

                choice = scanner.nextInt();
                scanner.nextLine();

            } catch (InputMismatchException e) {

                System.out.println("Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {

                // --------------------------------------------
                // SEARCH ROOMS
                // --------------------------------------------

                case 1:

                    System.out.println(
                            "\n========== SEARCH ROOMS =========="
                    );

                    LocalDate checkIn =
                            readDate(scanner,
                                    "Check-In Date (YYYY-MM-DD): ");

                    LocalDate checkOut =
                            readDate(scanner,
                                    "Check-Out Date (YYYY-MM-DD): ");

                    if (!checkOut.isAfter(checkIn)) {

                        System.out.println(
                                "Check-out must be after check-in."
                        );

                        break;
                    }

                    System.out.println("\nSelect Category:");
                    System.out.println("1. All");
                    System.out.println("2. Standard");
                    System.out.println("3. Deluxe");
                    System.out.println("4. Suite");

                    System.out.print("Enter choice: ");

                    int categoryChoice;

                    try {

                        categoryChoice =
                                scanner.nextInt();

                        scanner.nextLine();

                    } catch (InputMismatchException e) {

                        System.out.println(
                                "Invalid choice."
                        );

                        scanner.nextLine();
                        break;
                    }

                    String category;

                    switch (categoryChoice) {

                        case 1:
                            category = "All";
                            break;

                        case 2:
                            category = "Standard";
                            break;

                        case 3:
                            category = "Deluxe";
                            break;

                        case 4:
                            category = "Suite";
                            break;

                        default:
                            System.out.println(
                                    "Invalid category."
                            );

                            continue;
                    }

                    hotel.searchRooms(
                            category,
                            checkIn,
                            checkOut
                    );

                    break;


                // --------------------------------------------
                // MAKE RESERVATION
                // --------------------------------------------

                case 2:

                    hotel.makeBooking(scanner);

                    break;


                // --------------------------------------------
                // CANCEL RESERVATION
                // --------------------------------------------

                case 3:

                    hotel.cancelBooking(scanner);

                    break;


                // --------------------------------------------
                // VIEW BOOKING
                // --------------------------------------------

                case 4:

                    hotel.viewBooking(scanner);

                    break;


                // --------------------------------------------
                // VIEW ALL BOOKINGS
                // --------------------------------------------

                case 5:

                    hotel.viewAllBookings();

                    break;


                // --------------------------------------------
                // EXIT
                // --------------------------------------------

                case 6:

                    System.out.println(
                            "\nThank you for using the Hotel Reservation System."
                    );

                    scanner.close();
                    return;


                default:

                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }


    // Helper method for reading dates in main
    private static LocalDate readDate(Scanner scanner,
                                      String message) {

        while (true) {

            try {

                System.out.print(message);

                String input = scanner.nextLine();

                return LocalDate.parse(input);

            } catch (Exception e) {

                System.out.println(
                        "Invalid date. Please use YYYY-MM-DD."
                );
            }
        }
    }
}


