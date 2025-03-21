package main;

import gui.HotelBookingGUI;
import javax.swing.SwingUtilities;
import model.*;

public class Main {
	public static void main(String[] args) {
		// Hotel and Sample data for rooms and price
		Hotel hotel = new Hotel();
		hotel.addRoom(new StandardRoom("101", 100.00));
		hotel.addRoom(new StandardRoom("102", 100.00));
		hotel.addRoom(new StandardRoom("103", 100.00));
		hotel.addRoom(new StandardRoom("104", 100.00));
		hotel.addRoom(new StandardRoom("105", 100.00));
		hotel.addRoom(new DeluxRoom("106", 150.0, 50.0));
		hotel.addRoom(new DeluxRoom("107", 150.0, 50.0));
		hotel.addRoom(new DeluxRoom("108", 150.0, 70.0));
		hotel.addRoom(new DeluxRoom("109", 150.0, 70.0));
		hotel.addRoom(new DeluxRoom("110", 150.0, 70.0));
		
		// load persisted bookings
		hotel.getBookings().addAll(BookingStorage.loadBookings(hotel));
		
		SwingUtilities.invokeLater(() -> {
			HotelBookingGUI gui = new HotelBookingGUI(hotel);
			gui.setVisible(true);
		});
	}
}
