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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * <code>JDialog</code> that takes input text from application
 * user.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class TextAreaInputDialog extends JDialog {
	
	private static final long serialVersionUID = -1902108950337802284L;
	private JTextArea input;
	private String title, text;
	private boolean okPressed;
	
	/**
	 * Constructor
	 */
	public TextAreaInputDialog() {
		this("Text input", null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param title
	 */
	public TextAreaInputDialog(String title) {
		this(title, null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param title
	 * @param text
	 */
	public TextAreaInputDialog(String title, String text) {
		this.title = title;
		this.text = text;
		this.okPressed = false;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle(title);
		getContentPane().setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		{
			JPanel textPanel = new JPanel();
			textPanel.setLayout(new FlowLayout());
			
			input = new JTextArea(text, 5, 30);
			
			textPanel.add(new JScrollPane(input));
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
						text = input.getText();
						okPressed = true;
						dispose();
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
	 * Returns input text.
	 * 
	 * @return String
	 */
	public String getInput(){
		return text;
	}
	
	/**
	 * Returns true if <code>OK</code> button was pressed.
	 * 
	 * @return boolean
	 */
	public boolean isOkPressed() {
		return okPressed;
	}
}