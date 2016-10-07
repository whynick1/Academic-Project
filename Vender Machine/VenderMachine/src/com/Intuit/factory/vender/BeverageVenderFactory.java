package com.Intuit.factory.vender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.Intuit.domain.motor.Type1;
import com.Intuit.domain.motor.abs.Motor;
import com.Intuit.domain.product.abs.Beverage;
import com.Intuit.domain.vender.BeverageVender;
import com.Intuit.domain.vender.abs.Vender;
import com.Intuit.factory.abs.BeverageFactory;
import com.Intuit.factory.abs.VenderFactory;
import com.Intuit.factory.product.PepsiCokeFactory;

public class BeverageVenderFactory implements VenderFactory {

	@Override
	public Vender getInstance() {
		Motor m=new Type1();
		Map<Integer, Queue<Beverage>> stock=new HashMap<Integer, Queue<Beverage>>();
		Queue<Beverage> queue=new LinkedList<Beverage>();
		BeverageFactory factory=new PepsiCokeFactory();
		for(int i=0;i<10;i++){
			queue.offer(factory.getInstance());
		}
		stock.put(1,queue);
		return new BeverageVender(stock,m);
	}

}
