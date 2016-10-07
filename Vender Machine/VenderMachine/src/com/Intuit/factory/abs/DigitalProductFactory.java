package com.Intuit.factory.abs;

import com.Intuit.domain.product.abs.DigitalProduct;

public interface DigitalProductFactory extends Factory {

	public DigitalProduct getInstance();
	
	public DigitalProduct getInstance(String productId, String brand,
			double price, String type, String name);

}
