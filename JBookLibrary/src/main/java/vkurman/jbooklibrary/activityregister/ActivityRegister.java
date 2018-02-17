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

package vkurman.jbooklibrary.activityregister;

import java.util.ArrayList;
import java.util.List;

/**
 * This class registers all activity observers and passes messages
 * to them.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ActivityRegister {
	
	private static Activity activity = null;
	private static List<RegisterObserver> observers = new ArrayList<RegisterObserver>();
	
	/**
	 * Adding new <code>Activity</code> to the list.
	 * 
	 * @param originator
	 * @param message
	 */
	public static void newActivity(Object originator, String message){
		activity = new Activity(originator, message);
		
		notifyObservers(activity);
	}
	
	/**
	 * Returns last Activity from the list.
	 * 
	 * @return Activity
	 */
	public static Activity getActivity(){
		return activity;
	}
	
	/**
	 * Registers activity observer.
	 * 
	 * @param observer
	 */
	public static void registerObserver(RegisterObserver observer){
		if(observer != null){
			if(!observers.contains(observer)){
				observers.add(observer);
			}
		}
	}
	
	/**
	 * Unregisters activity observer.
	 * 
	 * @param observer
	 */
	public static void unregisterObserver(RegisterObserver observer){
		if(!observers.contains(observer)){
			observers.remove(observer);
		}
	}
	
	/**
	 * Notifying observers about new activity.
	 * 
	 * @param activity
	 */
	public static void notifyObservers(Activity activity){
		for(RegisterObserver observer: observers){
			observer.displayActivity(
					activity.getMessage());
		}
	}
}