package com.cisco.vehiclesurvey.core;
/**
 * Generic exception class.
 * @author Subrata Saha
 *
 */
public class VehicleDataAnalyserException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private int msgID;
	
	public static final int ERROR_DATA_NOT_FOUND = 1; 
	public static final int ERROR_DATA_ANALYSER_NOT_CREATED = 2;  

	public VehicleDataAnalyserException() {
		super();
	}

	public VehicleDataAnalyserException(String message, int msgID) {
		super(message);
		this.msgID = msgID; 
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public String getMessage() {
		return super.getMessage() + " for messageId :" + msgID;
	}

	public int getMessageId() {
		return msgID;
	}

}
