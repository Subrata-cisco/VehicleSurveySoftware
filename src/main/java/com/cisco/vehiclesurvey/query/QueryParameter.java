package com.cisco.vehiclesurvey.query;

import java.util.HashMap;
/**
 * Client will pass relevant query information through this class to middle ware.
 * @author Subrata Saha
 *
 */
public class QueryParameter {
	HashMap<Integer, Object> queryMap = new HashMap<>();
	
	public void addParameter(Integer key,Object value) {
		queryMap.put(key, value);
	}
	
	public Object getParameter(int key) {
		return queryMap.get(key);
	}
	
	public void clearAll() {
		queryMap.clear();
	}

}
