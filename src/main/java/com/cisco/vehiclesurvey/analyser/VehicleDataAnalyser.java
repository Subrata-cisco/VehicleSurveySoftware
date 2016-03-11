package com.cisco.vehiclesurvey.analyser;

import java.util.List;

import com.cisco.vehiclesurvey.core.IDataAnalyser;
import com.cisco.vehiclesurvey.core.InvalidInputParameterException;
import com.cisco.vehiclesurvey.core.VehicleData;
import com.cisco.vehiclesurvey.query.IQuery;
import com.cisco.vehiclesurvey.query.QueryFactory;
import com.cisco.vehiclesurvey.query.QueryParameter;
/**
 * Find out the query to be fired.
 * Set data and parameter of the query and execute it.
 * @author Subrata Saha
 *
 */
public class VehicleDataAnalyser implements IDataAnalyser {

	private List<VehicleData> data = null;
	
	public VehicleDataAnalyser(List<VehicleData> data) { 
		this.data = data;
	}

	@Override
	public IQuery applyQuery(QueryParameter param) throws InvalidInputParameterException { 
		IQuery query = null;
		
		if(param != null){
			query = QueryFactory.getQueryType((int)param.getParameter(IQuery.QUERTY_TYPE));
		}
		
		if(query != null){
			query.setQueryMasterData(data);
			query.setQueryInputParameters(param);
			query.executeQuery(); 
		}
		
		return query;
	}

}
