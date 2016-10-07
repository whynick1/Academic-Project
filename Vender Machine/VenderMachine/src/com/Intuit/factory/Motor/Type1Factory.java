package com.Intuit.factory.Motor;

import com.Intuit.domain.motor.Type1;
import com.Intuit.domain.motor.abs.Motor;
import com.Intuit.factory.abs.MotorFactory;

public class Type1Factory implements MotorFactory {

	@Override
	public Motor getInstance() {
		return new Type1();
	}

}
