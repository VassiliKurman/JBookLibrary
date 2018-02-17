package vkurman.jbooklibrary.gui.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.Borrower;
import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * Table model for users table.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UsersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -750308353875920353L;
	private String[] columnNames = {
			"User ID",
			"Has IDCard",
			"Name",
			"D.O.B.",
			"Address",
			"Status"};
	private List<Object> users;
	
	public UsersTableModel(List<User> users) {
		this.users = new ArrayList<Object>();
		this.users.addAll(users);
	}
	
	public UsersTableModel() {
		this("Borrower", GeneralStatus.ACTIVE);
	}
	
	public UsersTableModel(String userType, GeneralStatus generalStatus) {
		users = new ArrayList<Object>();
		this.retrieveData(userType, generalStatus);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return users.size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		String tempType = this.getUser(row).getClass().getSimpleName();
		
		if(tempType.equals("Borrower")) return getBorrowerValueAt(row, col);
		if(tempType.equals("Librarian")) return getLibrarianValueAt(row, col);
		
		return null;
	}
	
	private Object getBorrowerValueAt(int row, int col) {
		Borrower user = (Borrower) users.get(row);
		switch(col){
			case 0: return user.getUserID();
			case 1:	return user.hasIDCard();
			case 2:	return user.getName();
			case 3: return (user.getDob() == null) ?
					null : BasicLibraryDateFormatter.formatDate(user.getDob());
			case 4:	return user.getCurrentAddress();
			case 5:	return user.getGeneralStatus().toString();
			default: return null;
		}
	}
	
	private Object getLibrarianValueAt(int row, int col) {
		Librarian user = (Librarian) users.get(row);
		switch(col){
			case 0: return user.getUserID();
			case 1:	return user.hasIDCard();
			case 2:	return user.getName();
			case 3: return (user.getDob() == null) ?
					null : BasicLibraryDateFormatter.formatDate(user.getDob());
			case 4:	return user.getCurrentAddress();
			case 5:	return user.getGeneralStatus().toString();
			default: return null;
		}
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public Class<?> getColumnClass(int c) {
		return (getValueAt(0, c) == null) ? String.class : getValueAt(0, c).getClass();
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	/**
	 * Adds user to table model and updates table.
	 * 
	 * @param user
	 */
	public void addUser(User user){
		if(user != null){
			int size = users.size();
			this.users.add(user);
			this.fireTableRowsInserted(size, size);
		}
	}
	
	/**
	 * Returns user on specified row.
	 * 
	 * @param row
	 * @return Object
	 */
	public Object getUser(int row){
		return users.get(row);
	}
	
	/**
	 * Adds users to table model and updates table.
	 * 
	 * @param users
	 */
	public void addUsers(List<User> users){
		if(!users.isEmpty()){
			this.users.addAll(users);
			fireTableDataChanged();
		}
	}
	
	/**
	 * Removes user from table and updates table.
	 * 
	 * @param user
	 */
	public void removeUser(User user){
		if(user != null){
			if (users.contains(user)) {
				int index = users.indexOf(user);
				this.users.remove(user);
				fireTableRowsDeleted(index, index);
			}
		}
	}
	
	/**
	 * Updates user details in the table ONLY.
	 * 
	 * @param user
	 */
	public void updateUser(User user){
		if(user != null){
			if (users.contains(user)) {
				int index = users.indexOf(user);
				fireTableRowsUpdated(index, index);
			}
		}
	}
	
	private void retrieveData(String userType, GeneralStatus generalStatus){
		if(userType != null){
			if(userType.equals("Borrower")) {
					Iterator<Borrower> iterB = AdminUsers.getInstance().getBorrowers(generalStatus).iterator();
					while(iterB.hasNext()){
						users.add(iterB.next());
					}
			}
			if(userType.equals("Librarian")){
					Iterator<Librarian> iterL = AdminUsers.getInstance().getLibrarians(generalStatus).iterator();
					while(iterL.hasNext()){
						users.add(iterL.next());
					}
			}
		} else {
			Iterator<User> iterU = AdminUsers.getInstance().getUsers(generalStatus).iterator();
			while(iterU.hasNext()){
				users.add(iterU.next());
			}
		}
		
	}
	
	public void replaceData(User user){
		this.users.clear();
		this.users.add(user);
		this.fireTableDataChanged();
	}
	
	public void replaceData(String userType, GeneralStatus generalStatus){
		this.users.clear();
		this.retrieveData(userType, generalStatus);
		this.fireTableDataChanged();
	}
}