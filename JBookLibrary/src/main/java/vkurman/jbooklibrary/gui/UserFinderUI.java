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

package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;





import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.components.UsersTableModel;

/**
 * <code>JDialog</code> to find user.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UserFinderUI extends JDialog {
	private static final long serialVersionUID = -2310552073537966361L;
	private JButton okButton, cancelButton;
	private boolean okPressed;
	private TransObject obj;
	private JTable table;
	private UsersTableModel model;
	private List<User> foundUsers;
	
	/**
	 * Constructor.
	 * 
	 * @param obj
	 */
	public UserFinderUI(TransObject obj) {
		this.obj = obj;
		model = new UsersTableModel(null, GeneralStatus.ACTIVE);
		
		showUI();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param obj
	 * @param userName
	 */
	public UserFinderUI(TransObject obj, String userName) {
		this.obj = obj;
		this.foundUsers = new ArrayList<User>();
		if (userName != null) {
			if (this.findUser(userName)) {
				model = new UsersTableModel(foundUsers);
			} else {
				model = new UsersTableModel(null, GeneralStatus.ACTIVE);
				
				if(isStringDigit(userName)){
					UIDisplayManager.displayErrorMessage(
							this,
							"Can't find User with ID: " + userName);
				} else {
					UIDisplayManager.displayErrorMessage(
							this,
							"Can't find User with name: " + userName);
				}
			}
		} else {
			model = new UsersTableModel(null, GeneralStatus.ACTIVE);
		}
		
		showUI();
	}
	
	/**
	 * Sets and displays UI.
	 */
	private void showUI() {
		setTitle("User Finder");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		setLayout(new BorderLayout(10, 10));
		
		okPressed = false;
		
		JPanel content = new JPanel(new BorderLayout(0, 0));
		content.setBorder(BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Users: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		content.add(getTablePanel(), BorderLayout.CENTER);
		
		add(content, BorderLayout.CENTER);
		add(getButtonPane(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns <code>JPanel</code> with "OK" and
	 * "Cancel" buttons.
	 * 
	 * @return JPanel
	 */
	private JPanel getButtonPane(){
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		{
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					okPressed = true;
					dispose();
				}
			});
			
			// Control okButton visibility
			if(table.getSelectedRow() > -1) {
				okButton.setEnabled(true);
				getRootPane().setDefaultButton(okButton);
			} else {
				okButton.setEnabled(false);
				getRootPane().setDefaultButton(cancelButton);
			}
			
			buttonPane.add(okButton);
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					okPressed = false;
					dispose();
				}
			});
			buttonPane.add(cancelButton);
		}
		return buttonPane;
	}
	
	/**
	 * Creates and returns panel with table. Table contains found
	 * users.
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getTablePanel() {
		table = new JTable(model);
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(table.getSelectedRow() > -1) {
					// Set userObject to selected member
					obj.setObject((User) model.getUser(table.getSelectedRow()));
					
					okButton.setEnabled(true);
					getRootPane().setDefaultButton(okButton);
				} else {
					okButton.setEnabled(false);
					getRootPane().setDefaultButton(cancelButton);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(75);
        table.getColumnModel().getColumn(4).setPreferredWidth(300);
        table.getColumnModel().getColumn(5).setPreferredWidth(75);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	/**
	 * This method is adding found users with specified name or part
	 * of the name in the 'foundUsers' list and returns TRUE if at
	 * least one user has been found.
	 * 
	 * @param userName
	 * @return boolean
	 */
	private boolean findUser(String userName) {
		boolean found = false;
		// Checking if list is empty or not
		if (!foundUsers.isEmpty()) {
			foundUsers.clear();
		}
		
		// Checking if userName is actually user id
		if(isStringDigit(userName)){
			User u = AdminUsers.getInstance().getUser(Long.valueOf(userName));
			if(u != null){
				foundUsers.add(u);
				found = true;
			}
		} else {
			foundUsers.addAll(AdminUsers.getInstance().getUsers(userName));
			if(!foundUsers.isEmpty()){
				found = true;
			}
		}
		return found;
	}
	
	/**
	 * This method is checking if all characters in the String
	 * are digits.
	 * 
	 * @param string
	 * @return boolean
	 */
	private boolean isStringDigit(String string){
		int index = string.length();
		if(index > 0){
			for(int i = 0; i < index; i++){
				if(!Character.isDigit(string.charAt(i))){
					// Returning false if any character not digit
					return false;
				}
			}
			return true;
		} else {
			// Returning false if string length is 0
			return false;
		}
	}
	
	/**
	 * This method confirms if 'OK' button was pressed or not.
	 * 
	 * @return boolean
	 */
	public boolean isOKPressed() {
		return this.okPressed;
	}
}