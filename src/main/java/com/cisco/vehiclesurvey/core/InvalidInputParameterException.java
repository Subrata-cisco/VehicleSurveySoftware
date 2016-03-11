package com.cisco.vehiclesurvey.core;
/**
 * Exception class for any kind of wrong user input values.
 * @author Subrata Saha
 *
 */
public class InvalidInputParameterException extends
		VehicleDataAnalyserException {

	private static final long serialVersionUID = -1603225236082843190L;
	private long inputParam = 0;
	
	public InvalidInputParameterException(long data,String message){
		super(message, -1);
		this.inputParam = data;
	}
	
	public long getInputParam() {
		return inputParam;
	}
}
