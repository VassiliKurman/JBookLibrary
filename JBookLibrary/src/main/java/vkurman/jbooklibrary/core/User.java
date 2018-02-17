package vkurman.jbooklibrary.core;

import java.util.Calendar;

import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.Sex;
import vkurman.jbooklibrary.enums.Withdrawal;

/**
 * <code>User</code> is an abstract generic class for
 * defining concrete classes of specific user groups, like
 * <code>Librarian</code>, <code>Borrower</code>, etc.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public abstract class User {
	
	protected long userID;
	protected long idCardNumber;
	protected String title;
	protected String degree;
	protected String institution;
	protected String firstname;
	protected String middlename;
	protected String surname;
	protected Sex sex;
	protected GeneralStatus generalStatus;
	protected Calendar dob;
	protected Address currentAddress;
	protected String privateEmail;
	protected String privatePhone;
	protected String privateMobile;
	protected String privateFax;
	protected String officeEmail;
	protected String officePhone;
	protected String officeMobile;
	protected String officeFax;
	protected String url;
	protected String userCategory;
	protected Withdrawal withdrawal;
	
	/**
	 * Getter for 'generalStatus'
	 * 
	 * @return GeneralStatus
	 */
	public GeneralStatus getGeneralStatus() {
		return generalStatus;
	}
	
	/**
	 * Setter for 'generalStatus'
	 * 
	 * @param generalStatus
	 */
	public void setGeneralStatus(GeneralStatus generalStatus) {
		this.generalStatus = generalStatus;
	}
	
	/**
	 * Use this method to check if user is active or not.
	 * This method returns TRUE if 'generalStatus' set to ACTIVE or
	 * FALSE if 'generalStatus' set to INACTIVE.
	 * 
	 * @return boolean
	 */
	public boolean isActive(){
		return (generalStatus == GeneralStatus.ACTIVE) ? true : false;
	}
	
	/**
	 * Getter for 'idCardNumber'
	 * 
	 * @return long
	 */
	public long getIdCardNumber() {
		return idCardNumber;
	}
	
	/**
	 * Setter for 'idCardNumber'
	 * 
	 * @param idCardNumber
	 */
	public void setIdCardNumber(long idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	
	/**
	 * Use this method to check if User has IDCard or not.
	 * This method returns TRUE if 'idCardNumber' set greater
	 * than '0L'
	 * 
	 * @return boolean
	 */
	public boolean hasIDCard() {
		return (idCardNumber > 0L) ? true : false;
	}
	
	/**
	 * Getter for 'title'. Returns NULL if 'title' not set.
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter for 'title'
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter for 'middleName'. Returns NULL if 'middleName' not set.
	 * 
	 * @return String
	 */
	public String getMiddlename() {
		return middlename;
	}
	
	/**
	 * Setter for 'middleName'
	 * 
	 * @param middlename
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	/**
	 * Getter for user 'sex'. Returns NULL if 'sex' not set.
	 * 
	 * @return Sex
	 */
	public Sex getSex() {
		return sex;
	}
	
	/**
	 * Setter for user 'sex'
	 * 
	 * @param sex
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	/**
	 * Getter for 'userID'
	 * 
	 * @return long
	 */
	public long getUserID() {
		return userID;
	}
	
	/**
	 * Setter for 'userID'
	 * 
	 * @param userID
	 */
	public void setUserID(long userID) {
		this.userID = userID;
	}
	
	/**
	 * Getter for 'firstname'
	 * 
	 * @return String
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Setter for 'firstname'
	 * 
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * Getter for 'surname'
	 * 
	 * @return String
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Setter for 'surname'
	 * 
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * This method returns full name of the user. Full name contains
	 * firstname, middlename (if it has been set) and surname.
	 * 
	 * @return String
	 */
	public String getName() {
		StringBuilder sb = new StringBuilder();
		sb.append(firstname);
		if(middlename != null) {
			sb.append(" " + middlename);
		}
		sb.append(" " + surname);
		return sb.toString();
	}
	
	/**
	 * Getter for 'currentAddress'. This method returns NULL if
	 * 'currentAddress' is not set.
	 * 
	 * @return Address
	 */
	public Address getCurrentAddress() {
		return currentAddress;
	}
	
	/**
	 * Sets address for user.
	 * 
	 * @param address
	 */
	public void setAddress(Address address) {
		currentAddress = address;
	}
	
	/**
	 * This method changes current address with the address provided by
	 * passing reference to 'changeAddress()' method.
	 * 
	 * @param address
	 */
	public void addAddress(Address address) {
		changeAddress(address);
	}
	
	/**
	 * Changes current address to specified address. In case if specified
	 * <code>Address</code> is <code>null</code> than address is not
	 * changed. Instead please use <code>setAddress</code> method is
	 * specified address is <code>null</code>.
	 * 
	 * @param address
	 */
	public void changeAddress(Address address) {
		if(address != null){
			currentAddress = address;
		}
	}
	
	/**
	 * Getter for 'dob'. This method returns NULL if 'dob' not set.
	 * 
	 * @return Calendar
	 */
	public Calendar getDob() {
		return dob;
	}
	
	/**
	 * Setter for 'dob'
	 * 
	 * @param dob
	 */
	public void setDob(Calendar dob) {
		this.dob = dob;
	}
	
	/**
	 * Getter for user 'degree'
	 * 
	 * @return String
	 */
	public String getDegree() {
		return degree;
	}
	
	/**
	 * Setter for user 'degree'
	 * 
	 * @param degree
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	/**
	 * Getter for 'institution'
	 * 
	 * @return String
	 */
	public String getInstitution() {
		return institution;
	}
	
	/**
	 * Setter for 'institution'
	 * 
	 * @param institution
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
	/**
	 * Getter for 'privateEmail'
	 * 
	 * @return String
	 */
	public String getPrivateEmail() {
		return privateEmail;
	}
	
	/**
	 * Setter for 'privateEmail'
	 * 
	 * @param privateEmail
	 */
	public void setPrivateEmail(String privateEmail) {
		this.privateEmail = privateEmail;
	}
	
	/**
	 * Getter for 'privatePhone'
	 * 
	 * @return String
	 */
	public String getPrivatePhone() {
		return privatePhone;
	}
	
	/**
	 * Setter for 'privatePhone'
	 * 
	 * @param privatePhone
	 */
	public void setPrivatePhone(String privatePhone) {
		this.privatePhone = privatePhone;
	}
	
	/**
	 * Getter for 'privateMobile'
	 * 
	 * @return String
	 */
	public String getPrivateMobile() {
		return privateMobile;
	}
	
	/**
	 * Setter for 'privateMobile'
	 * 
	 * @param privateMobile
	 */
	public void setPrivateMobile(String privateMobile) {
		this.privateMobile = privateMobile;
	}
	
	/**
	 * Getter for 'privateFax'
	 * 
	 * @return String
	 */
	public String getPrivateFax() {
		return privateFax;
	}
	
	/**
	 * Setter for 'privateFax'
	 * 
	 * @param privateFax
	 */
	public void setPrivateFax(String privateFax) {
		this.privateFax = privateFax;
	}
	
	/**
	 * Getter for 'officeEmail'
	 * 
	 * @return String
	 */
	public String getOfficeEmail() {
		return officeEmail;
	}
	
	/**
	 * Setter for 'officeEmail'
	 * 
	 * @param officeEmail
	 */
	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}
	
	/**
	 * Getter for 'officePhone'
	 * 
	 * @return String
	 */
	public String getOfficePhone() {
		return officePhone;
	}
	
	/**
	 * Setter for 'officePhione'
	 * 
	 * @param officePhone
	 */
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	
	/**
	 * Getter for 'officeMobile'
	 * 
	 * @return String
	 */
	public String getOfficeMobile() {
		return officeMobile;
	}
	
	/**
	 * Setter for 'officeMobile'
	 * 
	 * @param officeMobile
	 */
	public void setOfficeMobile(String officeMobile) {
		this.officeMobile = officeMobile;
	}
	
	/**
	 * Getter for 'officeFax'
	 * 
	 * @return String
	 */
	public String getOfficeFax() {
		return officeFax;
	}
	
	/**
	 * Setter for 'officeFax'
	 * 
	 * @param officeFax
	 */
	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}
	
	/**
	 * Getter for 'url'
	 * 
	 * @return String
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Setter for 'url'
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Getter for 'userCategory'
	 * 
	 * @return String
	 */
	public String getUserCategory() {
		return userCategory;
	}
	
	/**
	 * Setter for 'userCategory'
	 * 
	 * @param userCategory
	 */
	public void setUserCategory(String userCategory) {
		this.userCategory = userCategory;
	}
	
	/**
	 * Getter for 'withdrawal'
	 * 
	 * @return String
	 */
	public Withdrawal getWithdrawal() {
		return withdrawal;
	}
	
	/**
	 * Setter for 'withdrawal'
	 * 
	 * @param withdrawal
	 */
	public void setWithdrawal(Withdrawal withdrawal) {
		this.withdrawal = withdrawal;
	}
	
	/**
	 * This method checks if two users are equals. If user surname
	 * and firstname are same, and if dob is set and is same, than
	 * method returns TRUE or else it returns FALSE.
	 * 
	 * @param user
	 * @return boolean
	 */
	public boolean equals(User user) {
		// Checking if surnames are same
		if(!surname.equals(user.getSurname())) return false;
		
		// Checking if firstnames are same
		if(!firstname.equals(user.getFirstname())) return false;
		
		// Checking if DOB's are set
		// DOB's are not set, return true
		if(dob == null || user.getDob() == null) return true;
		
		// Checking if DOB's are same
		return (dob.equals(user.getDob())) ? true : false;
	}
	
	/**
	 * This method overrides toString() method displaying user
	 * full name, user id, user idcard number and user current
	 * address in a single row.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		String user = "Name: " + this.getName() +
				", User ID Number: " + this.userID +
				", IDCard Number: " + this.idCardNumber +
				", Address: " + this.currentAddress;
		return user;
	}
}