package vkurman.jbooklibrary.demo.mock;

/**
 * <code>Enumerator</code> of UK cities.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum City {
	BATH("Bath"), BIRMINGHAM("Birmingham"), BRADFORD("Bradford"), BRIGHTON("Brighton"),
	HOVE("Hove"), BRISTOL("Bristol"), CAMBRIDGE("Cambridge"), CANTERBURY("Canterbury"),
	CARLISLE("Carlisle"), CHESTER("Chester"), CHICHESTER("Chichester"), LONDON("London"),
	COVENTRY("Coventry"), DERBY("Derby"), DURHAM("Durham"), ELY("Ely"), EXETER("Exeter"),
	GLOUCESTER("Gloucester"), HEREFORD("Hereford"), KINGSTON_UPON_HULL("Kingston upon Hull"),
	LANCASTER("Lancaster"), LEEDS("Leeds"), LEICESTER("Leicester"), LICHFIELD("Lichfield"),
	LINCOLN("Lincoln"), LIVERPOOL("Liverpool"), MANCHESTER("Manchester"), NORWICH("Norwich"),
	NEWCASTLE_UPON_TYNE("Newcastle upon Tyne"), NOTTINGHAM("Nottingham"), OXFORD("Oxford"),
	PETERBOROUGH("Peterborough"), PLYMOUTH("Plymouth"), PORTSMOUTH("Portsmouth"),
	PRESTON("Preston"), RIPON("Ripon"), SALFORD("Salford"), SALISBURY("Salisbury"),
	SHEFFIELD("Sheffield"), SOUTHAMPTON("Southampton"), ST_ALBANS("St Albans"), TRURO("Truro"),
	STOKE_ON_TRENT("Stoke-on-Trent"), SUNDERLAND("Sunderland"), WAKEFIELD("Wakefield"),
	WELLS("Wells"), WESTMINSTER("Westminster"), WINCHESTER("Winchester"), YORK("York"),
	WOLVERHAMPTON("Wolverhampton"), WORCESTER("Worcester");
	
	private String nameAsString;
	
	private City(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}