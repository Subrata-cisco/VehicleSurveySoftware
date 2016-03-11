package com.cisco.vehiclesurvey.core;
/**
 * Exception class for corrupted data.
 * The generated sensor data file may be corrupted , to intimate the same user this shall be used.
 * @author Subrata Saha
 *
 */
public class CorruptedDataException extends VehicleDataAnalyserException {
	private static final long serialVersionUID = 1L;
	private String corruptedData = null;
	
	public CorruptedDataException(String data,String message){
		super(message, -1);
		this.corruptedData = data;
	}
	
	public String getCorruptedId() {
		return corruptedData;
	}

}
