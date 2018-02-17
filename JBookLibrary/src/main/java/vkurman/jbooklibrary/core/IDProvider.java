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

import vkurman.jbooklibrary.db.ParserSQLToObject;

/**
 * This class provides id's for all the objects in the application that
 * will be saved in the database.
 * The IDProvider class takes benefits of Singleton Design Pattern
 * and double-checked locking with synchronisation in the first round
 * checking to make sure there are only one instance of IDProvider
 * exists in the system.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDProvider{

	public static long ADDRESS_DEFAULT_ID = 0L;
	public static long ADDRESS_HISTORY_DEFAULT_ID = 0L;
	public static long BOOK_DEFAULT_ID = 0L;
	public static long BORROWER_DEFAULT_ID = AdminPrefs.BORROWER_MIN_ID_NUMBER;
	public static long BORROWER_MAX_ID = AdminPrefs.BORROWER_MAX_ID_NUMBER;
	public static long FINE_DEFAULT_ID = 0L;
	public static long IDCARD_DEFAULT_ID = 0L;
	public static long LIBRARIAN_DEFAULT_ID = AdminPrefs.LIBRARIAN_MIN_ID_NUMBER;
	public static long LIBRARIAN_MAX_ID = AdminPrefs.LIBRARIAN_MAX_ID_NUMBER;
	public static long LOAN_DEFAULT_ID = 0L;
	public static long PAYMENT_DEFAULT_ID = 0L;
	public static long REQUEST_DEFAULT_ID = 0L;
	public static long RESERVATION_DEFAULT_ID = 0L;
	
	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the idProvider variable correctly when it is being
	 * initialised to the IDProviderPayment instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static IDProvider idProvider;
	
	/**
	 * Default private Constructor
	 */
	private IDProvider() {
	}
	
	/**
	 * This method ensures that only one instance of IDProvider
	 * exists during application run time and returns that instance.
	 * 
	 * @return IDProvider
	 */
	public static IDProvider getInstance() {
		// Double-checked locking with synchronisation
		if(idProvider == null) {
			synchronized (IDProvider.class) {
				if(idProvider == null) {
					idProvider = new IDProvider();
				}
			}
		}
		// Under either circumstance this returns the instance
		return idProvider;
	}
	
	/**
	 * This method returns last used id for address object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getAddressLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("ADDRESSES");
		return (id > ADDRESS_DEFAULT_ID) ? id : ADDRESS_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for address object.
	 * 
	 * @return long
	 */
	public long getAddressNextID() {
		return getAddressLastID() + 1;
	}
	
	/**
	 * This method returns last used id for address history. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getAddressHistoryLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("ADDRESSHISTORIES");
		return (id > ADDRESS_HISTORY_DEFAULT_ID) ? id : ADDRESS_HISTORY_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for address history object.
	 * 
	 * @return long
	 */
	public long getAddressHistoryNextID() {
		return getAddressHistoryLastID() + 1;
	}
	
	/**
	 * This method returns last used id for book object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getBookLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("BOOKS");
		return (id > BOOK_DEFAULT_ID) ? id : BOOK_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for book object.
	 * 
	 * @return long
	 */
	public long getBookNextID() {
		return getBookLastID() + 1;
	}
	
	/**
	 * This method returns last used id for borrower object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getBorrowerLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("BORROWERS");
		return (id > BORROWER_DEFAULT_ID) ? id : BORROWER_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for borrower object.
	 * 
	 * @return long
	 */
	public long getBorrowerNextID() {
		return getBorrowerLastID() + 1;
	}
	
	/**
	 * This method returns last used id for fine object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getFineLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("FINES");
		return (id > FINE_DEFAULT_ID) ? id : FINE_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for fine object.
	 * 
	 * @return long
	 */
	public long getFineNextID() {
		return getFineLastID() + 1;
	}
	
	/**
	 * This method returns last used id for idcard object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getIDCardLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("IDCARDS");
		return (id > IDCARD_DEFAULT_ID) ? id : IDCARD_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for idcard object.
	 * 
	 * @return long
	 */
	public long getIDCardNextID() {
		return getIDCardLastID() + 1;
	}
	
	/**
	 * This method returns last used id for librarian object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getLibrarianLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("LIBRARIANS");
		return (id > LIBRARIAN_DEFAULT_ID) ? id: LIBRARIAN_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for librarian object.
	 * 
	 * @return long
	 */
	public long getLibrarianNextID() {
		return getLibrarianLastID() + 1;
	}
	
	/**
	 * This method returns last used id for loan object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getLoanLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("LOANS");
		return (id > LOAN_DEFAULT_ID) ? id : LOAN_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for loan object.
	 * 
	 * @return long
	 */
	public long getLoanNextID() {
		return getLoanLastID() + 1;
	}
	
	/**
	 * This method returns last used id for payment object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getPaymentLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("PAYMENTS");
		return (id > PAYMENT_DEFAULT_ID) ? id : PAYMENT_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for payment object.
	 * 
	 * @return long
	 */
	public long getPaymentNextID() {
		return getPaymentLastID() + 1;
	}
	
	/**
	 * This method returns last used id for reservation object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getReservationLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("RESERVATIONS");
		return (id > RESERVATION_DEFAULT_ID) ? id : RESERVATION_DEFAULT_ID;
	}
	
	/**
	 * This method returns next available id for reservation object.
	 * 
	 * @return long
	 */
	public long getReservationNextID() {
		return getReservationLastID() + 1;
	}
	
	/**
	 * Returns last used id for request object. It returns
	 * default id if no record found.
	 * 
	 * @return long
	 */
	public long getRequestLastID() {
		long id = ParserSQLToObject.getInstance().getLastID("REQUESTS");
		return (id > REQUEST_DEFAULT_ID) ? id : REQUEST_DEFAULT_ID;
	}
	
	/**
	 * Returns next available id for request object.
	 * 
	 * @return long
	 */
	public long getRequestNextID() {
		return getRequestLastID() + 1;
	}
}