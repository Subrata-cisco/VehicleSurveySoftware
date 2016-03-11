package com.cisco.vehiclesurvey.analyser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cisco.vehiclesurvey.core.Constants;
import com.cisco.vehiclesurvey.core.VehicleData;
import com.cisco.vehiclesurvey.core.VehicleTiming;

/**
 * 1) Take the List<VehicleData>
 * 2) Create 2 List - For North and South Direction.
 * 3) With in above list - It keeps data in the bucket according to the time given. (e.g per 20 minute,60 minute etc)
 * 4) It will take the time from client and distribute it accordingly.
 * 5) Another client (i.e different query classes) will act as bridge and use this class to find different query result.
 * 
 * @author Subrata Saha
 */
public abstract class AbstractDataFilter {
	
    /** the following list will contain 2 list , one for entire north and another for entire sount bound data at 0,1 index respectively. **/
	private List<List<List<VehicleData>>> northAndSouthBoundData = null;
	
	public AbstractDataFilter(){ 
		super();
	}
	
	public void createFilteredData(List<VehicleData> data){
		if(data != null && data.size() >0){
			filterDataByDirectionAndDayAndTime(data); 
		}
	}
	
	/**
	 * Creates Vehicle data by {Direction List { Day List } }
	 * Direction list size = 2 , Day list size = 5
	 * 
	 * @param data
	 */
	private void filterDataByDirectionAndDayAndTime(List<VehicleData> data) {
		// 1. Find the sublist containing the data for North and South bound data.
		Map<VehicleData.DIRECTION, List<VehicleData>> byDirection = data.stream()
				.collect(Collectors.groupingBy(VehicleData::getDirection));
		
		List<VehicleData> northBoundData = byDirection.get(VehicleData.DIRECTION.NORTH);
		List<VehicleData> southBoundData = byDirection.get(VehicleData.DIRECTION.SOUTH);
		
		// 2. Find the data for each day for North bound data.
		List<List<VehicleData>> dayWiseNorthBoundData = constructDayWiseData(northBoundData);
		
		// 3. Find the data for each day for South bound data.
		List<List<VehicleData>> dayWiseSouthBoundData = constructDayWiseData(southBoundData);
		
		// so final data have direction wise each day data.
		northAndSouthBoundData = new ArrayList<List<List<VehicleData>>>(2);
		northAndSouthBoundData.add(dayWiseNorthBoundData);
		northAndSouthBoundData.add(dayWiseSouthBoundData);
	}

	private List<List<VehicleData>> constructDayWiseData(List<VehicleData> vehicleData) {
		List<List<VehicleData>> dayWiseSouthBoundData = new ArrayList<List<VehicleData>>(Constants.TOTAL_NO_OF_DAYS);
		for(int i=1;i<=Constants.TOTAL_NO_OF_DAYS;i++){
			final int day = i;
			List<VehicleData> dayWiseSouthBoundVehicleData = new ArrayList<>();
			if(vehicleData != null){
				dayWiseSouthBoundVehicleData = vehicleData.stream()
						.filter(p -> p.getVehicleTiming().getDay() == day).map(p -> p)
						.collect(Collectors.toList());
			}
			dayWiseSouthBoundData.add(dayWiseSouthBoundVehicleData);
		}
		return dayWiseSouthBoundData;
	}
	
