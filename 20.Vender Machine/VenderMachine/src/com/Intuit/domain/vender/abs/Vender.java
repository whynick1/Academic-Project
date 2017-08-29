package com.Intuit.domain.vender.abs;

import java.util.Map;

import com.Intuit.domain.motor.abs.Motor;

public abstract class Vender{
	protected Motor motor;
	protected Double income; //How much money this vender earned science last settlement
	
	protected Vender(Motor motor) {
		this.motor = motor;
		this.income = 0.0;
		
	}
	
	/**
	 * Check whether the product is in stock
	 * @param position
	 * Product position
	 * @return
	 * Whether the product in stock
	 * */
	public abstract boolean stockCheck(Integer position, Integer amount);
	
	
	/**
	 * Dispense the product
	 * @param position
	 * Product position in vender
	 * */
	public abstract void dispense(Integer position, Integer amount);
	
	
	public abstract <K,V> Map<K,V> getStock();
	
	public Motor getMotor(){
		return this.motor;
	}

	public void setMotor(Motor motor){
		this.motor=motor;
	}

	public Double getIncome(){
		return this.income;
	}

	/**
	 * Rest the value of income to 0
	 * */
	public void resetIncome(){
		this.income=0.0;
	}
	
	/**
	 * Add the new income to total income
	 * */
	public void incomeAdd(double add){
		this.income+=add;
	}
	
	/**
	 * Show products in this vender
	 * */
	public abstract void showProducts();
	
	
	
}
