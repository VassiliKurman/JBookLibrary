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

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.h2.jdbcx.JdbcConnectionPool;

import vkurman.jbooklibrary.activityregister.ActivityRegister;
import vkurman.jbooklibrary.activityregister.RegisterObserverPanel;
import vkurman.jbooklibrary.gui.DeskUI;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.gui.WelcomeUI;

/**
 * This class is the entry point to the application and has main() method.
 * This class also opens connection to database and keeps it open while
 * application is running. Connection stays open due to connecting to
 * database is slow and it is not acceptable within the application to slow
 * down processes.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Library {
	private static String path = AdminPrefs.DATABASE_PATH;
	private static String user = AdminPrefs.DATABASE_USER;
	private static String password = AdminPrefs.DATABASE_PASSWORD;
	private static JdbcConnectionPool cp;
	private static Connection conn;
	private static ServerSocket ss;
	
	private AdminPrefs adminPrefs;
	
	private volatile static Library library;
	private DeskUI desk;
	
	/**
	 * Default private Constructor that opens connection to
	 * the database.
	 */
	private Library() {
		// Initialising and displaying DeskUI
		displayDesk();
		// Loading preferences
		adminPrefs = AdminPrefs.getInstance();
		adminPrefs.setFields();
		
		// Displaying "Welcome" dialog if application is not initialised
		if(!AdminPrefs.INITIALISED){
			// If EventQueue is used to display dialog bellow,
			// than startUpUI is not displayed properly
			try{
				WelcomeUI welcome = new WelcomeUI();
				if(welcome.isInitialised()){
					AdminPrefs.setInitialised(true);
					
					// Saving properties
					ActivityRegister.newActivity(this, "Saving settings...");
					AdminPrefs.saveProperties(AdminPrefs.PROPERTIES, AdminPrefs.getConfigFile());
					ActivityRegister.newActivity(this, "... settings saved!");
					
					// Connecting to database
					connectToDB();
				} else {
					// Ending program
					System.exit(0);
				}
			} catch(Exception e){
				UIDisplayManager.displayErrorMessage(
						null,
						"Cannot display 'Welcome' dialog.");
			}
		} else {
			connectToDB();
		}
	}
	
	/**
	 * Requests to open connection to database and requests to display library UI.
	 */
	private void connectToDB(){
		// Opening DB
		if(open()){
			if(AdminPrefs.DISPLAY_ACTIVITY_REGISTER){
				// Displaying UI with library system activities
				RegisterObserverPanel observerPanel = new RegisterObserverPanel();
				ActivityRegister.registerObserver(observerPanel);
				// Displaying activity dialog
				JDialog dialog = new JDialog();
				dialog.add(observerPanel);
				dialog.pack();
				dialog.setVisible(true);
			}
			desk.ready();
		} else {
			UIDisplayManager.displayErrorMessage(
					null,
					"Cannot open connection to database!");
		}
	}
	
	/**
	 * This method returns instance of Library class. It can be
	 * either existing one or newly created in case if application
	 * has just been started.
	 * 
	 * @return Library
	 */
	public static Library getInstance() {
		// Double-checked locking with synchronisation
		if(library == null) {
			synchronized (Library.class) {
				if(library == null) {
					library = new Library();
				}
			}
		}
		// Under either circumstance this returns the instance
		return library;
	}
	
	/**
	 * This method opens connection to database.
	 * 
	 * @return boolean
	 */
	private boolean open() {
		ActivityRegister.newActivity(this, "Opening database connection...");
		try {
			Class.forName("org.h2.Driver");
			
			try {
				cp = JdbcConnectionPool.
					    create("jdbc:h2:~/" + path, user, password);
				conn = cp.getConnection();
				
				ActivityRegister.newActivity(
						this,
						"... database connection opened!");
				return true;
			} catch (SQLException e) {
				ActivityRegister.newActivity(
						this,
						".. cannot open connection to database!");
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			ActivityRegister.newActivity(this, "Cannot find database!!!");
			return false;
		}
	}
	
	/**
	 * This method closes connection to database.
	 */
	private void close(){
		ActivityRegister.newActivity(this, "Closing database connection...");
		try {
			if(conn != null) {
				conn.close();
				ActivityRegister.newActivity(this, "... connection closed!");
			}
			if(cp != null) {
				cp.dispose();
				ActivityRegister.newActivity(this, "... connection pool disposed!");
			}
		} catch (SQLException e) {
			ActivityRegister.newActivity(this, "... cannot close connection!");
		}
	}
	
	/**
	 * This method displays Library UI
	 */
	private void displayDesk() {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// Displaying UI
					desk = new DeskUI();
					ActivityRegister.registerObserver(desk);
				}
			});
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Error occured while trying to display \"Desk UI\"!");
		}
	}
	
	/**
	 * Getter for connection to database
	 * 
	 * @return Connection
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * This method exits the application. Prior the exit
	 * connection to database is closed.
	 */
	public void exit(){
		// Closing connection to database
		this.close();
		// Closing server socket
		try {
			ss.close();
		} catch (IOException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Error occur while closing socket.");
		}
		// Exiting application
		System.exit(0);
	}
	
	/**
	 * Applications entry point.
	 * ServerSocket object is created to make sure that only
	 * one instance of application is running.
	 */
	public static void main(String[] args) {
		try {
			// Creating socket object
			ss = new ServerSocket(3030);
			// Starting Library application
			Library.getInstance();
		} catch (IOException e) {
			UIDisplayManager.displayErrorMessage(
					null,
					AdminPrefs.LIBRARY_NAME + " is running!");
		}
	}
}