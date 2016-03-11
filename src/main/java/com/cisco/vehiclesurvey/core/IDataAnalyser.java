package com.cisco.vehiclesurvey.core;

import com.cisco.vehiclesurvey.query.IQuery;
import com.cisco.vehiclesurvey.query.QueryParameter;

/**
 * Analyser class to find apply query specific implementation.
 * @author Subrata Saha
 *
 */
public interface IDataAnalyser {
	public IQuery applyQuery(QueryParameter payload) throws InvalidInputParameterException;;
}
