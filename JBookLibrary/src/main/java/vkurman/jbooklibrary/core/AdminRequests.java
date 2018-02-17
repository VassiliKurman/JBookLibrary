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

import vkurman.jbooklibrary.db.ParserObjectToSQL;
import vkurman.jbooklibrary.db.ParserSQLToObject;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.ItemType;

/**
 * Administrator that handles item requests.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AdminRequests {
	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the idProvider variable correctly when it is being
	 * initialised to the IDProviderUser instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static AdminRequests admin;
	
	/**
	 * Default constructor.
	 */
	private AdminRequests() {
		
	}
	
	/**
	 * This method is checking if the instance of current class exists
	 * in the system and returns ever the instance that has been created
	 * earlier or the newly created instance.
	 * @return AdminRequests object
	 */
	public static AdminRequests getInstance() {
		// Double-checked locking with synchronisation
		if(admin == null) {
			synchronized (AdminRequests.class) {
				if(admin == null) {
					admin = new AdminRequests();
				}
			}
		}
		// Under either circumstance this returns the instance
		return admin;
	}
	
	/**
	 * Saves new request to database.
	 * 
	 * @param request
	 * @return boolean
	 */
	public boolean newRequest(Request request){
		// Checking that book is not null
		if(request == null) return false;
		
		// Checking if book has ID set up
		if(request.getID() == 0){
			request.setID(IDProvider.getInstance().getRequestNextID());
		}
		// Adding book to database
		return ParserObjectToSQL.getInstance().newRequest(request);
	}
	
	/**
	 * Returns <code>Request</code> with specified parameters.
	 * 
	 * @param status
	 * @param type
	 * @return Requests
	 */
	public List<Request> getRequests(GeneralStatus status, ItemType type){
		return ParserSQLToObject.getInstance().getRequests(
				status,
				type);
	}
	
	/**
	 * Requesting book administrator to change request status to inactive.
	 * FALSE returned if request is NULL, as well as if book status has not
	 * been changed.
	 * 
	 * @param request
	 * @return boolean
	 */
	public boolean remove(Request request) {
		if(request == null) return false;
		
		request.setStatus(GeneralStatus.INACTIVE);
		// Requesting database update
		if(ParserObjectToSQL.getInstance().changeField(
				"REQUESTS",
				"STATUS",
				request.getStatus().toString(),
				request.getID())){
						request.setCancelationDate((Calendar) Calendar.getInstance().clone());
						// Requesting database update
						return ParserObjectToSQL.getInstance().changeField(
								"REQUESTS",
								"CANCELATIONDATE",
								request.getCancelationDate().getTime(),
								request.getID());
		} else {
			return false;
		}
	}
	
	public boolean received(Request request) {
		if(request == null) return false;
		
		request.setStatus(GeneralStatus.INACTIVE);
		// Requesting database update
		if(ParserObjectToSQL.getInstance().changeField(
				"REQUESTS",
				"STATUS",
				request.getStatus().toString(),
				request.getID())){
						request.setCancelationDate((Calendar) Calendar.getInstance().clone());
						// Requesting database update
						if(ParserObjectToSQL.getInstance().changeField(
								"REQUESTS",
								"CANCELATIONDATE",
								request.getCancelationDate().getTime(),
								request.getID())){
							boolean result = true;
							for(int i = 0; i < request.getQuantity(); i++){
								if(request.getType() == ItemType.BOOK){
									Book book = new Book();
									
									Calendar year = Calendar.getInstance();
									year.set(Calendar.YEAR, Integer.parseInt(request.getYear()));
									year.set(Calendar.MONTH, 0);
									year.set(Calendar.DAY_OF_MONTH, 1);
									
									book.setIsbn(request.getIsbn());
									book.setTitle(request.getTitle());
									book.setEdition(Integer.parseInt(request.getIssue()));
									book.setPublicationDate(year);
									book.addAuthor(request.getAuthor());
									book.setPublisher(request.getPublisher());
									
									if(!AdminBooks.getInstance().addBook(book)){
										return false;
									}
								}
							}
							return result;
						}
		}
		return false;
	}
}