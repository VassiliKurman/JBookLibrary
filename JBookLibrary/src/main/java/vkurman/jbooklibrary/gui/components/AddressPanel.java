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
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import vkurman.jbooklibrary.core.Address;
import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.gui.AddressHistoryUI;
import vkurman.jbooklibrary.gui.AddressUI;
import vkurman.jbooklibrary.gui.UIDisplayManager;

/**
 * Panel that displays current <code>Address</code> for
 * specified user as well, as address history.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AddressPanel extends JPanel {
	
	private static final long serialVersionUID = -1627878792933166752L;
	private User user;
	private JButton newAddressButton, addressHistoryButton;
	private JTextArea txtAddress;
	
	/**
	 * Constructor.
	 * 
	 * @param user
	 */
	public AddressPanel(User user) {
		this.user = user;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI() {
		setLayout(new BorderLayout(5, 5));
		setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Address", TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		setPreferredSize(new Dimension(400, 160));
		
		//Add components to panel
		add(getAddressTextArea(), BorderLayout.CENTER);
		add(getAddressButtonPanel(), BorderLayout.LINE_END);
	}
	
	/**
	 * Creates and displays panel with buttons to add new
	 * <code>Address</code> and view address history.
	 * 
	 * @return JPanel
	 */
	private JPanel getAddressButtonPanel(){
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new FlowLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1, 5, 5));
		
		{
			newAddressButton = new JButton("New Address");
			newAddressButton.setActionCommand("newAddress");
			newAddressButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					addNewAddress();
				}
			});
			
			addressHistoryButton = new JButton("Address History");
			addressHistoryButton.setActionCommand("addressHistory");
			addressHistoryButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					viewAddressHistory();
				}
			});
		    
		    //Add buttons to panel
		    buttonPanel.add(newAddressButton);
		    buttonPanel.add(addressHistoryButton);
		}
		bPanel.add(buttonPanel);
		
		return bPanel;
	}
	
	private JScrollPane getAddressTextArea(){
		txtAddress = new JTextArea(7, 20);
		if(user.getCurrentAddress() != null){
			txtAddress.setText(user.getCurrentAddress().toPost());
		}
		JScrollPane scrollPane = new JScrollPane(txtAddress); 
		txtAddress.setEditable(false);
		
		return scrollPane;
	}
	
	/**
	 * This method displays dialog asking to enter data for new
	 * address and setting that address data for current user.
	 */
	private void addNewAddress() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransObject obj = new TransObject();
					AddressUI dialog = new AddressUI(obj);
					
					if(dialog.isOKPressed()){
						if(AdminUsers.getInstance().newAddress(
								user, (Address) obj.getObject())){
										txtAddress.setText(
												user.getCurrentAddress().toPost());
						} else {
							UIDisplayManager.displayErrorMessage(
									AddressPanel.this,
									"Can't add new address!");
						}
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							AddressPanel.this,
							"Can't display AddressUI!");
				}
			}
		});
	}
	
	/**
	 * Displays address history UI.
	 */
	private void viewAddressHistory() {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					if(!(new AddressHistoryUI(user).isShowing())){
						if(user.getCurrentAddress() != null){
							// When address history UI is closed, than retrieving
							// user current address from database and setting it
							// in user object
							long addressID = user.getCurrentAddress().getAddressID();
							if(addressID > 0L){
								Address address = AdminUsers.getInstance().getAddress(addressID);
								if(address != null){
									user.setAddress(address);
									
									// Updating text area with user current address
									EventQueue.invokeLater(new Runnable() {
										@Override
										public void run() {
											txtAddress.setText(user.getCurrentAddress().toPost());
										}
									});
								}
							}
						}
					}
				}
			});
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					AddressPanel.this,
					"Can't display AddressHistoryUI!");
		}
	}
}