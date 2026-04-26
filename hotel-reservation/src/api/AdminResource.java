package api;

import model.*;
import service.*;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource instance = null;

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    // -------------------------
    // CUSTOMERS
    // -------------------------

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // -------------------------
    // ROOMS
    // -------------------------

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    // ✅ ADD THIS METHOD (FIX ERROR)
    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    // -------------------------
    // RESERVATIONS
    // -------------------------

    // ✅ ADD THIS METHOD (FIX ERROR)
    public void printAllReservations() {
        reservationService.printAllReservation();
    }
}