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
import java.util.Locale;

import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.ItemType;

/**
 * Request object that holds data about requested item.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Request {
	private long id;
	private ItemType type;
	private String isbn;
	private String title;
	private String issue;
	private String year;
	private String author;
	private String publisher;
	private String comments;
	private int quantity;
	private Calendar requestDate;
	private Calendar cancelationDate;
	private GeneralStatus status;
	
	public Request(){
		requestDate = (Calendar)Calendar.getInstance(Locale.getDefault()).clone();
	}
	
	/**
	 * Returns request id.
	 * 
	 * @return long
	 */
	public long getID() {
		return id;
	}
	
	/**
	 * Sets request id.
	 * 
	 * @param id
	 */
	public void setID(long id) {
		this.id = id;
	}
	
	/**
	 * Returns item type.
	 * 
	 * @return ItemType
	 */
	public ItemType getType() {
		return type;
	}
	
	/**
	 * Sets item type.
	 * 
	 * @param type
	 */
	public void setType(ItemType type) {
		this.type = type;
	}
	
	/**
	 * Return isbn.
	 * 
	 * @return String
	 */
	public String getIsbn() {
		return isbn;
	}
	
	/**
	 * Sets isbn.
	 * 
	 * @param isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	/**
	 * Returns item title.
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets item title.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Return issue
	 * 
	 * @return String
	 */
	public String getIssue() {
		return issue;
	}
	
	/**
	 * Sets year
	 * 
	 * @param issue
	 */
	public void setIssue(String issue) {
		this.issue = issue;
	}

	/**
	 * Return year
	 * 
	 * @return String
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * Sets year
	 * 
	 * @param year
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Return author
	 * 
	 * @return String
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Sets author
	 * 
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Return publisher
	 * 
	 * @return String
	 */
	public String getPublisher() {
		return publisher;
	}
	
	/**
	 * Sets publisher
	 * 
	 * @param publisher
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * Return quantity
	 * 
	 * @return int
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Sets quantity
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Return comments
	 * 
	 * @return String
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * Sets comments
	 * 
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * Return date when request was made
	 * 
	 * @return Calendar
	 */
	public Calendar getRequestDate() {
		return requestDate;
	}
	
	/**
	 * Sets date when request was made
	 * 
	 * @param date
	 */
	public void setRequestDate(Calendar date) {
		this.requestDate = date;
	}
	
	/**
	 * Return date when request was cancelled
	 * 
	 * @return Calendar
	 */
	public Calendar getCancelationDate() {
		return cancelationDate;
	}
	
	/**
	 * Sets date when request was cancelled
	 * 
	 * @param cancelationDate
	 */
	public void setCancelationDate(Calendar cancelationDate) {
		this.cancelationDate = cancelationDate;
	}
	
	/**
	 * Return status
	 * 
	 * @return GeneralStatus
	 */
	public GeneralStatus getStatus() {
		return status;
	}
	
	/**
	 * Sets status
	 * 
	 * @param status
	 */
	public void setStatus(GeneralStatus status) {
		this.status = status;
	}
}