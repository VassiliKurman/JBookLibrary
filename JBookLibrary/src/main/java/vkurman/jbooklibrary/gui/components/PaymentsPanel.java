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

package vkurman.jbooklibrary.gui.components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;





import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.Payment;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.gui.PaymentDetailsUI;
import vkurman.jbooklibrary.interfaces.FilterObserver;

/**
 * This panel contains a List of Payments.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class PaymentsPanel extends JPanel implements FilterObserver {

	private static final long serialVersionUID = 1933794249327158188L;
	private UserBasicSearchPanel userBasicSearchPanel;
	private PaymentsTableModel model;
	private TitledBorder titledBorder;
	private User user;
	private Fine fine;
	private JButton viewPaymentButton;
	private JTable table;
	private Payment selectedPayment;

	public PaymentsPanel(boolean showUserSearchPanel) {
		this.user = null;
		this.fine = null;
		this.selectedPayment = null;
		model = new PaymentsTableModel(user, fine);
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Payments: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		// Creating StatusFilterPanel and registering itself in it
		userBasicSearchPanel = new UserBasicSearchPanel();
		userBasicSearchPanel.register(this);
		
		showUI(showUserSearchPanel);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public PaymentsPanel(Fine fine) {
		this.user = null;
		this.fine = fine;
		this.selectedPayment = null;
		model = new PaymentsTableModel(user, fine);
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Payments: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		// Creating StatusFilterPanel and registering itself in it
		userBasicSearchPanel = new UserBasicSearchPanel();
		userBasicSearchPanel.register(this);
		
		showUI(false);
	}
	
	public PaymentsPanel(User user) {
		this.user = user;
		this.fine = null;
		this.selectedPayment = null;
		model = new PaymentsTableModel(user, fine);
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Payments: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		// Creating StatusFilterPanel and registering itself in it
		userBasicSearchPanel = new UserBasicSearchPanel();
		userBasicSearchPanel.register(this);
		
		showUI(false);
	}
	
	public PaymentsPanel(boolean showUserSearchPanel, User user) {
		this.user = user;
		this.fine = null;
		this.selectedPayment = null;
		model = new PaymentsTableModel(user, fine);
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Payments: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		// Creating StatusFilterPanel and registering itself in it
		userBasicSearchPanel = new UserBasicSearchPanel();
		userBasicSearchPanel.register(this);
		
		showUI(showUserSearchPanel);
	}
	
	private void showUI(boolean showUserSearchPanel){
		setLayout(new BorderLayout(5, 5));
		setBorder(titledBorder);
		
		if(showUserSearchPanel){
			add(getFilterPane(showUserSearchPanel), BorderLayout.PAGE_START);
		}
		add(getDataPane(), BorderLayout.CENTER);
		add(getActionButtonPane(), BorderLayout.PAGE_END);
	}
	
	private JPanel getFilterPane(boolean showUserSearchPanel){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		if(showUserSearchPanel){
			panel.add(userBasicSearchPanel);
		}
		
		return panel;
	}
	
	private JScrollPane getDataPane() {
		table = new JTable(model);
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(table.getSelectedRow() > -1) {
					selectedPayment = model.getPayment(table.getSelectedRow());
					
					viewPaymentButton.setEnabled(true);
				} else {
					viewPaymentButton.setEnabled(false);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	private JPanel getActionButtonPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		viewPaymentButton = new JButton("View Payment");
		viewPaymentButton.setActionCommand("viewPayment");
		viewPaymentButton.setEnabled(false);
		viewPaymentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							new PaymentDetailsUI(selectedPayment);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Can't display PaymentDetailsUI!",
									"Payment Details Display error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		});
		
		panel.add(viewPaymentButton);
		
		return panel;
	}
	
	public void addPayment(Object input) {
		model.addPayment((Payment) input);
		
		updateTitledBorder();
	}
	
	@Override
	public void update(String className, Object arg) {
		if(className.equals("UserBasicSearchPanel")){
			user = (User) arg;
		}
		model.replaceData(user, fine);
		
		updateTitledBorder();
	}
	
	private void updateTitledBorder(){
		titledBorder.setTitle("Payments: "+model.getRowCount()+" records found");
		repaint();
	}
}