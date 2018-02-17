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

import vkurman.jbooklibrary.core.AdminIDCards;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * Table model for <code>IDCard</code> table.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDCardsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -7692158328296794032L;
	private String[] columnNames = {
			"IDCard ID",
			"User ID",
			"User Name",
			"Valid From",
			"Valid To",
			"Deactivated",
			"Reason",
			"Status"};
	private List<IDCard> idCards;
	
	public IDCardsTableModel() {
		this(GeneralStatus.ACTIVE, 0L);
	}
	
	public IDCardsTableModel(GeneralStatus generalStatus, long userID) {
		idCards = new ArrayList<IDCard>();
		retrieveData(generalStatus, userID);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return idCards.size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		IDCard idCard = idCards.get(row);
		switch(col){
			case 0:
				return idCard.getCardID();
			case 1:
				return idCard.getUserID();
			case 2:
				return idCard.getUserName();
			case 3:
				return BasicLibraryDateFormatter.formatDateExtended(idCard.getValidFrom());
			case 4:
				return BasicLibraryDateFormatter.formatDateExtended(idCard.getValidTo());
			case 5:
				if(idCard.getDeactivationDate() != null){
					return BasicLibraryDateFormatter.formatDateExtended(idCard.getDeactivationDate());
				} else {
					return null;
				}
			case 6:
				if(idCard.getDeactivationReason() != null){
					return idCard.getDeactivationReason().toString();
				} else {
					return null;
				}
			case 7:
				return idCard.getGeneralStatus().toString();
			default:
				return null;
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
	
	public void addIDCard(IDCard idCard){
		if(idCard != null){
			int size = idCards.size();
			this.idCards.add(idCard);
			this.fireTableRowsInserted(size, size);
		}
	}
	
	public IDCard getIDCard(int row){
		return idCards.get(row);
	}
	
	public void addIDCards(List<IDCard> idCards){
		if(!idCards.isEmpty()){
			this.idCards.addAll(idCards);
			this.fireTableDataChanged();
		}
	}
	
	public void removeIDCard(IDCard idCard){
		if(idCard != null){
			if (idCards.contains(idCards)) {
				int index = idCards.indexOf(idCard);
				this.idCards.remove(idCard);
				this.fireTableRowsDeleted(index, index);
			}
		}
	}
	
	private void retrieveData(GeneralStatus generalStatus, long userID){
		if(generalStatus == GeneralStatus.ACTIVE){
			if(userID != 0L){
				idCards = AdminIDCards.getInstance().getAllActiveIDCards(userID);
			} else {
				idCards = AdminIDCards.getInstance().getAllActiveIDCards();
			}
		} else if(generalStatus == GeneralStatus.INACTIVE){
			if(userID != 0L){
				idCards = AdminIDCards.getInstance().getAllInactiveIDCards(userID);
			} else {
				idCards = AdminIDCards.getInstance().getAllInactiveIDCards();
			}
		} else {
			if(userID != 0L){
				idCards = AdminIDCards.getInstance().getAllIDCards(userID);
			} else {
				idCards = AdminIDCards.getInstance().getAllIDCards();
			}
		}
	}
	
	public void replaceData(GeneralStatus generalStatus, long userID){
		this.idCards.clear();
		this.retrieveData(generalStatus, userID);
		this.fireTableDataChanged();
	}
}