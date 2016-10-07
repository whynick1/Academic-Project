package com.Intuit.domain.vender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import com.Intuit.domain.motor.abs.Motor;
import com.Intuit.domain.product.abs.Food;
import com.Intuit.domain.vender.abs.Vender;

public class FoodVender extends Vender {
	
	private Map<Integer,Queue<Food>> stock;
	public static final Integer TOTAL_POSITION=10;
	
	public FoodVender(Map<Integer,Queue<Food>> stock, Motor motor) {
		super(motor);
		this.stock=stock;
	}

	@Override
	public boolean stockCheck(Integer position, Integer amount){
		if(stock==null) return false;
		if(stock.containsKey(position)){
			Queue<Food> queue=stock.get(position);
			if(queue.size()-amount>=0) return true;
			System.out.println("Don't have enough product in stock, please decrease your purchase amount");
			return false;
		}else{
			System.out.println("out of stock, please select another product");
			return false;
		}
	}

	@Override
	public void dispense(Integer position, Integer amount) {
		Queue<Food> queue=stock.get(position);
		for(int i=0; i<amount; i++){
			System.out.println(queue.poll().getName()+" dispensed");
		}
	}

	public Map<Integer,Queue<Food>> getStock() {
		// TODO Auto-generated method stub
		return this.stock;
	}

	public void setStock(Map<Integer, Queue<Food>> stock) {
		this.stock = stock;
	}

	
	@Override
	public void showProducts() {
		List<String> list=new ArrayList<String>();
		for(int i=1;i<=this.TOTAL_POSITION;i++){
			if(stock.containsKey(i)){
				String str=i+": "+stock.get(i).peek().getName();
				list.add(str);
			}else{
				list.add(i+": ");
			}
		}
		System.out.println(list.toString());
	}
	
	

}
