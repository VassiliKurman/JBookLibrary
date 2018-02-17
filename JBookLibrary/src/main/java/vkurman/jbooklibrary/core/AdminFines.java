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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import vkurman.jbooklibrary.db.ParserObjectToSQL;
import vkurman.jbooklibrary.db.ParserSQLToObject;
import vkurman.jbooklibrary.enums.GeneralStatus;

/**
 * The purpose of AdminFines class is to have a centralised point, where
 * administration and holding information about Fines/Penalties can be accessed from
 * anywhere in the application.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AdminFines {
	
	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the admin variable correctly when it is being
	 * initialised to the AdminFines instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static AdminFines admin;
	
	/**
	 * Default Constructor.
	 */
	private AdminFines() {
	}
	
	/**
	 *  Returns instance of AdminFines class.
	 *  
	 * @return AdminFines
	 */
	public static AdminFines getInstance() {
		// Double-checked locking with synchronisation
		if(admin == null) {
			synchronized (AdminFines.class) {
				if(admin == null) {
					admin = new AdminFines();
				}
			}
		}

		// Under either circumstance this returns the instance
		return admin;
	}
	
	/**
	 * Creates new Fine and returns it back.
	 * 
	 * @param loan
	 * @param days
	 * @return fine
	 */
	public Fine addFine(Loan loan, int days) {
		if(loan == null) return null;
		
		// Generating new fine for user for current loan
		Fine fine = new Fine(
				IDProvider.getInstance().getFineNextID(),
				loan.getLoanID(),
				loan.getUserID(),
				days,
				AdminPrefs.DAILY_FINE);
		
		return (addFine(fine)) ? fine : null;
	}
	
	/**
	 * Calculates and returns days that should be charged for overdue
	 * loans based on library opening days.
	 * 
	 * @param start
	 * @param end
	 * @return int
	 */
	public int getFineDays(Calendar start, Calendar end){
		if(end.getTimeInMillis() < start.getTimeInMillis()) return 0;
		
		int days = 0;
		
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		
		Calendar cal = (Calendar) start.clone();
		while(cal.getTimeInMillis() <= end.getTimeInMillis()){
			if(AdminPrefs.isLibraryOpen(cal)){
				days++;
			}
			cal.add(Calendar.HOUR_OF_DAY, 24);
		}
		
		return days;
	}
	
	/**
	 * This method returns all Fines.
	 * 
	 * @return List
	 */
	public List<Fine> getAllFines(){
		return ParserSQLToObject.getInstance().getFines(null, 0L, 0L);
	}
	
	/**
	 * This method returns all ACTIVE Fines.
	 * 
	 * @return List
	 */
	public List<Fine> getAllActiveFines(){
		return ParserSQLToObject.getInstance().getFines(GeneralStatus.ACTIVE, 0L, 0L);
	}
	
	/**
	 * This method returns all INACTIVE Fines.
	 * 
	 * @return List
	 */
	public List<Fine> getAllInactiveFines(){
		return ParserSQLToObject.getInstance().getFines(GeneralStatus.INACTIVE, 0L, 0L);
	}
	
	/**
	 * This method returns all Fines with specified user id.
	 * 
	 * @param userID
	 * @return List
	 */
	public List<Fine> getAllUserFines(long userID){
		return (userID > 0L) ?
			ParserSQLToObject.getInstance().getFines(null, 0L, userID) :
				new ArrayList<Fine>();
	}
	
	/**
	 * This method returns all ACTIVE Fines with specified user id.
	 * 
	 * @param userID
	 * @return
	 */
	public List<Fine> getAllActiveUserFines(long userID){
		return (userID > 0L) ?
			ParserSQLToObject.getInstance().getFines(GeneralStatus.ACTIVE, 0L, userID) :
				new ArrayList<Fine>();
	}
	
	/**
	 * This method returns all INACTIVE Fines with specified user id.
	 * 
	 * @param userID
	 * @return List
	 */
	public List<Fine> getAllInactiveUserFines(long userID){
		return (userID > 0L) ?
			ParserSQLToObject.getInstance().getFines(GeneralStatus.INACTIVE, 0L, userID) :
				new ArrayList<Fine>();
	}
	
	/**
	 * This method returns all Fines with specified loan id.
	 * 
	 * @param loanID
	 * @return List
	 */
	public List<Fine> getAllLoanFines(long loanID){
		return (loanID > 0L) ?
			ParserSQLToObject.getInstance().getFines(null, loanID, 0L) :
				new ArrayList<Fine>();
	}
	
	/**
	 * This method returns all ACTIVE Fines with specified loan id.
	 * 
	 * @param loanID
	 * @return List
	 */
	public List<Fine> getAllActiveLoanFines(long loanID){
		return (loanID > 0L) ?
			ParserSQLToObject.getInstance().getFines(GeneralStatus.ACTIVE, loanID, 0L) :
				new ArrayList<Fine>();
	}
	
	/**
	 * This method returns all INACTIVE Fines with specified loan id.
	 * 
	 * @param loanID
	 * @return List
	 */
	public List<Fine> getAllInactiveLoanFines(long loanID){
		return(loanID > 0L) ?
			ParserSQLToObject.getInstance().getFines(GeneralStatus.INACTIVE, loanID, 0L) :
				new ArrayList<Fine>();
	}
	
	/**
	 * Getter for 'dailyFine'.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getDailyFine() {
		return AdminPrefs.DAILY_FINE;
	}
	
	/**
	 * Setter for 'dailyFine'.
	 * 
	 * @param dailyFine
	 */
	public void setDailyFine(BigDecimal dailyFine) {
		AdminPrefs.setDailyFine(dailyFine);
	}
	
	/**
	 * This method is adding new Fine to database.
	 * 
	 * @param fine
	 * @return boolean
	 */
	public boolean addFine(Fine fine){
		return (fine != null) ?
				ParserObjectToSQL.getInstance().newFine(fine) :
					false;
	}
	
	/**
	 * This method is adding a list of fines to database.
	 * 
	 * @param fines
	 */
	public void addFines(List<Fine> fines) {
		if(fines != null && !fines.isEmpty()){
			Iterator<Fine> iter = fines.iterator();
			while(iter.hasNext()){
				addFine(iter.next());
			}
		}
	}
	
	/**
	 * This method returns Fine with the specified fine id or NULL.
	 * 
	 * @param fineID
	 * @return Fine
	 */
	public Fine getFine(long fineID){
		return (fineID > 0L) ?
				ParserSQLToObject.getInstance().getFine(fineID) :
					null;
	}
	
	/**
	 * Saving <code>Fine</code> generalStatus to DB.
	 * 
	 * @param Fine
	 * @return boolean
	 */
	public boolean clearFine(Fine fine){
		if(fine == null) return false;
		
		if(ParserObjectToSQL.getInstance().changeField(
						"FINES",
						"STATUS",
						fine.getStatus().toString(),
						fine.getFineID())){
			return ParserObjectToSQL.getInstance().changeField(
					"FINES",
					"FINEPAIDDATE",
					fine.getFinePaidDate().getTime(),
					fine.getFineID());
						} else {
							return false;
						}
	}
	
	/**
	 * This method is requesting database BigDecimal record update. If fine
	 * record in database updated successfully TRUE returned. This method
	 * returns FALSE if fine record in database was not updated successfully.
	 * @param fine
	 * @return TRUE or FALSE
	 */
	public boolean updateFinePaidValue(Fine fine){
		return (fine != null) ?
				ParserObjectToSQL.getInstance().changeField(
						"FINES",
						"VALUEPAID",
						fine.getValuePaid(),
						fine.getFineID()) :
							false;
	}
	
	/**
	 * This method is requesting database paid Fine records update. If fine
	 * record in database updated successfully TRUE returned. This method
	 * returns FALSE if fine record in database was not updated successfully.
	 * @param fine
	 * @return TRUE or FALSE
	 */
	public boolean updatePaidFine(Fine fine){
		if (fine == null) return false;
		
		return (ParserObjectToSQL.getInstance().changeField(
				"FINES",
				"STATUS",
				fine.getStatus().toString(),
				fine.getFineID())) ?
						ParserObjectToSQL.getInstance().changeField(
								"FINES",
								"FINEPAIDDATE",
								fine.getFinePaidDate().getTime(),
								fine.getFineID()) :
									false;
	}
}