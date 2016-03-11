package com.cisco.vehiclesurvey.core;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * VehicleData data transfer object having required vehicle information.
 * VehicleTiming object here has no meaning with out VehicleData then is it required
 * to get it created in other rather being here.We will check.
 * 
 * @author Subrata Saha
 *
 */
public class VehicleData {
	
	public static enum DIRECTION {NORTH,SOUTH};
	
	/** This payload shall contain all the transaction i.e 2 for North bound (e.g A268981-A269123) and 4 for South bound vehicle pass over.**/
	private LinkedList<String> transactions = new LinkedList<>();
	
	/** Concluded direction for each transaction **/
	private DIRECTION direction = DIRECTION.NORTH;
	
	private VehicleTiming vehicleTiming = null;

	public String getTransactions() {
		Iterator<String> it = transactions.iterator();
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()){
			sb.append(it.next()).append(Constants.STRING_SEPARATOR);
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	public void setTransactions(String transaction) {
		transactions.addFirst(transaction);
	}

	public DIRECTION getDirection() {
		return direction;
	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}

	public VehicleTiming getVehicleTiming() {
		return vehicleTiming;
	}

	public void setVehicleTiming(VehicleTiming vehicleTiming) {
		this.vehicleTiming = vehicleTiming;
	}

}
