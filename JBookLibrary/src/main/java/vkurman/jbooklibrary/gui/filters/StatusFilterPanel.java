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




import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.interfaces.FilterSubject;

/**
 * Panel with buttons to filter objects by their </code>GeneralStatus</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class StatusFilterPanel extends JPanel implements FilterSubject {
	
	private static final long serialVersionUID = -7045069478538827521L;
	private List<FilterObserver> observers;
	private GeneralStatus generalStatus;
	private JRadioButton allFilterButton,
		activeFilterButton,
		inactiveFilterButton;
	
	/**
	 * Constructor.
	 */
	public StatusFilterPanel(){
		this(GeneralStatus.ACTIVE);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param generalStatus
	 */
	public StatusFilterPanel(GeneralStatus generalStatus){
		// Creating an ArrayList to hold all observers
		observers = new ArrayList<FilterObserver>();
		this.generalStatus = generalStatus;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
			"Status Filter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		addButtons();
	}
	
	/**
	 * Adds buttons.
	 */
	private void addButtons(){
		//Create the radio buttons.
	    allFilterButton = new JRadioButton("All");
	    allFilterButton.setMnemonic(KeyEvent.VK_Z);
	    allFilterButton.setActionCommand("All");
	    
	    activeFilterButton = new JRadioButton("Active");
	    activeFilterButton.setMnemonic(KeyEvent.VK_A);
	    activeFilterButton.setActionCommand("Active");
	    
	    inactiveFilterButton = new JRadioButton("Inactive");
	    inactiveFilterButton.setMnemonic(KeyEvent.VK_I);
	    inactiveFilterButton.setActionCommand("Inactive");
	    
	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(allFilterButton);
	    group.add(activeFilterButton);
	    group.add(inactiveFilterButton);
	
	    //Register a listener for the radio buttons.
	    allFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						changeStatus(null);
					}
				});
			}
		});
	    
	    activeFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						changeStatus(GeneralStatus.ACTIVE);
					}
				});
			}
		});
		
		inactiveFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						changeStatus(GeneralStatus.INACTIVE);
					}
				});
			}
		});
		
		checkSelection();
		
		this.add(allFilterButton);
		this.add(activeFilterButton);
		this.add(inactiveFilterButton);
	}
	
	/**
	 * Selects radio button depending on <code>GeneralStatus</code>
	 * value.
	 */
	private void checkSelection(){
		switch (generalStatus){
			case ACTIVE:
				activeFilterButton.setSelected(true);
				break;
			case INACTIVE:
				inactiveFilterButton.setSelected(true);
				break;
			default:
				allFilterButton.setSelected(true);
			}
	}
	
	@Override
	public void setEnabled(boolean arg){
		if(arg == true){
			allFilterButton.setEnabled(true);
			activeFilterButton.setEnabled(true);
			inactiveFilterButton.setEnabled(true);
		} else {
			allFilterButton.setEnabled(false);
			activeFilterButton.setEnabled(false);
			inactiveFilterButton.setEnabled(false);
		}
	}
	
	@Override
	public void register(FilterObserver observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(FilterObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver(Object status) {
		for(FilterObserver observer : observers){
			observer.update(this.getClass().getSimpleName(), status);
		}
  	}
	
	/**
	 * This method notifies observers about change
	 * @param generalStatus
	 */
	private void changeStatus(Object arg) {
		notifyObserver(arg);
	}
}