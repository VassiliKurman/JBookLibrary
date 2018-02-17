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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.AdminIDCards;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.enums.IDCardDeactivationReason;

/**
 * Dialog that offers to user to select <code>IDCard</code>
 * deactivation reason.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDCardDeactivationReasonDialog extends JDialog {

	private static final long serialVersionUID = 1459657682331064042L;
	private final JPanel contentPanel = new JPanel();
	private IDCard card;
	private JComboBox cbxReasons;
	private JButton okButton;
	private JButton cancelButton;

	private boolean okPressed;
	
	public IDCardDeactivationReasonDialog(IDCard idCard) {
		this.card = idCard;
		this.okPressed = false;
		
		showUI();
	}
	
	private void showUI(){
		setTitle("IDCard " + card.getCardID() + " deactivation");
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		contentPanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblReasonForDisactivation = new JLabel("Reason for deactivation: ");
			contentPanel.add(lblReasonForDisactivation);
		}
		{
			cbxReasons = new JComboBox();
			IDCardDeactivationReason reasons[] = IDCardDeactivationReason.values();
			for (int i = 0; i < reasons.length; i++) {
				cbxReasons.addItem(reasons[i]);
			}
			contentPanel.add(cbxReasons);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ev) {
						if(cbxReasons.getSelectedIndex() != -1){
							IDCardDeactivationReason r = (IDCardDeactivationReason) cbxReasons.getSelectedItem();
							if(AdminIDCards.getInstance().deactivateIDCard(card, r)){
								okPressed = true;
								
								UIDisplayManager.displayInformationMessage(
										IDCardDeactivationReasonDialog.this,
										"IDCard " + card.getCardID() + " deactivated successfully!");
								
								dispose();
							} else {
								UIDisplayManager.displayErrorMessage(
										IDCardDeactivationReasonDialog.this,
										"IDCard deactivation error!");
							}
						} else {
							UIDisplayManager.displayErrorMessage(
									IDCardDeactivationReasonDialog.this,
									"IDCard deactivation reason hasn't been selected!\n" +
									"Plase selecte the reason for IDCard deactivation from provided box");
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
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
		}
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public boolean isOkPressed() {
		return okPressed;
	}
}