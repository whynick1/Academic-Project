package com.Intuit.controller;

import com.Intuit.domain.motor.abs.Motor;

public class MotorHandler {

	private Motor motor;
	public MotorHandler(Motor motor) {
		this.motor=motor;
	}
	
	public void moveToTarget(Integer targetPosition){
		System.out.println("Motor moving to target position.....");
		System.out.println("Get the product...");
		System.out.println("Motor moving back to start position.....");
	}

}
