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

import java.util.Calendar;
import java.util.Locale;

import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;
import vkurman.jbooklibrary.utils.JDate;

/**
 * This class holds information about user book loan.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Loan {
	private long loanID;
	private long bookID;
	private long userID;
	private String bookTitle;
	private String userName;
	private Calendar borrowDate;
	private Calendar dueDate;
	private Calendar returnDate;
	private GeneralStatus status;
	
	public Loan(){
		this(0L, 0L, null, 0L, null, null);
	}
	
	/**
	 * This Constructor takes in User and Book object references.
	 * 
	 * @param loanID
	 * @param book
	 * @param user
	 * @param dueDate
	 */
	public Loan(
			long loanID,
			Book book,
			User user,
			Calendar dueDate) {
					this(
							0L,
							book.getBookID(),
							book.getTitle(),
							user.getUserID(),
							user.getName(),
							dueDate);
	}
	
	/**
	 * This Constructor takes data necessary for loan.
	 * 
	 * @param loanID
	 * @param bookID
	 * @param bookTitle
	 * @param userID
	 * @param userName
	 * @param dueDate
	 */
	public Loan(
			long loanID,
			long bookID,
			String bookTitle,
			long userID,
			String userName,
			Calendar dueDate) {
					this.loanID = loanID;
					this.bookID = bookID;
					this.bookTitle = bookTitle;
					this.userID = userID;
					this.userName = userName;
					this.borrowDate = (Calendar)Calendar.getInstance(Locale.getDefault()).clone();
					this.dueDate = dueDate;
					this.returnDate = null;
					this.status = GeneralStatus.ACTIVE;
	}
	
	/**
	 * Getter for 'status'
	 * 
	 * @return GeneralStatus
	 */
	public GeneralStatus getStatus() {
		return status;
	}
	
	/**
	 * Setter for 'status'
	 * 
	 * @param status
	 */
	public void setStatus(GeneralStatus status) {
		this.status = status;
	}
	
	/**
	 * Use this method to check if loan is active or not.
	 * This method returns TRUE if 'status' set to ACTIVE or
	 * FALSE if 'status' set to INACTIVE.
	 * 
	 * @return boolean
	 */
	public boolean isActive(){
		return (status == GeneralStatus.ACTIVE) ? true : false;
	}
	
	/**
	 * Use this method to check if loan has been expired.
	 * This method returns TRUE if 'returnDate' is before current
	 * system's time or FALSE if it is not.
	 * 
	 * @return boolean
	 */
	public boolean isExpired(){
		if(returnDate.before(Calendar.getInstance(Locale.getDefault()))){
			status = GeneralStatus.INACTIVE;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Getter for 'loanID'
	 * 
	 * @return long
	 */
	public long getLoanID() {
		return loanID;
	}
	
	/**
	 * Setter for 'loanID'
	 * 
	 * @param loanID
	 */
	public void setLoanID(long loanID) {
		this.loanID = loanID;
	}
	
	/**
	 * Getter for 'bookID'
	 * 
	 * @return long
	 */
	public long getBookID() {
		return bookID;
	}
	
	/**
	 * Setter for 'bookID'
	 * 
	 * @param bookID
	 */
	public void setBookID(long bookID) {
		this.bookID = bookID;
	}
	
	/**
	 * Getter for 'bookTitle'
	 * 
	 * @return String
	 */
	public String getBookTitle() {
		return bookTitle;
	}
	
	/**
	 * Setter for 'bookTitile'
	 * 
	 * @param bookTitle
	 */
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	
	/**
	 * Getter for 'userID'
	 * 
	 * @return long
	 */
	public long getUserID() {
		return userID;
	}
	
	/**
	 * Setter for 'userID'
	 * 
	 * @param userID
	 */
	public void setUserID(long userID) {
		this.userID = userID;
	}
	
	/**
	 * Getter for 'userName'
	 * 
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Setter for 'userName'
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * This method calculates overdue days and returns number as 'int'. If
	 * item returned on time this method returns 0. If loan is still ACTIVE
	 * and 'returnDate' has not passed yet, this method returns 0.
	 * 
	 * @return int
	 */
	public int getOverdueDays() {
		int overdue = 0;
		if(returnDate != null){
			// Item already returned case
			if(dueDate.before(returnDate)) {
				// Loan due back date has passed
				overdue = JDate.daysDifference(returnDate, dueDate);
			}
		} else {
			// Item not returned yet
			if(dueDate.before(Calendar.getInstance(Locale.getDefault()))) {
				// Loan due back date has passed
				overdue = JDate.daysDifference((Calendar) Calendar.getInstance(Locale.getDefault()).clone(), dueDate);
			}
		}
		return overdue;
	}
	
	/**
	 * Getter for 'borrowDate'
	 * 
	 * @return Calendar
	 */
	public Calendar getBorrowDate() {
		return borrowDate;
	}
	
	/**
	 * Setter for 'borrowDate'
	 * 
	 * @param borrowDate
	 */
	public void setBorrowDate(Calendar borrowDate) {
		this.borrowDate = borrowDate;
	}
	
	/**
	 * Getter for 'returnDate'
	 * 
	 * @return Calendar
	 */
	public Calendar getReturnDate() {
		return returnDate;
	}
	
	/**
	 * Setter for 'returnDate'
	 * 
	 * @param returnDate
	 */
	public void setReturnDate(Calendar returnDate) {
		this.returnDate = returnDate;
	}
	
	/**
	 * Getter for 'dueDate'
	 * 
	 * @return Calendar
	 */
	public Calendar getDueDate() {
		return dueDate;
	}
	
	/**
	 * Setter for 'dueDate'
	 * 
	 * @param dueDate
	 */
	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * This method overrides default toString() method in the Object class.
	 * This method multiple row String containing some details about loan
	 * itself, borrowed item and the borrower.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		String borrowed = BasicLibraryDateFormatter.formatDate(borrowDate);
		String due = BasicLibraryDateFormatter.formatDate(dueDate);
		String returned = "";
		if (returnDate != null){
			BasicLibraryDateFormatter.formatDate(returnDate);
		}
		
		StringBuilder sb = new StringBuilder("Loan number: " + loanID);
		sb.append("\n" + "Book ID: " + bookID);
		sb.append("\n" + "Book Title: " + bookTitle);
		sb.append("\n" + "User ID: " + userID);
		sb.append("\n" + "User Name: " + userName);
		sb.append("\n" + "Borrowed on: " + borrowed);
		sb.append("\n" + "Due back: " + due);
		sb.append("\n" + "Returned: " + returned + "\n");
		
		return sb.toString();
	}
}
