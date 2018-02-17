package vkurman.jbooklibrary.enums;

/**
 * Enumeration class for Person sex.
 * 
* <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
*/
public enum Sex {
	MALE("Male"), FEMALE("Female"), UNSPECIFIED("Unspecified");
	
	private String nameAsString;
	
	private Sex(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
	public static Sex fromString(String text){
		if(text != null){
			for(Sex e : Sex.values()){
				if(text.equalsIgnoreCase(e.toString())){
					return e;
				}
			} 
			return Sex.UNSPECIFIED;
		} else {
			return Sex.UNSPECIFIED;
		}
	}
}