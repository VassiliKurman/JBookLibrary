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
import java.util.Calendar;

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

import vkurman.jbooklibrary.core.AdminFines;
import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.Payment;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.FineDetailsUI;
import vkurman.jbooklibrary.gui.NewPaymentUI;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.gui.filters.StatusFilterPanel;
import vkurman.jbooklibrary.interfaces.FilterObserver;

/**
 * Panel with list of fines and buttons to perform actions on
 * selected fine.
 * 
 * <p>Date created: 2013.07.28
 *
 * @author Vassili Kurman
 * @version 0.1
 */
public class FinesPanel extends JPanel implements FilterObserver {
	private static final long serialVersionUID = -8168929315211657473L;
	private UserBasicSearchPanel userBasicSearchPanel;
	private TitledBorder titledBorder;
	private long loanID, userID;
	private JTable table;
	private FinesTableModel model;
	private GeneralStatus generalStatus;
	private StatusFilterPanel statusFilterPanel;
	private Fine fine;
	private JButton viewFineButton;
	private JButton clearFineButton;
	private JButton payFineButton;
	private PaymentsUpdateControl control;
	
	public FinesPanel() {
		this(false, false, GeneralStatus.ACTIVE, 0L, 0L);
	}
	
	public FinesPanel(boolean showStatusFilters) {
		this(showStatusFilters, false, GeneralStatus.ACTIVE, 0L, 0L);
	}
	
	public FinesPanel(boolean showStatusFilters, boolean showUserSearchPanel) {
		this(showStatusFilters, showUserSearchPanel, GeneralStatus.ACTIVE, 0L, 0L);
	}
	
	public FinesPanel(GeneralStatus generalStatus, long userID) {
		this(false, false, generalStatus, 0L, userID);
	}
	
	public FinesPanel(long loanID, GeneralStatus generalStatus) {
		this(false, false, generalStatus, loanID, 0L);
	}
	
	public FinesPanel(GeneralStatus generalStatus, long loanID, long userID){
		this(false, false, generalStatus, loanID, userID);
	}
	
	public FinesPanel(
			boolean showStatusFilters,
			boolean showUserSearchPanel,
			GeneralStatus generalStatus,
			long loanID,
			long userID){
		this.generalStatus = generalStatus;
		this.loanID = loanID;
		this.userID = userID;
		this.fine = null;
		
		control = new PaymentsUpdateControl();
		model = new FinesTableModel(generalStatus, loanID, userID);
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Fines: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		showUI(showStatusFilters, showUserSearchPanel);
	}
	
	private void showUI(boolean showStatusFilters, boolean showUserSearchPanel){
		setLayout(new BorderLayout(5, 5));
		setBorder(titledBorder);
		
		if(showStatusFilters || showUserSearchPanel){
			add(getFilterPane(showStatusFilters, showUserSearchPanel), BorderLayout.PAGE_START);
		}
		add(getTablePane(), BorderLayout.CENTER);
		add(getActionButtonPane(), BorderLayout.PAGE_END);
	}
	
