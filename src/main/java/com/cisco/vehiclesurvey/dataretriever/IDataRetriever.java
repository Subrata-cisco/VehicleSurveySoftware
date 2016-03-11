package com.cisco.vehiclesurvey.dataretriever;

import java.util.List;

import com.cisco.vehiclesurvey.core.CorruptedDataException;
import com.cisco.vehiclesurvey.core.InvalidInputParameterException;
import com.cisco.vehiclesurvey.core.VehicleData;

public interface IDataRetriever {
   public static final int DATA_RETRIEVER_TYPE_FILE = 1;
   
	public List<VehicleData> retrieveData() throws CorruptedDataException,
			InvalidInputParameterException;
  
}
