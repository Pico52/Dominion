package com.pico52.dominion.object;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.db.DominionDatabaseHandler;

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
	protected DominionDatabaseHandler db;
	
	public DominionObjectManager(Dominion plugin){
		this.plugin = plugin;
		db = plugin.getDBHandler();
	}
}
