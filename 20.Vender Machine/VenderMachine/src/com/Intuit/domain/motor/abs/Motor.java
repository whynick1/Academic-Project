package com.Intuit.domain.motor.abs;

public interface Motor {

	/**
	 * start the motor
	 * */
	public void start();
	
	/**
	 * stop the motor
	 * */
	public void stop();
	
	/**
	 * move horizontal
	 * @param direction
	 * Direction to move on X-axis, 0 is left, 1 is right
	 * */
	public void XMove(int direction);
	
	/**
	 * move vertical
	 * @param direction
	 * Direction to move on Y-axis, 0 is up, 1 is down
	 * */
	public void YMove(int direction);
}
