package com.cisco.vehiclesurvey.core;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.cisco.vehiclesurvey.analyser.VehicleDataAnalyser;
import com.cisco.vehiclesurvey.dataretriever.IDataRetriever;
@RunWith(PowerMockRunner.class) 
public class VehicleSurveyDataBuilderTest extends TestCase {
	
	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test(expected=VehicleDataAnalyserException.class)
	public void testBuildWithOutData() throws Exception {
		//preparation
		VehicleSurveyDataBuilder builder = new VehicleSurveyDataBuilder();
		
		// call
		builder.build();
	}
	
	@Test(expected=VehicleDataAnalyserException.class)  
	public void testBuildAnalyserWithOutData() throws Exception {
		//preparation
		VehicleSurveyDataBuilder builder = new VehicleSurveyDataBuilder();
		
		// call
		builder.buildAnalyser();
	}
	
	@Test
	public void testBuild() throws Exception {
		//preparation
		IDataRetriever retriever = PowerMockito.mock(IDataRetriever.class);
		List<VehicleData> data = createListOfVehicleData();
		Mockito.when(retriever.retrieveData()).thenReturn(data);
		
		VehicleSurveyDataBuilder builder = new VehicleSurveyDataBuilder();
		builder = PowerMockito.spy(builder);
		
		// call
		builder.buildData(retriever);
		builder.buildAnalyser();
		VehicleDataAnalyser retrunedAnalyser = builder.build();
		
		// verify
		assertNotNull(retrunedAnalyser);
	}
	
	private List<VehicleData> createListOfVehicleData(){
		VehicleData data = new VehicleData();
		data.setTransactions("A1016488");
		
		VehicleTiming firstEntry = new VehicleTiming();
		firstEntry.setTiming((byte)0, (byte)0, (byte)16, (byte)56, 488);  
		data.setVehicleTiming(firstEntry);
		
		VehicleData secondData = new VehicleData();
		secondData.setTransactions("A55038000");
		
		VehicleTiming secondEntry = new VehicleTiming();
		secondEntry.setTiming((byte)0, (byte)15, (byte)17, (byte)18, 0);  
		secondData.setVehicleTiming(secondEntry);
		
		List<VehicleData> listData =  new ArrayList<>();
		listData.add(data);
		listData.add(secondData);
		
		return listData;
	}
}
