package com.cisco.vehiclesurvey.analyser;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.modules.junit4.PowerMockRunner;

import com.cisco.vehiclesurvey.core.Constants;
import com.cisco.vehiclesurvey.core.NorthSouthDataPreparation;
import com.cisco.vehiclesurvey.core.VehicleData;
import com.cisco.vehiclesurvey.core.VehicleTiming;
/**
 * Possible use case. 
 * 1.Create a bucket which contains sorted data according to direction from input values.
 * 2.Assume time given 60 minute, so create days list (8.e for 5 days) and in each day will have 24 List containing vehicle data.
 *   So we have Direction List { Day List { Hour List {Possible vehicle data}}}
 * @author Subrata Saha 
 *
 */
@RunWith(PowerMockRunner.class) 
public class AbstractDataFilterTest extends TestCase {
	
	DataFilter dataFilter = null;
	List<VehicleData> list = null;
	String[] sampleData = { "A98186", "A98333", "A499718", "A499886", "A638379",
			"B638382", "A638520", "B638523", "A1016488", "A1016648",
			"A1058535", "B1058538", "A1058659", "B1058662", "A1201386",
			"B1201389", "A1201539", "B1201542", "A1624044", "B1624047",
			"A1624188", "B1624191", "A1782481", "B1782484", "A1782660",
			"B1782664", "A2146213", "A2146349", "A2211004", "A2211128",
			"A2422884", "B2422887", "A2423013", "B2423016", "A2597513",
			"B2597516", "A2597667", "B2597670", "A2695213", "A2695369",
			"A3280268", "B3280271", "A3280386", "B3280388", "A3318189",
			"A3318355", "A4021664", "B4021667", "A4021806", "B4021809",
			"A4455194", "B4455196", "A4455327", "B4455330", "A5060792",
			"B5060795", "A5060911", "B5060913", "A5411133", "B5411136",
			"A5411294", "B5411297", "A5508987", "B5508991", "A5509166",
			"B5509170", "A5810441", "B5810444", "A5810600", "B5810603",
			"A6383154", "B6383158", "A6383348", "B6383352", "A7051179",
			"A7051310", "A7522142", "B7522145", "A7522265", "B7522267",
			"A8368330", "A8368477" };
	
	/* "For Data :A98186 day:1:0:1:38","For Data :A98333 day:1:0:1:38","For Data :A499718 day:1:0:8:19",
	"For Data :A499886 day:1:0:8:19","For Data :A638379 day:1:0:10:38","For Data :B638382 day:1:0:10:38",
	"For Data :A638520 day:1:0:10:38","For Data :B638523 day:1:0:10:38","For Data :A1016488 day:1:0:16:56",
	"For Data :A1016648 day:1:0:16:56","For Data :A1058535 day:1:0:17:38","For Data :B1058538 day:1:0:17:38",
	"For Data :A1058659 day:1:0:17:38","For Data :B1058662 day:1:0:17:38","For Data :A1201386 day:1:0:20:1",
	"For Data :B1201389 day:1:0:20:1","For Data :A1201539 day:1:0:20:1","For Data :B1201542 day:1:0:20:1",
	"For Data :A1624044 day:1:0:27:4","For Data :B1624047 day:1:0:27:4","For Data :A1624188 day:1:0:27:4",
	"For Data :B1624191 day:1:0:27:4","For Data :A1782481 day:1:0:29:42","For Data :B1782484 day:1:0:29:42",
	"For Data :A1782660 day:1:0:29:42","For Data :B1782664 day:1:0:29:42","For Data :A2146213 day:1:0:35:46",
	"For Data :A2146349 day:1:0:35:46","For Data :A2211004 day:1:0:36:51","For Data :A2211128 day:1:0:36:51",
	"For Data :A2422884 day:1:0:40:22","For Data :B2422887 day:1:0:40:22","For Data :A2423013 day:1:0:40:23",
	"For Data :B2423016 day:1:0:40:23","For Data :A2597513 day:1:0:43:17","For Data :B2597516 day:1:0:43:17",
	"For Data :A2597667 day:1:0:43:17","For Data :B2597670 day:1:0:43:17","For Data :A2695213 day:1:0:44:55",
	"For Data :A2695369 day:1:0:44:55","For Data :A3280268 day:1:0:54:40","For Data :B3280271 day:1:0:54:40",
	"For Data :A3280386 day:1:0:54:40","For Data :B3280388 day:1:0:54:40","For Data :A3318189 day:1:0:55:18",
	"For Data :A3318355 day:1:0:55:18","For Data :A4021664 day:1:1:7:1","For Data :B4021667 day:1:1:7:1",
	"For Data :A4021806 day:1:1:7:1","For Data :B4021809 day:1:1:7:1","For Data :A4455194 day:1:1:14:15",
	"For Data :B4455196 day:1:1:14:15","For Data :A4455327 day:1:1:14:15","For Data :B4455330 day:1:1:14:15",
	"For Data :A5060792 day:1:1:24:20","For Data :B5060795 day:1:1:24:20","For Data :A5060911 day:1:1:24:20",
	"For Data :B5060913 day:1:1:24:20","For Data :A5411133 day:1:1:30:11","For Data :B5411136 day:1:1:30:11",
	"For Data :A5411294 day:1:1:30:11","For Data :B5411297 day:1:1:30:11","For Data :A5508987 day:1:1:31:48",
	"For Data :B5508991 day:1:1:31:48","For Data :A5509166 day:1:1:31:49","For Data :B5509170 day:1:1:31:49",
	"For Data :A5810441 day:1:1:36:50","For Data :B5810444 day:1:1:36:50","For Data :A5810600 day:1:1:36:50",
	"For Data :B5810603 day:1:1:36:50","For Data :A6383154 day:1:1:46:23","For Data :B6383158 day:1:1:46:23",
	"For Data :A6383348 day:1:1:46:23","For Data :B6383352 day:1:1:46:23","For Data :A7051179 day:1:1:57:31",
	"For Data :A7051310 day:1:1:57:31","For Data :A7522142 day:1:2:5:22","For Data :B7522145 day:1:2:5:22",
	"For Data :A7522265 day:1:2:5:22","For Data :B7522267 day:1:2:5:22","For Data :A8368330 day:1:2:19:28",
	"For Data :A8368477 day:1:2:19:28" */
	
