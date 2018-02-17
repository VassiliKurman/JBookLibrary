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

package vkurman.jbooklibrary.demo.mock;

/**
 * <code>Enumerator</code> of Latin capital letters.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum CharacterCapital {
	A("A"), B("B"), C("C"), D("D"), E("E"), F("F"), G("G"),
	H("H"), I("I"), J("J"), K("K"), L("L"), M("M"), N("N"),
	O("O"), P("P"), Q("Q"), R("R"), S("S"), T("T"), U("U"),
	V("V"), W("W"), X("X"), Y("Y"), Z("Z");
	
	private String nameAsString;
	
	private CharacterCapital(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}