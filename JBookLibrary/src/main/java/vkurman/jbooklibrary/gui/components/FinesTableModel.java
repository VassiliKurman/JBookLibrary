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

package vkurman.jbooklibrary.gui.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import vkurman.jbooklibrary.core.AdminFines;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.enums.GeneralStatus;

/**
 * Fines table model for fines table.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class FinesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 6247398227594467843L;
	private String[] columnNames = {
			"Fine ID",
			"Loan ID",
			"User ID",
			"User Name",
			"Total",
			"Paid",
			"Status"};
	private List<Fine> fines;
	
	/**
	 * Default constructor.
	 */
	public FinesTableModel() {
		this(GeneralStatus.ACTIVE, 0L, 0L);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param generalStatus
	 * @param loanID
	 * @param userID
	 */
	public FinesTableModel(GeneralStatus generalStatus, long loanID, long userID) {
		fines = new ArrayList<Fine>();
		retrieveData(generalStatus, loanID, userID);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return fines.size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Fine fine = fines.get(row);
		switch(col){
			case 0:
				return fine.getFineID();
			case 1:
				return fine.getLoanID();
			case 2:
				return fine.getUserID();
			case 3:
				return fine.getUserName();
			case 4:
				return fine.getTotal().toString();
			case 5:
				return fine.getValuePaid().toString();
			case 6:
				return fine.getStatus().toString();
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
	 * Adds fine to the model.
	 * 
	 * @param fine
	 */
	public void addFine(Fine fine){
		if(fine != null){
			int size = fines.size();
			this.fines.add(fine);
			this.fireTableRowsInserted(size, size);
		}
	}
	
	/**
	 * Returns fine in specified row.
	 * 
	 * @param row
	 * @return Fine
	 */
	public Fine getFine(int row){
		return fines.get(row);
	}
	
	/**
	 * Adds fines to the model.
	 * 
	 * @param fines
	 */
	public void addFines(List<Fine> fines){
		if(!fines.isEmpty()){
			this.fines.addAll(fines);
			this.fireTableDataChanged();
		}
	}
	
	/**
	 * Removes specified fine from the model.
	 * 
	 * @param fine
	 */
	public void removeFine(Fine fine){
		if(fine != null){
			if (fines.contains(fine)) {
				int index = fines.indexOf(fine);
				this.fines.remove(fine);
				this.fireTableRowsDeleted(index, index);
			}
		}
	}
	
	/**
	 * Retrieves fines from database.
	 * 
	 * @param generalStatus
	 * @param loanID
	 * @param userID
	 */
	private void retrieveData(GeneralStatus generalStatus, long loanID, long userID){
		if(generalStatus == GeneralStatus.ACTIVE){
			if(loanID == 0L && userID == 0L){
				fines = AdminFines.getInstance().getAllActiveFines();
			} else if(userID != 0L){
				fines = AdminFines.getInstance().getAllActiveUserFines(userID);
			} else {
				fines = AdminFines.getInstance().getAllActiveLoanFines(loanID);
			}
		} else if(generalStatus == GeneralStatus.INACTIVE){
			if(loanID == 0L && userID == 0L){
				fines = AdminFines.getInstance().getAllInactiveFines();
			} else if(userID != 0L){
				fines = AdminFines.getInstance().getAllInactiveUserFines(userID);
			} else {
				fines = AdminFines.getInstance().getAllInactiveLoanFines(loanID);
			}
		} else {
			if(loanID == 0L && userID == 0L){
				fines = AdminFines.getInstance().getAllFines();
			} else if(userID != 0L){
				fines = AdminFines.getInstance().getAllUserFines(userID);
			} else {
				fines = AdminFines.getInstance().getAllLoanFines(loanID);
			}
		}
	}
	
	/**
	 * Refreshes fines in the model.
	 * 
	 * @param generalStatus
	 * @param loanID
	 * @param userID
	 */
	public void replaceData(GeneralStatus generalStatus, long loanID, long userID){
		fines.clear();
		retrieveData(generalStatus, loanID, userID);
		fireTableDataChanged();
	}
}