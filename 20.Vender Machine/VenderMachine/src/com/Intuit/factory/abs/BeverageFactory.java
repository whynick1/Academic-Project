package com.Intuit.factory.abs;

import com.Intuit.domain.product.abs.Beverage;

public interface BeverageFactory extends Factory {

	public Beverage getInstance();
	
	public Beverage getInstance(String productId, String brand,
			double price,String volume,String name);
	

}
