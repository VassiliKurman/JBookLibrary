package vkurman.jbooklibrary.enums;

/**
 * Enumeration class for Employment Termination Reasons.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum EmploymentTerminationReason {
	RESIGNATION("Resignation"), ATTENDANCE("Attendance"), ILLNESS("Illness"), DEATH("Death"),
	MISCONDUCT("Misconduct"), POOR_PERFORMANCE("Poor Performance"), REDUNDANCY("Redundancy"),
	INSUBORINATION("Insubordination"), RETIREMENT("Retirement"), OTHER("Other");
	
	private String nameAsString;
	
	private EmploymentTerminationReason(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
	public static EmploymentTerminationReason fromString(String text){
		if(text != null){
			for(EmploymentTerminationReason e : EmploymentTerminationReason.values()){
				if(text.equalsIgnoreCase(e.toString())){
					return e;
				}
			} 
			return EmploymentTerminationReason.OTHER;
		} else {
			return EmploymentTerminationReason.OTHER;
		}
	}
}