package vkurman.jbooklibrary.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Locale;

import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * This class holds information about user payments made for the
 * fines, caused by not returning books on time or not renewing
 * existing loan. Payment has it's amount set as BigDecimal with
 * scale '2' and RoundingMode set as 'HALF_EVEN'. Payment also has
 * a reference to the fine that it was paid for, and the user, who
 * was paying the fine.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Payment {
	private long paymentID,
		fineID,
		userID;
	private String userName;
	private BigDecimal amount;
	private Calendar paymentDate;
	
	/**
	 * Default Constructor with attribute values set to NULL or 0L where
	 * appropriate.
	 */
	public Payment(){
		this(
			0L,
			0L,
			0L,
			new BigDecimal("0.00").setScale(2, RoundingMode.HALF_EVEN),
			(Calendar) Calendar.getInstance(Locale.getDefault()).clone());
	}
	
	/**
	 * This Constructor takes in parameters for all attributes except the
	 * payment time. This Constructor sets payment time to the current system
	 * time.
	 * 
	 * @param paymentID
	 * @param fineID
	 * @param userID
	 * @param amount
	 */
	public Payment(long paymentID, long fineID, long userID, BigDecimal amount) {
		this(
				paymentID,
				fineID,
				userID,
				amount,
				(Calendar) Calendar.getInstance(Locale.getDefault()).clone());
	}
	
	/**
	 * This Constructor takes in parameters for all the attributes it has.
	 * 
	 * @param paymentID
	 * @param fineID
	 * @param userID
	 * @param amount
	 * @param date
	 */
	public Payment(long paymentID, long fineID, long userID, BigDecimal amount, Calendar date) {
		this.paymentID = paymentID;
		this.fineID = fineID;
		this.userID = userID;
		this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
		this.paymentDate = date;
	}
	
	/**
	 * Getter for 'paymentID'
	 * 
	 * @return long
	 */
	public long getPaymentID() {
		return paymentID;
	}
	
	/**
	 * Setter for 'paymentID'
	 * 
	 * @param paymentID
	 */
	public void setPaymentID(long paymentID) {
		this.paymentID = paymentID;
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
	 * Getter for 'amount' paid
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	
	/**
	 * Setter for 'amount' paid
	 * 
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * Getter for 'paymentDate'
	 * 
	 * @return Calendar
	 */
	public Calendar getPaymentDate() {
		return paymentDate;
	}
	
	/**
	 * Setter for 'paymentDate'
	 * 
	 * @param paymentDate
	 */
	public void setPaymentDate(Calendar paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	/**
	 * This method returns all the details about payment as a String with
	 * multiple rows. This method overrides Object's toString() method.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Payment number: " + paymentID);
		sb.append("\n" + "Fine: " + fineID);
		sb.append("\n" + "User ID: " + userID);
		sb.append("\n" + "User: " + userName);
		sb.append("\n" + "Value: " + amount);
		sb.append("\n" + "Paid on: " + BasicLibraryDateFormatter.formatDate(paymentDate) + "\n");
		
		return sb.toString();
	}
}