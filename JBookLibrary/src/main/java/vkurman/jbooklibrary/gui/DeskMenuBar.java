package vkurman.jbooklibrary.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import vkurman.jbooklibrary.demo.DemoButtonsPanelUI;
import vkurman.jbooklibrary.utils.jbasiccalendar.JBasicDateChooserUI;

/**
 * 
 * DeskMenuBar is menu bar for library desk frame.
 *
 * <p>
 * Date : 27 Mar 2015
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public class DeskMenuBar extends JMenuBar {

	private static final long serialVersionUID = 4996119998197671262L;
	private ActionListener listener;
	
	private JMenu mnFile;
	private JMenu menuDesk;
	private JMenu mnCatalogue;
	private JMenu mnCalendar;
	private JMenu mnReports;
	private JMenu mnAdministration;
	private JMenu mnTools;
	private JMenu mnHelp;
	
	public DeskMenuBar(ActionListener listener){
		this.listener = listener;
		
		add(createFileMenu());
		add(createDeskMenu());
		add(createCatalogueMenu());
		add(createCalendarMenu());
		add(createReportsMenu());
		add(createAdministrationMenu());
		add(createToolsMenu());
		add(createHelpMenu());
	}
	
	/**
	 * Creates and returns "File" menu.
	 * 
	 * @return JMenu
	 */
	private JMenu createFileMenu(){
		mnFile = new JMenu("File");
		mnFile.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(listener);
		mntmExit.setActionCommand("exit");
		mntmExit.setMnemonic(KeyEvent.VK_X);
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		mnFile.add(mntmExit);
		
		return mnFile;
	}
	
	/**
	 * Creates and returns "Desk" menu.
	 * 
	 * @return JMenu
	 */
	private JMenu createDeskMenu(){
		menuDesk = new JMenu("Desk");
		menuDesk.setMnemonic(KeyEvent.VK_D);
		
		JMenuItem mntmRegister = new JMenuItem("Register User");
		mntmRegister.setActionCommand("register user");
		mntmRegister.addActionListener(listener);
		
		JMenuItem mntmActivate = new JMenuItem("Activate IDCard");
		mntmActivate.setActionCommand("activate idcard");
		mntmActivate.addActionListener(listener);
		
		JMenuItem mntmReserve = new JMenuItem("Reserve");
		mntmReserve.setActionCommand("reserve");
		mntmReserve.addActionListener(listener);
		
		JMenuItem mntmBorrow = new JMenuItem("Borrow");
		mntmBorrow.setActionCommand("borrow");
		mntmBorrow.addActionListener(listener);
		
		JMenuItem mntmRenew = new JMenuItem("Renew");
		mntmRenew.setActionCommand("renew");
		mntmRenew.addActionListener(listener);
		
		JMenuItem mntmReturn = new JMenuItem("Return");
		mntmReturn.setActionCommand("return");
		mntmReturn.addActionListener(listener);
		
		// Adding items to menu
		menuDesk.add(mntmRegister);
		menuDesk.add(mntmActivate);
		menuDesk.addSeparator();
		menuDesk.add(mntmReserve);
		menuDesk.add(mntmBorrow);
		menuDesk.add(mntmRenew);
		menuDesk.add(mntmReturn);
		
		return menuDesk;
	}
	
	/**
	 * Creates and returns "Catalogue" menu.
	 * 
	 * @return JMenu
	 */
	private JMenu createCatalogueMenu(){
		mnCatalogue = new JMenu("Catalogue");
		mnCatalogue.setMnemonic(KeyEvent.VK_G);
		
		JMenuItem mntmNewBook = new JMenuItem("New Book");
		mntmNewBook.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TextInputDialog dialog = new TextInputDialog("Book title input");
					if(dialog.isOkPressed()){
						if(dialog.getInput() != null){
							if(!dialog.getInput().isEmpty()){
								DeskUI.addNewBook(dialog.getInput());
							} else {
								displayErrorMessage("Book title has not been entered!");
							}
						} else {
							displayErrorMessage("Book title is NULL");
						}
					}
				} catch (Exception ex) {
					displayErrorMessage();
				}
			}
		});
		
		JMenuItem mntmCatalogueSearch = new JMenuItem("Catalogue Search");
		mntmCatalogueSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIDisplayManager.startBooksAdminUI();
			}
		});
		
		mnCatalogue.add(mntmNewBook);
		mnCatalogue.addSeparator();
		mnCatalogue.add(mntmCatalogueSearch);
		
		return mnCatalogue;
	}
	
	/**
	 * Creates and returns "Calendar" menu.
	 * 
	 * @return JMenu
	 */
	private JMenu createCalendarMenu(){
		mnCalendar = new JMenu("Calendar");
		mnCalendar.setMnemonic(KeyEvent.VK_C);
		
		JMenuItem mntmDisplayCalendar = new JMenuItem("Display Calendar");
		mntmDisplayCalendar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					JBasicDateChooserUI cal = new JBasicDateChooserUI(DeskMenuBar.this);
					cal.setVisible(true);
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
		mnCalendar.add(mntmDisplayCalendar);
		
		return mnCalendar;
	}
	
	/**
	 * Creates and returns "Reports" menu.
	 * 
	 * @return JMenu
	 */
	private JMenu createReportsMenu(){
		mnReports = new JMenu("Reports");
		mnReports.setMnemonic(KeyEvent.VK_R);
		
		JMenuItem mntmStatistics = new JMenuItem("Statistics");
		mntmStatistics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new StatisticsUI();
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
		mnReports.add(mntmStatistics);
		
		mnReports.addSeparator();
		
		// TODO implement functionality
		JMenuItem mntmRequestedItems = new JMenuItem("Requested Items");
		mntmRequestedItems.setEnabled(false);
		mnReports.add(mntmRequestedItems);
		
		// TODO implement functionality
		JMenuItem mntmBooksOnLoan = new JMenuItem("Books on Loan");
		mntmBooksOnLoan.setEnabled(false);
		mnReports.add(mntmBooksOnLoan);
		
		// TODO implement functionality
		JMenuItem mntmOverdueLoans = new JMenuItem("Overdue Loans");
		mntmOverdueLoans.setEnabled(false);
		mnReports.add(mntmOverdueLoans);
		
		return mnReports;
	}
	
	/**
	 * Creates and returns "Administration" menu.
	 * 
	 * @return JMenu
	 */
	private JMenu createAdministrationMenu(){
		mnAdministration = new JMenu("Administration");
		mnAdministration.setMnemonic(KeyEvent.VK_A);
		
		JMenuItem mntmRequestItem = new JMenuItem("Request Item");
		mntmRequestItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new RequestHandlerUI();
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
		mnAdministration.add(mntmRequestItem);
		
		mnAdministration.addSeparator();
		
		JMenuItem mntmBooksAdministration = new JMenuItem("Books Administration");
		mntmBooksAdministration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		mntmBooksAdministration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIDisplayManager.startBooksAdminUI();
			}
		});
		mnAdministration.add(mntmBooksAdministration);
		
		JMenuItem mntmUsersAdministration = new JMenuItem("Users Administration");
		mntmUsersAdministration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		mntmUsersAdministration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIDisplayManager.startUsersAdminUI();
			}
		});
		mnAdministration.add(mntmUsersAdministration);
		
		JMenuItem mntmCardsAdministration = new JMenuItem("IDCards Administration");
		mntmCardsAdministration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		mntmCardsAdministration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIDisplayManager.startIDCardsAdminUI();
			}
		});
		mnAdministration.add(mntmCardsAdministration);
		
		JMenuItem mntmLoansAdministration = new JMenuItem("Loans Administration");
		mntmLoansAdministration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		mntmLoansAdministration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIDisplayManager.startLoansAdminUI();
			}
		});
		mnAdministration.add(mntmLoansAdministration);
		
		JMenuItem mntmFinesAdministration = new JMenuItem("Fines Administration");
		mntmFinesAdministration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		mntmFinesAdministration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIDisplayManager.startFinesAdminUI();
			}
		});
		mnAdministration.add(mntmFinesAdministration);
		
		JMenuItem mntmPaymentsAdministration = new JMenuItem("Payments Administration");
		mntmPaymentsAdministration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		mntmPaymentsAdministration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIDisplayManager.startPaymentsAdminUI();
			}
		});
		mnAdministration.add(mntmPaymentsAdministration);
		
		JMenuItem mntmRequestsAdministration = new JMenuItem("Requests Administration");
		mntmRequestsAdministration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		mntmRequestsAdministration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					UIDisplayManager.startRequestsAdminUI();
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
		mnAdministration.add(mntmRequestsAdministration);
		
		JMenuItem mntmReservationsAdministration = new JMenuItem("Reservations Administration");
		mntmReservationsAdministration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		mntmReservationsAdministration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIDisplayManager.startReservationsAdminUI();
			}
		});
		mnAdministration.add(mntmReservationsAdministration);
		
		return mnAdministration;
	}
	
	/**
	 * Creates and returns "Tools" menu.
	 * 
	 * @return JMenu
	 */
	private JMenu createToolsMenu(){
		mnTools = new JMenu("Tools");
		mnTools.setMnemonic(KeyEvent.VK_T);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mntmPreferences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new PreferencesUI();
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
		mnTools.add(mntmPreferences);
		
		JMenuItem mntmDatabaseInfo = new JMenuItem("Database Information");
		mntmDatabaseInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new DBInfoUI();
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
		mnTools.add(mntmDatabaseInfo);
		
		mnTools.addSeparator();
		
		JMenuItem mntmDemoButtons = new JMenuItem("Demo");
		mntmDemoButtons.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new DemoButtonsPanelUI();
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
		mnTools.add(mntmDemoButtons);
		
		return mnTools;
	}
	
	/**
	 * Creates and returns "Help" menu.
	 * 
	 * @return JMenu
	 */
	private JMenu createHelpMenu(){
		mnHelp = new JMenu("Help");
		mnHelp.setMnemonic(KeyEvent.VK_H);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new AboutUI();
				} catch (Exception e) {
					displayErrorMessage();
				}
			}
		});
		mnHelp.add(mntmAbout);
		
		return mnHelp;
	}
	
	/**
	 * Displays general error message in dialog box.
	 */
	private void displayErrorMessage(){
		UIDisplayManager.displayErrorMessage(
				this,
				"Error occured while trying to display dialog box!");
	}
	
	/**
	 * Displays general error message in dialog box.
	 * 
	 * @param text
	 */
	private void displayErrorMessage(String text){
		UIDisplayManager.displayErrorMessage(
				this,
				text);
	}
	
	@Override
	public void setEnabled(boolean enable){
		mnFile.setEnabled(enable);
		menuDesk.setEnabled(enable);
		mnCatalogue.setEnabled(enable);
		mnCalendar.setEnabled(enable);
		mnReports.setEnabled(enable);
		mnAdministration.setEnabled(enable);
		mnTools.setEnabled(enable);
		mnHelp.setEnabled(enable);
	}
}