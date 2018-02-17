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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Panel that has a spinner with years.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class JYearPanel extends JPanel implements ChangeListener, CalendarObservable {
	
	private static final long serialVersionUID = -3086135300320098793L;
	private static final int SPINNER_STEP_SIZE = 1;
	private List<CalendarObserver> observers;
	private SpinnerModel model;
	private JSpinner spinner;
	private int selectedYear;
	private int minYear;
	private int maxYear;
	
	/**
	 * Default constructor that sets selected year to current
	 * calendar year.
	 */
    public JYearPanel() {
        this(((Calendar) Calendar.getInstance(
        		Locale.getDefault()).clone()).get(Calendar.YEAR));
    }
    
    /**
     * Main constructor that sets specified year as selected year.
     * 
     * @param year
     */
    public JYearPanel(int year) {
    	observers = new ArrayList<CalendarObserver>();
    	selectedYear = year;
        minYear = year - 150;
        maxYear = year + 150;
        
        showUI();
    }
    
    /**
     * Creates and displays UI.
     */
    private void showUI(){
    	model = new SpinnerNumberModel(
    			selectedYear,
    			minYear,
    			maxYear,
    			SPINNER_STEP_SIZE);
        spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "####"));
        spinner.addChangeListener(this);
        
    	add(spinner);
    }
    
    /**
     * Sets selected year to the year specified as parameter.
     * 
     * @param year
     */
	public void setYear(int year) {
		if (selectedYear != year && year >= minYear && year <= maxYear) {
			selectedYear = year;
			// Changing value in the spinner model
			model.setValue(selectedYear);
		}
	}
	
	/**
	 * Returns selected year.
	 * 
	 * @return int - selected year
	 */
	public int getYear() {
		return selectedYear;
	}
	
	/**
	 * Returns maximum year.
	 * 
	 * @return int - maximum displayed year
	 */
	public int getMaxYear() {
		return maxYear;
	}
	
    /**
     * Returns minimum year.
     * 
     * @return int - minimum year that displayed
     */
    public int getMinYear() {
        return minYear;
    }
    
	@Override
	public void stateChanged(ChangeEvent arg0) {
        selectedYear = (Integer) model.getValue();
        notifyObserver(Calendar.YEAR, selectedYear);
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