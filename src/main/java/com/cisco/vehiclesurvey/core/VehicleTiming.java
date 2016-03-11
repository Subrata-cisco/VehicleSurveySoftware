package com.cisco.vehiclesurvey.core;
/**
 * Keeps the day,hour,minute,second etc timing data.
 * @author Subrata Saha
 *
 */
public class VehicleTiming implements Comparable<VehicleTiming> {
	
	private byte day = 0;
	private byte hour = 0;
	private byte min = 0;
	private byte second = 0;
	private int mili = 0;
	
	
	public void setTiming(byte d,byte h,byte m,byte s,int mili) {
		this.day = d;
		this.hour = h;
		this.min = m;
		this.second = s;
		this.mili = mili;
	}


	public byte getDay() {
		return day;
	}


	public byte getHour() {
		return hour;
	}


	public byte getMin() {
		return min;
	}


	public byte getSecond() {
		return second;
	}


	public int getMili() {
		return mili;
	}


	@Override
	public int compareTo(VehicleTiming timing) {
		int compareResult;
		
		compareResult = new Byte(hour).compareTo(new Byte(
				timing.getHour()));
		
		if (compareResult == 0){
			compareResult = new Byte(min).compareTo(new Byte(
					timing.getMin()));
		}
		
		if (compareResult == 0){
			compareResult = new Byte(second)
					.compareTo(new Byte(timing.getSecond()));
		}
		
		return compareResult;
	}


	@Override
	public String toString() {
		return " [day=" + day + ", hour=" + hour + ", min=" + min
				+ ", second=" + second + ", milisecond=" + mili + "]";
	}
	
	
	
}
