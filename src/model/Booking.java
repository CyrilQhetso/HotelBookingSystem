package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
	private Room room;
	private Customer customer;
	private LocalDateTime bookingTime;
	private LocalDateTime cancellationTime;
	
	// Contructor for new bookings
	public Booking(Room room, Customer customer) {
		this.room = room;
		this.customer = customer;
		this.bookingTime = LocalDateTime.now();
		this.cancellationTime = null;
	}
	
	// Constructor to load bookings
	public Booking(Room room, Customer customer, LocalDateTime bookingTime, LocalDateTime cancellationTime) {
		this.room = room;
		this.customer = customer;
		this.bookingTime = bookingTime;
		this.cancellationTime = cancellationTime;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public LocalDateTime getBookingTime() {
		return bookingTime;
	}
	
	public LocalDateTime getCancellationTime() {
		return cancellationTime;
	}
	
	public void setCancellationTime(LocalDateTime cancellationTime) {
		this.cancellationTime = cancellationTime;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String result = "Room " + room.getRoomNumber() + " booked by " + customer.getName() + " at " + bookingTime.format(formatter); 
		if (cancellationTime != null) {
			result += " | Cancelled at" + cancellationTime.format(formatter);
		}
		return result;
	}
}
