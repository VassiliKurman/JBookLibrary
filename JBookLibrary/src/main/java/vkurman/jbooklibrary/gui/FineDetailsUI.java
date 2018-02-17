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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import vkurman.jbooklibrary.core.AdminFines;
import vkurman.jbooklibrary.core.AdminLoans;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.Loan;
import vkurman.jbooklibrary.core.Payment;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.PaymentsPanel;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * Dialog that displays details about fine.
 *
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class FineDetailsUI extends JDialog {

	private static final long serialVersionUID = 7440959209728947756L;
	private Fine fine;
	private Loan loan;
	private JButton payFineButton;
	private JButton clearFineButton;
	private JTextField txtPaid;
	private JTextField txtToPay;
	private JTextField isPaid;
	private PaymentsPanel paymentsPanel;
	
	/**
	 * Constructor.
	 * 
	 * @param fine
	 */
	public FineDetailsUI(Fine fine) {
		this.fine = fine;
		loan = AdminLoans.getInstance().getLoan(fine.getLoanID());
		paymentsPanel = new PaymentsPanel(fine);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Details for fine: " + fine.getFineID());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setLayout(new BorderLayout(10, 10));
		
		getContentPane().add(getContent(), BorderLayout.CENTER);
		getContentPane().add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		changeTextState();
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns all content about fine.
	 * 
	 * @return JPanel
	 */
	private JPanel getContent() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		
		panel.add(getDetailsPanel(), BorderLayout.PAGE_START);
		panel.add(paymentsPanel, BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * Creates and returns panel with details about fine.
	 * 
	 * @return JPanel
	 */
	private JPanel getDetailsPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		
		JPanel detailsPanel = new JPanel(new SpringLayout());
		JPanel p1 = new JPanel(new SpringLayout());
		JPanel p2 = new JPanel(new SpringLayout());
		
		p2.add(getUserPanel());
		p2.add(getItemPanel());
		SpringUtilities.makeCompactGrid(
				p2,
				2, 1,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		p1.add(getFinePanel());
		p1.add(p2);
		
		SpringUtilities.makeCompactGrid(
				p1,
				1, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		detailsPanel.add(p1);
		detailsPanel.add(getLoanPanel());
		
		SpringUtilities.makeCompactGrid(
				detailsPanel,
				2, 1,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		panel.add(detailsPanel, BorderLayout.CENTER);
		panel.add(getActionButtonsPanel(), BorderLayout.PAGE_END);
		
		return panel;
	}
	
	/**
	 * Creates and returns panel with user details.
	 * 
	 * @return JPanel
	 */
	private JPanel getUserPanel(){
		JPanel panel = new JPanel(new SpringLayout());
		panel.setBorder(BorderFactory.createTitledBorder("User details"));
		
		panel.add(new JLabel("User ID:"));
		panel.add(new JTextField(Long.toString(fine.getUserID())));
		
		panel.add(new JLabel("Name:"));
		panel.add(new JTextField(fine.getUserName()));
		
		SpringUtilities.makeCompactGrid(
				panel,
				2, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
				
		return panel;
	}
	
	/**
	 * Creates and returns panel with item details.
	 * 
	 * @return JPanel
	 */
	private JPanel getItemPanel(){
		JPanel panel = new JPanel(new SpringLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Item details"));
		
		panel.add(new JLabel("Item ID:"));
		panel.add(new JTextField(Long.toString(loan.getBookID())));
		
		panel.add(new JLabel("Title:"));
		panel.add(new JTextField(loan.getBookTitle()));
		
		SpringUtilities.makeCompactGrid(
				panel,
				2, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
				
		return panel;
	}
	
	/**
	 * Creates and returns panel with loan details.
	 * 
	 * @return JPanel
	 */
	private JPanel getLoanPanel(){
		JPanel panel = new JPanel(new SpringLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Loan details"));
		
		panel.add(new JLabel("Loan ID:"));
		panel.add(new JTextField(Long.toString(loan.getLoanID())));
		panel.add(new JLabel("Borrow date:"));
		panel.add(new JTextField(
				BasicLibraryDateFormatter.formatDate(loan.getBorrowDate())));
		panel.add(new JLabel("Due date:"));
		panel.add(new JTextField(
				BasicLibraryDateFormatter.formatDate(loan.getDueDate())));
		panel.add(new JLabel("Return date:"));
		panel.add(loan.isActive() ?
				new JTextField("") :
					loan.getReturnDate() == null ?
							new JTextField("") :
								new JTextField(BasicLibraryDateFormatter.formatDate(loan.getReturnDate())));
		SpringUtilities.makeCompactGrid(
				panel,
				4, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
				
		return panel;
	}
	
	/**
	 * Creates and returns panel with fine details.
	 * 
	 * @return JPanel
	 */
	private JPanel getFinePanel(){
		JPanel panel = new JPanel(new SpringLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Fine details"));
		
		panel.add(new JLabel("Fine ID:"));
		panel.add(new JTextField(Long.toString(fine.getFineID())));
		
		panel.add(new JLabel("Daily charge:"));
		panel.add(new JTextField(fine.getFinePerDay().toString()));
		
		panel.add(new JLabel("Overdue days:"));
		panel.add(new JTextField(Integer.toString(fine.getDays())));
		
		panel.add(new JLabel("Total to pay:"));
		panel.add(new JTextField(fine.getTotal().toString()));
		
		panel.add(new JLabel("Amount paid:"));
		txtPaid = new JTextField(fine.getValuePaid().toString());
		panel.add(txtPaid);
		
		panel.add(new JLabel("Amount to pay:"));
		txtToPay = new JTextField(fine.toPay().toString());
		panel.add(txtToPay);
		
		panel.add(new JLabel("Fine paid on:"));
		if(fine.isActive()){
			isPaid = new JTextField("Not Paid");
		} else {
			isPaid = new JTextField(
					fine.getFinePaidDate() == null ?
							"Not Paid" : 
								BasicLibraryDateFormatter.formatDate(fine.getFinePaidDate()));
		}
		panel.add(isPaid);
		
		SpringUtilities.makeCompactGrid(
				panel,
				7, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
				
		return panel;
	}
	
	/**
	 * Creates and returns panel with buttons to perform actions, such
	 * as "pay fine" or "clear fine" on specified fine.
	 * 
	 * @return JPanel
	 */
	private JPanel getActionButtonsPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		payFineButton = new JButton("Pay Fine");
		payFineButton.setActionCommand("payFine");
		payFineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							TransObject obj = new TransObject();
							NewPaymentUI dialog = new NewPaymentUI(fine, obj);
							if(dialog.isOKPressed()) {
								// Changing textFields
								changeTextState();
								
								// Changing button state
								changeButtonState();
								
								paymentsPanel.addPayment((Payment) obj.getObject());
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									FineDetailsUI.this,
									"Can't display FineDetailsUI!");
						}
					}
				});
			}
		});
		
		clearFineButton = new JButton("Clear Fine");
		clearFineButton.setActionCommand("clearFine");
		clearFineButton.setEnabled(false);
		clearFineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				
				boolean confirmed = JOptionPane.showConfirmDialog(
						FineDetailsUI.this,
						"Do you really want to clear the fine?",
						"Fine clearance confirmaion",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
				
				if(confirmed){
					// Saving initial generalStatus
					GeneralStatus generalStatus = fine.getStatus();
					// Setting generalStatus to INACTIVE
					fine.setStatus(GeneralStatus.INACTIVE);
					fine.setFinePaidDate(Calendar.getInstance());
					// Updating DB and displaying message
					if(AdminFines.getInstance().clearFine(fine)){
						
						UIDisplayManager.displayInformationMessage(
								FineDetailsUI.this,
								"Fine cleared!");
						
						// Updating UI
						changeButtonState();
					} else {
						// Setting generalStatus to INACTIVE
						fine.setStatus(generalStatus);
						
						// Displaying error message
						UIDisplayManager.displayErrorMessage(
								FineDetailsUI.this,
								"Can't clear Fine!");
					}
				}
			}
		});
		
		changeButtonState();
		
		panel.add(clearFineButton);
		panel.add(payFineButton);
		
		return panel;
	}
	
	/**
	 * Changes buttons states by enabling or disabling them.
	 */
	private void changeButtonState(){
		if(fine.isActive()){
			clearFineButton.setEnabled(true);
			payFineButton.setEnabled(true);
		} else {
			clearFineButton.setEnabled(false);
			payFineButton.setEnabled(false);
		}
	}
	
	/**
	 * Changes texts in text fields.
	 */
	private void changeTextState(){
		txtPaid.setText(fine.getValuePaid().toString());
		
		if(fine.isActive()){
			txtToPay.setText(fine.toPay().toString());
		}else {
			isPaid.setText(
					BasicLibraryDateFormatter.formatDate(fine.getFinePaidDate().getTime()));
		}
	}
}