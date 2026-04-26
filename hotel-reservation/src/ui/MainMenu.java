package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void start() {
        while (true) {
            System.out.println("\nMain Menu");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.start();
                    break;
                case "5":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    // ==========================
    // FIND & RESERVE ROOM
    // ==========================
    private static void findAndReserveRoom() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("Enter check-in date (dd/MM/yyyy):");
            Date checkInDate = sdf.parse(scanner.nextLine());

            System.out.println("Enter check-out date (dd/MM/yyyy):");
            Date checkOutDate = sdf.parse(scanner.nextLine());

            // 🔥 DATE VALIDATION (REQUIRED)
            Date today = new Date();

            if (checkInDate.before(today)) {
                System.out.println("Check-in date cannot be in the past.");
                return;
            }

            if (!checkOutDate.after(checkInDate)) {
                System.out.println("Check-out must be after check-in.");
                return;
            }

            Collection<IRoom> rooms =
                    hotelResource.findARoom(checkInDate, checkOutDate);

            // ==========================
            // 🔥 RECOMMENDED ROOMS FIX
            // ==========================
            if (rooms.isEmpty()) {
                System.out.println("No rooms available for selected dates.");

                Calendar calendar = Calendar.getInstance();

                calendar.setTime(checkInDate);
                calendar.add(Calendar.DATE, 7);
                Date newCheckIn = calendar.getTime();

                calendar.setTime(checkOutDate);
                calendar.add(Calendar.DATE, 7);
                Date newCheckOut = calendar.getTime();

                System.out.println("\nSearching for recommended rooms...");
                System.out.println("New Dates: " + newCheckIn + " to " + newCheckOut);

                Collection<IRoom> recommendedRooms =
                        hotelResource.findARoom(newCheckIn, newCheckOut);

                if (recommendedRooms.isEmpty()) {
                    System.out.println("No rooms available even after recommendation.");
                    return;
                }

                System.out.println("\nRecommended Rooms:");
                for (IRoom room : recommendedRooms) {
                    System.out.println(room);
                }

                System.out.println("\nWould you like to book a recommended room? (y/n)");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("y")) {
                    bookRoomFlow(newCheckIn, newCheckOut);
                }

            } else {
                System.out.println("\nAvailable Rooms:");
                for (IRoom room : rooms) {
                    System.out.println(room);
                }

                System.out.println("\nWould you like to book a room? (y/n)");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("y")) {
                    bookRoomFlow(checkInDate, checkOutDate);
                }
            }

        } catch (Exception e) {
            System.out.println("Invalid date format. Use dd/MM/yyyy");
        }
    }

    // ==========================
    // BOOK ROOM (REUSABLE)
    // ==========================
    private static void bookRoomFlow(Date checkIn, Date checkOut) {

        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        Customer customer = hotelResource.getCustomer(email);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();

        IRoom room = hotelResource.getRoom(roomNumber);

        if (room == null) {
            System.out.println("Invalid room.");
            return;
        }

        try {
            Reservation reservation = hotelResource.bookARoom(
                    email,
                    room,
                    checkIn,
                    checkOut
            );

            System.out.println("\nReservation successful!");
            System.out.println(reservation);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // SEE RESERVATIONS
    // ==========================
    private static void seeReservations() {
        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        try {
            Collection<Reservation> reservations =
                    hotelResource.getCustomersReservations(email);

            if (reservations.isEmpty()) {
                System.out.println("No reservations found.");
                return;
            }

            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // CREATE ACCOUNT
    // ==========================
    private static void createAccount() {
        System.out.println("Enter email:");
        String email = scanner.nextLine();

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}