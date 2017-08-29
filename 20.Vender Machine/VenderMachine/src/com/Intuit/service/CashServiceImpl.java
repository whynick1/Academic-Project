package com.Intuit.service;

import com.Intuit.domain.payment.Cash;

public class CashServiceImpl implements CashService {
	//1--1 dollar, 2--5 dollar, 3--10 dollar, 4--20 dollar
	@Override
	public Cash readCash(int select) {
		if(select==1){
			return new Cash(1);
		}else if(select==2){
			return new Cash(5);
		}else if(select==3){
			return new Cash(10);
		}else if(select==4){
			return new Cash(20);
		}else{
			System.out.println("Error: Input Cash value equals: 0");
			return new Cash(0);
		}
	}
}
