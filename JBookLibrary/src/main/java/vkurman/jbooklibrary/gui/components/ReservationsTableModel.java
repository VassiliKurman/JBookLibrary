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

import vkurman.jbooklibrary.core.AdminReservations;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Reservation;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * Table model for reservations table.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ReservationsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 6247398227594467843L;
	private String[] columnNames = {
			"Reservation ID",
			"User ID",
			"User Name",
			"Book ID",
			"Book Title",
			"Reserve Date",
			"Expire Date",
			"Status"};
	private List<Reservation> reservations;
	
	public ReservationsTableModel() {
		this(GeneralStatus.ACTIVE, null, null);
	}
	
	public ReservationsTableModel(GeneralStatus generalStatus, User user, Book book) {
		reservations = new ArrayList<Reservation>();
		retrieveData(generalStatus, user, book);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return reservations.size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Reservation reservation = reservations.get(row);
		switch(col){
			case 0:
				return reservation.getReservationID();
			case 1:
				return reservation.getUserID();
			case 2:
				return reservation.getUserName();
			case 3:
				return reservation.getBookID();
			case 4:
				return reservation.getBookTitle();
			case 5:
				return (reservation.getReserveDate() == null) ?
						null : BasicLibraryDateFormatter.formatDate(reservation.getReserveDate());
			case 6:
				return (reservation.getExpireDate() == null) ?
						null : BasicLibraryDateFormatter.formatDate(reservation.getExpireDate());
			case 7:
				return reservation.getGeneralStatus().toString();
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
	
	public void addReservation(Reservation reservation){
		if(reservation != null){
			int size = reservations.size();
			this.reservations.add(reservation);
			this.fireTableRowsInserted(size, size);
		}
	}
	
	public Reservation getReservation(int row){
		return reservations.get(row);
	}
	
	public void addReservations(List<Reservation> reservations){
		if(!reservations.isEmpty()){
			this.reservations.addAll(reservations);
			this.fireTableDataChanged();
		}
	}
	
	public void removeReservation(Reservation reservation){
		if(reservation != null){
			if (reservations.contains(reservation)) {
				int index = reservations.indexOf(reservation);
				this.reservations.remove(reservation);
				this.fireTableRowsDeleted(index, index);
			}
		}
	}
	
	private void retrieveData(GeneralStatus generalStatus, User user, Book book){
		if(generalStatus == GeneralStatus.ACTIVE){
			if(book == null && user == null){
				reservations = AdminReservations.getInstance().getAllActiveReservations();
			} else if(user != null){
				reservations = AdminReservations.getInstance().getActiveUserReservations(user);
			} else {
				reservations = AdminReservations.getInstance().getActiveBookReservations(book);
			}
		} else if(generalStatus == GeneralStatus.INACTIVE){
			if(book == null && user == null){
				reservations = AdminReservations.getInstance().getAllInactiveReservations();
			} else if(user != null){
				reservations = AdminReservations.getInstance().getInactiveUserReservations(user);
			} else {
				reservations = AdminReservations.getInstance().getInactiveBookReservations(book);
			}
		} else {
			if(book == null && user == null){
				reservations = AdminReservations.getInstance().getAllReservations();
			} else if(user != null){
				reservations = AdminReservations.getInstance().getAllUserReservations(user);
			} else {
				reservations = AdminReservations.getInstance().getAllBookReservations(book);
			}
		}
	}
	
	public void replaceData(GeneralStatus generalStatus, User user, Book book){
		this.reservations.clear();
		this.retrieveData(generalStatus, user, book);
		this.fireTableDataChanged();
	}
}