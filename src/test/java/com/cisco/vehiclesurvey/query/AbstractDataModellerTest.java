package com.cisco.vehiclesurvey.query;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cisco.vehiclesurvey.core.VehicleData;
import com.cisco.vehiclesurvey.core.VehicleTiming;
import com.cisco.vehiclesurvey.core.VehicleData.DIRECTION;

public class AbstractDataModellerTest extends TestCase { 

	AbstractDataModellerImpl modeller = null;
	
	@Before
	public void setUp() throws Exception {
		modeller = new AbstractDataModellerImpl();
	}
	
	@After
	public void tearDown() throws Exception {
		modeller = null;
	}
	
	@Test
	public void testCreateFilteredData_directionList() throws Exception {
		//prepare
		List<List<List<List<VehicleData>>>> list = new ArrayList<>();
		//List<VehicleData> vehicleListForOneBucket = createListOfVehicleData();
		for (int i = 0; i < 4; i++) {
			List<List<List<VehicleData>>> dayList = new ArrayList<>();
			for (int j = 0; j < 22; j++) {
				List<List<VehicleData>> bucketList = new ArrayList<>();
				if(j == 0){
					bucketList.add(createListOfVehicleData());
				}
				dayList.add(bucketList);
			}
			list.add(dayList);
		}
		
		// call
		modeller.modelResult(list);
		QueryResult result = modeller.getResult();
		
		//verify
		assertEquals(result.getDirection(), VehicleData.DIRECTION.NORTH);
		assertEquals(result.getMaxVolumeDayWise((byte)1), new Integer(2));
		assertEquals(result.getMinVolumeDayWise((byte)1), new Integer(2));
		
		List<Integer> distribution = result.getTrafficDistributionAccrossTimeBucketDayWise((byte)1);
		assertEquals(distribution.get(0), new Integer(2));
		assertEquals(result.getAverageAcrossAllTheDays(), new Double(0.09090909090909091));
		assertEquals(result.getAverageAcrossEachTheDays((byte)1), new Double(2));
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

class AbstractDataModellerImpl extends AbstractDataModeller {

	@Override
	public DIRECTION getDirection() {
		return VehicleData.DIRECTION.NORTH;
	}

	@Override
	public byte getIndex() {
		return 0;
	}
	
}
