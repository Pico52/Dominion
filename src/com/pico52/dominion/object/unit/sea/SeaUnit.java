package com.pico52.dominion.object.unit.sea;

import com.pico52.dominion.object.unit.Unit;

public class SeaUnit extends Unit{
	protected boolean tradeShip;
	protected boolean warShip;
	protected String shipSize;
	
	public boolean isTradeShip(){
		return tradeShip;
	}
	
	public boolean isWarShip(){
		return warShip;
	}
}
