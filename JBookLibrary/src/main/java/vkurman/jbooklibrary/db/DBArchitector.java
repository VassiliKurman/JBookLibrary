package vkurman.jbooklibrary.db;

import java.sql.Connection;

import vkurman.jbooklibrary.activityregister.ActivityRegister;
import vkurman.jbooklibrary.core.Library;

/**
 * DBArchitector class creates all required tables for BasicLibrary
 * application. This class is called only once at first start of
 * application or any time when database cannot be found in the
 * specified location.
 * The DBArchitector class takes benefits of Singleton Design Pattern
 * and double-checked locking with synchronisation in the first round
 * checking to make sure there are only one instance of DBArchitector
 * exists in the system.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class DBArchitector {
	
	private static Connection conn;
	
	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the dbArchitector variable correctly when it is being
	 * initialised to the DBArchitector instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static DBArchitector dbArchitector;
	
	/**
	 * Default Constructor.
	 */
	private DBArchitector() {
		DBArchitector.conn = Library.getInstance().getConnection();
	}

	public static DBArchitector getInstance() {
		// Double-checked locking with synchronisation
		if(dbArchitector == null) {
			synchronized (DBArchitector.class) {
				if(dbArchitector == null) {
					dbArchitector = new DBArchitector();
				}
			}
		}
		// Under either circumstance this returns the instance
		return dbArchitector;
	}
	
	/**
	 * Creating all necessary tables in the database.
	 */
	public void createTables() {
		this.createAddressesTable();
		this.createAddressHistoriesTable();
		this.createBooksTable();
		this.createBorrowersTable();
		this.createFinesTable();
		this.createIDCardsTable();
		this.createLibrariansTable();
		this.createLoansTable();
		this.createPaymentsTable();
		this.createReservationsTable();
		this.createRequestsTable();
    }
	
	/**
	 * Creating ADDRESSES table.
	 * 
	 * @return boolean
	 */
	private boolean createAddressesTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_ADDRESSES +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute("CREATE TABLE " + DBContract.TABLE_ADDRESSES +
					"(ID LONG PRIMARY KEY, " +
					"FLATNUMBER VARCHAR, " +
					"HOUSENAME VARCHAR, " +
					"HOUSENUMBER VARCHAR, " +
					"STREET VARCHAR, " +
					"CITY VARCHAR, " +
					"COUNTY VARCHAR, " +
					"POSTCODE VARCHAR, " +
					"COUNTRY VARCHAR)");
			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating ADDRESSHISTORIES table.
	 * 
	 * @return boolean
	 */
	private boolean createAddressHistoriesTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_ADDRESS_HISTORIES +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute(
					"CREATE TABLE " + DBContract.TABLE_ADDRESS_HISTORIES +
					"(ID LONG PRIMARY KEY, " +
					"USERID LONG, " +
					"ADDRESSID LONG)");
			if(success){
				ActivityRegister.newActivity(this, "Table ADDRESSHISTORIES created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create ADDRESSHISTORIES table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating BOOKS table.
	 * 
	 * @return boolean
	 */
	private boolean createBooksTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_BOOKS +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute(
					"CREATE TABLE " + DBContract.TABLE_BOOKS +
					"(ID LONG PRIMARY KEY, " +
					"EDITION INT, " +
					"PAGINATION SMALLINT, " +
					"RECOMMENDEDAGE TINYINT, " +
					"PRICE DECIMAL, " +
					"STATUS VARCHAR, " +
					"TITLE VARCHAR, " +
					"SUBTITLE VARCHAR, " +
					"LANGUAGE VARCHAR, " +
					"DESCRIPTION VARCHAR, " +
					"CONDITION VARCHAR, " +
					"EDITOR VARCHAR, " +
					"PUBLICATIONPLACE VARCHAR, " +
					"PUBLISHER VARCHAR, " +
					"ISBN VARCHAR, " +
					"FORMAT VARCHAR, " +
					"LOCATION VARCHAR, " +
					"TRANSLATEDFROM VARCHAR, " +
					"SERIES VARCHAR, " +
					"SUPPLEMENTS VARCHAR, " +
					"FOOTNOTE VARCHAR, " +
					"DATEOFENTRY TIMESTAMP, " +
					"PUBLICATIONDATE DATE, " +
					"AUTHORS VARCHAR, " +
					"SUBHEADINGS VARCHAR, " +
					"GENRES VARCHAR, " +
					"KEYWORDS VARCHAR)");
			
			// LoanHistory  retrieve from LOANS table
			
			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating BORROWERS table.
	 * 
	 * @return boolean
	 */
	private boolean createBorrowersTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_BORROWERS +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute(
					"CREATE TABLE " + DBContract.TABLE_BORROWERS +
					"(ID LONG PRIMARY KEY, " +
					"IDCARD LONG, " +
					"CURRENTADDRESS LONG, " +
					"TITLE VARCHAR, " +
					"DEGREE VARCHAR, " +
					"INSTITUTION VARCHAR, " +
					"FIRSTNAME VARCHAR, " +
					"MIDDLENAME VARCHAR, " +
					"SURNAME VARCHAR, " +
					"SEX VARCHAR, " +
					"DOB DATE, " +
					"PRIVATEEMAIL VARCHAR, " +
					"PRIVATEPHONE VARCHAR, " +
					"PRIVATEMOBILE VARCHAR, " +
					"PRIVATEFAX VARCHAR, " +
					"OFFICEEMAIL VARCHAR, " +
					"OFFICEPHONE VARCHAR, " +
					"OFFICEMOBILE VARCHAR, " +
					"OFFICEFAX VARCHAR, " +
					"URL VARCHAR, " +
					"USERCATEGORY VARCHAR, " +
					"WITHDRAWAL VARCHAR, " +
					"STATUS VARCHAR, " +
					"REGISTERED DATE, " +
					"DEACTIVATED DATE)");

//					Bellow variables should be retrieved from relevant tables
//					ADDRESSHISTORY ARRAY -> ADDRESSHISTORY TABLE -> ADDRESSES TABLE
//					Loans for current user can be retrieve from LOANS TABLE
//					Fines for current user can be retrieve from FINES TABLE
//					Payments for current user can be retrieve from PAYMENTS TABLE

			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating FINES table.
	 * 
	 * @return boolean
	 */
	private boolean createFinesTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_FINES +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute(
					"CREATE TABLE " + DBContract.TABLE_FINES +
					"(ID LONG PRIMARY KEY, " +
					"DAYS SMALLINT, " +
					"LOANID LONG, " +
					"USERID LONG, " +
					"FINEPERDAY DECIMAL, " +
					"VALUEPAID DECIMAL, " +
					"FINEPAIDDATE TIMESTAMP, " +
					"STATUS VARCHAR)");
			
			// paymentHistory retrieve from PAYMENTS table
			
			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating IDCARDS table.
	 * 
	 * @return boolean
	 */
	private boolean createIDCardsTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_IDCARDS +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute(
					"CREATE TABLE " + DBContract.TABLE_IDCARDS +
					"(ID LONG PRIMARY KEY, " +
					"STATUS VARCHAR, " +
					"USERID LONG, " +
					"USERNAME VARCHAR, " +
					"VALIDFROM TIMESTAMP, " +
					"VALIDTO DATE, " +
					"DEACTIVATIONDATE TIMESTAMP, " +
					"DEACTIVATIONREASON VARCHAR)");
			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating LIBRARIANS table.
	 * 
	 * @return boolean
	 */
	private boolean createLibrariansTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_LIBRARIANS +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute(
					"CREATE TABLE " + DBContract.TABLE_LIBRARIANS +
					"(ID LONG PRIMARY KEY, " +
					"IDCARD LONG, " +
					"CURRENTADDRESS LONG, " +
					"TITLE VARCHAR, " +
					"DEGREE VARCHAR, " +
					"INSTITUTION VARCHAR, " +
					"FIRSTNAME VARCHAR, " +
					"MIDDLENAME VARCHAR, " +
					"SURNAME VARCHAR, " +
					"SEX VARCHAR, " +
					"DOB DATE, " +
					"PRIVATEEMAIL VARCHAR, " +
					"PRIVATEPHONE VARCHAR, " +
					"PRIVATEMOBILE VARCHAR, " +
					"PRIVATEFAX VARCHAR, " +
					"OFFICEEMAIL VARCHAR, " +
					"OFFICEPHONE VARCHAR, " +
					"OFFICEMOBILE VARCHAR, " +
					"OFFICEFAX VARCHAR, " +
					"URL VARCHAR, " +
					"USERCATEGORY VARCHAR, " +
					"WITHDRAWAL VARCHAR, " +
					"STATUS VARCHAR, " +
					"EMPLOYMENTSTARTED DATE, " +
					"EMPLOYMENTFINISHED DATE, " +
					"TERMINATIONREASON VARCHAR)");

//					Bellow variables should be retrieved from relevant tables
//					ADDRESSHISTORY ARRAY -> ADDRESSHISTORY TABLE -> ADDRESSES TABLE
//					Loans for current user can be retrieved from LOANS TABLE
//					Fines for current user can be retrieved from FINES TABLE
//					Payments for current user can be retrieved from PAYMENTS TABLE

			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating LOANS table.
	 * 
	 * @return boolean
	 */
	private boolean createLoansTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_LOANS +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute("CREATE TABLE " + DBContract.TABLE_LOANS +
					"(ID LONG PRIMARY KEY, " +
					"BOOKID LONG, " +
					"USERID LONG, " +
					"BORROWDATE DATE, " +
					"DUEDATE DATE, " +
					"RETURNDATE DATE, " +
					"STATUS VARCHAR)");
			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating PAYMENTS table.
	 * 
	 * @return boolean
	 */
	private boolean createPaymentsTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_PAYMENTS +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute("CREATE TABLE " + DBContract.TABLE_PAYMENTS +
					"(ID LONG PRIMARY KEY, " +
					"FINEID LONG, " +
					"USERID LONG, " +
					"VALUE DECIMAL, " +
					"PAYMENTDATE DATE)");
			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating RESERVATIONS table.
	 * 
	 * @return boolean
	 */
	private boolean createReservationsTable(){
		ActivityRegister.newActivity(this, "Creating "+ DBContract.TABLE_RESERVATIONS +" table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute(
				"CREATE TABLE " + DBContract.TABLE_RESERVATIONS +
					"(ID LONG PRIMARY KEY, " +
					"USERID LONG, " +
					"BOOKID LONG, " +
					"RESERVEDATE TIMESTAMP, " +
					"EXPIREDATE DATE, " +
					"STATUS VARCHAR)");
			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
	
	/**
	 * Creating REQUESTS table.
	 * 
	 * @return boolean
	 */
	private boolean createRequestsTable(){
		ActivityRegister.newActivity(this, "Creating " + DBContract.TABLE_REQUESTS + " table!!!");
		boolean success = false;
		try {
			success = conn.createStatement().execute(
				"CREATE TABLE " + DBContract.TABLE_REQUESTS +
					"(ID LONG PRIMARY KEY, " +
					"ITEMTYPE VARCHAR, " +
					"ISBN VARCHAR, " +
					"TITLE VARCHAR, " +
					"ISSUE VARCHAR, " +
					"YEAR VARCHAR, " +
					"AUTHOR VARCHAR, " +
					"PUBLISHER VARCHAR, " +
					"QUANTITY INT, " +
					"COMMENTS VARCHAR, " +
					"REQUESTDATE TIMESTAMP, " +
					"CANCELATIONDATE TIMESTAMP, " +
					"STATUS VARCHAR)");
			if(success){
				ActivityRegister.newActivity(this, "Table created!!!");
			} else {
				ActivityRegister.newActivity(this, "Query NOT executed successfuly");
			}
		} catch (Exception e) {
			ActivityRegister.newActivity(this, "Cannot create table! Table already exists");
		}
		return success;
	}
}