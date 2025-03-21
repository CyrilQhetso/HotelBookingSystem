package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Room> rooms;
    private List<Booking> bookings;

    public Hotel() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isBooked()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    
    public Room findRoomByNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    public boolean bookRoom(String roomNumber, Customer customer) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber) && !room.isBooked()) {
                room.book();
                Booking booking = new Booking(room, customer);
                bookings.add(booking);
                BookingStorage.saveBookings(bookings);
                return true;
            }
        }
        return false;
    }

    // Instead of removing the booking, mark it as cancelled.
    public boolean cancelBooking(String roomNumber, Customer customer) {
        Booking bookingToCancel = null;
        for (Booking booking : bookings) {
            if (booking.getRoom().getRoomNumber().equals(roomNumber) &&
                booking.getCustomer().getName().equalsIgnoreCase(customer.getName()) &&
                booking.getCancellationTime() == null) { // Only cancel if not already cancelled.
                bookingToCancel = booking;
                break;
            }
        }
        if (bookingToCancel != null) {
            bookingToCancel.getRoom().cancelBooking(); // Free the room.
            bookingToCancel.setCancellationTime(LocalDateTime.now());
            BookingStorage.saveBookings(bookings);
            return true;
        }
        return false;
    }
    
    public List<Booking> getBookings() {
        return bookings;
    }
}