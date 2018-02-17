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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.Library;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * Displays information about database structure and
 * how many records each table have.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class DBInfoUI extends JDialog {
	private static final long serialVersionUID = 459458325644249847L;
	
	/**
	 * Constructor.
	 * 
	 * @param parent
	 */
	public DBInfoUI() {
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 * 
	 * @param parent
	 */
	private void showUI(){
		setTitle("Database Information - " + AdminPrefs.LIBRARY_NAME);
		setLayout(new BorderLayout(10, 10));
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		add(getInfoArea(), BorderLayout.CENTER);
		add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with details about database.
	 * 
	 * @return JScrollPane
	 */
	private JPanel getInfoArea(){
		JPanel panel = new JPanel(new SpringLayout());
		int rows = 0;
		
		try {
			ResultSet rs = Library.getInstance().getConnection().createStatement().executeQuery("SHOW TABLES");
			while(rs.next()){
				rows++;
				
				final String table = rs.getString("TABLE_NAME");
				panel.add(new JLabel(table));
				ResultSet rsci = Library.getInstance().getConnection().createStatement().executeQuery("SELECT COUNT(ID) FROM "+rs.getString("TABLE_NAME"));
				rsci.next();
				panel.add(new JLabel(" : " + rsci.getInt(1) + " records"));
				final JButton button = new JButton("Details");
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						EventQueue.invokeLater(new Runnable() {
							@Override
							public void run() {
								new TableDetails(table);
							}
						});
					}
				});
				panel.add(button);
			}
		} catch (SQLException e) {
			UIDisplayManager.displayErrorMessage(
					this,
					"Error retrieving data!!!");
		}
		
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(
				panel,
				rows, 3,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
	
	/**
	 * Class that extends JDialog and displays table details.
	 * 
	 * <p>Date created: 2013.07.28
	 * 
	 * @author Vassili Kurman
	 * @version 0.1
	 */
	private class TableDetails extends JDialog {
		
		/**
		 * Auto-generated serialVersionUID
		 */
		private static final long serialVersionUID = 7593503265350152605L;
		private String tableName;
		
		/**
		 * Constructor.
		 * 
		 * @param tableName
		 */
		public TableDetails(String tableName){
			this.tableName = tableName;
			
			showUI();
		}
		
		/**
		 * Creates and displays UI.
		 */
		private void showUI(){
			setTitle(tableName);
			setLayout(new BorderLayout(10, 10));
			setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			add(createContent(), BorderLayout.CENTER);
			add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
			
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		}
		
		/**
		 * Creates and returns content.
		 * 
		 * @return JPanel
		 */
		private JPanel createContent(){
			JPanel panel = new JPanel(new BorderLayout(5, 5));
			
			JPanel label = new JPanel(new BorderLayout(5, 5));
			label.add(new JLabel("Columns: ", JLabel.TRAILING), BorderLayout.PAGE_START);
			
			JPanel tables = new JPanel(new GridLayout(0, 1, 5, 5));
			// Displaying column names
			ResultSet rs = null;
			try {
				rs = Library.getInstance().getConnection().createStatement().executeQuery("SHOW COLUMNS FROM " + tableName);
				while(rs.next()){
					tables.add(new JLabel(rs.getString("COLUMN_NAME")));
				}
			} catch (SQLException e) {
				UIDisplayManager.displayErrorMessage(
						this,
						"Error retrieving data!!!");
			}
			
			panel.add(label, BorderLayout.LINE_START);
			panel.add(tables, BorderLayout.CENTER);
			
			return panel;
		}
	}
}