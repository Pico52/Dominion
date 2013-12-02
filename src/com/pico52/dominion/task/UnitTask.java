package com.pico52.dominion.task;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.UnitManager;

/** 
 * <b>UnitTask</b><br>
 * <br>
 * &nbsp;&nbsp;public class UnitTask extends {@link DominionTimerTask}
 * <br>
 * <br>
 * Controller for the unit task.
 */
public class UnitTask extends DominionTimerTask{
	private UnitManager unitManager;

	/** 
	 * <b>UnitTask</b><br>
	 * <br>
	 * &nbsp;&nbsp;public UnitTask({@link Dominion} plugin)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link UnitTask} class.
	 * @param instance - The {@link Dominion} plugin this task will be running on.
	 */
	public UnitTask(Dominion plugin){
		super(plugin, plugin.getLogger());
	}
	
	@Override
	public void run() {
		db = plugin.getDBHandler();
		unitManager = plugin.getUnitManager();
		log.info("Unit tick..");
		if(DominionSettings.broadcastUnitTick)
			plugin.getServer().broadcastMessage(plugin.getLogPrefix() + "Unit tick..");
		advanceProductions();
		feedUnits();
		payUnits();
		moveUnits();
		dealDamage();
		removeDeadUnits();
		healCampedUnits();
	}
	
	private void advanceProductions(){
		ResultSet productions = db.getTableData("production", "*");
		int productionId, duration;
		try{
			while(productions.next()){
				productionId = productions.getInt("production_id");
				duration = productions.getInt("duration");
				duration--;
				db.update("production", "duration", duration, "production_id", productionId);
			}	
			productions.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while advancing productions.");
			ex.printStackTrace();
		}
			
		createNewUnits();
	}
	
	private void createNewUnits(){
		ResultSet productions = db.getTableData("production", "*", "duration<1");
		int productionId, ownerId, settlementId;
		String classification;
		try{
			while(productions.next()){
				productionId = productions.getInt("production_id");
				settlementId = productions.getInt("settlement_id");
				ownerId = productions.getInt("owner_id");
				classification = productions.getString("class");
				int newId = unitManager.createUnit(settlementId, ownerId, classification);
				unitManager.commandToCamp(newId);
				db.remove("production", productionId);
			}
			productions.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while creating new units.");
			ex.printStackTrace();
		}
	}
	
	private void feedUnits(){
		ResultSet units = db.getTableData("unit", "*");
		int unitId, settlementId;
		double food, consumption;
		String classType;
		try{
			while(units.next()){
				unitId = units.getInt("unit_id");
				settlementId = units.getInt("settlement_id");
				classType = units.getString("class");
				food = plugin.getSettlementManager().getMaterial(settlementId, "food");
				if(unitManager.getUnitType(classType).equalsIgnoreCase("land"))
					consumption = unitManager.getUnit(classType).foodConsumption;
				else if (unitManager.getUnitType(classType).equalsIgnoreCase("sea"))
					consumption = unitManager.getUnit(classType).foodConsumption;
				else
					consumption = 0;
				if(food - consumption < 0){
					unitManager.kill(unitId, "starvation");
					db.subtractMaterial(settlementId, "food", food);
				} else
					db.subtractMaterial(settlementId, "food", consumption);
			}
			units.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while trying to feed units.");
			ex.printStackTrace();
		}
	}
	
	private void payUnits(){
		ResultSet units = db.getTableData("unit", "*");
		int unitId, settlementId;
		double wealth, consumption;
		String classType;
		try{
			while(units.next()){
				unitId = units.getInt("unit_id");
				settlementId = units.getInt("settlement_id");
				classType = units.getString("class");
				wealth = plugin.getSettlementManager().getMaterial(settlementId, "wealth");
				if(unitManager.getUnitType(classType).equalsIgnoreCase("land"))
					consumption = unitManager.getUnit(classType).upkeep;
				else if (unitManager.getUnitType(classType).equalsIgnoreCase("sea"))
					consumption = unitManager.getUnit(classType).upkeep;
				else
					consumption = 0;
				if(wealth - consumption < 0){
					unitManager.kill(unitId, "payment");
					db.subtractMaterial(settlementId, "wealth", wealth);
				} else
					db.subtractMaterial(settlementId, "wealth", consumption);
			}
			units.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while trying to pay units");
			ex.printStackTrace();
		}
	}
	
	private void moveUnits(){
		ResultSet commands = db.getTableData("command", "*");
		int unitId = 0;
		double toXCoord = 0, toZCoord = 0;
		String command = "";
		try{
			while(commands.next()){
				command = commands.getString("command");
				unitId = commands.getInt("unit_id");
				if(command.equalsIgnoreCase("move")){
					toXCoord = commands.getDouble("xcoord");
					toZCoord = commands.getDouble("zcoord");
				} else if (command.equalsIgnoreCase("attack")){
					toXCoord = unitManager.getTargetX(unitId);
					toZCoord = unitManager.getTargetZ(unitId);
				} else {
					toXCoord = unitManager.getUnitX(unitId);
					toZCoord = unitManager.getUnitZ(unitId);
				}
				unitManager.moveUnitTowards(unitId, toXCoord, toZCoord);
			}
			commands.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while trying to move units.");
			ex.printStackTrace();
		}
	}
	
	private void dealDamage(){
		ResultSet commands = db.getTableData("command", "*", "command=\"attack\"");
		int unitId=0, targetId=0;
		try{
			while(commands.next()){
				unitId = commands.getInt("unit_id");
				targetId = commands.getInt("target_id");
				if(unitManager.withinRange(unitId, targetId))
					unitManager.damage(targetId, unitManager.getUnit(unitManager.getClass(unitId)).offense);
				if(unitManager.getHealth(targetId) <= 0)
					unitManager.commandToCamp(unitId);
			}
			commands.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while trying to damage units.");
			ex.printStackTrace();
		}
	}
	
	private void removeDeadUnits(){
		ResultSet units = db.getTableData("unit", "*", "health<=0");
		int unitId;
		double health;
		try{
			while(units.next()){
				unitId = units.getInt("unit_id");
				health = units.getDouble("health");
				if(health <= 0){
					unitManager.kill(unitId);
				}
			}
			units.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while trying to remove dead units.");
			ex.printStackTrace();
		}
	}
	
	private void healCampedUnits(){
		ResultSet commands = plugin.getDBHandler().getTableData("command", "*", "command=\"camp\"");
		int unitId=0;
		double maxHealth=0, difference=0, healRate=0;
		String classType="";
		try{
			while(commands.next()){
				unitId = commands.getInt("unit_id");
				classType = unitManager.getClass(unitId);
				maxHealth = unitManager.getUnit(classType).health;
				healRate = unitManager.getHealRate(unitId);
				difference = maxHealth * healRate;
				unitManager.heal(unitId, difference);
			}
			commands.getStatement().close();
		} catch (SQLException ex){
			log.info("Error while trying to heal camped units.");
			ex.printStackTrace();
		}
	}
}
