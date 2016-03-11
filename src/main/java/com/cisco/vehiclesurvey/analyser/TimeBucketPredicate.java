package com.cisco.vehiclesurvey.analyser;

import java.util.function.Predicate;

import com.cisco.vehiclesurvey.core.Constants;
import com.cisco.vehiclesurvey.core.VehicleData;
import com.cisco.vehiclesurvey.core.VehicleTiming;
/**
 * Find out the time bucket.
 * @author Subrata Saha
 *
 */
public class TimeBucketPredicate {

	private VehicleTiming currentTiming;
	private VehicleTiming prevTiming;
	
	public TimeBucketPredicate (VehicleTiming prevTiming,VehicleTiming currentTiming){
		this.currentTiming = currentTiming;
		this.prevTiming = prevTiming;
	}
	
	/**
	 * Calculate the total second of prevTiming and currentTiming and later the incoming timing value in seconds
	 * Now check if IncomingTimng > prevTiming &&  IncomingTimng <= currentTiming
	 * 
	 * @return
	 */
	public Predicate<VehicleData> isDataWithInTimeRange() {
		return incoming ->    (incoming.getVehicleTiming().getHour()* Constants.ONE_HOUR_IN_SECOND 
				    		+ incoming.getVehicleTiming().getMin()* Constants.ONE_MINUTE_IN_SECOND
				    		+ incoming.getVehicleTiming().getSecond() 
				    		>
		              		  prevTiming.getHour()* Constants.ONE_HOUR_IN_SECOND 
		              		+ prevTiming.getMin()* Constants.ONE_MINUTE_IN_SECOND 
		              		+ prevTiming.getSecond())
		            
		              		&& 
		              		  (incoming.getVehicleTiming().getHour()* Constants.ONE_HOUR_IN_SECOND
		              		+ incoming.getVehicleTiming().getMin()* Constants.ONE_MINUTE_IN_SECOND
		              		+ incoming.getVehicleTiming().getSecond() 
		              		<= 
		              		  currentTiming.getHour()* Constants.ONE_HOUR_IN_SECOND
		              		+ currentTiming.getMin()* Constants.ONE_MINUTE_IN_SECOND 
		              		+ currentTiming.getSecond());
    }
}
