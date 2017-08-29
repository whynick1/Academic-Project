package com.Intuit.factory.abs;

import com.Intuit.domain.product.abs.Food;

public interface FoodFactory extends Factory {

	public Food getInstance();
	
	public Food getInstance(String productId, String brand,
			double price,Integer size,String name);

}
