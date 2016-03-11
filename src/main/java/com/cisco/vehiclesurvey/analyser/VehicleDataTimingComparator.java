package com.cisco.vehiclesurvey.analyser;

import java.util.Comparator;

import com.cisco.vehiclesurvey.core.VehicleData;
/**
 * To check the timing between different VehicleData and sort them according to VehicleTiming object.
 * 
 * @author Subrata Saha
 *
 */
public class VehicleDataTimingComparator implements Comparator<VehicleData> {
	@Override
	public int compare(VehicleData firstTiming, VehicleData secondTiming) {
		return firstTiming.getVehicleTiming().compareTo(secondTiming.getVehicleTiming());
	}
}
