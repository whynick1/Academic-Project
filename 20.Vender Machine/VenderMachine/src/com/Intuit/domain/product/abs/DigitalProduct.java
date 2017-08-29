package com.Intuit.domain.product.abs;

public class DigitalProduct extends Product {
	protected String type;

	protected DigitalProduct(String productId, String brand,
			double price, String type, String name) {
		super(productId, brand, price, name);
		this.type=type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
