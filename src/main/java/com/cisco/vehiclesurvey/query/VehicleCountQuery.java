package com.cisco.vehiclesurvey.query;

import java.util.ArrayList;
import java.util.List;

import com.cisco.vehiclesurvey.analyser.AbstractDataFilter;
import com.cisco.vehiclesurvey.core.Constants;
import com.cisco.vehiclesurvey.core.InvalidInputParameterException;
import com.cisco.vehiclesurvey.core.VehicleData;
/**
 * This class acts as a bridge and take the help of AbstractDataFilter to compute the actual result
 * and then concluding how to respond to a client request by further delegating the result to compute
 * from different IQueryResult
 * 
 * @author Subrata Saha
 *
 */
public class VehicleCountQuery extends AbstractDataFilter implements IQuery {

	private List<VehicleData> payLoad = null;
	
	private QueryParameter queryParam = null;
	
	private int[]  results = null;
	
	private long time = 0;

	@Override
	public void setQueryMasterData(List<VehicleData> payLoad) {
		this.payLoad = payLoad;
	}

	@Override
	public void setQueryInputParameters(QueryParameter param) {
		this.queryParam = param;
	}
	
	@Override
	public void executeQuery() throws InvalidInputParameterException {
		if(queryParam != null){
			
			if(queryParam.getParameter(IQuery.QUERTY_TIME) != null){
				time = (Long)queryParam.getParameter(IQuery.QUERTY_TIME); 
			}
			
			results = (int[]) queryParam.getParameter(IQuery.QUERTY_RESULT_TYPES);
			
			if (queryParam.getParameter(IQuery.QUERTY_MORNING_EVENING) != null
					&& queryParam.getParameter(IQuery.QUERTY_MORNING_EVENING)
							.equals(IQuery.QUERTY_MORNING_EVENING_VALUE)) {
				time = Constants.TWELVE_HOUR_IN_MINUTE;
			}
			
			if (time > Constants.TWENTY_FOUR_HOUR_IN_MINUTE || time <= 0) 
				throw new InvalidInputParameterException(time,
						" Timing input as per IQuery.QUERTY_TIME/IQuery.QUERTY_MORNING_EVENING is not correct !!");
		}
		
		if(payLoad != null){
			createFilteredData(payLoad);
		}
	}

	@Override
	public long getTime() {
		return time;
	}

	/**
	 * From this API we have to return multiple results , so better to delegate to specific 
	 * class to take care of it.
	 * 
	 */
	@Override
	public List<IQueryResultDataModeller> getIQueryResult() {
		List<IQueryResultDataModeller> resultList = new ArrayList<>();
		List<List<List<List<VehicleData>>>> list = getTimeWiseDataForBothDirection();
		
		if(list != null && results != null && results.length > 0){
			QueryResultFactory factory = new QueryResultFactory();
			for(int queryResultType : results){
				IQueryResultDataModeller result = factory.getIQueryResult(queryResultType);
				if(result != null){
					result.modelResult(list);
					resultList.add(result); 
				}
			}
		}
		return resultList;
	}

}
