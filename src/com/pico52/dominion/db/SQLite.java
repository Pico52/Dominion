package com.pico52.dominion.db;

import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/** 
 * <b>SQLite</b><br>
 * <br>
 * &nbsp;&nbsp;public class SQLite extends {@link Database}
 * <br>
 * <br>
 * The SQLite database manager.
 */
public class SQLite extends Database {
	public String location;
	public String name;
	private File sqlFile;
	
	/** 
	 * <b>SQLite</b><br>
	 * <br>
	 * &nbsp;&nbsp;public SQLite({@link Logger} log, {@link String} prefix, {@link String} name, {@link String} location)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link SQLite} class.
	 * @param log - The logger used to output messages.
	 * @param prefix - The prefix to go before all messages.
	 * @param name - The database name.
	 * @param location - The plugin file directory.
	 */
	public SQLite(Logger log, String prefix, String name, String location) {
		super(log,prefix,"[SQLite] ");
		this.name = name;
		this.location = location;
		File folder = new File(this.location);
		if (this.name.contains("/") ||
				this.name.contains("\\") ||
				this.name.endsWith(".db")) {
			this.writeError("The database name can not contain: /, \\, or .db", true);
		}
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		sqlFile = new File(folder.getAbsolutePath() + File.separator + name + ".db");
	}
	
	@Override
	protected boolean initialize() {
		try {
		  Class.forName("org.sqlite.JDBC");
		  
		  return true;
		} catch (ClassNotFoundException e) {
		  this.writeError("You need the SQLite library " + e, true);
		  return false;
		}
	}
	
	@Override
	public Connection open() {
		if (initialize()) {
			try {
			  this.connection = DriverManager.getConnection("jdbc:sqlite:" +
					  	   sqlFile.getAbsolutePath());
			  return this.connection;
			} catch (SQLException e) {
			  this.writeError("SQLite exception on initialize " + e, true);
			}
		}
		return null;
	}
	
	@Override
	public void close() {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException ex) {
				this.writeError("Error on Connection close: " + ex, true);
			}
	}
	
	@Override
	public Connection getConnection() {
		if (this.connection == null)
			return open();
		return this.connection;
	}
	
	@Override
	public boolean checkConnection() {
		if (connection != null)
			return true;
		return false;
	}
	
	@Override
	public ResultSet querySelect(String query){
		try{
			getConnection();
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			return results;
		}catch (SQLException ex){
			writeError("Error at SQL Query: " + ex.getMessage(), false);
		}
		return null;
	}
	
	@Override
	public boolean queryWithResult(String query){
		try{
			getConnection();
			Statement statement = connection.createStatement();
			statement.execute(query);
			statement.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	PreparedStatement prepare(String query) {
		try
	    {
	        connection = open();
	        PreparedStatement ps = connection.prepareStatement(query);
	        return ps;
	    } catch(SQLException e) {
	        if(!e.toString().contains("not return ResultSet"))
	        	this.writeError("Error in SQL prepare() query: " + e.getMessage(), false);
	    }
	    return null;
	}
	
	@Override
	public boolean createTable(String query) {
		Statement statement = null;
		System.out.println("In create table.. Query: " + query);
		try {
			if (query.equals("") || query == null) {
				this.writeError("SQL Create Table query empty.", true);
				return false;
			}
			
			statement = connection.createStatement();
			boolean success = statement.execute(query);
			statement.close();
			return success;
		} catch (SQLException ex){
			this.writeError(ex.getMessage(), true);
			return false;
		}
	}
	
	@SuppressWarnings("finally")
	@Override
	public boolean createTable(String tableName, String[] columns, String[] dims){
		Statement statement = null;
			// - Check if all there's any information provided at all.
			if (tableName.equals("") || tableName == null) {
				this.writeError("SQL create table failed due to the table name being null.", true);
				return false;
			}
			if(columns == null){
				this.writeError("SQL create table failed due to the columns information being null.", true);
				return false;
			}
			if(dims == null){
				this.writeError("SQL create table failed due to the dimensions information being null.", true);
				return false;
			}
		try{
			// - The information should be there.  Now let's create the query statement.
			statement = connection.createStatement();
			String query = "CREATE TABLE " + tableName + "(";
            for (int i = 0; i < columns.length; i++) {
                if (i!=0) {
                    query += ",";
                }
                query += columns[i] + " " + dims[i];
            }
            query += ")";
            // - Query statement created.  Now execute it.
            boolean success =	statement.execute(query);
            System.out.println("##### Query successful: " + query);
            statement.close();
			return success;
		} catch (SQLException ex){
			this.writeError(ex.getMessage(), true);
			try{  // - Last chance to close this statement.
				statement.close();
			} catch (SQLException e){}
			finally{  // - Statement closed or not, we're out of here!
				return false;
			}
		}
	}
	
	@Override
	public boolean checkTable(String table) {
		DatabaseMetaData dbm = null;
		try {
			dbm = this.open().getMetaData();
			ResultSet tables = dbm.getTables(null, null, table, null);
			if (tables.next()){
				tables.getStatement().close();
				return true;
			}else{
				tables.getStatement().close();
				return false;
			}
		} catch (SQLException e) {
			this.writeError("Failed to check if table \"" + table + "\" exists: " + e.getMessage(), true);
			return false;
		}
	}
	
	@Override
	public boolean wipeTable(String table) {
		Statement statement = null;
		String query = null;
		try {
			if (!this.checkTable(table)) {
				this.writeError("Error at Wipe Table: table, " + table + ", does not exist", true);
				return false;
			}
			statement = connection.createStatement();
			query = "DELETE FROM " + table + ";";
			statement.executeQuery(query);
			return true;
		} catch (SQLException ex) {
			if (!(ex.getMessage().toLowerCase().contains("locking") ||
				ex.getMessage().toLowerCase().contains("locked")) &&
				!ex.toString().contains("not return ResultSet"))
					this.writeError("Error at SQL Wipe Table Query: " + ex, false);
			return false;
		}
	}
	
	public ResultSet retry(String query) {
		//boolean passed = false;
		Statement statement = null;
		ResultSet result = null;
		
		//while (!passed) {
			try {
				statement = connection.createStatement();
				result = statement.executeQuery(query);
				//passed = true;
				return result;
			} catch (SQLException ex) {
				if (ex.getMessage().toLowerCase().contains("locking") || ex.getMessage().toLowerCase().contains("locked")) {
					this.writeError("Please close your previous ResultSet to run the query: \n" + query, false);
					//passed = false;
				} else {
					this.writeError("Error in SQL query: " + ex.getMessage(), false);
				}
			}
		//}
		
		return null;
	}

}
