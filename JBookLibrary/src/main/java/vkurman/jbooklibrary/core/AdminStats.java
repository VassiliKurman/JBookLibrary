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
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.ItemStatus;

/**
 * AdminStats class is providing basic statistical information from database
 * and can be used by any class from anywhere in the application.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AdminStats {

	///////////////////////////////
	/// Books Statistics        ///
	///////////////////////////////
	
	/**
	 * Getting the number of books with disposed generalStatus.
	 * 
	 * @return int
	 */
	public static int getNumberOfDisposedBooks() {
		return ParserSQLToObject.getInstance().getCount(
				"BOOKS",
				"STATUS",
				ItemStatus.DISPOSED.toString());
	}
	
	/**
	 * Getting the number of books with on loan generalStatus.
	 * 
	 * @return int
	 */
	public static int getNumberOfOnLoanBooks() {
		return ParserSQLToObject.getInstance().getCount(
				"BOOKS",
				"STATUS",
				ItemStatus.ONLOAN.toString());
	}
	
	/**
	 * Getting the number of books with on shelf generalStatus.
	 * 
	 * @return int
	 */
	public static int getNumberOfOnShelfBooks() {
		return ParserSQLToObject.getInstance().getCount(
				"BOOKS",
				"STATUS",
				ItemStatus.ONSHELF.toString());
	}
	
	/**
	 * Getting the number of books with reserved generalStatus.
	 * 
	 * @return int
	 */
	public static int getNumberOfReservedBooks() {
		return ParserSQLToObject.getInstance().getCount(
				"BOOKS",
				"STATUS",
				ItemStatus.RESERVED.toString());
	}
	
	/**
	 * Getting the number of books with unknown generalStatus.
	 * 
	 * @return int
	 */
	public static int getNumberOfUnknownBooks() {
		return ParserSQLToObject.getInstance().getCount(
				"BOOKS",
				"STATUS",
				ItemStatus.UNKNOWN.toString());
	}
	
	///////////////////////////////
	/// Fines Statistics        ///
	///////////////////////////////
	
	/**
	 * Getting the number of active fines.
	 * 
	 * @return int
	 */
	public static int getNumberOfActiveFines() {
		return ParserSQLToObject.getInstance().getCount(
				"FINES",
				"STATUS",
				GeneralStatus.ACTIVE.toString());
	}
	
	/**
	 * Getting the number of inactive fines.
	 * 
	 * @return int
	 */
	public static int getNumberOfInactiveFines() {
		return ParserSQLToObject.getInstance().getCount(
				"FINES",
				"STATUS",
				GeneralStatus.INACTIVE.toString());
	}
	
	///////////////////////////////
	/// IDCards Statistics      ///
	///////////////////////////////
	
	/**
	 * Getting the number of active IDCards.
	 * 
	 * @return int
	 */
	public static int getNumberOfActiveIDCards() {
		return ParserSQLToObject.getInstance().getCount(
				"IDCARDS",
				"STATUS",
				GeneralStatus.ACTIVE.toString());
	}
	
	/**
	 * Getting the number of inactive IDCards.
	 * 
	 * @return int
	 */
	public static int getNumberOfInactiveIDCards() {
		return ParserSQLToObject.getInstance().getCount(
				"IDCARDS",
				"STATUS",
				GeneralStatus.INACTIVE.toString());
	}
	
	///////////////////////////////
	/// Loans Statistics        ///
	///////////////////////////////
	
	/**
	 * Getting the number of active loans.
	 * 
	 * @return int
	 */
	public static int getNumberOfActiveLoans() {
		return ParserSQLToObject.getInstance().getCount(
				"LOANS",
				"STATUS",
				GeneralStatus.ACTIVE.toString());
	}
	
	/**
	 * Getting the number of inactive loans.
	 * 
	 * @return int
	 */
	public static int getNumberOfInactiveLoans() {
		return ParserSQLToObject.getInstance().getCount(
				"LOANS",
				"STATUS",
				GeneralStatus.INACTIVE.toString());
	}
	
	/**
	 * Getting the number of active overdue loans.
	 * 
	 * @return int
	 */
	public static int getNumberOfOverdueActiveLoans() {
		return ParserSQLToObject.getInstance().getActiveOverdueLoansCount();
	}
	
	///////////////////////////////
	/// Payments Statistics     ///
	///////////////////////////////
	
	/**
	 * Getting the number of payments.
	 * 
	 * @return int
	 */
	public static int getNumberOfPayments() {
		return ParserSQLToObject.getInstance().getCount(
				"PAYMENTS",
				"ID");
	}
	
	///////////////////////////////
	/// Reservations Statistics ///
	///////////////////////////////
	
	/**
	 * Getting the number of active reservations.
	 * 
	 * @return int
	 */
	public static int getNumberOfActiveReservations() {
		return ParserSQLToObject.getInstance().getCount(
				"RESERVATIONS",
				"STATUS",
				GeneralStatus.ACTIVE.toString());
	}
	
	/**
	 * Getting the number of inactive reservations.
	 * 
	 * @return int
	 */
	public static int getNumberOfInactiveReservations() {
		return ParserSQLToObject.getInstance().getCount(
				"RESERVATIONS",
				"STATUS",
				GeneralStatus.INACTIVE.toString());
	}
	
	///////////////////////////////
	/// Users Statistics        ///
	///////////////////////////////
	
	/**
	 * Getting the number of active borrowers.
	 * 
	 * @return int
	 */
	public static int getNumberOfActiveBorrowers() {
		return ParserSQLToObject.getInstance().getCount(
				"BORROWERS",
				"STATUS",
				GeneralStatus.ACTIVE.toString());
	}
	
	/**
	 * Getting the number of inactive borrowers.
	 * 
	 * @return int
	 */
	public static int getNumberOfInactiveBorrowers() {
		return ParserSQLToObject.getInstance().getCount(
				"BORROWERS",
				"STATUS",
				GeneralStatus.INACTIVE.toString());
	}
	
	/**
	 * Getting the number of active librarians.
	 * 
	 * @return int
	 */
	public static int getNumberOfActiveLibrarians() {
		return ParserSQLToObject.getInstance().getCount(
				"LIBRARIANS",
				"STATUS",
				GeneralStatus.ACTIVE.toString());
	}
	
	/**
	 * Getting the number of inactive librarians.
	 * 
	 * @return int
	 */
	public static int getNumberOfInactiveLibrarians() {
		return ParserSQLToObject.getInstance().getCount(
				"LIBRARIANS",
				"STATUS",
				GeneralStatus.INACTIVE.toString());
	}
}