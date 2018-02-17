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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.IDCardDeactivationDialog;
import vkurman.jbooklibrary.gui.IDCardDetailsUI;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.gui.filters.StatusFilterPanel;
import vkurman.jbooklibrary.interfaces.FilterObserver;

/**
 * Panel that displays IDCards and buttons to perform actions
 * on selected <code>IDCard</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
*/
public class IDCardsPanel extends JPanel implements FilterObserver {
	private static final long serialVersionUID = -8168929315211657473L;
	private UserBasicSearchPanel userBasicSearchPanel;
	private TitledBorder titledBorder;
	private long userID;
	private JTable table;
	private IDCardsTableModel model;
	private GeneralStatus generalStatus;
	private StatusFilterPanel statusFilterPanel;
	private JButton viewIDCardButton;
	private JButton deactivateButton;
	private IDCard idCard;
	
	public IDCardsPanel() {
		this(false, GeneralStatus.ACTIVE, 0L);
	}
	
	public IDCardsPanel(boolean showStatusFilters) {
		this(showStatusFilters, GeneralStatus.ACTIVE, 0L);
	}
	
	public IDCardsPanel(boolean showStatusFilters, GeneralStatus generalStatus) {
		this(showStatusFilters, generalStatus, 0L);
	}
	
	public IDCardsPanel(GeneralStatus generalStatus, long userID){
		this(false, generalStatus, userID);
	}
	
	public IDCardsPanel(
			boolean showStatusFilters,
			GeneralStatus generalStatus,
			long userID){
		this.generalStatus = generalStatus;
		this.userID = userID;
		this.idCard = null;
		
		model = new IDCardsTableModel(generalStatus, userID);
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"IDCards: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		showUI(showStatusFilters);
	}
	
	private void showUI(boolean showStatusFilters){
		setLayout(new BorderLayout(5, 5));
		setBorder(titledBorder);
		
		if(showStatusFilters){
			// Creating StatusFilterPanel and registering itself in it
			statusFilterPanel = new StatusFilterPanel(generalStatus);
			statusFilterPanel.register(this);
			
			// Creating StatusFilterPanel and registering itself in it
			userBasicSearchPanel = new UserBasicSearchPanel();
			userBasicSearchPanel.register(this);
			
			add(getFilterPane(showStatusFilters), BorderLayout.PAGE_START);
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
					// Set fine from selected row
					idCard = (IDCard) model.getIDCard(table.getSelectedRow());
					
					viewIDCardButton.setEnabled(true);
					if(idCard.isActive()){
						deactivateButton.setEnabled(true);
					} else {
						deactivateButton.setEnabled(false);
					}
				} else {
					viewIDCardButton.setEnabled(false);
					deactivateButton.setEnabled(false);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.getColumnModel().getColumn(4).setPreferredWidth(10);
        table.getColumnModel().getColumn(5).setPreferredWidth(10);
        table.getColumnModel().getColumn(6).setPreferredWidth(10);
        table.getColumnModel().getColumn(7).setPreferredWidth(10);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	private JPanel getActionButtonPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		viewIDCardButton = new JButton("View IDCard");
		viewIDCardButton.setActionCommand("viewIDCard");
		viewIDCardButton.setEnabled(false);
		viewIDCardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							IDCardDetailsUI dialog = new IDCardDetailsUI(idCard);
							if(!dialog.isShowing()){
								refresh();
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									IDCardsPanel.this,
									"Can't display IDCardDetailsUI!");
						}
					}
				});
			}
		});
		
		deactivateButton = new JButton("Deactivate IDCard");
		deactivateButton.setActionCommand("Deactivate");
		deactivateButton.setEnabled(false);
		deactivateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							IDCardDeactivationDialog dialog = new IDCardDeactivationDialog(idCard);
							if(!dialog.isShowing()) {
								refresh();
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									IDCardsPanel.this,
									"Can't display IDCardDeactivationDialog!");
						}
					}
				});
			}
		});
		
		panel.add(viewIDCardButton);
		panel.add(deactivateButton);
		
		return panel;
	}
	
	private JPanel getFilterPane(boolean showStatusFilters){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		if(showStatusFilters){
			panel.add(statusFilterPanel);
			panel.add(userBasicSearchPanel);
		}
		
		return panel;
	}
	
	public void addIDCard(IDCard idCard){
		if(generalStatus == null || generalStatus == idCard.getGeneralStatus()){
			model.addIDCard(idCard);
			
			updateTitledBorder();
		}
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
		model.replaceData(generalStatus, userID);
		
		updateTitledBorder();
	}
	
	/**
	 * Forces table model to refresh data.
	 */
	public void refresh(){
		model.replaceData(generalStatus, userID);
		
		updateTitledBorder();
	}
	
	private void updateTitledBorder(){
		titledBorder.setTitle("IDCards: "+model.getRowCount()+" records found");
		repaint();
	}
}