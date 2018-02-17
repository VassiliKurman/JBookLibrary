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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import vkurman.jbooklibrary.core.AdminFines;
import vkurman.jbooklibrary.core.AdminLoans;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.Loan;
import vkurman.jbooklibrary.core.Payment;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * Dialog that displays details about specified <code>Payment</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class PaymentDetailsUI extends JDialog {

	private static final long serialVersionUID = 6855915034216143067L;
	
	private Payment payment;
	private Fine fine;
	private Loan loan;
	
	/**
	 * Constructor.
	 * 
	 * @param payment
	 */
	public PaymentDetailsUI(Payment payment) {
		this.payment = payment;
		fine = AdminFines.getInstance().getFine(payment.getFineID());
		loan = AdminLoans.getInstance().getLoan(fine.getLoanID());
		
		this.showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	public void showUI(){
		setTitle("Details for payment: " + payment.getPaymentID());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setLayout(new BorderLayout(10, 10));
		
		getContentPane().add(getContent(), BorderLayout.CENTER);
		getContentPane().add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns content panel for <code>this</code> dialog.
	 * 
	 * @return JPanel
	 */
	private JPanel getContent(){
		JPanel panel = new JPanel(new SpringLayout());
		
		panel.add(getPaymentPanel());
		panel.add(getUserPanel());
		panel.add(getItemPanel());
		panel.add(getLoanPanel());
		panel.add(getFinePanel());
		
		SpringUtilities.makeCompactGrid(
				panel,
				panel.getComponentCount(), 1,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
	
	/**
	 * Creates and returns panel with payment details.
	 * 
	 * @return JPanel
	 */
	private JPanel getPaymentPanel(){
		JPanel panel = new JPanel(new SpringLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Payment details"));
		
		panel.add(new JLabel("Payment ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(payment.getPaymentID())));
		panel.add(new JLabel("Payment date:", JLabel.TRAILING));
		panel.add(new JTextField(
				BasicLibraryDateFormatter.formatDate(payment.getPaymentDate())));
		panel.add(new JLabel("Amount paid:", JLabel.TRAILING));
		panel.add(new JTextField(payment.getAmount().toString()));
		
		
		SpringUtilities.makeCompactGrid(
				panel,
				3, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
				
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
		
		panel.add(new JLabel("User ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(payment.getUserID())));
		
		panel.add(new JLabel("Name:", JLabel.TRAILING));
		panel.add(new JTextField(payment.getUserName()));
		
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
		
		panel.add(new JLabel("Item ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(loan.getBookID())));
		
		panel.add(new JLabel("Title:", JLabel.TRAILING));
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
		
		panel.add(new JLabel("Loan ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(loan.getLoanID())));
		panel.add(new JLabel("Borrow date:", JLabel.TRAILING));
		panel.add(new JTextField(
				BasicLibraryDateFormatter.formatDate(loan.getBorrowDate())));
		panel.add(new JLabel("Due date:", JLabel.TRAILING));
		panel.add(new JTextField(
				BasicLibraryDateFormatter.formatDate(loan.getDueDate())));
		panel.add(new JLabel("Return date:", JLabel.TRAILING));
		panel.add(loan.isActive() ?
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
		
		panel.add(new JLabel("Fine ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(fine.getFineID())));
		
		panel.add(new JLabel("Daily charge:", JLabel.TRAILING));
		panel.add(new JTextField(fine.getFinePerDay().toString()));
		
		panel.add(new JLabel("Overdue days:", JLabel.TRAILING));
		panel.add(new JTextField(Integer.toString(fine.getDays())));
		
		panel.add(new JLabel("Total to pay:", JLabel.TRAILING));
		panel.add(new JTextField(fine.getTotal().toString()));
		
		panel.add(new JLabel("Amount paid:", JLabel.TRAILING));
		panel.add(new JTextField(fine.getValuePaid().toString()));
		
		if(fine.isActive()){
			panel.add(new JLabel("Amount to pay:", JLabel.TRAILING));
			panel.add(new JTextField(fine.toPay().toString()));
		} else {
			panel.add(new JLabel("Fine paid on:", JLabel.TRAILING));
			panel.add(new JTextField(
					BasicLibraryDateFormatter.formatDate(fine.getFinePaidDate())));
		}
		
		SpringUtilities.makeCompactGrid(
				panel,
				6, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
				
		return panel;
	}
}