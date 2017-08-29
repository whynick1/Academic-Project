package com.Intuit.domain.product.abs;

public class Beverage extends Product{
	protected String volume;

	public Beverage(String productId, String brand,
			double price,String volume ,String name) {
		super(productId, brand, price, name);
		this.volume=volume;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	
}
