package vkurman.jbooklibrary.demo.mock;

import java.util.List;
import java.util.Random;

/**
 * <code>JObjectGenerator</code> returns random object
 * of specified type.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class JObjectGenerator {
	
	private static final Random random = new Random();
	
	/**
	 * Returns number between <code>min</code> (inclusive) and
	 * <code>max</code> (exclusive).
	 * 
	 * @param min
	 * @param max
	 * @return int
	 */
	public static int getRandomNumber(int min, int max){
		while(true){
			int n = random.nextInt(max);
			if (n >= min){
				return n;
			}
		}
	}
	
	/**
	 * Returns random <code>CharacterCapital</code>.
	 * 
	 * @return String
	 */
	public static String getRandomCharacterCapital(){
		return CharacterCapital.values()[getRandomNumber(0,  CharacterCapital.values().length)].toString();
	}
	
	/**
	 * Returns random <code>CharacterSmall</code>.
	 * 
	 * @return String
	 */
	public static String getRandomCharacterSmall(){
		return CharacterSmall.values()[getRandomNumber(0,  CharacterSmall.values().length)].toString();
	}
	
	/**
	 * Returns random UK first name, either <code>FirstnameFemale</code>
	 * or <code>FirstnameMale</code> and random UK <code>Surname</code>.
	 * 
	 * @return String
	 */
	public static String getRandomName(){
		return getRandomFirstname() + " " + getRandomSurname();
	}
	
	/**
	 * Returns random UK <code>FirstnameFemale</code> and random UK <code>Surname</code>.
	 * 
	 * @return String
	 */
	public static String getRandomFemaleName(){
		return getRandomFemaleFirstname() + " " + getRandomSurname();
	}
	
	/**
	 * Returns random UK <code>FirstnameMale</code> and random UK <code>Surname</code>.
	 * 
	 * @return String
	 */
	public static String getRandomMaleName(){
		return getRandomMaleFirstname() + " " + getRandomSurname();
	}
	
	/**
	 * Returns random UK first name either <code>FirstnameFemale</code>
	 * or <code>FirstnameMale</code>.
	 * 
	 * @return String
	 */
	public static String getRandomFirstname(){
		if(random.nextBoolean()){
			return FirstnameFemale.values()[getRandomNumber(0,  FirstnameFemale.values().length)].toString();
		} else {
			return FirstnameMale.values()[getRandomNumber(0,  FirstnameMale.values().length)].toString();
		}
	}
	
	/**
	 * Returns random UK <code>FirstnameFemale</code>.
	 * 
	 * @return String
	 */
	public static String getRandomFemaleFirstname(){
		return FirstnameFemale.values()[getRandomNumber(0,  FirstnameFemale.values().length)].toString();
	}
	
	/**
	 * Returns random UK <code>FirstnameMale</code>.
	 * 
	 * @return String
	 */
	public static String getRandomMaleFirstname(){
		return FirstnameMale.values()[getRandomNumber(0,  FirstnameMale.values().length)].toString();
	}
	
	/**
	 * Returns random UK <code>Surname</code>.
	 * 
	 * @return String
	 */
	public static String getRandomSurname(){
		return Surname.values()[getRandomNumber(0,  Surname.values().length)].toString();
	}
	
	/**
	 * Returns random UK <code>HouseName</code>.
	 * 
	 * @return String
	 */
	public static String getRandomHouseName(){
		return HouseName.values()[getRandomNumber(0,  HouseName.values().length)].toString();
	}
	
	/**
	 * Returns random UK <code>Street</code>.
	 * 
	 * @return String
	 */
	public static String getRandomStreet(){
		return Street.values()[getRandomNumber(0,  Street.values().length)].toString();
	}
	
	/**
	 * Returns random UK <code>City</code>.
	 * 
	 * @return String
	 */
	public static String getRandomCity(){
		return City.values()[getRandomNumber(0,  City.values().length)].toString();
	}
	
	/**
	 * Returns random UK <code>County</code>.
	 * 
	 * @return String
	 */
	public static String getRandomCounty(){
		return County.values()[getRandomNumber(0,  County.values().length)].toString();
	}
	
	/**
	 * Returns random <code>Country</code>.
	 * 
	 * @return String
	 */
	public static String getRandomCountry(){
		return Country.values()[getRandomNumber(0,  Country.values().length)].toString();
	}
	
	/**
	 * Returns random <code>Publisher</code>.
	 * 
	 * @return String
	 */
	public static String getRandomPublisher(){
		return Publisher.values()[getRandomNumber(0,  Publisher.values().length)].toString();
	}
	
	/**
	 * Returns random <code>String</code> from list provided.
	 * If list is empty, than <code>null</code> is returned.
	 * 
	 * @param list
	 * @return String
	 */
	public static String getRandomString(List<String> list){
		if (list != null && !list.isEmpty()) {
			return list.get(getRandomNumber(0,  list.size()));
		} else {
			return null;
		}
	}
	
	/**
	 * Returns random object from list provided. If list is
	 * empty, than <code>null</code> is returned.
	 * 
	 * @param list
	 * @return Object
	 */
	public static Object getRandomObject(List<?> list){	
		if (list != null && !list.isEmpty()) {
			return list.get(getRandomNumber(0,  list.size()));
		} else {
			return null;
		}
	}
	
	/**
	 * Returns random object from array provided. If array is
	 * empty, than <code>null</code> is returned.
	 * 
	 * @param list
	 * @return Object
	 */
	public static Object getRandomObject(Object[] list){	
		if (list != null && list.length > 0) {
			return list[getRandomNumber(0,  list.length)];
		} else {
			return null;
		}
	}
}