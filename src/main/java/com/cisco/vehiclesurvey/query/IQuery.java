package com.cisco.vehiclesurvey.query;

import java.util.List;

import com.cisco.vehiclesurvey.core.InvalidInputParameterException;
import com.cisco.vehiclesurvey.core.VehicleData;

public interface IQuery {

	public static final int QUERY_TYPE_VEHICLE_COUNT = 1;
	public static final int QUERY_TYPE_PEAK_VOLUME = 2;
	public static final int QUERY_TYPE_SPEED_DISTRIBUTION = 3;
	public static final int QUERY_TYPE_ROUGH_DISTANCE = 4;
	
	public static final int QUERTY_TYPE = 5;
	public static final int QUERTY_TIME = 6;
	public static final int QUERTY_MORNING_EVENING = 7; 
	public static final int QUERTY_MORNING_EVENING_VALUE = 8; 
	public static final int QUERTY_DAY = 9;
	public static final int QUERTY_RESULT_TYPES = 10; 
	
	public static final int QUERTY_INVALID_FILE_PATH = 11; 
	
	
	public void setQueryMasterData(List<VehicleData> payLoad);
	public void setQueryInputParameters(QueryParameter param);
	public void executeQuery() throws InvalidInputParameterException;
	public List<IQueryResultDataModeller> getIQueryResult(); 
}
