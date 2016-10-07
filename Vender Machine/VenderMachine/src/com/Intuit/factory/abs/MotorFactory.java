package com.Intuit.factory.abs;

import com.Intuit.domain.motor.abs.Motor;

public interface MotorFactory extends Factory {

	public Motor getInstance();

}
