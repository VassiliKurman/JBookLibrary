package vkurman.jbooklibrary.utils.jbasiccalendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JPanel;

/**
 * <code>JBasicCalendarPanel</code> combines all the components of calendar,
 * such as year, month and date panels into a single panel.
 * 
 * <p>
 * Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class JBasicCalendarPanel extends JPanel implements CalendarObserver,
		UpdateObservable {

	private static final long serialVersionUID = -1537658042484702357L;
	/**
	 * Default panel background colour.
	 */
	private static final Color BACKGROUND_COLOUR_DEFAULT = new Color(210, 228,
			238);
	private List<UpdateObserver> observers;
	private Calendar calendar;
	private JYearPanel yearPanel;
	private JMonthPanel monthPanel;
	protected JDayPanel dayPanel;
	private boolean isDateSet;

	/**
	 * Default constructor that sets current date in the panel.
	 */
	public JBasicCalendarPanel() {
		this((Calendar) Calendar.getInstance(Locale.getDefault()).clone());
	}

	/**
	 * Main constructor that puts together all bits.
	 * 
	 * @param date
	 */
	public JBasicCalendarPanel(Calendar c) {
		observers = new ArrayList<UpdateObserver>();
		isDateSet = false;
		calendar = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
		if (c != null) {
			calendar.setTime(c.getTime());
		}

		yearPanel = new JYearPanel(calendar.get(Calendar.YEAR));
		yearPanel.setBackground(BACKGROUND_COLOUR_DEFAULT);
		yearPanel.register(this);
		monthPanel = new JMonthPanel(calendar.get(Calendar.MONTH));
		monthPanel.setBackground(BACKGROUND_COLOUR_DEFAULT);
		monthPanel.register(this);
		dayPanel = new JDayPanel(calendar);
		dayPanel.register(this);

		showUI();
	}

	/**
	 * Creates and displays UI.
	 */
	private void showUI() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		panel.add(monthPanel);
		panel.add(yearPanel);

		add(panel, BorderLayout.PAGE_START);
		add(dayPanel, BorderLayout.CENTER);
	}

	/**
	 * Returns <code>true</code> if date is set, or else returns
	 * <code>false</code>.
	 * 
	 * @return boolean
	 */
	public boolean isDateSet() {
		return isDateSet;
	}

	/**
	 * Returns <code>Date</code> object from calendar.
	 * 
	 * @return Date
	 */
	public Date getDate() {
		return calendar.getTime();
	}

	/**
	 * Returns calendar.
	 * 
	 * @return Calendar
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * Returns time in milliseconds.
	 * 
	 * @return long
	 */
	public long getTimeInMillis() {
		return calendar.getTimeInMillis();
	}

	@Override
	public void update(Object arg1, Object arg2) {
		calendar.set((Integer) arg1, (Integer) arg2);

		if ((Integer) arg1 == 1) {
			dayPanel.setYear((Integer) arg2);
		} else if ((Integer) arg1 == 2) {
			dayPanel.setMonth((Integer) arg2);
		} else if ((Integer) arg1 == 5) {
			isDateSet = true;
			notifyObserver(true);
		}
	}

	@Override
	public void register(UpdateObserver observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObserver(boolean arg) {
		for (UpdateObserver observer : observers) {
			observer.updated(arg);
		}
	}
}