package com.Intuit.factory.product;

import java.util.UUID;

import com.Intuit.domain.product.LaysChips;
import com.Intuit.domain.product.OreoCake;
import com.Intuit.domain.product.abs.Food;
import com.Intuit.factory.abs.FoodFactory;

public class LaysChipFactory implements FoodFactory {

	@Override
	public Food getInstance() {
		return new LaysChips(UUID.randomUUID().toString(), "Lay's", 5.00, OreoCake.LARGE_SIZE, "Lay's Chip Classic");
	}

	@Override
	public Food getInstance(String productId, String brand, double price, Integer size, String name) {
		return new LaysChips(productId, brand, price, size, name);
	}
}

