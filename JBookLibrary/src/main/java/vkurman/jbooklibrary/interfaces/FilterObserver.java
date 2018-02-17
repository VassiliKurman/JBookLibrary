package vkurman.jbooklibrary.interfaces;

/**
 * The FilterObserver update method is called when the observable
 * subject field changes.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public interface FilterObserver {
	public void update(String className, Object arg);
}