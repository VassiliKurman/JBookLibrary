package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.AdminPayments;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.Payment;
import vkurman.jbooklibrary.core.TransObject;

/**
 * Input dialog for new <code>Payment</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class NewPaymentUI extends JDialog {

	private static final long serialVersionUID = 8935796801038590116L;
	private final JPanel contentPanel = new JPanel();
	private boolean okPressed;
	private JButton okButton, cancelButton;
	private JTextField amount;
	private Fine fine;
	private BigDecimal payIn;
	private TransObject obj;

	public NewPaymentUI(Fine fine, TransObject obj) {
		this.fine = fine;
		this.okPressed = false;
		this.payIn = new BigDecimal("0.00");
		this.obj = obj;
		
		showUI();
	}
	
	private void showUI(){
		setTitle("New Payment");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(10, 10));
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPaymentPanel(), BorderLayout.CENTER);
		
		add(contentPanel, BorderLayout.CENTER);
		add(getButtonPane(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel getPaymentPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1, 1, 5, 5));
		labelPanel.add(new JLabel("Amount:"));
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1, 1, 5, 5));
		
		amount = new JTextField(24);
		textPanel.add(amount);
		
		panel.add(labelPanel, BorderLayout.LINE_START);
		panel.add(textPanel, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel getButtonPane(){
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		{
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					if(isValidated()){
						Payment payment = AdminPayments.getInstance().makePayment(fine, payIn);
						if(payment != null){
							obj.setObject(payment);
							okPressed = true;
							dispose();
						} else {
							UIDisplayManager.displayErrorMessage(
									NewPaymentUI.this,
									"Unsuccessfull Payment!");
						}
					}
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
	
	private boolean isValidated(){
		boolean b = false;
		// Checking that JTextField is not empty
		if(!amount.getText().isEmpty()){
			String s = amount.getText();
			// Block to check that numbers were entered in JTextField
			try {
				Double d = Double.parseDouble(s);
				if (d > 0) {
					BigDecimal pay = new BigDecimal(d);
					pay = pay.setScale(2, RoundingMode.HALF_EVEN);
					// Checking if entered amount less or equal to outstanding amount
					if (pay.compareTo(fine.toPay()) <= 0) {
						payIn = pay;
						b = true;
					} else {
						UIDisplayManager.displayErrorMessage(
								NewPaymentUI.this,
								"Please enter amount less or equal to "
								+ fine.toPay() + " !");
					}
				} else {
					UIDisplayManager.displayErrorMessage(
							NewPaymentUI.this,
							"Please enter positive numbers!");
				}
			}
			catch (NumberFormatException e) {
				UIDisplayManager.displayErrorMessage(
						NewPaymentUI.this,
						"Please enter numbers only!");
			}
		} else {
			UIDisplayManager.displayErrorMessage(
					NewPaymentUI.this,
					"Text field is empty!");
		}
		return b;
	}
	
	public boolean isOKPressed() {
		return this.okPressed;
	}
}
