package com.cisco.vehiclesurvey.query;

/**
 * Depending on how many query being asked by client , this is the result which will modeled to QueryResult by this classes.  
 * @author Subrata Saha
 *
 */
public class QueryResultFactory {

	public IQueryResultDataModeller getIQueryResult(int queryResultType) {
		IQueryResultDataModeller queryResult = null;
		switch (queryResultType) {
		case IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_NORTH_BOUND:
			queryResult = new NorthDirectionResultDataModeller();
			break;
		case IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_SOUTH_BOUND:
			queryResult = new SouthDirectionResultDataModeller();
			break;
		}
		return queryResult;
	}

}
