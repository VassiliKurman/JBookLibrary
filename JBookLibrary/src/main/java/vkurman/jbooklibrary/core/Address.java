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

package vkurman.jbooklibrary.core;

import java.util.Locale;

/**
 * The Address class is designed for use in United Kingdom.
 * Variables flatNumber and houseNumber are declared of String
 * type to allow entering letters at the end of the numbers,
 * like 17a, 17b, etc.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Address implements Comparable<Address>{
	private long addressID;
	private String flatNumber;
	private String houseName;
	private String houseNumber;
	private String street;
	private String city;
	private String county;
	private String postcode;
	private String country;
	
	/**
	 * Default constructor with all fields set to NULL and 'addressID' set to '0L'.
	 */
	public Address (Locale locale) {
		if(locale == Locale.UK){
			// TODO Create AddressUK
		} else {
			// TODO Create general Address
		}
	}
	
	/**
	 * Constructor with 'addressID' set to default '0L'.
	 * 
	 * @param flatNumber
	 * @param houseName
	 * @param houseNumber
	 * @param street
	 * @param city
	 * @param county
	 * @param postcode
	 * @param country
	 */
	public Address (
			String flatNumber,
			String houseName,
			String houseNumber,
			String street,
			String city,
			String county,
			String postcode,
			String country){
					this(
							0L,
							flatNumber,
							houseName,
							houseNumber,
							street,
							city,
							county,
							postcode,
							country);
	}
	
	/**
	 * This constructor sets all fields to specified values passed as parameters.
	 * 
	 * @param addressID
	 * @param flatNumber
	 * @param houseName
	 * @param houseNumber
	 * @param street
	 * @param city
	 * @param county
	 * @param postcode
	 * @param country
	 */
	public Address (
			long addressID,
			String flatNumber,
			String houseName,
			String houseNumber,
			String street,
			String city,
			String county,
			String postcode,
			String country){
					this.addressID = addressID;
					this.flatNumber = flatNumber;
					this.houseName = houseName;
					this.houseNumber = houseNumber;
					this.street = street;
					this.city = city;
					this.county = county;
					this.postcode = postcode;
					this.country = country;
	}
	
	/**
	 * Getting 'addressID'
	 * @return long
	 */
	public long getAddressID() {
		return addressID;
	}
	
	/**
	 * Setting 'addressID'
	 * 
	 * @param addressID
	 */
	public void setAddressID(long addressID) {
		this.addressID = addressID;
	}
	
	/**
	 * Getting 'flatNumber'
	 * 
	 * @return String
	 */
	public String getFlatNumber() {
		return flatNumber;
	}
	
	/**
	 * Getting 'houseName'
	 * 
	 * @return String
	 */
	public String getHouseName() {
		return houseName;
	}
	
	/**
	 * Getting 'houseNumber'
	 * 
	 * @return String
	 */
	public String getHouseNumber() {
		return houseNumber;
	}
	
	/**
	 * Getting 'street'
	 * 
	 * @return String
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * Getting 'city'
	 * 
	 * @return String
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Getting 'county'
	 * 
	 * @return String
	 */
	public String getCounty() {
		return county;
	}
	
	/**
	 * Getting 'postcode'
	 * 
	 * @return String
	 */
	public String getPostcode() {
		return postcode;
	}
	
	/**
	 * Getting 'country'
	 * 
	 * @return String
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Setting 'flatNumber'
	 * 
	 * @param flatNumber
	 */
	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}
	
	/**
	 * Setting 'houseName'
	 * 
	 * @param houseName
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	
	/**
	 * Setting 'houseNumber'
	 * 
	 * @param houseNumber
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	/**
	 * Setting 'street'
	 * 
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * Setting 'city'
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Setting 'county'
	 * 
	 * @param county
	 */
	public void setCounty(String county) {
		this.county = county;
	}
	
	/**
	 * Setting 'postcode'
	 * 
	 * @param postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	/**
	 * Setting 'country'
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * This method returns an address fields split between multiple rows.
	 * 
	 * @return String
	 */
	public String toPost() {
		StringBuffer address = new StringBuffer();
		if(this.flatNumber != null && !flatNumber.isEmpty()) {
			address.append("Flat " + this.flatNumber + ",\n");
		}
		if(this.houseName != null && !houseName.isEmpty()) {
			address.append(this.houseName + ",\n");
		}
		if(this.houseNumber != null && !houseNumber.isEmpty()) {
			address.append(this.houseNumber + " " + this.street + ",\n");
		} else {
			address.append(this.street + ",\n");
		}
		address.append(this.city + ",\n");
		if (this.county != null && !county.isEmpty()) {
			address.append(this.county + ",\n");
		}
		address.append(this.postcode + ",\n");
		address.append(this.country);
		return address.toString();
	}
	
	/**
	 * This method overrides toString() method. This method returns a
	 * single row String with all fields that has been set.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuffer address = new StringBuffer();
		if(this.flatNumber != null && !flatNumber.isEmpty()) {
			address.append("Flat " + this.flatNumber + ", ");
		}
		if(this.houseName != null && !houseName.isEmpty()) {
			address.append(this.houseName + ", ");
		}
		if(this.houseNumber != null && !houseNumber.isEmpty()) {
			address.append(this.houseNumber + " " + this.street + ", ");
		} else {
			address.append(this.street + ", ");
		}
		address.append(this.city + ", ");
		if (this.county != null && !county.isEmpty()) {
			address.append(this.county + ", ");
		}
		address.append(this.postcode + ", ");
		address.append(this.country);
		return address.toString();
	}
	
	/**
	 * This method compares two addresses and returns 0 if both addresses
	 * are the same Physical Addresses with same post code, flatNumber,
	 * houseName and houseNumber.
	 * 
	 * @return int
	 */
	@Override
	public int compareTo (Address object) {
		Address address = object;
		int compare = this.postcode.compareToIgnoreCase(address.postcode);
		if ((compare == 0) && (this.flatNumber != null)) {
			compare = this.flatNumber.compareToIgnoreCase(address.flatNumber);
		} if ((compare == 0) && (this.houseNumber != null)){
			compare = this.houseName.compareToIgnoreCase(address.houseName);
		} if ((compare == 0) && (this.houseName != null)){
			compare = this.houseNumber.compareToIgnoreCase(address.houseNumber);
		}
		return (compare);
	}
}