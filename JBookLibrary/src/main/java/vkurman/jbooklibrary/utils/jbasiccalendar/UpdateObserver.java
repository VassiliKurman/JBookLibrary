package vkurman.jbooklibrary.utils.jbasiccalendar;

/**
 * The <code>UpdateObserver</code>'s <code>update()</code> method is called when
 * the observable subject field changes.
 * 
 * <p>
 * Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public interface UpdateObserver {
	public void updated(boolean arg);
}