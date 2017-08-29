package com.Intuit.factory.Motor;

import com.Intuit.domain.motor.Type2;
import com.Intuit.domain.motor.abs.Motor;
import com.Intuit.factory.abs.MotorFactory;

public class Type2Factory implements MotorFactory {

	@Override
	public Motor getInstance() {
		return new Type2();
	}

}
