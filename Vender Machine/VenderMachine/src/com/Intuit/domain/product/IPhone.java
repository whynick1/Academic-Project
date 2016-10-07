package com.Intuit.domain.product;

import com.Intuit.domain.product.abs.DigitalProduct;

public class IPhone extends DigitalProduct {

	public IPhone(String productId, String brand,
			double price, String type, String name) {
		super(productId, brand, price, type, name);
	}

}
