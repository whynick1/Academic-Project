package com.Intuit.factory.product;

import java.util.UUID;

import com.Intuit.domain.product.OreoCake;
import com.Intuit.domain.product.PepsiCoke;
import com.Intuit.domain.product.abs.Beverage;
import com.Intuit.factory.abs.BeverageFactory;

public class PepsiCokeFactory implements BeverageFactory {

	@Override
	public Beverage getInstance() {
		return new PepsiCoke(UUID.randomUUID().toString(), "Pepsi", 3.00, "300ml", "Pepsi Original");
	}

	@Override
	public Beverage getInstance(String productId,
			String brand, double price, String volume, String name) {
		return new PepsiCoke(productId, brand, price, volume, name);
	}

}
