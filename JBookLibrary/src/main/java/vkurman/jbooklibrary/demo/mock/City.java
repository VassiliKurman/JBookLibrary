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
 * <code>Enumerator</code> of UK cities.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum City {
	BATH("Bath"), BIRMINGHAM("Birmingham"), BRADFORD("Bradford"), BRIGHTON("Brighton"),
	HOVE("Hove"), BRISTOL("Bristol"), CAMBRIDGE("Cambridge"), CANTERBURY("Canterbury"),
	CARLISLE("Carlisle"), CHESTER("Chester"), CHICHESTER("Chichester"), LONDON("London"),
	COVENTRY("Coventry"), DERBY("Derby"), DURHAM("Durham"), ELY("Ely"), EXETER("Exeter"),
	GLOUCESTER("Gloucester"), HEREFORD("Hereford"), KINGSTON_UPON_HULL("Kingston upon Hull"),
	LANCASTER("Lancaster"), LEEDS("Leeds"), LEICESTER("Leicester"), LICHFIELD("Lichfield"),
	LINCOLN("Lincoln"), LIVERPOOL("Liverpool"), MANCHESTER("Manchester"), NORWICH("Norwich"),
	NEWCASTLE_UPON_TYNE("Newcastle upon Tyne"), NOTTINGHAM("Nottingham"), OXFORD("Oxford"),
	PETERBOROUGH("Peterborough"), PLYMOUTH("Plymouth"), PORTSMOUTH("Portsmouth"),
	PRESTON("Preston"), RIPON("Ripon"), SALFORD("Salford"), SALISBURY("Salisbury"),
	SHEFFIELD("Sheffield"), SOUTHAMPTON("Southampton"), ST_ALBANS("St Albans"), TRURO("Truro"),
	STOKE_ON_TRENT("Stoke-on-Trent"), SUNDERLAND("Sunderland"), WAKEFIELD("Wakefield"),
	WELLS("Wells"), WESTMINSTER("Westminster"), WINCHESTER("Winchester"), YORK("York"),
	WOLVERHAMPTON("Wolverhampton"), WORCESTER("Worcester");
	
	private String nameAsString;
	
	private City(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}