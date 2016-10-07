package com.Intuit.factory.product;

import java.util.UUID;

import com.Intuit.domain.product.IPhone;
import com.Intuit.domain.product.LaysChips;
import com.Intuit.domain.product.OreoCake;
import com.Intuit.domain.product.abs.DigitalProduct;
import com.Intuit.domain.product.abs.Food;
import com.Intuit.factory.abs.DigitalProductFactory;

public class IPhoneFactory implements DigitalProductFactory {

	@Override
	public DigitalProduct getInstance() {
		return new IPhone(UUID.randomUUID().toString(), "Apple", 1200.00, "6 plus", "iPhone 6 plus");
	}

	@Override
	public DigitalProduct getInstance(String productId,
			String brand, double price, String type, String name) {
		return new IPhone(productId, brand, price, type, name);
	}

}
