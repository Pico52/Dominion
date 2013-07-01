package com.pico52.dominion.task;

import java.util.TimerTask;
import java.util.logging.Logger;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.db.DominionDatabaseHandler;

/** 
 * <b>DominionTimerTask</b><br>
 * <br>
 * &nbsp;&nbsp;public abstract class DominionTimerTask extends {@link TimerTask}
 * <br>
 * <br>
 * Abstract class for all task controllers.
 */
public abstract class DominionTimerTask extends TimerTask{
	protected Dominion plugin;
	protected DominionDatabaseHandler db;
	protected Logger log;

	/** 
	 * <b>DominionTimerTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public DominionTimerTask({@link Dominion} plugin)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link DominionTimerTask} abstract class.
	 * @param instance - The {@link Dominion} plugin this task will be running on.
	 */
	public DominionTimerTask(Dominion plugin){
		this.plugin = plugin;
	}
	
	/** 
	 * <b>DominionTimerTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public DominionTimerTask({@link Dominion} plugin, {@link DominionDatabaseHandler} db)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link DominionTimerTask} abstract class.
	 * @param instance - The {@link Dominion} plugin this task will be running on.
	 * @param db - The {@link DominionDatabaseHandler} that this task will use to interact with the database.
	 */
	public DominionTimerTask(Dominion plugin, DominionDatabaseHandler db){
		this.plugin = plugin;
		this.db = db;
	}
	
	/** 
	 * <b>DominionTimerTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public DominionTimerTask({@link Dominion} plugin, {@link Logger} log)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link DominionTimerTask} abstract class.
	 * @param instance - The {@link Dominion} plugin this task will be running on.
	 * @param log - The {@link Logger} this task will use to log 
	 */
	public DominionTimerTask(Dominion plugin, Logger log){
		this.plugin = plugin;
		this.log = log;
	}
	
	/** 
	 * <b>DominionTimerTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public DominionTimerTask({@link Dominion} plugin, {@link DominionDatabaseHandler} db)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link DominionTimerTask} abstract class.
	 * @param instance - The {@link Dominion} plugin this task will be running on.
	 * @param db - The {@link DominionDatabaseHandler} this task will use to interact with the database.
	 * @param log - The {@link Logger} this task will use to log and output data.
	 */
	public DominionTimerTask(Dominion plugin, DominionDatabaseHandler db, Logger log){
		this.plugin = plugin;
		this.db = db;
		this.log = log;
	}
}
