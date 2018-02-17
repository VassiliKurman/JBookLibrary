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
import vkurman.jbooklibrary.enums.GeneralStatus;

/**
 * This class manages all reservations.
 * 
* <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
*/
public class AdminReservations {

	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the admin variable correctly when it is being
	 * initialised to the AdminReservations instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static AdminReservations admin;
	
	/**
	 * Private Default Constructor.
	 */
	private AdminReservations() {
	}
	
	/**
	 * This method ensures that only one instance of this class
	 * exists in the system while application is running.
	 * 
	 * @return AdminReservations
	 */
	public static AdminReservations getInstance() {
		// Double-checked locking with synchronisation
		if(admin == null) {
			synchronized (AdminReservations.class) {
				if(admin == null) {
					admin = new AdminReservations();
				}
			}
		}
		// Under either circumstance this returns the instance
		return admin;
	}
	
	/**
	 * This method is requesting database STRING record update. If reservation
	 * record in database updated successfully this method returns TRUE. This
	 * method returns FALSE if reservation record in database was not updated
	 * successfully.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return boolean
	 */
	public boolean updateReservationDB(int id, String field, String data){
		return ParserObjectToSQL.getInstance().changeField("RESERVATIONS",
				field.toUpperCase(),
				data,
				id);
	}
	
	/**
	 * This method requests to find if book is reserved in database. This method
	 * returns TRUE if object is returned from request and FALSE in situations where
	 * NULL is returned from request or NULL is passed to this method as parameter.
	 * 
	 * @param book
	 * @return boolean
	 */
	public boolean isReserved(Book book){
		// Checking that book is not null
		if(book == null) return false;
		
		Reservation reservation = getActiveReservation(book);
		return (reservation != null) ? true : false;
	}
	
	/**
	 * This method is getting all active reservations from database
	 * and is asking 'isReservationExpired()' method to check each
	 * reservations generalStatus and update it if necessary.
	 */
	public void updateReservationsStatus(){
		List<Reservation> list = getAllActiveReservations();
		if(!list.isEmpty()){
			Iterator<Reservation> iter = list.iterator();
			while(iter.hasNext()){
				isReservationExpired(iter.next());
			}
		}
	}
	
	/**
	 * Getter for Reservation with id provided in parameter. NULL is
	 * returned if reservation not found or provided id is 0L.
	 * 
	 * @param id
	 * @return Reservation
	 */
	public Reservation getReservation(long id){
		return (id > 0L) ? ParserSQLToObject.getInstance().getReservation(id) : null;
	}
	
	/**
	 * This method returns active reservation for book specified.
	 * 
	 * @param book
	 * @return Reservation
	 */
	public Reservation getActiveReservation(Book book){
		if(book == null) return null;
		
		List<Reservation> reservations = getActiveBookReservations(book);
		if(reservations.isEmpty()) return null;
		
		Iterator<Reservation> iter = reservations.iterator();
		Reservation reservation = null;
		while(iter.hasNext()){
			Reservation temp = iter.next();
			if(temp != null){
				if(!isReservationExpired(temp)){
					reservation = temp;
				}
			}
		}
		return reservation;
	}
	
	/**
	 * This method is checking if reservation's expire date is before
	 * current system time or not. If expire date is before current date,
	 * than this method is asking 'remove()' method to deactivate
	 * reservation, else it returns FALSE. If specified reservation is
	 * NULL than TRUE is returned.
	 * 
	 * @param reservation
	 * @return boolean
	 */
	public boolean isReservationExpired(Reservation reservation){
		if(reservation == null) return true;
		
		return (reservation.getExpireDate().before((Calendar) Calendar.getInstance(Locale.getDefault()))) ?
				remove(reservation) :
					false;
	}
	
	/**
	 * This method returns all reservations, both ACTIVE and
	 * INACTIVE.
	 * 
	 * @return List of Reservations
	 */
	public List<Reservation> getAllReservations(){
		return ParserSQLToObject.getInstance().getReservations(
				null,
				0L,
				0L);
	}
	
	/**
	 * This method returns all ACTIVE reservations.
	 * 
	 * @return List of Reservations
	 */
	public List<Reservation> getAllActiveReservations(){
		return ParserSQLToObject.getInstance().getReservations(
				GeneralStatus.ACTIVE,
				0L,
				0L);
	}
	
	/**
	 * This method returns all INACTIVE reservations.
	 * 
	 * @return List of Reservations
	 */
	public List<Reservation> getAllInactiveReservations(){
		return ParserSQLToObject.getInstance().getReservations(
				GeneralStatus.INACTIVE,
				0L,
				0L);
	}
	
	/**
	 * This method returns all reservations for specified user, both
	 * ACTIVE and INACTIVE.
	 * 
	 * @param user
	 * @return List of Reservations
	 */
	public List<Reservation> getAllUserReservations(User user){
		return (user != null) ?
				ParserSQLToObject.getInstance().getReservations(
						null,
						user.getUserID(),
						0L) :
							new ArrayList<Reservation>();
	}
	
	/**
	 * This method returns all ACTIVE reservations for specified user.
	 * 
	 * @param user
	 * @return List of Reservations
	 */
	public List<Reservation> getActiveUserReservations(User user){
		return (user != null) ?
				ParserSQLToObject.getInstance().getReservations(
						GeneralStatus.ACTIVE,
						user.getUserID(),
						0L) :
							new ArrayList<Reservation>();
	}
	
