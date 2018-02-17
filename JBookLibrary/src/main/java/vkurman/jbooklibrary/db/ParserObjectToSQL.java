/*
 * Copyright 2018 Vassili Kurman
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vkurman.jbooklibrary.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import vkurman.jbooklibrary.activityregister.ActivityRegister;
import vkurman.jbooklibrary.core.Address;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Borrower;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.core.IDProvider;
import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.core.Library;
import vkurman.jbooklibrary.core.Loan;
import vkurman.jbooklibrary.core.Payment;
import vkurman.jbooklibrary.core.Request;
import vkurman.jbooklibrary.core.Reservation;
import vkurman.jbooklibrary.core.User;

/**
 * This class converts objects passed to it's method into the
 * SQL query to be able to save data in the SQL database.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ParserObjectToSQL {
	private volatile static ParserObjectToSQL parser;
	private static Connection conn;
	
	/**
	 * Default Constructor.
	 */
	private ParserObjectToSQL() {
		ParserObjectToSQL.conn = Library.getInstance().getConnection();
	}
	
	/**
	 * Getter for ParserObjectToSQL instance.
	 * 
	 * @return ParserObjectToSQL
	 */
	public static ParserObjectToSQL getInstance() {
		// Double-checked locking with synchronisation
		if(parser == null) {
			synchronized (ParserObjectToSQL.class) {
				if(parser == null) {
					parser = new ParserObjectToSQL();
				}
			}
		}
		// Under either circumstance this returns the instance
		return parser;
	}
	
	////////////////////////////
	// New Objects to Database
	////////////////////////////
	
	/**
	 * This method creates a new Address record in the database
	 * and saves all object data in DB.
	 * 
	 * @param address
	 * @return boolean
	 */
	public boolean newAddress(Address address){
		boolean success = false;
		Statement stmt = null;
		
		// Checking if id is set
		if(address.getAddressID() == 0L){
			address.setAddressID(IDProvider.getInstance().getAddressNextID());
		}
		
		try{
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery("SELECT * FROM ADDRESSES");
			uprs.moveToInsertRow();
			
			uprs.updateLong("ID", address.getAddressID());
			uprs.updateString("FLATNUMBER", address.getFlatNumber());
			uprs.updateString("HOUSENAME", address.getHouseName());
			uprs.updateString("HOUSENUMBER", address.getHouseNumber());
			uprs.updateString("STREET", address.getStreet());
			uprs.updateString("CITY", address.getCity());
			uprs.updateString("COUNTY", address.getCounty());
			uprs.updateString("POSTCODE", address.getPostcode());
			uprs.updateString("COUNTRY", address.getCountry());
       		
			uprs.insertRow();
			uprs.beforeFirst();
			
			success = true;
		} catch (SQLException e ) {
			ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					ActivityRegister.newActivity(this, "SQLException while closing Statement");
				}
			}
		}
		return success;
	}
	
	/**
	 * This method creates a new Address History record in the
	 * database.
	 * 
	 * @param user
	 * @param address
	 * @return boolean
	 */
	public boolean newAddressHistory(User user, Address address){
		if(user != null && address != null){
			boolean success = false;
			Statement stmt = null;
			
			// Checking if id is set
			if(address.getAddressID() == 0L){
				address.setAddressID(IDProvider.getInstance().getAddressNextID());
			}
			
			long id = IDProvider.getInstance().getAddressHistoryNextID();
			
			try{
				stmt = conn.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
	
				ResultSet uprs = stmt.executeQuery("SELECT * FROM ADDRESSHISTORIES");
				uprs.moveToInsertRow();
				
				uprs.updateLong("ID", id);
				uprs.updateLong("USERID", user.getUserID());
				uprs.updateLong("ADDRESSID", address.getAddressID());
	       		
				uprs.insertRow();
				uprs.beforeFirst();
				
				success = true;
			} catch (SQLException e ) {
				ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
			} finally {
				if (stmt != null) { try {
					stmt.close();
				} catch (SQLException e) {
					ActivityRegister.newActivity(this, "SQLException while closing Statement");
				} }
			}
			return success;
		} else {
			return false;
		}
	}
	
	/**
	 * This method creates a new Book record in the database
	 * and saves all Book object data in DB.
	 * 
	 * @param book
	 * @return boolean
	 */
	public boolean newBook(Book book){
		boolean success = false;
		Statement stmt = null;
		
		// Checking if id is set
		if(book.getBookID() == 0L){
			book.setBookID(IDProvider.getInstance().getBookNextID());
		}
		
		try{
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery("SELECT * FROM BOOKS");
			uprs.moveToInsertRow();
			
			uprs.updateLong("ID", book.getBookID());
			uprs.updateInt("EDITION", book.getEdition());
			uprs.updateInt("PAGINATION", book.getPagination());
			uprs.updateInt("RECOMMENDEDAGE", book.getRecommendedAge());
			uprs.updateBigDecimal("PRICE", book.getPrice());
			uprs.updateString("STATUS", book.getStatus().toString());
			uprs.updateString("TITLE", book.getTitle());
			uprs.updateString("SUBTITLE", book.getSubtitle());
			uprs.updateString("LANGUAGE", book.getLanguage());
			uprs.updateString("DESCRIPTION", book.getDescription());
			uprs.updateString("CONDITION", book.getCondition());
			uprs.updateString("EDITOR", book.getEditor());
			uprs.updateString("PUBLICATIONPLACE", book.getPublicationPlace());
			uprs.updateString("PUBLISHER", book.getPublisher());
			uprs.updateString("ISBN", book.getIsbn());
			uprs.updateString("FORMAT", book.getFormat());
			uprs.updateString("LOCATION", book.getLocation());
			uprs.updateString("TRANSLATEDFROM", book.getTranslatedFrom());
			uprs.updateString("SERIES", book.getSeries());
			uprs.updateString("SUPPLEMENTS", book.getSupplements());
			uprs.updateString("FOOTNOTE", book.getFootNote());
			uprs.updateTimestamp("DATEOFENTRY", new Timestamp(book.getDateOfEntry().getTimeInMillis()));
			if(book.getPublicationDate() != null){
				uprs.updateDate("PUBLICATIONDATE", new java.sql.Date(book.getPublicationDate().getTimeInMillis()));
			}
			uprs.updateString("AUTHORS", book.getAuthorsAsString());
			uprs.updateString("SUBHEADINGS", book.getSubjectHeadingsAsString());
			uprs.updateString("GENRES", book.getGenresAsString());
			uprs.updateString("KEYWORDS", book.getKeywordsAsString());
			
			uprs.insertRow();
			uprs.beforeFirst();
			
			success = true;
		} catch (SQLException e ) {
			ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
		} finally {
			if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				ActivityRegister.newActivity(this, "SQLException while closing Statement");
			} }
		}
		return success;
	}
	
	/**
	 * This method creates a new Borrower record in the database
	 * and saves all Borrower object data in DB.
	 * 
	 * @param borrower
	 * @return boolean
	 */
	public boolean newBorrower(Borrower borrower){
		boolean success = false;
		
		if (borrower != null) {
			Statement stmt = null;
			// Checking if id is set
			if (borrower.getUserID() == 0L) {
				borrower.setUserID(IDProvider.getInstance().getBorrowerNextID());
			}
			try {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);

				ResultSet uprs = stmt.executeQuery("SELECT * FROM BORROWERS");
				uprs.moveToInsertRow();
				
				uprs.updateLong("ID", borrower.getUserID());
				uprs.updateLong("IDCARD", borrower.getIdCardNumber());
				if (borrower.getCurrentAddress() != null) {
					uprs.updateLong("CURRENTADDRESS", borrower
							.getCurrentAddress().getAddressID());
				} else {
					uprs.updateLong("CURRENTADDRESS", 0L);
				}
				uprs.updateString("TITLE", borrower.getTitle());
				uprs.updateString("DEGREE", borrower.getDegree());
				uprs.updateString("INSTITUTION", borrower.getInstitution());
				uprs.updateString("FIRSTNAME", borrower.getFirstname());
				uprs.updateString("MIDDLENAME", borrower.getMiddlename());
				uprs.updateString("SURNAME", borrower.getSurname());
				if (borrower.getSex() != null) {
					uprs.updateString("SEX", borrower.getSex().toString());
				}
				if (borrower.getDob() != null) {
					uprs.updateDate("DOB", new java.sql.Date(borrower.getDob()
							.getTimeInMillis()));
				}
				uprs.updateString("PRIVATEEMAIL", borrower.getPrivateEmail());
				uprs.updateString("PRIVATEPHONE", borrower.getPrivatePhone());
				uprs.updateString("PRIVATEMOBILE", borrower.getPrivateMobile());
				uprs.updateString("PRIVATEFAX", borrower.getPrivateFax());
				uprs.updateString("OFFICEEMAIL", borrower.getOfficeEmail());
				uprs.updateString("OFFICEPHONE", borrower.getOfficePhone());
				uprs.updateString("OFFICEMOBILE", borrower.getOfficeMobile());
				uprs.updateString("OFFICEFAX", borrower.getOfficeFax());
				uprs.updateString("URL", borrower.getUrl());
				uprs.updateString("USERCATEGORY", borrower.getUserCategory());
				if (borrower.getWithdrawal() != null) {
					uprs.updateString("WITHDRAWAL", borrower.getWithdrawal()
							.toString());
				}
				if (borrower.getGeneralStatus() != null) {
					uprs.updateString("STATUS", borrower.getGeneralStatus().toString());
				}
				if (borrower.getRegistered() != null) {
					uprs.updateDate("REGISTERED", new java.sql.Date(borrower
							.getRegistered().getTimeInMillis()));
				}
				if (borrower.getDeactivated() != null) {
					uprs.updateDate("DEACTIVATED", new java.sql.Date(borrower
							.getDeactivated().getTimeInMillis()));
				}
				uprs.insertRow();
				uprs.beforeFirst();
				
				success = true;
			} catch (SQLException e) {
				ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						ActivityRegister.newActivity(this, "SQLException while closing Statement");
					}
				}
			}
		}
		
		return success;
	}
	
	/**
	 * This method creates a new Fine record in the database
	 * and saves all Fine object data in DB.
	 * 
	 * @param fine
	 * @return boolean
	 */
	public boolean newFine(Fine fine){
		boolean success = false;
		Statement stmt = null;
		
		// Checking if id is set
		if(fine.getFineID() == 0L){
			fine.setFineID(IDProvider.getInstance().getFineNextID());
		}
		try{
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery("SELECT * FROM FINES");
			uprs.moveToInsertRow();
			
			uprs.updateLong("ID", fine.getFineID());
			uprs.updateInt("DAYS", fine.getDays());
			uprs.updateLong("LOANID", fine.getLoanID());
			uprs.updateLong("USERID", fine.getUserID());
			uprs.updateBigDecimal("FINEPERDAY", fine.getFinePerDay());
			uprs.updateBigDecimal("VALUEPAID", fine.getValuePaid());
			if(fine.getFinePaidDate() != null){
				uprs.updateDate("FINEPAIDDATE", new java.sql.Date(fine.getFinePaidDate().getTimeInMillis()));
			}
			uprs.updateString("STATUS", fine.getStatus().toString());
			
			uprs.insertRow();
			uprs.beforeFirst();
			
			success = true;
		} catch (SQLException e ) {
			ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
		} finally {
			if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				ActivityRegister.newActivity(this, "SQLException while closing Statement");
			} }
		}
		return success;
	}
	
	/**
	 * This method creates a new IDCard record in the database
	 * and saves all IDCard object data in DB.
	 * 
	 * @param idCard
	 * @return boolean
	 */
	public boolean newIDCard(IDCard idCard){
		boolean success = false;
		// Checking that idCardNumber not null and user is set for idCardNumber
		if(idCard != null && idCard.getUserID() != 0L){
			Statement stmt = null;
			
			// Checking if id is set
			if(idCard.getCardID() == 0L){
				idCard.setCardID(IDProvider.getInstance().getIDCardNextID());
			}
			
			try {
				stmt = conn.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
	
				ResultSet uprs = stmt.executeQuery("SELECT * FROM IDCARDS");
				uprs.moveToInsertRow();
				
				uprs.updateLong("ID", idCard.getCardID());
				if(idCard.getGeneralStatus() != null){
					uprs.updateString("STATUS", idCard.getGeneralStatus().toString());
				}
				uprs.updateLong("USERID", idCard.getUserID());
				uprs.updateString("USERNAME", idCard.getUserName());
				if(idCard.getValidFrom() != null){
					uprs.updateDate("VALIDFROM", new java.sql.Date(idCard.getValidFrom().getTimeInMillis()));
				}
				if(idCard.getValidTo() != null){
					uprs.updateDate("VALIDTO", new java.sql.Date(idCard.getValidTo().getTimeInMillis()));
				}
				if(idCard.getDeactivationDate() != null){
					uprs.updateDate("DEACTIVATIONDATE", new java.sql.Date(idCard.getDeactivationDate().getTimeInMillis()));
				}
				if(idCard.getDeactivationReason() != null){
					uprs.updateString("DEACTIVATIONREASON", idCard.getDeactivationReason().toString());
				}
				
				uprs.insertRow();
				uprs.beforeFirst();
				
				success = true;
			} catch (SQLException e ) {
				ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
			} finally {
				if (stmt != null) { try {
					stmt.close();
				} catch (SQLException e) {
					ActivityRegister.newActivity(this, "SQLException while closing Statement");
				} }
			}
		}
		return success;
	}
	
	/**
	 * This method creates a new Librarian record in the database
	 * and saves all Librarian object data in DB.
	 * 
	 * @param librarian
	 * @return boolean
	 */
	public boolean newLibrarian(Librarian librarian){
		boolean success = false;
		Statement stmt = null;
		
		// Checking if id is set
		if(librarian.getUserID() == 0L){
			librarian.setUserID(IDProvider.getInstance().getLibrarianNextID());
		}
		
		try {
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery("SELECT * FROM LIBRARIANS");
			uprs.moveToInsertRow();
			
			uprs.updateLong("ID", librarian.getUserID());
			uprs.updateLong("IDCARD", librarian.getIdCardNumber());
			if(librarian.getCurrentAddress() != null){
				uprs.updateLong("CURRENTADDRESS", librarian.getCurrentAddress().getAddressID());
			} else {
				uprs.updateLong("CURRENTADDRESS", 0L);
			}
			uprs.updateString("TITLE", librarian.getTitle());
			uprs.updateString("DEGREE", librarian.getDegree());
			uprs.updateString("INSTITUTION", librarian.getInstitution());
			uprs.updateString("FIRSTNAME", librarian.getFirstname());
			uprs.updateString("MIDDLENAME", librarian.getMiddlename());
			uprs.updateString("SURNAME", librarian.getSurname());
			if(librarian.getSex() != null){
				uprs.updateString("SEX", librarian.getSex().toString());
			}
			if(librarian.getDob() != null){
				uprs.updateDate("DOB", new java.sql.Date(librarian.getDob().getTimeInMillis()));
			}
			uprs.updateString("PRIVATEEMAIL", librarian.getPrivateEmail());
			uprs.updateString("PRIVATEPHONE", librarian.getPrivatePhone());
			uprs.updateString("PRIVATEMOBILE", librarian.getPrivateMobile());
			uprs.updateString("PRIVATEFAX", librarian.getPrivateFax());
			uprs.updateString("OFFICEEMAIL", librarian.getOfficeEmail());
			uprs.updateString("OFFICEPHONE", librarian.getOfficePhone());
			uprs.updateString("OFFICEMOBILE", librarian.getOfficeMobile());
			uprs.updateString("OFFICEFAX", librarian.getOfficeFax());
			uprs.updateString("URL", librarian.getUrl());
			uprs.updateString("USERCATEGORY", librarian.getUserCategory());
			if(librarian.getWithdrawal() != null){
				uprs.updateString("WITHDRAWAL", librarian.getWithdrawal().toString());
			}
			if(librarian.getGeneralStatus() != null){
				uprs.updateString("STATUS", librarian.getGeneralStatus().toString());
			}
			if(librarian.getEmploymentStarted() != null){
				uprs.updateDate("EMPLOYMENTSTARTED", new java.sql.Date(librarian.getEmploymentStarted().getTimeInMillis()));
			}
			if(librarian.getEmploymentFinished() != null){
				uprs.updateDate("EMPLOYMENTFINISHED", new java.sql.Date(librarian.getEmploymentFinished().getTimeInMillis()));
			}
			if(librarian.getTerminationReason() != null){
				uprs.updateString("TERMINATIONREASON", librarian.getTerminationReason().toString());
			}
			
			uprs.insertRow();
			uprs.beforeFirst();
			
			success = true;
		} catch (SQLException e ) {
			ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
		} finally {
			if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				ActivityRegister.newActivity(this, "SQLException while closing Statement");
			} }
		}
		return success;
	}
	
	/**
	 * This method creates a new Loan record in the database
	 * and saves all Loan object data in DB.
	 * 
	 * @param loan
	 * @return boolean
	 */
	public boolean newLoan(Loan loan){
		boolean success = false;
		Statement stmt = null;
		
		// Checking if id is set
		if(loan.getLoanID() == 0L){
			loan.setLoanID(IDProvider.getInstance().getLoanNextID());
		}
		
		try {
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery("SELECT * FROM LOANS");
			uprs.moveToInsertRow();
			
			uprs.updateLong("ID", loan.getLoanID());
			uprs.updateLong("BOOKID", loan.getBookID());
			uprs.updateLong("USERID", loan.getUserID());
			uprs.updateDate("BORROWDATE", new java.sql.Date(loan.getBorrowDate().getTimeInMillis()));
			uprs.updateDate("DUEDATE", new java.sql.Date(loan.getDueDate().getTimeInMillis()));
			if(loan.getReturnDate() != null){
				uprs.updateDate("RETURNDATE", new java.sql.Date(loan.getReturnDate().getTimeInMillis()));
			}
			uprs.updateString("STATUS", loan.getStatus().toString());
			
			uprs.insertRow();
			uprs.beforeFirst();
			
			success = true;
		} catch (SQLException e ) {
			ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
		} finally {
			if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				ActivityRegister.newActivity(this, "SQLException while closing Statement");
			} }
		}
		return success;
	}
	
	/**
	 * This method creates a new Payment record in the database
	 * and saves all Payment object data in DB.
	 * 
	 * @param payment
	 * @return boolean
	 */
	public boolean newPayment(Payment payment){
		boolean success = false;
		Statement stmt = null;
		
		// Checking if id is set
		if(payment.getPaymentID() == 0L){
			payment.setPaymentID(IDProvider.getInstance().getPaymentNextID());
		}
		
		try{
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery("SELECT * FROM PAYMENTS");
			uprs.moveToInsertRow();
			
			uprs.updateLong("ID", payment.getPaymentID());
			uprs.updateLong("FINEID", payment.getFineID());
			uprs.updateLong("USERID", payment.getUserID());
			uprs.updateBigDecimal("VALUE", payment.getAmount());
			uprs.updateDate("PAYMENTDATE", new java.sql.Date(payment.getPaymentDate().getTimeInMillis()));
			
			uprs.insertRow();
			uprs.beforeFirst();
			
			success = true;
		} catch (SQLException e ) {
			ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
		} finally {
			if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				ActivityRegister.newActivity(this, "SQLException while closing Statement");
			} }
		}
		return success;
	}
	
	/**
	 * This method creates a new Reservation record in the database
	 * and saves all Reservation object data in DB.
	 * 
	 * @param reservation
	 * @return boolean
	 */
	public boolean newReservation(Reservation reservation){
		boolean success = false;
		Statement stmt = null;
		
		// Checking if id is set
		if(reservation.getReservationID() == 0L){
			reservation.setReservationID(IDProvider.getInstance().getReservationNextID());
		}
		
		try {
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery("SELECT * FROM RESERVATIONS");
			uprs.moveToInsertRow();
			
			uprs.updateLong("ID", reservation.getReservationID());
			uprs.updateLong("USERID", reservation.getUserID());
			uprs.updateLong("BOOKID", reservation.getBookID());
			uprs.updateTimestamp("RESERVEDATE", new Timestamp(reservation.getReserveDate().getTimeInMillis()));
			uprs.updateDate("EXPIREDATE", new java.sql.Date(reservation.getExpireDate().getTimeInMillis()));
			uprs.updateString("STATUS", reservation.getGeneralStatus().toString());
			
			uprs.insertRow();
			uprs.beforeFirst();
			
			success = true;
		} catch (SQLException e ) {
			ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
		} finally {
			if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				ActivityRegister.newActivity(this, "SQLException while closing Statement");
			} }
		}
		return success;
	}
	
	/**
	 * Creates a new Request record in the database and saves all
	 * object data in DB.
	 * 
	 * @param request
	 * @return boolean
	 */
	public boolean newRequest(Request request){
		boolean success = false;
		Statement stmt = null;
		
		// Checking if id is set
		if(request.getID() == 0L){
			request.setID(IDProvider.getInstance().getRequestNextID());
		}
		
		try {
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery("SELECT * FROM REQUESTS");
			uprs.moveToInsertRow();
			
			uprs.updateLong("ID", request.getID());
			uprs.updateString("ITEMTYPE", request.getType().toString());
			uprs.updateString("ISBN", request.getIsbn());
			uprs.updateString("TITLE", request.getTitle());
			uprs.updateString("ISSUE", request.getIssue());
			uprs.updateString("YEAR", request.getYear());
			uprs.updateString("AUTHOR", request.getAuthor());
			uprs.updateString("PUBLISHER", request.getPublisher());
			uprs.updateInt("QUANTITY", request.getQuantity());
			uprs.updateString("COMMENTS", request.getComments());
			uprs.updateTimestamp("REQUESTDATE", new Timestamp(request.getRequestDate().getTimeInMillis()));
			if(request.getCancelationDate() != null){
				uprs.updateTimestamp("CANCELATIONDATE", new Timestamp(request.getCancelationDate().getTimeInMillis()));
			}
			uprs.updateString("STATUS", request.getStatus().toString());
			
			uprs.insertRow();
			uprs.beforeFirst();
			
			success = true;
		} catch (SQLException e ) {
			ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
		} finally {
			if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				ActivityRegister.newActivity(this, "SQLException while closing Statement");
			} }
		}
		return success;
	}
	
	///////////////////////////////////////
	// Update Specific Object Details in Database
	///////////////////////////////////////
	
	public boolean updateAddress(Address address){
		if(address == null) return false;
		
		boolean success = false;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			
			ResultSet uprs = stmt.executeQuery("SELECT * FROM ADDRESSES WHERE ID="+address.getAddressID()+";");
			uprs.first();
			
			uprs.updateString("FLATNUMBER", address.getFlatNumber());
			uprs.updateString("HOUSENAME", address.getHouseName());
			uprs.updateString("HOUSENUMBER", address.getHouseNumber());
			uprs.updateString("STREET", address.getStreet());
			uprs.updateString("CITY", address.getCity());
			uprs.updateString("COUNTY", address.getCounty());
			uprs.updateString("POSTCODE", address.getPostcode());
			uprs.updateString("COUNTRY", address.getCountry());
			
			uprs.updateRow();
			uprs.beforeFirst();
			
			success = true;
			
			ActivityRegister.newActivity(this, "Address updated successfully");
		} catch (SQLException e ) {
			ActivityRegister.newActivity(this, "SQLException while updating ResultSet object");
		} finally {
			if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				ActivityRegister.newActivity(this, "SQLException while closing Statement");
			} }
		}
		return success;
	}
	
	///////////////////////////////////////
	// Update Object Details in Database
	///////////////////////////////////////
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, String data, int id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, String data, long id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, boolean data, int id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, boolean data, long id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, int data, int id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, int data, long id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, long data, int id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, long data, long id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, java.util.Date data, int id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, java.util.Date data, long id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
	
	/**
	 * This method updates record in the specified table.
	 * 
	 * @param table
	 * @param column
	 * @param data
	 * @param id
	 * @return boolean
	 */
	public boolean changeField(String table, String column, BigDecimal data, long id){
		return DBUpdater.getInstance().updateDB(table, column, data, id);
	}
}