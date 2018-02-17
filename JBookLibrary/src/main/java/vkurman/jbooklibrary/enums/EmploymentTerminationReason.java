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
 * Enumeration class for Employment Termination Reasons.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum EmploymentTerminationReason {
	RESIGNATION("Resignation"), ATTENDANCE("Attendance"), ILLNESS("Illness"), DEATH("Death"),
	MISCONDUCT("Misconduct"), POOR_PERFORMANCE("Poor Performance"), REDUNDANCY("Redundancy"),
	INSUBORINATION("Insubordination"), RETIREMENT("Retirement"), OTHER("Other");
	
	private String nameAsString;
	
	private EmploymentTerminationReason(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
	
	public static EmploymentTerminationReason fromString(String text){
		if(text != null){
			for(EmploymentTerminationReason e : EmploymentTerminationReason.values()){
				if(text.equalsIgnoreCase(e.toString())){
					return e;
				}
			} 
			return EmploymentTerminationReason.OTHER;
		} else {
			return EmploymentTerminationReason.OTHER;
		}
	}
}