package vkurman.jbooklibrary.utils.jbasiccalendar;

/**
 * <code>UpdateObservable</code> handles registering and notifying
 * <code>UpdateObserver</code> about change.
 * 
 * <p>
 * Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public interface UpdateObservable {
	public void register(UpdateObserver observer);

	public void notifyObserver(boolean arg);
}