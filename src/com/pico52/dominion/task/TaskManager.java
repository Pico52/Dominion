package com.pico52.dominion.task;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.lang.time.DateUtils;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

/** 
 * <b>TaskManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class TaskManager
 * <br>
 * <br>
 * Controller for task scheduling.
 */
public class TaskManager {
	private static Dominion plugin;
	private static Logger log;
	private int productionTask = -1;
	private int unitTask = -1;
	private int spellTask = -1;
	
	/** 
	 * <b>TaskManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public TaskManager({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link TaskManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public TaskManager(Dominion instance){
		plugin = instance;
		log = plugin.getLogger();
	}
	
	/** 
	 * <b>onDisable</b><br>
	 * <br>
	 * &nbsp;&nbsp;public void onDisable()
	 * <br>
	 * <br>
	 * Closes all tasks related to the plugin.
	 */
	public void onDisable(){
		plugin.getServer().getScheduler().cancelTasks(plugin);
	}
	
	/** 
	 * <b>startTimers</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean startTimers()
	 * <br>
	 * <br>
	 * Starts all task timers.
	 * @return True if all timers have been started.  False if at least one of them has not.
	 */
	public boolean startTimers(){
		long productionDelay = 0, unitDelay = 0, spellDelay = 0;
		if(DominionSettings.productionTimerWaitToNextHour){
			Date now = new Date();
			Date nextHour = DateUtils.addHours(now, 1);
			nextHour = DateUtils.truncate(nextHour, Calendar.HOUR);
			productionDelay = nextHour.getTime() - now.getTime();
			productionDelay /= 50; // In server ticks. /1000 for milliseconds to seconds, but * 20 for server ticks.
		}
		// - In the future, this will be every 10 minutes.
		if(DominionSettings.unitTimerWaitToNextMinute){
			Date now = new Date();
			Date nextMinute = DateUtils.addMinutes(now, 1);
			nextMinute = DateUtils.truncate(nextMinute,  Calendar.MINUTE);
			unitDelay = nextMinute.getTime() - now.getTime();
			unitDelay /= 50; // In server ticks. /1000 for milliseconds to seconds, but * 20 for server ticks.
		}
		if(DominionSettings.spellTimerWaitToNextMinute){
			Date now = new Date();
			Date nextMinute = DateUtils.addMinutes(now, 1);
			nextMinute = DateUtils.truncate(nextMinute,  Calendar.MINUTE);
			spellDelay = nextMinute.getTime() - now.getTime();
			spellDelay /= 50; // In server ticks. /1000 for milliseconds to seconds, but * 20 for server ticks.
		}
		boolean success = true;
		productionTask = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new ProductionTask(plugin), productionDelay, DominionSettings.productionTaskTime * 20);
		if(productionTask  == -1){
			log.info("Could not schedule the repeating production task!");
			success = false;
		} else
			log.info("Repeating production task scheduled.");
		
		unitTask = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new UnitTask(plugin), unitDelay, DominionSettings.unitTaskTime * 20);
		if(unitTask == -1){
			log.info("Could not schedule the repeating unit task.");
			success = false;
		} else
			log.info("Repeating unit task scheduled.");
		
		spellTask = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new SpellTask(plugin), spellDelay, DominionSettings.spellTaskTime * 20);
		if(spellTask == -1){
			log.info("Could not schedule the repeating spell task.");
			success = false;
		} else
			log.info("Repeating spell task scheduled.");
		
		return success;
	}
	
	/** 
	 * <b>isProductionTaskRunning</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isProductionTaskRunning()
	 * <br>
	 * <br>
	 * @return True if the production task timer is running.  False if it is not.
	 */
	public boolean isProductionTaskRunning(){
		return productionTask != -1;
	}
	
	/** 
	 * <b>isUnitTaskRunning</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isUnitTaskRunning()
	 * <br>
	 * <br>
	 * @return True if the unit task timer is running.  False if it is not.
	 */
	public boolean isUnitTaskRunning(){
		return unitTask != -1;
	}
	
	/** 
	 * <b>isSpellTaskRunning</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isSpellTaskRunning()
	 * <br>
	 * <br>
	 * @return True if the spell task timer is running.  False if it is not.
	 */
	public boolean isSpellTaskRunning(){
		return spellTask != -1;
	}
	
	/** 
	 * <b>getProductionTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getProductionTask()
	 * <br>
	 * <br>
	 * @return The task number for the production task.
	 */
	public int getProductionTask(){
		return productionTask;
	}
	
	/** 
	 * <b>getUnitTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getUnitTask()
	 * <br>
	 * <br>
	 * @return The task number for the unit task.
	 */
	public int getUnitTask(){
		return unitTask;
	}
	
	/** 
	 * <b>getSpellTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getSpellTask()
	 * <br>
	 * <br>
	 * @return The task number for the spell task.
	 */
	public int getSpellTask(){
		return spellTask;
	}
}
