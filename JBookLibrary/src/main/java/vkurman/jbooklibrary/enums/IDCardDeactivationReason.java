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
 * Enumeration class for IDCard deactivation reasons.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum IDCardDeactivationReason {
	LOST("Lost"), EXPIRED("Expired"), OTHER("Other");
	
	private String nameAsString;
	
	private IDCardDeactivationReason(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
	public static IDCardDeactivationReason fromString(String text){
		if(text != null){
			for(IDCardDeactivationReason e : IDCardDeactivationReason.values()){
				if(text.equalsIgnoreCase(e.toString())){
					return e;
				}
			} 
			return IDCardDeactivationReason.OTHER;
		} else {
			return IDCardDeactivationReason.OTHER;
		}
	}
}