package com.pico52.dominion.task;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

/** 
 * <b>ProductionTask</b><br>
 * <br>
 * &nbsp;&nbsp;public class ProductionTask extends {@link DominionTimerTask}
 * <br>
 * <br>
 * Controller for the production task.
 */
public class ProductionTask extends DominionTimerTask{

	/** 
	 * <b>ProductionTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public ProductionTask({@link Dominion} plugin)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link ProductionTask} class.
	 * @param instance - The {@link Dominion} plugin this task will be running on.
	 */
	public ProductionTask(Dominion plugin){
		super(plugin);
	}
	
	@Override
	public void run() {
		plugin.getLogger().info("Production tick..");
		if(DominionSettings.broadcastProductionTick)
			plugin.getServer().broadcastMessage(plugin.getLogPrefix() + "Production tick..");
		plugin.getSettlementManager().updateAll();
	}
}
