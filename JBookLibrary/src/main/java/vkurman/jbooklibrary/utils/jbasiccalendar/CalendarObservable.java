package vkurman.jbooklibrary.utils.jbasiccalendar;

/**
 * <code>CalendarObservable</code> handles registering and notifying
 * <code>CalendarObserver</code> about update.
 * 
 * <p>
 * Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public interface CalendarObservable {
	public void register(CalendarObserver observer);

	public void notifyObserver(Object arg1, Object arg2);
}