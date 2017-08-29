package com.Intuit.domain.vender;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import com.Intuit.domain.motor.abs.Motor;
import com.Intuit.domain.product.abs.Beverage;
import com.Intuit.domain.product.abs.DigitalProduct;
import com.Intuit.domain.product.abs.Food;
import com.Intuit.domain.vender.abs.Vender;

public class MultiFunctionVender <T> extends Vender{
	private Map<Integer,Queue<T>> stock;
	public static final Integer TOTAL_POSITION=10;
	
	public MultiFunctionVender(Map<Integer,Queue<T>> stock, Motor motor) {
		super(motor);
		this.stock=stock;
	}

	@Override
	public boolean stockCheck(Integer position, Integer amount){
		if(stock==null) return false;
		if(stock.containsKey(position)){
			Queue<?> queue=stock.get(position);
			if(queue.size()-amount>=0) return true;
			System.out.println("Don't have enough product in stock, please decrease your purchase amount");
			return false;
		}else{
			System.out.println("out of stock, please select another product");
			return false;
		}
	}

	public void dispense(Integer position, Integer amount) {
		try {
			Queue<T> queue=(Queue<T>) stock.get(position);
			for(int i=0; i<amount; i++){
				T t=queue.poll();
				Field declaredField = t.getClass().getSuperclass().getSuperclass().getDeclaredField("name");
				declaredField.setAccessible(true);
				String name=(String) declaredField.get(t);
				System.out.println(name+" dispensed");	
			}
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
	}

	public Map<Integer,Queue<T>> getStock() {
		// TODO Auto-generated method stub
		return this.stock;
	}

	public void setStock(Map<Integer, Queue<T>> stock) {
		this.stock = stock;
	}

	@Override
	public void showProducts() {
		List<String> list=new ArrayList<String>();
		try {
			for(int i=1;i<=this.TOTAL_POSITION;i++){
				if(stock.containsKey(i)){
					T t=stock.get(i).peek();
					Field declaredField = t.getClass().getSuperclass().getSuperclass().getDeclaredField("name");
					declaredField.setAccessible(true);
					String name=(String) declaredField.get(t);
					String str=i+": "+name;
					list.add(str);
				}else{
					list.add(i+":");
				}
			}
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
		System.out.println(list.toString());
	}
	
}
