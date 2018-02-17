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
import java.util.List;
import java.util.Locale;

import vkurman.jbooklibrary.db.ParserObjectToSQL;
import vkurman.jbooklibrary.db.ParserSQLToObject;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.IDCardDeactivationReason;

/**
 * The purpose of AdminIDCards class is to have a centralised point, where
 * administration and holding information about IDCards can be accessed from
 * anywhere in the application.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AdminIDCards {

	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the idCardAdmin variable correctly when it is being
	 * initialised to the AdminIDCards instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static AdminIDCards idCardAdmin;
	
	/**
	 * Default Constructor.
	 */
	private AdminIDCards() {}
	
	/**
	 * Returns instance of AdminIDCards class.
	 * 
	 * @return AdminIDCards
	 */
	public static AdminIDCards getInstance() {
		// Double-checked locking with synchronisation
		if(idCardAdmin == null) {
			synchronized (AdminIDCards.class) {
				if(idCardAdmin == null) {
					idCardAdmin = new AdminIDCards();
				}
			}
		}
		// Under either circumstance this returns the instance
		return idCardAdmin;
	}
	
	/**
	 * This method activates new IDCard for the user with the specified
	 * user id. User id must be greater than 0, otherwise no IDCard will
	 * be activated.
	 * 
	 * @param userID
	 * @return IDCard or NULL
	 */
	public IDCard activateIDCard(long userID) {
		if(userID <= 0L) return null;
		
		User user = AdminUsers.getInstance().getUser(userID);
		if (user == null) return null;
		
		// If user is not active or has IDCard, don't activate new IDCard
		if (!user.isActive() || user.hasIDCard()) return null;
		
		// Set IDCard expire date to the date set in application preferences
		Calendar c = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
		c.add(Calendar.YEAR, AdminPrefs.IDCARD_EXPIRE_PERIOD);
		
		// Create new instance of IDCard and pass it's reference to user object
		IDCard card = new IDCard(
				IDProvider.getInstance().getIDCardNextID(),
				userID,
				user.getName(),
				(Calendar) Calendar.getInstance(Locale.getDefault()).clone(),
				c);
		
		// Request user update
		if(AdminUsers.getInstance().activateIDCard(user, card)){
			// Store new IDCard in database
			if(ParserObjectToSQL.getInstance().newIDCard(card)){
				return card;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * This method deactivates specified IDCard.
	 * 
	 * @param idCard
	 * @param reason
	 * @return boolean
	 */
	public boolean deactivateIDCard(IDCard idCard, IDCardDeactivationReason reason) {
		if(idCard == null || reason == null) return false;
		
		// Changing card details
		idCard.deactivate(reason);
		
		// changing card generalStatus in DB
		if(!ParserObjectToSQL.getInstance().changeField(
				"IDCARDS",
				"STATUS",
				idCard.getGeneralStatus().toString(),
				idCard.getCardID())) return false;
		
		if(!ParserObjectToSQL.getInstance().changeField(
				"IDCARDS",
				"DEACTIVATIONDATE",
				idCard.getDeactivationDate().getTime(),
				idCard.getCardID())) return false;
		
		if(!ParserObjectToSQL.getInstance().changeField(
				"IDCARDS",
				"DEACTIVATIONREASON",
				idCard.getDeactivationReason().toString(),
				idCard.getCardID())) return false;
		
		// Set variable hasIDCard for user to false
		if(!AdminUsers.getInstance().deactivateIDCard(idCard.getUserID())) return false;
			
		return true;
	}
	
	/**
	 * This method deactivates IDCard with the specified card number.
	 * 
	 * @param cardNumber
	 * @param reason
	 * @return
	 */
	public boolean deactivateIDCard(long cardNumber, IDCardDeactivationReason reason) {
		if(cardNumber <= 0L || reason == null) return false;
		
		IDCard idCard = getIDCard(cardNumber);
		
		if(idCard == null) return false;
		
		// IDCard is already inactive
		if(!idCard.isActive()) return true;
		
		// Changing card details
		idCard.deactivate(reason);
		// changing card generalStatus in DB
		if(!ParserObjectToSQL.getInstance().changeField(
				"IDCARDS", "STATUS", idCard.getGeneralStatus().toString(),
				idCard.getCardID())) return false;
		if(!ParserObjectToSQL.getInstance().changeField(
				"IDCARDS", "DEACTIVATIONDATE",
				idCard.getDeactivationDate().getTime(),
				idCard.getCardID())) return false;
		if(!ParserObjectToSQL.getInstance().changeField(
				"IDCARDS", "DEACTIVATIONREASON",
				idCard.getDeactivationReason().toString(),
				idCard.getCardID())) return false;
		// Set variable hasIDCard for user to false
		if(!AdminUsers.getInstance().deactivateIDCard(
				idCard.getUserID())) return false;
		
		return true;
	}
	
	/**
	 * This method returns IDCard with specified card number.
	 * 
	 * @param idCard
	 * @return IDCard
	 */
	public IDCard getIDCard(long idCard) {
		return ParserSQLToObject.getInstance().getIDCard(idCard);
	}
	
	/**
	 * This method returns IDCard with specified user id number.
	 * 
	 * @param userID
	 * @return IDCard
	 */
	public IDCard getUserActiveIDCard(long userID) {
		return ParserSQLToObject.getInstance().getUserActiveIDCard(userID);
	}
	
	/**
	 * This method returns all IDCard with generalStatus ACTIVE.
	 * 
	 * @return List
	 */
	public List<IDCard> getAllActiveIDCards() {
		return ParserSQLToObject.getInstance().getIDCards(GeneralStatus.ACTIVE, 0L);
	}
	
	/**
	 * This method returns all IDCard with generalStatus ACTIVE and with specified
	 * user id number.
	 * 
	 * @param userID
	 * @return List
	 */
	public List<IDCard> getAllActiveIDCards(long userID) {
		return ParserSQLToObject.getInstance().getIDCards(GeneralStatus.ACTIVE, userID);
	}
	
	/**
	 * This method returns all IDCard with generalStatus INACTIVE.
	 * 
	 * @return List
	 */
	public List<IDCard> getAllInactiveIDCards() {
		return ParserSQLToObject.getInstance().getIDCards(GeneralStatus.INACTIVE, 0L);
	}
	
	/**
	 * This method returns all IDCard with generalStatus INACTIVE and with
	 * specified user id number.
	 * 
	 * @param userID
	 * @return List
	 */
	public List<IDCard> getAllInactiveIDCards(long userID) {
		return ParserSQLToObject.getInstance().getIDCards(GeneralStatus.INACTIVE, userID);
	}
	
	/**
	 * This method returns all IDCards.
	 * 
	 * @return List
	 */
	public List<IDCard> getAllIDCards() {
		return ParserSQLToObject.getInstance().getIDCards(null, 0L);
	}
	
	/**
	 * This method returns all IDCards with specified user id number.
	 * 
	 * @param userID
	 * @return List
	 */
	public List<IDCard> getAllIDCards(long userID) {
		return ParserSQLToObject.getInstance().getIDCards(null, userID);
	}
}