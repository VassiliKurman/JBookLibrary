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

import vkurman.jbooklibrary.enums.Sex;

/**
 * Dialog that offers to user to choose sex.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class EnumSexInputDialog extends JDialog {

	private static final long serialVersionUID = 6414603261355777145L;
	private JComboBox cbxSex;
	private Sex value;
	private boolean okPressed;
	
	public EnumSexInputDialog() {
		this(null);
	}
	
	public EnumSexInputDialog(Sex sex) {
		this.value = sex;
		this.okPressed = false;
		
		showUI();
	}
	
	private void showUI(){
		setTitle("Change sex");
		getContentPane().setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		{
			JPanel textPanel = new JPanel();
			textPanel.setLayout(new FlowLayout());
			
			JLabel lblStatus = new JLabel("Sex: ");
			textPanel.add(lblStatus);
			
			cbxSex = new JComboBox();
			Sex sexes[] = Sex.values();
			for (int i = 0; i < sexes.length; i++) {
				cbxSex.addItem(sexes[i]);
			}
			if(value != null){
				cbxSex.setSelectedItem(value);
			}
			
			textPanel.add(cbxSex);
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
						if(cbxSex.getSelectedIndex() != -1){
							value = (Sex) cbxSex.getSelectedItem();
							okPressed = true;
							
							dispose();
						} else {
							UIDisplayManager.displayErrorMessage(
									EnumSexInputDialog.this,
									"Sex hasn't been selected!\n" +
									"Please select the SEX from provided box");
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
	
	public Sex getInput(){
		return value;
	}
	
	public boolean isOkPressed() {
		return okPressed;
	}
}