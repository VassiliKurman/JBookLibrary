package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.utils.SpringUtilities;
import vkurman.jbooklibrary.utils.jbasiccalendar.JBasicEventPanel;
import vkurman.jbooklibrary.utils.jbasiccalendar.JDayPanel;
import vkurman.jbooklibrary.utils.jbasiccalendar.UpdateObserver;

/**
 * Preferences user interface for library.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class PreferencesUI extends JDialog implements ActionListener, ChangeListener {
	
	private static final long serialVersionUID = -7251417603328472115L;
	private static final String TITLE = "Preferences - " + AdminPrefs.LIBRARY_NAME;
	
	private JTextField txtLibraryName;
	private JTextField txtConfigurationPath;
	
	private JCheckBox chxInitialised;
	private JCheckBox chxDisplayActivityRegister;
	
	private JTextField txtApplicationName;
	private JTextField txtVersion;
	private JTextField txtAuthor;
	private JTextField txtYear;
	
	private JTextField txtMessageDelayTime;
	
	private JTextField txtDatabasePath;
	private JTextField txtDatabaseUser;
	private JTextField txtDatabasePassword;
	
	private JTextField txtDateFormatStringYear;
	private JTextField txtDateFormatSring;
	private JTextField txtDateFormatStringExtended;
	
	private JSpinner IDCardExpirePeriod;
	private JSpinner IDCardBorrowerExpirePeriod;
	private JSpinner IDCardLibrarianExpirePeriod;
	private JSpinner shortLoanPeriod;
	private JSpinner standardLoanPeriod;
	private JSpinner extendedLoanPeriod;
	private JSpinner standardReservationPeriod;
	
	private JSpinner maxIDCardNumber;
	private JSpinner maxItemsToBorrow;
	private JSpinner maxItemsToBorrowSpecial;
	private JSpinner bookIDLength;
	private JSpinner bookMaxIDNumber;
	private JSpinner userIDLength;
	private JSpinner userMaxIDNumber;
	private JSpinner librarianMinIDNumber;
	private JSpinner librarianMaxIDNumber;
	private JSpinner borowerMinIDNumber;
	private JSpinner borrowerMaxIDNumber;
	
	private JSpinner width;
	private JSpinner height;
	private JSpinner x;
	private JSpinner y;
	
	private JCheckBox chargeOverdue;
	private JTextField txtDailyFine;
	
	private JCheckBox mon;
	private JCheckBox tue;
	private JCheckBox wed;
	private JCheckBox thu;
	private JCheckBox fri;
	private JCheckBox sat;
	private JCheckBox sun;
	
	/**
	 * Constructor.
	 * 
	 * @param parent
	 */
	public PreferencesUI(){
		txtLibraryName = new JTextField(AdminPrefs.LIBRARY_NAME);
		txtConfigurationPath = new JTextField(AdminPrefs.CONFIGURATION_PATH);
		
		chxInitialised = new JCheckBox("Initialised");
		chxInitialised.setSelected(AdminPrefs.INITIALISED);
		chxInitialised.setEnabled(false);
		
		chxDisplayActivityRegister = new JCheckBox("Display Activity Register");
		chxDisplayActivityRegister.setSelected(AdminPrefs.DISPLAY_ACTIVITY_REGISTER);
		
		txtApplicationName = new JTextField(AdminPrefs.APPLICATION_NAME);
		txtApplicationName.setEditable(false);
		txtVersion = new JTextField(Float.toString(AdminPrefs.VERSION));
		txtVersion.setEditable(false);
		txtAuthor = new JTextField(AdminPrefs.AUTHOR);
		txtAuthor.setEditable(false);
		txtYear = new JTextField(AdminPrefs.YEAR);
		txtYear.setEditable(false);
		
		txtMessageDelayTime = new JTextField(Long.toString(AdminPrefs.MESSAGE_DELAY_TIME));
		
		txtDatabasePath = new JTextField(AdminPrefs.DATABASE_PATH);
		txtDatabaseUser = new JTextField(AdminPrefs.DATABASE_USER);
		txtDatabasePassword = new JTextField(AdminPrefs.DATABASE_PASSWORD);
		
		txtDateFormatStringYear = new JTextField(AdminPrefs.DATE_FORMAT_STRING_YEAR);
		txtDateFormatSring = new JTextField(AdminPrefs.DATE_FORMAT_STRING);
		txtDateFormatStringExtended = new JTextField(AdminPrefs.DATE_FORMAT_STRING_EXTENDED);
		
		maxItemsToBorrow = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.MAX_ITEMS_TO_BORROW, 1, 100, 1));
		maxItemsToBorrowSpecial = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.MAX_ITEMS_TO_BORROW_SPECIAL, 1, 100, 1));
		
		IDCardExpirePeriod = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.IDCARD_EXPIRE_PERIOD, 1, 100, 1));
		IDCardBorrowerExpirePeriod = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.IDCARD_BORROWER_EXPIRE_PERIOD, 1, 100, 1));
		IDCardLibrarianExpirePeriod = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.IDCARD_LIBRARIAN_EXPIRE_PERIOD, 1, 100, 1));
		shortLoanPeriod = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.SHORT_LOAN_PERIOD, 1, 100, 1));
		standardLoanPeriod = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.STANDARD_LOAN_PERIOD, 1, 100, 1));
		extendedLoanPeriod = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.EXTENDED_LOAN_PERIOD, 1, 100, 1));
		standardReservationPeriod = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.STANDARD_RESERVATION_PERIOD, 1, 100, 1));
		
		maxIDCardNumber = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.MAX_IDCARD_NUMBER, 1, 99999, 1));
		bookIDLength = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.BOOK_ID_LENGTH, 1, 4, 1));
		bookIDLength.setEnabled(false);
		bookMaxIDNumber = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.BOOK_MAX_ID_NUMBER, 1, 9999, 1));
		userIDLength = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.USER_ID_LENGTH, 1, 5, 1));
		userIDLength.setEnabled(false);
		userMaxIDNumber = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.USER_MAX_ID_NUMBER, 1, 99999, 1));
		librarianMinIDNumber = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.LIBRARIAN_MIN_ID_NUMBER, 1, 99999, 1));
		librarianMaxIDNumber = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.LIBRARIAN_MAX_ID_NUMBER, 1, 99999, 1));
		borowerMinIDNumber = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.BORROWER_MIN_ID_NUMBER, 1, 99999, 1));
		borrowerMaxIDNumber = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.BORROWER_MAX_ID_NUMBER, 1, 99999, 1));
		
		width = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.DIMENSION_TABLE_PANEL.width, 1, 2000, 1));
		height = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.DIMENSION_TABLE_PANEL.height, 1, 2000, 1));
		x = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.DEFAULT_WINDOW_LOCATION.x, 1, 2000, 1));
		y = new JSpinner(new SpinnerNumberModel(
				AdminPrefs.DEFAULT_WINDOW_LOCATION.y, 1, 2000, 1));
		
		chargeOverdue = new JCheckBox("Charge");
		chargeOverdue.setSelected(AdminPrefs.CHARGE_FOR_LOAN_OVERDUE);
		chargeOverdue.addChangeListener(this);
		
		txtDailyFine = new JTextField(AdminPrefs.DAILY_FINE.toString());
		txtDailyFine.setEnabled(chargeOverdue.isSelected());
		
		mon = new JCheckBox();
		mon.setSelected(AdminPrefs.WORKING_DAYS[0]);
		tue = new JCheckBox();
		tue.setSelected(AdminPrefs.WORKING_DAYS[1]);
		wed = new JCheckBox();
		wed.setSelected(AdminPrefs.WORKING_DAYS[2]);
		thu = new JCheckBox();
		thu.setSelected(AdminPrefs.WORKING_DAYS[3]);
		fri = new JCheckBox();
		fri.setSelected(AdminPrefs.WORKING_DAYS[4]);
		sat = new JCheckBox();
		sat.setSelected(AdminPrefs.WORKING_DAYS[5]);
		sun = new JCheckBox();
		sun.setSelected(AdminPrefs.WORKING_DAYS[6]);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 * 
	 * @param parent
	 */
	private void showUI(){
		setTitle(TITLE);
		setLayout(new BorderLayout(5, 5));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		
		add(createTabbedPanel(), BorderLayout.CENTER);
		add(createButtonsPanel(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns JTabbedPane.
	 * 
	 * @return JTabbedPane
	 */
	private JTabbedPane createTabbedPanel(){
		JTabbedPane panel = new JTabbedPane();
		
		panel.addTab("General", createGeneralPanel());
		panel.setMnemonicAt(0, KeyEvent.VK_1);
		
		panel.addTab("Configuration", createConfigPanel());
		panel.setMnemonicAt(1, KeyEvent.VK_2);
		
		panel.addTab("Opening hours", createDatesPanel());
		panel.setMnemonicAt(2, KeyEvent.VK_3);
		
		return panel;
	}
	
	/**
	 * Creates and returns panel.
	 * 
	 * @return JPanel
	 */
	private JPanel createGeneralPanel(){
		JPanel panel = new JPanel(new SpringLayout());
		
		// Period panel
		JPanel periodPanel = new JPanel(new SpringLayout());
		periodPanel.setBorder(BorderFactory.createTitledBorder("Period"));
		periodPanel.add(new JLabel("IDCard Expire Period:", JLabel.TRAILING));
		periodPanel.add(IDCardExpirePeriod);
		periodPanel.add(new JLabel("years", JLabel.LEADING));
		periodPanel.add(new JLabel("IDCard Borrower Expire Period:", JLabel.TRAILING));
		periodPanel.add(IDCardBorrowerExpirePeriod);
		periodPanel.add(new JLabel("years", JLabel.LEADING));
		periodPanel.add(new JLabel("IDCard Librarian Expire Period:", JLabel.TRAILING));
		periodPanel.add(IDCardLibrarianExpirePeriod);
		periodPanel.add(new JLabel("years", JLabel.LEADING));
		
		periodPanel.add(new JLabel("Short Loan Period:", JLabel.TRAILING));
		periodPanel.add(shortLoanPeriod);
		periodPanel.add(new JLabel("days", JLabel.LEADING));
		periodPanel.add(new JLabel("Standard Loan Period:", JLabel.TRAILING));
		periodPanel.add(standardLoanPeriod);
		periodPanel.add(new JLabel("days", JLabel.LEADING));
		periodPanel.add(new JLabel("Extended Loan Period:", JLabel.TRAILING));
		periodPanel.add(extendedLoanPeriod);
		periodPanel.add(new JLabel("days", JLabel.LEADING));
		periodPanel.add(new JLabel("Standard Reservation Period:", JLabel.TRAILING));
		periodPanel.add(standardReservationPeriod);
		periodPanel.add(new JLabel("days", JLabel.LEADING));
		SpringUtilities.makeCompactGrid(
				periodPanel,
				7, 3,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		
		// ID panel
		JPanel idPanel = new JPanel(new SpringLayout());
		idPanel.setBorder(BorderFactory.createTitledBorder("Identification"));
		idPanel.add(new JLabel("Max IDCard Number:", JLabel.TRAILING));
		idPanel.add(maxIDCardNumber);
		idPanel.add(new JLabel("Book ID Length:", JLabel.TRAILING));
		idPanel.add(bookIDLength);
		idPanel.add(new JLabel("Book Max ID Number:", JLabel.TRAILING));
		idPanel.add(bookMaxIDNumber);
		idPanel.add(new JLabel("User ID Length:", JLabel.TRAILING));
		idPanel.add(userIDLength);
		idPanel.add(new JLabel("User Max ID Number:", JLabel.TRAILING));
		idPanel.add(userMaxIDNumber);
		idPanel.add(new JLabel("Librarian Min ID Number:", JLabel.TRAILING));
		idPanel.add(librarianMinIDNumber);
		idPanel.add(new JLabel("Librarian Max ID Number:", JLabel.TRAILING));
		idPanel.add(librarianMaxIDNumber);
		idPanel.add(new JLabel("Borower Min ID Number:", JLabel.TRAILING));
		idPanel.add(borowerMinIDNumber);
		idPanel.add(new JLabel("Borrower Max ID Number:", JLabel.TRAILING));
		idPanel.add(borrowerMaxIDNumber);
		SpringUtilities.makeCompactGrid(
				idPanel,
				9, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		// Panel
		JPanel numbersPanel = new JPanel(new SpringLayout());
		numbersPanel.setBorder(BorderFactory.createTitledBorder("Numbers"));
		numbersPanel.add(new JLabel("Max Items To Borrow:", JLabel.TRAILING));
		numbersPanel.add(maxItemsToBorrow);
		numbersPanel.add(new JLabel("Max Items To Borrow Special:", JLabel.TRAILING));
		numbersPanel.add(maxItemsToBorrowSpecial);
		SpringUtilities.makeCompactGrid(
				numbersPanel,
				2, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		// Panel
		JPanel finePanel = new JPanel(new SpringLayout());
		finePanel.setBorder(BorderFactory.createTitledBorder("Fine"));
		finePanel.add(chargeOverdue);
		finePanel.add(new JLabel("Daily Fine:", JLabel.TRAILING));
		finePanel.add(txtDailyFine);
		SpringUtilities.makeCompactGrid(
				finePanel,
				1, 3,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		panel.add(periodPanel);
		panel.add(numbersPanel);
		panel.add(idPanel);
		panel.add(finePanel);
		
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(
				panel,
				4, 1,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
	
	/**
	 * Creates and returns panel.
	 * 
	 * @return JPanel
	 */
	private JPanel createConfigPanel(){
		JPanel panel = new JPanel(new SpringLayout());
		
		// Application panel
		JPanel appPanel = new JPanel(new SpringLayout());
		appPanel.setBorder(BorderFactory.createTitledBorder("Application"));
		appPanel.add(new JLabel("Application Name:", JLabel.TRAILING));
		appPanel.add(txtApplicationName);
		appPanel.add(new JLabel("Version:", JLabel.TRAILING));
		appPanel.add(txtVersion);
		appPanel.add(new JLabel("Author:", JLabel.TRAILING));
		appPanel.add(txtAuthor);
		appPanel.add(new JLabel("Year:", JLabel.TRAILING));
		appPanel.add(txtYear);
		SpringUtilities.makeCompactGrid(
				appPanel,
				4, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		// Library panel
		JPanel libraryPanel = new JPanel(new SpringLayout());
		libraryPanel.setBorder(BorderFactory.createTitledBorder("Library"));
		libraryPanel.add(new JLabel("Library name:", JLabel.TRAILING));
		libraryPanel.add(txtLibraryName);
		libraryPanel.add(new JLabel("Configuration Path:", JLabel.TRAILING));
		libraryPanel.add(txtConfigurationPath);
		libraryPanel.add(chxInitialised);
		libraryPanel.add(chxDisplayActivityRegister);
		SpringUtilities.makeCompactGrid(
				libraryPanel,
				3, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		JPanel databasePanel = new JPanel(new SpringLayout());
		databasePanel.setBorder(BorderFactory.createTitledBorder("Database"));
		databasePanel.add(new JLabel("Database Path:", JLabel.TRAILING));
		databasePanel.add(txtDatabasePath);
		databasePanel.add(new JLabel("Database Username:", JLabel.TRAILING));
		databasePanel.add(txtDatabaseUser);
		databasePanel.add(new JLabel("Database Password:", JLabel.TRAILING));
		databasePanel.add(txtDatabasePassword);
		SpringUtilities.makeCompactGrid(
				databasePanel,
				3, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		// Message delay panel
		JPanel msgPanel = new JPanel(new SpringLayout());
		msgPanel.setBorder(BorderFactory.createTitledBorder("Timing"));
		msgPanel.add(new JLabel("Message Delay Time:", JLabel.TRAILING));
		msgPanel.add(txtMessageDelayTime);
		msgPanel.add(new JLabel("milliseconds", JLabel.LEADING));
		SpringUtilities.makeCompactGrid(
				msgPanel,
				1, 3,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		// Text formatting
		JPanel txtPanel = new JPanel(new SpringLayout());
		txtPanel.setBorder(BorderFactory.createTitledBorder("Text Formatting"));
		txtPanel.add(new JLabel("Date Format String Year:", JLabel.TRAILING));
		txtPanel.add(txtDateFormatStringYear);
		txtPanel.add(new JLabel("Date Format Sring:", JLabel.TRAILING));
		txtPanel.add(txtDateFormatSring);
		txtPanel.add(new JLabel("Date Format String Extended:", JLabel.TRAILING));
		txtPanel.add(txtDateFormatStringExtended);
		SpringUtilities.makeCompactGrid(
				txtPanel,
				3, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		// GUI settings
		JPanel uiPanel = new JPanel(new SpringLayout());
		uiPanel.setBorder(BorderFactory.createTitledBorder("GUI settigs"));
		
		JPanel dimesionPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		dimesionPanel.setBorder(BorderFactory.createTitledBorder("Table Panel Dimensions"));
		dimesionPanel.add(new JLabel("width:", JLabel.TRAILING));
		dimesionPanel.add(width);
		dimesionPanel.add(new JLabel("height:", JLabel.TRAILING));
		dimesionPanel.add(height);
		
		JPanel locationPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		locationPanel.setBorder(BorderFactory.createTitledBorder("Window Location"));
		locationPanel.add(new JLabel("x:", JLabel.TRAILING));
		locationPanel.add(x);
		locationPanel.add(new JLabel("y:", JLabel.TRAILING));
		locationPanel.add(y);
		
		uiPanel.add(dimesionPanel);
		uiPanel.add(locationPanel);
		
		SpringUtilities.makeCompactGrid(
				uiPanel,
				2, 1,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		panel.add(appPanel);
		panel.add(libraryPanel);
		panel.add(databasePanel);
		panel.add(msgPanel);
		panel.add(txtPanel);
		panel.add(uiPanel);
		
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(
				panel,
				6, 1,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
	
	/**
	 * Creates and returns panel.
	 * 
	 * @return JPanel
	 */
	private JPanel createDatesPanel(){
		JPanel panel = new JPanel(new SpringLayout());
		
		// Panel
		JPanel calendarPanel = new JPanel(new BorderLayout(5, 5));
		
		final JCheckBox chxRepeat = new JCheckBox("Yearly repeatable holiday");
		chxRepeat.setSelected(true);
		
		final JBasicEventPanel cPanel = new JBasicEventPanel();

		if(!AdminPrefs.HOLIDAYS.isEmpty()){
			cPanel.markDates(AdminPrefs.HOLIDAYS, new Color(255, 0, 255));
		}
		cPanel.setBorder(BorderFactory.createTitledBorder(""));
		cPanel.register(new UpdateObserver() {
			@Override
			public void updated(boolean arg) {
				if((boolean) arg){
					if(!AdminPrefs.addHoliday(
							cPanel.getCalendar(),
							chxRepeat.isSelected())){
						AdminPrefs.removeHoliday(
								cPanel.getCalendar());
					}
				}
			}
		});
		
		JPanel hintsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
		
		JLabel black = new JLabel("Black - week number in the year");
		black.setForeground(Color.BLACK);
		JLabel blue = new JLabel("Blue - weekdays + Saturday");
		blue.setForeground(JDayPanel.FOREGROUND_COLOUR_ACTIVE_WEEKDAY);
		JLabel red = new JLabel("Red - Sunday");
		red.setForeground(JDayPanel.FOREGROUND_COLOUR_ACTIVE_SUNDAY);
		JLabel green = new JLabel("Green - current date");
		green.setForeground(JDayPanel.FOREGROUND_COLOUR_CURRENT);
		JLabel purple = new JLabel("Purple - Holiday");
		purple.setForeground(JDayPanel.BACKGROUND_COLOUR_SPECIAL);
		
		hintsPanel.add(black);
		hintsPanel.add(blue);
		hintsPanel.add(red);
		hintsPanel.add(green);
		hintsPanel.add(purple);
		
		calendarPanel.add(chxRepeat, BorderLayout.PAGE_START);
		calendarPanel.add(cPanel, BorderLayout.CENTER);
		calendarPanel.add(hintsPanel, BorderLayout.PAGE_END);
		
		// Panel
		JPanel daysPanel = new JPanel(new SpringLayout());
		daysPanel.setBorder(BorderFactory.createTitledBorder("Working days"));
		daysPanel.add(new JLabel("Mon", JLabel.CENTER));
		daysPanel.add(new JLabel("Tue", JLabel.CENTER));
		daysPanel.add(new JLabel("Wen", JLabel.CENTER));
		daysPanel.add(new JLabel("Thu", JLabel.CENTER));
		daysPanel.add(new JLabel("Fri", JLabel.CENTER));
		daysPanel.add(new JLabel("Sat", JLabel.CENTER));
		daysPanel.add(new JLabel("Sun", JLabel.CENTER));
		daysPanel.add(mon);
		daysPanel.add(tue);
		daysPanel.add(wed);
		daysPanel.add(thu);
		daysPanel.add(fri);
		daysPanel.add(sat);
		daysPanel.add(sun);
		SpringUtilities.makeCompactGrid(
				daysPanel,
				2, 7,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		panel.add(calendarPanel);
		panel.add(daysPanel);
		
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(
				panel,
				2, 1,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
	
	/**
	 * Creates and returns panel with buttons.
	 * 
	 * @return JPanel
	 */
	private JPanel createButtonsPanel(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("ok");
		okButton.addActionListener(this);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		
		panel.add(okButton);
		panel.add(cancelButton);
		
		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")){
			// Closing dialog in any case
			dispose();
		} else if(e.getActionCommand().equals("ok")){
			AdminPrefs.setLibraryName(txtLibraryName.getText());
			AdminPrefs.setConfigurationPath(txtConfigurationPath.getText());
			
			AdminPrefs.setInitialised(chxInitialised.isSelected());
			AdminPrefs.showActivityRegister(chxDisplayActivityRegister.isSelected());
			
			AdminPrefs.setMessageDelayTime(Long.parseLong(txtMessageDelayTime.getText()));
			
			AdminPrefs.setDatabasePath(txtDatabasePath.getText());
			AdminPrefs.setDatabaseUser(txtDatabaseUser.getText());
			AdminPrefs.setDatabasePassword(txtDatabasePassword.getText());
			
			AdminPrefs.setDateFormatStringYear(txtDateFormatStringYear.getText());
			AdminPrefs.setDateFormatString(txtDateFormatSring.getText());
			AdminPrefs.setDateFormatStringExtended(txtDateFormatStringExtended.getText());
			
			AdminPrefs.setMaxIDCardNumber((Integer) maxIDCardNumber.getModel().getValue());
			AdminPrefs.setMaximumItemsToBorrow((Integer) maxItemsToBorrow.getModel().getValue());
			AdminPrefs.setMaximumItemsToBorrowSpecial((Integer) maxItemsToBorrowSpecial.getModel().getValue());
			
			AdminPrefs.setIDCardExpirePeriod((Integer) IDCardExpirePeriod.getModel().getValue());
			AdminPrefs.setBorrowerIDCardExpirePeriod((Integer) IDCardBorrowerExpirePeriod.getModel().getValue());
			AdminPrefs.setLibrarianIDCardExpirePeriod((Integer) IDCardLibrarianExpirePeriod.getModel().getValue());
			AdminPrefs.setShortLoanPeriod((Integer) shortLoanPeriod.getModel().getValue());
			AdminPrefs.setStandardLoanPeriod((Integer) standardLoanPeriod.getModel().getValue());
			AdminPrefs.setExtendedLoanPeriod((Integer) extendedLoanPeriod.getModel().getValue());
			AdminPrefs.setStandardReservationPeriod((Integer) standardReservationPeriod.getModel().getValue());
			
			AdminPrefs.setBookIDLength((Integer) bookIDLength.getModel().getValue());
			AdminPrefs.setMaximumBookIDNumber((Integer) bookMaxIDNumber.getModel().getValue());
			AdminPrefs.setUserIDLength((Integer) userIDLength.getModel().getValue());
			AdminPrefs.setMaximumUserIDNumber((Integer) userMaxIDNumber.getModel().getValue());
			AdminPrefs.setMinimumLibrarianIDNumber((Integer) librarianMinIDNumber.getModel().getValue());
			AdminPrefs.setMaximumLibrarianIDNumber((Integer) librarianMaxIDNumber.getModel().getValue());
			AdminPrefs.setMinimumBorrowerIDNumber((Integer) borowerMinIDNumber.getModel().getValue());
			AdminPrefs.setMaximumBorrowerIDNumber((Integer) borrowerMaxIDNumber.getModel().getValue());
			
			AdminPrefs.setDimensionForTablePanel(new Dimension(
					(Integer) width.getModel().getValue(),
					(Integer) height.getModel().getValue()));
			AdminPrefs.setDefaultWindowLacation(new Point(
					(Integer) x.getModel().getValue(),
					(Integer) y.getModel().getValue()));
			
			AdminPrefs.setChargeForLoanOverdue(chargeOverdue.isSelected());
			
			AdminPrefs.setDailyFine(new BigDecimal(txtDailyFine.getText()));
			
			boolean[] days = {
					mon.isSelected(),
					tue.isSelected(),
					wed.isSelected(),
					thu.isSelected(),
					fri.isSelected(),
					sat.isSelected(),
					sun.isSelected()};
			AdminPrefs.setWorkingDays(days);
			
			AdminPrefs.setHolidays();
			
			// Saving properties
			AdminPrefs.saveProperties(AdminPrefs.PROPERTIES, AdminPrefs.getConfigFile());
			
			// Closing dialog in any case
			dispose();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource().equals(chargeOverdue)){
			txtDailyFine.setEnabled(chargeOverdue.isSelected());
		}
	}
}