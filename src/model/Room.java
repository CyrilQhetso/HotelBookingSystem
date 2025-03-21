package model;

public abstract class Room {
	protected String roomNumber;
	protected boolean isBooked;
	
	public Room(String roomNumber) {
		this.roomNumber = roomNumber;
		this.isBooked = false;
	}
	
	public boolean isBooked() {
		return isBooked;
	}
	
	public void book() {
		this.isBooked = true;
	}
	
	public void cancelBooking() {
		this.isBooked = false;
	}
	
	public abstract double calculatePrice();
	
	public String getRoomNumber() {
		return roomNumber;
	}
}
