package com.cisco.vehiclesurvey.core;

import java.util.List;

import com.cisco.vehiclesurvey.core.CorruptedDataException;
/**
 * Through this interface we can move to a altogether different algorithm, so just adding the extensibilty.
 * @author Subrata Saha
 *
 */
public interface IDataPreparationStrategy {
  public static final int NORTH_SOUTH_BOUND_DATA_PREPARATION_STRATEGY = 1; 
  public List<VehicleData> prepareData() throws CorruptedDataException;
  public void setInputData(List<String> list);
}
