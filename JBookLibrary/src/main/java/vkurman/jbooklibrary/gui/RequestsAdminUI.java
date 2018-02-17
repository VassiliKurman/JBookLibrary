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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.ItemType;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.RequestsPanel;

/**
 * Requests administrator UI.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class RequestsAdminUI extends JDialog {
	
	/**
	 * Auto generated serialVersionUID
	 */
	private static final long serialVersionUID = 638112515560051606L;
	private RequestsPanel requestsPanel;
	
	/**
	 * Constructor.
	 */
	public RequestsAdminUI() {
		requestsPanel = new RequestsPanel(
				true,
				true,
				ItemType.BOOK,
				GeneralStatus.ACTIVE);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Requests Administration UI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		setLayout(new BorderLayout(5, 5));
		getContentPane().add(getActionsButtonPanel(), BorderLayout.PAGE_START);
		getContentPane().add(requestsPanel, BorderLayout.CENTER);
		getContentPane().add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with button to make new request.
	 * 
	 * @return JPanel
	 */
	private JPanel getActionsButtonPanel(){
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JButton newButton = new JButton("New Request");
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							RequestHandlerUI dialog = new RequestHandlerUI();
							if(dialog.isOkPressed()){
								if(dialog.getRequest() != null){
									requestsPanel.addRequest(dialog.getRequest());
								}
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									RequestsAdminUI.this,
									"Can't request book!");
						}
					}
				});
			}
		});
		buttonPane.add(newButton);
		
		return buttonPane;
	}
}