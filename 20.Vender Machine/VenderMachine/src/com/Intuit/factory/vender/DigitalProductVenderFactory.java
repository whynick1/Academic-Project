package com.Intuit.factory.vender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.Intuit.domain.motor.Type1;
import com.Intuit.domain.motor.abs.Motor;
import com.Intuit.domain.product.abs.DigitalProduct;
import com.Intuit.domain.vender.DigitalProductVender;
import com.Intuit.domain.vender.abs.Vender;
import com.Intuit.factory.abs.DigitalProductFactory;
import com.Intuit.factory.abs.VenderFactory;
import com.Intuit.factory.product.IPhoneFactory;

public class DigitalProductVenderFactory implements VenderFactory {

	@Override
	public Vender getInstance() {
		Motor m=new Type1();
		Map<Integer, Queue<DigitalProduct>> stock=new HashMap<Integer, Queue<DigitalProduct>>();
		Queue<DigitalProduct> queue=new LinkedList<DigitalProduct>();
		DigitalProductFactory factory=new IPhoneFactory();
		for(int i=0;i<10;i++){
			queue.offer(factory.getInstance());
		}
		stock.put(3, queue);
		return new DigitalProductVender(stock,m);
	}

}
