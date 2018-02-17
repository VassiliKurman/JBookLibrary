package vkurman.jbooklibrary.db;

import java.math.BigDecimal;
import java.sql.Connection;

import vkurman.jbooklibrary.activityregister.ActivityRegister;
import vkurman.jbooklibrary.core.Library;

/**
 * DBUpdater class executes 'UPDATE' query by using parameters that
 * has been passed to the relevant method. All methods of this class
 * return 'true' if the query has been executed successfully or 'false'
 * if it has encountered error during query execution.
 * The DBUpdater class takes benefits of Singleton Design Pattern
 * and double-checked locking with synchronisation in the first round
 * checking to make sure there are only one instance of DBUpdater
 * exists in the system.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class DBUpdater {
	private static Connection conn;
	
	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the dbUpdater variable correctly when it is being
	 * initialised to the DBUpdater instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static DBUpdater dbUpdater;
	
	/**
	 * Constructor.
	 */
	private DBUpdater() {
		DBUpdater.conn = Library.getInstance().getConnection();
	}
	
	/**
	 * Getter for DBUpdater instance.
	 * 
	 * @return DBUpdater
	 */
	public static DBUpdater getInstance() {
		// Double-checked locking with synchronisation
		if(dbUpdater == null) {
			synchronized (DBUpdater.class) {
				if(dbUpdater == null) {
					dbUpdater = new DBUpdater();
				}
			}
		}
		// Under either circumstance this returns the instance
		return dbUpdater;
	}
	
	/**
	 * Update data of type 'String' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, String data, int id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// Executing query
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"='"+data+"' WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'String' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, String data, long id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// Executing query
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"='"+data+"' WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'int' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, int data, int id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// Executing query
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"="+data+" WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'int' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, int data, long id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// Executing query
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"="+data+" WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'long' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, long data, int id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// Executing query
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"="+data+" WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'long' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, long data, long id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// Executing query
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"="+data+" WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'boolean' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, boolean data, int id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// Opening connection
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"="+data+" WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'boolean' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, boolean data, long id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// Opening connection
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"="+data+" WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'java.util.Date' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, java.util.Date data, int id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");

		// converting from java.util.Date to java.sql.Date
		java.sql.Date date = new java.sql.Date(data.getTime());
		
		// creating statement
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"='"+date+"' WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'java.util.Date' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, java.util.Date data, long id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		
		// converting from java.util.Date to java.sql.Date
		java.sql.Date date = new java.sql.Date(data.getTime());
		
		// creating statement
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"='"+date+"' WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'java.sql.Date' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, java.sql.Date data, int id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// creating statement
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"='"+data+"' WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type 'java.util.Date' in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, java.sql.Date data, long id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// creating statement
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"='"+data+"' WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot update table!");
			return false;
		}
	}
	
	/**
	 * Update data of type BigDecimal in the specified table, row and column
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 */
	public boolean updateDB(String table, String column, BigDecimal data, long id){
		ActivityRegister.newActivity(this, "Updating table "+table+"!!!");
		// creating statement
		try {
			conn.createStatement().execute("UPDATE "+table.toUpperCase()+
					" SET "+column.toUpperCase()+
					"='"+data+"' WHERE ID="+id+";");
			ActivityRegister.newActivity(this, "Table " + table.toUpperCase() + " updated!!!");
			return true;
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Can't update " + table.toUpperCase() + " table!!!");
			return false;
		}
	}
}