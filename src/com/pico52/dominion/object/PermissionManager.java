package com.pico52.dominion.object;

import com.pico52.dominion.Dominion;

public class PermissionManager extends DominionObjectManager{

	public PermissionManager(Dominion plugin) {
		super(plugin);
	}
	
	public boolean createPermission(int ownerId, int granteeId, String node){
		return createPermission(ownerId, granteeId, node, 0);
	}
	
	public boolean createPermission(int ownerId, int granteeId, String node, int referId){
		if(!isNode(node))
			return false;
		return db.createPermission(ownerId, granteeId, node, referId);
	}
	
	public boolean removePermission(String player, String permission, int referId){
		return removePermission(db.getPlayerId(player), permission, referId);
	}
	
	public boolean removePermission(int playerId, String permission, int referId){
		return removePermission(getPermissionId(playerId, permission, referId));
	}
	
	public boolean removePermission(int permissionId){
		return db.remove("permission", permissionId);
	}
	
	public boolean isNode(String node){
		if(node.equalsIgnoreCase("build") || 
				node.equalsIgnoreCase("cast") || 
				node.equalsIgnoreCase("destroy") || 
				node.equalsIgnoreCase("production") || 
				node.equalsIgnoreCase("see") || 
				node.equalsIgnoreCase("storage") || 
				node.equalsIgnoreCase("trade") ||
				node.equalsIgnoreCase("train") || 
				node.equalsIgnoreCase("withdraw"))
			return true;
		return false;
	}
	
	public String getReference(String node){
		if(node.equalsIgnoreCase("build") || 
				node.equalsIgnoreCase("cast") || 
				node.equalsIgnoreCase("destroy") || 
				node.equalsIgnoreCase("production") || 
				node.equalsIgnoreCase("storage") || 
				node.equalsIgnoreCase("trade") ||
				node.equalsIgnoreCase("train") || 
				node.equalsIgnoreCase("withdraw"))
			return "settlement";
		if (node.equalsIgnoreCase("see"))
			return "player";
		return null;
	}
	
	public boolean hasPermission(int playerId, String node, int referId){
		for(int i: db.getSpecificIds("permission", playerId, "grantee_id", getPermissionsReferBy(referId))){
			if(getNode(i).equalsIgnoreCase(node))
				return true;
		}
		return false;
	}
	
	public boolean isForbidden(int playerId, String node, int referId){
		return hasPermission(playerId, "forbidden_" + node, referId);
	}
	
	//---Multiple Ids---//
	public int[] getPermissionsGrantedBy(int playerId){
		return  db.getSpecificIds("permission", playerId, "owner_id");
	}
	
	public int[] getPermissions(int playerId){
		return  db.getSpecificIds("permission", playerId, "grantee_id");
	}
	
	public int[] getPermissionsReferBy(int referId){
		return db.getSpecificIds("permission", referId, "refer_id");
	}

	//---Permission Data---//
	public int getPermissionId(int playerId, String node, int referId){
		for(int i: db.getSpecificIds("permission", playerId, "grantee_id", getPermissionsReferBy(referId))){
			if(getNode(i).equalsIgnoreCase(node))
				return i;
		}
		return 0;
	}
	
	public int getOwnerId(int permissionId){
		return db.getSingleColumnInt("permission", "owner_id", permissionId, "permission_id");
	}
	
	public int getGranteeId(int permissionId){
		return db.getSingleColumnInt("permission", "grantee_id", permissionId, "permission_id");
	}
	
	public String getNode(int permissionId){
		return db.getSingleColumnString("permission", "node", permissionId, "permission_id");
	}
	
	public int getReferId(int permissionId){
		return db.getSingleColumnInt("permission", "refer_id", permissionId, "permission_id");
	}
}
