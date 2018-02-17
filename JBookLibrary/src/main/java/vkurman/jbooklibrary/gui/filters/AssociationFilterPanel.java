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

package vkurman.jbooklibrary.gui.filters;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import vkurman.jbooklibrary.gui.components.BookBasicSearchPanel;
import vkurman.jbooklibrary.gui.components.UserBasicSearchPanel;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.interfaces.FilterSubject;

/**
 * Filter panel that allows to filter results with books or users.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AssociationFilterPanel extends JPanel implements FilterSubject, FilterObserver {
	private static final long serialVersionUID = -3771571566480972688L;
	private List<FilterObserver> observers;
	private BookBasicSearchPanel bookBasicSearchPanel;
	private UserBasicSearchPanel userBasicSearchPanel;
	private boolean displayBook,
		displayUser;
	
	public AssociationFilterPanel(boolean displayBook, boolean displayUser){
		// Creating an ArrayList to hold all observers
		observers = new ArrayList<FilterObserver>();
		
		// Creating BookBasicSearchPanel and registering itself in it
		bookBasicSearchPanel = new BookBasicSearchPanel(false);
		bookBasicSearchPanel.register(this);
		
		// Creating BookBasicSearchPanel and registering itself in it
		userBasicSearchPanel = new UserBasicSearchPanel(false);
		userBasicSearchPanel.register(this);
		
		this.displayBook = displayBook;
		this.displayUser = displayUser;
		
		showUI();
	}
	
	private void showUI(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(addRadioButtons());
		if(displayBook){
			add(bookBasicSearchPanel);
		}
		if(displayUser){
			add(userBasicSearchPanel);
		}
	}
	
	private JPanel addRadioButtons(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Association Filter",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		
		//Create the radio buttons.
	    JRadioButton noneFilterButton = new JRadioButton("None");
	    noneFilterButton.setMnemonic(KeyEvent.VK_N);
	    noneFilterButton.setActionCommand("None");
	    noneFilterButton.setSelected(true);
	    
	    JRadioButton userFilterButton = new JRadioButton("User");
	    userFilterButton.setMnemonic(KeyEvent.VK_U);
	    userFilterButton.setActionCommand("User");
	    
	    JRadioButton bookFilterButton = new JRadioButton("Book");
	    bookFilterButton.setMnemonic(KeyEvent.VK_B);
	    bookFilterButton.setActionCommand("Inactive");
	    
	    //Group the radio buttons.
	    ButtonGroup groupB = new ButtonGroup();
	    groupB.add(noneFilterButton);
	    groupB.add(userFilterButton);
	    groupB.add(bookFilterButton);

	    //Register a listener for the radio buttons.
	    noneFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						bookBasicSearchPanel.setEnabled(false);
						userBasicSearchPanel.setEnabled(false);
						
						update("AssociationFilterPanel", "none");
					}
				});
			}
		});
	    
	    userFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						bookBasicSearchPanel.setEnabled(false);
						userBasicSearchPanel.setEnabled(true);
						
						update("AssociationFilterPanel", "user");
					}
				});
			}
		});
		
		bookFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						userBasicSearchPanel.setEnabled(false);
						bookBasicSearchPanel.setEnabled(true);
						
						update("AssociationFilterPanel", "book");
					}
				});
			}
		});
		
		panel.add(noneFilterButton);
		panel.add(userFilterButton);
		panel.add(bookFilterButton);
		
		return panel;
	}
	
	@Override
	public void register(FilterObserver observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(FilterObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * This method does nothing. Please use update(String, Object)
	 * method to pass data further
	 */
	@Override
	public void notifyObserver(Object user) {
  	}
	
	/**
	 * This method just passes references to observer
	 */
	@Override
	public void update(String className, Object arg) {
		for(FilterObserver observer : observers){
			observer.update(className, arg);
		}
	}
}