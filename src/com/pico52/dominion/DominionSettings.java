package com.pico52.dominion;

public class DominionSettings {
	
	public static int productionTaskTime, unitTaskTime, spellTaskTime;
	public static boolean productionTimerWaitToNextHour, unitTimerWaitToNextMinute, 
	spellTimerWaitToNextMinute;

	public static void setDefaults(){
		productionTaskTime = 3600;
		unitTaskTime = 600;
		spellTaskTime = 600;
		productionTimerWaitToNextHour = true;
		unitTimerWaitToNextMinute = true;
		spellTimerWaitToNextMinute = true;
	}
}
