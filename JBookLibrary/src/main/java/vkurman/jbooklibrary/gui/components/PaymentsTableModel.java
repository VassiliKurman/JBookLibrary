package vkurman.jbooklibrary.gui.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import vkurman.jbooklibrary.core.AdminPayments;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.Payment;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * Table model for Payments table.
 *  
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class PaymentsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -3655699111636974391L;
	private String[] columnNames = {
			"Payment ID",
			"Fine ID",
			"User ID",
			"Name",
			"Value",
			"Paid"};
	private List<Payment> payments;
	
	/**
	 * Defaults constructor.
	 */
	public PaymentsTableModel() {
		this(null, null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param user
	 */
	public PaymentsTableModel(User user) {
		this(user, null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param fine
	 */
	public PaymentsTableModel(Fine fine) {
		this(null, fine);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param user
	 * @param fine
	 */
	public PaymentsTableModel(User user, Fine fine) {
		payments = new ArrayList<Payment>();
		retrieveData(user, fine);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return payments.size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Payment payment = payments.get(row);
		switch(col){
			case 0:
				return payment.getPaymentID();
			case 1:
				return payment.getFineID();
			case 2:
				return payment.getUserID();
			case 3:
				return payment.getUserName();
			case 4:
				return payment.getAmount().toString();
			case 5:
				if(payment.getPaymentDate() != null){
					return BasicLibraryDateFormatter.formatDate(payment.getPaymentDate());
				} else {
					return null;
				}
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
	 * Adds specified payment to the table list.
	 * 
	 * @param payment
	 */
	public void addPayment(Payment payment){
		if(payment != null){
			int size = payments.size();
			this.payments.add(payment);
			this.fireTableRowsInserted(size, size);
		}
	}
	
	/**
	 * Returns payment from specified row.
	 * 
	 * @param row
	 * @return Payment
	 */
	public Payment getPayment(int row){
		return payments.get(row);
	}
	
	/**
	 * Adds specified list of payments to table list.
	 * 
	 * @param payments
	 */
	public void addPayments(List<Payment> payments){
		if(!payments.isEmpty()){
			this.payments.addAll(payments);
			this.fireTableDataChanged();
		}
	}
	
	/**
	 * Removes specified payment from table.
	 * 
	 * @param payment
	 */
	public void removePayment(Payment payment){
		if(payment != null){
			if (payments.contains(payment)) {
				int index = payments.indexOf(payment);
				this.payments.remove(payment);
				this.fireTableRowsDeleted(index, index);
			}
		}
	}
	
	/**
	 * Retrieves data from database.
	 * 
	 * @param user
	 * @param fine
	 */
	private void retrieveData(User user, Fine fine){
		if(fine == null && user == null){
			payments = AdminPayments.getInstance().getAllPayments();
		} else if(user != null){
			payments = AdminPayments.getInstance().getUserPayments(user.getUserID());
		} else {
			payments = AdminPayments.getInstance().getFinePayments(fine.getFineID());
		}
	}
	
	/**
	 * Clears list of payments and requests new data from database.
	 * 
	 * @param user
	 * @param fine
	 */
	public void replaceData(User user, Fine fine){
		this.payments.clear();
		this.retrieveData(user, fine);
		this.fireTableDataChanged();
	}
}