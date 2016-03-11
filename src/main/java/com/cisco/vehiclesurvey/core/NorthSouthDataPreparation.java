package com.cisco.vehiclesurvey.core;

import java.util.ArrayList;
import java.util.List;
/**
 * The responsibility of this class :
 * 1) Create VehicleData.[with info like days,hour,minute,second,mili second]
 * 
 * @author Subrata Saha
 *
 */
public class NorthSouthDataPreparation implements IDataPreparationStrategy {

	private List<String> data = null;
	private long prevData = 0;
	
	// variable to check the days for each line of data.
	private byte day = 1;
	
	public NorthSouthDataPreparation() {
		// nothing..
	}

	@Override
	public List<VehicleData> prepareData() throws CorruptedDataException {
		return createListOfVehicleData();
	}

	@Override
	public void setInputData(List<String> list) {
		this.data = list;
	}
	
	private List<VehicleData> createListOfVehicleData() throws CorruptedDataException {
		List<VehicleData> list = new ArrayList<>();
		// the minimum data size for file input is 2.
		// why using list to array , easy to manipulate and check data items by maintaining multiple pointers.
		if(data != null && data.size() >= 2){
			String[] sData = new String[data.size()];
			sData = data.toArray(sData);
			
			// start and end index for each transaction. for North bound - endIndex  = startIndex +1 and for South bound endIndex  = startIndex +3
			int startIndex = 0;
			int endIndex = 1;
			
			while(startIndex<sData.length && endIndex < sData.length){
				
				VehicleData startData = getVehicleDataFromRawString(sData[startIndex]);
				VehicleData endData = getVehicleDataFromRawString(sData[endIndex]);
				
				if (checkIfNorthBoundData(startData, endData)) {
					
					handleNorthBoundData(list, startData, endData);
					startIndex = endIndex + 1;
					endIndex = startIndex + 1;
					
				} else if (checkIfSouthBoundData(startData, endData)) {  // A followed by B
					
					endIndex++; // 3rd data
					VehicleData nextFirstData = getDataFromIndexForSouthBound(sData, endIndex, startData);
					
					endIndex++; // 4th data
					VehicleData nextSecondData = getDataFromIndexForSouthBound(sData, endIndex, startData);
					
					List<VehicleData> allSouthData = new ArrayList<>();
					allSouthData.add(startData);allSouthData.add(endData);allSouthData.add(nextFirstData);allSouthData.add(nextSecondData);
					handleSouthBoundData(list, allSouthData); 
					
					startIndex = endIndex + 1;
					endIndex = startIndex + 1;
				} else{
					throw new CorruptedDataException(startData.getTransactions(),
							"[Type-3] North/South bound data is not correct starting from the raw string ::"+startData.getTransactions());
				}
			}
		}
		return list;
	}

	private void handleSouthBoundData(List<VehicleData> list,List<VehicleData> allConsecutiveData)
			throws CorruptedDataException {
		if (checkTimeDiffBetweenSameWheel(allConsecutiveData)) {
			
			allConsecutiveData.get(0).setTransactions(allConsecutiveData.get(1).getTransactions());
			allConsecutiveData.get(0).setTransactions(allConsecutiveData.get(2).getTransactions());
			allConsecutiveData.get(0).setTransactions(allConsecutiveData.get(3).getTransactions());
			allConsecutiveData.get(0).setVehicleTiming(allConsecutiveData.get(3).getVehicleTiming());
			allConsecutiveData.get(0).setDirection(VehicleData.DIRECTION.SOUTH);
			
			list.add(allConsecutiveData.get(0));
			
		}else{
			throw new CorruptedDataException(allConsecutiveData.get(0).getTransactions(),
					"[Type-2] North/South bound data is not correct starting from the raw string::"+allConsecutiveData.get(0).getTransactions());
		}
	}

	private void handleNorthBoundData(List<VehicleData> list,
			VehicleData startData, VehicleData endData)
			throws CorruptedDataException {
		if (checkIfCorrectTimeDifferenceByBothWheel(startData, endData)) {
				
			startData.setTransactions(endData.getTransactions());
			startData.setVehicleTiming(endData.getVehicleTiming());
			startData.setDirection(VehicleData.DIRECTION.NORTH);
			
			list.add(startData);
		}else{
			throw new CorruptedDataException(startData.getTransactions(),
					"[Type-1] North/South bound data is not correct starting from the raw string printed ::"+startData.getTransactions());
		}
	}

