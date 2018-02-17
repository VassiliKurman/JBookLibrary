package vkurman.jbooklibrary.core;

import java.util.Calendar;
import java.util.Locale;

import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * This class represents reservation object. Items can be reserved by
 * users to be borrowed within a limited time frame. Once reservation
 * period expires, reservation is deactivated. Initially there can be
 * only one reservation made for a particular book within specified
 * reservation period. Once reservation expires, new reservation can
 * be made by same user or anyone else. If particular book is on loan,
 * than reservation period starts from the date when the loan period
 * expires.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Reservation {
	private long reservationID,
		userID,
		bookID;
	private String userName,
		bookTitle;
	private Calendar reserveDate;
	private Calendar expireDate;
	private GeneralStatus generalStatus;
	
	/**
	 * Default Constructor with all fields set to NULL and 0L where
	 * appropriate.
	 */
	public Reservation(){
		this(0L, 0L, 0L);
	}
	
	/**
	 * Major Constructor if reservation id, user and book are known.
	 * Reservation expire date is set to the same date when reservation
	 * was made and needs to be calculated and changed on the time when
	 * the actual reservation was made.
	 * 
	 * @param id
	 * @param userID
	 * @param bookID
	 */
	public Reservation(long id, long userID, long bookID) {
		this.reservationID = id;
		this.bookID = bookID;
		this.userID = userID;
		this.reserveDate = (Calendar)Calendar.getInstance(Locale.getDefault()).clone();
		this.expireDate = (Calendar)Calendar.getInstance(Locale.getDefault()).clone();
		this.generalStatus = GeneralStatus.ACTIVE;
	}
	
	/**
	 * Getter for 'reservationID'
	 * 
	 * @return long
	 */
	public long getReservationID() {
		return reservationID;
	}
	
	/**
	 * Setter for 'reservationID'
	 * 
	 * @param reservationID
	 */
	public void setReservationID(long reservationID) {
		this.reservationID = reservationID;
	}
	
	/**
	 * Getter for 'generalStatus'
	 * 
	 * @return GeneralStatus
	 */
	public GeneralStatus getGeneralStatus() {
		return generalStatus;
	}
	
	/**
	 * Setter for 'generalStatus'
	 * 
	 * @param generalStatus
	 */
	public void setGeneralStatus(GeneralStatus generalStatus) {
		this.generalStatus = generalStatus;
	}
	
	/**
	 * Use this method to check if reservation is active or not.
	 * This method returns TRUE if 'generalStatus' set to ACTIVE or
	 * FALSE if 'generalStatus' set to INACTIVE.
	 * 
	 * @return boolean
	 */
	public boolean isActive(){
		return (generalStatus == GeneralStatus.ACTIVE) ? true : false;
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
	 * Setter for 'bookTitle'
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
	 * Getter for 'reserveDate'
	 * 
	 * @return Calendar
	 */
	public Calendar getReserveDate() {
		return reserveDate;
	}
	
	/**
	 * Setter for 'reserveDate'
	 * 
	 * @param date
	 */
	public void setReserveDate(Calendar date) {
		this.reserveDate = date;
	}
	
	/**
	 * Getter for 'expireDate'
	 * 
	 * @return Calendar
	 */
	public Calendar getExpireDate() {
		return expireDate;
	}
	
	/**
	 * Setter for 'expireDate'
	 * 
	 * @param expireDate
	 */
	public void setExpireDate(Calendar expireDate) {
		this.expireDate = expireDate;
	}
	
	/**
	 * Use this method to check if reservation has been expired.
	 * This method returns TRUE if 'expireDate' is before current
	 * system time or FALSE if it is not.
	 * 
	 * @return boolean
	 */
	public boolean isExpired(){
		if(expireDate.before(Calendar.getInstance(Locale.getDefault()))){
			generalStatus = GeneralStatus.INACTIVE;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Overrides default toString() method in the Object class.
	 * Returns multiple row String containing some book details,
	 * user details, reservation generalStatus and reservation
	 * beginning and expire dates.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Reservation: " + reservationID);
		sb.append("\n" + "Book: " + bookTitle);
		sb.append("\n" + "User: " + userName);
		sb.append("\n" + "Reserved on: " + BasicLibraryDateFormatter.formatDate(reserveDate));
		sb.append("\n" + "Reservation expires: " + BasicLibraryDateFormatter.formatDate(expireDate));
		sb.append("\n" + "Reservation generalStatus: " + generalStatus + "\n");
		
		return sb.toString();
	}
}