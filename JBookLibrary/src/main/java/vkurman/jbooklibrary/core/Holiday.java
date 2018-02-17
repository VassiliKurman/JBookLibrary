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

/**
 * Holiday class that represents all dates in the calendar when
 * library is closed. Standard weekends are not included here.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Holiday {
	
	private Calendar date;
	private boolean isRepeatable;
	
	/**
	 * Constructor. Sets <code>date</code> to current date and
	 * <code>isRepeatable</code> to <code>true</code>.
	 */
	public Holiday(){
		this((Calendar) Calendar.getInstance().clone(), true);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param date
	 * @param isRepeatable
	 */
	public Holiday(Calendar date, boolean isRepeatable){
		this.date = date;
		this.isRepeatable = isRepeatable;
	}
	
	/**
	 * Returns date of holiday.
	 * 
	 * @return Calendar
	 */
	public Calendar getDate() {
		return date;
	}
	
	/**
	 * Sets date of holiday.
	 * 
	 * @param date
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	/**
	 * Returns <code>true</code> if holiday is repeated every
	 * year.
	 * 
	 * @return boolean
	 */
	public boolean isRepeatable() {
		return isRepeatable;
	}
	
	/**
	 * Sets specified value for <code>isRepeatable</code> field.
	 * 
	 * @param isRepeatable
	 */
	public void setRepeatable(boolean isRepeatable) {
		this.isRepeatable = isRepeatable;
	}
}