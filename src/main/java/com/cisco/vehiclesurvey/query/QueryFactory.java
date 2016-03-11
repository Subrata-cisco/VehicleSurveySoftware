package com.cisco.vehiclesurvey.query;

/**
 * Factory class to get the (e.g support different queries)
 * 1) Vehicle Count query.
 * 2) Speed related Query will instantiated from here only.
 * 
 * @author Subrata Saha
 *
 */
public class QueryFactory {

	public static IQuery getQueryType(int queryType) {
		IQuery query = null;
		switch (queryType) {
			case IQuery.QUERY_TYPE_VEHICLE_COUNT:
				query = new VehicleCountQuery();
				break;
		}
		return query;
	}
}
