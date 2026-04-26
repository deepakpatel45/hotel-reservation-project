package model;

import java.util.Date;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }

        if (!checkOutDate.after(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation:\n" +
                "Customer: " + customer + "\n" +
                "Room: " + room + "\n" +
                "Check-in: " + checkInDate + "\n" +
                "Check-out: " + checkOutDate;
    }
}