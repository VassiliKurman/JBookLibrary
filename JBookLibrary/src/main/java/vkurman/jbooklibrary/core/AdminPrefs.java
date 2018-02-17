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

import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import vkurman.jbooklibrary.activityregister.ActivityRegister;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.utils.Utils;

/**
 * The purpose of this class is to load and save application's
 * preferences, properties and settings.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AdminPrefs {
	/**
	 * Library name.
	 */
	public static String LIBRARY_NAME = "Library";
	
	/**
	 * Path to application's configuration file.
	 */
	public static String CONFIGURATION_PATH = "library.conf";
	
	/**
	 * Path to holidays file.
	 */
	private static String HOLIDAYS_FILE_PATH = "holidays.xml";
	
	/**
	 * Indicator to confirm that application is initialised or not.
	 */
	public static boolean INITIALISED = false;
	/**
	 * Indication for displaying <code>activity register</code> or not.
	 */
	public static boolean DISPLAY_ACTIVITY_REGISTER = false;
	/**
	 * Application name given by the <code>author</code>.
	 */
	public static final String APPLICATION_NAME = "eLibrary Management System Project";
	/**
	 * Application version.
	 */
	public static float VERSION = 0.1f;
	/**
	 * Application author.
	 */
	public static final String AUTHOR = "Vassili Kurman";
	/**
	 * Application development year.
	 */
	public static final String YEAR = "2013-2017";
	/**
	 * Delay time between messages.
	 */
	public static long MESSAGE_DELAY_TIME = 500;
	
	// Database settings
	/**
	 * Path to database.
	 */
	public static String DATABASE_PATH = "db";
	/**
	 * User name to access database.
	 */
	public static String DATABASE_USER = "librarian";
	/**
	 * Password to access database.
	 */
	public static String DATABASE_PASSWORD = "librarian";
	
	// Date format settings
	/**
	 * Format to display year.
	 */
	public static String DATE_FORMAT_STRING_YEAR = "yyyy";
	/**
	 * Format to display date.
	 */
	public static String DATE_FORMAT_STRING = "dd/MM/yyyy";
	/**
	 * Format to display date and time.
	 */
	public static String DATE_FORMAT_STRING_EXTENDED = "dd/MM/yyyy HH:mm:ss";
	
	/**
	 * IDCard maximum number
	 */
	public static int MAX_IDCARD_NUMBER = 99999;
	/**
	 * Maximum items that allowed to borrow.
	 */
	public static int MAX_ITEMS_TO_BORROW = 10;
	/**
	 * Maximum items that allowed to borrow.
	 */
	public static int MAX_ITEMS_TO_BORROW_SPECIAL = 20;
	/**
	 * IDCArd expire period in years
	 */
	public static int IDCARD_EXPIRE_PERIOD = 10;
	/**
	 * Borrower IDCard expire period in years
	 */
	public static int IDCARD_BORROWER_EXPIRE_PERIOD = 20;
	/**
	 * Librarian IDCard expire period in years
	 */
	public static int IDCARD_LIBRARIAN_EXPIRE_PERIOD = 20;
	/**
	 * Special short loan period.
	 */
	public static int SHORT_LOAN_PERIOD = 3;
	/**
	 * Standard loan period.
	 */
	public static int STANDARD_LOAN_PERIOD = 7;
	/**
	 * Special long loan period.
	 */
	public static int EXTENDED_LOAN_PERIOD = 14;
	/**
	 * Standard reservation period for items.
	 */
	public static int STANDARD_RESERVATION_PERIOD = 3;
	/**
	 * Maximum book id number.
	 */
	public static int BOOK_MAX_ID_NUMBER = 9999;
	/**
	 * Book id length.
	 */
	public static int BOOK_ID_LENGTH = BOOK_MAX_ID_NUMBER < 10 ? 1 :
		BOOK_MAX_ID_NUMBER < 100 ? 2 :
			BOOK_MAX_ID_NUMBER < 1000 ? 3 :
				BOOK_MAX_ID_NUMBER < 10000 ? 4 :
					BOOK_MAX_ID_NUMBER < 100000 ? 5 :
						BOOK_MAX_ID_NUMBER < 1000000 ? 6 : 0;
	/**
	 * Maximum user id number.
	 */
	public static int USER_MAX_ID_NUMBER = 99999;
	/**
	 * User id length.
	 */
	public static int USER_ID_LENGTH = USER_MAX_ID_NUMBER < 10 ? 1 :
		USER_MAX_ID_NUMBER < 100 ? 2 :
			USER_MAX_ID_NUMBER < 1000 ? 3 :
				USER_MAX_ID_NUMBER < 10000 ? 4 :
					USER_MAX_ID_NUMBER < 100000 ? 5 :
						USER_MAX_ID_NUMBER < 1000000 ? 6 : 0;
	/**
	 * Minimum librarian id number.
	 */
	public static int LIBRARIAN_MIN_ID_NUMBER = 10001;
	/**
	 * Maximum librarian id number.
	 */
	public static int LIBRARIAN_MAX_ID_NUMBER = 29999;
	/**
	 * Minimum borrower id number.
	 */
	public static int BORROWER_MIN_ID_NUMBER = 30001;
	/**
	 * Maximum borrower id number.
	 */
	public static int BORROWER_MAX_ID_NUMBER = USER_MAX_ID_NUMBER;
	/**
	 * Dimension for table panel on various windows.
	 */
	public static Dimension DIMENSION_TABLE_PANEL = new Dimension(1200, 420);
	/**
	 * Default window location.
	 */
	public static Point DEFAULT_WINDOW_LOCATION = new Point(100, 100);
	/**
	 * Application properties.
	 */
	public static Properties PROPERTIES = null;
	/**
	 * Daily charge for overdue loans.
	 */
	public static BigDecimal DAILY_FINE = new BigDecimal("0.20");
	/**
	 * Shows if fine should be charged for overdue loans or not.
	 */
	public static boolean CHARGE_FOR_LOAN_OVERDUE = false;
	/**
	 * Shows on what days library is working, starting from Monday to Sunday
	 */
	public static boolean[] WORKING_DAYS = {true, true, true, true, true, false, false};
	
	public static List<Holiday> HOLIDAYS = new ArrayList<Holiday>();
	
	/**
	 * Character that separates data in the <code>String</code> object
	 * on various field values.
	 */
	private static final String STRING_SEPARATOR_CHARACTER = ":";
	
	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the <code>admin</code> variable correctly when it is being
	 * initialised to the <code>AdminPrefs</code> instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static AdminPrefs admin;
	
	/**
	 * Default constructor.
	 */
	private AdminPrefs() {
		ActivityRegister.newActivity(this, "Loading properties from file...");
		// Setting delay time to display message
		Utils.delayTime(MESSAGE_DELAY_TIME);
		PROPERTIES = loadProperties(getConfigFile());
		ActivityRegister.newActivity(this, "... properties loaded!");
		// Setting delay time to display message
		Utils.delayTime(MESSAGE_DELAY_TIME);
	}
	
	/**
	 * This method is checking if the instance of current class exists
	 * in the system and returns ever the instance that has been created
	 * earlier or the newly created instance.
	 * 
	 * @return AdminPrefs object
	 */
	public static AdminPrefs getInstance() {
		// Double-checked locking with synchronisation
		if(admin == null) {
			synchronized (AdminPrefs.class) {
				if(admin == null) {
					admin = new AdminPrefs();
				}
			}
		}
		// Under either circumstance this returns the instance
		return admin;
	}
	
	/**
	 * Assigns values to the class static fields from <code>Properties</code>
	 * object. If there is no property exists with the specific <code>key</code>
	 * than default value is used.
	 */
	public void setFields(){
		ActivityRegister.newActivity(this, "Updating class fields...");
		// Setting delay time to display message
		Utils.delayTime(MESSAGE_DELAY_TIME);
		
		if(PROPERTIES.getProperty("INITIALISED") != null) {
			INITIALISED = (PROPERTIES.getProperty("INITIALISED").equals("true")) ? true : false;
		}
		if(PROPERTIES.getProperty("MESSAGE_DELAY_TIME") != null) {
			MESSAGE_DELAY_TIME = Long.parseLong(PROPERTIES.getProperty("MESSAGE_DELAY_TIME"));
		}
		if(PROPERTIES.getProperty("LIBRARY_NAME") != null) {
			LIBRARY_NAME = PROPERTIES.getProperty("LIBRARY_NAME");
		}
		if(PROPERTIES.getProperty("DATABASE_PATH") != null) {
			DATABASE_PATH = PROPERTIES.getProperty("DATABASE_PATH");
		}
		if(PROPERTIES.getProperty("DATABASE_USER") != null) {
			DATABASE_USER = PROPERTIES.getProperty("DATABASE_USER");
		}
		if(PROPERTIES.getProperty("DATABASE_PASSWORD") != null) {
			DATABASE_PASSWORD = PROPERTIES.getProperty("DATABASE_PASSWORD");
		}
		if(PROPERTIES.getProperty("DATE_FORMAT_STRING_YEAR") != null) {
			DATE_FORMAT_STRING_YEAR = PROPERTIES.getProperty("DATE_FORMAT_STRING_YEAR");
		}
		if(PROPERTIES.getProperty("DATE_FORMAT_STRING") != null) {
			DATE_FORMAT_STRING = PROPERTIES.getProperty("DATE_FORMAT_STRING");
		}
		if(PROPERTIES.getProperty("DATE_FORMAT_STRING_EXTENDED") != null) {
			DATE_FORMAT_STRING_EXTENDED = PROPERTIES.getProperty("DATE_FORMAT_STRING_EXTENDED");
		}
		if(PROPERTIES.getProperty("MAX_IDCARD_NUMBER") != null) {
			MAX_IDCARD_NUMBER = Integer.parseInt(PROPERTIES.getProperty("MAX_IDCARD_NUMBER"));
		}
		if(PROPERTIES.getProperty("MAX_ITEMS_TO_BORROW") != null) {
			MAX_ITEMS_TO_BORROW = Integer.parseInt(PROPERTIES.getProperty("MAX_ITEMS_TO_BORROW"));
		}
		if(PROPERTIES.getProperty("MAX_ITEMS_TO_BORROW_SPECIAL") != null) {
			MAX_ITEMS_TO_BORROW_SPECIAL = Integer.parseInt(PROPERTIES.getProperty("MAX_ITEMS_TO_BORROW_SPECIAL"));
		}
		if(PROPERTIES.getProperty("IDCARD_EXPIRE_PERIOD") != null) {
			IDCARD_EXPIRE_PERIOD = Integer.parseInt(PROPERTIES.getProperty("IDCARD_EXPIRE_PERIOD"));
		}
		if(PROPERTIES.getProperty("IDCARD_BORROWER_EXPIRE_PERIOD") != null) {
			IDCARD_BORROWER_EXPIRE_PERIOD = Integer.parseInt(PROPERTIES.getProperty("IDCARD_BORROWER_EXPIRE_PERIOD"));
		}
		if(PROPERTIES.getProperty("IDCARD_LIBRARIAN_EXPIRE_PERIOD") != null) {
			IDCARD_LIBRARIAN_EXPIRE_PERIOD = Integer.parseInt(PROPERTIES.getProperty("IDCARD_LIBRARIAN_EXPIRE_PERIOD"));
		}
		if(PROPERTIES.getProperty("SHORT_LOAN_PERIOD") != null) {
			SHORT_LOAN_PERIOD = Integer.parseInt(PROPERTIES.getProperty("SHORT_LOAN_PERIOD"));
		}
		if(PROPERTIES.getProperty("STANDARD_LOAN_PERIOD") != null) {
			STANDARD_LOAN_PERIOD = Integer.parseInt(PROPERTIES.getProperty("STANDARD_LOAN_PERIOD"));
		}
		if(PROPERTIES.getProperty("EXTENDED_LOAN_PERIOD") != null) {
			EXTENDED_LOAN_PERIOD = Integer.parseInt(PROPERTIES.getProperty("EXTENDED_LOAN_PERIOD"));
		}
		if(PROPERTIES.getProperty("STANDARD_RESERVATION_PERIOD") != null) {
			STANDARD_RESERVATION_PERIOD = Integer.parseInt(PROPERTIES.getProperty("STANDARD_RESERVATION_PERIOD"));
		}
		if(PROPERTIES.getProperty("BOOK_ID_LENGTH") != null) {
			BOOK_ID_LENGTH = Integer.parseInt(PROPERTIES.getProperty("BOOK_ID_LENGTH"));
		}
		if(PROPERTIES.getProperty("BOOK_MAX_ID_NUMBER") != null) {
			BOOK_MAX_ID_NUMBER = Integer.parseInt(PROPERTIES.getProperty("BOOK_MAX_ID_NUMBER"));
		}
		if(PROPERTIES.getProperty("USER_ID_LENGTH") != null) {
			USER_ID_LENGTH = Integer.parseInt(PROPERTIES.getProperty("USER_ID_LENGTH"));
		}
		if(PROPERTIES.getProperty("USER_MAX_ID_NUMBER") != null) {
			USER_MAX_ID_NUMBER = Integer.parseInt(PROPERTIES.getProperty("USER_MAX_ID_NUMBER"));
		}
		if(PROPERTIES.getProperty("LIBRARIAN_MIN_ID_NUMBER") != null) {
			LIBRARIAN_MIN_ID_NUMBER = Integer.parseInt(PROPERTIES.getProperty("LIBRARIAN_MIN_ID_NUMBER"));
		}
		if(PROPERTIES.getProperty("LIBRARIAN_MAX_ID_NUMBER") != null) {
			LIBRARIAN_MAX_ID_NUMBER = Integer.parseInt(PROPERTIES.getProperty("LIBRARIAN_MAX_ID_NUMBER"));
		}
		if(PROPERTIES.getProperty("BORROWER_MIN_ID_NUMBER") != null) {
			BORROWER_MIN_ID_NUMBER = Integer.parseInt(PROPERTIES.getProperty("BORROWER_MIN_ID_NUMBER"));
		}
		if(PROPERTIES.getProperty("BORROWER_MAX_ID_NUMBER") != null) {
			BORROWER_MAX_ID_NUMBER = Integer.parseInt(PROPERTIES.getProperty("BORROWER_MAX_ID_NUMBER"));
		}
		if(PROPERTIES.getProperty("DIMENSION_TABLE_PANEL") != null) {
			String[] lengths = PROPERTIES.getProperty("DIMENSION_TABLE_PANEL")
					.split(STRING_SEPARATOR_CHARACTER);
			DIMENSION_TABLE_PANEL = new Dimension(
					Integer.parseInt(lengths[0]),
					Integer.parseInt(lengths[1]));
		}
		if(PROPERTIES.getProperty("DEFAULT_WINDOW_LOCATION") != null) {
			String[] points = PROPERTIES.getProperty("DEFAULT_WINDOW_LOCATION")
					.split(STRING_SEPARATOR_CHARACTER);
			DEFAULT_WINDOW_LOCATION = new Point(
					Integer.parseInt(points[0]),
					Integer.parseInt(points[1]));
		}
		if(PROPERTIES.getProperty("DAILY_FINE") != null) {
			DAILY_FINE = new BigDecimal(PROPERTIES.getProperty("DAILY_FINE"));
		}
		if(PROPERTIES.getProperty("CHARGE_FOR_LOAN_OVERDUE") != null){
			CHARGE_FOR_LOAN_OVERDUE = (PROPERTIES.getProperty("CHARGE_FOR_LOAN_OVERDUE").equals("true")) ? true : false;
		}
		if(PROPERTIES.getProperty("WORKING_DAYS") != null){
			String[] str = PROPERTIES.getProperty("WORKING_DAYS")
					.split(STRING_SEPARATOR_CHARACTER);
			
			for(int i = 0; i < WORKING_DAYS.length; i++){
				WORKING_DAYS[i] = (str[i].equals("true")) ? true : false;
			}
		}
		
		HOLIDAYS = readHolidays();
		
		ActivityRegister.newActivity(this, "... class fields updated!!!");
		// Setting delay time to display message
		Utils.delayTime(MESSAGE_DELAY_TIME);
	}
	
	/**
	 * Sets value for <code>INITIALISED</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param initialised
	 */
	public static void setInitialised(boolean initialised){
		INITIALISED = initialised;
		PROPERTIES.setProperty("INITIALISED", (initialised) ? "true" : "false");
	}
	
	/**
	 * Sets value for <code>LIBRARY_NAME</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param libraryName
	 */
	public static void setLibraryName(String libraryName){
		LIBRARY_NAME = libraryName;
		PROPERTIES.setProperty("LIBRARY_NAME", libraryName);
	}
	
	/**
	 * Sets value for <code>DAILY_FINE</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param dailyFine
	 */
	public static void setDailyFine(BigDecimal dailyFine){
		DAILY_FINE = dailyFine;
		PROPERTIES.setProperty("DAILY_FINE", dailyFine.toString());
	}
	
	/**
	 * Sets value for <code>BOOK_ID_LENGTH</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param length
	 */
	public static void setBookIDLength(int length){
		BOOK_ID_LENGTH = length;
		PROPERTIES.setProperty("BOOK_ID_LENGTH", Integer.toString(length));
	}
	
	/**
	 * Sets value for <code>USER_ID_LENGTH</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param length
	 */
	public static void setUserIDLength(int length){
		USER_ID_LENGTH = length;
		PROPERTIES.setProperty("USER_ID_LENGTH", Integer.toString(length));
	}
	
	/**
	 * Sets value for <code>CONFIGURATION_PATH</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param path
	 */
	public static void setConfigurationPath(String path) {
		CONFIGURATION_PATH = path;
		PROPERTIES.setProperty("CONFIGURATION_PATH", path);
	}
	
	/**
	 * Sets value for <code>DISPLAY_ACTIVITY_REGISTER</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param show
	 */
	public static void showActivityRegister(boolean show) {
		DISPLAY_ACTIVITY_REGISTER = show;
		PROPERTIES.setProperty("DISPLAY_ACTIVITY_REGISTER",
				(show) ? "true" : "false");
	}
	
	/**
	 * Sets value for <code>VERSION</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param version
	 */
	public static void setVersion(float version) {
		VERSION = version;
		PROPERTIES.setProperty("VERSION", Float.toString(version));
	}
	
	/**
	 * Sets value for <code>MESSAGE_DELAY_TIME</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param time
	 */
	public static void setMessageDelayTime(long time) {
		MESSAGE_DELAY_TIME = time;
		PROPERTIES.setProperty("MESSAGE_DELAY_TIME", Long.toString(time));
	}
	
	/**
	 * Sets value for <code>DATABASE_PATH</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param path
	 */
	public static void setDatabasePath(String path) {
		DATABASE_PATH = path;
		PROPERTIES.setProperty("DATABASE_PATH", path);
	}
	
	/**
	 * Sets value for <code>DATABASE_USER</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param user
	 */
	public static void setDatabaseUser(String user) {
		DATABASE_USER = user;
		PROPERTIES.setProperty("DATABASE_USER", user);
	}
	
	/**
	 * Sets value for <code>DATABASE_PASSWORD</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param password
	 */
	public static void setDatabasePassword(String password) {
		DATABASE_PASSWORD = password;
		PROPERTIES.setProperty("DATABASE_PASSWORD", password);
	}
	
	/**
	 * Sets value for <code>DATE_FORMAT_STRING_YEAR</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param format
	 */
	public static void setDateFormatStringYear(String format) {
		DATE_FORMAT_STRING_YEAR = format;
		PROPERTIES.setProperty("DATE_FORMAT_STRING_YEAR", format);
	}
	
	/**
	 * Sets value for <code>DATE_FORMAT_STRING</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param format
	 */
	public static void setDateFormatString(String format) {
		DATE_FORMAT_STRING = format;
		PROPERTIES.setProperty("DATE_FORMAT_STRING", format);
	}
	
	/**
	 * Sets value for <code>DATE_FORMAT_STRING_EXTENDED</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param format
	 */
	public static void setDateFormatStringExtended(String format) {
		DATE_FORMAT_STRING_EXTENDED = format;
		PROPERTIES.setProperty("DATE_FORMAT_STRING_EXTENDED", format);
	}
	
	/**
	 * Sets value for <code>MAX_IDCARD_NUMBER</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param max
	 */
	public static void setMaxIDCardNumber(int max) {
		MAX_IDCARD_NUMBER = max;
		PROPERTIES.setProperty("MAX_IDCARD_NUMBER", Integer.toString(max));
	}
	
	/**
	 * Sets value for <code>MAX_ITEMS_TO_BORROW</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param max
	 */
	public static void setMaximumItemsToBorrow(int max) {
		MAX_ITEMS_TO_BORROW = max;
		PROPERTIES.setProperty("MAX_ITEMS_TO_BORROW", Integer.toString(max));
	}
	
	/**
	 * Sets value for <code>MAX_ITEMS_TO_BORROW_SPECIAL</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param max
	 */
	public static void setMaximumItemsToBorrowSpecial(int max) {
		MAX_ITEMS_TO_BORROW_SPECIAL = max;
		PROPERTIES.setProperty("MAX_ITEMS_TO_BORROW_SPECIAL", Integer.toString(max));
	}
	
	/**
	 * Sets value for <code>IDCARD_EXPIRE_PERIOD</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param period
	 */
	public static void setIDCardExpirePeriod(int period) {
		IDCARD_EXPIRE_PERIOD = period;
		PROPERTIES.setProperty("IDCARD_EXPIRE_PERIOD", Integer.toString(period));
	}
	
	/**
	 * Sets value for <code>IDCARD_BORROWER_EXPIRE_PERIOD</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param period
	 */
	public static void setBorrowerIDCardExpirePeriod(int period) {
		IDCARD_BORROWER_EXPIRE_PERIOD = period;
		PROPERTIES.setProperty("IDCARD_BORROWER_EXPIRE_PERIOD", Integer.toString(period));
	}
	
	/**
	 * Sets value for <code>IDCARD_LIBRARIAN_EXPIRE_PERIOD</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param period
	 */
	public static void setLibrarianIDCardExpirePeriod(int period) {
		IDCARD_LIBRARIAN_EXPIRE_PERIOD = period;
		PROPERTIES.setProperty("IDCARD_LIBRARIAN_EXPIRE_PERIOD", Integer.toString(period));
	}
	
	/**
	 * Sets value for <code>SHORT_LOAN_PERIOD</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param period
	 */
	public static void setShortLoanPeriod(int period) {
		SHORT_LOAN_PERIOD = period;
		PROPERTIES.setProperty("SHORT_LOAN_PERIOD", Integer.toString(period));
	}
	
	/**
	 * Sets value for <code>STANDARD_LOAN_PERIOD</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param period
	 */
	public static void setStandardLoanPeriod(int period) {
		STANDARD_LOAN_PERIOD = period;
		PROPERTIES.setProperty("STANDARD_LOAN_PERIOD", Integer.toString(period));
	}
	
	/**
	 * Sets value for <code>EXTENDED_LOAN_PERIOD</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param period
	 */
	public static void setExtendedLoanPeriod(int period) {
		EXTENDED_LOAN_PERIOD = period;
		PROPERTIES.setProperty("EXTENDED_LOAN_PERIOD", Integer.toString(period));
	}
	
	/**
	 * Sets value for <code>STANDARD_RESERVATION_PERIOD</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param period
	 */
	public static void setStandardReservationPeriod(int period) {
		STANDARD_RESERVATION_PERIOD = period;
		PROPERTIES.setProperty("STANDARD_RESERVATION_PERIOD", Integer.toString(period));
	}
	
	/**
	 * Sets value for <code>BOOK_MAX_ID_NUMBER</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param maximum
	 */
	public static void setMaximumBookIDNumber(int maximum) {
		BOOK_MAX_ID_NUMBER = maximum;
		PROPERTIES.setProperty("BOOK_MAX_ID_NUMBER", Integer.toString(maximum));
	}
	
	/**
	 * Sets value for <code>USER_MAX_ID_NUMBER</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param maximum
	 */
	public static void setMaximumUserIDNumber(int maximum) {
		USER_MAX_ID_NUMBER = maximum;
		PROPERTIES.setProperty("USER_MAX_ID_NUMBER", Integer.toString(maximum));
	}
	
	/**
	 * Sets value for <code>LIBRARIAN_MIN_ID_NUMBER</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param minimum
	 */
	public static void setMinimumLibrarianIDNumber(int minimum) {
		LIBRARIAN_MIN_ID_NUMBER = minimum;
		PROPERTIES.setProperty("LIBRARIAN_MIN_ID_NUMBER", Integer.toString(minimum));
	}
	
	/**
	 * Sets value for <code>LIBRARIAN_MAX_ID_NUMBER</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param maximum
	 */
	public static void setMaximumLibrarianIDNumber(int maximum) {
		LIBRARIAN_MAX_ID_NUMBER = maximum;
		PROPERTIES.setProperty("LIBRARIAN_MAX_ID_NUMBER", Integer.toString(maximum));
	}
	
	/**
	 * Sets value for <code>BORROWER_MIN_ID_NUMBER</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param minimum
	 */
	public static void setMinimumBorrowerIDNumber(int minimum) {
		BORROWER_MIN_ID_NUMBER = minimum;
		PROPERTIES.setProperty("BORROWER_MIN_ID_NUMBER", Integer.toString(minimum));
	}
	
	/**
	 * Sets value for <code>BORROWER_MAX_ID_NUMBER</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param maximum
	 */
	public static void setMaximumBorrowerIDNumber(int maximum) {
		BORROWER_MAX_ID_NUMBER = maximum;
		PROPERTIES.setProperty("BORROWER_MAX_ID_NUMBER", Integer.toString(maximum));
	}
	
	/**
	 * Sets value for <code>DIMENSION_TABLE_PANEL</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param dimension
	 */
	public static void setDimensionForTablePanel(Dimension dimension) {
		DIMENSION_TABLE_PANEL = dimension;
		PROPERTIES.setProperty("DIMENSION_TABLE_PANEL",
				dimension.width +
				STRING_SEPARATOR_CHARACTER +
				dimension.height);
	}
	
	/**
	 * Sets value for <code>DEFAULT_WINDOW_LOCATION</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param location
	 */
	public static void setDefaultWindowLacation(Point location) {
		DEFAULT_WINDOW_LOCATION = location;
		PROPERTIES.setProperty("DEFAULT_WINDOW_LOCATION",
				location.x +
				STRING_SEPARATOR_CHARACTER +
				location.y);
	}
	
	/**
	 * Sets value for <code>CHARGE_FOR_LOAN_OVERDUE</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param charge
	 */
	public static void setChargeForLoanOverdue(boolean charge) {
		CHARGE_FOR_LOAN_OVERDUE = charge;
		PROPERTIES.setProperty("CHARGE_FOR_LOAN_OVERDUE",
				(charge) ? "true" : "false");
	}
	
	/**
	 * Sets value for <code>WORKING_DAYS</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * 
	 * @param days
	 */
	public static void setWorkingDays(boolean[] days) {
		WORKING_DAYS = days;
		StringBuffer text = new StringBuffer();
		for(int i = 0; i < days.length; i++){
			text.append(days[i] ? "true" : "false");
			if(i < days.length - 1){
				text.append(STRING_SEPARATOR_CHARACTER);
			}
		}
		PROPERTIES.setProperty("WORKING_DAYS", text.toString());
	}
	
	/**
	 * Saves <code>HOLIDAYS</code> data into storage location.
	 * 
	 * @param holidays
	 */
	public static void setHolidays() {
		writeHolidays();
	}
	
	/**
	 * Sets value for <code>HOLIDAYS</code> field and it's
	 * corresponding key in the <code>Property</code> object.
	 * Saves <code>HOLIDAYS</code> data into storage location.
	 * 
	 * @param holidays
	 */
	public static void setHolidays(List<Holiday> holidays) {
		HOLIDAYS = holidays;
		writeHolidays();
	}
	
	/**
	 * Adds specified holiday to the list of holidays.
	 * 
	 * @param date
	 * @param repeatable
	 * @return boolean
	 */
	public static boolean addHoliday(Calendar date, boolean repeatable){
		if(date == null) return false;
		
		boolean contains = false;
		for(Holiday holiday : HOLIDAYS){
			if(holiday.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
					holiday.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)){
				contains = true;
			}
			
		}
		
		return (contains) ?
				false :
					HOLIDAYS.add(new Holiday(date, repeatable));
	}
	
	/**
	 * Removes holiday that has specified date. If specified date is
	 * <code>null</code> than false is returned. If holiday with specified
	 * date is not found in the list, than <code>true</code> is returned.
	 * 
	 * @param date
	 * @return boolean
	 */
	public static boolean removeHoliday(Calendar date){
		if(date == null) return false;
		
		for(Holiday holiday : HOLIDAYS){
			if(holiday.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
					holiday.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)){
				return HOLIDAYS.remove(holiday);
			}
			
		}
		return true;
	}
	
	/**
	 * Reads data about holidays from <code>XML</code> file and returns new
	 * list of holidays. Am empty list is returned in case if error occur while
	 * reading data.
	 * 
	 * @return List<Holiday>
	 */
	private List<Holiday> readHolidays(){
		List<Holiday> holidays = new ArrayList<Holiday>();
		
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLStreamReader streamReader = inputFactory.createXMLStreamReader(
					new BufferedInputStream(
							new FileInputStream(HOLIDAYS_FILE_PATH)));
			
			XMLEventReader eventReader = inputFactory.createXMLEventReader(streamReader);
			
			Holiday holiday = null;
			
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().toString().equals("holiday"))
						holiday = new Holiday();
					if (startElement.getName().toString().equals("timestamp")) {
						Calendar calendar = (Calendar) Calendar.getInstance().clone();
						calendar.setTimeInMillis(Long.parseLong(
								eventReader.getElementText().toString()));
						holiday.setDate(calendar);
					}
					if (startElement.getName().toString().equals("is_repeatable")){
						holiday.setRepeatable(
								eventReader.getElementText().toString().equals("true") ?
										true : false);
					}
				}
				
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().toString().equals("holiday")) {
						holidays.add(holiday);
                    }
                }
			}
		} catch (FileNotFoundException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"File not found while trying to read HOLIDAYS from file." +
					"\nNew empty file is created!");
			// Creating new file
			try {
				File file = new File(HOLIDAYS_FILE_PATH);
				file.createNewFile();
			} catch (IOException e1) {
				UIDisplayManager.displayErrorMessage(
						null,
						"Error occured while creating new file!");
			}
		} catch (XMLStreamException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Error occured while trying to read HOLIDAYS from file." +
					"\nPlease check that file contains correct data and has " +
					"correct structure!");
		} catch (FactoryConfigurationError e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Factory Configuration Error occured while trying to read HOLIDAYS from file!");
		}
		
		return holidays;
	}
	
	/**
	 * Writes <code>HOLIDAYS</code> data into <code>XML</code> file.
	 */
	private static void writeHolidays(){
		try {
			XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter(
					new BufferedOutputStream(
							new FileOutputStream(HOLIDAYS_FILE_PATH)));
			
			// TODO
			writer.writeDTD("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			writer.writeStartElement("holidays");
//			writer.writeCharacters("\u232b");
			for(Holiday holiday: HOLIDAYS){
				writer.writeStartElement("holiday");
				
//				writer.writeCharacters("\u232b");
				writer.writeStartElement("timestamp");
				writer.writeCharacters(Long.toString(holiday.getDate().getTimeInMillis()));
				writer.writeEndElement();
//				writer.writeCharacters("\u232b");
				writer.writeStartElement("is_repeatable");
				writer.writeCharacters(holiday.isRepeatable() ? "true" : "false");
				writer.writeEndElement();
//				writer.writeCharacters("\u232b");
				writer.writeEndElement();
//				writer.writeCharacters("\u232b");
			}
			writer.writeEndElement();
			
			writer.close();
		} catch (FileNotFoundException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"File no found Exception occured while trying to write HOLIDAYS into file!");
		} catch (XMLStreamException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"XML Stream Exception occured while trying to write HOLIDAYS into file!");
		} catch (FactoryConfigurationError e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Factory Configuration Error occured while trying to write HOLIDAYS into file!");
		}
	}
	
	/**
	 * Sets properties for application.
	 * 
	 * @param properties
	 */
	public static void setProperties(Properties properties) {
		PROPERTIES = properties;
	}
	
	/**
	 * Returns configuration file.
	 * 
	 * @return File
	 */
	public static File getConfigFile() {
		return new File(CONFIGURATION_PATH);
	}
	
	/**
	 * Loads properties from specified file. If file does not exists,
	 * than new empty file will be created automatically.
	 * 
	 * @param file
	 * @return Properties
	 */
	public Properties loadProperties(File file) {
		Properties prop = new Properties();
		try {
			FileInputStream in = new FileInputStream(file);
			prop.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"File not found! New configuration file will be created!");
			
			try {
				// Creating new file
				file.createNewFile();
			} catch (IOException e1) {
				UIDisplayManager.displayErrorMessage(
						null,
						"Error occured while creating new file!");
			}
		} catch (IOException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Error occured while loading properties!");
		}
		return prop;
	}
	
	/**
	 * Saves properties into specified file. If properties not
	 * saved to the file, than they will be discarded when application
	 * closes.
	 * 
	 * @param file
	 * @return boolean
	 */
	public static boolean saveProperties(File file) {
		
		try {
			FileOutputStream out = new FileOutputStream(file);
			PROPERTIES.store(out, "\"" + LIBRARY_NAME + "\" configuration file");
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"File not found!");
			
			return false;
		} catch (IOException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Error occured!");
			
			return false;
		}
	}
	
	/**
	 * Saves properties into specified file.
	 * 
	 * @param properties
	 * @param file
	 * @return boolean
	 */
	public static boolean saveProperties(Properties properties, File file) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			properties.store(out, "Library configuration file");
			out.close();
			
			return true;
		} catch (FileNotFoundException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"File not found!");
			
			return false;
		} catch (IOException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Error occured!");
			
			return false;
		}
	}
	
	/**
	 * Sets specified property in <code>PROPERTIES</code> field.
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value){
		PROPERTIES.setProperty(key, value);
	}
	
	/**
	 * Checks if library is opened in specified date or not.
	 * 
	 * @param date
	 * @return boolean
	 */
	public static boolean isLibraryOpen(Calendar date){
		if(isHoliday(date)) return false;
		
		boolean open = false;
		switch(date.get(Calendar.DAY_OF_WEEK)){
			case Calendar.MONDAY:
				open = AdminPrefs.WORKING_DAYS[0];
				break;
			case Calendar.TUESDAY:
				open = AdminPrefs.WORKING_DAYS[1];
				break;
			case Calendar.WEDNESDAY:
				open = AdminPrefs.WORKING_DAYS[2];
				break;
			case Calendar.THURSDAY:
				open = AdminPrefs.WORKING_DAYS[3];
				break;
			case Calendar.FRIDAY:
				open = AdminPrefs.WORKING_DAYS[4];
				break;
			case Calendar.SATURDAY:
				open = AdminPrefs.WORKING_DAYS[5];
				break;
			case Calendar.SUNDAY:
				open = AdminPrefs.WORKING_DAYS[6];
				break;
		}
		return open;
	}
	
	/**
	 * Checks if specified day is marked as holiday.
	 * 
	 * @param day
	 * @return boolean
	 */
	public static boolean isHoliday(Calendar day){
		if(day == null) return false;
		
		for(Holiday holiday : HOLIDAYS){
			Calendar c = holiday.getDate();
			if(c.get(Calendar.YEAR) == day.get(Calendar.YEAR) &&
					c.get(Calendar.DAY_OF_YEAR) == day.get(Calendar.DAY_OF_YEAR)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sets <code>LookAndFeel</code> for application.
	 */
	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	    } catch (UnsupportedLookAndFeelException e) {
	    	UIDisplayManager.displayErrorMessage(
	    			null,
	    			"Specified 'Look & Feel' is not supported by OS!");
	    } catch (ClassNotFoundException e) {
	    	UIDisplayManager.displayErrorMessage(
	    			null,
	    			"Class wasn't found while trying to set 'Look & Feel'!");
	    } catch (InstantiationException e) {
	    	UIDisplayManager.displayErrorMessage(
	    			null,
	    			"Instantiation error thrown while trying to set 'Look & Feel'!");
	    } catch (IllegalAccessException e) {
	    	UIDisplayManager.displayErrorMessage(
	    			null,
	    			"Illegal Access error thrown while trying to set 'Look & Feel'!");
	    }
	}
}