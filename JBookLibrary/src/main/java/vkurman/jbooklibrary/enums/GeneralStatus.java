package vkurman.jbooklibrary.enums;

/**
 * Enumeration class for general generalStatus.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum GeneralStatus {
	ACTIVE("Active"), INACTIVE("Inactive");
	
	private String nameAsString;
	
	private GeneralStatus(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
	public static GeneralStatus fromString(String text){
		if(text != null){
			for(GeneralStatus e : GeneralStatus.values()){
				if(text.equalsIgnoreCase(e.toString())){
					return e;
				}
			} 
			return GeneralStatus.INACTIVE;
		} else {
			return GeneralStatus.INACTIVE;
		}
	}
}