package com.pico52.dominion.object.building;

/** 
 * <b>Building</b><br>
 * <br>
 * &nbsp;&nbsp;interface Building
 * <br>
 * <br>
 * Interface class for all buildings.
 */
public abstract class Building {
	
	public int workers;
	public float defense;
	protected boolean defenseBase, structure;
	
	/** 
	 * <b>Building</b><br>
	 * <br>
	 * &nbsp;&nbsp;protected Building()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Building} abstract class.
	 */
	protected Building(){
		this(0, 0, false, true);
	}

	/** 
	 * <b>Building</b><br>
	 * <br>
	 * &nbsp;&nbsp;protected Building(int defense, int workers)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Building} abstract class.
	 * @param defense - The base defense this building provides.
	 * @param workers - The maximum number of workers that may work here.
	 */
	protected Building(float defense, int workers){
		this(defense, workers, false);
	}
	
	/** 
	 * <b>Building</b><br>
	 * <br>
	 * &nbsp;&nbsp;protected Building(int defense, int workers, boolean structure)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Building} abstract class.
	 * @param defense - The base defense this building provides.
	 * @param workers - The maximum number of workers that may work here.
	 * @param structure - Whether or not this building is a structure (can be built outside of a city).
	 */
	protected Building(float defense, int workers, boolean structure){
		this(defense, workers, structure, true);
	}
	
	/** 
	 * <b>Building</b><br>
	 * <br>
	 * &nbsp;&nbsp;protected Building(int defense, int workers, boolean structure, boolean defenseBase)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Building} abstract class.
	 * @param defense - The base defense this building provides.
	 * @param workers - The maximum number of workers that may work here.
	 * @param structure - Whether or not this building is a structure (can be built outside of a city).
	 * @param defenseBase - Whether or not the building defense goes by level.  True for flat, false for by level.
	 */
	protected Building(float defense, int workers, boolean structure, boolean defenseBase){
		this.defense = defense;
		this.workers = workers;
		this.structure = structure;
		this.defenseBase = defenseBase;
	}
	
	/** 
	 * <b>isStructure</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isStructure()
	 * <br>
	 * <br>
	 * @return True if the building is a structure (can be built outside of a city).  False if it is not.
	 */
	public boolean isStructure(){
		return structure;
	}
	
	/** 
	 * <b>isDefenseBase</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isDefenseBase()
	 * <br>
	 * <br>
	 * @return True if the building's defense is added irrespective of level.  False if defense is by level.
	 */
	public boolean isDefenseBase(){
		return defenseBase;
	}
	
	/** 
	 * <b>getProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getProduction()
	 * <br>
	 * <br>
	 * @return The rate that this building produces the specified resource every building tick.
	 */
	public abstract double getProduction(String resource);
}
