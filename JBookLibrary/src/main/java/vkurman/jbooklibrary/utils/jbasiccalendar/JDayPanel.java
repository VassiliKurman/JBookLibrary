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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import vkurman.jbooklibrary.core.Holiday;

/**
 * Panel that has a grid of btnDays.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class JDayPanel extends JPanel implements ActionListener,
			FocusListener, CalendarObservable {
	
	/**
	 * Auto-generated serialVersionUID
	 */
	private static final long serialVersionUID = 8757839486922328605L;
	/**
	 * Background colour for week number and day name buttons.
	 */
	public static final Color BACKGROUND_COLOUR_NAMES = new Color(210, 228, 238);
	/**
	 * Background colour for active month button.
	 */
	public static final Color BACKGROUND_COLOUR_ACTIVE_MONTH = Color.WHITE;
	/**
	 * Background colour for inactive month button.
	 */
	public static final Color BACKGROUND_COLOUR_INACTIVE_MONTH = new Color(236, 233, 216);
	/**
	 * Background colour for selected button.
	 */
	public static final Color BACKGROUND_COLOUR_SELECTED = Color.ORANGE;
	/**
	 * Background colour for current date button.
	 */
	public static final Color BACKGROUND_COLOUR_CURRENT = Color.WHITE;
	/**
	 * Background colour for special date button.
	 */
	public static final Color BACKGROUND_COLOUR_SPECIAL = new Color(255, 0, 255);
	/**
	 * Foreground colour for weekday button on active month.
	 */
	public static final Color FOREGROUND_COLOUR_ACTIVE_WEEKDAY = new Color(0, 90, 164);
	/**
	 * Foreground colour for Sunday button on active month.
	 */
	public static final Color FOREGROUND_COLOUR_ACTIVE_SUNDAY = new Color(164, 0, 0);
	/**
	 * Foreground colour for weekday button, including Saturday,on inactive month. 
	 */
	public static final Color FOREGROUND_COLOUR_INACTIVE_WEEKDAY = Color.LIGHT_GRAY;
	/**
	 * Foreground  colour for Sunday button on inactive month.
	 */
	public static final Color FOREGROUND_COLOUR_INACTIVE_SUNDAY = new Color(255, 0, 0);
	/**
	 * Foreground colour for current date button. 
	 */
	public static final Color FOREGROUND_COLOUR_CURRENT = Color.GREEN;
	/**
	 * Foreground  colour for selected button.
	 */
	public static final Color FOREGROUND_COLOUR_SELECTED = Color.BLUE;
	
	private static final Insets BUTTON_INSETS = new Insets(0, 0, 0, 0);
	private static final int ROWS = 7;
	private static final int COLUMNS = 8;
	
	private List<CalendarObserver> observers;
	private CalendarButton[] buttons;
	private CalendarButton btnSelected;
	private Calendar calendar;
	private Calendar currentDate;
	
	/**
	 * Constructor.
	 * 
	 * @param calendar
	 */
	public JDayPanel(Calendar calendar) {
		observers = new ArrayList<CalendarObserver>();
		
		currentDate = Calendar.getInstance(Locale.getDefault());
		this.calendar = calendar;
		
		setLayout(new GridLayout(ROWS, COLUMNS, 0, 0));
		setBackground(BACKGROUND_COLOUR_NAMES);
		setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOUR_SELECTED));
		
		btnSelected = null;
		
		buttons = new CalendarButton[ROWS * COLUMNS];
		for (int row = 0; row < ROWS; row++) {
			for (int column = 0; column < COLUMNS; column++) {
				int index = column + (8 * row);
				
				if (row == 0) {
					buttons[index] = new NameButton();
					buttons[index].setFocusPainted(false);
				} else {
					if(column == 0){
						buttons[index] = new NameButton();
					} else {
						buttons[index] = new DayButton();
					}
				}
				buttons[index].setMargin(BUTTON_INSETS);
				add(buttons[index]);
			}
		}
		
		displayDayNames();
		
		init();
	}
	
	/**
	 * Displays weeks and days for specified year and month.
	 */
	protected void init() {
		displayWeeks();
		displayDays();
	}
	
	/**
	 * Displays day names on buttons.
	 */
	private void displayDayNames() {
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.getDefault());
		String[] dayNames = dateFormatSymbols.getShortWeekdays();
		
		int day = firstDayOfWeek;
		
		for (int i = 1; i < 8; i++) {
			buttons[i].setText(dayNames[day]);

			if (day == 1) {
				buttons[i].setForeground(FOREGROUND_COLOUR_ACTIVE_SUNDAY);
			} else {
				buttons[i].setForeground(FOREGROUND_COLOUR_ACTIVE_WEEKDAY);
			}

			if (day < 7) {
				day++;
			} else {
				day -= 6;
			}
		}
	}
	
	/**
	 * Displays week numbers.
	 */
	protected void displayWeeks() {
		Calendar tmpCal = (Calendar) calendar.clone();
		tmpCal.set(Calendar.DAY_OF_MONTH, 1);
		tmpCal.set(Calendar.HOUR_OF_DAY, 0);
		tmpCal.set(Calendar.MINUTE, 0);
		tmpCal.set(Calendar.SECOND, 0);
		tmpCal.set(Calendar.MILLISECOND, 0);
		
		// Date formatter for week numbers
		SimpleDateFormat sdf = new SimpleDateFormat("ww");
		
		// First week number for calendar panel
		int week = tmpCal.get(Calendar.WEEK_OF_YEAR);
		
		for (int i = COLUMNS; i < ROWS*COLUMNS; i+=COLUMNS) {
			buttons[i].setText(sdf.format(tmpCal.getTime()));
			
			week++;
			tmpCal.set(Calendar.WEEK_OF_YEAR, week);
		}
	}
	
	/**
	 * Displays day numbers.
	 */
	protected void displayDays() {
		Calendar tmpCal = (Calendar) calendar.clone();
		tmpCal.set(Calendar.DAY_OF_MONTH, 1);
		tmpCal.set(Calendar.HOUR_OF_DAY, 0);
		tmpCal.set(Calendar.MINUTE, 0);
		tmpCal.set(Calendar.SECOND, 0);
		tmpCal.set(Calendar.MILLISECOND, 0);
		
		int currentMonth = tmpCal.get(Calendar.MONTH);
		
		// First week in calendar panel
		tmpCal.set(Calendar.DAY_OF_WEEK, tmpCal.getFirstDayOfWeek());
		
		for (int i = 9; i < ROWS*COLUMNS; i++) {
			// If index equals to first column, skip it
			if(i % 8 == 0) i++;
			
			// Removing action and focus listeners from button
			buttons[i].removeActionListener(this);
			buttons[i].removeFocusListener(this);
			buttons[i].setFocusable(false);
			
			buttons[i].setText(Integer.toString(tmpCal.get(Calendar.DAY_OF_MONTH)));
			if(tmpCal.get(Calendar.MONTH) == currentMonth){
				buttons[i].setBackground(BACKGROUND_COLOUR_ACTIVE_MONTH);
				
				// Adding action and focus listeners to active button
				buttons[i].addActionListener(this);
				buttons[i].addFocusListener(this);
				buttons[i].setFocusable(true);
				
				if(tmpCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
					buttons[i].setForeground(FOREGROUND_COLOUR_ACTIVE_SUNDAY);
				} else {
					buttons[i].setForeground(FOREGROUND_COLOUR_ACTIVE_WEEKDAY);
				}
			} else {
				buttons[i].setBackground(BACKGROUND_COLOUR_INACTIVE_MONTH);
				if(tmpCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
					buttons[i].setForeground(FOREGROUND_COLOUR_INACTIVE_SUNDAY);
				} else {
					buttons[i].setForeground(FOREGROUND_COLOUR_INACTIVE_WEEKDAY);
				}
			}
			
			// Marking current date
			if(currentDate.get(Calendar.YEAR) == tmpCal.get(Calendar.YEAR) &&
					currentDate.get(Calendar.DAY_OF_YEAR) == tmpCal.get(Calendar.DAY_OF_YEAR)){
				buttons[i].setBackground(BACKGROUND_COLOUR_CURRENT);
				buttons[i].setForeground(FOREGROUND_COLOUR_CURRENT);
			}
			
			tmpCal.set(Calendar.DAY_OF_YEAR, tmpCal.get(Calendar.DAY_OF_YEAR) + 1);
		}
	}
	
	/**
	 * Sets month in the calendar and draws btnDays.
	 * 
	 * @param month
	 */
	public void setMonth(int month) {
		calendar.set(Calendar.MONTH, month);
		
		// Selecting new button and deselecting old one
		CalendarButton oldSelected = btnSelected;
		btnSelected = null;
		if(oldSelected != null) oldSelected.repaint();
		
		init();
	}
	
	/**
	 * Sets year in the calendar and draws btnDays.
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		calendar.set(Calendar.YEAR, year);
		
		// Selecting new button and deselecting old one
		CalendarButton oldSelected = btnSelected;
		btnSelected = null;
		if(oldSelected != null) oldSelected.repaint();
		
		init();
	}
	
	/**
	 * Sets calendar.
	 * 
	 * @param calendar
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		
		init();
	}
	
	/**
	 * Marks specified dates with specified colour.
	 * 
	 * @param holidays
	 * @param colour
	 */
	public void markHolidays(List<Holiday> holidays, Color colour){
		if(holidays == null) return;
		if(!holidays.isEmpty()){
			for(Holiday holiday : holidays){
				Calendar tmpCal = (Calendar) calendar.clone();
				tmpCal.set(Calendar.DAY_OF_MONTH, 1);
				
				int currentMonth = tmpCal.get(Calendar.MONTH);
				
				// First week in calendar panel
				tmpCal.set(Calendar.DAY_OF_WEEK, tmpCal.getFirstDayOfWeek());
				
				for (int i = 9; i < ROWS*COLUMNS; i++) {
					// If index equals to first column, skip it
					if(i % 8 == 0) i++;
					
					if(tmpCal.get(Calendar.MONTH) == currentMonth){
						// Marking current date
						if(holiday.getDate().get(Calendar.MONTH) == tmpCal.get(Calendar.MONTH) &&
								holiday.getDate().get(Calendar.DAY_OF_MONTH) == tmpCal.get(Calendar.DAY_OF_MONTH)){
							if(holiday.isRepeatable()){
								buttons[i].setBackground(colour);
							} else {
								if(holiday.getDate().get(Calendar.YEAR) == tmpCal.get(Calendar.YEAR)){
									buttons[i].setBackground(colour);
								}
							}
						}
					}
					
					tmpCal.set(Calendar.DAY_OF_YEAR, tmpCal.get(Calendar.DAY_OF_YEAR) + 1);
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CalendarButton button = (CalendarButton) e.getSource();
		
		String buttonText = button.getText();
		int day = new Integer(buttonText).intValue();
		
		// Selecting new button and deselecting old one
		CalendarButton oldSelected = btnSelected;
		btnSelected = button;
		
		if(oldSelected != null) oldSelected.repaint();
		
		notifyObserver(Calendar.DAY_OF_MONTH, day);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
	}
	
	@Override
	public void focusLost(FocusEvent e) {
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
	};
	
	/**
	 * <code>CalendarButton</code> class extends <code>JButton</code>
	 * class and serves as main class for various buttons in calendar
	 * panel.
	 * 
	 * <p>Date created: 2013.07.28
	 * 
	 * @author Vassili Kurman
	 * @version 0.1
	 */
	abstract class CalendarButton extends JButton {
		private static final long serialVersionUID = 128001914383581804L;
		
		@Override
		public boolean isBorderPainted(){
			return false;
		}
	}
	
	/**
	 * <code>NameButton</code> class extends <code>CalendarButton</code>
	 * class.
	 * 
	 * <p>Date created: 2013.07.28
	 * 
	 * @author Vassili Kurman
	 * @version 0.1
	 */
	class NameButton extends CalendarButton {
		private static final long serialVersionUID = -5306477668406547496L;

		public NameButton() {
		}
		
		@Override
		public void addMouseListener(MouseListener l) {
		}
		
		@Override
		public void addFocusListener(FocusListener l) {
		}
		
		@Override
		public boolean isFocusable() {
			return false;
		}
		
		@Override
		public Color getBackground(){
			return BACKGROUND_COLOUR_NAMES;
		}
	}
	
	/**
	 * <code>DayButton</code> class extends <code>CalendarButton</code>
	 * class and represents selectable button for holding date
	 * numbers.
	 * 
	 * <p>Date created: 2013.07.28
	 * 
	 * @author Vassili Kurman
	 * @version 0.1
	 */
	class DayButton extends CalendarButton {
		private static final long serialVersionUID = -5306477668406547496L;
		
		/**
		 * Constructor.
		 */
		public DayButton() {
		}
		
		@Override
		public Color getBackground(){
			return (this.equals(btnSelected)) ?
					BACKGROUND_COLOUR_SELECTED :
						super.getBackground();
		}
	}
}