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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import vkurman.jbooklibrary.core.AdminIDCards;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;

/**
 * Dialog that allows to search for <code>User</code> or
 * <code>IDCard</code> to deactivate <code>IDCard</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDCardDeactivationDialog extends JDialog {
	
	private static final long serialVersionUID = -5489373305342653499L;
	private IDCard idCard;
	private JPanel contentPane;
	
	private JButton cancelButton;
	private JButton okButton;
	private JButton findIDCardButton;
	private JButton findUserButton;
	private JTextField txtIDCard;
	private JTextField txtUser;
	
	private boolean idCardSet, okPressed;
	
	public IDCardDeactivationDialog() {
		this(null);
	}
	
	public IDCardDeactivationDialog(IDCard idCard) {
		this.idCard = idCard;
		this.idCardSet = (idCard == null) ? false : true;
		this.okPressed = false;
		
		showUI(idCard);
	}
	
	private void showUI(IDCard idCard){
		setTitle("Deactivate IDCard");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 10));
		setContentPane(contentPane);
		
		getContentPane().add(this.getMainPane(), BorderLayout.CENTER);
		getContentPane().add(this.getButtonPane(), BorderLayout.PAGE_END);
		
		if(idCard != null){
			setIDCard(idCard);
		}
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private boolean isIDCardSet() {
		return this.idCardSet;
	}
	
	private JPanel getMainPane() {
		JPanel pane = new JPanel();
		
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(2, 1, 5, 5));
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(2, 1, 5, 5));
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(2, 1, 5, 5));
		
		pane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pane.setLayout(new BorderLayout(10, 10));
		pane.add(left, BorderLayout.LINE_START);
		pane.add(center, BorderLayout.CENTER);
		pane.add(right, BorderLayout.EAST);
		
		{
			JLabel lblIdCard = new JLabel("IDCard:");
			left.add(lblIdCard);
			
			txtIDCard = new JTextField();
			center.add(txtIDCard);
			txtIDCard.setColumns(10);
			
			findIDCardButton = new JButton("Find IDCard");
			findIDCardButton.setActionCommand("findIDCard");
			findIDCardButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					findIDCard();
				}
			});
			right.add(findIDCardButton);
		}
		{
			JLabel lblUser = new JLabel("User:");
			left.add(lblUser);
			
			txtUser = new JTextField();
			center.add(txtUser);
			txtUser.setColumns(10);
			
			findUserButton = new JButton("Find User");
			findUserButton.setActionCommand("findUser");
			findUserButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					findUser();
				}
			});
			right.add(findUserButton);
		}
		
		return pane;
	}
	
	private JPanel getButtonPane() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				deactivateIDCard();
				okPressed = true;
			}
		});
		if(isIDCardSet()){
			okButton.setEnabled(true);
			getRootPane().setDefaultButton(okButton);
		} else {
			okButton.setEnabled(false);
			getRootPane().setDefaultButton(cancelButton);
		}
		buttonPane.add(okButton);
		
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});
		buttonPane.add(cancelButton);
		
		return buttonPane;
	}
	
	private void findUser() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransObject u = new TransObject();
					UserFinderUI dialog = new UserFinderUI(u, txtUser.getText());
					
					try {
						if(dialog.isOKPressed()){
							User temp = (User) u.getObject();
							if (temp.hasIDCard()) {
								IDCard card = AdminIDCards.getInstance().getIDCard(temp.getIdCardNumber());
								if(card != null){
									setIDCard(card);
								}
							} else {
								UIDisplayManager.displayErrorMessage(
										IDCardDeactivationDialog.this,
										"User DOES NOT has IDCard! \n"
										+ "Please activate new IDCard for "
										+ temp.getName()	+ " !!!");
							}
						} else {
							txtUser.setEditable(true);
							idCardSet = false;
							okButton.setEnabled(false);
						}
					} catch (Exception e) {
						UIDisplayManager.displayErrorMessage(
								IDCardDeactivationDialog.this,
								"Error occur while setting user details!");
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							IDCardDeactivationDialog.this,
							"Can't display UserFinderUI!");
				}
			}
		});
	}
	
	private void findIDCard() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransObject c = new TransObject();
					IDCardFinderUI dialog = new IDCardFinderUI(c, "IDCard Finder", Integer.valueOf(txtIDCard.getText()));
					dialog.setVisible(true);
					
					if(dialog.isOKPressed()){
						setIDCard((IDCard) c.getObject());
					} else {
						txtIDCard.setEditable(true);
						txtUser.setEditable(true);
						idCardSet = false;
						okButton.setEnabled(false);
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							IDCardDeactivationDialog.this,
							"Please check that you put correct IDCard number!!!");
				}
			}
		});
	}
	
	private void setIDCard(IDCard idCard){
		this.idCard = idCard;
		txtIDCard.setEditable(false);
		txtUser.setEditable(false);
		txtIDCard.setText(String.valueOf(idCard.getCardID()));
		txtUser.setText(idCard.getUserName());
		idCardSet = true;
		okButton.setEnabled(true);
	}
	
	private void deactivateIDCard() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IDCardDeactivationReasonDialog dialog = new IDCardDeactivationReasonDialog(idCard);
					if(dialog.isOkPressed()){
						dispose();
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							IDCardDeactivationDialog.this,
							"IDCard DeactivationReasonUI Exception!");
				}
			}
		});
	}
	
	public IDCard getIDCard() {
		return idCard;
	}
	
	public boolean isOkPressed() {
		return okPressed;
	}
}