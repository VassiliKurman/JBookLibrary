package vkurman.jbooklibrary.enums;

/**
 * Enumeration for actions that can be performed on books.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum Action {
	RESERVE("Reserve"), BORROW("Borrow"), RENEW("Renew"), RETURN("Return");
	
	private String nameAsString;
	
	private Action(String nameAsString)
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
			return null;
		} else {
			return null;
		}
	}
}