	@Before
	public void setUp() throws Exception {
		dataFilter = new DataFilter();
		List<String> listData = Arrays.asList(sampleData);
		NorthSouthDataPreparation dataPreparator = new NorthSouthDataPreparation();
		dataPreparator.setInputData(listData);
		list = dataPreparator.prepareData();
	}
	
	@After
	public void tearDown() throws Exception {
		dataFilter = null;
		if(list != null){
			list.clear();
			list = null;
		}
	}
	
	@Test
	public void testCreateFilteredData_directionList() throws Exception {
		// call
		dataFilter.createFilteredData(list);
		
		@SuppressWarnings("unchecked")
		List<List<List<VehicleData>>> dirList = (List<List<List<VehicleData>>>) Whitebox
				.getInternalState(dataFilter, "northAndSouthBoundData");
		
		//verify
		assertEquals(dirList.size(), 2);
	}
	
	@Test
	public void testCreateFilteredData_FirstListOnlyForNorthDirection() throws Exception {
		// call
		dataFilter.createFilteredData(list);
		
		@SuppressWarnings("unchecked")
		List<List<List<VehicleData>>> dirList = (List<List<List<VehicleData>>>) Whitebox
				.getInternalState(dataFilter, "northAndSouthBoundData");
		
		//verify
		List<List<VehicleData>> northOnlyData = dirList.get(0);
		assertTrue(checkIfCorrectDirection(northOnlyData,VehicleData.DIRECTION.NORTH));
	}
	
	@Test
	public void testCreateFilteredData_SecondListOnlyForSouthDirection() throws Exception {
		// call
		dataFilter.createFilteredData(list);
		
		@SuppressWarnings("unchecked")
		List<List<List<VehicleData>>> dirList = (List<List<List<VehicleData>>>) Whitebox
				.getInternalState(dataFilter, "northAndSouthBoundData");
		
		//verify
		List<List<VehicleData>> southOnlyData = dirList.get(1);
		assertTrue(checkIfCorrectDirection(southOnlyData,VehicleData.DIRECTION.SOUTH));
	}
	
	@Test
	public void testCreateFilteredData_NorthListHasFiveDays() throws Exception {
		// call
		dataFilter.createFilteredData(list);
		
		@SuppressWarnings("unchecked")
		List<List<List<VehicleData>>> dirList = (List<List<List<VehicleData>>>) Whitebox
				.getInternalState(dataFilter, "northAndSouthBoundData");
		
		//verify
		List<List<VehicleData>> northOnlyData = dirList.get(0);
		assertEquals(northOnlyData.size(), 5);
	}
	
