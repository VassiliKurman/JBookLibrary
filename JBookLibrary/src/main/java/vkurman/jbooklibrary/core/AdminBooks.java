package vkurman.jbooklibrary.core;

import java.util.ArrayList;
import java.util.List;

import vkurman.jbooklibrary.db.ParserObjectToSQL;
import vkurman.jbooklibrary.db.ParserSQLToObject;
import vkurman.jbooklibrary.enums.ItemStatus;

/**
 * This class manages all Book related activities, such as borrowing books,
 * extending loan period, reserving books, etc.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AdminBooks {
	
	/**
	 * The volatile keyword ensures that multiple threads
	 * handle the <code>admin</code> variable correctly when it is being
	 * initialised to the <code>AdminBooks</code> instance.
	 * In Java 1.4 and earlier the volatile keyword is improper
	 * synchronised for double-checked locking
	 */
	private volatile static AdminBooks admin;
	
	/**
	 * Default constructor.
	 */
	private AdminBooks() {
		
	}
	
	/**
	 * This method is checking if the instance of current class exists
	 * in the system and returns ever the instance that has been created
	 * earlier or the newly created instance.
	 * 
	 * @return AdminBooks object
	 */
	public static AdminBooks getInstance() {
		// Double-checked locking with synchronisation
		if(admin == null) {
			synchronized (AdminBooks.class) {
				if(admin == null) {
					admin = new AdminBooks();
				}
			}
		}
		// Under either circumstance this returns the instance
		return admin;
	}
	
	/**
	 * This method returns Book object found in database by specified
	 * bookID. If book with specified bookID not found, than NULL is
	 * returned.
	 * 
	 * @param bookID
	 * @return Book or NULL
	 */
	public Book getBook(long bookID){
		return (bookID > 0L) ?
				ParserSQLToObject.getInstance().getBook(bookID) :
					null;
	}
	
	/**
	 * This method returns a list of all NOT DISPOSED books found in database.
	 * 
	 * @return List of books or empty list if books are not found in database
	 */
	public List<Book> getAllBooks() {
		return ParserSQLToObject.getInstance().getBooks(ItemStatus.DISPOSED, true);
	}
	
	/**
	 * This method returns a list of all on shelf books found in database.
	 * 
	 * @return List of books or empty list if books are not found in database
	 */
	public List<Book> getOnShelfBooks() {
		return ParserSQLToObject.getInstance().getBooks(ItemStatus.ONSHELF, false);
	}
	
	/**
	 * This method returns a list of all reserved books found in database.
	 * 
	 * @return List of books or empty list if books are not found in database
	 */
	public List<Book> getReservedBooks() {
		return ParserSQLToObject.getInstance().getBooks(ItemStatus.RESERVED, false);
	}
	
	/**
	 * This method returns a list of all on loan books found in database.
	 * 
	 * @return List of books or empty list if books are not found in database
	 */
	public List<Book> getOnLoanBooks() {
		return ParserSQLToObject.getInstance().getBooks(ItemStatus.ONLOAN, false);
	}
	
	/**
	 * This method returns a list of all disposed books found in database.
	 * 
	 * @return List of books or empty list if books are not found in database
	 */
	public List<Book> getDisposedBooks() {
		return ParserSQLToObject.getInstance().getBooks(ItemStatus.DISPOSED, false);
	}
	
	/**
	 * This method returns a list of books with specified generalStatus found in database.
	 * 
	 * @return List of books or empty list if books are not found in database
	 */
	public List<Book> getBooks(ItemStatus status, boolean convert) {
		return ParserSQLToObject.getInstance().getBooks(status, convert);
	}
	
	/**
	 * This method is requesting to create new record in database for new
	 * book. If new record is created this method returns TRUE. This method
	 * returns FALSE if book object passed as the parameter to current
	 * method is equal to NULL or if new record was not created in database
	 * successfully.
	 * 
	 * @param book
	 * @return TRUE or FALSE
	 */
	public boolean addBook(Book book) {
		// Checking that book is not null
		if(book == null) return false;
		
		// Checking if book has ID set up
		if(book.getBookID() == 0){
			book.setBookID(IDProvider.getInstance().getBookNextID());
		}
		// Adding book to database
		return ParserObjectToSQL.getInstance().newBook(book);
	}
	
	/**
	 * This method is setting up book generalStatus to DISPOSED and returns TRUE
	 * if book database updated successfully. It returns FALSE if book
	 * object passed as the parameter to current method is equal to NULL
	 * or if database was not updated successfully.
	 * 
	 * @param book
	 * @return TRUE or FALSE
	 */
	public boolean disposeBook(Book book) {
		if(book == null) return false;
		
		if(!book.isStatusOnShelf()) return false;
		
		// Changing book generalStatus
		book.setStatus(ItemStatus.DISPOSED);
		// Updating database
		return updateBookDB(
				book.getBookID(),
				"ITEMSTATUS",
				book.getStatus().toString());
	}
	
	/**
	 * This method is requesting database STRING record update. If book
	 * record in database updated successfully TRUE. It returns FALSE if
	 * book record in database was not updated successfully.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return TRUE or FALSE
	 */
	public boolean updateBookDB(long id, String field, String data){
		return ParserObjectToSQL.getInstance().changeField(
				"BOOKS",
				field.toUpperCase(),
				data,
				id);
	}
	
	/**
	 * This method is requesting database INT record update. If book record
	 * in database updated successfully TRUE. It returns FALSE if book
	 * record in database was not updated successfully.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return TRUE or FALSE
	 */
	public boolean updateBookDB(long id, String field, int data){
		return ParserObjectToSQL.getInstance().changeField(
				"BOOKS",
				field.toUpperCase(),
				data,
				id);
	}
	
	/**
	 * This method is requesting database DATE record update. If book record
	 * in database updated successfully TRUE. It returns FALSE if book
	 * record in database was not updated successfully.
	 * 
	 * @param id
	 * @param field
	 * @param data
	 * @return TRUE or FALSE
	 */
	public boolean updateBookDB(long id, String field, java.util.Date data){
		return ParserObjectToSQL.getInstance().changeField(
				"BOOKS",
				field.toUpperCase(),
				data,
				id);
	}
	
	/**
	 * This method sends request to return book with specified id from
	 * database.
	 * 
	 * @param id
	 * @return book if book with specified id found or NULL
	 */
	public Book findBookByID(long id){
		return (id > 0) ?
				ParserSQLToObject.getInstance().getBook(id) :
					null;
	}
	
	/**
	 * This method returns list of books with specified id from
	 * database with one entry only or empty list if book not
	 * found.
	 * 
	 * @param id
	 * @return book if book with specified id found or NULL
	 */
	public List<Book> findBooksByID(long id){
		if(id <= 0L) return new ArrayList<Book>();
		
		List<Book> list = new ArrayList<Book>();
		Book book = findBookByID(id);
		// If book is NULL return empty List
		if(book == null) return new ArrayList<Book>();
		
		list.add(book);
		return list;
	}
	
	/**
	 * This method returns list of books with specified title
	 * from database or empty list if books not found.
	 * 
	 * @param title
	 * @return List
	 */
	public List<Book> findBookByTitle(String title){
		return (title == null) ?
				new ArrayList<Book>() :
					ParserSQLToObject.getInstance().getBooks(
							"TITLE",
							title.toLowerCase());
	}
	
	/**
	 * This method returns list of books with specified authors
	 * from database or empty list if books not found.
	 * 
	 * @param author
	 * @return List
	 */
	public List<Book> findBookByAuthor(String author){
		return (author == null) ?
				new ArrayList<Book>() :
					ParserSQLToObject.getInstance().getBooks(
							"AUTHORS",
							author.toLowerCase());
	}
	
	/**
	 * This method is returning Book with specified ISBN. If book not found
	 * than null returned.
	 * 
	 * @param isbn
	 * @return book o NULL
	 */
	public Book findBookByISBN(String isbn){
		return (isbn != null) ?
				ParserSQLToObject.getInstance().getBookByISBN(isbn) :
					null;
	}
	
	/**
	 * This method returns a list of Books with one entry only if book with
	 * specified ISBN is found. An empty list is returned if book not found.
	 * 
	 * @param isbn
	 * @return list with one entry or empty list
	 */
	public List<Book> findBooksByISBN(String isbn){
		if(isbn == null) return new ArrayList<Book>();
		
		List<Book> list = new ArrayList<Book>();
		Book book = findBookByISBN(isbn);
		
		if(book == null) return new ArrayList<Book>();
		
		list.add(book);
		return list;
	}
	
	/**
	 * This method returns TRUE if book is in the Reservation list.
	 * It also returns TRUE if book object passed as the parameter to
	 * current method is equal to NULL. In all other cases FALSE is
	 * returned.
	 * @param book
	 * @return TRUE or FALSE 
	 */
	public boolean isReserved(Book book){
		return (book != null) ?
				AdminReservations.getInstance().isReserved(book) :
					true;
	}
	
	/**
	 * This method returns TRUE if book is in the active Loan list.
	 * It also returns TRUE if book object passed as the parameter to
	 * current method is equal to NULL. In all other cases FALSE is
	 * returned.
	 * 
	 * @param book
	 * @return TRUE or FALSE 
	 */
	public boolean isOnLoan(Book book){
		return (book != null) ?
				AdminLoans.getInstance().isOnLoan(book) :
					true;
	}
	
	/**
	 * This method is checking if ACTIVE reservation exists for current book
	 * in database and if user who reserved the book is same user that tries
	 * to borrow the book.
	 * Than this method requests loan administrator to create new loan
	 * forwarding all parameters to it. After that reservation for this book
	 * deactivated and book generalStatus is updated in database.
	 * 
	 * @param user
	 * @param book
	 * @param days
	 * @return boolean
	 */
	private boolean borrowReservedBook(User user, Book book, int days){
		if(user == null || book == null) return false;
		
		if(AdminReservations.getInstance().getActiveReservation(book).getUserID()
				!= user.getUserID()) return false;
		
		// Adding new loan to database
		Loan loan = AdminLoans.getInstance().newLoan(
				book,
				user,
				days);
		
		if(loan == null) return false;
		
		// Requesting to remove book from Reservation list
		if(!AdminReservations.getInstance().remove(book)) return false;
		
		// Changing Item generalStatus to "ONLOAN"
		book.setStatus(ItemStatus.ONLOAN);
		
		return updateBookDB(book.getBookID(),
				"STATUS",
				book.getStatus().toString());
	}
	
	/**
	 * This method requests loan administrator to create new loan forwarding
	 * all parameters to it, and also updating book generalStatus in database after
	 * new loan has been created and saved successfully.
	 * 
	 * @param user
	 * @param book
	 * @param days
	 * @return boolean
	 */
	private boolean borrowNotReservedBook(User user, Book book, int days){
		// Requesting to create and add new loan to database
		Loan loan = AdminLoans.getInstance().newLoan(
				book,
				user,
				days);
		
		if(loan == null) return false;
		
		// Changing Item generalStatus to "ONLOAN"
		book.setStatus(ItemStatus.ONLOAN);
		
		return updateBookDB(
				book.getBookID(),
				"STATUS",
				book.getStatus().toString());
	}
	
	/**
	 * Use his method if user wants to borrow the book. New loan created
	 * for the period specified in the parameter field.
	 * 
	 * @param user
	 * @param book
	 * @param days
	 * @return boolean
	 */
	public boolean borrowBook(User user, Book book, int days) {
		if(user == null || book == null || days <= 0) return false;
		
		if(!user.isActive()) return false;
		
		if(book.isStatusDisposed() || book.isStatusUnknown()) return false;
		
		return (isBookStatusOnLoan(book)) ?
				renewLoan(user, book, days) :
					(!isReserved(book)) ?
							borrowNotReservedBook(user, book, days) :
								borrowReservedBook(user, book, days);
	}
	
	/**
	 * Use his method if user wants to borrow the book. New loan created
	 * for standard period specified in preferences.
	 * 
	 * @param user
	 * @param book
	 * @return boolean
	 */
	public boolean borrowBook(User user, Book book) {
		if(user == null || book == null) return false;
		
		if(!user.isActive()) return false;
		
		if(book.isStatusDisposed() || book.isStatusUnknown()) return false;
		
		int days = AdminPrefs.STANDARD_LOAN_PERIOD;
		if (days <= 0) return false;
		
		return (isBookStatusOnLoan(book)) ?
				renewLoan(user, book, days) :
					(!isReserved(book)) ?
							borrowNotReservedBook(user, book, days) :
								borrowReservedBook(user, book, days);
	}
	
	/**
	 * This method renews loan. New loan created for standard period
	 * specified in preferences.
	 * 
	 * @param book
	 * @return boolean
	 */
	public boolean renewLoan(Book book) {
		if(book == null) return false;
		
		long userID = AdminLoans.getInstance().getBookActiveLoan(book.getBookID()).getUserID();
		if(userID <= 0L) return false;
		
		User user = AdminUsers.getInstance().getUser(userID);
		if(user == null) return false;
		
		if(!user.isActive() || book.isStatusDisposed() || book.isStatusUnknown()) return false;
		
		int days = AdminPrefs.STANDARD_LOAN_PERIOD;
		if(days <= 0) return false;
		
		if (!isReserved(book)) {
			// First step is returning book
			return (returnBook(book)) ?
					borrowNotReservedBook(user, book, days) :
						false;
		} else {
			Reservation reservation = AdminReservations.getInstance().getActiveReservation(
							book);
			if (reservation != null) {
				return (reservation.getUserID() == user.getUserID()) ?
						(returnBook(book)) ?
								borrowReservedBook(user, book, days) :
									false :
										false;
			} else {
				return (returnBook(book)) ?
						borrowNotReservedBook(user, book, days) :
							false;
			}
		}
	}
	
	/**
	 * This method renews loan. New loan created for standard period
	 * specified in preferences.
	 * 
	 * @param user
	 * @param book
	 * @return boolean
	 */
	public boolean renewLoan(User user, Book book) {
		if(user == null || book == null) return false;
		
		if(!user.isActive() || book.isStatusDisposed() || book.isStatusUnknown()) return false;
		
		int days = AdminPrefs.STANDARD_LOAN_PERIOD;
		if (days <= 0) return false;
		
		if (!isReserved(book)) {
			return (returnBook(book)) ?
					borrowNotReservedBook(user, book, days) :
						false;
		} else {
			Reservation reservation = AdminReservations.getInstance().getActiveReservation(book);
			if (reservation != null) {
				return (reservation.getUserID() == user.getUserID()) ?
						(returnBook(book)) ?
							borrowReservedBook(user, book, days) :
								false :
									false;
			} else {
				return (returnBook(book)) ?
						borrowNotReservedBook(user, book, days) :
							false;
			}
		}
	}
	
	/**
	 * This method renews loan for the period specified in the parameter field.
	 * 
	 * @param user
	 * @param book
	 * @param days
	 * @return boolean
	 */
	public boolean renewLoan(User user, Book book, int days) {
		if(user == null || book == null || days <= 0) return false;
		
		if(!user.isActive() || book.isStatusDisposed() || book.isStatusUnknown()) return false;
		
		if (!isReserved(book)) {
			return (returnBook(book)) ?
					borrowNotReservedBook(user, book, days) :
						 false;
		} else {
			Reservation reservation = AdminReservations.getInstance()
					.getActiveReservation(book);
			if (reservation != null) {
				return (reservation.getUserID() == user.getUserID()) ?
						(returnBook(book)) ?
								borrowReservedBook(user, book, days) :
									false :
										false;
			} else {
				return (returnBook(book)) ?
						borrowNotReservedBook(user, book, days) :
							false;
			}
		}
	}
	
	/**
	 * This method renews loan. New loan created for standard period
	 * specified in preferences.
	 * 
	 * @param loan
	 * @return boolean
	 */
	public boolean renewLoan(Loan loan){
		if (loan == null) return false;
		
		User user = AdminUsers.getInstance().getUser(loan.getUserID());
		Book book = AdminBooks.getInstance().findBookByID(loan.getBookID());
		int days = AdminPrefs.STANDARD_LOAN_PERIOD;
		
		if(user == null || book == null || days <= 0) return false;
		
		if(!user.isActive() || book.isStatusDisposed() || book.isStatusUnknown()) return false;
		
		if (!isReserved(book)) {
			return (returnBook(book)) ?
					borrowNotReservedBook(user, book, days) :
						false;
		} else {
			Reservation reservation = AdminReservations.getInstance().getActiveReservation(book);
			if (reservation != null) {
				return (reservation.getUserID() == user.getUserID()) ?
						(returnBook(book)) ?
								borrowReservedBook(user, book, days):
									false :
										false;
			} else {
				return (returnBook(book)) ?
						borrowNotReservedBook(user, book, days) :
							false;
			}
		}
	}
	
	/**
	 * This method reserves the specified book for specified user.
	 * This method returns FALSE if book and user equal to NULL.
	 * This method returns FALSE if book generalStatus set to DISPOSED or
	 * to UNKNOWN.
	 * 
	 * @param book
	 * @param user
	 * @return boolean
	 */
	public boolean reserveBook(Book book, User user){
		// Checking that user and book is not null
		if(user == null || book == null) return false;
		
		// Check if book generalStatus is not DISPOSED
		if(book.isStatusDisposed() || book.isStatusUnknown()) return false;
		
		// Checking that book is not reserved yet
		return (!AdminReservations.getInstance().isReserved(book)) ?
				standardReservation(book, user) :
					false;
	}
	
	/**
	 * This method is called ONLY from reserveBook() method in case, if
	 * book is not reserved and reservation needs to be processed in
	 * standard way.
	 * This method returns TRUE if book reserved successfully by
	 * Reservation administrator, and FALSE if book generalStatus update
	 * in database has been failed or book NOT has not been reserved.
	 * 
	 * @param book
	 * @param user
	 * @return boolean
	 */
	private boolean standardReservation(Book book, User user){
		if(!AdminReservations.getInstance().reserve(user, book)) return false;
		
		// Checking book generalStatus is ONSHELF
		if(!isBookStatusOnShelf(book)) return false;
		
		// Updating book generalStatus in database
		book.setStatus(ItemStatus.RESERVED);
		return updateBookDB(book.getBookID(), "STATUS",	book.getStatus().toString());
	}
	
	/**
	 * This method changes book generalStatus ONLY and should be requested from
	 * Reservation administrator ONLY. If needed to cancel reservation,
	 * than please refer to appropriate method in AdminReservations class.
	 * 
	 * @param book
	 * @return boolean
	 */
	public boolean changeBookStatusFromReserved(Book book){
		if(book == null) return false;
		
		if(isBookStatusReserved(book)) {
			// Changing book generalStatus to "ONSHELF"
			book.setStatus(ItemStatus.ONSHELF);
			return updateBookDB(book.getBookID(), "STATUS", book.getStatus().toString());
		} else {
			// Returning true as book generalStatus is other than RESERVED
			return true;
		}
	}
	
	/**
	 * This method changes book generalStatus ONLY and should be requested from
	 * Reservation administrator ONLY. If needed to cancel reservation,
	 * than please refer to appropriate method in AdminReservations class.
	 * 
	 * @param bookID
	 * @return boolean
	 */
	public boolean changeBookStatusFromReserved(long bookID){
		if(bookID <= 0L) return false;
		
		Book book = getBook(bookID);
		// Checking that book is not null
		if(book == null) return false;
		
		if(isBookStatusReserved(book)) {
			// Changing book generalStatus to "ONSHELF"
			book.setStatus(ItemStatus.ONSHELF);
			return updateBookDB(book.getBookID(), "STATUS", book.getStatus().toString());
		} else {
			// Returning true as book generalStatus is other than RESERVED
			return true;
		}
	}
	
	/**
	 * This method requests Loan administrator to close current active loan
	 * for specified book and changes book generalStatus to either RESERVED if book
	 * is found in reservation list by Reservation's administrator, or ONSHELF.
	 * 
	 * @param book
	 * @return boolean
	 */
	public boolean returnBook(Book book) {
		// Checking if book is null
		if(book == null) return false;
		
		// Closing loan
		if(!AdminLoans.getInstance().closeLoan(book)) return false;
		
		// Changing Item GeneralStatus
		if(AdminReservations.getInstance().isReserved(book)){
			// Setting book generalStatus RESERVED if book is reserved
			book.setStatus(ItemStatus.RESERVED);
			return updateBookDB(
					book.getBookID(),
					"STATUS",
					book.getStatus().toString());
		} else{
			// Setting book generalStatus ONSHELF if book is not reserved
			book.setStatus(ItemStatus.ONSHELF);
			return updateBookDB(
					book.getBookID(),
					"STATUS",
					book.getStatus().toString());
		}
	}
	
	/**
	 * This method requests Loan administrator to close current active loan
	 * for specified book and changes book generalStatus to either RESERVED if book
	 * is found in reservation list by Reservation's administrator, or ONSHELF.
	 * 
	 * @param loan
	 * @return boolean
	 */
	public boolean returnBook(Loan loan) {
		// Checking if book is null
		if(loan == null) return false;
		
		Book book = AdminBooks.getInstance().findBookByID(loan.getBookID());
		if(book == null) return false;
		
		// Closing loan
		if(!AdminLoans.getInstance().closeLoan(book)) return false;
		
		// Changing Item GeneralStatus
		if (AdminReservations.getInstance().isReserved(book)) {
			// Setting book generalStatus RESERVED if book is reserved
			book.setStatus(ItemStatus.RESERVED);
			return updateBookDB(book.getBookID(), "STATUS", book.getStatus().toString());
		} else {
			// Setting book generalStatus ONSHELF if book is not reserved
			book.setStatus(ItemStatus.ONSHELF);
			return updateBookDB(book.getBookID(), "STATUS", book.getStatus().toString());
		}
	}
	
	/**
	 * This method returns TRUE if book generalStatus set to ONLOAN. It
	 * returns FALSE if book object passed as the parameter to current
	 * method is equal to NULL or book generalStatus is set to generalStatus other
	 * than ONLOAN.
	 * 
	 * @param book
	 * @return boolean 
	 */
	public boolean isBookStatusOnLoan(Book book){
		return (book != null) ?
				book.isStatusOnLoan() :
					false;
	}
	
	/**
	 * This method returns TRUE if book generalStatus set to RESERVED. It
	 * returns FALSE if book object passed as the parameter to current
	 * method is equal to NULL or book generalStatus is set other than RESERVED.
	 * 
	 * @param book
	 * @return boolean 
	 */
	public boolean isBookStatusReserved(Book book){
		return (book != null) ?
				book.isStatusReserved() :
					false;
	}
	
	/**
	 * This method returns TRUE if book generalStatus set to DISPOSED. It
	 * returns FALSE if book object passed as the parameter to current
	 * method is equal to NULL or book generalStatus is set to generalStatus other
	 * than DISPOSED.
	 * 
	 * @param book
	 * @return boolean 
	 */
	public boolean isBookStatusDisposed(Book book){
		return (book != null) ?
				book.isStatusDisposed() :
					false;
	}
	
	/**
	 * This method returns TRUE if book generalStatus set to ONSHELF. It
	 * returns FALSE if book object passed as the parameter to current
	 * method is equal to NULL or book generalStatus is set to generalStatus other
	 * than ONSHELF.
	 * 
	 * @param book
	 * @return boolean 
	 */
	public boolean isBookStatusOnShelf(Book book){
		return (book != null) ?
				book.isStatusOnShelf() :
					false;
	}
	
	/**
	 * This method returns TRUE if book generalStatus set to UNKNOWN. It
	 * returns FALSE if book object passed as the parameter to current
	 * method is equal to NULL or book generalStatus is set to generalStatus other
	 * than UNKNOWN.
	 * 
	 * @param book
	 * @return boolean 
	 */
	public boolean isBookStatusUnknown(Book book){
		return (book != null) ?
				book.isStatusUnknown() :
					false;
	}
}