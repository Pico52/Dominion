package com.pico52.dominion;

public class DominionSettings {
	
	public static int productionTaskTime, unitTaskTime, spellTaskTime, townDefense, cityDefense, fortressDefense;
	public static double unitPickUpFromSettlementRange, unitDropOffInSettlementRange, unitPickUpRange;
	public static boolean productionTimerWaitToNextHour, unitTimerWaitToNextMinute, 
	spellTimerWaitToNextMinute, broadcastProductionTick, broadcastUnitTick, broadcastSpellTick;

	public static void setDefaults(){
		//---UNITS---//
		unitPickUpFromSettlementRange = 100;
		unitDropOffInSettlementRange = 100;
		unitPickUpRange = 10;
		
		//---TIMERS---//
		productionTaskTime = 3600;
		unitTaskTime = 600;
		spellTaskTime = 600;
		productionTimerWaitToNextHour = true;
		unitTimerWaitToNextMinute = true;
		spellTimerWaitToNextMinute = true;
		broadcastProductionTick = true;
		broadcastUnitTick = false;
		broadcastSpellTick = false;
		
		//---SETTLEMENTS---//
		townDefense = 1500;
		cityDefense = 3000;
		fortressDefense = 2500;
	}
}