	@Test
	public void testCreateFilteredData_SouthListHasFiveDays() throws Exception {
		// call
		dataFilter.createFilteredData(list);
		
		@SuppressWarnings("unchecked")
		List<List<List<VehicleData>>> dirList = (List<List<List<VehicleData>>>) Whitebox
				.getInternalState(dataFilter, "northAndSouthBoundData");
		
		//verify
		List<List<VehicleData>> southOnlyData = dirList.get(1);
		assertEquals(southOnlyData.size(), 5);
	}
	
	@Test
	public void testCreateFilteredData_NorthListDataFallsIntoCorrectDayBucket() throws Exception {
		// call
		dataFilter.createFilteredData(list);
		
		@SuppressWarnings("unchecked")
		List<List<List<VehicleData>>> dirList = (List<List<List<VehicleData>>>) Whitebox
				.getInternalState(dataFilter, "northAndSouthBoundData");
		
		//verify
		List<List<VehicleData>> northOnlyData = dirList.get(0);
		assertEquals(northOnlyData.get(0).size(), 9);   // Total 82 records = 9*2 + 16*4
		assertEquals(northOnlyData.get(1).size(), 0);   // all the given data falls into first day
		assertEquals(northOnlyData.get(2).size(), 0);
		assertEquals(northOnlyData.get(3).size(), 0);
		assertEquals(northOnlyData.get(4).size(), 0);
	}
	
	@Test
	public void testCreateFilteredData_SouthListDataFallsIntoCorrectDayBucket() throws Exception {
		// call
		dataFilter.createFilteredData(list);
		
		@SuppressWarnings("unchecked")
		List<List<List<VehicleData>>> dirList = (List<List<List<VehicleData>>>) Whitebox
				.getInternalState(dataFilter, "northAndSouthBoundData");
		
		//verify
		List<List<VehicleData>> southOnlyData = dirList.get(1);
		assertEquals(southOnlyData.get(0).size(), 16); // Total 82 records = 9*2 + 16*4
		assertEquals(southOnlyData.get(1).size(), 0);  // all the given data falls into first day
		assertEquals(southOnlyData.get(2).size(), 0);
		assertEquals(southOnlyData.get(3).size(), 0);
		assertEquals(southOnlyData.get(4).size(), 0);
	}
	
	@Test
	public void testGetTimeWiseDistribution_listSizesForDirectionDaysAndHours(){
		// call
		dataFilter.createFilteredData(list);
		List<List<List<List<VehicleData>>>> list = (List<List<List<List<VehicleData>>>>) dataFilter.getTimeWiseDataForBothDirection();
		
		//verify first list should contains 2 list for direction, 5 list for days,23 list for each hour
		assertEquals(list.size(), 2);  
		assertEquals(list.get(0).size(), 5);  
		assertEquals(list.get(0).get(0).size(), 23); 
	}
	
	@Test
	public void testGetTimeWiseDistribution_NorthDirectionBucketDataIsCorrect(){
		// call
		dataFilter.createFilteredData(list);
		List<List<List<List<VehicleData>>>> masterList = (List<List<List<List<VehicleData>>>>) dataFilter.getTimeWiseDataForBothDirection();
		
		//verify hour wise bucket for vehicle data is correct for north direction.
		boolean isDataCorrect = ifBucketDataIsCorrectAcrossDirDayHourBucketIsCorrect(VehicleData.DIRECTION.NORTH,masterList);
		assertTrue(isDataCorrect);
	}
	
	@Test
	public void testGetTimeWiseDistribution_SouthDirectionBucketDataIsCorrect(){
		// call
		dataFilter.createFilteredData(list);
		List<List<List<List<VehicleData>>>> masterList = (List<List<List<List<VehicleData>>>>) dataFilter.getTimeWiseDataForBothDirection();
		
		//verify hour wise bucket for vehicle data is correct for north direction.
		boolean isDataCorrect = ifBucketDataIsCorrectAcrossDirDayHourBucketIsCorrect(VehicleData.DIRECTION.SOUTH,masterList);
		assertTrue(isDataCorrect);
	}
	
