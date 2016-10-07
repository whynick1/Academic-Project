package com.Intuit.service;

import com.Intuit.domain.payment.Coin;

public class CoinServiceImpl implements CoinService{
	//1--quarter, 2--50 cents, 3--1 dollar
	@Override
	public Coin readCoin(int select) {
		if(select==1){
			return new Coin(0.25);
		}else if(select==2){
			return new Coin(0.50);
		}else if(select==3){
			return new Coin(1.00);
		}else{
			System.out.println("Error: Input Coin value equals: 0");
			return new Coin(0.00);
		}
	}
}
