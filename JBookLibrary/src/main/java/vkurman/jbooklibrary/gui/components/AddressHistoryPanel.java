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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import vkurman.jbooklibrary.core.Address;
import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.db.ParserSQLToObject;
import vkurman.jbooklibrary.gui.AddressUI;

/**
 * Panel that displays address history for specified
 * user.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AddressHistoryPanel extends JPanel {

	private static final long serialVersionUID = 3199836960486702408L;
	private JButton btnEdit;
	private JTable table;
	private AddressHistoryTableModel model;
	
	/**
	 * Constructor.
	 * 
	 * @param user
	 */
	public AddressHistoryPanel(User user) {
		model = new AddressHistoryTableModel(user);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setLayout(new BorderLayout(5, 5));
		setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Address History: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		
		add(getDataPane(), BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.PAGE_END);
	}
	
	/**
	 * Creates and returns panel with user address history.
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getDataPane() {
		table = new JTable(model);
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(table.getSelectedRow() > -1) {
					btnEdit.setEnabled(true);
				} else {
					btnEdit.setEnabled(false);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(60);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);
        table.getColumnModel().getColumn(6).setPreferredWidth(40);
        table.getColumnModel().getColumn(7).setPreferredWidth(60);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	/**
	 * Creates and returns panel with buttons that allow to
	 * perform actions on selected address.
	 * 
	 * @return JPanel
	 */
	private JPanel getButtonPanel(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TransObject object = new TransObject();
				object.setObject(model.getAddress(table.getSelectedRow()));
				
				AddressUI dialog = new AddressUI(object);
				
				if(dialog.isOKPressed()){
					if(AdminUsers.getInstance().updateAddress((Address) object.getObject())){
						model.fireTableRowsUpdated(table.getSelectedRow(), table.getSelectedRow());
					} else {
						JOptionPane.showMessageDialog(
								AddressHistoryPanel.this,
								"Can't update address record!",
								"Address History Panel error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panel.add(btnEdit);
		
		return panel;
	}
	
	/**
	 * Table model for address history table.
	 * 
	 * <p>Date created: 2013.07.28
	 * 
	 * @author Vassili Kurman
	 * @version 0.1
	 */
	private class AddressHistoryTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1324293405034299239L;
		private String[] columnNames = {
				"Flat Number",
				"House Name",
				"House Number",
				"Street",
				"City",
				"County",
				"Postcode",
				"Country"};
		private List<Address> addressHistory;
		
		/**
		 * Constructor.
		 * 
		 * @param user
		 */
		public AddressHistoryTableModel(User user) {
			addressHistory = new ArrayList<Address>();
			addressHistory.addAll(
					ParserSQLToObject.getInstance().getAddressesFromAddressHistory(
							user.getUserID()));
		}
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}
		
		@Override
		public int getRowCount() {
			return addressHistory.size();
		}
		
		@Override
		public Object getValueAt(int row, int col) {
			Address address = addressHistory.get(row);
			switch(col){
				case 0:
					return address.getFlatNumber();
				case 1:
					return address.getHouseName();
				case 2:
					return address.getHouseNumber();
				case 3:
					return address.getStreet();
				case 4:
					return address.getCity();
				case 5:
					return address.getCounty();
				case 6:
					return address.getPostcode();
				case 7:
					return address.getCountry();
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
		 * Returns address from specified row.
		 * 
		 * @param row
		 * @return Address
		 */
		public Address getAddress(int row){
			return addressHistory.get(row);
		}
	}
}