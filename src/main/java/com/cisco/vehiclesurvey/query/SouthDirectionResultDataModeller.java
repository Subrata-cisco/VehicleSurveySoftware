package com.cisco.vehiclesurvey.query;

import com.cisco.vehiclesurvey.core.VehicleData.DIRECTION;
/**
 * Soth bound resulr modeler,this exist because client added this as 
 * IQuery.QUERTY_RESULT_TYPES, new int[] {IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_SOUTH_BOUND}
 * @author Subrata Saha
 *
 */
public class SouthDirectionResultDataModeller extends AbstractDataModeller {

	@Override
	public DIRECTION getDirection() {
		return DIRECTION.SOUTH;
	}

	@Override
	public byte getIndex() {
		return 1;
	}

}
