package vkurman.jbooklibrary.core;

/**
 * Primary use of TransObject class is to exchange references
 * for different class instances between different graphical user
 * interfaces.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class TransObject {
	
	/**
	 * Object declaration.
	 */
	private Object object;
	
	/**
	 * Getter for object
	 * 
	 * @return Object
	 */
	public Object getObject() {
		return this.object;
	}
	
	/**
	 * Setter for object
	 * 
	 * @param object
	 */
	public void setObject(Object object) {
		this.object = object;
	}
}