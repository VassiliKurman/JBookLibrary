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

package vkurman.jbooklibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vkurman.jbooklibrary.core.AdminPrefs;

/**
 * This class main purpose is to keep date format consistent throughout
 * the application. This class uses java.text.SimpleDateFormat class to
 * format the date.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BasicLibraryDateFormatter {
	
	/**
	 * This is static method that can be used anywhere in the application
	 * where date formatting is required. This method takes in parameter of
	 * java.util.Calendar class and returns String which contains only year,
	 * month, day of the month, hour, minutes and seconds in the format,
	 * specified in preferences.
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String formatDateExtended(Calendar calendar){
		return new SimpleDateFormat(AdminPrefs.DATE_FORMAT_STRING_EXTENDED).format(calendar.getTime());
	}
	
	/**
	 * This is static method that can be used anywhere in the application
	 * where date formatting is required. This method takes in parameter of
	 * java.util.Date class and returns String which contains only year,
	 * month, day of the month, hour, minutes and seconds in the format,
	 * specified in preferences.
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateExtended(Date date){
		return new SimpleDateFormat(AdminPrefs.DATE_FORMAT_STRING_EXTENDED).format(date);
	}
	
	/**
	 * This is static method that can be used anywhere in the application
	 * where date formatting is required. This method takes in parameter of
	 * java.util.Calendar class and returns String which contains only year,
	 * month and day of the month in the format, specified in preferences.
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String formatDate(Calendar calendar){
		return calendar == null ?
				"" :
					new SimpleDateFormat(AdminPrefs.DATE_FORMAT_STRING).format(calendar.getTime());
	}
	
	/**
	 * This is static method that can be used anywhere in the application
	 * where date formatting is required. This method takes in parameter of
	 * java.util.Date class and returns String which contains only year,
	 * month and day of the month in the format, specified in preferences.
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date){
		return new SimpleDateFormat(AdminPrefs.DATE_FORMAT_STRING).format(date);
	}
	
	/**
	 * This is static method that can be used anywhere in the application
	 * where date formatting is required. This method takes in parameter of
	 * java.util.Calendar class and returns String which contains only year
	 * in format 'yyyy'.
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String formatDateToYear(Calendar calendar){
		return new SimpleDateFormat(AdminPrefs.DATE_FORMAT_STRING_YEAR).format(calendar.getTime());
	}
	
	/**
	 * This is static method that can be used anywhere in the application
	 * where date formatting is required. This method takes in parameter of
	 * java.util.Date class and returns String which contains only year in
	 * format 'yyyy'.
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateToYear(Date date){
		return new SimpleDateFormat(AdminPrefs.DATE_FORMAT_STRING_YEAR).format(date);
	}
}