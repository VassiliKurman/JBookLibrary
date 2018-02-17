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

import vkurman.jbooklibrary.enums.ItemStatus;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.interfaces.FilterSubject;

/**
 * Filter panel that allows to filter items by their status.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ItemStatusFilterPanel extends JPanel implements FilterSubject, ActionListener {
	
	private static final long serialVersionUID = -6119150490144661069L;
	private ButtonGroup group;
	private JRadioButton allFilterButton;
	private JRadioButton disposedFilterButton;
	private JRadioButton onLoanFilterButton;
	private JRadioButton onShelfFilterButton;
	private JRadioButton reservedFilterButton;
	private JRadioButton unknownFilterButton;
	private List<FilterObserver> observers;
	
	public ItemStatusFilterPanel(){
		this(ItemStatus.ONSHELF);
	}
	
	public ItemStatusFilterPanel(ItemStatus status){
		// Creating an ArrayList to hold all observers
		observers = new ArrayList<FilterObserver>();
		
		showUI();
	}
	
	private void showUI(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"GeneralStatus Filter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		addButtons();
	}
	
	private void addButtons(){
		//Create the radio buttons.
	    allFilterButton = new JRadioButton("All");
	    allFilterButton.setMnemonic(KeyEvent.VK_A);
	    allFilterButton.setActionCommand("ALL");
	    allFilterButton.addActionListener(this);
	    allFilterButton.setSelected(true);
	    
	    disposedFilterButton = new JRadioButton("Disposed");
	    disposedFilterButton.setMnemonic(KeyEvent.VK_D);
	    disposedFilterButton.setActionCommand("DISPOSED");
	    disposedFilterButton.addActionListener(this);
	    
	    onLoanFilterButton = new JRadioButton("On Loan");
	    onLoanFilterButton.setMnemonic(KeyEvent.VK_L);
	    onLoanFilterButton.setActionCommand("ONLOAN");
	    onLoanFilterButton.addActionListener(this);
	    
	    onShelfFilterButton = new JRadioButton("On Shelf");
	    onShelfFilterButton.setMnemonic(KeyEvent.VK_S);
	    onShelfFilterButton.setActionCommand("ONSHELF");
	    onShelfFilterButton.addActionListener(this);
	    
	    reservedFilterButton = new JRadioButton("Reserved");
	    reservedFilterButton.setMnemonic(KeyEvent.VK_R);
	    reservedFilterButton.setActionCommand("RESERVED");
	    reservedFilterButton.addActionListener(this);
	    
	    unknownFilterButton = new JRadioButton("Unknown");
	    unknownFilterButton.setMnemonic(KeyEvent.VK_U);
	    unknownFilterButton.setActionCommand("UNKNOWN");
	    unknownFilterButton.addActionListener(this);
	    
	    //Group the radio buttons.
	    group = new ButtonGroup();
	    group.add(allFilterButton);
	    group.add(disposedFilterButton);
	    group.add(onLoanFilterButton);
	    group.add(onShelfFilterButton);
	    group.add(reservedFilterButton);
	    group.add(unknownFilterButton);
	    
		this.add(allFilterButton);
		this.add(onLoanFilterButton);
		this.add(onShelfFilterButton);
		this.add(reservedFilterButton);
		this.add(disposedFilterButton);
		this.add(unknownFilterButton);
	}
	
	public String getSelectionActionCommand(){
		return group.getSelection().getActionCommand();
	}
	
	@Override
	public void setEnabled(boolean arg){
		if(arg == true){
			allFilterButton.setEnabled(true);
			disposedFilterButton.setEnabled(true);
			onLoanFilterButton.setEnabled(true);
			onShelfFilterButton.setEnabled(true);
			reservedFilterButton.setEnabled(true);
			unknownFilterButton.setEnabled(true);
		} else {
			allFilterButton.setEnabled(false);
			disposedFilterButton.setEnabled(false);
			onLoanFilterButton.setEnabled(false);
			onShelfFilterButton.setEnabled(false);
			reservedFilterButton.setEnabled(false);
			unknownFilterButton.setEnabled(false);
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
	 * setStatus method notifies observers about change
	 * @param generalStatus
	 */
	public void changeStatus(Object arg) {
		notifyObserver(arg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String status = e.getActionCommand();
		if(status.equalsIgnoreCase("ALL")){
			changeStatus(null);
		} else if(status.equalsIgnoreCase("DISPOSED")) {
			changeStatus(ItemStatus.DISPOSED);
		} else if(status.equalsIgnoreCase("ONLOAN")) {
			changeStatus(ItemStatus.ONLOAN);
		} else if(status.equalsIgnoreCase("ONSHELF")) {
			changeStatus(ItemStatus.ONSHELF);
		} else if(status.equalsIgnoreCase("RESERVED")) {
			changeStatus(ItemStatus.RESERVED);
		} else if(status.equalsIgnoreCase("UNKNOWN")) {
			changeStatus(ItemStatus.UNKNOWN);
		}
	}
}