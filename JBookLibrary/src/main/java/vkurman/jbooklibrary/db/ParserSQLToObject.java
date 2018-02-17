package vkurman.jbooklibrary.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vkurman.jbooklibrary.activityregister.ActivityRegister;
import vkurman.jbooklibrary.core.Address;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Borrower;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.core.IDProvider;
import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.core.Loan;
import vkurman.jbooklibrary.core.Payment;
import vkurman.jbooklibrary.core.Request;
import vkurman.jbooklibrary.core.Reservation;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.ItemStatus;
import vkurman.jbooklibrary.enums.ItemType;
import vkurman.jbooklibrary.gui.UIDisplayManager;

/**
 * ParserSQLToObject class is designed to create objects from
 * ResultSet data.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ParserSQLToObject {
	private volatile static ParserSQLToObject parser;
	
	/**
	 * Default Constructor.
	 */
	private ParserSQLToObject() {
	}
	
	/**
	 * Getter for ParserSQLToObject instance.
	 * 
	 * @return ParserSQLToObject
	 */
	public static ParserSQLToObject getInstance() {
		// Double-checked locking with synchronisation
		if(parser == null) {
			synchronized (ParserSQLToObject.class) {
				if(parser == null) {
					parser = new ParserSQLToObject();
				}
			}
		}
		// Under either circumstance this returns the instance
		return parser;
	}
	
	/**
	 * This method returns last primary key ID or 0L if no entries returned
	 * 
	 * @param table
	 * @return Last ID used in specified table or 0 if no records found
	 */
	public long getLastID(String table){
		long  id = 0L;
		try {
			ResultSet rs = DBRetriever.getInstance().getMAXFromDB(table, "ID");
			if(rs.next()){
				id = rs.getLong(1);
			}
	    	return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		}
	}
	
	/**
	 * This method returns total number of rows or 0 if no entries returned
	 * 
	 * @param table
	 * @param column
	 * @return Number of rows in specified table with specified matching column
	 * or 0 if no records found
	 */
	public int getCount(String table, String column){
		int  records = 0;
		try {
			ResultSet rs = DBRetriever.getInstance().getCOUNTFromDB(table, column);
			if(rs.next()){
				records = rs.getInt(1);
			}
	    	return records;
		} catch (SQLException e) {
			e.printStackTrace();
			return records;
		}
	}
	
	/**
	 * This method returns total number of rows or 0 if no entries returned
	 * 
	 * @param table
	 * @param column
	 * @param match
	 * @return Number of rows in specified table with specified matching string
	 * or 0 if no records found
	 */
	public int getCount(String table, String column, String match){
		int  records = 0;
		try {
			ResultSet rs = DBRetriever.getInstance().getCOUNTFromDB(table, column, match);
			if(rs.next()){
				records = rs.getInt(1);
			}
	    	return records;
		} catch (SQLException e) {
			e.printStackTrace();
			return records;
		}
	}
	
	/**
	 * This method returns total number of rows or 0 if no entries returned
	 * 
	 * @return Number of active overdue loans
	 */
	public int getActiveOverdueLoansCount(){
		try {
			ResultSet rs = DBRetriever.getInstance().getCOUNTActiveOverdueLoansFromDB();
			if(rs.next()){
				return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	///////////////////////
	// Address
	///////////////////////
	
	/**
	 * This method creates Address object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param id
	 * @return Address
	 */
	public Address getAddress(long id){
		ActivityRegister.newActivity(this, "Retrieving data from ADDRESSES table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB(
					"ADDRESSES",
					id);
		    if(rs.next()) {
		    	return ObjectConstructor.getInstance().createAddress(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates a List of Address objects from ResultSet data,
	 * retrieving data from database by asking to execute appropriate SQL
	 * query.
	 * 
	 * @param userID
	 * @return List of Address objects
	 */
	public List<Address> getAddressesFromAddressHistory(long userID){
		List<Address> list = new ArrayList<Address>();
		ActivityRegister.newActivity(this, "Retrieving data from ADDRESSHISTORIES table!!!");
		try {
			ResultSet rs = null;
			if (userID != 0L) {
				rs = DBRetriever.getInstance().getObjectsFromDB(
						"ADDRESSHISTORIES",
						"USERID",
						userID);
				
			    while (rs.next()) {
			    	long id = rs.getInt("ADDRESSID");
			    	if(id > 0L){
			    		Address temp = getAddress(id);
			    		if(temp != null){
			    			list.add(temp);
			    		}
			    	}
			    }
			    ActivityRegister.newActivity(this, "Values from ADDRESSHISTORIES retrieve successfully!!!");
			}
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve ADDRESSHISTORIES values from ResultSet!!!");
			e.printStackTrace();
		}
		return list;
	}
	
	///////////////////////
	// Book
	///////////////////////
	
	/**
	 * This method returns Book title from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param id
	 * @return String
	 */
	public String getBookTitle(long id){
		ActivityRegister.newActivity(this, "Retrieving BOOK TITLE from BOOKS table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB(
					"BOOKS",
					id,
					"TITLE");
		    if(rs.next()) {
		    	return rs.getString("TITLE");
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates Book object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param id
	 * @return Book
	 */
	public Book getBook(long id){
		ActivityRegister.newActivity(this, "Retrieving data from BOOKS table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB(
					"BOOKS",
					id);
		    if(rs.next()) {
		    	return ObjectConstructor.getInstance().createBook(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method returns book with specified ISBN found in database.
	 * 
	 * @param isbn
	 * @return Book
	 */
	public Book getBookByISBN(String isbn){
		ActivityRegister.newActivity(this, "Retrieving data from BOOKS table by ISBN!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB(
					"BOOKS",
					"ISBN",
					isbn);
		    if(rs.next()) {
		    	return ObjectConstructor.getInstance().createBook(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method returns a list of books found in database.
	 * 
	 * @param generalStatus
	 * @param convert
	 * @return List
	 */
	public List<Book> getBooks(ItemStatus status, boolean convert){
		List<Book> list = new ArrayList<Book>();
		
		ActivityRegister.newActivity(this, "Retrieving data from BOOKS table!!!");
		try {
			ResultSet rs = null;
			
			if(status == null){
				rs = DBRetriever.getInstance()
						.executeSpecificQuery(
								"SELECT * FROM (SELECT * FROM BOOKS MINUS SELECT * FROM BOOKS WHERE STATUS='Disposed') ORDER BY ID NULLS LAST");
			} else {
				if(convert == true){
					rs = DBRetriever.getInstance()
							.executeSpecificQuery(
									"SELECT * FROM (SELECT * FROM BOOKS MINUS SELECT * FROM BOOKS WHERE STATUS='"+status.toString()+"') ORDER BY ID NULLS LAST");
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB("BOOKS", "STATUS", status.toString());
				}
			}
			
			try{
				while (rs.next()) {
					list.add(ObjectConstructor.getInstance().createBook(rs));
			    }
			} catch(NullPointerException npe){
				UIDisplayManager.displayDBErrorMessage(
						null,
						"Table does not exists!" +
						"\nPress \"OK\" to create tables!");
			}
				
			ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	/**
	 * This method returns a list of books found in database.
	 * 
	 * @param column
	 * @param match
	 * @return List
	 */
	public List<Book> getBooks(String column, String match){
		List<Book> list = new ArrayList<Book>();
		
		ActivityRegister.newActivity(this, "Retrieving data from BOOKS table!!!");
		try {
			ResultSet rs = null;
			
			rs = DBRetriever.getInstance()
						.executeSpecificQuery(
								"SELECT * FROM BOOKS WHERE LCASE("+column.toUpperCase()+") LIKE '%"+match.toLowerCase()+"%'");
			
			while (rs.next()) {
				list.add(ObjectConstructor.getInstance().createBook(rs));
		    }
			ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	///////////////////////
	// Borrower
	///////////////////////
	
	/**
	 * This method creates Borrower name from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param id
	 * @return String
	 */
	public String getBorrowerName(long id){
		ActivityRegister.newActivity(this, "Retrieving BORROWER NAME from BORROWERS table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB(
					"BORROWERS",
					id,
					"FIRSTNAME",
					"MIDDLENAME",
					"SURNAME");
		    if(rs.next()) {
		    	StringBuilder sb = new StringBuilder(rs.getString("FIRSTNAME"));
		    	String middlename = rs.getString("MIDDLENAME");
		    	if(middlename != null){
		    		sb.append(" " + middlename);
		    	}
				sb.append(" " + rs.getString("SURNAME"));
				
		    	return sb.toString();
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates Borrower object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param id
	 * @return Borrower
	 */
	public Borrower getBorrower(long id){
		ActivityRegister.newActivity(this, "Retrieving data from BORROWERS table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB("BORROWERS", id);
		    if(rs.next()) {
		    	return ObjectConstructor.getInstance().createBorrower(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates a List of Borrower objects from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param generalStatus
	 * @return List of Borrower objects
	 */
	public List<Borrower> getBorrowers(GeneralStatus generalStatus){
		List<Borrower> list = new ArrayList<Borrower>();
		
		ActivityRegister.newActivity(this, "Retrieving data from BORROWERS table!!!");
		try {
			ResultSet rs = null;
			
			if(generalStatus != null){
				rs = DBRetriever.getInstance().getObjectsFromDB(
						"BORROWERS",
						"STATUS",
						generalStatus.toString());
			} else {
				rs = DBRetriever.getInstance().getObjectsFromDB(
						"BORROWERS");
			}
			
			try{
				while (rs.next()) {
					list.add(ObjectConstructor.getInstance().createBorrower(rs));
			    }
			} catch(NullPointerException npe){
				UIDisplayManager.displayDBErrorMessage(
						null,
						"Table does not exists!" +
						"\nPress \"OK\" to create tables!");
			}
		    ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	/**
	 * This method is searching for borrowers with specified match string
	 * on specified column. If column is passed as 'name' than searching is
	 * performed on firstname, middlename and surname. Specified match can be
	 * part of word or whole word and it is NOT case sensitive.
	 * 
	 * @param column
	 * @param match
	 * @return List
	 */
	public List<Borrower> getBorrowers(String column, String match){
		List<Borrower> list = new ArrayList<Borrower>();
		
		ActivityRegister.newActivity(this, "Retrieving data from BORROWERS table!!!");
		try {
			
			ResultSet rs;
			if(column.equals("name")){
				rs = DBRetriever.getInstance().executeSpecificQuery(
						"SELECT * FROM BORROWERS WHERE " +
								"LCASE(FIRSTNAME) LIKE '%"+match.toLowerCase()+"%' " +
										"OR LCASE(MIDDLENAME) LIKE '%"+match.toLowerCase()+"%' " +
												"OR LCASE(SURNAME) LIKE '%"+match.toLowerCase()+"%'");
			} else {
				rs = DBRetriever.getInstance().executeSpecificQuery(
						"SELECT * FROM BORROWERS WHERE LCASE("+column.toUpperCase()+") LIKE '%"+match.toLowerCase()+"%'");
			}
			
		    while (rs.next()) {
			    list.add(ObjectConstructor.getInstance().createBorrower(rs));
		    }
		    ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	///////////////////////
	// Fine
	///////////////////////
	
	/**
	 * This method creates Fine object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param fineId
	 * @return Fine
	 */
	public Fine getFine(long fineId){
		ActivityRegister.newActivity(this, "Retrieving data from FINES table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB("FINES", fineId);
			// Checking if result set is not empty
		    if(rs.next()) {
		    	return ObjectConstructor.getInstance().createFine(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates List of Fine objects from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param generalStatus
	 * @param loanID
	 * @param userID
	 * @return List of Fine objects
	 */
	public List<Fine> getFines(GeneralStatus generalStatus, long loanID, long userID){
		List<Fine> list = new ArrayList<Fine>();
		
		ActivityRegister.newActivity(this, "Retrieving data from FINES table!!!");
		try {
			ResultSet rs = null;
			if (generalStatus == null) {
				if (userID > 0L) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"FINES",
							"USERID",
							userID);
				} else if (loanID > 0L) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"FINES",
							"LOANID",
							loanID);
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"FINES");
				}
			} else {
				if (userID > 0L) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"FINES",
							"USERID",
							userID,
							"STATUS",
							generalStatus.toString());
				} else if (loanID > 0L) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"FINES",
							"LOANID",
							loanID,
							"STATUS",
							generalStatus.toString());
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"FINES",
							"STATUS",
							generalStatus.toString());
				}
			}
			
			try{
				while (rs.next()) {
					list.add(ObjectConstructor.getInstance().createFine(rs));
			    }
			} catch(NullPointerException npe){
				UIDisplayManager.displayDBErrorMessage(
						null,
						"Table does not exists!" +
						"\nPress \"OK\" to create tables!");
			}
		    ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	//////////////////
	// IDCard
	//////////////////
	
	/**
	 * This method creates IDCard object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param id
	 * @return IDCard
	 */
	public IDCard getIDCard(long id){
		ActivityRegister.newActivity(this, "Retrieving data from IDCARDS table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB("IDCARDS", id);
		    if(rs.next()) {
		    	return ObjectConstructor.getInstance().createIDCard(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates IDCard object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param userID
	 * @return IDCard
	 */
	public IDCard getUserActiveIDCard(long userID){
		ActivityRegister.newActivity(this, "Retrieving data from IDCARDS table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB(
					"IDCARDS",
					"USERID",
					userID,
					"STATUS",
					GeneralStatus.ACTIVE.toString());
		    if(rs.next()) {
		    	return ObjectConstructor.getInstance().createIDCard(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates List of IDCard objects from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param generalStatus
	 * @param userID
	 * @return List of IDCard objects
	 */
	public List<IDCard> getIDCards(GeneralStatus generalStatus, long userID){
		List<IDCard> list = new ArrayList<IDCard>();
		
		ActivityRegister.newActivity(this, "Retrieving data from IDCARDS table!!!");
		try {
			ResultSet rs = null;
			if(generalStatus != null){
				if(userID > 0L){
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"IDCARDS",
							"USERID",
							userID,
							"STATUS",
							generalStatus.toString());
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"IDCARDS",
							"STATUS",
							generalStatus.toString());
				}
			} else {
				if(userID > 0L){
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"IDCARDS",
							"USERID",
							userID);
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"IDCARDS");
				}
			}
			
			try{
				while (rs.next()) {
					list.add(ObjectConstructor.getInstance().createIDCard(rs));
			    }
			} catch(NullPointerException npe){
				UIDisplayManager.displayDBErrorMessage(
						null,
						"Table does not exists!" +
						"\nPress \"OK\" to create tables!");
			}
			
		    ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	///////////////////////
	// Librarian
	///////////////////////
	
	/**
	 * This method returns Librarian name from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param id
	 * @return String
	 */
	public String getLibrarianName(long id){
		ActivityRegister.newActivity(this, "Retrieving LIBRARIAN NAME from LIBRARIANS table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB(
					"LIBRARIANS",
					id,
					"FIRSTNAME",
					"MIDDLENAME",
					"SURNAME");
		    if(rs.next()) {
		    	StringBuilder sb = new StringBuilder(rs.getString("FIRSTNAME"));
		    	String middlename = rs.getString("MIDDLENAME");
		    	if(middlename != null){
		    		sb.append(" " + middlename);
		    	}
				sb.append(" " + rs.getString("SURNAME"));
				
		    	return sb.toString();
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates Librarian object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param id
	 * @return Librarian
	 */
	public Librarian getLibrarian(long id){
		ActivityRegister.newActivity(this, "Retrieving data from LIBRARIANS table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB("LIBRARIANS", id);
		    if(rs.next()) {
		    	return ObjectConstructor.getInstance().createLibrarian(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates List of Librarian objects from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param generalStatus
	 * @return List of Librarian objects
	 */
	public List<Librarian> getLibrarians(GeneralStatus generalStatus){
		List<Librarian> list = new ArrayList<Librarian>();
		
		ActivityRegister.newActivity(this, "Retrieving data from LIBRARIANS table!!!");
		try {
			ResultSet rs = null;
			
			if(generalStatus != null){
				rs = DBRetriever.getInstance().getObjectsFromDB(
						"LIBRARIANS",
						"STATUS",
						generalStatus.toString());
			} else {
				rs = DBRetriever.getInstance().getObjectsFromDB(
						"LIBRARIANS");
			}
			
			try{
				while (rs.next()) {
					list.add(ObjectConstructor.getInstance().createLibrarian(rs));
			    }
			} catch(NullPointerException npe){
				UIDisplayManager.displayDBErrorMessage(
						null,
						"Table does not exists!" +
						"\nPress \"OK\" to create tables!");
			}
			
		    ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	/**
	 * This method is searching for borrowers with specified match string
	 * on specified column. If column is passed as 'name' than searching is
	 * performed on firstname, middlename and surname. Specified match can be
	 * part of word or whole word and it is NOT case sensitive.
	 * 
	 * @param column
	 * @param match
	 * @return List
	 */
	public List<Librarian> getLibrarians(String column, String match){
		List<Librarian> list = new ArrayList<Librarian>();
		
		ActivityRegister.newActivity(this, "Retrieving data from LIBRARIANS table!!!");
		try {
			ResultSet rs;
			if(column.equals("name")){
				rs = DBRetriever.getInstance().executeSpecificQuery(
						"SELECT * FROM LIBRARIANS WHERE " +
								"LCASE(FIRSTNAME) LIKE '%"+match.toLowerCase()+"%' " +
										"OR LCASE(MIDDLENAME) LIKE '%"+match.toLowerCase()+"%' " +
												"OR LCASE(SURNAME) LIKE '%"+match.toLowerCase()+"%'");
			} else {
				rs = DBRetriever.getInstance().executeSpecificQuery(
						"SELECT * FROM LIBRARIANS WHERE LCASE("+column.toUpperCase()+") LIKE '%"+match.toLowerCase()+"%'");
			}
			
			while (rs.next()) {
		    	list.add(ObjectConstructor.getInstance().createLibrarian(rs));
		    }
		    ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	///////////////////////
	// Loan
	///////////////////////
	
	/**
	 * This method creates Loan object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param id
	 * @return Loan
	 */
	public Loan getLoan(long id){
		ActivityRegister.newActivity(this, "Retrieving data from LOANS table!!!");
		try {
			ResultSet rs = DBRetriever.getInstance().getObjectsFromDB("LOANS", id);
		    if(rs.next()) {
		    	return ObjectConstructor.getInstance().createLoan(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates List of Loan objects from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param generalStatus
	 * @param userID
	 * @param bookID
	 * @return List of Loan objects
	 */
	public List<Loan> getLoans(GeneralStatus generalStatus, long userID, long bookID){
		List<Loan> list = new ArrayList<Loan>();
		
		ActivityRegister.newActivity(this, "Retrieving data from LOANS table!!!");
		try {
			ResultSet rs = null;
			if(generalStatus != null){
				if (userID != 0L){
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"LOANS",
							"USERID",
							userID,
							"STATUS",
							generalStatus.toString());
				} else if (bookID != 0L){
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"LOANS",
							"BOOKID",
							bookID,
							"STATUS",
							generalStatus.toString());
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"LOANS",
							"STATUS",
							generalStatus.toString());
				}
				// GeneralStatus parameter is NULL
			} else {
				if (userID != 0L){
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"LOANS",
							"USERID",
							userID);
				} else if (bookID != 0L){
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"LOANS",
							"BOOKID",
							bookID);
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"LOANS");
				}
			} 
			
			try{
				while (rs.next()) {
					if(rs != null){
						list.add(ObjectConstructor.getInstance().createLoan(rs));
					}
			    }
			} catch(NullPointerException npe){
				UIDisplayManager.displayDBErrorMessage(
						null,
						"Table does not exists!" +
						"\nPress \"OK\" to create tables!");
			}
			
		    ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	///////////////////////
	// Payment
	///////////////////////
	
	/**
	 * This method creates Payment object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param paymentId
	 * @param userId
	 * @param fineId
	 * @return Payment
	 */
	public Payment getPayment(long paymentId, long userId, long fineId){
		ActivityRegister.newActivity(this, "Retrieving data from PAYMENTS table!!!");
		try {
			ResultSet rs = null;
			
			// Checking which parameter has been passed
			if(paymentId != 0L){
				rs = DBRetriever.getInstance().getObjectsFromDB("PAYMENTS", paymentId);
			} else if (userId != 0L){
				rs = DBRetriever.getInstance().getObjectsFromDB("PAYMENTS", "USERID", userId);
			} else {
				rs = DBRetriever.getInstance().getObjectsFromDB("PAYMENTS", "FINEID", fineId);
			}
			// Checking if result set is not empty
			if(rs.next()) {
		    	return ObjectConstructor.getInstance().createPayment(rs);
		    } else {
		    	ActivityRegister.newActivity(this, "No value in ResultSet!!!");
		    	return null;
		    }
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates List of Payment objects from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param userID
	 * @param fineID
	 * @return List of Payments
	 */
	public List<Payment> getPayments(long userID, long fineID){
		List<Payment> list = new ArrayList<Payment>();
		
		ActivityRegister.newActivity(this, "Retrieving data from PAYMENTS table!!!");
		try {
			ResultSet rs = null;
			if(userID > 0L){
				rs = DBRetriever.getInstance().getObjectsFromDB("PAYMENTS", "USERID", userID);
			} else if(fineID > 0L){
				rs = DBRetriever.getInstance().getObjectsFromDB("PAYMENTS", "FINEID", fineID);
			} else {
				rs = DBRetriever.getInstance().getObjectsFromDB("PAYMENTS");
			}
			
			try{
				while (rs.next()) {
					if(rs != null){
			    		list.add(ObjectConstructor.getInstance().createPayment(rs));
			    	}
			    }
			} catch(NullPointerException npe){
				UIDisplayManager.displayDBErrorMessage(
						null,
						"Table does not exists!" +
						"\nPress \"OK\" to create tables!");
			}
		    
		    ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	///////////////////////
	// Reservation
	///////////////////////
	
	/**
	 * This method creates Reservation object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param reservationID
	 * @return Reservation
	 */
	public Reservation getReservation(long reservationID){
		ActivityRegister.newActivity(this, "Retrieving data from RESERVATIONS table!!!");
		try {
			ResultSet rs = null;
			// Checking which parameter has been passed
			if(reservationID != 0L){
				rs = DBRetriever.getInstance().getObjectsFromDB("RESERVATIONS", reservationID);
				
				// Checking if result set is not empty
				if(rs.next()) {
			    	return ObjectConstructor.getInstance().createReservation(rs);
			    } else {
			    	ActivityRegister.newActivity(this, "No value in ResultSet for specified reservation ID!!!");
			    	return null;
			    }
			} else {
				return null;
			}
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * This method creates List of Reservation objects from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param generalStatus
	 * @param userID
	 * @param bookID
	 * @return List of Reservations
	 */
	public List<Reservation> getReservations(GeneralStatus generalStatus, long userID, long bookID){
		List<Reservation> list = new ArrayList<Reservation>();
		
		ActivityRegister.newActivity(this, "Retrieving data from RESERVATIONS table!!!");
		try {
			ResultSet rs = null;
			if (generalStatus == null) {
				if (userID != 0L) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"RESERVATIONS", "USERID", userID);
				} else if (bookID != 0L) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"RESERVATIONS", "BOOKID", bookID);
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"RESERVATIONS");
				}
			} else {
				if (userID != 0L) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"RESERVATIONS", "USERID", userID, "STATUS", generalStatus.toString());
				} else if (bookID != 0L) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"RESERVATIONS", "BOOKID", bookID, "STATUS", generalStatus.toString());
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"RESERVATIONS", "STATUS", generalStatus.toString());
				}
			}
			
			try{
				while (rs.next()) {
					list.add(ObjectConstructor.getInstance().createReservation(rs));
			    }
			} catch(NullPointerException npe){
				UIDisplayManager.displayDBErrorMessage(
						null,
						"Table does not exists!" +
						"\nPress \"OK\" to create tables!");
			}
			
			ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	
	///////////////////////
	// Request
	///////////////////////
	
	/**
	 * Creates Request object from ResultSet data, retrieving
	 * data from database by asking to execute appropriate SQL query.
	 * 
	 * @param requestID
	 * @return Request
	 */
	public Request getRequest(long requestID){
		ActivityRegister.newActivity(this, "Retrieving data from REQUESTS table!!!");
		try {
			ResultSet rs = null;
			// Checking which parameter has been passed
			if(requestID != 0L){
				rs = DBRetriever.getInstance().getObjectsFromDB("REQUESTS", requestID);
				
				// Checking if result set is not empty
				if(rs.next()) {
			    	return ObjectConstructor.getInstance().createRequest(rs);
			    } else {
			    	ActivityRegister.newActivity(this, "No value in ResultSet for specified reservation ID!!!");
			    	return null;
			    }
			} else {
				return null;
			}
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
			return null;
		}
	}
	
	/**
	 * Creates <code>List</code> of <code>Request</code> objects from
	 * <code>ResultSet</code> data, retrieving data from database by
	 * asking to execute appropriate SQL query.
	 * 
	 * @param generalStatus
	 * @param itemType
	 * @return List of Requests
	 */
	public List<Request> getRequests(GeneralStatus generalStatus, ItemType itemType){
		List<Request> list = new ArrayList<Request>();
		
		ActivityRegister.newActivity(this, "Retrieving data from REQUESTS table!!!");
		try {
			ResultSet rs = null;
			if (generalStatus == null) {
				if (itemType != null) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"REQUESTS", "ITEMTYPE", itemType.toString());
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"REQUESTS");
				}
			} else {
				if (itemType != null) {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"REQUESTS", "STATUS", generalStatus.toString(), "ITEMTYPE", itemType.toString());
				} else {
					rs = DBRetriever.getInstance().getObjectsFromDB(
							"REQUESTS", "STATUS", generalStatus.toString());
				}
			}
			
			try{
				while (rs.next()) {
					list.add(ObjectConstructor.getInstance().createRequest(rs));
			    }
			} catch(NullPointerException npe){
				UIDisplayManager.displayDBErrorMessage(
						null,
						"Table does not exists!" +
						"\nPress \"OK\" to create tables!");
			}
			
			ActivityRegister.newActivity(this, "Values retrieve successfully!!!");
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "Cannot retrieve value from ResultSet!!!");
		}
		return list;
	}
	
	///////////////////////
	// User Selection
	///////////////////////
	
	/**
	 * This method returns a list of User, mixed together Librarians
	 * and Borrowers.
	 * 
	 * @param generalStatus
	 * @return List
	 */
	public List<User> getUsers(GeneralStatus generalStatus){
		List<User> list = new ArrayList<User>();
		
		list.addAll(getLibrarians(generalStatus));
		list.addAll(getBorrowers(generalStatus));
		
		return list;
	}
	
	/**
	 * This method returns User. Use this method if it is unknown to
	 * which class user belongs to: is he Librarian or the Borrower.
	 * 
	 * @param id
	 * @return User
	 */
	public User getUser(long id){
		if(id > 0L){
			if(id >= IDProvider.LIBRARIAN_DEFAULT_ID && id <= IDProvider.LIBRARIAN_MAX_ID){
				return this.getLibrarian(id);
			} else if(id >= IDProvider.BORROWER_DEFAULT_ID && id <= IDProvider.BORROWER_MAX_ID) {
				return this.getBorrower(id);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * This method returns user name as a String object. User
	 * name includes firstname, middlename and surname.
	 * Use this method if it is unknown to which class user
	 * belongs to: is he Librarian or the Borrower.
	 * 
	 * @param id
	 * @return String
	 */
	public String getUserName(long id){
		if(id > 0L){
			if(id >= IDProvider.LIBRARIAN_DEFAULT_ID && id <= IDProvider.LIBRARIAN_MAX_ID){
				return this.getLibrarianName(id);
			} else if(id >= IDProvider.BORROWER_DEFAULT_ID && id <= IDProvider.BORROWER_MAX_ID) {
				return this.getBorrowerName(id);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}