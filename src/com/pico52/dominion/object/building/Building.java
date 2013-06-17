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
	
	public int defense, workers;
	private boolean structure;

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
	protected Building(int defense, int workers){
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
	protected Building(int defense, int workers, boolean structure){
		this.defense = defense;
		this.workers = workers;
		this.structure = structure;
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
	 * <b>getProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getProduction()
	 * <br>
	 * <br>
	 * @return The rate that this building produces the specified resource every building tick.
	 */
	public abstract double getProduction(String resource);
}
