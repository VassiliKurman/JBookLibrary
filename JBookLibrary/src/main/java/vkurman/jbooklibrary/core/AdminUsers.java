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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import vkurman.jbooklibrary.db.ParserObjectToSQL;
import vkurman.jbooklibrary.db.ParserSQLToObject;
import vkurman.jbooklibrary.enums.EmploymentTerminationReason;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.IDCardDeactivationReason;

/**
 * User Administrator class manages all user related activities.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AdminUsers {

	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the adminUsers variable correctly when it is being
	 * initialised to the AdminUsers instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static AdminUsers adminUsers;
	
	/**
	 * Private default constructor.
	 */
	private AdminUsers() {
	}
	
	/**
	 * This method ensures that only one instance of this class
	 * exists in the system while application is running.
	 * 
	 * @return AdminUser
	 */
	public static AdminUsers getInstance() {
		// Double-checked locking with synchronisation
		if(adminUsers == null) {
			synchronized (AdminUsers.class) {
				if(adminUsers == null) {
					adminUsers = new AdminUsers();
				}
			}
		}
		// Under either circumstance this returns the instance
		return adminUsers;
	}
	
	/**
	 * This method returns a List of all users with specified generalStatus.
	 * If generalStatus is not specified, i.e. equals to NULL, than this method
	 * returns all users with any all statuses.
	 * 
	 * @param generalStatus
	 * @return List of users, which includes librarians and borrowers
	 */
	public List<User> getUsers(GeneralStatus generalStatus){
		List<User> users = new ArrayList<User>();
		
		// Adding librarians to list of users
		users.addAll(ParserSQLToObject.getInstance().getLibrarians(generalStatus));
		// Adding borrowers to list of users
		users.addAll(ParserSQLToObject.getInstance().getBorrowers(generalStatus));
		
		return users;
	}
	
	/**
	 * This method returns list of users that has been specified in the
	 * 'userType' as 'Borrower' or 'Librarian'. If 'userType' is NULL than
	 * mixed list of borrowers and librarians is returned.
	 * 
	 * @param generalStatus
	 * @param userType
	 * @return List
	 */
	public List<User> getUsers(GeneralStatus generalStatus, String userType){
		List<User> users = new ArrayList<User>();
		
		if(userType.equals("Borrower")){
				users.addAll(ParserSQLToObject.getInstance().getBorrowers(generalStatus));
		} else if(userType.equals("Librarian")){
			users.addAll(ParserSQLToObject.getInstance().getLibrarians(generalStatus));
		} else {
			users.addAll(ParserSQLToObject.getInstance().getUsers(generalStatus));
		}
		return users;
	}
	
	/**
	 * This method returns list of users with specified name. Name can be
	 * written in full or as a part of firstname, middlename or surname.
	 * 
	 * @param name
	 * @return List
	 */
	public List<User> getUsers(String name){
		List<User> users = new ArrayList<User>();
		
		// Adding found librarians
		users.addAll(ParserSQLToObject.getInstance().getLibrarians("name", name));
		// Adding found borrowers
		users.addAll(ParserSQLToObject.getInstance().getBorrowers("name", name));
		
		return users;
	}
	
	/**
	 * This method returns all ACTIVE and INACTIVE librarians found in the
	 * database.
	 * 
	 * @return List
	 */
	public List<Librarian> getLibrarians() {
		return ParserSQLToObject.getInstance().getLibrarians(null);
	}
	
	/**
	 * This method returns all librarians with specified generalStatus found in the
	 * database. They could be ACTIVE or INACTIVE, or both if specified generalStatus
	 * is NULL.
	 * 
	 * @return List
	 */
	public List<Librarian> getLibrarians(GeneralStatus generalStatus) {
		return ParserSQLToObject.getInstance().getLibrarians(generalStatus);
	}
	
	/**
	 * This method is adding a new Librarian to the database and returns
	 * TRUE if record was created successfully or FALSE if not. If userID
	 * is not set, than this method is asking for the next available id
	 * for librarian before adding him to the database.
	 * 
	 * @param librarian
	 * @return boolean
	 */
	public boolean addLibrarian(Librarian librarian) {
		if(librarian != null){
			if(librarian.getUserID() == 0L){
				librarian.setUserID(IDProvider.getInstance().getLibrarianNextID());
			}
			return ParserObjectToSQL.getInstance().newLibrarian(librarian);
		} else {
			return false;
		}
	}
	
	/**
	 * This method is adding Librarians from the list to the database one
	 * by one.
	 * 
	 * @param librarians
	 */
	public void addLibrarians(List<Librarian> librarians) {
		Iterator<Librarian> iter = librarians.iterator();
		while(iter.hasNext()){
			this.addLibrarian(iter.next());
		}
	}
	
	/**
	 * This method is requesting database to be updated with details provided
	 * for Librarian that has left the job. Specified employment termination
	 * reasons are saved in DB as well, as librarians INACTIVE generalStatus with the
	 * date employment has been terminated.
	 * 
	 * @param librarian
	 * @param reason
	 * @return boolean
	 */
	public boolean removeLibrarian(Librarian librarian,	EmploymentTerminationReason reason) {
		if(librarian == null || reason == null) return false;
		
		boolean update1 = false;
		boolean update2 = false;
		boolean update3 = false;
		boolean update4 = false;
		
		librarian.setGeneralStatus(GeneralStatus.INACTIVE);
		update1 = ParserObjectToSQL.getInstance().changeField(
				"LIBRARIANS",
				"STATUS",
				librarian.getGeneralStatus().toString(),
				librarian.getUserID());
		
		librarian.setTerminationReason(reason);
		update2 = ParserObjectToSQL.getInstance().changeField(
				"LIBRARIANS",
				"TERMINATIONREASON",
				librarian.getTerminationReason().toString(),
				librarian.getUserID());
		
		librarian.setEmploymentFinished((Calendar) Calendar.getInstance(Locale.getDefault()).clone());
		update3 = ParserObjectToSQL.getInstance().changeField(
				"LIBRARIANS",
				"EMPLOYMENTFINISHED",
				librarian.getEmploymentFinished().getTime(),
				librarian.getUserID());
		
		update4 = (librarian.hasIDCard()) ?
				AdminIDCards.getInstance().deactivateIDCard(
						librarian.getIdCardNumber(),
						IDCardDeactivationReason.OTHER) :
							true;

		return (update1 && update2 && update3 && update4) ? true : false;
	}
	
	/**
	 * This method is requesting database to be updated with details provided
	 * for Librarian that has been reactivated. Librarian ACTIVE generalStatus is
	 * updated in database.
	 * 
	 * @param librarian
	 * @return boolean
	 */
	public boolean reactivateLibrarian(Librarian librarian) {
		if(librarian == null) return false;
		
		// Changing user generalStatus and updating database
		librarian.setGeneralStatus(GeneralStatus.ACTIVE);
		return ParserObjectToSQL.getInstance().changeField(
					"LIBRARIANS",
					"STATUS",
					librarian.getGeneralStatus().toString(),
					librarian.getUserID());
	}
	
	/**
	 * This method creates new Librarian object from parameters provided,
	 * creates new record in database for that librarian and returns that
	 * librarian back to caller.
	 * 
	 * @param firstname
	 * @param middlename
	 * @param surname
	 * @param employmentStarted
	 * @return Librarian
	 */
	public Librarian newLibrarian(
			String firstname,
			String middlename,
			String surname,
			Calendar employmentStarted) {
					Librarian l = new Librarian(
							IDProvider.getInstance().getLibrarianNextID(),
							firstname,
							middlename,
							surname,
							employmentStarted);
					
					// Adding librarian to DB
					return (this.addLibrarian(l)) ? l : null;
	}
	
	/**
	 * This method returns librarian with specified id or NULL
	 * if librarian not found in database.
	 * 
	 * @param id
	 * @return Librarian
	 */
	public Librarian getLibrarian(long id){
		return ParserSQLToObject.getInstance().getLibrarian(id);
	}
	
	/**
	 * This method returns all ACTIVE and INACTIVE borrowers found in the
	 * database.
	 * 
	 * @return List
	 */
	public List<Borrower> getBorrowers() {
		return ParserSQLToObject.getInstance().getBorrowers(null);
	}
	
	/**
	 * This method returns all borrowers with specified generalStatus found in the
	 * database. They could be ACTIVE or INACTIVE, or both if specified generalStatus
	 * is NULL.
	 * 
	 * @return List
	 */
	public List<Borrower> getBorrowers(GeneralStatus generalStatus) {
		return ParserSQLToObject.getInstance().getBorrowers(generalStatus);
	}
	
	/**
	 * This method is adding a new Borrower to the database and returns
	 * TRUE if record was created successfully or FALSE if not. If userID
	 * is not set, than this method is asking for the next available id
	 * for borrower before adding him to the database.
	 * 
	 * @param borrower
	 * @return boolean
	 */
	public boolean addBorrower(Borrower borrower) {
		if(borrower == null) return false;
		
		if(borrower.getUserID() == 0L){
			borrower.setUserID(IDProvider.getInstance().getBorrowerNextID());
		}
		return ParserObjectToSQL.getInstance().newBorrower(borrower);
	}
	
	/**
	 * This method is adding Borrowers from the list to the database one
	 * by one.
	 * 
	 * @param borrowers
	 */
	public void addBorrowers(List<Borrower> borrowers) {
		Iterator<Borrower> iter = borrowers.iterator();
		while(iter.hasNext()){
			addBorrower(iter.next());
		}
	}
	
	/**
	 * This method creates new Borrower object from parameters provided,
	 * creates new record in database for that borrower and returns that
	 * borrower back to caller.
	 * 
	 * @param firstname
	 * @param middlename
	 * @param surname
	 * @return Borrower
	 */
	public Borrower newBorrower(String firstname, String middlename, String surname){
		Borrower borrower = new Borrower(
				IDProvider.getInstance().getBorrowerNextID(),
				firstname,
				middlename,
				surname,
				(Calendar) Calendar.getInstance(Locale.getDefault()).clone());
		
		// Adding borrower to DB
		return (addBorrower(borrower)) ? borrower : null;
	}
	
	/**
	 * This method returns borrower with specified id or NULL
	 * if borrower not found in database.
	 * 
	 * @param id
	 * @return Borrower
	 */
	public Borrower getBorrower(long id){
		return ParserSQLToObject.getInstance().getBorrower(id);
	}
	
	/**
	 * This method is requesting database to be updated with details provided
	 * for Borrower that has been deactivated. Borrower INACTIVE generalStatus with
	 * the date he was deactivated are saved in DB.
	 * 
	 * @param borrower
	 * @return boolean
	 */
	public boolean removeBorrower(Borrower borrower) {
		if(borrower == null) return false;
		
		boolean update1 = false;
		boolean update2 = false;
		boolean update3 = false;
		
		{
			// Changing user generalStatus and updating database
			borrower.setGeneralStatus(GeneralStatus.INACTIVE);
			update1 = ParserObjectToSQL.getInstance().changeField(
					"BORROWERS",
					"STATUS",
					borrower.getGeneralStatus().toString(),
					borrower.getUserID());
		}
		{
			// Setting deactivation date and updating database
			borrower.setDeactivated((Calendar) Calendar.getInstance(Locale.getDefault()).clone());
			update2 = ParserObjectToSQL.getInstance().changeField(
					"BORROWERS",
					"DEACTIVATED",
					borrower.getDeactivated().getTime(),
					borrower.getUserID());
		}
		{
			if(borrower.hasIDCard()){
				// Deactivating IDCard through IDCard admin
				update3 = AdminIDCards.getInstance().deactivateIDCard(
						borrower.getIdCardNumber(),
						IDCardDeactivationReason.OTHER);
			} else {
				update3 = true;
			}
		}
		
		return (update1 && update2 && update3) ? true : false;
	}
	
	/**
	 * This method is requesting database to be updated with details provided
	 * for Borrower that has been reactivated. Borrower ACTIVE generalStatus is
	 * updated in database.
	 * 
	 * @param borrower
	 * @return boolean
	 */
	public boolean reactivateBorrower(Borrower borrower) {
		if(borrower == null) return false;
		
		// Changing user generalStatus and updating database
		borrower.setGeneralStatus(GeneralStatus.ACTIVE);
		return ParserObjectToSQL.getInstance().changeField(
					"BORROWERS",
					"STATUS",
					borrower.getGeneralStatus().toString(),
					borrower.getUserID());
	}
	
	/**
	 * This method DOES NOT creates a record in database for new IDCard.
	 * This method ONLY setting IDCard in the user object and updates
	 * user record in database.
	 * 
	 * @param user
	 * @param card
	 * @return boolean
	 */
	public boolean activateIDCard(User user, IDCard card){
		if(user == null || card == null) return false;
		
		// Setting idCardNumber for user
		user.setIdCardNumber(card.getCardID());
		// Requesting user database update
		return ParserObjectToSQL.getInstance().changeField(
				getTableName(user),
				"IDCARD",
				card.getCardID(),
				user.getUserID());
	}
	
	/**
	 * This method DOES NOT deactivates existing user IDCard.
	 * This method ONLY removing IDCard in the user object and
	 * updates user record in database.
	 * 
	 * @param user
	 * @return boolean
	 */
	public boolean deactivateIDCard(long userID){
		if(userID <= 0L) return false;
		
		User user = getUser(userID);
		
		if(user == null) return false;
		
		// Set variable idCardNumber for user to 0L
		user.setIdCardNumber(0L);
		// Requesting database update
		return ParserObjectToSQL.getInstance().changeField(
				getTableName(user),
				"IDCARD",
				0L,
				user.getUserID());
	}
	
	/**
	 * Adds new address record for specified user and updates his
	 * current address record.
	 * 
	 * @param user
	 * @param address
	 * @return boolean
	 */
	public boolean newAddress(User user, Address address){
		if (user == null) return false;
		
		// Requesting to create new record for address
		if (!ParserObjectToSQL.getInstance().newAddress(address)) return false;
		
		// Changing address in user object
		user.changeAddress(address);
			// Requesting database update
		if(ParserObjectToSQL.getInstance().changeField(
				getTableName(user),
				"CURRENTADDRESS",
				user.getCurrentAddress().getAddressID(),
				user.getUserID())){
						// Requesting to create new record for user address history
						return ParserObjectToSQL.getInstance().newAddressHistory(
							user, address);
		} else {
			// Current address record for user not updated in DB
			return false;
		}
	}
	
	/**
	 * Updates specified address record in the database.
	 * 
	 * @param user
	 * @param address
	 * @return boolean
	 */
	public boolean updateAddress(Address address){
		if (address == null) return false;
		
		// Requesting database update
		return ParserObjectToSQL.getInstance().updateAddress(address);
	}
	
	/**
	 * Retrieves address data from database with specified id.
	 * 
	 * @param addressID
	 * @return Address
	 */
	public Address getAddress(long addressID){
		if(addressID == 0L) return null;
		
		return ParserSQLToObject.getInstance().getAddress(addressID);
	}
	
	/**
	 * If user id is known and user class is unknown than use this
	 * method to get either librarian or borrower with type of User.
	 * 
	 * @param userID
	 * @return User
	 */
	public User getUser(long userID){
		if(userID <= 0L) return null;
		
		Librarian l = getLibrarian(userID);
		return (l != null) ? l : getBorrower(userID);
	}
	
	/**
	 * Private method that returns table name in DB for user, which
	 * can be either 'BORROWERS' or LIBRARIANS'
	 * 
	 * @param user
	 * @return String
	 */
	private String getTableName(User user){
		return (user != null) ?
				(user.getClass().getSimpleName().equals("Borrower")) ?
						"BORROWERS" :
							"LIBRARIANS" :
								null;
	}
	
	/**
	 * This method is requesting database STRING record update. If user
	 * record in database updated successfully TRUE. It returns FALSE if
	 * user record in database was not updated successfully.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return TRUE or FALSE
	 */
	public boolean updateBorrowerDB(long id, String field, String data){
		return ParserObjectToSQL.getInstance().changeField("BORROWERS",
				field.toUpperCase(),
				data,
				id);
	}
	
	/**
	 * This method is requesting database DATE record update. If user
	 * record in database updated successfully TRUE. It returns FALSE if
	 * user record in database was not updated successfully.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return TRUE or FALSE
	 */
	public boolean updateBorrowerDB(long id, String field, java.util.Date data){
		return ParserObjectToSQL.getInstance().changeField("BORROWERS",
				field.toUpperCase(),
				data,
				id);
	}
	
	/**
	 * This method is requesting database STRING record update. If user
	 * record in database updated successfully TRUE. It returns FALSE if
	 * user record in database was not updated successfully.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return TRUE or FALSE
	 */
	public boolean updateLibrarianDB(long id, String field, String data){
		return ParserObjectToSQL.getInstance().changeField("LIBRARIANS",
				field.toUpperCase(),
				data,
				id);
	}
	
	/**
	 * This method is requesting database DATE record update. If user
	 * record in database updated successfully TRUE. It returns FALSE if
	 * user record in database was not updated successfully.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return TRUE or FALSE
	 */
	public boolean updateLibrarianDB(long id, String field, java.util.Date data){
		return ParserObjectToSQL.getInstance().changeField("LIBRARIANS",
				field.toUpperCase(),
				data,
				id);
	}
}