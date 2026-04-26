package service;

import model.*;

import java.util.*;

public class ReservationService {

    private static ReservationService instance = null;

    // Store rooms (unique room number)
    private final Map<String, IRoom> rooms = new HashMap<>();

    // Store reservations
    private final List<Reservation> reservations = new ArrayList<>();

    // Singleton pattern
    private ReservationService() {}

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    // -------------------------------
    // ROOM METHODS
    // -------------------------------

    public boolean addRoom(IRoom room) {
        if (room == null || room.getRoomNumber() == null || room.getRoomNumber().trim().isEmpty()) {
            System.out.println("Invalid room number!");
            return false;
        }

        if (rooms.containsKey(room.getRoomNumber())) {
            System.out.println("Room already exists!");
            return false;
        }

        rooms.put(room.getRoomNumber(), room);
        return true;
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    // -------------------------------
    // RESERVATION METHODS
    // -------------------------------

    public Reservation reserveARoom(Customer customer, IRoom room,
                                    Date checkInDate, Date checkOutDate) {

        // Prevent double booking
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room) &&
                    reservation.getCheckInDate().before(checkOutDate) &&
                    reservation.getCheckOutDate().after(checkInDate)) {

                throw new IllegalArgumentException("Room is already booked for these dates.");
            }
        }

        Reservation newReservation =
                new Reservation(customer, room, checkInDate, checkOutDate);

        reservations.add(newReservation);
        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {

        List<IRoom> availableRooms = new ArrayList<>(rooms.values());

        for (Reservation reservation : reservations) {
            if (reservation.getCheckInDate().before(checkOutDate) &&
                    reservation.getCheckOutDate().after(checkInDate)) {

                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {

        List<Reservation> result = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                result.add(reservation);
            }
        }

        return result;
    }

    public void printAllReservation() {

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}