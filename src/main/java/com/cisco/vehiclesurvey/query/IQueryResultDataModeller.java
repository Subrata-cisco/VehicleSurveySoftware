package com.cisco.vehiclesurvey.query;
/**
 * This interface will prepare the QueryResult for different client request (e.g for North direction or South direction.) 
 * @author Subrata Saha
 *
 */
public interface IQueryResultDataModeller {
	public static final int QUERTY_RESULT_TYPE_VEHICLE_COUNT_SOUTH_BOUND = 1;
	public static final int QUERTY_RESULT_TYPE_VEHICLE_COUNT_NORTH_BOUND = 2;

	public void modelResult(Object object);

	public QueryResult getResult();
}
