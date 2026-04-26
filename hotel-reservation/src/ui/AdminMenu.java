package ui;

import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    seeAllCustomers();
                    break;

                case "2":
                    seeAllRooms();
                    break;

                case "3":
                    seeAllReservations();
                    break;

                case "4":
                    addRoom(scanner);
                    break;

                case "5":
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // -------------------------
    // OPTIONS
    // -------------------------

    private static void seeAllCustomers() {
        Collection<?> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Object customer : customers) {
            System.out.println(customer);
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }

        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }

    private static void seeAllReservations() {
        adminResource.printAllReservations(); // ✅ FIXED METHOD NAME
    }

    // -------------------------
    // ADD ROOM (FIXED)
    // -------------------------

    private static void addRoom(Scanner scanner) {

        while (true) {

            System.out.println("Enter room number:");
            String roomNumber = scanner.nextLine();

            if (roomNumber.trim().isEmpty()) {
                System.out.println("Room number cannot be empty!");
                continue;
            }

            // ✅ DUPLICATE CHECK
            if (adminResource.getRoom(roomNumber) != null) {
                System.out.println("Room already exists!");
                continue;
            }

            // PRICE
            double price;
            try {
                System.out.println("Enter price:");
                price = Double.parseDouble(scanner.nextLine());

                if (price < 0) {
                    System.out.println("Price cannot be negative!");
                    continue;
                }

            } catch (Exception e) {
                System.out.println("Invalid price! Enter a valid number.");
                continue;
            }

            // ROOM TYPE (STRICT VALIDATION)
            System.out.println("Enter room type (1 for SINGLE, 2 for DOUBLE):");
            String typeInput = scanner.nextLine();

            RoomType roomType;

            if ("1".equals(typeInput)) {
                roomType = RoomType.SINGLE;
            } else if ("2".equals(typeInput)) {
                roomType = RoomType.DOUBLE;
            } else {
                System.out.println("Invalid! Enter valid room type (1 or 2)");
                continue;
            }

            IRoom room = new Room(roomNumber, price, roomType);

            // ✅ API EXPECTS LIST
            List<IRoom> roomList = new ArrayList<>();
            roomList.add(room);

            adminResource.addRoom(roomList);

            System.out.println("Room added successfully!");

            // ADD MORE?
            System.out.println("Add another room? (y/n)");
            String again = scanner.nextLine();

            if (!"y".equalsIgnoreCase(again)) {
                break;
            }
        }
    }
}