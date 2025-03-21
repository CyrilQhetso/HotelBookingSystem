package model;

public class StandardRoom extends Room {
	private double basePrice;
	
	public StandardRoom(String roomNumber, double basePrice) {
		super(roomNumber);
		this.basePrice = basePrice;
	}
	
	@Override
	public double calculatePrice() {
		return basePrice;
	}
}