	private JScrollPane getTablePane() {
		table = new JTable(model);
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(table.getSelectedRow() > -1) {
					// TODO
					// Set fine from selected row
					fine = (Fine) model.getFine(table.getSelectedRow());
					// View button
					viewFineButton.setEnabled(true);
					// Fine button
					if(fine.isActive()){
						clearFineButton.setEnabled(true);
					} else {
						clearFineButton.setEnabled(false);
					}
					// Pay button
					if(fine.isFinePaid()){
						payFineButton.setEnabled(false);
					}else{
						payFineButton.setEnabled(true);
					}
				} else {
					viewFineButton.setEnabled(false);
					clearFineButton.setEnabled(false);
					payFineButton.setEnabled(false);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(60);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	private JPanel getActionButtonPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		viewFineButton = new JButton("View Fine");
		viewFineButton.setActionCommand("viewFine");
		viewFineButton.setEnabled(false);
		viewFineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							FineDetailsUI dialog = new FineDetailsUI(fine);
							if(!dialog.isShowing()) {
								// Updating UI
								changeFineStatusAfterPayment(null);
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
					    			null,
					    			"Can't display FineDetailsUI!");
						}
					}
				});
			}
		});
		
		clearFineButton = new JButton("Clear Fine");
		clearFineButton.setActionCommand("clearFine");
		clearFineButton.setEnabled(false);
		clearFineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				clearFine();
			}
		});
		
		payFineButton = new JButton("Pay Fine");
		payFineButton.setActionCommand("payFine");
		payFineButton.setEnabled(false);
		payFineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							TransObject obj = new TransObject();
							NewPaymentUI dialog = new NewPaymentUI(fine, obj);
							if(dialog.isOKPressed()) {
								// Updating UI
								changeFineStatusAfterPayment(obj);
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
					    			null,
					    			"Can't display FineDetailsUI!");
						}
					}
				});
			}
		});
		
		panel.add(viewFineButton);
		panel.add(clearFineButton);
		panel.add(payFineButton);
		
		return panel;
	}
	
	private void clearFine(){
		if(JOptionPane.showConfirmDialog(FinesPanel.this,
				"Do you really want to clear the fine?",
				"Fine clearance confirmaion",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			// Saving initial generalStatus
			GeneralStatus generalStatus = fine.getStatus();
			// Setting generalStatus to INACTIVE
			fine.setStatus(GeneralStatus.INACTIVE);
			fine.setFinePaidDate(Calendar.getInstance());
			// Updating DB and displaying message
			if(AdminFines.getInstance().clearFine(fine)){
				UIDisplayManager.displayInformationMessage(
		    			null,
		    			"Fine cleared!");
				// Updating UI
				changeFineStatusAfterPayment(null);
			} else {
				// Setting generalStatus to INACTIVE
				fine.setStatus(generalStatus);
				// Displaying error message
				UIDisplayManager.displayErrorMessage(
		    			null,
		    			"Can't clear Fine!");
			}
		}
	}
	
	public void setControl(PaymentsUpdateControl control){
		this.control = control;
	}
	
	private JPanel getFilterPane(boolean showStatusFilters, boolean showUserSearchPanel){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		if(showStatusFilters){
			// Creating StatusFilterPanel and registering itself in it
			statusFilterPanel = new StatusFilterPanel(generalStatus);
			statusFilterPanel.register(this);
			
			panel.add(statusFilterPanel);
		}
		if(showUserSearchPanel){
			// Creating StatusFilterPanel and registering itself in it
			userBasicSearchPanel = new UserBasicSearchPanel();
			userBasicSearchPanel.register(this);
			
			panel.add(userBasicSearchPanel);
		}
		
		return panel;
	}
	
	private void changeFineStatusAfterPayment(TransObject obj){
		if(fine.isFinePaid()){
			payFineButton.setEnabled(false);
			
			// Updating table model
			refresh();
		} else {
			payFineButton.setEnabled(true);
			// Update cell with new value
			model.fireTableRowsUpdated(
					table.getSelectedRow(),
					table.getSelectedRow());
		}
		
		// Notify update controller about event
		if (control != null && obj != null) {
			control.addAction((Payment) obj.getObject());
		}
		
		updateTitledBorder();
	}
	
	/**
	 * This method is asking table model to update it's
	 * data passing current generalStatus, user and loan data
	 * as parameters.
	 */
	public void refresh(){
		// Updating table data
		model.replaceData(generalStatus, loanID, userID);
		
		// Updating titled border
		updateTitledBorder();
	}
	
	@Override
	public void update(String className, Object arg) {
		if(className.equals("UserBasicSearchPanel")){
			if(arg != null){
				userID = ((User) arg).getUserID();
			} else {
				userID = 0L;
			}
		} else if(className.equals("StatusFilterPanel")){
			generalStatus = (GeneralStatus) arg;
		}
		
		refresh();
	}
	
	private void updateTitledBorder(){
		titledBorder.setTitle("Fines: "+model.getRowCount()+" records found");
		repaint();
	}
}