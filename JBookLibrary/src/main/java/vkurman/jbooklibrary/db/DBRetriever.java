package vkurman.jbooklibrary.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vkurman.jbooklibrary.core.Library;

/**
 * DBRetriever class executes 'SELECT' query by using parameters that
 * has been passed to the relevant method. All methods of this class
 * return 'ResultSet' object.
 * The DBRetriever class takes benefits of Singleton Design Pattern
 * and double-checked locking with synchronisation in the first round
 * checking to make sure there are only one instance of DBRetriever
 * exists in the system.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class DBRetriever {
	private static Connection conn;
	
	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the dbRetriever variable correctly when it is being
	 * initialised to the DBRetriever instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static DBRetriever dbRetriever;
	
	/**
	 * Default Constructor.
	 */
	private DBRetriever() {
		DBRetriever.conn = Library.getInstance().getConnection();
	}
	
	/**
	 * Getter for DBRetriever instance.
	 * 
	 * @return DBRetriever
	 */
	public static DBRetriever getInstance() {
		// Double-checked locking with synchronisation
		if(dbRetriever == null) {
			synchronized (DBRetriever.class) {
				if(dbRetriever == null) {
					dbRetriever = new DBRetriever();
				}
			}
		}
		// Under either circumstance this returns the instance
		return dbRetriever;
	}
	
	/**
	 * Returns ResultSet of one row MAX data from specified column in the table.
	 * ResultSet is set to CONCUR_READ_ONLY state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param column
	 * @return ResultSet
	 */
	public ResultSet getMAXFromDB(String table, String column){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// Executing query
			return stmt.executeQuery("SELECT MAX("+column.toUpperCase()+") FROM "+table.toUpperCase());
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of one row COUNT data from specified column in the table.
	 * ResultSet is set to CONCUR_READ_ONLY state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param column
	 * @return ResultSet
	 */
	public ResultSet getCOUNTFromDB(String table, String column){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// Executing query
			return stmt.executeQuery("SELECT COUNT("+column.toUpperCase()+") FROM "+table.toUpperCase());
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of one row COUNT data from specified column in the table.
	 * ResultSet is set to CONCUR_READ_ONLY state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param column
	 * @param match
	 * @return ResultSet
	 */
	public ResultSet getCOUNTFromDB(String table, String column, String match){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// Executing query
			return stmt.executeQuery(
					"SELECT COUNT("+column.toUpperCase()+") FROM "+table.toUpperCase()+
					" WHERE "+column.toUpperCase()+"='"+match+"'");
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of one row COUNT data from database. This row
	 * contains the number of ACTIVE Overdue Loans id DB.
	 * ResultSet is set to CONCUR_READ_ONLY state and cursor can move
	 * backward and forward.
	 * 
	 * @return ResultSet
	 */
	public ResultSet getCOUNTActiveOverdueLoansFromDB(){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// Executing query
			return stmt.executeQuery("SELECT COUNT(*) FROM LOANS WHERE DUEDATE < CURRENT_TIMESTAMP AND STATUS='Active'");
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet with the cursor pointing before the first row of
	 * object ID's in database from the specified table.
	 * ResultSet is set to read only state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @return ResultSet
	 */
	public ResultSet getIDsFromDB(String table){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// Executing query
			return stmt.executeQuery("SELECT ID FROM "+table.toUpperCase());
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of all rows from specified table.
	 * ResultSet is set to updatable state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(String table){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery("SELECT * FROM "+table.toUpperCase());
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of rows from specified table where id matches specified id. 
	 * ResultSet is set to updatable state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param id
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(String table, long id){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery("SELECT * FROM "+table.toUpperCase()+" WHERE ID="+id);
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of rows from specified table where data matches
	 * specified int data from specified column.
	 * ResultSet is set to updatable state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param column
	 * @param match
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(String table, String column, int match){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery("SELECT * FROM "+table.toUpperCase()+
					" WHERE "+column.toUpperCase()+"="+match);
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of rows from specified table where data matches
	 * specified long data from specified column.
	 * ResultSet is set to updatable state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param column
	 * @param match
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(String table, String column, long match){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery("SELECT * FROM "+table.toUpperCase()+
					" WHERE "+column.toUpperCase()+"="+match);
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of rows from specified table where data matches
	 * specified String from specified column and id equals to specified id.
	 * ResultSet is set to updatable state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param id
	 * @param column
	 * @param match
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(String table, long id, String column, String match){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery("SELECT * FROM "+table.toUpperCase()+
					" WHERE ID="+id+" AND "+column.toUpperCase()+"='"+match+"'");
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of rows from specified table where data matches
	 * specified String in column2 and specified column1 matches id.
	 * ResultSet is set to CONCUR_READ_ONLY state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param column1
	 * @param id
	 * @param column2
	 * @param match
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(String table, String column1, long id, String column2, String match){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery("SELECT * FROM "+table.toUpperCase()+
					" WHERE "+column1.toUpperCase()+"="+id+" AND "+column2.toUpperCase()+"='"+match+"'");
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of rows from specified table where data matches
	 * specified String in column1 and specified String in column2.
	 * ResultSet is set to CONCUR_READ_ONLY state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param column1
	 * @param match1
	 * @param column2
	 * @param match2
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(String table, String column1, String match1, String column2, String match2){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery("SELECT * FROM "+table.toUpperCase()+
					" WHERE "+column1.toUpperCase()+"='"+match1+"' AND "+column2.toUpperCase()+"='"+match2+"'");
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of rows from specified table where data matches
	 * specified String data from specified column.
	 * ResultSet is set to updatable state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param column
	 * @param match
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(String table, String column, String match){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery("SELECT * FROM "+table.toUpperCase() +
					" WHERE "+column.toUpperCase()+"="+"\'"+match+"\'");
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of rows where required String value is retrieved from specified
	 * column where both table name and row id are specified.
	 * ResultSet is set to updatable state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param id
	 * @param column
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(String table, long id, String column){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery(
					"SELECT "+column.toUpperCase()+
					" FROM "+table.toUpperCase() +
					" WHERE ID="+id);
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of rows where required String values are retrieved from specified
	 * column where both table name and row id are specified.
	 * ResultSet is set to updatable state and cursor can move backward and forward.
	 * 
	 * @param table
	 * @param id
	 * @param column1
	 * @param column2
	 * @param column3
	 * @return ResultSet
	 */
	public ResultSet getObjectsFromDB(
			String table,
			long id,
			String column1,
			String column2,
			String column3){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery(
					"SELECT "+column1.toUpperCase()+
					", "+column2.toUpperCase()+
					", "+column3.toUpperCase()+
					" FROM "+table.toUpperCase() +
					" WHERE ID="+id);
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns ResultSet of specified query.
	 * 
	 * @param query
	 * @return ResultSet
	 */
	public ResultSet executeSpecificQuery(String query){
		try {
			// Creating statement
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Executing query
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			return null;
		}
	}
}