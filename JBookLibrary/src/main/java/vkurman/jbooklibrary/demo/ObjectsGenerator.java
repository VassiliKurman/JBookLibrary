package vkurman.jbooklibrary.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import vkurman.jbooklibrary.core.Address;
import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Borrower;
import vkurman.jbooklibrary.core.IDProvider;
import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.demo.mock.JObjectGenerator;
import vkurman.jbooklibrary.utils.JDate;

/**
 * Generates random objects.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ObjectsGenerator {
	
	/**
	 * Constructor.
	 */
	public ObjectsGenerator() {
	}
	
	/**
	 * Returns random <code>Borrower</code> from database.
	 * 
	 * @return Borrower
	 */
	public static Borrower getRandomBorrower(){
		return (Borrower) JObjectGenerator.getRandomObject(
				AdminUsers.getInstance().getBorrowers());
	}
	
	/**
	 * Returns random <code>Book</code> from database.
	 * 
	 * @return Book
	 */
	public static Book getRandomBook(){
		return (Book) JObjectGenerator.getRandomObject(
				AdminBooks.getInstance().getAllBooks());
	}
	
	/**
	 * Generates and returns a random list of non real author names.
	 * 
	 * @param quantity
	 * @return List<String>
	 */
	private static List<String> generateAuthors(int quantity){
		List<String> authors = new ArrayList<String>();
		int i = 0;
		while(i < quantity){
			authors.add(JObjectGenerator.getRandomSurname() +
					" "+ JObjectGenerator.getRandomCharacterCapital() +".");
			i++;
		}
		return authors;
	}
	
	/**
	 * Generates and returns non real book.
	 * 
	 * @return Book
	 */
	public static Book generateBook() {
		Book book = new Book(generateAuthors(JObjectGenerator.getRandomNumber(1, 3)),
				JObjectGenerator.getRandomObject(BookTitle.values()).toString(),
				JObjectGenerator.getRandomNumber(1, 10),
				JDate.generateRandomDateBeforeCurrent(7200),
				JObjectGenerator.getRandomCountry(),
				JObjectGenerator.getRandomPublisher(),
				"eng",
				String.valueOf(JObjectGenerator.getRandomNumber(100000000, 999999999)),
				JObjectGenerator.getRandomNumber(50, 500),
				"Book description ",
				"print");
		return book;
	}
	
	/**
	 * Generates and returns a list of non real books.
	 * 
	 * @param quantity
	 * @return List<Book>
	 */
	public static List<Book> generateBooks(int quantity) {
		List<Book> list = new ArrayList<Book>();
		for (int counter = 0; counter < quantity; counter++) {
			list.add(generateBook());
		}
		return list;
	}
	
	/**
	 * Generates and returns borrower.
	 * 
	 * @return Borrower
	 */
	public static Borrower generateBorrower() {
		Borrower borrower = new Borrower(
				JObjectGenerator.getRandomFirstname(),
				null,
				JObjectGenerator.getRandomSurname());
		borrower.setDob((Calendar) Calendar.getInstance(Locale.getDefault()).clone());
		
		return borrower;
	}
	
	/**
	 * Generates and returns a list of borrowers.
	 * 
	 * @param quantity
	 * @return List<Borrower>
	 */
	public static List<Borrower> generateBorrowers(int quantity) {
		List<Borrower> list = new ArrayList<Borrower>();
		long id = IDProvider.getInstance().getBorrowerNextID();
		
		for (int i = 0; i < quantity; i++) {
			Borrower borrower = generateBorrower();
			borrower.setUserID(id);
			
			list.add(borrower);
			
			id++;
		}
		return list;
	}
	
	/**
	 * Generates and returns <code>Librarian</code>
	 * 
	 * @return Librarian
	 */
	public static Librarian generateLibrarian() {
		Librarian librarian = new Librarian(
				JObjectGenerator.getRandomFirstname(),
				null,
				JObjectGenerator.getRandomSurname());
		librarian.setDob((Calendar) Calendar.getInstance(Locale.getDefault()).clone());
		
		return librarian;
	}
	
	/**
	 * Generates and returns a list of librarians.
	 * 
	 * @param quantity
	 * @return List<Librarian>
	 */
	public static List<Librarian> generateLibrarians(int quantity) {
		List<Librarian> list = new ArrayList<Librarian>();
		long id = IDProvider.getInstance().getLibrarianNextID();
		
		for (int i = 0; i < quantity; i++) {
			
			Librarian librarian = generateLibrarian();
			librarian.setUserID(id);
			
			list.add(librarian);
			
			id++;
		}
		return list;
	}
	
	/**
	 * Generates and returns a list of not real addresses.
	 * 
	 * @param quantity
	 * @return List<Address>
	 */
	public static List<Address> generateAddresses(int quantity) {
		List<Address> a = new ArrayList<Address>();
		for (int i = 0; i < quantity; i++) {
			a.add(generateAddress());
		}
		return a;
	}
	
	/**
	 * Generates and returns not real random address.
	 * 
	 * @return Address
	 */
	public static Address generateAddress() {
		String city = JObjectGenerator.getRandomCity();
		
		return new Address(String.valueOf(new Random().nextInt(31)),
				JObjectGenerator.getRandomHouseName(),
				String.valueOf(new Random().nextInt(301)),
				JObjectGenerator.getRandomStreet(),
				city,
				JObjectGenerator.getRandomCounty(),
				generatePostcode(city),
				JObjectGenerator.getRandomCountry());
	}
	
	/**
	 * Generates and returns not real UK post code based on specified name
	 * of the city.
	 *  
	 * @param city
	 * @return String
	 */
	private static String generatePostcode(String city) {
		StringBuffer p = new StringBuffer();
		
		p.append(city.substring(0, 2).toLowerCase());
		p.append(new Random().nextInt(10));
		p.append(new Random().nextInt(10));
		p.append(" " + new Random().nextInt(10));
		p.append(JObjectGenerator.getRandomCharacterSmall());
		p.append(JObjectGenerator.getRandomCharacterSmall());
		
		return p.toString();
	}
}