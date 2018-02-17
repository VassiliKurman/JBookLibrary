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
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;




import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.Borrower;
import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.UsersPanel;

/**
 * Users administrator UI.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UserAdminUI extends JDialog {
	private static final long serialVersionUID = -8560377082410628936L;
	private UsersPanel usersPanel;
	
	/**
	 * Constructor
	 */
	public UserAdminUI() {
		usersPanel = new UsersPanel();
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("User Administration UI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		setLayout(new BorderLayout(5, 5));
		getContentPane().add(getActionsButtonPanel(), BorderLayout.PAGE_START);
		getContentPane().add(usersPanel, BorderLayout.CENTER);
		getContentPane().add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with button to register new user.
	 * 
	 * @return JPanel
	 */
	private JPanel getActionsButtonPanel(){
		JPanel wraper = new JPanel();
		
		JButton newButton = new JButton("Register New User");
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					NewUserUI dialog = new NewUserUI();
					if(dialog.isOkPressed()){
						String userType = null;
						if(dialog.isBorrowerSelected()){
							userType = "Borrower";
						} else {
							userType = "Librarian";
						}
						
						addUser(
								userType,
								dialog.getFirstname(),
								dialog.getMiddlename(),
								dialog.getSurname());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		wraper.add(newButton);
		
		return wraper;
	}
	
	/**
	 * Registering new user.
	 * 
	 * @param userType
	 * @param firstname
	 * @param middlename
	 * @param surname
	 */
	private void addUser(String userType, String firstname, String middlename, String surname){
		final boolean isBorrower;
		if(userType.equals("Borrower")){
			isBorrower = true;
		} else {
			isBorrower = false;
		}
		
		final User user;
		if(isBorrower){
			user = new Borrower();
		} else {
			user = new Librarian();
		}
		
		user.setFirstname(firstname);
		user.setMiddlename(middlename);
		user.setSurname(surname);
		
		// Updating database
		new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				if(isBorrower){
					AdminUsers.getInstance().addBorrower((Borrower) user);
				} else {
					AdminUsers.getInstance().addLibrarian((Librarian) user);
				}
				
				return null;
			}
		}.execute();
		
		// Updating UI text field
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserDetailsUI dialog = new UserDetailsUI(user);
					if(!dialog.isShowing()){
						// Adding user to users panel
						usersPanel.addUser(user);
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							UserAdminUI.this,
							"Can't display UserDetailsUI!");
				}
			}
		});
	}
}