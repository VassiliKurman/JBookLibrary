package vkurman.jbooklibrary.enums;

/**
 * Enumeration class for Loan Types.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum LoanType {
	NEW("New"), RENEWAL("Renewal"), RETURN("Return");
	
	private String nameAsString;
	
	private LoanType(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
	public static LoanType fromString(String text){
		if(text != null){
			for(LoanType e : LoanType.values()){
				if(text.equalsIgnoreCase(e.toString())){
					return e;
				}
			} 
			return LoanType.NEW;
		} else {
			return LoanType.NEW;
		}
	}
}