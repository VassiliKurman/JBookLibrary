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

package vkurman.jbooklibrary.interfaces;

/**
 * FilterSubject interface handles registering, unregistering
 * and notifying all FilterObservers
 *  
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public interface FilterSubject {
	/**
	 * Registers observer.
	 * 
	 * @param observer
	 */
	public void register(FilterObserver observer);
	
	/**
	 * Unregisters observer.
	 * 
	 * @param observer
	 */
	public void unregister(FilterObserver observer);
	
	/**
	 * Notifies observer about change.
	 * 
	 * @param arg
	 */
	public void notifyObserver(Object arg);
}