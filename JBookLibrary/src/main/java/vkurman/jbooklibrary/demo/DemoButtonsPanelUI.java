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

package vkurman.jbooklibrary.demo;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.activityregister.ActivityRegister;
import vkurman.jbooklibrary.core.Address;
import vkurman.jbooklibrary.core.AdminIDCards;
import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Borrower;
import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.core.Library;
import vkurman.jbooklibrary.db.DBArchitector;
import vkurman.jbooklibrary.db.ParserObjectToSQL;
import vkurman.jbooklibrary.db.ParserSQLToObject;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;

/**
 * Dialog that creates objects in database for demonstration purposes.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class DemoButtonsPanelUI extends JDialog implements ActionListener {

	private static final long serialVersionUID = -7371020060578461652L;
	private JPanel contentPane;
	
	private JButton createButton;
	private JButton generateBooksButton;
	private JButton generateBorrowersButton;
	private JButton generateLibrariansButton;
	private JButton generateAddressesButton;
	private JButton generateIDCardsButton;
	private JButton clearDatabaseButton;
	
	private int buttons = 7;
	private int maxBooks = 500;
	private int maxBorrowers = 100;
	private int maxLibrarians = 100;
	
	/**
	 * Constructor.
	 * 
	 * @param parent
	 */
	public DemoButtonsPanelUI() {
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 * 
	 * @param parent
	 */
	private void showUI(){
		setResizable(false);
		setTitle("Demo");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout(10, 10));
		contentPane.add(getButtonPane(), BorderLayout.CENTER);
		contentPane.add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		setContentPane(contentPane);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with buttons to manipulate with
	 * database records for demonstration purposes.
	 * 
	 * @return JPanel
	 */
	private JPanel getButtonPane() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(buttons, 1, 10, 10));
		
		createButton = new JButton("Create Tables");
		createButton.setActionCommand("tables");
		createButton.addActionListener(this);
		
		generateBooksButton = new JButton("Generate "+maxBooks+" Books");
		generateBooksButton.setActionCommand("books");
		generateBooksButton.addActionListener(this);
		
		generateBorrowersButton = new JButton("Generate "+maxBorrowers+" Borrowers");
		generateBorrowersButton.setActionCommand("borrowers");
		generateBorrowersButton.addActionListener(this);
		
		generateLibrariansButton = new JButton("Generate "+maxLibrarians+" Librarians");
		generateLibrariansButton.setActionCommand("librarians");
		generateLibrariansButton.addActionListener(this);
		
		generateAddressesButton = new JButton("Generate Addresses");
		generateAddressesButton.setActionCommand("addresses");
		generateAddressesButton.addActionListener(this);
		
		generateIDCardsButton = new JButton("Generate IDCards");
		generateIDCardsButton.setActionCommand("idcards");
		generateIDCardsButton.addActionListener(this);
		
		clearDatabaseButton = new JButton("Clear Database");
		clearDatabaseButton.setActionCommand("clear");
		clearDatabaseButton.addActionListener(this);
		
		buttonPane.add(createButton);
		buttonPane.add(generateBooksButton);
		buttonPane.add(generateBorrowersButton);
		buttonPane.add(generateLibrariansButton);
		buttonPane.add(generateAddressesButton);
		buttonPane.add(generateIDCardsButton);
		buttonPane.add(clearDatabaseButton);
		
		return buttonPane;
	}
	
	/**
	 * Creates required tables in database.
	 */
	private void createTables(){
		DBArchitector.getInstance().createTables();
	}
	
	/**
	 * Generates books
	 */
	private void generateBooks(){
		new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				int counter = 0;
				Iterator<Book> iter = ObjectsGenerator.generateBooks(maxBooks).iterator();
				while(iter.hasNext()){
					if(ParserObjectToSQL.getInstance().newBook(iter.next())){
						counter++;
						ActivityRegister.newActivity(
								DemoButtonsPanelUI.this,
								"Book "+counter+" saved!!!");
					}
				}
				return null;
			}
		}.execute();
	}
	
	/**
	 * Generates borrowers
	 */
	private void generateBorrowers(){
		new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				int counter = 0;
				Iterator<Borrower> iter = ObjectsGenerator.generateBorrowers(maxBorrowers).iterator();
				while(iter.hasNext()){
					if(ParserObjectToSQL.getInstance().newBorrower(iter.next())){
						counter++;
						ActivityRegister.newActivity(
								DemoButtonsPanelUI.this,
								"Borrower "+counter+" saved!!!");
					}
				}
				return null;
			}
		}.execute();
	}
	
	/**
	 * Generates librarians.
	 */
	private void generateLibrarians(){
		new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				int counter = 0;
				Iterator<Librarian> iter = ObjectsGenerator.generateLibrarians(maxLibrarians).iterator();
				while(iter.hasNext()){
					if(ParserObjectToSQL.getInstance().newLibrarian(iter.next())){
						counter++;
						ActivityRegister.newActivity(
								DemoButtonsPanelUI.this,
								"Librarian "+counter+" saved!!!");
					}
				}
				return null;
			}
		}.execute();
	}
	
	/**
	 * Generates new address for every user.
	 */
	private void generateAddresses(){
		new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				// Generating Addresses for Borrowers
				ActivityRegister.newActivity(
						DemoButtonsPanelUI.this,
						"Generating Addresses for Borrowers");
				List<Borrower> b = ParserSQLToObject.getInstance().getBorrowers(GeneralStatus.ACTIVE);
				Iterator<Borrower> borrowers = b.iterator();
				
				int size = b.size();
				
				Iterator<Address> addresses = ObjectsGenerator.generateAddresses(size).iterator();
				
				while(borrowers.hasNext()){
					AdminUsers.getInstance().newAddress(borrowers.next(), addresses.next());
				}
				ActivityRegister.newActivity(
						DemoButtonsPanelUI.this,
						"Addresses for Borrowers generated!");
				
				ActivityRegister.newActivity(
						DemoButtonsPanelUI.this,
						"Generating Addresses for Librarians");
				// Generating Addresses for Librarians
				List<Librarian> l = ParserSQLToObject.getInstance().getLibrarians(GeneralStatus.ACTIVE);
				Iterator<Librarian> librarians = l.iterator();
				
				int sizeL = l.size();
				
				Iterator<Address> addressesL = ObjectsGenerator.generateAddresses(sizeL).iterator();
				
				while(librarians.hasNext()){
					AdminUsers.getInstance().newAddress(librarians.next(), addressesL.next());
				}
				ActivityRegister.newActivity(
						DemoButtonsPanelUI.this,
						"Addresses for Librarians generated!");
				
				return null;
			}
		}.execute();
	}
	
	/**
	 * Generates IDCards for all users.
	 */
	private void generateIDCards(){
		new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				
				ActivityRegister.newActivity(
						DemoButtonsPanelUI.this,
						"Generating IDCards for Borrowers");
				// Generating Addresses for Borrowers
				List<Borrower> b = ParserSQLToObject.getInstance().getBorrowers(GeneralStatus.ACTIVE);
				Iterator<Borrower> borrowers = b.iterator();
				
				while(borrowers.hasNext()){
					Borrower borrower = borrowers.next();
					if (!borrower.hasIDCard()) {
						AdminIDCards.getInstance().activateIDCard(
								borrower.getUserID());
					}
				}
				ActivityRegister.newActivity(
						DemoButtonsPanelUI.this,
						"IDCards for Borrowers generated!");
				
				ActivityRegister.newActivity(
						DemoButtonsPanelUI.this,
						"Generating IDCards for Librarians");
				// Generating Addresses for Librarians
				List<Librarian> l = ParserSQLToObject.getInstance().getLibrarians(GeneralStatus.ACTIVE);
				Iterator<Librarian> librarians = l.iterator();
				
				while(librarians.hasNext()){
					Librarian librarian = librarians.next();
					if (!librarian.hasIDCard()) {
						AdminIDCards.getInstance().activateIDCard(
								librarian.getUserID());
					}
				}
				ActivityRegister.newActivity(
						DemoButtonsPanelUI.this,
						"IDCards for Librarians generated!");
				
				return null;
			}
		}.execute();
	}
	
	/**
	 * Removes all records from database.
	 */
	private void clearDatabase(){
		if (JOptionPane.showConfirmDialog(
				DemoButtonsPanelUI.this,
				"Are you sure you want to delete all records from database?",
				"Clear all database records",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					Connection conn = Library.getInstance()
							.getConnection();
					
					// Disabling referential integrity
					conn.createStatement().execute("SET REFERENTIAL_INTEGRITY FALSE");
					
					// Getting list of all table names
					Statement stmt = conn.createStatement(
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					ResultSet rs = stmt.executeQuery("SHOW TABLES");

					while (rs.next()) {
						String table = rs.getString("TABLE_NAME");
						
						ActivityRegister.newActivity(
								DemoButtonsPanelUI.this,
								"Clearing data from " + table
										+ " table!");
						// Removing all records from specified table
						conn.createStatement().execute("TRUNCATE TABLE " + table);

						ActivityRegister.newActivity(
								DemoButtonsPanelUI.this,
								"All records from table " + table
										+ " deleted!");
					}
					
					// Enabling referential integrity
					conn.createStatement().execute("SET REFERENTIAL_INTEGRITY TRUE");

					return null;
				}
			}.execute();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("tables")){
			createTables();
		} else if(e.getActionCommand().equals("books")){
			generateBooks();
		} else if(e.getActionCommand().equals("borrowers")){
			generateBorrowers();
		} else if(e.getActionCommand().equals("librarians")){
			generateLibrarians();
		} else if(e.getActionCommand().equals("addresses")){
			generateAddresses();
		} else if(e.getActionCommand().equals("idcards")){
			generateIDCards();
		} else if(e.getActionCommand().equals("clear")){
			clearDatabase();
		}
	}
}