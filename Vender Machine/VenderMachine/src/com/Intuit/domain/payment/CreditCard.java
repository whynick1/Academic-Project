package com.Intuit.domain.payment;

import com.Intuit.domain.payment.abs.Payment;

public class CreditCard implements Payment {
	private Integer creditAvailable;
	public CreditCard(Integer creditAvailable) {
		this.creditAvailable=creditAvailable;
	}

	@Override
	public boolean pay(Double amount) {
		if(Authorize()){
			System.out.println("Authorized");
			//check whether have enough credit to buy
			if(amount<=this.creditAvailable){
				System.out.println("pay success");
				return true;
			}else{
				System.out.println("You don't have enough credit");
				return false;
			}
		}else{
			System.out.println("Authorize Fail");
		}
		return false;
	}
	
	//Authorize credit card
	private boolean Authorize(){
		System.out.println("Authorizing.....");
		
		return true;
	}

}
