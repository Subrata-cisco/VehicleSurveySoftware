package com.cisco.vehiclesurvey.core;
/**
 * Different Constants, We may consider to keep similar type of 
 * constants here and moving the rest to specific files.
 * 
 * @author Subrata Saha
 *
 */
public class Constants {
	
  public static long TWENTY_FOUR_HOUR_IN_MILI = 24 * 60 * 60 * 1000; 
  public static long TWENTY_FOUR_HOUR_IN_SECOND = 24 * 60 * 60 ; 
  public static long ONE_HOUR_IN_SECOND = 60 * 60; 
  public static long ONE_MINUTE_IN_SECOND = 60; 
  public static long ONE_SECOND = 1; 
  public static long SIXTY = 60; 
  
  public static long ONE_HOUR_IN_MINUTE = 60 ; 
  public static long TWENTY_FOUR_HOUR_IN_MINUTE = 24 * 60; 
  public static long TWELVE_HOUR_IN_MINUTE = 12 * 60; 
  
  public static final String STRING_SEPARATOR = "$";
  public static final char CHAR_A = 'A';
  public static final char CHAR_B = 'B';
  
  public static final int TOTAL_NO_OF_DAYS = 5;
  
  /** with 60 KM average speed and distance between the axle , it shall not take more than 300 mili second**/
  public static final int TIME_DIFFERENCE_BY_BOTH_WHEEL_TO_HIT_SAME_RUBBER_HORSE = 281;
  
  /** for 2 different horse it takes max 6 mili second**/
  public static final int TIME_DIFFERENCE_BY_SAME_WHEEL_TO_HIT_DIFFERENT_RUBBER_HORSE = 6;
  
}
