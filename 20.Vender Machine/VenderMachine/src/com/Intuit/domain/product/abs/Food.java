package com.Intuit.domain.product.abs;

public class Food extends Product {
	protected Integer size;
	public static final Integer SMALL_SIZE=1; 
	public static final Integer NORMAL_SIZE=2; 
	public static final Integer LARGE_SIZE=3; 
	public static final Integer EXTRA_LARGE_SIZE=4; 
	
	protected Food(String productId, String brand,
			double price,Integer size,String name) {
		super(productId, brand, price, name);
		this.size=size;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
}
