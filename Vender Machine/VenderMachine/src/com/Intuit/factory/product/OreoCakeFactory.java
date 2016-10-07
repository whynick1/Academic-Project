package com.Intuit.factory.product;

import java.util.UUID;

import com.Intuit.domain.product.LaysChips;
import com.Intuit.domain.product.OreoCake;
import com.Intuit.domain.product.abs.Food;
import com.Intuit.factory.abs.FoodFactory;

public class OreoCakeFactory implements FoodFactory {

	@Override
	public Food getInstance() {
		return new OreoCake(UUID.randomUUID().toString(), "Orio", 5.00, OreoCake.NORMAL_SIZE, "Orio cake");
	}

	@Override
	public Food getInstance(String productId, String brand,
			double price, Integer size, String name) {
		return new LaysChips(productId, brand, price, size, name);
	}

}
