package vkurman.jbooklibrary.core;

import java.util.Calendar;
import java.util.Locale;

import vkurman.jbooklibrary.enums.EmploymentTerminationReason;
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
public class Librarian extends User{
	private Calendar employmentStarted;
	private Calendar employmentFinished;
	private EmploymentTerminationReason terminationReason;
	
	/**
	 * Default Constructor with all attribute values set either
	 * to NULL or 0L.
	 */
	public Librarian(){
		this(
				0L,
				null,
				null,
				null,
				(Calendar) Calendar.getInstance(Locale.getDefault()).clone(),
				null);
	}
	
	/**
	 * Constructor that takes firstname and surname and sets
	 * the rest attribute values either to NULL or 0L.
	 * 
	 * @param firstname
	 * @param surname
	 */
	public Librarian(String firstname, String surname) {
		this(
				0L,
				firstname,
				null,
				surname,
				(Calendar) Calendar.getInstance(Locale.getDefault()).clone(),
				null);
	}
	
	/**
	 * Constructor that takes firstname, middlename and surname
	 * and sets the rest attribute values either to NULL or 0L.
	 * 
	 * @param firstname
	 * @param middlename
	 * @param surname
	 */
	public Librarian(String firstname, String middlename, String surname) {
		this(
				0L,
				firstname,
				middlename,
				surname,
				(Calendar) Calendar.getInstance(Locale.getDefault()).clone(),
				null);
	}
	
	/**
	 * Constructor that takes userID, firstname, surname and employmentStarted
	 * and sets the rest attribute values either to NULL or 0L.
	 * 
	 * @param userID
	 * @param firstname
	 * @param middlename
	 * @param surname
	 * @param employmentStarted
	 */
	public Librarian(
			long userID,
			String firstname,
			String middlename,
			String surname,
			Calendar employmentStarted) {
					this(userID,
							firstname,
							middlename,
							surname,
							employmentStarted, null);
	}
	
	/**
	 * Main Librarian Constructor.
	 * 
	 * @param userID
	 * @param firstname
	 * @param middlename
	 * @param surname
	 * @param employmentStarted
	 * @param address
	 */
	public Librarian(
			long userID,
			String firstname,
			String middlename,
			String surname,
			Calendar employmentStarted,
			Address address) {
					this.generalStatus = GeneralStatus.ACTIVE;
					this.sex = Sex.UNSPECIFIED;
					this.withdrawal = Withdrawal.NONE;
					this.userID = userID;
					this.firstname = firstname;
					this.middlename = middlename;
					this.surname = surname;
					this.employmentStarted = employmentStarted;
					this.employmentFinished = null;
					this.terminationReason = null;
					
					this.addAddress(address);
	}
	
	/**
	 * Getter for employment 'terminationReason'
	 * 
	 * @return EmploymentTerminationReason
	 */
	public EmploymentTerminationReason getTerminationReason() {
		return terminationReason;
	}
	
	/**
	 * Setter for employment 'terminationReason'
	 * 
	 * @param terminationReason
	 */
	public void setTerminationReason(
			EmploymentTerminationReason terminationReason) {
		this.terminationReason = terminationReason;
	}
	
	/**
	 * This method is setting user 'generalStatus' to INACTIVE and
	 * 'employmentFinished' date to current system's date.
	 */
	public void terminateEmployment(){
		generalStatus = GeneralStatus.INACTIVE;
		employmentFinished = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
	}
	
	/**
	 * Getter for 'employmentStarted'
	 * 
	 * @return Calendar
	 */
	public Calendar getEmploymentStarted() {
		return employmentStarted;
	}
	
	/**
	 * Setter for 'employmentStarted'
	 * 
	 * @param employmentStarted
	 */
	public void setEmploymentStarted(Calendar employmentStarted) {
		this.employmentStarted = employmentStarted;
	}
	
	/**
	 * Getter for 'employmentFinished'
	 * 
	 * @return Calendar
	 */
	public Calendar getEmploymentFinished() {
		return employmentFinished;
	}
	
	/**
	 * Setter for 'employmentFinished'
	 * 
	 * @param employmentFinished
	 */
	public void setEmploymentFinished(Calendar employmentFinished) {
		this.employmentFinished = employmentFinished;
	}
}
