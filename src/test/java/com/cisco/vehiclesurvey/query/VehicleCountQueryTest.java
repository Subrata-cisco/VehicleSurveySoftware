package com.cisco.vehiclesurvey.query;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.cisco.vehiclesurvey.core.Constants;
import com.cisco.vehiclesurvey.core.VehicleData;
import com.cisco.vehiclesurvey.core.VehicleTiming;

public class VehicleCountQueryTest extends TestCase {
	
	VehicleCountQuery countQuery = null;
	
	@Before
	public void setUp() throws Exception {
		countQuery = new VehicleCountQuery();
	}
	
	@After
	public void tearDown() throws Exception {
		countQuery = null;
	}
	
	@Test
	public void testQueryParameters_QUERTY_TIME() throws Exception {
		//prepare
		countQuery.setQueryMasterData(createListOfVehicleData());
		QueryParameter param = new QueryParameter();
		param.addParameter(IQuery.QUERTY_TIME, 60L);
		
		countQuery.setQueryInputParameters(param);
		
		// call
		countQuery.executeQuery();
		
		//verify
		long time = (long) Whitebox.getInternalState(countQuery, "time");
		assertEquals(time,60L);
	}
	
	@Test
	public void testQueryParameters_QUERTY_MORNING_EVENING() throws Exception {
		//prepare
		countQuery.setQueryMasterData(createListOfVehicleData());
		QueryParameter param = new QueryParameter();
		param.addParameter(IQuery.QUERTY_TIME, 60L);
		param.addParameter(IQuery.QUERTY_MORNING_EVENING,IQuery.QUERTY_MORNING_EVENING_VALUE);
		
		countQuery.setQueryInputParameters(param);
		
		// call
		countQuery.executeQuery();
		
		//verify
		long time = (long) Whitebox.getInternalState(countQuery, "time");
		assertEquals(time,Constants.TWELVE_HOUR_IN_MINUTE);
	}
	
	@Test
	public void testQueryParameters_QUERTY_RESULT_TYPES() throws Exception {
		//prepare
		countQuery.setQueryMasterData(createListOfVehicleData());
		QueryParameter param = new QueryParameter();
		param.addParameter(IQuery.QUERTY_TIME, 60L);
		param.addParameter(IQuery.QUERTY_RESULT_TYPES, new int[] {
				IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_SOUTH_BOUND,
				IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_NORTH_BOUND });
		
		countQuery.setQueryInputParameters(param);
		
		// call
		countQuery.executeQuery();
		
		//verify
		int[] results = (int[]) Whitebox.getInternalState(countQuery, "results");
		assertEquals(results[0],IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_SOUTH_BOUND);
		assertEquals(results[1],IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_NORTH_BOUND);
	}
	
	@Test
	public void testGetResult() throws Exception {
		//prepare
		countQuery.setQueryMasterData(createListOfVehicleData());
		QueryParameter param = new QueryParameter();
		param.addParameter(IQuery.QUERTY_TIME, 60L);
		param.addParameter(IQuery.QUERTY_RESULT_TYPES, new int[] {
				IQueryResultDataModeller.QUERTY_RESULT_TYPE_VEHICLE_COUNT_NORTH_BOUND });
		
		countQuery.setQueryInputParameters(param);
		
		// call
		countQuery.executeQuery();
		List<IQueryResultDataModeller> resultList = countQuery.getIQueryResult();
		
		//verify
		assertEquals(resultList.size(), 1);
		IQueryResultDataModeller modeller = resultList.get(0);
		assertNotNull(modeller);
	}
	
	private List<VehicleData> createListOfVehicleData(){
		VehicleData firstData = new VehicleData();
		firstData.setTransactions("A1016488");
		firstData.setDirection(VehicleData.DIRECTION.NORTH);
		
		VehicleTiming firstEntry = new VehicleTiming();
		firstEntry.setTiming((byte)0, (byte)0, (byte)16, (byte)56, 488);  
		firstData.setVehicleTiming(firstEntry);
		
		VehicleData secondData = new VehicleData();
		secondData.setTransactions("A55038000");
		secondData.setDirection(VehicleData.DIRECTION.NORTH);
		
		VehicleTiming secondEntry = new VehicleTiming();
		secondEntry.setTiming((byte)0, (byte)15, (byte)17, (byte)18, 0);  
		secondData.setVehicleTiming(secondEntry);
		
		List<VehicleData> listData =  new ArrayList<>();
		listData.add(firstData);
		listData.add(secondData);
		
		return listData;
	}
}
