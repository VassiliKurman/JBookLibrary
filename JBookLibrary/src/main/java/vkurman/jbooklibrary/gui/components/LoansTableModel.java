package vkurman.jbooklibrary.gui.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import vkurman.jbooklibrary.core.AdminLoans;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Loan;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * Table model for loans table.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class LoansTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6225545162647378264L;
	private String[] columnNames = {
			"Loan ID",
			"Book ID",
			"Book Title",
			"User ID",
			"User Name",
			"Borrowed",
			"Due Back",
			"Returned",
			"Status"};
	private List<Loan> loans;
	
	/**
	 * Constructor that has main purpose only to display
	 * loans in the table. If model for table is created
	 * using this constructor, than no data is transmitted
	 * between application and database, and only borrowed
	 * list of current session loans are displayed.
	 * 
	 * @param list
	 */
	public LoansTableModel(List<Loan> list){
		loans = list;
	}
	
	/**
	 * Constructor that requests all <code>ACTIVE</code>
	 * loans.
	 */
	public LoansTableModel() {
		this(GeneralStatus.ACTIVE, null, null);
	}
	
	/**
	 * Main constructor that requests specified loans.
	 * 
	 * @param generalStatus
	 * @param user
	 * @param book
	 */
	public LoansTableModel(GeneralStatus generalStatus, User user, Book book) {
		loans = new ArrayList<Loan>();
		retrieveData(generalStatus, user, book);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return loans.size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Loan loan = loans.get(row);
		switch(col){
			case 0:
				return loan.getLoanID();
			case 1:
				return loan.getBookID();
			case 2:
				return loan.getBookTitle();
			case 3:
				return loan.getUserID();
			case 4:
				return loan.getUserName();
			case 5:
				return (loan.getBorrowDate() == null) ?
					null : BasicLibraryDateFormatter.formatDate(loan.getBorrowDate());
			case 6:
				return (loan.getDueDate() == null) ?
					null : BasicLibraryDateFormatter.formatDate(loan.getDueDate());
			case 7:
				return (loan.getReturnDate() == null)?
					null : BasicLibraryDateFormatter.formatDate(loan.getReturnDate());
			case 8:
				return loan.getStatus().toString();
			default:
				return null;
		}
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		return (getValueAt(0, c) == null) ? String.class : getValueAt(0, c).getClass();
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	/**
	 * Adds specified loan to the model.
	 * 
	 * @param loan
	 */
	public void addLoan(Loan loan){
		if(loan != null){
			int size = loans.size();
			this.loans.add(loan);
			this.fireTableRowsInserted(size, size);
		}
	}
	
	/**
	 * Returns loan from specified row.
	 * 
	 * @param row
	 * @return Loan
	 */
	public Loan getLoan(int row){
		return loans.get(row);
	}
	
	/**
	 * Adds list of loans to the model.
	 * 
	 * @param loans
	 */
	public void addLoans(List<Loan> loans){
		if(!loans.isEmpty()){
			this.loans.addAll(loans);
			this.fireTableDataChanged();
		}
	}
	
	/**
	 * Removes specified loan from model.
	 * 
	 * @param loan
	 */
	public void removeLoan(Loan loan){
		if(loan != null){
			if (loans.contains(loan)) {
				int index = loans.indexOf(loan);
				this.loans.remove(loan);
				this.fireTableRowsDeleted(index, index);
			}
		}
	}
	
	/**
	 * Retrieves specified data from database.
	 * 
	 * @param generalStatus
	 * @param user
	 * @param book
	 */
	private void retrieveData(GeneralStatus generalStatus, User user, Book book){
		if(generalStatus == GeneralStatus.ACTIVE){
			if(book == null && user == null){
				loans = AdminLoans.getInstance().getAllActiveLoans();
			} else if(user != null){
				loans = AdminLoans.getInstance().getAllUserActiveLoans(user.getUserID());
			} else {
				loans = AdminLoans.getInstance().getAllBookActiveLoans(book.getBookID());
			}
		} else if(generalStatus == GeneralStatus.INACTIVE){
			if(book == null && user == null){
				loans = AdminLoans.getInstance().getAllInactiveLoans();
			} else if(user != null){
				loans = AdminLoans.getInstance().getAllUserInactiveLoans(user.getUserID());
			} else {
				loans = AdminLoans.getInstance().getAllBookInactiveLoans(book.getBookID());
			}
		} else {
			if(book == null && user == null){
				loans = AdminLoans.getInstance().getAllLoans();
			} else if(user != null){
				loans = AdminLoans.getInstance().getAllUserLoans(user.getUserID());
			} else {
				loans = AdminLoans.getInstance().getAllBookLoans(book.getBookID());
			}
		}
	}
	
	/**
	 * Replaces all data in the model.
	 * 
	 * @param generalStatus
	 * @param user
	 * @param book
	 */
	public void replaceData(GeneralStatus generalStatus, User user, Book book){
		this.loans.clear();
		this.retrieveData(generalStatus, user, book);
		this.fireTableDataChanged();
	}
}