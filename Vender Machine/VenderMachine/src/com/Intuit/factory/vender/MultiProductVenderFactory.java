package com.Intuit.factory.vender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.Intuit.domain.motor.Type1;
import com.Intuit.domain.motor.abs.Motor;
import com.Intuit.domain.product.abs.Beverage;
import com.Intuit.domain.product.abs.DigitalProduct;
import com.Intuit.domain.product.abs.Food;
import com.Intuit.domain.vender.MultiFunctionVender;
import com.Intuit.domain.vender.abs.Vender;
import com.Intuit.factory.abs.BeverageFactory;
import com.Intuit.factory.abs.DigitalProductFactory;
import com.Intuit.factory.abs.FoodFactory;
import com.Intuit.factory.abs.VenderFactory;
import com.Intuit.factory.product.IPhoneFactory;
import com.Intuit.factory.product.LaysChipFactory;
import com.Intuit.factory.product.OreoCakeFactory;
import com.Intuit.factory.product.PepsiCokeFactory;

public class MultiProductVenderFactory implements VenderFactory {

	@Override
	public <T> Vender getInstance() {
		Motor m=new Type1();
		Map<Integer, Queue<T>> stock=new HashMap<Integer, Queue<T>>();
		Queue<T> queue1=new LinkedList<T>();
		Queue<T> queue2=new LinkedList<T>();
		Queue<T> queue3=new LinkedList<T>();
		FoodFactory factory1=new OreoCakeFactory();
		BeverageFactory factory2=new PepsiCokeFactory();
		DigitalProductFactory factory3=new IPhoneFactory();
		for(int i=0;i<5;i++){
			queue1.offer((T) factory1.getInstance());
			queue2.offer((T) factory2.getInstance());
			queue3.offer((T) factory3.getInstance());
		}
		stock.put(4,queue1);
		stock.put(6,queue2);
		stock.put(9,queue3);
		return new MultiFunctionVender(stock,m);
	}

}
