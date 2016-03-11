package com.cisco.vehiclesurvey.core;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;
/**
 * The test cases this class :
 * 1) test for North transaction.
 * 2) test for consecutive days North transaction.
 * 3) check for corrupted data in north transaction.
 * 4) check for South transaction.
 * 5) check for consecutive days South transaction.
 * 6) corrupted data for South transaction.
 * 
 * @author Subrata Saha
 *
 */
@RunWith(PowerMockRunner.class) 
public class NorthSouthDataPreparationTest extends TestCase {

	NorthSouthDataPreparation dataPreparator = null;

	@Before
	public void setUp() throws Exception {
		dataPreparator = new NorthSouthDataPreparation();
	}
	
	@After
	public void tearDown() throws Exception {
		dataPreparator = null;
	}
	
	@Test(expected=CorruptedDataException.class)
	public void testPrepareData_corruptedNorthData_notstartingWithA() throws Exception {
		// prepare data
		String[] sampleData = {"A7051179","A7051479"};
		List<String> listData = Arrays.asList(sampleData);
		NorthSouthDataPreparation dataPreparator = new NorthSouthDataPreparation();
		dataPreparator.setInputData(listData);
		
		// call
		dataPreparator.prepareData();
	}
	
	@Test(expected=CorruptedDataException.class)
	public void testPrepareData_corruptedNorthData_TimeDifferenceIsMore() throws Exception {
		// prepare data
		String[] sampleData = {"B7051179","B7051310"};
		List<String> listData = Arrays.asList(sampleData);
		NorthSouthDataPreparation dataPreparator = new NorthSouthDataPreparation();
		dataPreparator.setInputData(listData);
		
		// call
		dataPreparator.prepareData();
	}
	
	@Test
	public void testPrepareData_checkOnlyNorthBoundTransactionProperties_sameDays() throws Exception {
		// prepare data
		/* For Data :A7051179 day:1:1:57:31
		   For Data :A7051310 day:1:1:57:31 */
		String[] sampleData = {"A7051179","A7051310"};
		List<String> listData = Arrays.asList(sampleData);
		NorthSouthDataPreparation dataPreparator = new NorthSouthDataPreparation();
		dataPreparator.setInputData(listData);
		
		// call
		List<VehicleData> list = dataPreparator.prepareData();
		
		//verify
		assertEquals(list.size(), 1); 
		assertEquals(list.get(0).getTransactions(), "A7051310$A7051179");
		assertEquals(list.get(0).getVehicleTiming().getDay(),1);
		assertEquals(list.get(0).getVehicleTiming().getHour(),1);
		assertEquals(list.get(0).getVehicleTiming().getMin(),57);
		assertEquals(list.get(0).getVehicleTiming().getSecond(),31);
		assertEquals(list.get(0).getVehicleTiming().getMili(),310);
		assertEquals(list.get(0).getDirection(),VehicleData.DIRECTION.NORTH);
	}
	
