package com.cisco.vehiclesurvey.analyser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cisco.vehiclesurvey.core.VehicleData;
import com.cisco.vehiclesurvey.core.VehicleTiming;

public class TimeBucketPredicateTest extends TestCase {
	
	TimeBucketPredicate predicate = null;
	List<VehicleData> listData = null;
	
	@Before
	public void setUp() throws Exception {
		
		/* For Data :A1016488 day:1:0:16:56 is with 1 hour time.
		   For Data :A55038000 day:2:15:17:18 not with in 1 hour */
		listData = new ArrayList<>();
		
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
		
		listData.add(data);
		listData.add(secondData);
	}
	
	@After
	public void tearDown() throws Exception {
		if(listData != null){
			listData.clear();
			listData = null;
		}
	}
	
	@Test
	public void testPredicateW_inRange() throws Exception {
		// preparation
		VehicleTiming currentTiming = new VehicleTiming();
		currentTiming.setTiming((byte) 0, (byte) 1, (byte) 5, (byte) 0, 0);
		
		VehicleTiming previousTiming = new VehicleTiming();
		
		// call
		List<VehicleData> data = listData
				.stream()
				.filter(new TimeBucketPredicate(previousTiming,currentTiming).isDataWithInTimeRange())
				.map(p -> p)
				.collect(Collectors.toList());
		
		// verify
		assertEquals(data.size(), 1);
		assertEquals(data.get(0).getVehicleTiming().getHour(),0);
		assertEquals(data.get(0).getVehicleTiming().getMin(),16);
		assertEquals(data.get(0).getVehicleTiming().getSecond(),56);
	}
}