	private boolean ifBucketDataIsCorrectAcrossDirDayHourBucketIsCorrect(VehicleData.DIRECTION dir,List<List<List<List<VehicleData>>>> list){
		boolean isDataCorrect = true;
		List<List<List<VehicleData>>> dirData = null;
		if(dir == VehicleData.DIRECTION.NORTH){
			dirData = (List<List<List<VehicleData>>>)list.get(0);
		}else{
			dirData = (List<List<List<VehicleData>>>)list.get(1);
		}
		
		VehicleTiming currentTiming = getTiming(dataFilter.getTime());
		VehicleTiming initialTiming = new VehicleTiming();
		VehicleTiming previousTiming = new VehicleTiming();
		initialTiming.setTiming((byte)0, currentTiming.getHour(), currentTiming.getMin(), currentTiming.getSecond(), 0);	
		
		for(List<List<VehicleData>> eachDay : dirData){
			for(List<VehicleData> bucket : eachDay){
				
				for(VehicleData data : bucket){
					VehicleTiming timing  = data.getVehicleTiming();
					long incomingTimingValue =	(timing.getHour()* Constants.ONE_HOUR_IN_SECOND 
					    		+ timing.getMin()* Constants.ONE_MINUTE_IN_SECOND
					    		+ timing.getSecond());
					
					long previousTimingValue = previousTiming.getHour()* Constants.ONE_HOUR_IN_SECOND 
		              		+ previousTiming.getMin()* Constants.ONE_MINUTE_IN_SECOND 
		              		+ previousTiming.getSecond();
					
					long currenTimingValue = currentTiming.getHour()* Constants.ONE_HOUR_IN_SECOND
		              		+ currentTiming.getMin()* Constants.ONE_MINUTE_IN_SECOND 
		              		+ currentTiming.getSecond();
					
					if (incomingTimingValue > previousTimingValue
							&& incomingTimingValue <= currenTimingValue) {
						isDataCorrect = true;
					} else {
						isDataCorrect = false;
						break;
					}
				}
				previousTiming.setTiming((byte)0, currentTiming.getHour(), currentTiming.getMin(), currentTiming.getSecond(), 0);
				createNextBucketTiming(currentTiming, initialTiming); 
			}
			currentTiming = getTiming(dataFilter.getTime());
			previousTiming = new VehicleTiming();
		}
		
		return  isDataCorrect ;
	}

	private boolean checkIfCorrectDirection(List<List<VehicleData>> directionData,VehicleData.DIRECTION dir) {
		boolean isAllSameRequiredData = true;
		for(List<VehicleData> dirData: directionData){
			for(VehicleData dirDataForDay: dirData){
				if(dirDataForDay.getDirection() != dir){
					isAllSameRequiredData = false;
					break;
				}
			}
		}
		return isAllSameRequiredData;
	}
	
	private void createNextBucketTiming(VehicleTiming tempTiming,
			VehicleTiming initialTiming) {
		
		byte minute = 0;
		byte hour = 0;
		
		byte second = (byte) (tempTiming.getSecond() + initialTiming.getSecond());
		
		if(second / Constants.SIXTY > 0){
			minute = (byte) (second / Constants.SIXTY);
			second = (byte) (second % Constants.SIXTY);
		}
		
		minute = (byte) (minute + tempTiming.getMin() + initialTiming.getMin()) ;
		if(minute / Constants.SIXTY > 0){
			hour = (byte) (minute / Constants.SIXTY);
			minute = (byte) (minute % Constants.SIXTY);
		}
		
		hour = (byte) (hour + tempTiming.getHour() + initialTiming.getHour()) ;
		
		tempTiming.setTiming((byte)0, hour, minute, second, 0);
	}
	
	private VehicleTiming getTiming(long val){
		byte hour = 0;
		byte minute = 0;
		
		if (val / Constants.ONE_HOUR_IN_MINUTE >= 0) {
			hour = (byte) (val / Constants.ONE_HOUR_IN_MINUTE); // in hour
			minute = (byte)(val % Constants.ONE_HOUR_IN_MINUTE); // ( contains minute)
		}else{
			minute = (byte)(val);
		}
		
		VehicleTiming timing  = new VehicleTiming();
		timing.setTiming((byte)0, hour, minute, (byte)0, 0); 
		
		return timing;
	}
	
	class DataFilter extends AbstractDataFilter {
		@Override
		public long getTime() {
			return 60;
		}
	}
	
}
