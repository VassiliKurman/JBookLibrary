package vkurman.jbooklibrary.core;

import java.util.Calendar;
import java.util.Locale;

import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.Sex;
import vkurman.jbooklibrary.enums.Withdrawal;

/**
 * This class extends User class and adds some new attributes.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Borrower extends User {
	
	private Calendar registered;
	private Calendar deactivated;
	
	/**
	 * Default Constructor with all attribute values set either
	 * to NULL or 0L.
	 */
	public Borrower(){
		this(
				0L,
				null,
				null,
				null,
				(Calendar) Calendar.getInstance(Locale.getDefault()).clone(),
				null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param firstname
	 * @param surname
	 */
	public Borrower(String firstname, String surname) {
		this(
				0L,
				firstname,
				null,
				surname,
				(Calendar) Calendar.getInstance(Locale.getDefault()).clone(),
				null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param firstname
	 * @param middlename
	 * @param surname
	 */
	public Borrower(String firstname, String middlename, String surname) {
		this(
				0L,
				firstname,
				middlename,
				surname,
				(Calendar) Calendar.getInstance(Locale.getDefault()).clone(),
				null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param userID
	 * @param firstname
	 * @param middlename
	 * @param surname
	 * @param registered
	 */
	public Borrower(
			long userID,
			String firstname,
			String middlename,
			String surname,
			Calendar registered) {
					this(
							userID,
							firstname,
							middlename,
							surname,
							registered,
							null);
	}
	
	/**
	 * This Constructor sets all required attribute values.
	 * 
	 * @param userID
	 * @param firstname
	 * @param middlename
	 * @param surname
	 * @param registered
	 * @param address
	 */
	public Borrower(
			long userID,
			String firstname,
			String middlename,
			String surname,
			Calendar registered,
			Address address) {
					this.generalStatus = GeneralStatus.ACTIVE;
					this.sex = Sex.UNSPECIFIED;
					this.withdrawal = Withdrawal.NONE;
					this.userID = userID;
					this.firstname = firstname;
					this.middlename = middlename;
					this.surname = surname;
					this.registered = registered;
					this.deactivated = null;
					this.addAddress(address);
	}
	
	/**
	 * This method is setting user 'generalStatus' to INACTIVE and
	 * 'deactivated' date to current system's date.
	 */
	public void deactivate(){
		generalStatus = GeneralStatus.INACTIVE;
		deactivated = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
	}
	
	/**
	 * Getter for date borrower was 'registered'
	 * 
	 * @return Calendar
	 */
	public Calendar getRegistered() {
		return registered;
	}
	
	/**
	 * Setter for date borrower was 'registered'
	 * 
	 * @param registered
	 */
	public void setRegistered(Calendar registered) {
		this.registered = registered;
	}
	
	/**
	 * Getter for date borrower was 'deactivated'
	 * 
	 * @return Calendar
	 */
	public Calendar getDeactivated() {
		return deactivated;
	}
	
	/**
	 * Setter for date borrower was 'deactivated'
	 * 
	 * @param deactivated
	 */
	public void setDeactivated(Calendar deactivated) {
		this.deactivated = deactivated;
	}
}