	private boolean checkTimeDiffBetweenSameWheel(List<VehicleData> allConsecutiveData) {
		return allConsecutiveData.get(1).getVehicleTiming().getMili()
				- allConsecutiveData.get(0).getVehicleTiming().getMili() <= Constants.TIME_DIFFERENCE_BY_SAME_WHEEL_TO_HIT_DIFFERENT_RUBBER_HORSE
				&& allConsecutiveData.get(3).getVehicleTiming().getMili()
						- allConsecutiveData.get(2).getVehicleTiming()
								.getMili() <= Constants.TIME_DIFFERENCE_BY_SAME_WHEEL_TO_HIT_DIFFERENT_RUBBER_HORSE;
	}

	private VehicleData getDataFromIndexForSouthBound(String[] sData, int endIndex,
			VehicleData startData) throws CorruptedDataException {
		if(endIndex >= sData.length){
			throw new CorruptedDataException(startData.getTransactions(),
					"[Type-4] North/South bound data is not correct starting from the raw string ::"+startData.getTransactions());
		}
		VehicleData data = getVehicleDataFromRawString(sData[endIndex]);
		return data;
	}

	private boolean checkIfCorrectTimeDifferenceByBothWheel(
			VehicleData startData, VehicleData endData) {
		return endData.getVehicleTiming().getMili()
				- startData.getVehicleTiming().getMili() <= Constants.TIME_DIFFERENCE_BY_BOTH_WHEEL_TO_HIT_SAME_RUBBER_HORSE;
	}

	private boolean checkIfSouthBoundData(VehicleData startData,
			VehicleData endData) {
		return startData.getTransactions().charAt(0) == Constants.CHAR_A
				&& endData.getTransactions().charAt(0) == Constants.CHAR_B;
	}

	private boolean checkIfNorthBoundData(VehicleData startData,
			VehicleData endData) {
		return startData.getTransactions().charAt(0) == endData.getTransactions().charAt(0) // both the data start with 'A'
				&& startData.getTransactions().charAt(0) == Constants.CHAR_A;
	}
	
	/**
	 * Calculate Day,Hour,Minute,Second,MiliSecond for each raw data.
	 * @param rawData
	 * @return
	 * @throws CorruptedDataException
	 */
	private VehicleData getVehicleDataFromRawString(String rawData) throws CorruptedDataException{
		VehicleData vData = null;
		long val = extractNumericValue(rawData);
		if (val < Constants.TWENTY_FOUR_HOUR_IN_MILI && val > 0) {
			// if previous data is greater than current raw data then we are going to next day.
			if(prevData !=0 && prevData > val){
				day++;
			}
			prevData = val;
			vData = createVehicleTiming(val,rawData);
		} else{
			throw new CorruptedDataException(rawData,
					"[Type-0] North or South bound data is not correct, and does not fall between 24 hours !! for raw string::"+rawData);
		}
		return vData;
	}

	private long extractNumericValue(String rawData)
			throws CorruptedDataException {
		StringBuffer sb = new StringBuffer(rawData);
		char directionSymbol = sb.charAt(0);
		
		if(directionSymbol != Constants.CHAR_A && directionSymbol != Constants.CHAR_B){
			throw new CorruptedDataException(rawData," String is not starting with A or B !!");
		}
		
		sb.deleteCharAt(0);
		
		long val = 0;
		try {
			val = Integer.valueOf(sb.toString());
		} catch (NumberFormatException exception) {
			throw new CorruptedDataException(rawData," String after A or B is not convertable to integer !!");
		}
		return val;
	}

	private VehicleData createVehicleTiming(long val,String rawData) {
		byte hour = 0;
		byte minute = 0;
		byte seconds = 0;
		int mili = 0;
		
		mili = (int) (val % 1000) ;
		val = val / 1000; // convert to seconds from mili-seconds.
		
		if (val / Constants.ONE_HOUR_IN_SECOND >= 0) {
			hour = (byte) (val / Constants.ONE_HOUR_IN_SECOND); // in hour
			val = val % Constants.ONE_HOUR_IN_SECOND;  // ( contains minute:second)
		}

		if (val / Constants.ONE_MINUTE_IN_SECOND >= 0) {
			minute = (byte) (val / Constants.ONE_MINUTE_IN_SECOND); // in minute
			seconds = (byte) (val % Constants.ONE_MINUTE_IN_SECOND); // in sec
		}else{
			seconds = (byte) (val );
		}
		
		VehicleData vData;
		VehicleTiming vTiming = new VehicleTiming();
		vTiming.setTiming(day, hour, minute, seconds, mili); 
		
		vData = new VehicleData();
		vData.setVehicleTiming(vTiming);
		vData.setTransactions(rawData);
		
		return vData;
	}
}
