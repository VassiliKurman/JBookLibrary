package vkurman.jbooklibrary.activityregister;

/**
 * Represents activity or action that system performed on
 * database or on application.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Activity {
	
	private Object source;
	private String message;
	
	/**
	 * Constructor.
	 * 
	 * @param source
	 * @param message
	 */
	public Activity(Object source, String message){
		this.source = source;
		this.message = message;
	}
	
	/**
	 * Getter for source
	 * 
	 * @return Object
	 */
	public Object getSource() {
		return source;
	}
	
	/**
	 * Setter for source
	 * 
	 * @param source
	 */
	public void setSource(Object source) {
		this.source = source;
	}
	
	/**
	 * Getter for message
	 * 
	 * @return String
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Setter for message
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}