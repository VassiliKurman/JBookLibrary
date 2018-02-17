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
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialog to get <code>BigDecimal</code> input from user.
 * 
* <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
*/
public class BigDecimalInputDialog extends JDialog {

	private static final long serialVersionUID = 3378115667555749411L;
	private JTextField input;
	private BigDecimal value;
	private boolean okPressed;
	
	/**
	 * Default constructor with value set to <code>0.00</code>
	 */
	public BigDecimalInputDialog() {
		this(new BigDecimal("0.00").setScale(2, RoundingMode.HALF_EVEN));
	}
	
	/**
	 * Constructor.
	 * 
	 * @param value
	 */
	public BigDecimalInputDialog(BigDecimal value) {
		this.value = value.setScale(2, RoundingMode.HALF_EVEN);
		okPressed = false;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Decimal input");
		getContentPane().setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		{
			JPanel textPanel = new JPanel();
			textPanel.setLayout(new FlowLayout());
			
			input = new JTextField(value.toString(), 30);
			
			textPanel.add(input);
			getContentPane().add(textPanel, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.PAGE_END);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ev) {
						if(isValidNumber()){
							okPressed = true;
							dispose();
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ev) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Checks if entered text is valid number of type
	 * <code>BigDecimal</code>.
	 * 
	 * @return boolean
	 */
	private boolean isValidNumber(){
		boolean valid = false;
		// Checking that textfield is no empty
		if(input.getText().isEmpty()){
			UIDisplayManager.displayErrorMessage(
					BigDecimalInputDialog.this,
					"TextField is empty! Please enter price");
		} else {
			String s = input.getText();
			if(s.matches("\\d+|\\d*\\.\\d+|\\d+\\.\\d*")){
				value = new BigDecimal(s);
				valid = true;
			} else {
				UIDisplayManager.displayErrorMessage(
						BigDecimalInputDialog.this,
						"Not a valid number! Please enter price in the following format: '0.00'");
			}
		}
		return valid;
	}
	
	/**
	 * Returns value from input field.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getInput(){
		return value;
	}
	
	/**
	 * Marker for <code>OK</code> button if it has been
	 * pressed or not.
	 * 
	 * @return boolean
	 */
	public boolean isOkPressed() {
		return okPressed;
	}
}