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
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * <code>JDialog</code> that take input for new user firstname,
 * middle name, surname and a role (<code>Borrower</code> or
 * <code>Librarian</code>).
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class NewUserUI extends JDialog implements ActionListener {

	private static final long serialVersionUID = 8776242307916014937L;
	private JButton okButton, cancelButton;
	private boolean okPressed, isBorrower;
	private String firstname, middlename, surname;
	private JTextField txtFirstname, txtMiddlename, txtSurname;
	
	/**
	 * Constructor
	 */
	public NewUserUI() {
		this.firstname = null;
		this.middlename = null;
		this.surname = null;
		this.okPressed = false;
		this.isBorrower = true;
		
		txtFirstname = new JTextField(20);
		txtMiddlename = new JTextField(20);
		txtSurname = new JTextField(20);
		
		showUI();
	}
	
	/**
	 * Creates and displays GUI.
	 */
	private void showUI(){
		setTitle("New User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(10, 10));
		
		getContentPane().add(this.getContentPanel(), BorderLayout.CENTER);
		getContentPane().add(this.getButtonPane(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns main content panel.
	 * 
	 * @return JPanel
	 */
	private JPanel getContentPanel(){
		JPanel rolePanel = new JPanel();
		
		//Create the radio buttons.
	    JRadioButton borrowerButton = new JRadioButton("Borrower");
	    borrowerButton.setMnemonic(KeyEvent.VK_B);
	    borrowerButton.setActionCommand("Borrower");
	    borrowerButton.setSelected(true);

	    JRadioButton librarianButton = new JRadioButton("Librarian");
	    librarianButton.setMnemonic(KeyEvent.VK_L);
	    librarianButton.setActionCommand("Librarian");

	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(borrowerButton);
	    group.add(librarianButton);

	    //Register a listener for the radio buttons.
	    borrowerButton.addActionListener(this);
	    librarianButton.addActionListener(this);
	    
	    rolePanel.add(borrowerButton);
	    rolePanel.add(librarianButton);
		
		JPanel panel = new JPanel(new SpringLayout());
		
		panel.add(new JLabel("Firstname:", JLabel.TRAILING));
		panel.add(txtFirstname);
		panel.add(new JLabel("Middle name:", JLabel.TRAILING));
		panel.add(txtMiddlename);
		panel.add(new JLabel("Surname:", JLabel.TRAILING));
		panel.add(txtSurname);
		panel.add(new JLabel("Role:", JLabel.TRAILING));
		panel.add(rolePanel);
		
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(
				panel,
				4, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
	
	/**
	 * Creates and returns button panel
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
					if(validateInput()) dispose();
				}
			});
			buttonPane.add(okButton);
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					dispose();
				}
			});
			buttonPane.add(cancelButton);
		}
		return buttonPane;
	}
	
	/**
	 * Validating input.
	 * 
	 * @return boolean
	 */
	private boolean validateInput(){
		boolean valid = false;
		
		if(txtFirstname.getText().length() > 0){
			if(txtSurname.getText().length() > 0){
				firstname = txtFirstname.getText();
				middlename = txtMiddlename.getText();
				surname = txtSurname.getText();
				
				okPressed = true;
				valid = true;
			} else {
				// Displaying error message
				UIDisplayManager.displayErrorMessage(
						NewUserUI.this,
						"Please enter user surname!");
			}
		} else {
			// Displaying error message
			UIDisplayManager.displayErrorMessage(
					NewUserUI.this,
					"Please enter user firstname!");
		}
		
		return valid;
	}
	
	/**
	 * Returns <code>true</code> is "OK" button was pressed.
	 * 
	 * @return boolean
	 */
	public boolean isOkPressed() {
		return okPressed;
	}
	
	/**
	 * Returns <code>true</code> if "borrower" is selected.
	 * 
	 * @return boolean
	 */
	public boolean isBorrowerSelected() {
		return isBorrower;
	}
	
	/**
	 * Getter for firstname.
	 * 
	 * @return String
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Getter for middle name
	 * 
	 * @return String
	 */
	public String getMiddlename() {
		return middlename;
	}
	
	/**
	 * Getter for surname
	 * 
	 * @return String
	 */
	public String getSurname() {
		return surname;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Borrower") {
			isBorrower = true;
	    } else {
	        isBorrower = false;
	    }
	}
}