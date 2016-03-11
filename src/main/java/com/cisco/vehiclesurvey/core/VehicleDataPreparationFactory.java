package com.cisco.vehiclesurvey.core;

/**
 * Factory class to create the specific imput data to be used later on for filtering.
 * TODO - NorthSouthDataPreparation is not so resource restriction, so still we need it as Singleton ?? We will see.
 * @author Subrata Saha
 *
 */
public class VehicleDataPreparationFactory {
	
	private static VehicleDataPreparationFactory factory = new VehicleDataPreparationFactory();
	
	private VehicleDataPreparationFactory(){
	  // do nothing 	
	}
	
	public static VehicleDataPreparationFactory getInstance(){
		return factory;
	}
	
	public IDataPreparationStrategy getDataPreparator(int type) {
		IDataPreparationStrategy dataPreparator = null; 
		switch (type) {
			case IDataPreparationStrategy.NORTH_SOUTH_BOUND_DATA_PREPARATION_STRATEGY:   
				dataPreparator = new NorthSouthDataPreparation();
				break;
		}
		return dataPreparator;
	}
}
