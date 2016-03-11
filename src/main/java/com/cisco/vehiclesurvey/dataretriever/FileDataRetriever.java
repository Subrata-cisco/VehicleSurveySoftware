package com.cisco.vehiclesurvey.dataretriever;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.cisco.vehiclesurvey.core.CorruptedDataException;
import com.cisco.vehiclesurvey.core.IDataPreparationStrategy;
import com.cisco.vehiclesurvey.core.InvalidInputParameterException;
import com.cisco.vehiclesurvey.core.VehicleData;
import com.cisco.vehiclesurvey.core.VehicleDataPreparationFactory;
import com.cisco.vehiclesurvey.query.IQuery;

/**
 * FileDataRetriever responsibility.
 * 
 * 1) Read the file from filePath. 2) Create the list of VehicleData.
 * 
 * @author Subrata Saha
 *
 */
public class FileDataRetriever implements IDataRetriever {

	private String filePath = null;
	private int dataPreparationStrategy = -1;

	public FileDataRetriever(String filePath,int strategy) {
		this.filePath = filePath;
		this.dataPreparationStrategy  = strategy;
	}

	public List<VehicleData> retrieveData() throws CorruptedDataException,
			InvalidInputParameterException {
		return getVehicleDataFromFile();
	}

	private List<VehicleData> getVehicleDataFromFile()
			throws CorruptedDataException, InvalidInputParameterException {
	
		if(filePath == null){
			throw new InvalidInputParameterException(IQuery.QUERTY_INVALID_FILE_PATH, 
					" file Path to read the data is not set !!");
		}
		
		File file = new File(filePath);
		List<String> data = new ArrayList<>();
		List<VehicleData> list = null;
		
		//read the file input
		if (file.exists()) {
			try (BufferedReader r = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(file)),
					StandardCharsets.UTF_8));) {

				String line = null;
				while ((line = r.readLine()) != null) {
					data.add(line);
				}

			} catch (FileNotFoundException exception) {
			} catch (IOException exception) {
			} finally {
				
			}

		}
		
		// if data is there then delegate it to separate class to load it according to some algorithm.
		if(data.size()>0){
			IDataPreparationStrategy strategy = VehicleDataPreparationFactory
					.getInstance().getDataPreparator(dataPreparationStrategy);
			if(strategy != null){
				strategy.setInputData(data);
				list = strategy.prepareData();
			}
		}
		
		return list;
	}
}
