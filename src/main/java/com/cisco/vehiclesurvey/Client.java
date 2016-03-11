package com.cisco.vehiclesurvey;

import java.util.List;

import com.cisco.vehiclesurvey.core.CorruptedDataException;
import com.cisco.vehiclesurvey.core.InvalidInputParameterException;
import com.cisco.vehiclesurvey.core.VehicleDataAnalyserException;
import com.cisco.vehiclesurvey.core.VehicleSurveyDataBuilder;
import com.cisco.vehiclesurvey.dataretriever.DataRetrieverFactory;
import com.cisco.vehiclesurvey.dataretriever.IDataRetriever;
import com.cisco.vehiclesurvey.query.IQuery;
import com.cisco.vehiclesurvey.query.IQueryResultDataModeller;
import com.cisco.vehiclesurvey.query.QueryParameter;
import com.cisco.vehiclesurvey.query.QueryResult;


public class Client {
	public static void main(String[] args)
			throws InvalidInputParameterException, CorruptedDataException,
			VehicleDataAnalyserException {
		String path = "E://Subrata//code//tdd//cisco-coding-challenge//cisco-coding-challenge//Vehicle Survey Coding Challenge//data.txt";
		
		QueryParameter param = new QueryParameter();
		param.addParameter(IQuery.QUERTY_TYPE, IQuery.QUERY_TYPE_VEHICLE_COUNT);
		param.addParameter(IQuery.QUERTY_TIME, 60L);
		//param.addParameter(IQuery.QUERTY_MORNING_EVENING,IQuery.QUERTY_MORNING_EVENING_VALUE);
		param.addParameter(IQuery.QUERTY_RESULT_TYPES, new int[] {
				IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_SOUTH_BOUND,
				IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_NORTH_BOUND });
		
		IQuery query =  new VehicleSurveyDataBuilder()
		 				.buildData(DataRetrieverFactory.getInstance().getDataRetriever(IDataRetriever.DATA_RETRIEVER_TYPE_FILE,path))
		 				.buildAnalyser()
		 				.build()
		 				.applyQuery(param);
		
		List<IQueryResultDataModeller>  list = query.getIQueryResult();
		for(IQueryResultDataModeller result :list){
			QueryResult qResult = result.getResult();
			System.out.println(qResult);
			System.out.println(qResult.getAverageAcrossEachTheDays());
			System.out.println(qResult.getAverageAcrossAllTheDays());
		}
	
	}
}
