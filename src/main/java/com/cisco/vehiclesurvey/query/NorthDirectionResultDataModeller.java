package com.cisco.vehiclesurvey.query;

import com.cisco.vehiclesurvey.core.VehicleData.DIRECTION;

/**
 * Class to get the North bound data.
 * @author Subrata Saha
 *
 */
public class NorthDirectionResultDataModeller extends AbstractDataModeller  {

	@Override
	public DIRECTION getDirection() {
		return DIRECTION.NORTH;
	}

	@Override
	public byte getIndex() {
		return 0;
	}

}
