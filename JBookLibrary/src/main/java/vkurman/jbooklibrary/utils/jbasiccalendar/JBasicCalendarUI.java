package vkurman.jbooklibrary.utils.jbasiccalendar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * User interface that displays <code>JBasicCalendarPanel</code>
 * with <code>OK</code> and <code>Cancel</code> buttons.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class JBasicCalendarUI extends JDialog {
	
	private static final long serialVersionUID = 3787957172315086122L;
	private JBasicCalendarPanel calendarPanel;
	private JButton okButton, cancelButton;
	private boolean isOkPressed;
	
	/**
	 * Defaults constructor that sets current time calendar.
	 */
	public JBasicCalendarUI(){
		this(null, Calendar.getInstance(Locale.getDefault()));
	}
	
	/**
	 * Main constructor that sets calendar to specified one.
	 * 
	 * @param calendar
	 */
	public JBasicCalendarUI(Component parent, Calendar calendar){
		isOkPressed = false;
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
		
		add(calendarPanel, BorderLayout.CENTER);
		add(getButtonPane(), BorderLayout.PAGE_END);
		setResizable(false);
		
		pack();
	}
	
	/**
	 * If <code>OK</code> buttons has been pressed, than <code>true</code>
	 * will be returned.
	 * 
	 * @return boolean
	 */
	public boolean isOkPressed(){
		return isOkPressed;
	}
	
	/**
	 * Returns <code>Date</code> object from calendar.
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
	 * Creates and returns panel with <code>OK</code> and
	 * <code>Cancel</code> buttons.
	 * 
	 * @return JPanel
	 */
	private JPanel getButtonPane(){
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		{
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					isOkPressed = true;
					dispose();
				}
			});
			buttonPane.add(okButton);
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					dispose();
				}
			});
			buttonPane.add(cancelButton);
		}
		return buttonPane;
	}
}