	@Test
	public void testPrepareData_checkOnlyNorthBoundTransactionProperties_differentDays() throws Exception {
		// prepare data
		/*  For Data :A86335139 day:1:23:58:55
			For Data :A86335248 day:1:23:58:55
			For Data :A457868 day:2:0:7:37
			For Data :A457996 day:2:0:7:37
		*/
		String[] sampleData = {"A86335139","A86335248","A457868","A457996"};
		List<String> listData = Arrays.asList(sampleData);
		NorthSouthDataPreparation dataPreparator = new NorthSouthDataPreparation();
		dataPreparator.setInputData(listData);
		
		// call
		List<VehicleData> list = dataPreparator.prepareData();
		
		//verify that it can understand consecutive days for North bound travel
		assertEquals(list.size(), 2); 
		
		// for first day
		assertEquals(list.get(0).getTransactions(), "A86335248$A86335139");
		assertEquals(list.get(0).getVehicleTiming().getDay(),1);
		assertEquals(list.get(0).getVehicleTiming().getHour(),23);
		assertEquals(list.get(0).getVehicleTiming().getMin(),58);
		assertEquals(list.get(0).getVehicleTiming().getSecond(),55);
		assertEquals(list.get(0).getVehicleTiming().getMili(),248);
		assertEquals(list.get(0).getDirection(),VehicleData.DIRECTION.NORTH);
		
		// for second day
		assertEquals(list.get(1).getTransactions(), "A457996$A457868");
		assertEquals(list.get(1).getVehicleTiming().getDay(),2);
		assertEquals(list.get(1).getVehicleTiming().getHour(),0);
		assertEquals(list.get(1).getVehicleTiming().getMin(),7);
		assertEquals(list.get(1).getVehicleTiming().getSecond(),37);
		assertEquals(list.get(1).getVehicleTiming().getMili(),996);
		assertEquals(list.get(1).getDirection(),VehicleData.DIRECTION.NORTH);
	}
	
	
	@Test(expected=CorruptedDataException.class)
	public void testPrepareData_corruptedSouthData_TimeDifferenceIsMore() throws Exception {
		// prepare data
		/*  For Data :A638379 day:1:0:10:38
			For Data :B638382 day:1:0:10:38
			For Data :A638520 day:1:0:10:38
			For Data :B638523 day:1:0:10:38
		*/
		String[] sampleData = {"A638379","B638387","A638520","B638523"};
		List<String> listData = Arrays.asList(sampleData);
		NorthSouthDataPreparation dataPreparator = new NorthSouthDataPreparation();
		dataPreparator.setInputData(listData);
		
		// call
		dataPreparator.prepareData();
	}
	
	
	@Test
	public void testPrepareData_checkOnlySouthBoundTransactionProperties_sameDays() throws Exception {
		// prepare data
		/*  For Data :A638379 day:1:0:10:38
			For Data :B638382 day:1:0:10:38
			For Data :A638520 day:1:0:10:38
			For Data :B638523 day:1:0:10:38
		*/
		String[] sampleData = {"A638379","B638382","A638520","B638523"};
		List<String> listData = Arrays.asList(sampleData);
		NorthSouthDataPreparation dataPreparator = new NorthSouthDataPreparation();
		dataPreparator.setInputData(listData);
		
		// call
		List<VehicleData> list = dataPreparator.prepareData();
		
		//verify
		assertEquals(list.size(), 1); 
		assertEquals(list.get(0).getTransactions(), "B638523$A638520$B638382$A638379");
		assertEquals(list.get(0).getVehicleTiming().getDay(),1);
		assertEquals(list.get(0).getVehicleTiming().getHour(),0);
		assertEquals(list.get(0).getVehicleTiming().getMin(),10);
		assertEquals(list.get(0).getVehicleTiming().getSecond(),38);
		assertEquals(list.get(0).getVehicleTiming().getMili(),523);
		assertEquals(list.get(0).getDirection(),VehicleData.DIRECTION.SOUTH);
	}
	
	@Test
	public void testPrepareData_checkOnlySouthBoundTransactionProperties_differentDays() throws Exception {
		// prepare data
		/*  
		    For Data :A638379 day:1:0:10:38
			For Data :B638382 day:1:0:10:38
			For Data :A638520 day:1:0:10:38
			For Data :B638523 day:1:0:10:38
			
			For Data :A30881599 day:2:8:34:41
			For Data :B30881602 day:2:8:34:41
			For Data :A30881738 day:2:8:34:41
			For Data :B30881740 day:2:8:34:41

		*/
		String[] sampleData = {"A30881599","B30881602","A30881738","B30881740","A638379","B638382","A638520","B638523"};
		List<String> listData = Arrays.asList(sampleData);
		NorthSouthDataPreparation dataPreparator = new NorthSouthDataPreparation();
		dataPreparator.setInputData(listData);
		
		// call
		List<VehicleData> list = dataPreparator.prepareData();
		
		//verify
		assertEquals(list.size(), 2); 
		
		// first day data
		assertEquals(list.get(0).getTransactions(), "B30881740$A30881738$B30881602$A30881599");
		assertEquals(list.get(0).getVehicleTiming().getDay(),1);
		assertEquals(list.get(0).getVehicleTiming().getHour(),8);
		assertEquals(list.get(0).getVehicleTiming().getMin(),34);
		assertEquals(list.get(0).getVehicleTiming().getSecond(),41);
		assertEquals(list.get(0).getVehicleTiming().getMili(),740);
		assertEquals(list.get(0).getDirection(),VehicleData.DIRECTION.SOUTH);
		
		// second day data
		assertEquals(list.get(1).getTransactions(), "B638523$A638520$B638382$A638379");
		assertEquals(list.get(1).getVehicleTiming().getDay(),2);
		assertEquals(list.get(1).getVehicleTiming().getHour(),0);
		assertEquals(list.get(1).getVehicleTiming().getMin(),10);
		assertEquals(list.get(1).getVehicleTiming().getSecond(),38);
		assertEquals(list.get(1).getVehicleTiming().getMili(),523);
		assertEquals(list.get(1).getDirection(),VehicleData.DIRECTION.SOUTH);
	}
}
