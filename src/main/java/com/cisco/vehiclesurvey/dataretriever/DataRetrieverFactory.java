package com.cisco.vehiclesurvey.dataretriever;

import com.cisco.vehiclesurvey.core.IDataPreparationStrategy;

/**
 * Factory class to retrieve the data from File, web etc...
 * @author Subrata Saha
 *
 */
public class DataRetrieverFactory implements IDataRetrieverFactory {

	private static DataRetrieverFactory factory = new DataRetrieverFactory();
	
	private DataRetrieverFactory(){
	  // do nothing 	
	}
	
	public static DataRetrieverFactory getInstance(){
		return factory;
	}
	
	public IDataRetriever getDataRetriever(int type,String path) {
		IDataRetriever dataRetriever = null;
		switch (type) {
			case IDataRetriever.DATA_RETRIEVER_TYPE_FILE:   
				dataRetriever = new FileDataRetriever(path,IDataPreparationStrategy.NORTH_SOUTH_BOUND_DATA_PREPARATION_STRATEGY);
				break;
		}
		return dataRetriever;
	}

	

}

