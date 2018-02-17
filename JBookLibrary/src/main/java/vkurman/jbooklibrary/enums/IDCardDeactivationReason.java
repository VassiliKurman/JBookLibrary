package vkurman.jbooklibrary.enums;

/**
 * Enumeration class for IDCard deactivation reasons.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum IDCardDeactivationReason {
	LOST("Lost"), EXPIRED("Expired"), OTHER("Other");
	
	private String nameAsString;
	
	private IDCardDeactivationReason(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
	public static IDCardDeactivationReason fromString(String text){
		if(text != null){
			for(IDCardDeactivationReason e : IDCardDeactivationReason.values()){
				if(text.equalsIgnoreCase(e.toString())){
					return e;
				}
			} 
			return IDCardDeactivationReason.OTHER;
		} else {
			return IDCardDeactivationReason.OTHER;
		}
	}
}