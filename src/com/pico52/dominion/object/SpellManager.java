package com.pico52.dominion.object;

import com.pico52.dominion.Dominion;

public class SpellManager extends DominionObjectManager{

	public SpellManager(Dominion plugin) {
		super(plugin);
	}
	
	public boolean castSpell(int settlementId, int casterId, String spell, int targetId){
		return true;
	}
	
	public boolean isSpell(String spell){
		return false;
	}
}
