package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialog that offers to user to input a number.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class NumberInputDialog extends JDialog {

	private static final long serialVersionUID = 9137430770153537520L;
	private JTextField input;
	private int value, min, max;
	private boolean okPressed;
	
	/**
	 * Default constructor with <code>value</code> 0, 
	 * minimum allowed number to enter 0 and maximum number
	 * allowed to enter equals to Integer.MAX_VALUE.
	 */
	public NumberInputDialog() {
		this(0, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Constructor with specified <code>value</code>, 
	 * minimum allowed number to enter 0 and maximum number
	 * allowed to enter equals to Integer.MAX_VALUE.
	 * 
	 * @param value
	 */
	public NumberInputDialog(int value) {
		this(value, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Constructor with specified <code>value</code>, 
	 * minimum allowed number to enter and maximum number
	 * allowed to enter.
	 * 
	 * @param value
	 * @param min
	 * @param max
	 */
	public NumberInputDialog(int value, int min, int max) {
		this.value = value;
		this.min = min;
		if(max > Integer.MAX_VALUE){
			this.max = Integer.MAX_VALUE;
		} else {
			this.max = max;
		}
		okPressed = false;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Number input");
		getContentPane().setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		{
			JPanel textPanel = new JPanel();
			textPanel.setLayout(new FlowLayout());
			
			input = new JTextField(Integer.toString(value), 30);
			
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
	 * Checks if input is a valid number.
	 * 
	 * @return boolean
	 */
	private boolean isValidNumber(){
		boolean valid = false;
		// Checking that textfield is no empty
		if(input.getText().isEmpty()){
			UIDisplayManager.displayErrorMessage(
					NumberInputDialog.this,
					"TextField is empty! Please enter number between "+min+" and "+max);
		} else {
			String s = input.getText();
			boolean number = true;
			for(int i = 0; i < s.length(); i++){
				if(!Character.isDigit(s.charAt(i))){
					number = false;
					
					UIDisplayManager.displayErrorMessage(
							NumberInputDialog.this,
							"Not a valid number! Please enter number between "+min+" and "+max);
				}
			}
			// If in the input all digits return true 
			if(number){
				int temp = Integer.valueOf(s);
				if(temp >= min && temp <= max){
					value = temp;
					valid = true;
				} else {
					UIDisplayManager.displayErrorMessage(
							NumberInputDialog.this,
							"Please enter number between "+min+" and "+max+" inclusive!");
				}
			}
		}
		return valid;
	}
	
	/**
	 * Returns input value.
	 * 
	 * @return int
	 */
	public int getInput(){
		return value;
	}
	
	/**
	 * Marker for <code>OK</code> button if it was pressed or
	 * not.
	 * 
	 * @return boolean
	 */
	public boolean isOkPressed() {
		return okPressed;
	}
}