package com.Intuit.domain.product.abs;

public abstract class Product {
	
	protected String productId;
	protected String name;
	protected String brand;
	protected double price;
	
	protected Product(String productId, String brand,
			double price,String name) {
		productId = productId;
		this.brand = brand;
		this.price = price;
		this.name=name;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		productId = productId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
