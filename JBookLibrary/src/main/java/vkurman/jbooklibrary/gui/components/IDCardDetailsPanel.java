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
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import vkurman.jbooklibrary.core.AdminIDCards;
import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.IDCardDeactivationReasonDialog;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.print.PrintingManager;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * Panel that displays details about specified <code>IDCard</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDCardDetailsPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 8630098881955628276L;
	private JTextField txtUserID, txtUserName, txtIDCardStatus, txtIDCardValidFrom,
		txtIDCardValidTo, txtDeactivationDate, txtDeactivationReason;
	private JButton btnDeactivate;
	private JButton newIDCard;
	private JButton printIDCard;
	private IDCard card;
	private User user;
	
	/**
	 * Constructor.
	 * 
	 * @param card
	 */
	public IDCardDetailsPanel(IDCard card) {
		this.card = card;
		if(card != null && card.getUserID() > 0L){
			this.user = AdminUsers.getInstance().getUser(card.getUserID());
		}
		
		showUI();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param user
	 */
	public IDCardDetailsPanel(User user) {
		this.user = user;
		if(user != null && user.getUserID() > 0L){
			this.card = AdminIDCards.getInstance().getUserActiveIDCard(user.getUserID());
		}
		
		showUI();
	}
	
	/**
	 * Creates UI.
	 */
	private void showUI(){
		setLayout(new BorderLayout(10, 10));
		
		if(card != null){
			setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"IDCard: " + card.getCardID(), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		} else {
			setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
					"IDCard: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		}
		
		add(getCardPanel(), BorderLayout.CENTER);
	}
	
	/**
	 * Creates and returns panel with <code>IDCard</code>
	 * details and buttons with various functions, such as
	 * deactivate current <code>IDCard</code>, or send card
	 * details to printer.
	 * 
	 * @return JPanel
	 */
	private JPanel getCardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		
		panel.add(getCardDetailsPanel(), BorderLayout.CENTER);
		panel.add(getButtonPanel(), BorderLayout.PAGE_END);
		
		return panel;
	}
	
	/**
	 * Creates and returns <code>IDCard</code> details panel
	 * with empty fields.
	 * 
	 * @return JPanel
	 */
	private JPanel getCardDetailsPanel() {
		JPanel panel = new JPanel(new SpringLayout());
		
		txtUserID = new JTextField(24);
		txtUserID.setEditable(false);
		
		txtUserName = new JTextField(24);
		txtUserName.setEditable(false);
		
		txtIDCardStatus = new JTextField(24);
		txtIDCardStatus.setEditable(false);
		
		txtIDCardValidFrom = new JTextField(24);
		txtIDCardValidFrom.setEditable(false);
		
		txtIDCardValidTo = new JTextField(24);
		txtIDCardValidTo.setEditable(false);
		
		txtDeactivationDate = new JTextField(24);
		txtDeactivationDate.setEditable(false);
		
		txtDeactivationReason = new JTextField(24);
		txtDeactivationReason.setEditable(false);
		
		if(card != null){
			fillTextPanel();
		}
		
		panel.add(new JLabel("User ID:", JLabel.TRAILING));
		panel.add(txtUserID);
		panel.add(new JLabel("User Name:", JLabel.TRAILING));
		panel.add(txtUserName);
		panel.add(new JLabel("IDCard Status:", JLabel.TRAILING));
		panel.add(txtIDCardStatus);
		panel.add(new JLabel("Valid From:", JLabel.TRAILING));
		panel.add(txtIDCardValidFrom);
		panel.add(new JLabel("Valid To:", JLabel.TRAILING));
		panel.add(txtIDCardValidTo);
		panel.add(new JLabel("Deactivation Date:", JLabel.TRAILING));
		panel.add(txtDeactivationDate);
		panel.add(new JLabel("Deactivation Reason:", JLabel.TRAILING));
		panel.add(txtDeactivationReason);
		
		SpringUtilities.makeCompactGrid(
				panel,
				7, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
	
	/**
	 * Fills up empty fields for <code>IDCard</code> with details.
	 */
	private void fillTextPanel(){
		if(card.getUserID() > 0L){
			txtUserID.setText(Long.toString(card.getUserID()));
		}
		if(card.getUserName() != null){
			txtUserName.setText(card.getUserName());
		}
		if(card.getGeneralStatus() != null){
			txtIDCardStatus.setText(card.getGeneralStatus().toString());
		}
		if(card.getValidFrom() != null){
			txtIDCardValidFrom.setText(
					BasicLibraryDateFormatter.formatDate(card.getValidFrom()));
		}
		if(card.getValidTo() != null){
			txtIDCardValidTo.setText(
					BasicLibraryDateFormatter.formatDate(card.getValidTo()));
		}
		if(card.getGeneralStatus() == GeneralStatus.INACTIVE) {
			if(card.getDeactivationDate() != null){
				txtDeactivationDate.setText(
						BasicLibraryDateFormatter.formatDate(card.getDeactivationDate()));
			}
			if(card.getDeactivationReason() != null){
				txtDeactivationReason.setText(card.getDeactivationReason().toString());
			}
		}
	}
	
	/**
	 * Creates and returns panel with buttons.
	 * 
	 * @return JPanel
	 */
	private JPanel getButtonPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		panel.add(getDeactivateButton());
		panel.add(getNewIDCardButton());
		panel.add(getPrintIDCardButton());
		
		updateButtonState();
		
		return panel;
	}
	
	/**
	 * Creates and returns button with functionality to deactivate
	 * <code>IDCard</code>.
	 * 
	 * @return JButton
	 */
	private JButton getDeactivateButton(){
		btnDeactivate = new JButton("Deactivate");
		btnDeactivate.setEnabled(false);
		btnDeactivate.setToolTipText("Deactivate current IDCard");
		btnDeactivate.setActionCommand("deactivate");
		btnDeactivate.addActionListener(this);
		
		return btnDeactivate;
	}
	
	/**
	 * Initialises and returns button with functionality to create
	 * new <code>IDCard</code>.
	 * 
	 * @return JButton
	 */
	private JButton getNewIDCardButton(){
		newIDCard = new JButton("New IDCard");
		newIDCard.setEnabled(false);
		newIDCard.setToolTipText("Create new IDCard");
		newIDCard.setActionCommand("create");
		newIDCard.addActionListener(this);
		
		return newIDCard;
	}
	
	/**
	 * Initialises and returns button to print <code>IDCard</code>
	 * details.
	 * 
	 * @return JButton
	 */
	private JButton getPrintIDCardButton(){
		printIDCard = new JButton("Print IDCard");
		printIDCard.setEnabled(false);
		printIDCard.setToolTipText("This function not implemented yet");
		printIDCard.setActionCommand("print");
		printIDCard.addActionListener(this);
		
		return printIDCard;
	}
	
	/**
	 * Deactivates current IDCard.
	 */
	private void deactivateIDCard(){
		try {
			IDCardDeactivationReasonDialog dialog =
					new IDCardDeactivationReasonDialog(card);
			if(dialog.isOkPressed()){
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						txtIDCardStatus.setText(card.getGeneralStatus().toString());
						txtDeactivationDate.setText(
								BasicLibraryDateFormatter.formatDate(
										card.getDeactivationDate()));
						txtDeactivationReason.setText(
								card.getDeactivationReason().toString());
						
						updateButtonState();
					}
				});
			}
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					IDCardDetailsPanel.this,
					"IDCard Deactivation UI Exception!");
		}
	}
	
	/**
	 * Creates new <code>IDCard</code>.
	 */
	private void createIDCard(){
		try {
			IDCard temp = AdminIDCards.getInstance().activateIDCard(user.getUserID());
			if(temp != null){
				card = temp;
				user.setIdCardNumber(card.getCardID());
				// Displaying message
				UIDisplayManager.displayInformationMessage(
						IDCardDetailsPanel.this,
						"New IDCard " + temp.getCardID() + " created!");
				// Updating details on panel
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						setBorder(new TitledBorder(
								UIManager.getBorder("TitledBorder.border"),
								"IDCard: " + card.getCardID(),
								TitledBorder.LEADING,
								TitledBorder.TOP,
								null,
								null));
						
						txtUserID.setText(Long.toString(card.getUserID()));
						txtUserName.setText(card.getUserName());
						txtIDCardStatus.setText(card.getGeneralStatus().toString());
						txtIDCardValidFrom.setText(
								BasicLibraryDateFormatter.formatDate(
										card.getValidFrom().getTime()));
						txtIDCardValidTo.setText(
								BasicLibraryDateFormatter.formatDate(
										card.getValidTo().getTime()));
						txtDeactivationDate.setText("");
						txtDeactivationReason.setText("");
						
						updateButtonState();
					}
				});
			} else {
				UIDisplayManager.displayErrorMessage(
						IDCardDetailsPanel.this,
						"Error occur while trying to activate new IDCard!" +
						"\nPlease check that the user is ACTIVE and that he/she does " +
						"not have active IDCard!");
			}
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					IDCardDetailsPanel.this,
					"New IDCard UI Exception!");
		}
	}
	
	/**
	 * Sends <code>IDCard</code> details to printer. 
	 */
	private void printIDCard(){
		try {
			if(card != null) PrintingManager.printIDCard(card);
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					IDCardDetailsPanel.this,
					"Print IDCard UI Exception!");
		}
	}
	
	/**
	 * Updates button state by enabling or disabling them,
	 * depending on <code>IDCard</code> status.
	 */
	private void updateButtonState(){
		if(card != null && card.isActive()){
			btnDeactivate.setEnabled(true);
			newIDCard.setEnabled(false);
			printIDCard.setEnabled(true);
		} else {
			if(user != null && user.isActive() && !user.hasIDCard()){
				btnDeactivate.setEnabled(false);
				newIDCard.setEnabled(true);
				printIDCard.setEnabled(false);
			} else {
				btnDeactivate.setEnabled(false);
				newIDCard.setEnabled(false);
				printIDCard.setEnabled(false);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("deactivate")){
			deactivateIDCard();
		} else if(e.getActionCommand().equals("create")){
			createIDCard();
		} else if(e.getActionCommand().equals("print")){
			printIDCard();
		}
	}
}