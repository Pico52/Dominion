package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pico52.dominion.Dominion;

/** 
 * <b>SquelchManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class SquelchManager extends {@link DominionObjectManager}
 * <br>
 * <br>
 * Controller for squelches.
 */
public class SquelchManager extends DominionObjectManager{

	/** 
	 * <b>SquelchManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public SquelchManager()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link SquelchManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public SquelchManager(Dominion plugin) {
		super(plugin);
	}

	public boolean createSquelch(int playerId, String requestType){
		return db.createSquelch(playerId, requestType);
	}
	
	public boolean removeSquelch(int playerId, String requestType){
		if(!isSquelched(playerId, requestType))
			return false;
		int squelch = getSquelchId(playerId, requestType);
		if(squelch <= 0)
			return false;
		return removeSquelch(squelch);
	}
	
	public boolean removeAllSquelches(int playerId){
		boolean success = true;
		int[] squelches = getSquelches(playerId);
		for(int squelch: squelches){
			if(!removeSquelch(squelch))
				success = false;
		}
		return success;
	}
	
	public boolean removeSquelch(int squelchId){
		return db.remove("squelch", squelchId);
	}
	
	public boolean isSquelched(int playerId, String requestType){
		boolean squelched = false;
		ResultSet squelchData = db.getTableData("squelch", "squelch_id", "player_id=" + playerId + " AND request=\'" + requestType + "\'");
		try{
			if(squelchData.next())
				squelched = true;
			squelchData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return squelched;
	}
	
	public int getSquelchId(int playerId, String requestType){
		int squelched = 0;
		ResultSet squelchData = db.getTableData("squelch", "squelch_id", "player_id=" + playerId + " AND request=\'" + requestType + "\'");
		try{
			if(squelchData.next())
				squelched = squelchData.getInt("squelch_id");
			squelchData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return squelched;
	}
	
	public int[] getSquelches(int playerId){
		ArrayList<Integer> squelchList = new ArrayList<Integer>();
		ResultSet squelchData = db.getTableData("squelch", playerId, "squelch_id","player_id");
		try{
			while(squelchData.next()){
				squelchList.add(squelchData.getInt("squelch_id"));
			}
			squelchData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] squelches = new int[squelchList.size()];
		int i = 0;
	    for (Integer n : squelchList) {
	        squelches[i++] = n;
	    }
		return squelches;
	}
	
	public String[] getSquelchList(int playerId){
		ArrayList<String> squelchList = new ArrayList<String>();
		ResultSet squelchData = db.getTableData("squelch", playerId, "request","player_id");
		try{
			while(squelchData.next()){
				squelchList.add(squelchData.getString("request"));
			}
			squelchData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		String[] squelches = new String[squelchList.size()];
		squelches = squelchList.toArray(squelches);
		return squelches;
	}
	
	public boolean isSquelchType(String squelch){
		if(plugin.getRequestManager().isRequestType(squelch) ||
				squelch.equalsIgnoreCase("build") || 
				squelch.equalsIgnoreCase("all"))
			return true;
		return false;
	}
}
