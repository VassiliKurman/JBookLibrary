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

package vkurman.jbooklibrary.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import vkurman.jbooklibrary.db.ParserObjectToSQL;
import vkurman.jbooklibrary.db.ParserSQLToObject;
import vkurman.jbooklibrary.enums.GeneralStatus;

/**
 * AdminLoans is administrator for all loans in the application.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AdminLoans {

	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the adminLoans variable correctly when it is being
	 * initialised to the AdminLoans instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static AdminLoans adminLoans;
	
	/**
	 * Default Constructor.
	 */
	private AdminLoans() {	}
	
	/**
	 * Returns instance of AdminLoans class.
	 * 
	 * @return AdminLoans
	 */
	public static AdminLoans getInstance() {
		// Double-checked locking with synchronisation
		if(adminLoans == null) {
			synchronized (AdminLoans.class) {
				if(adminLoans == null) {
					adminLoans = new AdminLoans();
				}
			}
		}

		// Under either circumstance this returns the instance
		return adminLoans;
	}
	
	/**
	 * This method creates new Loan object and returns it to requester.
	 * 
	 * @param book
	 * @param user
	 * @param days
	 * @return Loan
	 */
	public Loan newLoan(Book book, User user, int days){
		long id = IDProvider.getInstance().getLoanNextID();
		
		// Generating due date for loan
		Calendar due = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
		due.add(Calendar.DATE, days);

		Loan loan = new Loan(id, book, user, due);
		
		return (ParserObjectToSQL.getInstance().newLoan(loan)) ?
				loan :
					null;
	}
	
	/**
	 * This method closes loan by setting "STATUS" to "INACTIVE" and
	 * setting up the "return date". In case if loan is overdue his method
	 * also initiates creating new fine.
	 * 
	 * @param loanID
	 * @return boolean
	 */
	public boolean closeLoan(long loanID) {
		Loan loan = ParserSQLToObject.getInstance().getLoan(loanID);
		
		if(loan == null) return false;
		
		// Setting return date and adding loan to returned loans list
		loan.setReturnDate((Calendar) Calendar.getInstance(Locale.getDefault()).clone());
		if(!updateLoan(loanID, "RETURNDATE", loan.getReturnDate())) return false;
		
		loan.setStatus(GeneralStatus.INACTIVE);
		if(!updateLoan(loanID, "STATUS", loan.getStatus().toString())) return false;
		
		// Checking if loan is overdue
		return (loan.getDueDate().before(loan.getReturnDate())) ?
			// Calculating overdue days and generating new fine
			(AdminFines.getInstance().addFine(
					loan,
					AdminFines.getInstance().getFineDays(
							loan.getDueDate(),
							loan.getReturnDate())) != null) ?
					true :
						false :
							true;
	}
	
	/**
	 * This method closes loan by setting "STATUS" to "INACTIVE" and
	 * setting up the "return date". In case if loan is overdue his method
	 * also initiates creating new fine.
	 * 
	 * @param book
	 * @return boolean
	 */
	public boolean closeLoan(Book book) {
		Loan loan = this.getBookActiveLoan(book.getBookID());
		
		// Setting return date and adding loan to returned loans list
		loan.setReturnDate((Calendar) Calendar.getInstance(Locale.getDefault()).clone());
		if(updateLoan(loan.getLoanID(), "RETURNDATE", loan.getReturnDate())){
			loan.setStatus(GeneralStatus.INACTIVE);
			if(updateLoan(loan.getLoanID(), "STATUS", loan.getStatus().toString())){
				// Checking if loan is overdue
				if(loan.getDueDate().before(loan.getReturnDate())){
					// Calculating overdue days
					int over = loan.getOverdueDays();
					// If overdue is more than full day, than generating fine
					if(over > 0){
						// Generating new fine
						if(AdminFines.getInstance().addFine(loan,
								AdminFines.getInstance().getFineDays(
										loan.getDueDate(),
										loan.getReturnDate())) != null){
							return true;
						} else {
							return false;
						}
					} else {
						// Overdue days not more than 0 days
						return true;
					}
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	/**
	 * Returns <code>Loan</code> with specified <code>loanID</code>.
	 * 
	 * @param loanID
	 * @return Loan
	 */
	public Loan getLoan(long loanID) {
		return ParserSQLToObject.getInstance().getLoan(loanID);
	}
	
	/**
	 * This method returns all loans found in database. Empty list
	 * is returned if no loans found in database.
	 * 
	 * @return List
	 */
	public List<Loan> getAllLoans() {
		return ParserSQLToObject.getInstance().getLoans(null, 0L, 0L);
	}
	
	/**
	 * This method returns all active loans found in database. Empty list
	 * is returned if no loans found in database.
	 * 
	 * @return List
	 */
	public List<Loan> getAllActiveLoans() {
		return ParserSQLToObject.getInstance().getLoans(GeneralStatus.ACTIVE, 0L, 0L);
	}
	
	/**
	 * This method returns all inactive loans found in database. Empty list
	 * is returned if no loans found in database.
	 * 
	 * @return List
	 */
	public List<Loan> getAllInactiveLoans() {
		return ParserSQLToObject.getInstance().getLoans(GeneralStatus.INACTIVE, 0L, 0L);
	}
	
	/**
	 * This method returns one active loan entry in the list of loans for
	 * specified book. If specified book is equal to NULL, than NULL returned.
	 * If loan not found for specified book than NULL is returned.
	 * 
	 * @param book
	 * @return ArrayList of loans with one entry
	 */
	public List<Loan> getAllBookLoans(long bookID) {
		return (bookID > 0L) ?
				ParserSQLToObject.getInstance().getLoans(null, 0L, bookID) :
					new ArrayList<Loan>();
	}
	
	/**
	 * This method returns active loan for specified book. If specified book
	 * is equal to NULL, than NULL returned. If loan not found for specified
	 * book than NULL is returned.
	 * 
	 * @param book
	 * @return Loan
	 */
	public Loan getBookActiveLoan(long bookID) {
		if(bookID <= 0L) return null;
		
		Iterator<Loan> iterator = ParserSQLToObject.getInstance()
				.getLoans(GeneralStatus.ACTIVE, 0L, bookID).iterator();
		
		return (iterator.hasNext()) ?
				iterator.next() :
					null;
	}
	
	/**
	 * This method returns active loan for specified book. If specified book
	 * is equal to NULL, than NULL returned. If loan not found for specified
	 * book than NULL is returned.
	 * 
	 * @param book
	 * @return List
	 */
	public List<Loan> getAllBookActiveLoans(long bookID) {
		return (bookID > 0L) ?
				ParserSQLToObject.getInstance().getLoans(GeneralStatus.ACTIVE, 0L, bookID) :
					null;
	}
	
	/**
	 * This method returns all inactive loans for specified book. If specified book
	 * is equal to NULL, than NULL returned. If loan not found for specified
	 * book than NULL is returned.
	 * 
	 * @param book
	 * @return List
	 */
	public List<Loan> getAllBookInactiveLoans(long bookID) {
		return (bookID > 0L) ?
				ParserSQLToObject.getInstance().getLoans(GeneralStatus.INACTIVE, 0L, bookID) :
					new ArrayList<Loan>();
	}
	
	/**
	 * This method returns all loans for specified user. If no
	 * loans found for specified user, than empty list is returned.
	 * 
	 * @param user
	 * @return List
	 */
	public List<Loan> getAllUserLoans(long userID) {
		return (userID > 0L) ?
				ParserSQLToObject.getInstance().getLoans(null, userID, 0L) :
					new ArrayList<Loan>();
	}
	
	/**
	 * This method returns all active loans for specified user. If no
	 * loans found for specified user, than empty list is returned.
	 * 
	 * @param user
	 * @return List of loans
	 */
	public List<Loan> getAllUserActiveLoans(long userID) {
		return (userID > 0L) ?
				ParserSQLToObject.getInstance().getLoans(GeneralStatus.ACTIVE, userID, 0L) :
					new ArrayList<Loan>();
	}
	
	/**
	 * This method returns all inactive loans for specified user. If no
	 * loans found for specified user, than empty list is returned.
	 * 
	 * @param user
	 * @return List of loans
	 */
	public List<Loan> getAllUserInactiveLoans(long userID) {
		return (userID > 0L) ?
				ParserSQLToObject.getInstance().getLoans(GeneralStatus.INACTIVE, userID, 0L) :
					new ArrayList<Loan>();
	}
	
	/**
	 * This method checks if specified book exists in the list of active
	 * loans. If list is not empty than TRUE value is returned, or else
	 * FALSE is returned.
	 * 
	 * @param book
	 * @return TRUE if returned list is not empty or else FALSE
	 */
	public boolean isOnLoan(Book book){
		return (!getAllBookActiveLoans(book.getBookID()).isEmpty()) ?
				true :
					false;
	}
	
	/**
	 * Updating Loan data of type String.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return boolean
	 */
	public boolean updateLoan(long id, String field, String data){
		return ParserObjectToSQL.getInstance().changeField(
				"LOANS",
				field.toUpperCase(),
				data,
				id);
	}
	
	/**
	 * Updating Loan data of type Calendar.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return boolean
	 */
	public boolean updateLoan(long id, String field, Calendar data){
		return ParserObjectToSQL.getInstance().changeField(
				"LOANS",
				field.toUpperCase(),
				data.getTime(),
				id);
	}
}