package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import vkurman.jbooklibrary.activityregister.ActivityRegister;
import vkurman.jbooklibrary.activityregister.RegisterObserver;
import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Borrower;
import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.core.Library;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.db.ParserObjectToSQL;
import vkurman.jbooklibrary.enums.Action;

/**
 * Main Graphical User Interface for application.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class DeskUI extends JFrame implements ActionListener, RegisterObserver {

	private static final long serialVersionUID = 4454447707486291402L;
	public static final String TITLE = "Desk - " + AdminPrefs.LIBRARY_NAME;
	
	public static final Point DEFAULT_WINDOW_LOCATION = new Point(100, 100);
	
	private static boolean initialised = false;
	private DeskMenuBar menubar;
	private JButton registerButton;
	private JButton activateButton;
	private JButton reserveButton;
	private JButton borrowButton;
	private JButton renewalButton;
	private JButton returnButton;
	private JButton catalogueButton;
	private JTextField status;
	
	/**
	 * Constructor.
	 */
	public DeskUI() {
		// Setting Look&Feel for UI
		AdminPrefs.setLookAndFeel();
		
		menubar = new DeskMenuBar(this);
		menubar.setEnabled(initialised);
		
		status = new JTextField("Starting up library...");
		status.setEditable(false);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setResizable(false);
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				exitApplication();
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
		setMinimumSize(new Dimension(450, 300));
		setPreferredSize(new Dimension(450, 300));
		
		setJMenuBar(menubar);
		setContentPane(createContentPanel());
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns content panel.
	 * 
	 * @return JPanel
	 */
	private JPanel createContentPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel main = new JPanel(new BorderLayout());
		main.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		main.add(createDeskButtonsPanel(), BorderLayout.CENTER);
		main.add(createExitButtonPane(), BorderLayout.PAGE_END);
		
		panel.add(main, BorderLayout.CENTER);
		panel.add(status, BorderLayout.PAGE_END);
		
		return panel;
	}
	
	/**
	 * Creates and returns panel that holds buttons with main library
	 * actions.
	 * 
	 * @return JPanel
	 */
	private JPanel createDeskButtonsPanel() {
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		buttonPane.add(getUserPanel());
		buttonPane.add(getActionsPanel());
		buttonPane.add(getCataloguePanel());
		
		return buttonPane;
	}
	
	private JPanel getUserPanel(){
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		registerButton = new JButton("Register User");
		registerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				registerUser();
			}
		});
		
		activateButton = new JButton("Activate IDCard");
		activateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				activateIDCard();
			}
		});
		
		buttonPanel.add(registerButton);
		buttonPanel.add(activateButton);
		
		// TODO
		registerButton.setEnabled(initialised);
		activateButton.setEnabled(initialised);
		
		return buttonPanel;
	}
	
	private JPanel getActionsPanel(){
		JPanel buttonPane = new JPanel(new GridLayout(1, 4, 10, 10));
		
		reserveButton = new JButton("Reserve");
		reserveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reserve();
			}
		});
		
		borrowButton = new JButton("Borrow");
		borrowButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				borrow();
			}
		});
		
		renewalButton = new JButton("Renewal");
		renewalButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				renew();
			}
		});
		
		returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				returnBook();
			}
		});
		
		buttonPane.add(reserveButton);
		buttonPane.add(borrowButton);
		buttonPane.add(renewalButton);
		buttonPane.add(returnButton);
		
		// TODO
		reserveButton.setEnabled(initialised);
		borrowButton.setEnabled(initialised);
		renewalButton.setEnabled(initialised);
		returnButton.setEnabled(initialised);
		
		return buttonPane;
	}
	
	/**
	 * Creates and returns panel that holds button with catalogue search
	 * functionality.
	 * 
	 * @return JPanel
	 */
	private JPanel getCataloguePanel(){
		JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 10, 10));
		
		catalogueButton = new JButton("Catalogue Search");
		catalogueButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIDisplayManager.startBooksAdminUI();
			}
		});
		
		buttonPanel.add(catalogueButton);
		
		// TODO
		catalogueButton.setEnabled(initialised);
		
		return buttonPanel;
	}
	
	/**
	 * Displays UI that allows user to reserve books.
	 */
	private void reserve(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new BooksHandlerUI(Action.RESERVE);
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
	}
	
	/**
	 * Displays UI that allows user to borrow books.
	 */
	private void borrow(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new BooksHandlerUI(Action.BORROW);
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
	}
	
	/**
	 * Displays UI that allows user to renew loans.
	 */
	private void renew(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new BooksHandlerUI(Action.RENEW);
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
	}
	
	/**
	 * Displays UI that allows user to return books.
	 */
	private void returnBook(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new BooksHandlerUI(Action.RETURN);
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
	}
	
	/**
	 * Creates and adds to database new book.
	 * 
	 * @param title
	 */
	public static boolean addNewBook(String title){
		if(title == null) return false;
		
		boolean success = false;
		
		// Creating new book
		final Book book = new Book(title);
		// Adding book to database
		success = ParserObjectToSQL.getInstance().newBook(book);
		if(success){
			try{
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						new BookDetailsUI(book);
					}
				});
				
			} catch(Exception ex){EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					UIDisplayManager.displayErrorMessage(
							null,
							"Error displaying BookDetailsUI");
					}
				});
			}
		}
		return success;
	}
	
	/**
	 * Registers new user.
	 */
	private void registerUser(){
		try {
			EventQueue.invokeLater(new Runnable(){
				@Override
				public void run() {
					NewUserUI dialog = new NewUserUI();
					if(dialog.isOkPressed()){
						String userType = null;
						if(dialog.isBorrowerSelected()){
							userType = "Borrower";
						} else {
							userType = "Librarian";
						}
						
						addUser(
								userType,
								dialog.getFirstname(),
								dialog.getMiddlename(),
								dialog.getSurname());
					}
				}
			});
		} catch (Exception e) {
			displayErrorMessage();
		}
	}
	
	/**
	 * Requesting to activate new IDCard.
	 */
	private void activateIDCard(){
		try {
			new IDCardActivationUI();
		} catch (Exception e) {
			displayErrorMessage();
		}
	}
	
	private void addUser(String userType, String firstname, String middlename, String surname){
		final User user;
		
		if(userType.equals("Borrower")){
			user = new Borrower();
			
			user.setFirstname(firstname);
			user.setMiddlename(middlename);
			user.setSurname(surname);
			
			// Updating database
			new SwingWorker<Void, Void>(){
				@Override
				protected Void doInBackground() throws Exception {
					AdminUsers.getInstance().addBorrower((Borrower) user);
					return null;
				}
			}.execute();
			
			displayUserDetails(user);
		} else {
			user = new Librarian();
			
			user.setFirstname(firstname);
			user.setMiddlename(middlename);
			user.setSurname(surname);
			
			// Updating database
			new SwingWorker<Void, Void>(){
				@Override
				protected Void doInBackground() throws Exception {
					AdminUsers.getInstance().addLibrarian((Librarian) user);
					return null;
				}
			}.execute();
			
			displayUserDetails(user);
		}
	}
	
	/**
	 * Displays details about specified user.
	 * 
	 * @param userIn
	 */
	private void displayUserDetails(User userIn){
		final User user = userIn;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new UserDetailsUI(user);
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
	}
	
	/**
	 * Creates and returns panel with exit button.
	 * 
	 * @return JPanel
	 */
	private JPanel createExitButtonPane() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton exitButton = new JButton("Exit");
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		panel.add(exitButton);
		
		return panel;
	}
	
	/**
	 * Displays general error message in dialog box.
	 */
	private void displayErrorMessage(){
		UIDisplayManager.displayErrorMessage(
				DeskUI.this,
				"Error occured while trying to display dialog box!");
	}
	
	private void exitApplication(){
		if(JOptionPane.showConfirmDialog(
				DeskUI.this,
				"Do you really want to exit application?",
				"Exit application",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION){
			Library.getInstance().exit();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		if(event.equals("register user")){
			registerUser();
		} else if(event.equals("activate idcard")){
			activateIDCard();
		} else if(event.equals("reserve")){
			reserve();
		} else if(event.equals("borrow")){
			borrow();
		} else if(event.equals("renew")){
			renew();
		} else if(event.equals("return")){
			returnBook();
		} else if(event.equals("exit")){
			exitApplication();
		}
	}
	
	@Override
	public void displayActivity(String text) {
		status.setText(text);
	}
	
	public void ready(){
		initialised = true;
		
		menubar.setEnabled(initialised);
		
		registerButton.setEnabled(initialised);
		activateButton.setEnabled(initialised);
		reserveButton.setEnabled(initialised);
		borrowButton.setEnabled(initialised);
		renewalButton.setEnabled(initialised);
		returnButton.setEnabled(initialised);
		catalogueButton.setEnabled(initialised);
		
		ActivityRegister.newActivity(this, "Ready to use!");
	}
}