package com.cisco.vehiclesurvey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.cisco.vehiclesurvey.analyser.AbstractDataFilterTest;
import com.cisco.vehiclesurvey.analyser.TimeBucketPredicateTest;
import com.cisco.vehiclesurvey.core.NorthSouthDataPreparationTest;
import com.cisco.vehiclesurvey.core.VehicleSurveyDataBuilderTest;
import com.cisco.vehiclesurvey.query.AbstractDataModellerTest;
import com.cisco.vehiclesurvey.query.VehicleCountQueryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AbstractDataFilterTest.class,
	NorthSouthDataPreparationTest.class,
	TimeBucketPredicateTest.class,
	VehicleSurveyDataBuilderTest.class,
	AbstractDataModellerTest.class,
	VehicleCountQueryTest.class
})
/**
 * Test suite for all th etest classes.
 * @author Subrata Saha
 *
 */
public class VehicleAnalysingSoftwareTestSuite {
	
}
