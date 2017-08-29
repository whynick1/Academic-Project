package com.Intuit.service;

import com.Intuit.domain.payment.CreditCard;

public class CreditCardServiceImpl implements CreditCardService{

	@Override
	public CreditCard readCard() {
		//set available credit limitation Integer.MAX_VALUE
		return new CreditCard(Integer.MAX_VALUE);
	}

}