	/**
	 * This method returns all INACTIVE reservations for specified
	 * user.
	 * 
	 * @param user
	 * @return List of Reservations
	 */
	public List<Reservation> getInactiveUserReservations(User user){
		return (user != null) ?
				ParserSQLToObject.getInstance().getReservations(
						GeneralStatus.INACTIVE,
						user.getUserID(),
						0L) :
							new ArrayList<Reservation>();
	}
	
	/**
	 * This method returns all reservations for specified book, both
	 * ACTIVE and INACTIVE.
	 * 
	 * @param book
	 * @return List of Reservations
	 */
	public List<Reservation> getAllBookReservations(Book book){
		return (book != null) ?
				ParserSQLToObject.getInstance().getReservations(
						null,
						0L,
						book.getBookID()) :
							new ArrayList<Reservation>();
	}
	
	/**
	 * Getting list of ACTIVE reservations for specified book. There
	 * should be only one entry in the list as only one reservation is
	 * aloud at any time.
	 * 
	 * @param book
	 * @return List of Reservations
	 */
	public List<Reservation> getActiveBookReservations(Book book){
		return (book != null) ?
				ParserSQLToObject.getInstance().getReservations(
						GeneralStatus.ACTIVE,
						0L,
						book.getBookID()) :
							new ArrayList<Reservation>();
	}
	
	/**
	 * Getting all INACTIVE reservations for specified book.
	 * 
	 * @param book
	 * @return List of Reservations
	 */
	public List<Reservation> getInactiveBookReservations(Book book){
		return (book != null) ?
				ParserSQLToObject.getInstance().getReservations(
						GeneralStatus.INACTIVE,
						0L,
						book.getBookID()) :
							new ArrayList<Reservation>();
	}
	
	/**
	 * Reserves book for specified user. Active reservations are checked to
	 * make sure that that specified book is not reserved yet as only one
	 * reservation allowed at the any time for book.
	 * This method must be called from book administrator only to make sure
	 * that book generalStatus has been changed before creating new reservation
	 * record in database.
	 * 
	 * @param user
	 * @param item
	 * @return boolean
	 */
	public boolean reserve(User user, Book book) {
		if(user == null || book == null) return false;
		
		if(isReserved(book) && AdminBooks.getInstance().isBookStatusDisposed(book)) return false;
		
		Reservation r = new Reservation(IDProvider.getInstance()
					.getReservationNextID(), user.getUserID(), book.getBookID());
		if (AdminBooks.getInstance().isBookStatusOnLoan(book)) {
			/* Setting reservation expire date, which should start after book returned
			 * if it is on loan
			 */
			Calendar expireDate = (Calendar) AdminLoans.getInstance()
					.getBookActiveLoan(book.getBookID()).getDueDate().clone();
			expireDate.add(Calendar.YEAR,
					AdminPrefs.STANDARD_RESERVATION_PERIOD);
			r.setExpireDate(expireDate);
		} else {
			// Setting reservation expire date
			Calendar expireDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
			expireDate.add(Calendar.DATE, AdminPrefs.STANDARD_RESERVATION_PERIOD);
			r.setExpireDate(expireDate);
		}
		// Adding new reservation to database
		return addReservation(r);
	}
	
	/**
	 * This method is adding new reservation to database. It is returning TRUE
	 * if reservation added to database successfully
	 * 
	 * @param reservation
	 * @return boolean
	 */
	public boolean addReservation(Reservation reservation){
		return (reservation != null) ?
				ParserObjectToSQL.getInstance().newReservation(reservation) :
					false;
	}
	
	/**
	 * Requesting book administrator to change book generalStatus and changing
	 * reservation generalStatus to inactive. FALSE returned if reservation is
	 * NULL or book is NULL, as well as if book generalStatus has not been changed.
	 * 
	 * @param book
	 * @return boolean
	 */
	public boolean remove(Book book) {
		// Checking that book object exists
		if(book == null) return false;
		
		Reservation reservation = getActiveReservation(book);
		// Checking that reservation object exists
		if(reservation == null) return false;
		
		// Changing book generalStatus
		if(!AdminBooks.getInstance().changeBookStatusFromReserved(book)) return false;
		
		// Changing reservation generalStatus
		reservation.setGeneralStatus(GeneralStatus.INACTIVE);
		// Requesting database update
		return ParserObjectToSQL.getInstance().changeField(
				"RESERVATIONS",
				"STATUS",
				reservation.getGeneralStatus().toString(),
				reservation.getReservationID());
	}
	
	/**
	 * Requesting book administrator to change book generalStatus and changing
	 * reservation generalStatus to inactive. FALSE returned if reservation is
	 * NULL, as well as if book generalStatus has not been changed.
	 * 
	 * @param reservation
	 * @return boolean
	 */
	public boolean remove(Reservation reservation) {
		if(reservation == null) return false;
		
		if(!AdminBooks.getInstance().changeBookStatusFromReserved(reservation.getBookID())) return false;
		
		// Changing reservation generalStatus
		reservation.setGeneralStatus(GeneralStatus.INACTIVE);
		// Requesting database update
		return ParserObjectToSQL.getInstance().changeField(
				"RESERVATIONS",
				"STATUS",
				reservation.getGeneralStatus().toString(),
				reservation.getReservationID());
	}
}