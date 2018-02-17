package vkurman.jbooklibrary.gui.components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.AdminLoans;
import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.AdminReservations;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.enums.ItemStatus;
import vkurman.jbooklibrary.gui.BookDetailsUI;
import vkurman.jbooklibrary.gui.LoanDetailsUI;
import vkurman.jbooklibrary.gui.NewLoanUI;
import vkurman.jbooklibrary.gui.NewReservationUI;
import vkurman.jbooklibrary.gui.ReservationDetailsUI;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.gui.filters.ItemStatusFilterPanel;
import vkurman.jbooklibrary.interfaces.FilterObserver;

/**
 * <code>JPanel</code> that has a table of books and
 * search options.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BooksPanel extends JPanel implements FilterObserver, ActionListener {
	
	private static final long serialVersionUID = -7715773067320777294L;
	private JPanel searchResultsPanel;
	private JTable table;
	private TitledBorder titledBorder;
	private ItemStatusFilterPanel itemStatusFilterPanel;
	
	private JButton viewBookButton;
	private JButton reserveBookButton;
	private JButton viewReservationButton;
	private JButton cancelReservationButton;
	private JButton borrowBookButton;
	private JButton viewLoanButton;
	private JButton returnBookButton;
	
	private Book selectedBook;
	private BookAdvancedSearchPanel bookAdvancedSearchPanel;
	private BooksTableModel model;
	private ItemStatus itemStatus;
	
	/**
	 * Constructor.
	 */
	public BooksPanel() {
		selectedBook = null;
		model = new BooksTableModel();
		itemStatus = null;
		// Creating BookAdvancedSearchPanel and registering itself in it
		bookAdvancedSearchPanel = new BookAdvancedSearchPanel();
		bookAdvancedSearchPanel.register(this);
		// Creating StatusFilterPanel and registering itself in it
		itemStatusFilterPanel = new ItemStatusFilterPanel(itemStatus);
		itemStatusFilterPanel.register(this);
		
		// Creating titled border
		// Title border should be initialised after table model
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Search Results: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		// Creating search results panel
		// Search results panel should be initialised after titled border
		searchResultsPanel = getSearchResultsPanel();
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setLayout(new BorderLayout(5, 5));
		
		add(bookAdvancedSearchPanel, BorderLayout.PAGE_START);
		add(searchResultsPanel, BorderLayout.CENTER);
	}
	
	private JPanel getSearchResultsPanel(){
		JPanel panel= new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		panel.setBorder(titledBorder);
		
		panel.add(itemStatusFilterPanel, BorderLayout.PAGE_START);
		panel.add(getDataPane(), BorderLayout.CENTER);
		panel.add(getActionsButtonPane(), BorderLayout.PAGE_END);
		
		return panel;
	}
	
	private JScrollPane getDataPane() {
		table = new JTable(model);
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(table.getSelectedRow() > -1) {
					// Set book from selected row
					selectedBook = model.getBook(table.getSelectedRow());
					
					// Update table model
					updateModelIfNeeded();
					
					// Updating buttons state
					updateButtonsState();
				} else {
					// Update table model
					updateModelIfNeeded();
					
					// Updating buttons state
					updateButtonsState();
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(400);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(400);
        table.getColumnModel().getColumn(4).setPreferredWidth(400);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	/**
	 * Use this method if book details has been changed and model
	 * requires updating to reflect changes in details, i.e. book generalStatus
	 */
	private void updateModelIfNeeded(){
		if(selectedBook == null){
			return;
		}
		String actionCommand = itemStatusFilterPanel.getSelectionActionCommand();
		ItemStatus status = selectedBook.getStatus();
		
		if(actionCommand.equals("ALL")){
			if(status == ItemStatus.DISPOSED){
				model.removeBook(selectedBook);
				selectedBook = null;
			} else {
				model.fireTableRowsUpdated(table.getSelectedRow(), table.getSelectedRow());
			}
		} else if(actionCommand.equals("DISPOSED")){
			if(status != ItemStatus.DISPOSED){
				model.removeBook(selectedBook);
				selectedBook = null;
			} else {
				model.fireTableRowsUpdated(table.getSelectedRow(), table.getSelectedRow());
			}
		} else if(actionCommand.equals("ONLOAN")){
			if(status != ItemStatus.ONLOAN){
				model.removeBook(selectedBook);
				selectedBook = null;
			} else {
				model.fireTableRowsUpdated(table.getSelectedRow(), table.getSelectedRow());
			}
		} else if(actionCommand.equals("ONSHELF")){
			if(status != ItemStatus.ONSHELF){
				model.removeBook(selectedBook);
				selectedBook = null;
			} else {
				model.fireTableRowsUpdated(table.getSelectedRow(), table.getSelectedRow());
			}
		} else if(actionCommand.equals("RESERVED")){
			if(status != ItemStatus.RESERVED){
				model.removeBook(selectedBook);
				selectedBook = null;
			} else {
				model.fireTableRowsUpdated(table.getSelectedRow(), table.getSelectedRow());
			}
		} else if(actionCommand.equals("UNKNOWN")){
			if(status != ItemStatus.UNKNOWN){
				model.removeBook(selectedBook);
				selectedBook = null;
			} else {
				model.fireTableRowsUpdated(table.getSelectedRow(), table.getSelectedRow());
			}
		}
	}
	
	private void updateButtonsState(){
		if(selectedBook == null){
			viewBookButton.setEnabled(false);
			reserveBookButton.setEnabled(false);
			viewReservationButton.setEnabled(false);
			cancelReservationButton.setEnabled(false);
			borrowBookButton.setEnabled(false);
			viewLoanButton.setEnabled(false);
			returnBookButton.setEnabled(false);
			return;
		}
		// Checking if book reserved or disposed
		ItemStatus status = selectedBook.getStatus();
		switch(status){
			case RESERVED:
				viewBookButton.setEnabled(true);
				reserveBookButton.setEnabled(false);
				viewReservationButton.setEnabled(true);
				cancelReservationButton.setEnabled(true);
				borrowBookButton.setEnabled(true);
				viewLoanButton.setEnabled(false);
				returnBookButton.setEnabled(false);
				break;
			case ONLOAN:
				// Checking if book reserved to set buttons
				boolean enable = AdminReservations.getInstance().isReserved(selectedBook);
				
				viewBookButton.setEnabled(true);
				reserveBookButton.setEnabled(!enable);
				viewReservationButton.setEnabled(false);
				cancelReservationButton.setEnabled(enable);
				borrowBookButton.setEnabled(false);
				viewLoanButton.setEnabled(true);
				returnBookButton.setEnabled(true);
				break;
			case ONSHELF:
				viewBookButton.setEnabled(true);
				reserveBookButton.setEnabled(true);
				viewReservationButton.setEnabled(false);
				cancelReservationButton.setEnabled(false);
				borrowBookButton.setEnabled(true);
				viewLoanButton.setEnabled(false);
				returnBookButton.setEnabled(false);
				break;
			case DISPOSED:
				viewBookButton.setEnabled(true);
				reserveBookButton.setEnabled(false);
				viewReservationButton.setEnabled(false);
				cancelReservationButton.setEnabled(false);
				borrowBookButton.setEnabled(false);
				viewLoanButton.setEnabled(false);
				returnBookButton.setEnabled(false);
				break;
			case UNKNOWN:
				viewBookButton.setEnabled(true);
				reserveBookButton.setEnabled(false);
				viewReservationButton.setEnabled(false);
				cancelReservationButton.setEnabled(false);
				borrowBookButton.setEnabled(false);
				viewLoanButton.setEnabled(false);
				returnBookButton.setEnabled(false);
				break;
			default:
				viewBookButton.setEnabled(false);
				reserveBookButton.setEnabled(false);
				viewReservationButton.setEnabled(false);
				cancelReservationButton.setEnabled(false);
				borrowBookButton.setEnabled(false);
				viewLoanButton.setEnabled(false);
				returnBookButton.setEnabled(false);
				break;
		}
	}
	
	private JPanel getActionsButtonPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		viewBookButton = new JButton("View Book");
		viewBookButton.setActionCommand("view book");
		viewBookButton.setEnabled(false);
		viewBookButton.addActionListener(this);
		
		reserveBookButton = new JButton("Reserve Book");
		reserveBookButton.setActionCommand("reserve");
		reserveBookButton.setEnabled(false);
		reserveBookButton.addActionListener(this);
		
		viewReservationButton = new JButton("View Reservation");
		viewReservationButton.setActionCommand("view reservation");
		viewReservationButton.setEnabled(false);
		viewReservationButton.addActionListener(this);
		
		cancelReservationButton = new JButton("Cancel Reservation");
		cancelReservationButton.setActionCommand("cancel reservation");
		cancelReservationButton.setEnabled(false);
		cancelReservationButton.addActionListener(this);
		
		borrowBookButton = new JButton("Borrow Book");
		borrowBookButton.setActionCommand("borrow");
		borrowBookButton.setEnabled(false);
		borrowBookButton.addActionListener(this);
		
		viewLoanButton = new JButton("View Loan");
		viewLoanButton.setActionCommand("view loan");
		viewLoanButton.setEnabled(false);
		viewLoanButton.addActionListener(this);
		
		returnBookButton = new JButton("Return Book");
		returnBookButton.setActionCommand("return");
		returnBookButton.setEnabled(false);
		returnBookButton.addActionListener(this);
		
		panel.add(viewBookButton);
		panel.add(reserveBookButton);
		panel.add(viewReservationButton);
		panel.add(cancelReservationButton);
		panel.add(borrowBookButton);
		panel.add(viewLoanButton);
		panel.add(returnBookButton);
		
		return panel;
	}
	
	private void viewBook(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookDetailsUI dialog = new BookDetailsUI(selectedBook);
					if(!dialog.isShowing()){
						// Update table model
						updateModelIfNeeded();
						
						// Updating buttons UI
						updateButtonsState();
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							BooksPanel.this,
							"Can't display BookDetailsUI!");
				}
			}
		});
	}
	
	private void reserveBook(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewReservationUI dialog = new NewReservationUI(selectedBook);
					if(!dialog.isShowing()){
						// Update table model
						updateModelIfNeeded();
						
						// Updating buttons UI
						updateButtonsState();
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							BooksPanel.this,
							"Can't reserve book!");
				}
			}
		});
	}
	
	private void viewReservation(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ReservationDetailsUI(
							AdminReservations.getInstance().getActiveReservation(selectedBook));
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							BooksPanel.this,
							"Can't view reservation!");
				}
			}
		});
	}
	
	private void cancelReservation(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(AdminReservations.getInstance().remove(selectedBook)){
						// Update table model
						updateModelIfNeeded();
						
						// Updating buttons UI
						updateButtonsState();
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							BooksPanel.this,
							"Can't cancel reservation!");
				}
			}
		});
	}
	
	private void borrowBook(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewLoanUI dialog = new NewLoanUI(selectedBook);
					if(dialog.isOkPressed()){
						// Update table model
						updateModelIfNeeded();
						
						// Updating buttons UI
						updateButtonsState();
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							BooksPanel.this,
							"Can't borrow book!");
				}
			}
		});
	}
	
	private void viewLoan(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoanDetailsUI(
							AdminLoans.getInstance().getBookActiveLoan(
									selectedBook.getBookID()));
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							BooksPanel.this,
							"Can't view loan!");
				}
			}
		});
	}
	
	private void returnBook(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(AdminBooks.getInstance().returnBook(selectedBook)){
						UIDisplayManager.displayInformationMessage(
								BooksPanel.this,
								"Book "+selectedBook.getTitle()+" returned!");
						
						// Update table model
						updateModelIfNeeded();
						
						// Updating buttons UI
						updateButtonsState();
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							BooksPanel.this,
							"Can't return book!");
				}
			}
		});
	}
	
	@Override
	public void update(String className, Object arg) {
		if(className.equals("BookAdvancedSearchPanel")){
			if(arg != null){
				itemStatusFilterPanel.setEnabled(false);
				
				@SuppressWarnings("unchecked")
				List<Book> list = (List<Book>) arg;
				
				model.replaceData(list);
			} else {
				itemStatusFilterPanel.setEnabled(true);
				
				model.replaceData(itemStatus, false);
			}
		} else {
			itemStatus = (ItemStatus) arg;
			
			model.replaceData(itemStatus, false);
		}
		
		// Updating titled border
		updateTitledBorder();
	}
	
	/**
	 * This method is updating title in the titled border.
	 */
	private void updateTitledBorder(){
		// Setting title
		titledBorder.setTitle(
				"Search Results: "+model.getRowCount()+" records found");
		
		// Updating UI
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equalsIgnoreCase("view book")){
			viewBook();
			return;
		}
		if (action.equalsIgnoreCase("reserve")) {
			reserveBook();
			return;
		}
		if (action.equalsIgnoreCase("view reservation")) {
			viewReservation();
			return;
		}
		if (action.equalsIgnoreCase("cancel reservation")) {
			cancelReservation();
			return;
		}
		if (action.equalsIgnoreCase("borrow")) {
			borrowBook();
			return;
		}
		if (action.equalsIgnoreCase("view loan")) {
			viewLoan();
			return;
		}
		if (action.equalsIgnoreCase("return")) {
			returnBook();
			return;
		}
	}
}