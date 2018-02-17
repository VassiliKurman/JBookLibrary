package vkurman.jbooklibrary.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vkurman.jbooklibrary.db.ParserObjectToSQL;
import vkurman.jbooklibrary.db.ParserSQLToObject;

/**
 * The purpose of AdminPayments class is to have a centralised point, where
 * administration and holding information about Payments can be accessed from
 * anywhere in the application.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AdminPayments {

	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the admin variable correctly when it is being
	 * initialised to the AdminPayments instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static AdminPayments admin;
	
	/**
	 * Default Constructor.
	 */
	private AdminPayments() { }
	
	/**
	 * Returns instance of AdminPayments class.
	 * 
	 * @return AdminPayments
	 */
	public static AdminPayments getInstance() {
		// Double-checked locking with synchronisation
		if(admin == null) {
			synchronized (AdminPayments.class) {
				if(admin == null) {
					admin = new AdminPayments();
				}
			}
		}

		// Under either circumstance this returns the instance
		return admin;
	}
	
	/**
	 * Making Payment for specified Fine.
	 * 
	 * @param fine
	 * @param payIn
	 * @return Payment
	 */
	public Payment makePayment(Fine fine, BigDecimal payIn) {
		User user = AdminUsers.getInstance().getUser(fine.getUserID());
		if(user == null) return null;
		
		BigDecimal bd = new BigDecimal("0.00");
		if ((payIn.compareTo(bd) == 1) && (payIn.compareTo(fine.toPay()) <= 0)) {
			bd = payIn.setScale(2, RoundingMode.HALF_EVEN);
			Payment payment = new Payment(
					IDProvider.getInstance().getPaymentNextID(),
					fine.getFineID(),
					user.getUserID(),
					bd);
			
			payment.setUserName(user.getName());
			
			// Paying fine
			fine.pay(payment);
			
			// Adding payment to DB
			if(!makePayment(payment)) return null;
			
			// Updating fine record
			if(!AdminFines.getInstance().updateFinePaidValue(fine)) return null;
			
			if(fine.isFinePaid()) AdminFines.getInstance().updatePaidFine(fine);
			
			// Return payment
			return payment;
		} else{
			return null;
		}
	}
	
	/**
	 * Adding payment to database.
	 * 
	 * @param payment
	 * @return boolean
	 */
	public boolean makePayment(Payment payment) {
		return (payment != null) ? ParserObjectToSQL.getInstance().newPayment(payment) : false;
	}
	
	/**
	 * Returns all Payments for User with specified user id number.
	 * 
	 * @param userID
	 * @return List
	 */
	public List<Payment> getUserPayments(long userID) {
		return (userID > 0L) ?
				ParserSQLToObject.getInstance().getPayments(userID, 0L) :
					new ArrayList<Payment>();
	}
	
	/**
	 * Returns all Payments for Fine with specified fine id number.
	 * 
	 * @param fineID
	 * @return List
	 */
	public List<Payment> getFinePayments(long fineID) {
		return (fineID > 0L) ?
				ParserSQLToObject.getInstance().getPayments(0L, fineID) :
					new ArrayList<Payment>();
	}
	
	/**
	 * Returns all Payments.
	 * 
	 * @return List
	 */
	public List<Payment> getAllPayments() {
		return ParserSQLToObject.getInstance().getPayments(0L, 0L);
	}
	
	/**
	 * Adding Payments to database.
	 * 
	 * @param payments
	 */
	public void addPayments(List<Payment> payments) {
		Iterator<Payment> iter = payments.iterator();
		while(iter.hasNext()){
			if(!ParserObjectToSQL.getInstance().newPayment(iter.next()))
				System.out.println("AdminPayment > Couldn't add new Payment!!!");
		}
	}
	
	/**
	 * Returns Payment with specified payment id number.
	 * 
	 * @param paymentID
	 * @return Payment
	 */
	public Payment getPayment(long paymentID){
		return ParserSQLToObject.getInstance().getPayment(paymentID, 0, 0);
	}
}