package vkurman.jbooklibrary.enums;

/**
 * Enumeration class for access rights withdrawals.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum Withdrawal {
	ACCESS("Access to library denied"),
	IDCARD("IDCard lost"),
	LOAN("Loan period exceeded"),
	OTHER("Other"),
	NONE("None");
	
	private String nameAsString;
	
	private Withdrawal(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
	public static Withdrawal fromString(String text){
		if(text != null){
			for(Withdrawal e : Withdrawal.values()){
				if(text.equalsIgnoreCase(e.toString())){
					return e;
				}
			} 
			return Withdrawal.OTHER;
		} else {
			return Withdrawal.OTHER;
		}
	}
}