/*
 * Copyright 2018 Vassili Kurman
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vkurman.jbooklibrary.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;

import vkurman.jbooklibrary.core.Address;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Borrower;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.core.Loan;
import vkurman.jbooklibrary.core.Payment;
import vkurman.jbooklibrary.core.Request;
import vkurman.jbooklibrary.core.Reservation;
import vkurman.jbooklibrary.enums.EmploymentTerminationReason;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.IDCardDeactivationReason;
import vkurman.jbooklibrary.enums.ItemStatus;
import vkurman.jbooklibrary.enums.ItemType;
import vkurman.jbooklibrary.enums.Sex;
import vkurman.jbooklibrary.enums.Withdrawal;

/**
 * The ONLY purpose of the ObjectConstructor class is to creates
 * Library Application's objects. Specific object is created from
 * passed java.sql.ResultSet object as parameter to concrete method
 * of current class. Usually if no record found in Database, an empty
 * object is returned. Empty object is an object of specified class
 * with no values set (default Java language values are set, such as
 * '0' for int or 'null' for object, etc.).
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ObjectConstructor {
	private volatile static ObjectConstructor constructor;
	
	/**
	 * Constructor.
	 */
	private ObjectConstructor() {
	}
	
	/**
	 * This method ensures that only one instance of ObjectConstructor
	 * class exists during application runtime.
	 * 
	 * @return ObjectConstructor object
	 */
	public static ObjectConstructor getInstance() {
		// Double-checked locking with synchronisation
		if(constructor == null) {
			synchronized (ObjectConstructor.class) {
				if(constructor == null) {
					constructor = new ObjectConstructor();
				}
			}
		}
		// Under either circumstance this returns the instance
		return constructor;
	}
	
	/**
	 * This method creates Address object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return Address object
	 * @throws SQLException
	 */
	public Address createAddress(ResultSet rs) throws SQLException{
		Address address = new Address(Locale.UK);
		
	    address.setAddressID(rs.getLong("ID"));
	    address.setFlatNumber(rs.getString("FLATNUMBER"));
	    address.setHouseName(rs.getString("HOUSENAME"));
	    address.setHouseNumber(rs.getString("HOUSENUMBER"));
	    address.setStreet(rs.getString("STREET"));
	    address.setCity(rs.getString("CITY"));
	    address.setCounty(rs.getString("COUNTY"));
	    address.setPostcode(rs.getString("POSTCODE"));
	    address.setCountry(rs.getString("COUNTRY"));
    	
    	return address;
	}
	
	/**
	 * This method creates Book object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return Book object
	 * @throws SQLException
	 */
	public Book createBook(ResultSet rs) throws SQLException{
		Book book = new Book();
    	
    	long bookID = rs.getLong("ID");
    	
    	book.setBookID(bookID);
    	book.setEdition(rs.getInt("EDITION"));
    	book.setPagination(rs.getInt("PAGINATION"));
    	book.setRecommendedAge(rs.getInt("RECOMMENDEDAGE"));
    	book.setPrice(rs.getBigDecimal("PRICE"));
    	book.setStatus(ItemStatus.fromString(rs.getString("STATUS")));
   		book.setTitle(rs.getString("TITLE"));
		book.setSubtitle(rs.getString("SUBTITLE"));
		book.setLanguage(rs.getString("LANGUAGE"));
		book.setDescription(rs.getString("DESCRIPTION"));
		book.setCondition(rs.getString("CONDITION"));
		book.setEditor(rs.getString("EDITOR"));
		book.setPublicationPlace(rs.getString("PUBLICATIONPLACE"));
		book.setPublisher(rs.getString("PUBLISHER"));
		book.setIsbn(rs.getString("ISBN"));
		book.setFormat(rs.getString("FORMAT"));
		book.setLocation(rs.getString("LOCATION"));
		book.setTranslatedFrom(rs.getString("TRANSLATEDFROM"));
		book.setSeries(rs.getString("SERIES"));
		book.setSupplements(rs.getString("SUPPLEMENTS"));
		book.setFootNote(rs.getString("FOOTNOTE"));
		book.setAuthorsFromString(rs.getString("AUTHORS"));
		book.setSubjectHeadingsFromString(rs.getString("SUBHEADINGS"));
		book.setGenresFromString(rs.getString("GENRES"));
		book.setKeywordsFromString(rs.getString("KEYWORDS"));
		if(rs.getTimestamp("DATEOFENTRY") != null){
    		Calendar dateOfEntry = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		dateOfEntry.setTimeInMillis(rs.getTimestamp("DATEOFENTRY").getTime());
    		book.setDateOfEntry(dateOfEntry);
    	}
    	if(rs.getDate("PUBLICATIONDATE") != null){
    		Calendar publicationDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		publicationDate.setTime(new java.util.Date(rs.getDate("PUBLICATIONDATE").getTime()));
    		book.setPublicationDate(publicationDate);
    	}
		
    	return book;
	}
	
	/**
	 * This method creates Borrower object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return Borrower object
	 * @throws SQLException
	 */
	public Borrower createBorrower(ResultSet rs) throws SQLException{
		Borrower borrower = new Borrower();
		
    	Calendar dob = (Calendar) Calendar.getInstance().clone();
    	Calendar registered = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    	Calendar deactivated = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    	
    	long userID = rs.getLong("ID");
    	
    	borrower.setUserID(userID);
    	if(rs.getInt("CURRENTADDRESS") > 0L){
    		borrower.addAddress(ParserSQLToObject.getInstance().getAddress(rs.getLong("CURRENTADDRESS")));
    	}
    	borrower.setTitle(rs.getString("TITLE"));
    	borrower.setDegree(rs.getString("DEGREE"));
    	borrower.setInstitution(rs.getString("INSTITUTION"));
    	borrower.setFirstname(rs.getString("FIRSTNAME"));
    	borrower.setMiddlename(rs.getString("MIDDLENAME"));
    	borrower.setSurname(rs.getString("SURNAME"));
    	if(rs.getString("SEX") != null){
    		borrower.setSex(Sex.fromString(rs.getString("SEX")));
    	}
    	if(rs.getDate("DOB") != null){
    		dob.setTime(rs.getDate("DOB"));
    		borrower.setDob(dob);
    	}
    	borrower.setPrivateEmail(rs.getString("PRIVATEEMAIL"));
    	borrower.setPrivatePhone(rs.getString("PRIVATEPHONE"));
    	borrower.setPrivateMobile(rs.getString("PRIVATEMOBILE"));
    	borrower.setPrivateFax(rs.getString("PRIVATEFAX"));
    	borrower.setOfficeEmail(rs.getString("OFFICEEMAIL"));
    	borrower.setOfficePhone(rs.getString("OFFICEPHONE"));
    	borrower.setOfficeMobile(rs.getString("OFFICEMOBILE"));
    	borrower.setOfficeFax(rs.getString("OFFICEFAX"));
    	borrower.setUrl(rs.getString("URL"));
    	borrower.setUserCategory(rs.getString("USERCATEGORY"));
    	if(rs.getString("WITHDRAWAL") != null){
    		borrower.setWithdrawal(Withdrawal.fromString(rs.getString("WITHDRAWAL")));
    	}
    	if(rs.getString("STATUS") != null){
    		borrower.setGeneralStatus(GeneralStatus.fromString(rs.getString("STATUS")));
    	}
    	
   		borrower.setIdCardNumber(rs.getLong("IDCARD"));
   		
    	if(rs.getDate("REGISTERED") != null){
    		registered.setTimeInMillis(rs.getTimestamp("REGISTERED").getTime());
    		borrower.setRegistered(registered);
    	}
    	if(rs.getDate("DEACTIVATED") != null){
    		deactivated.setTimeInMillis(rs.getTimestamp("DEACTIVATED").getTime());
    		borrower.setDeactivated(deactivated);
    	}
    	
    	return borrower;
	}
	
	/**
	 * This method creates Fine object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return Fine object
	 * @throws SQLException
	 */
	public Fine createFine(ResultSet rs) throws SQLException{
		Fine fine = new Fine();
    	
    	long fineID = rs.getLong("ID");
    	long userID = rs.getLong("USERID");
    	
    	fine.setFineID(fineID);
    	fine.setDays(rs.getInt("DAYS"));
    	fine.setLoanID(rs.getLong("LOANID"));
    	fine.setUserID(userID);
    	fine.setUserName(ParserSQLToObject.getInstance().getUserName(userID));
    	fine.setFinePerDay(rs.getBigDecimal("FINEPERDAY"));
    	fine.setValuePaid(rs.getBigDecimal("VALUEPAID"));
    	if(rs.getTimestamp("FINEPAIDDATE") != null){
    		Calendar finePaidDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		finePaidDate.setTimeInMillis(rs.getTimestamp("FINEPAIDDATE").getTime());
    		fine.setFinePaidDate(finePaidDate);
    	}
    	fine.setStatus(GeneralStatus.fromString((rs.getString("STATUS"))));
    	
    	return fine;
	}
	
	/**
	 * This method creates IDCard object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return IDCard object
	 * @throws SQLException
	 */
	public IDCard createIDCard(ResultSet rs) throws SQLException{
		IDCard idCard = new IDCard();
    	
		idCard.setCardID(rs.getLong("ID"));
		idCard.setUserID(rs.getLong("USERID"));
    	idCard.setUserName(rs.getString("USERNAME"));
    	if(rs.getTimestamp("VALIDFROM") != null){
    		Calendar validFromDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		validFromDate.setTimeInMillis(rs.getTimestamp("VALIDFROM").getTime());
    		idCard.setValidFrom(validFromDate);
    	}
    	if(rs.getTimestamp("VALIDTO") != null){
    		Calendar validToDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		validToDate.setTimeInMillis(rs.getTimestamp("VALIDTO").getTime());
    		idCard.setValidTo(validToDate);
    	}
    	if(rs.getTimestamp("DEACTIVATIONDATE") != null){
    		Calendar deactivationDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		deactivationDate.setTimeInMillis(rs.getTimestamp("DEACTIVATIONDATE").getTime());
    		idCard.setDeactivationDate(deactivationDate);
    	}
    	if(rs.getString("DEACTIVATIONREASON") != null){
    		idCard.setDeactivationReason(IDCardDeactivationReason.fromString(rs.getString("DEACTIVATIONREASON")));
    	}
    	if(rs.getString("STATUS") != null){
    		idCard.setGeneralStatus(GeneralStatus.fromString(rs.getString("STATUS")));
    	}
    	
    	return idCard;
	}
	
	/**
	 * This method creates Librarian object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return Librarian object
	 * @throws SQLException
	 */
	public Librarian createLibrarian(ResultSet rs) throws SQLException{
		Librarian librarian = new Librarian();
    	
    	long userID = rs.getLong("ID");
    	
    	librarian.setUserID(userID);
    	if(rs.getLong("CURRENTADDRESS") > 0L){
    		librarian.addAddress(ParserSQLToObject.getInstance().getAddress(rs.getLong("CURRENTADDRESS")));
    	}
    	librarian.setTitle(rs.getString("TITLE"));
    	librarian.setDegree(rs.getString("DEGREE"));
    	librarian.setInstitution(rs.getString("INSTITUTION"));
    	librarian.setFirstname(rs.getString("FIRSTNAME"));
    	librarian.setMiddlename(rs.getString("MIDDLENAME"));
    	librarian.setSurname(rs.getString("SURNAME"));
    	if(rs.getString("SEX") != null){
    		librarian.setSex(Sex.fromString(rs.getString("SEX")));
    	}
    	if(rs.getDate("DOB") != null){
    		Calendar dob = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		dob.setTime(rs.getDate("DOB"));
    		librarian.setDob(dob);
    	}
    	librarian.setPrivateEmail(rs.getString("PRIVATEEMAIL"));
    	librarian.setPrivatePhone(rs.getString("PRIVATEPHONE"));
    	librarian.setPrivateMobile(rs.getString("PRIVATEMOBILE"));
    	librarian.setPrivateFax(rs.getString("PRIVATEFAX"));
    	librarian.setOfficeEmail(rs.getString("OFFICEEMAIL"));
    	librarian.setOfficePhone(rs.getString("OFFICEPHONE"));
    	librarian.setOfficeMobile(rs.getString("OFFICEMOBILE"));
    	librarian.setOfficeFax(rs.getString("OFFICEFAX"));
    	librarian.setUrl(rs.getString("URL"));
    	librarian.setUserCategory(rs.getString("USERCATEGORY"));
    	if(rs.getString("WITHDRAWAL") != null){
    		librarian.setWithdrawal(Withdrawal.fromString(rs.getString("WITHDRAWAL")));
    	}
    	if(rs.getString("STATUS") != null){
    		librarian.setGeneralStatus(GeneralStatus.fromString(rs.getString("STATUS")));
    	}
    	
   		librarian.setIdCardNumber(rs.getLong("IDCARD"));
    	
   		if(rs.getDate("EMPLOYMENTSTARTED") != null){
    		Calendar employmentStarted = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
        	employmentStarted.setTimeInMillis(rs.getTimestamp("EMPLOYMENTSTARTED").getTime());
    		librarian.setEmploymentStarted(employmentStarted);
    	}
    	if(rs.getDate("EMPLOYMENTFINISHED") != null){
    		Calendar employmentFinished = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		employmentFinished.setTimeInMillis(rs.getTimestamp("EMPLOYMENTFINISHED").getTime());
    		librarian.setEmploymentFinished(employmentFinished);
    	}
    	if(rs.getString("TERMINATIONREASON") != null){
    		librarian.setTerminationReason(EmploymentTerminationReason.fromString(rs.getString("TERMINATIONREASON")));
    	}
    	
    	return librarian;
	}
	
	/**
	 * This method creates Loan object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return Loan object
	 * @throws SQLException
	 */
	public Loan createLoan(ResultSet rs) throws SQLException{
		Loan loan = new Loan();
		
	    loan.setLoanID(rs.getLong("ID"));
	    
    	if(rs.getDate("BORROWDATE") != null){
    		Calendar borrowDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		borrowDate.setTimeInMillis(rs.getTimestamp("BORROWDATE").getTime());
    		loan.setBorrowDate(borrowDate);
    	}
    	
    	if(rs.getDate("DUEDATE") != null){
    		Calendar dueDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		dueDate.setTimeInMillis(rs.getTimestamp("DUEDATE").getTime());
    		loan.setDueDate(dueDate);
    	}
    	
    	if(rs.getDate("RETURNDATE") != null){
    		Calendar returnDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		returnDate.setTimeInMillis(rs.getTimestamp("RETURNDATE").getTime());
    		loan.setReturnDate(returnDate);
    	}
    	
    	long bookID = rs.getLong("BOOKID");
    	loan.setBookID(bookID);
    	loan.setBookTitle(ParserSQLToObject.getInstance().getBookTitle(bookID));
    	
    	long userID = rs.getLong("USERID");
    	loan.setUserID(userID);
    	loan.setUserName(ParserSQLToObject.getInstance().getUserName(userID));
    	
    	if(rs.getString("STATUS") != null){
    		loan.setStatus(GeneralStatus.fromString(rs.getString("STATUS")));
    	}
    	
    	return loan;
	}
	
	/**
	 * This method creates Payment object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return Payment object
	 * @throws SQLException
	 */
	public Payment createPayment(ResultSet rs) throws SQLException{
		Payment payment = new Payment();
    	
    	payment.setPaymentID(rs.getLong("ID"));
    	payment.setFineID(rs.getLong("FINEID"));
    	long userID = rs.getLong("USERID");
    	payment.setUserID(userID);
    	payment.setUserName(ParserSQLToObject.getInstance().getUserName(userID));
    	payment.setAmount(rs.getBigDecimal("VALUE"));
    	if(rs.getTimestamp("PAYMENTDATE") != null){
    		Calendar paymentDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		paymentDate.setTimeInMillis(rs.getTimestamp("PAYMENTDATE").getTime());
    		payment.setPaymentDate(paymentDate);
    	}
    	
    	return payment;
	}
	
	/**
	 * This method creates Reservation object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return Reservation object
	 * @throws SQLException
	 */
	public Reservation createReservation(ResultSet rs) throws SQLException{
		Reservation reservation = new Reservation();
		
    	reservation.setReservationID(rs.getLong("ID"));
    	
    	long bookID = rs.getLong("BOOKID");
    	reservation.setBookID(bookID);
    	reservation.setBookTitle(ParserSQLToObject.getInstance().getBookTitle(bookID));
    	
    	long userID = rs.getLong("USERID");
    	reservation.setUserID(userID);
    	reservation.setUserName(ParserSQLToObject.getInstance().getUserName(userID));
    	
    	if(rs.getDate("RESERVEDATE") != null){
    		Calendar reserveDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		reserveDate.setTimeInMillis(rs.getTimestamp("RESERVEDATE").getTime());
    		reservation.setReserveDate(reserveDate);
    	}
    	if(rs.getDate("EXPIREDATE") != null){
    		Calendar expireDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		expireDate.setTimeInMillis(rs.getTimestamp("EXPIREDATE").getTime());
    		reservation.setExpireDate(expireDate);
    	}
    	if(rs.getString("STATUS") != null){
    		reservation.setGeneralStatus(GeneralStatus.fromString(rs.getString("STATUS")));
    	}
    	
    	return reservation;
	}
	
	/**
	 * Creates Request object from ResultSet passed as a reference
	 * through parameter.
	 * 
	 * @param rs
	 * @return Request object
	 * @throws SQLException
	 */
	public Request createRequest(ResultSet rs) throws SQLException{
		Request request = new Request();
		
		request.setID(rs.getLong("ID"));
		if(rs.getString("ITEMTYPE") != null){
    		request.setType(ItemType.fromString(rs.getString("ITEMTYPE")));
    	}
    	request.setIsbn(rs.getString("ISBN"));
    	request.setTitle(rs.getString("TITLE"));
    	request.setIssue(rs.getString("ISSUE"));
    	request.setYear(rs.getString("YEAR"));
    	request.setAuthor(rs.getString("AUTHOR"));
    	request.setPublisher(rs.getString("PUBLISHER"));
		request.setQuantity(rs.getInt("QUANTITY"));
		request.setComments(rs.getString("COMMENTS"));
    	if(rs.getDate("REQUESTDATE") != null){
    		Calendar requestDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		requestDate.setTimeInMillis(rs.getTimestamp("REQUESTDATE").getTime());
    		request.setRequestDate(requestDate);
    	}
    	if(rs.getDate("CANCELATIONDATE") != null){
    		Calendar cancelationDate = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
    		cancelationDate.setTimeInMillis(rs.getTimestamp("CANCELATIONDATE").getTime());
    		request.setCancelationDate(cancelationDate);
    	}
    	if(rs.getString("STATUS") != null){
    		request.setStatus(GeneralStatus.fromString(rs.getString("STATUS")));
    	}
    	
    	return request;
	}
}