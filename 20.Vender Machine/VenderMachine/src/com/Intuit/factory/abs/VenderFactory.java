package com.Intuit.factory.abs;

import com.Intuit.domain.vender.abs.Vender;

public interface VenderFactory extends Factory {

	public <T> Vender getInstance();

}
