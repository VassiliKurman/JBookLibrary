package vkurman.jbooklibrary.core;

import java.util.Calendar;
import java.util.Locale;

import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.IDCardDeactivationReason;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * This class represents user IDCard that can be used to borrow
 * and return items from library.
 * 
 * <p>Date created: 2013.07.25
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDCard {
	private long cardID;
	private GeneralStatus generalStatus;
	private long userID;
	private String userName;
	private Calendar validFrom;
	private Calendar validTo;
	private Calendar deactivationDate;
	private IDCardDeactivationReason deactivationReason;	
	
	/**
	 * Default Constructor
	 */
	public IDCard() {
			this(0L,
					0L,
					null,
					(Calendar) Calendar.getInstance(Locale.getDefault()).clone(),
					(Calendar) Calendar.getInstance(Locale.getDefault()).clone());
	}
	
	/**
	 * Constructor that is setting all necessary attribute values.
	 * 
	 * @param cardID
	 * @param userID
	 * @param userName
	 * @param validFrom
	 * @param validTo
	 */
	public IDCard(long cardID, long userID, String userName, Calendar validFrom, Calendar validTo) {
		this.cardID = cardID;
		this.generalStatus = GeneralStatus.ACTIVE;
		this.userID = userID;
		this.userName = userName;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.deactivationDate = null;
		this.deactivationReason = null;
	}
	
	/**
	 * Getter for 'cardID'
	 * 
	 * @return long
	 */
	public long getCardID() {
		return cardID;
	}
	
	/**
	 * Setter for 'cardID'
	 * 
	 * @param cardID
	 */
	public void setCardID(long cardID) {
		this.cardID = cardID;
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
	 * @param id
	 */
	public void setUserID(long id) {
		this.userID = id;
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
	 * @param name
	 */
	public void setUserName(String name) {
		this.userName = name;
	}
	
	/**
	 * Getter for 'validFrom'
	 * 
	 * @return Calendar
	 */
	public Calendar getValidFrom() {
		return validFrom;
	}
	
	/**
	 * Setter for 'validFrom'
	 * 
	 * @param validFrom
	 */
	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}
	
	/**
	 * Getter for 'validTo'
	 * 
	 * @return Calendar
	 */
	public Calendar getValidTo() {
		return validTo;
	}
	
	/**
	 * Setter for 'validTo'
	 * 
	 * @param validTo
	 */
	public void setValidTo(Calendar validTo) {
		this.validTo = validTo;
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
	 * Use this method to check if idcard is active or not.
	 * This method returns TRUE if 'generalStatus' set to ACTIVE or
	 * FALSE if 'generalStatus' set to INACTIVE.
	 * 
	 * @return boolean
	 */
	public boolean isActive(){
		return (generalStatus == GeneralStatus.ACTIVE) ? true : false;
	}
	
	/**
	 * Getter for 'deactivationDate'
	 * 
	 * @return Calendar
	 */
	public Calendar getDeactivationDate() {
		return deactivationDate;
	}
	
	/**
	 * Setter for 'deactivationDate'
	 * 
	 * @param deactivationDate
	 */
	public void setDeactivationDate(Calendar deactivationDate) {
		this.deactivationDate = deactivationDate;
	}
	
	/**
	 * Getter for 'deactivationReason'
	 * 
	 * @return IDCardDeactivationReason
	 */
	public IDCardDeactivationReason getDeactivationReason() {
		return deactivationReason;
	}
	
	/**
	 * Setter for 'deactivationReason'
	 * 
	 * @param deactivationReason
	 */
	public void setDeactivationReason(IDCardDeactivationReason deactivationReason) {
		this.deactivationReason = deactivationReason;
	}
	
	/**
	 * This method deactivates IDCard. This method is setting 'generalStatus'
	 * to INACTIVE, 'deactivationDate' and 'deactivationReason'.
	 * 
	 * @param reason
	 */
	public void deactivate(IDCardDeactivationReason reason) {
		generalStatus = GeneralStatus.INACTIVE;
		deactivationReason = reason;
		deactivationDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
	}
	
	/**
	 * This method overrides default toString() method in the Object class.
	 * This method multiple row String containing all details about IDCard.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Card number: " + cardID);
		sb.append("\n" + "Name: " + userName);
		sb.append("\n" + "Valid from: " + BasicLibraryDateFormatter.formatDate(validFrom));
		sb.append("\n" + "Valid to: " + BasicLibraryDateFormatter.formatDate(validTo));
		
		return sb.toString();
	}
}