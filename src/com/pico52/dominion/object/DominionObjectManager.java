package com.pico52.dominion.object;

import com.pico52.dominion.Dominion;

/** 
 * <b>DominionObjectManager</b><br>
 * <br>
 * &nbsp;&nbsp;public abstract class DominionObjectManager
 * <br>
 * <br>
 * Abstract class for all object managers to use..
 */
public abstract class DominionObjectManager {
	protected Dominion plugin;
	
	public DominionObjectManager(Dominion plugin){
		this.plugin = plugin;
	}
}
