package com.Intuit.service;

import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import com.Intuit.domain.product.abs.Product;
import com.Intuit.domain.vender.abs.Vender;

public interface VenderService {
	/**
	 * Get product position from user Input
	 * @return
	 * Product position
	 * */
	public Integer getProductPositionFromUser();
	
	/**
	 * Get the amount user needs
	 * @return
	 * The Amount of product User needs
	 * */
	public Integer getAmountFromUser();
	
	/**
	 * Get user payment
	 * @param totalPayment
	 * Total payment
	 * @return
	 * Whether payment success
	 * */
	public boolean payment(Double totalPayment);
	
	/**
	 * Get products user selected
	 * @param vender
	 * The vender user is using
	 * @param position
	 * The product position in vender
	 * */
	public void getProducts(Vender vender, Integer position, Integer amount);
	
	/**
	 * Create vender by type
	 * @param venderType
	 * The vender type
	 * @return
	 * Vender Object
	 * */
	public Vender createVender(int venderType);
	
	/**
	 * Calculate total amount to pay
	 * @param position
	 * The product position in the vender
	 * @param amount
	 * The product amount
	 * @param vender
	 * The vender user is using
	 * @return
	 * total payment
	 * */
	public <T> double calculateTotalPayment(Integer position, Integer amount, Vender vender);
}
