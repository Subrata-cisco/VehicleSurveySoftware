package com.cisco.vehiclesurvey.query;

import java.util.ArrayList;
import java.util.List;

import com.cisco.vehiclesurvey.core.VehicleData;
/**
 * The Model class which shall read the filtered data and create the QueryResult based on client request.
 * @author Subrata Saha
 *
 */
public abstract class AbstractDataModeller implements IQueryResultDataModeller {
	
	private QueryResult result = new QueryResult();
	
	@Override
	public void modelResult(Object object) {
		@SuppressWarnings("unchecked")
		List<List<List<List<VehicleData>>>> list = (List<List<List<List<VehicleData>>>>) object;
		
		byte day = 0;
		
		result.setDirection(getDirection());
		
		List<List<List<VehicleData>>> northData = list.get(getIndex());
		for(List<List<VehicleData>> eachDayData : northData){
			day++;
			List<Integer> dayWiseTimeBoundVevicleCount = new ArrayList<>();
			
			int minTrafficVolume = 0;
			int maxTrafficVolume = 0;
			
			for(List<VehicleData> forOneParticularDayData : eachDayData){
				
				int size = forOneParticularDayData.size();
				if(minTrafficVolume == 0 && maxTrafficVolume == 0){
					minTrafficVolume = size;
					maxTrafficVolume = size;
				}else if(size < minTrafficVolume){
					minTrafficVolume = size;
				}else if(size > maxTrafficVolume){
					maxTrafficVolume = size;
				}
				
				dayWiseTimeBoundVevicleCount.add(size);
			}
			
			result.addMaxVolumeDayWise(day, maxTrafficVolume);
			result.addMinVolumeDayWise(day, minTrafficVolume);
			result.addTrafficDistributionAccrossTimeBucket(day, new ArrayList<>(dayWiseTimeBoundVevicleCount));
		}
		
	}

	@Override
	public QueryResult getResult() {
		return result;
	}
	
	public abstract VehicleData.DIRECTION getDirection();
	public abstract byte getIndex();
}
