package com.pico52.dominion.task;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.SpellManager;

/** 
 * <b>SpellTask</b><br>
 * <br>
 * &nbsp;&nbsp;public class SpellTask extends {@link DominionTimerTask}
 * <br>
 * <br>
 * Controller for the spell task.
 */
public class SpellTask extends DominionTimerTask{
	private SpellManager spellManager;

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
		db = plugin.getDBHandler();
		spellManager = plugin.getSpellManager();
		plugin.getLogger().info("Spells tick..");
		if(DominionSettings.broadcastSpellTick)
			plugin.getServer().broadcastMessage(plugin.getLogPrefix() + "Spell tick..");
		advanceSpells();
		removeFinishedSpells();
	}
	
	public void advanceSpells(){
		ResultSet spells = db.getTableData("spell", "*");
		int spellId, duration;
		try{
			while(spells.next()){
				spellId = spells.getInt("spell_id");
				duration = spells.getInt("duration");
				duration--;
				db.update("spell", "duration", duration, "spell_id", spellId);
				spellManager.castRecurringEffect(spellId);
			}	
			spells.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while advancing spells.");
			ex.printStackTrace();
		}
	}
	
	public void removeFinishedSpells(){
		ResultSet spells = db.getTableData("spell", "*", "duration <= 0");
		int spellId, duration;
		try{
			while(spells.next()){
				spellId = spells.getInt("spell_id");
				duration = spells.getInt("duration");
				if(duration <= 0)
					spellManager.removeSpell(spellId);
			}
			spells.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while removing finished spells.");
			ex.printStackTrace();
		}
	}
}
