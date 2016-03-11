package com.cisco.vehiclesurvey.core;

import java.util.List;

import com.cisco.vehiclesurvey.analyser.VehicleDataAnalyser;
import com.cisco.vehiclesurvey.dataretriever.IDataRetriever;
/**
 * Client will use this class to create the DataRetriver,Data analyser, Query  etc..
 * @author Subrata Saha
 *
 */
public class VehicleSurveyDataBuilder {
	
	private IDataRetriever data = null;
	private List<VehicleData> preparedData = null;
	private VehicleDataAnalyser analyser = null;
	
	public VehicleSurveyDataBuilder buildData(IDataRetriever data){
		this.data = data; 
		return this;
	}
	
	public VehicleSurveyDataBuilder buildAnalyser() throws VehicleDataAnalyserException{
		if(data == null){
			throw new VehicleDataAnalyserException("Data not found",VehicleDataAnalyserException.ERROR_DATA_NOT_FOUND);  
		}
		preparedData = data.retrieveData();
		analyser =  new VehicleDataAnalyser(preparedData);
		return this;
	}
	
	public VehicleDataAnalyser build() throws VehicleDataAnalyserException{
		if(analyser == null){
			throw new VehicleDataAnalyserException("Analyser not created ",VehicleDataAnalyserException.ERROR_DATA_NOT_FOUND);  
		}
		return analyser;
	}
	
}