	/**
	 * Create Vehicle data by Direction List { Day List { Hour List {Possible vehicle data}}}
	 * Method is little big but i think for readability it is better to keep it in this way.
	 * @return
	 */
	public List<List<List<List<VehicleData>>>> getTimeWiseDataForBothDirection(){
		// allData will have 2 list - one for North direction and another for south direction.
		List<List<List<List<VehicleData>>>> allData = null;
		
		// The following list will have 5 list - for each day
		List<List<List<VehicleData>>> directionwiseAllDayTimewiseDistribution = null;
		
		// the following list will have n list - depending on time distribution for each day.
		List<List<VehicleData>> eachDayTimeWiseDistribution = null;
		
		if(northAndSouthBoundData != null){
			
			long timeInMinute = getTime();
			VehicleTiming currentTiming = getTiming(timeInMinute);
			VehicleTiming initialTiming = new VehicleTiming();
			VehicleTiming previousTiming = new VehicleTiming();
			
			initialTiming.setTiming((byte)0, currentTiming.getHour(), currentTiming.getMin(), currentTiming.getSecond(), 0);	
			
			allData = new ArrayList<>(northAndSouthBoundData.size());
			
			for(List<List<VehicleData>> dayWiseData : northAndSouthBoundData){
				directionwiseAllDayTimewiseDistribution = new ArrayList<>(dayWiseData.size());
				
				for(List<VehicleData> eachDayData:dayWiseData){
					
					eachDayTimeWiseDistribution = new ArrayList<>();
					Collections.sort(eachDayData,new VehicleDataTimingComparator());
					
					// create a list for each bucket according to timing and store in it eachDayTimeWiseDistribution list
					createEachDayBucket(eachDayTimeWiseDistribution,
							currentTiming, initialTiming, previousTiming,
							eachDayData);
					
					 currentTiming = getTiming(timeInMinute);
					 previousTiming = new VehicleTiming();
					
					directionwiseAllDayTimewiseDistribution.add(new ArrayList<>(eachDayTimeWiseDistribution));
				}
				allData.add(new ArrayList<>(directionwiseAllDayTimewiseDistribution));
			}
			
		}
		return allData;
	}

	private void createEachDayBucket(
			List<List<VehicleData>> eachDayTimeWiseDistribution,
			VehicleTiming currentTiming, VehicleTiming initialTiming,
			VehicleTiming previousTiming, List<VehicleData> eachDayData) {
		while ((currentTiming.getHour() < 24
				&& currentTiming.getMin() < 60 
				&& currentTiming.getSecond() < 60)){

				List<VehicleData> data = eachDayData
					.stream()
					.filter(new TimeBucketPredicate(previousTiming,currentTiming).isDataWithInTimeRange())
					.map(p -> p)
					.collect(Collectors.toList());
				
				eachDayTimeWiseDistribution.add(data);
				previousTiming.setTiming((byte)0, currentTiming.getHour(), currentTiming.getMin(), currentTiming.getSecond(), 0);
				createNextBucketTiming(currentTiming, initialTiming); 
		}
	}

	/**
	 * This API will create the next bucket of the day
	 * e.g if each shall contain 65 minute of data -> then from starting of the day the bucket will be 0-65 , 65-130 etc.
	 * The above timing is calculated here.
	 * 
	 * @param tempTiming
	 * @param initialTiming
	 */
	private void createNextBucketTiming(VehicleTiming tempTiming,
			VehicleTiming initialTiming) {
		
		byte minute = 0;
		byte hour = 0;
		
		byte second = (byte) (tempTiming.getSecond() + initialTiming.getSecond());
		
		if(second / Constants.SIXTY > 0){
			minute = (byte) (second / Constants.SIXTY);
			second = (byte) (second % Constants.SIXTY);
		}
		
		minute = (byte) (minute + tempTiming.getMin() + initialTiming.getMin()) ;
		if(minute / Constants.SIXTY > 0){
			hour = (byte) (minute / Constants.SIXTY);
			minute = (byte) (minute % Constants.SIXTY);
		}
		
		hour = (byte) (hour + tempTiming.getHour() + initialTiming.getHour()) ;
		
		tempTiming.setTiming((byte)0, hour, minute, second, 0);
	}
	
	/**
	 * API to create the end time of the next bucket.
	 * It will be used in finding data in time range between previous bucket  -> next bucket
	 * @param val
	 * @return
	 */
	private VehicleTiming getTiming(long val){
		byte hour = 0;
		byte minute = 0;
		
		if (val / Constants.ONE_HOUR_IN_MINUTE >= 0) {
			hour = (byte) (val / Constants.ONE_HOUR_IN_MINUTE); // in hour
			minute = (byte)(val % Constants.ONE_HOUR_IN_MINUTE); // ( contains minute)
		}else{
			minute = (byte)(val);
		}
		
		VehicleTiming timing  = new VehicleTiming();
		timing.setTiming((byte)0, hour, minute, (byte)0, 0); 
		
		return timing;
	}

	// expecting time in minute , not more than 24*60 minute.
	public abstract long getTime();
}
