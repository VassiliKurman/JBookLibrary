package vkurman.jbooklibrary.demo.mock;

/**
 * <code>Enumerator</code> of English male names.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum FirstnameMale {
	AIDEN("Aiden"), JACKSON("Jackson"), ETHAN("Ethan"), LIAM("Liam"), MASON("Mason"),
	NOAH("Noah"), LUCAS("Lucas"), JACOB("Jacob"), JAYDEN("Jayden"), JACK("Jack"),
	LOGAN("Logan"), RYAN("Ryan"), CALEB("Caleb"), BENJAMIN("Benjamin"), WILLIAM("William"),
	MICHAEL("Michael"), ALEXANDER("Alexander"), ELIJAH("Elijah"), MATTHEW("Matthew"),
	DYLAN("Dylan"), JAMES("James"), OWEN("Owen"), CONNOR("Connor"), BRAYDEN("Brayden"),
	CARTER("Carter"), LANDON("Landon"), JOSHUA("Joshua"), LUKE("Luke"), DANIEL("Daniel"),
	GABRIEL("Gabriel"), NICHOLAS("Nicholas"), NATHAN("Nathan"), OLIVER("Oliver"), HENRY("Henry"),
	ANDREW("Andrew"), GAVIN("Gavin"), CAMERON("Cameron"), ELI("Eli"), MAX("Max"), ISAAC("Isaac"),
	EVAN("Evan"), SAMUEL("Samuel"), GRAYSON("Grayson"), TYLER("Tyler"), ZACHARY("Zachary"),
	WYATT("Wyatt"), JOSEPH("Joseph"), CHARLIE("Charlie"), HUNTER("Hunter"), DAVID("David"),
	ANTHONY("Anthony"), CHRISTIAN("Christian"), COLTON("Colton"), THOMAS("Thomas"),
	DOMINIC("Dominic"), AUSTIN("Austin"), JOHN("John"), SEBASTIAN("Sebastian"), COOPER("Cooper"),
	LEVI("Levi"), PARKER("Parker"), ISAIAH("Isaiah"), CHASE("Chase"), BLAKE("Blake"), AARON("Aaron"),
	ALEX("Alex"), ADAM("Adam"), TRISTAN("Tristan"), JULIAN("Julian"), JONATHAN("Jonathan"),
	CHRISTOPHER("Christopher"), JACE("Jace"), NOLAN("Nolan"), MILES("Miles"), JORDAN("Jordan"),
	CARSON("Carson"), COLIN("Colin"), IAN("Ian"), RILEY("Riley"), XAVIER("Xavier"), HUDSON("Hudson"),
	ADRIAN("Adrian"), COLE("Cole"), BRODY("Brody"), LEO("Leo"), JAKE("Jake"), BENTLEY("Bentley"),
	SEAN("Sean"), JEREMIAH("Jeremiah"), ASHER("Asher"), NATHANIEL("Nathaniel"), MICAH("Micah"),
	JASON("Jason"), RYDER("Ryder"), DECLAN("Declan"), HAYDEN("Hayden"), BRANDON("Brandon"),
	EASTON("Easton"), LINCOLN("Lincoln"), HARRISON("Harrison");
	
	private String nameAsString;
	
	private FirstnameMale(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}