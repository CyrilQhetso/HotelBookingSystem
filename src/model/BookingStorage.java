package model;

import java.io.*;
import java.util.List;

public class BookingStorage {
    private static final String FILE_NAME = "bookings.txt";

    public static void saveBookings(List<Booking> bookings) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Booking booking : bookings) {
                // Format: roomNumber,customerName,bookingTime,cancellationTime
                String line = booking.getRoom().getRoomNumber() + "," +
                              booking.getCustomer().getName() + "," +
                              booking.getBookingTime().toString() + "," +
                              (booking.getCancellationTime() != null ? booking.getCancellationTime().toString() : "");
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Booking> loadBookings(Hotel hotel) {
        List<Booking> bookings = new java.util.ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return bookings;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length < 3) continue;
                String roomNumber = parts[0];
                String customerName = parts[1];
                String bookingTimeStr = parts[2];
                String cancellationTimeStr = parts.length >= 4 ? parts[3] : "";
                Room room = hotel.findRoomByNumber(roomNumber);
                if (room != null) {
                    java.time.LocalDateTime bookingTime = java.time.LocalDateTime.parse(bookingTimeStr);
                    java.time.LocalDateTime cancellationTime = cancellationTimeStr.isEmpty() ? null : java.time.LocalDateTime.parse(cancellationTimeStr);
                    Customer customer = new Customer(customerName);
                    Booking booking = new Booking(room, customer, bookingTime, cancellationTime);
                    bookings.add(booking);
                    // If the booking was active (not cancelled) then mark the room as booked.
                    if (cancellationTime == null) {
                        room.book();
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}