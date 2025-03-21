package model;

public class DeluxRoom extends Room{
	private double basePrice;
	private double extraCharge;
	
	public DeluxRoom(String roomNumber, double basePrice, double extraCharge) {
		super(roomNumber);
		this.basePrice = basePrice;
		this.extraCharge = extraCharge;
	}
	
	@Override
	public double calculatePrice() {
		return basePrice + extraCharge;
	}
}
