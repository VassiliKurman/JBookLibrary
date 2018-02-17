package vkurman.jbooklibrary.utils.jbasiccalendar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import vkurman.jbooklibrary.core.Holiday;

public class JBasicEventPanel extends JBasicCalendarPanel {
	
	private static final long serialVersionUID = 5628574985610891553L;
	
	private List<Holiday> holidays;
	private Color colour;
	
	/**
	 * Constructor.
	 */
	public JBasicEventPanel(){
		super();
		
		holidays = new ArrayList<Holiday>();
	}
	
	/**
	 * Marks specified holidays with specified colour.
	 * 
	 * @param holidays
	 * @param colour
	 */
	public void markDates(List<Holiday> holidays, Color colour){
		if(holidays == null) return;
		
		if(!holidays.isEmpty()){
			this.holidays = holidays;
			this.colour = (colour == null) ? JDayPanel.BACKGROUND_COLOUR_SPECIAL : colour;
			
			dayPanel.markHolidays(holidays, colour);
		}
	}
	
	/**
	 * Marks holidays.
	 */
	private void markDates(){
		if(holidays == null || holidays.isEmpty()) return;
		
		dayPanel.markHolidays(holidays, (colour == null) ? JDayPanel.BACKGROUND_COLOUR_SPECIAL : colour);
	}
	
	@Override
	public void update(Object arg1, Object arg2){
		super.update(arg1, arg2);
		
		markDates();
	}
}