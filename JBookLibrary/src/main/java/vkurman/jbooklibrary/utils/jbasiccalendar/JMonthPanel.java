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

package vkurman.jbooklibrary.utils.jbasiccalendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * <code>JMonthPanel</code> panel that has a combo box with a list of months.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class JMonthPanel extends JPanel implements ActionListener, CalendarObservable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6723419953967362658L;
	
	private List<CalendarObserver> observers;
	private String[] months = {
			"January",
			"February",
			"March",
			"April",
			"May",
			"June",
			"July",
			"August",
			"September",
			"October",
			"November",
			"December"};
	private JComboBox cbxMonths;
	private int selectedMonth;
	
	/**
	 * Default constructor that sets selected month to current calendar
	 * month.
	 */
    public JMonthPanel() {
        this(((Calendar) Calendar.getInstance().clone()).get(Calendar.MONTH));
    }
    
    /**
     * Main constructor.
     * 
     * @param month
     */
    public JMonthPanel(int month) {
    	observers = new ArrayList<CalendarObserver>();
    	selectedMonth = month;
    	
        showUI();
    }
    
    /**
     * Creates and displays UI.
     */
    private void showUI(){
    	cbxMonths = new JComboBox(months);
    	cbxMonths.setSelectedIndex(selectedMonth);
    	cbxMonths.setEditable(false);
    	cbxMonths.addActionListener(this);

    	add(cbxMonths);
    }
    
    /**
     * Sets selected month to specified month.
     * 
     * @param month
     */
	public void setMonth(int month) {
		if (month >= 0 && month <=11) {
			selectedMonth = month;
			// Setting selected month in combo box
			cbxMonths.setSelectedIndex(selectedMonth);
			notifyObserver(Calendar.MONTH, selectedMonth);
		}
	}
	
	/**
	 * Returns selected month.
	 * 
	 * @return int - index of month starting from 0
	 */
	public int getMonth() {
		return selectedMonth;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		selectedMonth = (int) cbxMonths.getSelectedIndex();
		notifyObserver(Calendar.MONTH, selectedMonth);
	}

	@Override
	public void register(CalendarObserver observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObserver(Object arg1, Object arg2) {
		for(CalendarObserver observer : observers){
			observer.update(arg1, arg2);
		}
	}
}