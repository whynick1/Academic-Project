package com.Intuit.factory.vender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.Intuit.domain.motor.Type1;
import com.Intuit.domain.motor.abs.Motor;
import com.Intuit.domain.product.abs.Beverage;
import com.Intuit.domain.product.abs.Food;
import com.Intuit.domain.vender.FoodVender;
import com.Intuit.domain.vender.abs.Vender;
import com.Intuit.factory.abs.FoodFactory;
import com.Intuit.factory.abs.VenderFactory;
import com.Intuit.factory.product.LaysChipFactory;
import com.Intuit.factory.product.OreoCakeFactory;

public class FoodVenderFactory implements VenderFactory {

	public FoodVenderFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vender getInstance() {
		Motor m=new Type1();
		Map<Integer, Queue<Food>> stock=new HashMap<Integer, Queue<Food>>();
		Queue<Food> queue1=new LinkedList<Food>();
		Queue<Food> queue2=new LinkedList<Food>();
		FoodFactory factory1=new OreoCakeFactory();
		FoodFactory factory2=new LaysChipFactory();
		for(int i=0;i<10;i++){
			queue1.offer(factory1.getInstance());
			queue2.offer(factory2.getInstance());
		}
		stock.put(1, queue1);
		stock.put(2, queue2);
		
		return new FoodVender(stock,m);
	}

}
