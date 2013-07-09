package com.pico52.dominion.task;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

/** 
 * <b>SpellTask</b><br>
 * <br>
 * &nbsp;&nbsp;public class SpellTask extends {@link DominionTimerTask}
 * <br>
 * <br>
 * Controller for the spell task.
 */
public class SpellTask extends DominionTimerTask{

	/** 
	 * <b>SpellTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public SpellTask({@link Dominion} plugin)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link SpellTask} class.
	 * @param instance - The {@link Dominion} plugin this task will be running on.
	 */
	public SpellTask(Dominion plugin){
		super(plugin);
	}
	
	@Override
	public void run() {
		plugin.getLogger().info("Spells tick..");
		if(DominionSettings.broadcastSpellTick)
			plugin.getServer().broadcastMessage(plugin.getLogPrefix() + "Spell tick..");
	}
}
