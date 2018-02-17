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

package vkurman.jbooklibrary.enums;

/**
 * Enumeration class for Item Types.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum ItemType {

	BOOK("Book");
//	DICTIONARY("Dictionary"), CATALOGUE("Catalogue"), MAP("Map"),
//	BROCHURE("Brochure"), MAGAZINE("Magazine"),	JOURNAL("Journal"),
//	NEWSPAPER("Newspaper"), DVD("DVD"), VHS("VHS"), CD("CD"), BLUERAY_DISK("BlueRay Disk"),
//	TAPE("Tape"), OTHER("Other");
	
	private String nameAsString;
	
	private ItemType(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
//	/**
//	 * Returns <code>ItemType</code> enumeration from specified
//	 * <code>String</code>. If specified <code>String</code> can't
//	 * be converted to <code>ItemType</code>, than <code>OTHER</code>
//	 * is returned.
//	 * 
//	 * @param text
//	 * @return ItemType
//	 */
	/**
	 * Returns <code>BOOK</code> item type.
	 * 
	 * @param text
	 * @return ItemType
	 */
	public static ItemType fromString(String text){
		return BOOK;
//		if(text != null){
//			for(ItemType e : ItemType.values()){
//				if(text.equalsIgnoreCase(e.toString())){
//					return e;
//				}
//			} 
//			return ItemType.OTHER;
//		} else {
//			return ItemType.OTHER;
//		}
	}
}