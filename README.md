--------------------------------------
Problem to Solve:VehicleSurveySoftware
--------------------------------------
Vehicle Survey Code Challenge
A small city government recently bought a vehicle counter. In order for the vehicle counter
to work, pneumatic rubber hoses are stretched across the road. Data is produced by the
vehicle counter as traffic drives across the hoses. The city government requires a program
to interpret the data that the machine produces.
The data from the machine looks like this:
! A268981
! A269123
! A604957
! B604960
! A605128
! B605132
! A1089807
! B1089810
! A1089948
! B1089951
The numbers are the number of milliseconds since midnight when the mark occurred.
Thus, the first line above represents a pair of tires driving by at 12:04:28am. The second
line represents another pair of tires going by 142ms later (almost certainly the 2nd axle of
the car).
The vehicle counter has two pneumatic rubber hoses - one stretches across both lanes of
traffic, and one goes just across traffic in one direction. Each hose independently records
when tires drive over it. As such, cars going in one direction (say, northbound) only record
on one sensor (preceded with an 'A'), while cars going in the direction (say, southbound)
are recorded on both sensors. Lines 3-6 above represent a second car going in the other
direction. The first set of tires hit the 'A' sensor at 12:10:04am, and then hit the 'B' sensor
3ms later. The second set of tires then hit the 'A' sensor 171ms later, and then the 'B'
sensor 4ms later.
The machine was left to run for 5 days in a row (starting on a Monday). This is obvious
because the times in the data make several sudden drops:
! A86328771
! B86328774
! A86328899
! B86328902
! A582668
! B582671
! A582787
! B582789
The city has asked you to see how many analysis features you can provide:
• Total vehicle counts in each direction: morning versus evening, per hour, per half
hour, per 20 minutes, and per 15 minutes.
• The above counts can be displayed for each day of the session, or you can see
averages across all the days.
• Peak volume times.

----------------------
Running this Program:
----------------------

1.	Import the Maven project from the Zip file.
2.	Requires java 8 runtime.
3.	com.aconex.vehiclesurvey.Client will execute the program.
4.  Populate path variable of the Client to invoke the program.
4.	com.aconex.vehiclesurvey.VehicleAnalysingSoftwareTestSuite for running all test cases.

-------------------
Design Decisions:
-------------------

1.	Read the vehicle data from a file.
2.	Convert it to a travel transaction.
3.	Travel transaction is of North/South bound.
4.	Create a bucket for each direction.
5.	Create a bucket of five days for each direction-bucket .
6.	Create a bucket of time in each day-bucket.
7.	Create a list of Vehicle data for each such time-bucket.
8.	Find out a way to create different queries on this prepared data model.
9.	Return an object which can give related query result.
10.	There can be different queries. (e.g vehicle count and speed between cars etc.)
11. Design should flexible enough to take future enhancements like 
   (Reading file from web instead of a file, different kind of query , different algo itself etc)

-------------------
My TDD Approach:
-------------------
1. Started with the classes where most important business logic is written.
   Where we think and think again and dont miss any use case (future bug)
2. Due to high TDD cost , I have less focused on testing the design flows
   rather than algorithm flows.[Algorithm is must, design follows]
3. Started TDD here with most important 2 class NorthSouthDataPreparation.java  
   and AbstractDataFilter.java 
4. Have convincing reason for not writing the Junit for all classes.
5. Junit should not have method description but just for readability and quick understanding.Dont write test case just for writing.
6. Code wrtitten in Junit to test code should be avoided but we should try to test the code with real data and then think about mocking.
   (This use case ought to be a more data specific rather than mocking specific.)
7.TDD is for more thinking even before writing the code, so start with file descrition and write what this class should do, nothing more than that.

-------------------
Coding standards:
-------------------
1. Tried saving every byte and choosen the primitive data type needed.So lots of byte variable in the code.
2. Used Java 8 Filter/Predicate/lamda API and saved writing big fat logic.
3. Made code very loose coupled by introducing delegation and single responsibilty.
4. Tried capturing all possible user errors in terms of dedicated exception.
5.Variable names are highly debatable, just following my instinct.

---------------------------------------
CRC cards: (Few Important classes only)
---------------------------------------
NorthSouthDataPreparation.java :
1. From List of Vehicle data , it concatenates all the data which belong to same transaction.
2. It also save the data as VehicleData object and does parsing of time and convert them in 
   days,hour,minute,second and saves them in VehicleTiming object inside VehicleData.
3. It can throw CorruptedDataException if the difference in times between front and rear tyres is not according to rule.

AbstractDataFilter.java:
1. Data prepared by NorthSouthDataPreparation is used here to filter the data according to the user query.
2. It creates a list like this - Direction List { Day List { Hour List {Possible vehicle data by timing}}}

VehicleSurveyDataBuilder.java:
1. Client uses this class to create the builder and inturn gets the analyser object on which query can be applied.

DataRetrieverFactory.java:
1. This is used to read the data , presently it can read the data from File using FileDataRetriever.java but 
if we want we can add separate implementation for reading the data from different medium.

TimeBucketPredicate.java:
1. Used to determine the condition for which data falls into a time bucket.

VehicleData.java,VehicleTiming.java :
1. For Storing the transaction along with their direction , and timing details.

AbstractDataModeller.java:
1. This class is used to model the data and to create QueryResult once filtering is done.

-------------------------------
Would like to do the following:
-------------------------------
1. Discuss with others about the design and algorithm of this problem and enhance it.
2. Performance measurement and profiling.

-------------------------------
No of modules and classes:
-------------------------------
analyser - 4 
core - 11
dataretriever - 4
query - 10
client - 1
Junit - 6
-----------------
Total  - 36
