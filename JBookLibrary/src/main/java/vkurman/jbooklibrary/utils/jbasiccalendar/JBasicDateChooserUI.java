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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * User interface that displays <code>JBasicCalendarPanel</code>
 * only. Once date is selected on <code>JDayPanel</code>, than this
 * dialog is disposed.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class JBasicDateChooserUI extends JDialog implements UpdateObserver{
	
	private static final long serialVersionUID = 3787957172315086122L;
	private JBasicCalendarPanel calendarPanel;
	
	/**
	 * Default constructor that sets current time calendar.
	 */
	public JBasicDateChooserUI(Component parent){
		this(parent, Calendar.getInstance(Locale.getDefault()));
	}
	
	/**
	 * Main constructor that sets specified calendar.
	 * 
	 * @param calendar
	 */
	public JBasicDateChooserUI(Component parent, Calendar calendar){
		if(calendar != null){
			calendarPanel = new JBasicCalendarPanel(calendar);
		} else {
			calendarPanel = new JBasicCalendarPanel();
		}
		
		showUI();
		
		setLocationRelativeTo(parent);
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setLocationByPlatform(true);
		setResizable(false);
		
		add(calendarPanel, BorderLayout.CENTER);
		
		pack();
	}
	
	/**
	 * Returns </code>Date</code> object from calendar.
	 * 
	 * @return Date
	 */
	public Date getDate(){
		return calendarPanel.getDate();
	}
	
	/**
	 * Returns calendar.
	 * 
	 * @return Calendar
	 */
	public Calendar getCalendar(){
		return calendarPanel.getCalendar();
	}
	
	/**
	 * Returns calendar time in milliseconds.
	 * 
	 * @return long
	 */
	public long getTimeInMillis(){
		return calendarPanel.getTimeInMillis();
	}
	
	/**
	 * If date is set, that <code>true</code> is returned.
	 * 
	 * @return boolean
	 */
	public boolean isDateSet(){
		return calendarPanel.isDateSet();
	}

	@Override
	public void updated(boolean arg) {
		dispose();
	}
}