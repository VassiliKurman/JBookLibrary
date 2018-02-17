package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.AdminLoans;
import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.Action;
import vkurman.jbooklibrary.enums.ItemStatus;
import vkurman.jbooklibrary.gui.components.BooksTableModel;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;

/**
 * <code>BooksHandlerUI</code> extends <code>JDialog</code>
 * and allows to borrow, renew and return items to/from library.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BooksHandlerUI extends JDialog {

	private static final long serialVersionUID = 8991276525347186533L;
	private User user;
	private Action action;
	private JPanel contentPane;
	private JTable booksTable;
	private JButton findBookButton;
	private JTextField txtBook;
	
	/**
	 * Constructor that allows to borrow, renew or return books. Please
	 * note, that argument passed to constructor must be one of the
	 * following <code>Action</code>'s: <code>Reserve</code>, <code>Borrow</code>,
	 * <code>Renew</code> or <code>Return</code>.
	 */
	public BooksHandlerUI(Action action) {
		this.action = action;
		if(action != null){
			booksTable = new JTable(new BooksTableModel(new ArrayList<Book>()));
			
			switch(action){
				case RESERVE:
					displayPreScreen();
					break;
				case BORROW:
					displayPreScreen();
					break;
				case RENEW :
					showUI();
					break;
				case RETURN :
					showUI();
					break;
				default:
					UIDisplayManager.displayErrorMessage(
							this,
							"Invalid action passed!!!!");
					
					dispose();
			}
		} else {
			UIDisplayManager.displayErrorMessage(
					this,
					"Book is not specified!");
			
			dispose();
		}
	}
	
	/**
	 * If the case is borrowing the books, than this method displays
	 * input dialog to find user, who will be borrowing books.
	 */
	private void displayPreScreen(){
		String text = JOptionPane.showInputDialog(
				BooksHandlerUI.this,
				"Please enter borrower name or id:",
				"Name/ID input dialog",
				JOptionPane.PLAIN_MESSAGE);
		
		// If 'Cancel' button pressed on dialog, than 'null' is returned
		if(text != null){
			TransObject o = new TransObject();
			UserFinderUI finder = new UserFinderUI(o, text);
			
			if(finder.isOKPressed()){
				user = (User) o.getObject();
				if(user == null) dispose(); else showUI();
			} else {
				dispose();
			}
		} else {
			dispose();
		}
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		String title = (user == null) ?
				action.toString() :
					user.getName() + " " + action.toString().toLowerCase() + "s:";
		setTitle(title);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 10));
		setContentPane(contentPane);
		
		booksTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		booksTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		booksTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		booksTable.getColumnModel().getColumn(3).setPreferredWidth(400);
		booksTable.getColumnModel().getColumn(4).setPreferredWidth(400);
		booksTable.getColumnModel().getColumn(5).setPreferredWidth(80);
		booksTable.setFillsViewportHeight(true);
		
		contentPane.add(createSearchPanel(), BorderLayout.PAGE_START);
		contentPane.add(new JScrollPane(booksTable), BorderLayout.CENTER);
		contentPane.add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns <code>JPanel</code> to be able to search
	 * specified books.
	 * 
	 * @return JPanel
	 */
	private JPanel createSearchPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		
		panel.add(new JLabel("Book:", JLabel.TRAILING), BorderLayout.LINE_START);
		
		txtBook = new JTextField();
		panel.add(txtBook, BorderLayout.CENTER);
		txtBook.setColumns(10);
		txtBook.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				if(txtBook.getText().length() == AdminPrefs.BOOK_ID_LENGTH){
					try{
						long id = (Long.parseLong(txtBook.getText()));
						// Taking appropriate actions
						takeAction(AdminBooks.getInstance().findBookByID(id));
					} catch(NumberFormatException nfe){}
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		findBookButton = new JButton("Find Book");
		findBookButton.setActionCommand("findBook");
		findBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				// Trying to find book and taking appropriate actions
				takeAction(findBook());
			}
		});
		panel.add(findBookButton, BorderLayout.LINE_END);
		
		return panel;
	}
	
	/**
	 * Finds and returns book. If book not found, than <code>null</code>
	 * is returned.
	 * 
	 * @return Book
	 */
	private Book findBook() {
		Book book = null;
		try {
			ItemStatus status;
			
			switch(action){
				case RESERVE:
					status = ItemStatus.ONSHELF;
					break;
				case BORROW:
					status = ItemStatus.ONSHELF;
					break;
				case RENEW:
					status = ItemStatus.ONLOAN;
					break;
				case RETURN:
					status = ItemStatus.ONLOAN;
					break;
				default:
					status = null;
					break;
			}
			
			TransObject obj = new TransObject();
			BookFinderUI dialog = new BookFinderUI(
					obj,
					txtBook.getText(),
					status, false);
			
			if(dialog.isOKPressed()){
				book = (Book) obj.getObject();
			}
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					this,
					"Can't display BookFinderUI!");
		}
		
		return book;
	}
	
	/**
	 * Takes actions on specified book depending on what has
	 * been passed to constructor.
	 * 
	 * @param book
	 */
	private void takeAction(Book book){
		if(book != null){
			switch(action){
				case RESERVE:
					reserveBook(book);
					break;
				case BORROW:
					borrowBook(book);
					break;
				case RENEW:
					renewBook(book);
					break;
				case RETURN:
					returnBook(book);
					break;
			}
			// Reseting text field for book search
			txtBook.setText("");
		}
	}
	
	/**
	 * Reserving book.
	 * 
	 * @param book
	 */
	private void reserveBook(Book book){
		if (book != null) {
			// Checking that book is not in active loan list
			if(!AdminLoans.getInstance().isOnLoan(book)){
				if(commitReserveBookAction(book)){
					// Adding book into UI table
					((BooksTableModel) booksTable.getModel()).addBook(book);
					
					UIDisplayManager.displayInformationMessage(
							this,
							"Book '" + book.getTitle() + "' reserved successfully!");
				} else {
					UIDisplayManager.displayErrorMessage(
							this,
							"Error occur while trying to reserve the book!");
				}
			} else {
				UIDisplayManager.displayErrorMessage(
						this,
						"Book already is on loan!");
			}
		} else {
			UIDisplayManager.displayErrorMessage(
					this,
					"Book is not selected!");
		}
	}
	
	/**
	 * Borrowing book.
	 * 
	 * @param book
	 */
	private void borrowBook(Book book){
		if (book != null) {
			// Checking that book is not in active loan list
			if(!AdminLoans.getInstance().isOnLoan(book)){
				if(commitBorrowBookAction(book)){
					// Adding book into UI table
					((BooksTableModel) booksTable.getModel()).addBook(book);
					
					UIDisplayManager.displayInformationMessage(
							this,
							"Book " + book.getTitle() + " borrowed successfully!");
				} else {
					UIDisplayManager.displayErrorMessage(
							this,
							"Error occur while trying to borrow the book!");
				}
			} else {
				UIDisplayManager.displayErrorMessage(
						this,
						"Book already is on loan!");
			}
		} else {
			UIDisplayManager.displayErrorMessage(
					this,
					"Book is not selected!");
		}
	}
	
	/**
	 * Renewing the book.
	 * 
	 * @param book
	 */
	private void renewBook(Book book){
		if(book != null){
			if(AdminLoans.getInstance().isOnLoan(book)){
				if(commitRenewBookAction(book)){
					// Adding book into UI table
					((BooksTableModel) booksTable.getModel()).addBook(book);
					
					UIDisplayManager.displayInformationMessage(
							this,
							"Loan for book " + book.getTitle() + " renewed!");
				} else {
					UIDisplayManager.displayErrorMessage(
							this,
							"Error occur while trying to renew loan for book!");
				}
			} else {
				UIDisplayManager.displayErrorMessage(
						this,
						"Book is not on loan!");
			}
		} else {
			UIDisplayManager.displayErrorMessage(
					this,
					"Book is not selected!");
		}
	}
	
	/**
	 * Returning the book to library.
	 * 
	 * @param book
	 */
	private void returnBook(Book book){
		if(book != null){
			if(AdminLoans.getInstance().isOnLoan(book)){
				if(commitReturnBookAction(book)){
					// Adding book into UI table
					((BooksTableModel) booksTable.getModel()).addBook(book);
					
					UIDisplayManager.displayInformationMessage(
							this,
							"Book " + book.getTitle() + " returned!");
				} else {
					UIDisplayManager.displayErrorMessage(
							this,
							"Error occur while trying to return book!");
				}
			} else {
				UIDisplayManager.displayErrorMessage(
						this,
						"Book is not on loan!");
			}
		} else {
			UIDisplayManager.displayErrorMessage(
					this,
					"Book is not selected!");
		}
	}
	
	/**
	 * Requesting books administrator to borrow the book.
	 * 
	 * @return boolean
	 */
	private boolean commitReserveBookAction(Book book) {
		return AdminBooks.getInstance().reserveBook(book, user);
	}
	
	/**
	 * Requesting books administrator to borrow the book.
	 * 
	 * @return boolean
	 */
	private boolean commitBorrowBookAction(Book book) {
		return AdminBooks.getInstance().borrowBook(user, book);
	}
	
	/**
	 * Requesting books administrator to renew the book.
	 * 
	 * @return boolean
	 */
	private boolean commitRenewBookAction(Book book) {
		return AdminBooks.getInstance().renewLoan(book);
	}
	
	/**
	 * Requesting books administrator to return the book.
	 * 
	 * @return boolean
	 */
	private boolean commitReturnBookAction(Book book) {
		return AdminBooks.getInstance().returnBook(book);
	}
}