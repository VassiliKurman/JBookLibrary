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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.enums.EmploymentTerminationReason;

/**
 * Dialog that offers to user to choose reason for terminating
 * an employment.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class EmploymentTerminationUI extends JDialog {
	
	private static final long serialVersionUID = 3148215428044058541L;
	private JComboBox cbxReasons;
	private EmploymentTerminationReason reason;
	private JButton okButton;
	private JButton cancelButton;

	private boolean okPressed;
	
	/**
	 * Constructor.
	 * 
	 * @param librarian
	 */
	public EmploymentTerminationUI(Librarian librarian) {
		this.okPressed = false;
		
		showUI(librarian);
	}
	
	/**
	 * Creates and displays UI.
	 * 
	 * @param librarian
	 */
	private void showUI(Librarian librarian){
		setTitle(librarian.getName() + " employment termination");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		setLayout(new BorderLayout());
		add(getMessagePanel(), BorderLayout.CENTER);
		add(getButtonsPanel(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel getMessagePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		{
			JLabel lblReasonForTermination = new JLabel("Reason for employment termination: ");
			panel.add(lblReasonForTermination);
		}
		{
			cbxReasons = new JComboBox();
			EmploymentTerminationReason reasons[] = EmploymentTerminationReason.values();
			for (int i = 0; i < reasons.length; i++) {
				cbxReasons.addItem(reasons[i]);
			}
			panel.add(cbxReasons);
		}
		return panel;
	}
	
	private JPanel getButtonsPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		{
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					if(cbxReasons.getSelectedIndex() != -1){
						reason = (EmploymentTerminationReason) cbxReasons.getSelectedItem();
						okPressed = true;
						
						dispose();
					} else {
						UIDisplayManager.displayErrorMessage(
								EmploymentTerminationUI.this,
								"Employment Termination reason hasn't been selected!\n" +
								"Plase selecte the reason for Employment Termination from provided box");
					}
				}
			});
			panel.add(okButton);
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
			panel.add(cancelButton);
		}
		
		return panel;
	}
	
	public boolean isOkPressed() {
		return okPressed;
	}
	
	public EmploymentTerminationReason getReason(){
		return reason;
	}
}