package vkurman.jbooklibrary.enums;

/**
 * Enumeration class for Book generalStatus.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum ItemStatus {
	ONSHELF("On Shelf"), RESERVED("Reserved"), ONLOAN("On Loan"), DISPOSED("Disposed"),
	UNKNOWN("Unknown");
	
	private String nameAsString;
	
	private ItemStatus(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
	public static ItemStatus fromString(String text){
		if(text != null){
			for(ItemStatus e : ItemStatus.values()){
				if(text.equalsIgnoreCase(e.toString())){
					return e;
				}
			} 
			return ItemStatus.UNKNOWN;
		} else {
			return ItemStatus.UNKNOWN;
		}
	}
}