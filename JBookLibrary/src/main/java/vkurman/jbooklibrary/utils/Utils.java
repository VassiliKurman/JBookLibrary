package vkurman.jbooklibrary.utils;

public class Utils {
	
	private Utils() {}
	
	/**
	 * Delays execution of program by specified time in
	 * milliseconds using <code>Thread.sleep()</code> method.
	 * 
	 * @param milliseconds
	 */
	public static void delayTime(long milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}	