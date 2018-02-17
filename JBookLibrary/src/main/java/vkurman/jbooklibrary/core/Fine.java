package vkurman.jbooklibrary.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Locale;

import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * This class holds the necessary data about the fine that has been
 * given to the borrower for the overdue loan.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Fine {
	private long fineID;
	private int days;
	private long loanID;
	private long userID;
	private String userName;
	private BigDecimal finePerDay;
	private BigDecimal valuePaid;
	private Calendar finePaidDate;
	private GeneralStatus status;
	
	/**
	 * Default Constructor
	 */
	public Fine(){
		this(
			0L,
			0L,
			0L,
			0,
			new BigDecimal("0.00").setScale(2, RoundingMode.HALF_EVEN),
			new BigDecimal("0.00").setScale(2, RoundingMode.HALF_EVEN),
			null);
	}
	
	/**
	 * Constructor that is setting all necessary attribute values. This
	 * Constructor also is setting 'finePaidDate' to current system's time.
	 * 
	 * @param fineID
	 * @param loanID
	 * @param userID
	 * @param days
	 * @param finePerDay
	 */
	public Fine(long fineID, long loanID, long userID, int days, BigDecimal finePerDay) {
		this(
				fineID,
				loanID,
				userID,
				days,
				finePerDay,
				new BigDecimal("0.00").setScale(2, RoundingMode.HALF_EVEN),
				null);
	}
	
	/**
	 * Constructor that is setting all necessary attribute values.
	 * 
	 * @param fineID
	 * @param loanID
	 * @param userID
	 * @param days
	 * @param finePerDay
	 * @param valuePaid
	 * @param finePaidDate
	 */
	public Fine(
			long fineID,
			long loanID,
			long userID,
			int days,
			BigDecimal finePerDay,
			BigDecimal valuePaid,
			Calendar finePaidDate) {
					this.fineID = fineID;
					this.loanID = loanID;
					this.userID = userID;
					this.days = days;
					this.finePerDay = finePerDay;
					this.valuePaid = valuePaid;
					this.finePaidDate = finePaidDate;
					this.status = GeneralStatus.ACTIVE;
	}
	
	/**
	 * Returns <code>status</code>
	 * 
	 * @return GeneralStatus
	 */
	public GeneralStatus getStatus() {
		return status;
	}
	
	/**
	 * Sets <code>status</code>
	 * 
	 * @param status
	 */
	public void setStatus(GeneralStatus status) {
		this.status = status;
	}
	
	/**
	 * Use this method to check if fine is active or not.
	 * This method returns TRUE if 'status' set to ACTIVE or
	 * FALSE if 'status' set to INACTIVE.
	 * 
	 * @return boolean
	 */
	public boolean isActive(){
		return (status == GeneralStatus.ACTIVE) ? true : false;
	}
	
	/**
	 * Getter for 'fineID'
	 * 
	 * @return long
	 */
	public long getFineID() {
		return fineID;
	}
	
	/**
	 * Setter for 'fineID'
	 * 
	 * @param fineID
	 */
	public void setFineID(long fineID) {
		this.fineID = fineID;
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
	 * @param loan
	 */
	public void setLoanID(long loan) {
		this.loanID = loan;
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
	 * @param user
	 */
	public void setUserID(long user) {
		this.userID = user;
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
	 * Getter for loan overdue 'days'
	 * 
	 * @return int
	 */
	public int getDays() {
		return days;
	}
	
	/**
	 * Setter for loan overdue 'days'
	 * 
	 * @param days
	 */
	public void setDays(int days) {
		this.days = days;
	}
	
	/**
	 * Getter for 'finePerDay'
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getFinePerDay() {
		return finePerDay;
	}
	
	/**
	 * Setter for 'finePerDay'
	 * 
	 * @param finePerDay
	 */
	public void setFinePerDay(BigDecimal finePerDay) {
		this.finePerDay = finePerDay;
	}
	
	/**
	 * Getter for 'finePaidDate'
	 * 
	 * @return Calendar
	 */
	public Calendar getFinePaidDate() {
		return finePaidDate;
	}
	
	/**
	 * Setter for 'finePaidDate'
	 * 
	 * @param finePaidDate
	 */
	public void setFinePaidDate(Calendar finePaidDate) {
		this.finePaidDate = finePaidDate;
	}
	
	/**
	 * Getter for 'valuePaid'
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getValuePaid() {
		return valuePaid;
	}
	
	/**
	 * Setter for 'valuePaid'
	 * 
	 * @param valuePaid
	 */
	public void setValuePaid(BigDecimal valuePaid) {
		this.valuePaid = valuePaid;
	}
	
	/**
	 * This method is checking if the already 'valuePaid' is greater or
	 * equal to the total amount that needs to be paid for the fine and
	 * returns TRUE if compareTo() method returns positive or 0 result.
	 * This method returns FALSE if compareTo() method in BigDecimal class
	 * returns negative number.
	 *  
	 * @return boolean
	 */
	public boolean isFinePaid() {
		return (status == GeneralStatus.INACTIVE) ?
				true :
					(valuePaid.compareTo(getTotal()) >= 0) ?
							true :
								false;
	}
	
	/**
	 * This method is adding the payment to value that has been already
	 * paid earlier. This method is also checking if fine has been already
	 * paid in full and if so, than it deactivates fine by setting 'status'
	 * to INACTIVE and setting 'finePaidDate' to current system's time.
	 * 
	 * @param payment
	 */
	public void pay(Payment payment){
		valuePaid = valuePaid.add(payment.getAmount());
		if(isFinePaid()){
			this.finePaidDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
			this.status = GeneralStatus.INACTIVE;
		}
	}
	
	/**
	 * This method returns the total amount that needs to be paid for current fine.
	 * Please use toPay() method if you need to know the amount that is left to
	 * pay for current fine. 
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getTotal(){
		return finePerDay.multiply(new BigDecimal(days));
	}
	
	/**
	 * This method returns the amount that has been left to pay for current fine.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal toPay(){
		return getTotal().subtract(valuePaid);
	}
	
	/**
	 * This method overrides default toString() method in the Object class.
	 * This method multiple row String containing some details about fine
	 * itself, loan details and the borrower details.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Fine: " + fineID);
		sb.append("\n" + "Loan: " + loanID);
		sb.append("\n" + "User: " + userID);
		sb.append("\n" + "Days: " + days);
		sb.append("\n" + "Fine per Day: " + finePerDay);
		sb.append("\n" + "Total Fine: " + getTotal());
		sb.append("\n" + "Paid: " + valuePaid);
		if (finePaidDate != null){
			sb.append("\n" + "Paid on: " + BasicLibraryDateFormatter.formatDate(finePaidDate));
		}
		sb.append("\n");
		
		return sb.toString();
	}
}
