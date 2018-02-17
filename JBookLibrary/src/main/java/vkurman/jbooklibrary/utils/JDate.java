package vkurman.jbooklibrary.utils;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/**
 * JDate class is a small utility class for Java Basic Library application
 * that provides static methods for calculating dates and also generating
 * random dates.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class JDate {
	
	/**
	 * This method calculates the difference between 2 Calendar
	 * objects in days.
	 * 
	 * @param c1
	 * @param c2
	 * @return int
	 */
	public static int daysDifference(Calendar c1, Calendar c2){
		return (int)((c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000 / 60 / 60 / 24);
	}
	
	/**
	 * This method returns date before current limited with specified
	 * parameter. If limitDays parameter is less or equal to 0, than
	 * default limit set to 10 years (3653 days).
	 * 
	 * @param limitDays
	 * @return Calendar
	 */
	public static Calendar generateRandomDateBeforeCurrent(int limitDays){
		int limit = 0;
		if(limitDays <= 0) {
			limit = 3653;
		} else {
			limit = limitDays;
		}
		
		Calendar cal = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
		cal.add(Calendar.DATE, new Random().nextInt(limit) * -1);
		return cal;
	}
}