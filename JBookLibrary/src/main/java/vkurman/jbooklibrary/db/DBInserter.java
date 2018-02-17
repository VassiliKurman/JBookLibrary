package vkurman.jbooklibrary.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DBInserter class executes 'INSERT' query passed to it's methods
 * as a String parameter. Theoretically and practically it is
 * possible to execute any valid SQL query in current version,
 * but it is not recommended. Instead, please use relevant classes
 * from the same package for creating new tables, updating or retrieving
 * data.
 * The DBInserter class takes benefits of Singleton Design Pattern
 * and double-checked locking with synchronisation in the first round
 * checking to make sure there are only one instance of DBInserter
 * exists in the system.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class DBInserter {
	private static Connection conn;
	
	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the dbInserter variable correctly when it is being
	 * initialised to the DBInserter instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static DBInserter dbInserter;
	
	/**
	 * Default Constructor.
	 * 
	 * @param conn
	 */
	private DBInserter(Connection conn) {
		DBInserter.conn = conn;
	}
	
	/**
	 * Getter for DBInserter instance.
	 * 
	 * @param conn
	 * @return DBInserter
	 */
	public static DBInserter getInstance(Connection conn) {
		// Double-checked locking with synchronisation
		if(dbInserter == null) {
			synchronized (DBInserter.class) {
				if(dbInserter == null) {
					dbInserter = new DBInserter(conn);
				}
			}
		}
		// Under either circumstance this returns the instance
		return dbInserter;
	}
	
	/**
	 * Executes INSERT query passed as parameter.
	 * 
	 * @param query
	 * @return boolean
	 */
	public boolean insertIntoDB(String query){
		System.out.println("DBInserter: Insert new row into database!!!");
		try {
			conn.createStatement().execute(query);
			System.out.println("DBInserter: Row inserted!!!");
			return true;
		} catch (SQLException e) {
			System.out.println("DBInserter: Cannot execute query!!!");
			e.printStackTrace();
			return false;
		}
	}
}