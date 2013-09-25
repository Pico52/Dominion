package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pico52.dominion.Dominion;

/** 
 * <b>RequestManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class RequestManager extends {@link DominionObjectManager}
 * <br>
 * <br>
 * Controller for requests.
 */
public class RequestManager extends DominionObjectManager{

	/** 
	 * <b>RequestManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public RequestManager()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link RequestManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public RequestManager(Dominion plugin) {
		super(plugin);
	}
	
	public boolean createRequest(int ownerId, int targetId, boolean toAdmin, int level, String request, int objectId, double xcoord, double zcoord){
		return createRequest(ownerId, targetId, toAdmin, level, request, objectId, 0, xcoord, zcoord);
	}
	
	public boolean createRequest(int ownerId, int targetId, boolean toAdmin, int level, String request, int objectId, int targetObjectId, double xcoord, double zcoord){
		return db.createRequest(ownerId, targetId, toAdmin, level, request, objectId, targetObjectId, xcoord, zcoord);
	}
	
	/** 
	 * <b>denyRequest</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean denyRequest(int requestId)
	 * <br>
	 * <br>
	 * Denies a request by sending messages to players involved that the request was denied and also removes the request from the database.  
	 * This method should be used if a player is attempting to remove the request rather than the removeRequest method.
	 * @param requestId - The id of the associated request.
	 * @return The success of the execution of this command.
	 */
	public boolean denyRequest(int requestId){
		// - We'll figure out who to send messages to and what to send.
		return removeRequest(requestId);
	}
	
	/** 
	 * <b>removeRequest</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean removeRequest(int requestId)
	 * <br>
	 * <br>
	 * Removes a request from the database.  Use the denyRequest method if a player is issuing this.
	 * @param requestId - The id of the associated request.
	 * @return The success of the execution of this command.
	 */
	public boolean removeRequest(int requestId){
		return db.remove("request", requestId);
	}
	
	public boolean acceptRequest(int requestId){
		// - This will figure out what request it is and which method should deal with it.
		return false;
	}

	public String getRequestOutput(int requestId){
		ResultSet request = plugin.getDBHandler().getTableData("request", requestId, "*", "request_id");
		int ownerId=0, targetId=0, toAdmin=0, level=0, objectId=0;
		double xCoord=0, zCoord=0;
		String requestType = "";
		try{
			if(request.next()){
				ownerId = request.getInt("owner_id");
				targetId = request.getInt("target_id");
				toAdmin = request.getInt("to_admin");
				level = request.getInt("level");
				objectId = request.getInt("object_id");
				xCoord = request.getDouble("xcoord");
				zCoord = request.getDouble("zcoord");
				requestType = request.getString("request");
			}
			request.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
			return null;
		}
		String sender = db.getPlayerName(ownerId), 
				toPlayer = db.getPlayerName(targetId), 
				output = "Sender: " + sender;
		if(toAdmin == 1)
			output += "  To: Admins";
		else
			output += "  To: " + toPlayer;
		output += "  Request: " + requestType;
		if(isBuildRequest(requestType)){
			output += "  Level: " + level + "  X: " + xCoord + "  Z: " + zCoord;
			return output;
		}
		output += " Object Id: " + objectId;
		return output;
	}
	
	//---Request Types---//
	public boolean isBuildRequest(String requestType){
		if(!requestType.startsWith("build_"))
			return false;
		String building = requestType.replace("build_", "");
		return plugin.getBuildingManager().isBuilding(building);
	}
	
	public boolean isRequestType(String request){
		if(request.equalsIgnoreCase("build") || 
				request.equalsIgnoreCase("kingdom_invite") || 
				request.equalsIgnoreCase("kingdom_request") || 
				request.equalsIgnoreCase("liege_invite") || 
				request.equalsIgnoreCase("liege_request") || 
				request.equalsIgnoreCase("trade") || 
				request.equalsIgnoreCase("permission"))
			return true;
		return false;
	}
	
	public boolean goesToAdmins(String request){
		if(request.equalsIgnoreCase("build"))
			return true;
		return false;
	}
	
	//---Multiple Requests---//
	public int[] getRequestsFrom(int playerId){
		return  db.getSpecificIds("request", playerId, "owner_id");
	}
	
	public int[] getRequestsTo(int playerId){
		return  db.getSpecificIds("request", playerId, "target_id");
	}
	
	public int[] getRequestsToAdmins(){
		return  db.getSpecificIds("request", 1, "to_admin");
	}
	
	//---Request Data---//
	public int getSenderId(int requestId){
		return db.getSingleColumnInt("request", "owner_id", requestId, "request_id");
	}
	
	public int getToId(int requestId){
		return db.getSingleColumnInt("request", "target_id", requestId, "request_id");
	}
	
	public int getLevel(int requestId){
		return db.getSingleColumnInt("request", "level", requestId, "request_id");
	}
	
	public boolean isToAdmin(int requestId){
		int toAdmin = db.getSingleColumnInt("request", "to_admin", requestId, "request_id");
		if(toAdmin == 1)
			return true;
		else
			return false;
	}
	
	public String getRequestType(int requestId){
		return db.getSingleColumnString("request", "request", requestId, "request_id");
	}
	
	public int getObjectId(int requestId){
		return db.getSingleColumnInt("request", "object_id", requestId, "request_id");
	}
	
	public double getXCoord(int requestId){
		return db.getSingleColumnDouble("request", "xcoord", requestId, "request_id");
	}
	
	public double getZCoord(int requestId){
		return db.getSingleColumnDouble("request", "zcoord", requestId, "request_id");
	}
}
