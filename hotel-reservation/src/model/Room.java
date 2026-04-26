package model;

public class Room implements IRoom {

    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        if (roomNumber == null || roomNumber.isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return price == 0;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                ", Price: " + price +
                ", Type: " + roomType;
    }
}