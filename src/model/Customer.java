package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String name;
	private List<Booking> bookings;
	
	public Customer(String name) {
		this.name = name;
		bookings =  new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addBooking(Booking booking) {
		bookings.add(booking);
	}
	
	public void cancelBooking(Booking booking) {
		bookings.remove(booking);
	}
	
	public List<Booking> getBookings() {
		return bookings;
	}
}
