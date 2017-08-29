package com.Intuit.service;

import java.lang.reflect.Field;
import java.util.Queue;
import java.util.Scanner;

import com.Intuit.controller.MotorHandler;
import com.Intuit.domain.motor.abs.Motor;
import com.Intuit.domain.payment.Cash;
import com.Intuit.domain.payment.Coin;
import com.Intuit.domain.payment.CreditCard;
import com.Intuit.domain.vender.FoodVender;
import com.Intuit.domain.vender.abs.Vender;
import com.Intuit.factory.abs.VenderFactory;
import com.Intuit.factory.vender.BeverageVenderFactory;
import com.Intuit.factory.vender.DigitalProductVenderFactory;
import com.Intuit.factory.vender.FoodVenderFactory;
import com.Intuit.factory.vender.MultiProductVenderFactory;

public class VenderServiceImpl implements VenderService {

	@Override
	public boolean payment(Double totalPayment) {
		Scanner sc=new Scanner(System.in);
		boolean flag=true;
		int type=0;
		while(true){
			System.out.println("Please select payment type: 1--Coin, 2--Cash, 3--Credit, 4--Cancel");
			System.out.println("Input the corresponding number:");
			type=sc.nextInt();
			if(type <= 0 || type >4) System.out.println("Incorrect Input, please select again");
			else break;
		}
		if(type==4) flag=false;
		
		double cur=0;
		while(flag){
			if(cur<totalPayment){
				double different=totalPayment-cur;
				System.out.println("Still have "+different+" left to pay");
				int select=0;
				switch(type){
				case 1:
					System.out.println("Please select coin domination: 1--quarter, 2--50 cents, 3--1 dollar, 4--Cancel, 5--Change to Cash payment");
					select=sc.nextInt();
					if (select == 5){
						type = 2;
						break;
					}else if(select==4){
						flag=false;
						break;
					}else if(select <= 0 || select >5){
						System.out.println("Incorrect Input, please select again");
						break;
					}else{
						CoinService service = new CoinServiceImpl();
						Coin coin = service.readCoin(select);
						if (coin.pay(totalPayment)){
							cur += coin.getValue();
						}
					}
					break;
				case 2:
					System.out.println("Please select cash domination: 1--1 dollar, 2--5 dollar, 3--10 dollar, 4--20 dollar, 5--Cancel, 6--Change to Coin payment");
					select=sc.nextInt();
					if (select == 6) {
						type = 1;
						break;
					}else if(select == 5){
						flag = false;
						break;
					}else if(select <= 0 || select >5){
						System.out.println("Incorrect Input, please select again");
						break;
					}else{
						CashService service = new CashServiceImpl();
						Cash cash = service.readCash(select);
						if (cash.pay(totalPayment)){
							cur += cash.getValue();
						}
					}		
					break;
				case 3:
					CreditCardService service=new CreditCardServiceImpl();
					CreditCard card = service.readCard();
					if(card.pay(totalPayment)){
						cur=totalPayment;
					}else{
						flag=false;
					}
					break;	
				case 4:
					System.out.println("You cancel the transaction, thank you");
					return false;
				}
				
			}else{
				if(cur>totalPayment)
					System.out.println("Payment success, please get your product and change");
				else
				    System.out.println("Payment success, please get your product");
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * Get the amount user needs
	 * @return
	 * The Amount of product User needs
	 * */
	public Integer getAmountFromUser(){
		Scanner sc=new Scanner(System.in);
		System.out.print("How many do you need (No more than 3): ");
		int amount=sc.nextInt();
		if(amount>3) return 3;
		return amount;
	}

	@Override
	public Integer getProductPositionFromUser() {
		Scanner sc=new Scanner(System.in);
		System.out.print("Please input the product position (Range from 1 to "+FoodVender.TOTAL_POSITION+"): ");
		int position=sc.nextInt();
		return position;
	}

	@Override
	public void getProducts(Vender vender, Integer position, Integer amount) {
		Motor motor=vender.getMotor();
		MotorHandler handler=new MotorHandler(motor);
		handler.moveToTarget(position);
		
	}

	@Override
	public Vender createVender(int venderType) {
		VenderFactory venderFactory=null;
		Vender vender=null;
		switch(venderType){
		case 1:
			venderFactory=new FoodVenderFactory();
			vender=venderFactory.getInstance();
			break;
		case 2:
			venderFactory=new BeverageVenderFactory();
			vender=venderFactory.getInstance();
			break;
		case 3:
			venderFactory=new DigitalProductVenderFactory();
			vender=venderFactory.getInstance();
			break;
		case 4:
			venderFactory=new MultiProductVenderFactory();
			vender=venderFactory.getInstance();
			break;
		case 5:
			System.out.println("Quit");
			System.exit(-1);
			break;
		default:
			System.out.println("Input error, System quit");
			System.exit(-1);
			break;
		}
		return vender;
	}

	@Override
	public <T> double calculateTotalPayment(Integer position, Integer amount, Vender vender) {
		try {
			@SuppressWarnings("unchecked")
			Queue<T> queue=(Queue<T>) vender.getStock().get(position);
			T t=queue.poll();
			String name=t.getClass().getSimpleName();
			Field declaredField = t.getClass().getSuperclass().getSuperclass().getDeclaredField("price");
			declaredField.setAccessible(true);
			double price=(double) declaredField.get(t);
			double totalPayment=price*amount;
			return totalPayment;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
