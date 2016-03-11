package com.cisco.vehiclesurvey.query;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalDouble;

import com.cisco.vehiclesurvey.core.VehicleData;
/**
 * The composite result object to get the related information. 
 * @author Subrata Saha
 *
 */
public class QueryResult {
	
	private VehicleData.DIRECTION direction;
	
	private HashMap<Byte,List<Integer>> trafficCountDistribution = new HashMap<>();
	
	private HashMap<Byte,Integer> maxVolumeDayWise = new HashMap<>();
	
	private HashMap<Byte,Integer> minVolumeDayWise = new HashMap<>();
	
	public HashMap<Byte,Double> getAverageAcrossEachTheDays(){
		Iterator<Byte> it = trafficCountDistribution.keySet().iterator();
		HashMap<Byte,Double> avgCountPerDay = new HashMap<>();
		byte cday = 0;
		while(it.hasNext()){
			cday++;
			Byte key = it.next();
			List<Integer> value = trafficCountDistribution.get(key);
			OptionalDouble avg = value.stream().mapToInt((x) -> x).average();
			if(avg.isPresent()){
				avgCountPerDay.put(cday,avg.getAsDouble());
			}else{
				avgCountPerDay.put(cday,0.0);
			}
			
		}
		return avgCountPerDay;
	}
	
	public Double getAverageAcrossEachTheDays(Byte day){
		return getAverageAcrossEachTheDays().get(day);
	}
	
	public Double getAverageAcrossAllTheDays(){
		HashMap<Byte,Double> avgCountPerDay = getAverageAcrossEachTheDays();
		OptionalDouble avg = avgCountPerDay.values().stream().mapToDouble(x -> x).average();
		return avg.getAsDouble();
	}
	
	public void addTrafficDistributionAccrossTimeBucket(Byte key,List<Integer> value) {
		trafficCountDistribution.put(key, value);
	}
	
	public List<Integer> getTrafficDistributionAccrossTimeBucketDayWise(Byte key) {
		return  trafficCountDistribution.get(key);
	}
	
	
	public void addMaxVolumeDayWise(Byte key,Integer value) {
		maxVolumeDayWise.put(key, value);
	}
	
	public Integer getMaxVolumeDayWise(Byte day) {
		return maxVolumeDayWise.get(day);
	}
	
	public void addMinVolumeDayWise(Byte key,Integer value) {
		minVolumeDayWise.put(key, value);
	}
	
	public  Integer getMinVolumeDayWise(Byte day) {
		return minVolumeDayWise.get(day);
	}
	
	public VehicleData.DIRECTION getDirection() {
		return direction;
	}

	public void setDirection(VehicleData.DIRECTION direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "QueryResult [direction=" + direction + ", maxVolumeDayWise="
				+ maxVolumeDayWise + ", minVolumeDayWise=" + minVolumeDayWise
				+ ", trafficDistributionTimeBucketWise="
				+ trafficCountDistribution + "]";
	}
	
}
