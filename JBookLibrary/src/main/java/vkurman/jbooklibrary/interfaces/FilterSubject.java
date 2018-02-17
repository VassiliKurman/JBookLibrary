package vkurman.jbooklibrary.interfaces;

/**
 * FilterSubject interface handles registering, unregistering
 * and notifying all FilterObservers
 *  
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public interface FilterSubject {
	/**
	 * Registers observer.
	 * 
	 * @param observer
	 */
	public void register(FilterObserver observer);
	
	/**
	 * Unregisters observer.
	 * 
	 * @param observer
	 */
	public void unregister(FilterObserver observer);
	
	/**
	 * Notifies observer about change.
	 * 
	 * @param arg
	 */
	public void notifyObserver(Object arg);
}