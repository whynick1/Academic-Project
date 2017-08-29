package com.Intuit.domain.payment;

import com.Intuit.domain.payment.abs.Payment;

public class Coin implements Payment {
	private Double value;
	public Coin(Double value) {
		this.value=value;
	}
	
	@Override
	public boolean pay(Double amount) {
		if(Authorize()){
			System.out.println("Authorized");
			return true;
		}else{
			System.out.println("Authorize Fail");
			return false;
		}
	}
	
	public Double getValue() {
		return value;
	}
	
	//Authorize cash 
	private boolean Authorize(){
		System.out.println("Authorizing.....");
		return true;
	}

}