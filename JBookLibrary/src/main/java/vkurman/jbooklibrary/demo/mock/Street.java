package vkurman.jbooklibrary.demo.mock;

/**
 * <code>Enumerator</code> of UK popular streets.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum Street {
	HIGH_STREET("High Street"), STATION_ROAD("Station Road"), MAIN_STREET("Main Street"),
	PARK_ROAD("Park Road"), CHURCH_ROAD("Church Road"), CHURCH_STREET("Church Street"),
	LONDON_ROAD("London Road"), VICTORIA_ROAD("Victoria Road"), GREEN_LANE("Green Lane"),
	MANOR_ROAD("Manor Road"), CHURCH_LANE("Church Lane"), PARK_AVENUE("Park Avenue"),
	THE_AVENUE("The Avenue"), THE_CRESCENT("The Crescent"), QUEENS_ROAD("Queens Road"),
	NEW_ROAD("New Road"), GRANGE_ROAD("Grange Road"), KINGS_ROAD("Kings Road"),
	KINGSWAY("Kingsway"), WINDSOR_ROAD("Windsor Road"), HIGHFIELD_ROAD("Highfield Road"),
	MILL_LANE("Mill Lane"), ALEXANDER_ROAD("Alexander Road"), YORK_ROAD("York Road"),
	ST_JOHNS_ROAD("St. John�s Road"), MAIN_ROAD("Main Road"), BROADWAY("Broadway"),
	KING_STREET("King Street"), THE_GREEN("The Green"), SPRINGFIELD_ROAD("Springfield Road"),
	GEORGE_STREET("George Street"), PARK_LANE("Park Lane"), VICTORIA_STREET("Victoria Street"),
	ALBERT_ROAD("Albert Road"), QUEENSWAY("Queensway"), NEW_STREET("New Street"),
	QUEEN_STREET("Queen Street"), WEST_STREET("West Street"), NORTH_STREET("North Street"),
	MANCHESTER_ROAD("Manchester Road"), THE_GROVE("The Grove"), RICHMOND_ROAD("Richmond Road"),
	GROVE_ROAD("Grove Road"), SOUTH_STREET("South Street"), SCHOOL_LANE("School Lane"),
	THE_DRIVE("The Drive"), NORTH_ROAD("North Road"), STANLEY_ROAD("Stanley Road"),
	CHESTER_ROAD("Chester Road"), MILL_ROAD("Mill Road");

	private String nameAsString;
	
	private Street(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}