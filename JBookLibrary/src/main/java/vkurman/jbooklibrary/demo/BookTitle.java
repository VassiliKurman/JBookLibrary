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

package vkurman.jbooklibrary.demo;

/**
 * Enumeration for <code>BookTitle</code>'s.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum BookTitle {
	
	BLUE_SHORE("Blue Shores"), THE_SEVENTH_FIRE("The Seventh Fire"),
	SOUL_OF_SWORD("Soul of Sword"), THE_SPARKS_WINTER("The Sparks's Winter"),
	THE_FLOWERS_OF_THE_BRIDGE("The Flowers of the Bridge"),
	SPIRIT_IN_TH_YEAR("Spirit in the Year"), LUSCIOUS_SOARING("Luscious Soaring"),
	THE_BLOODY_GIFT("The Bloody Gift"), EDGE_OF_MEN("Edge of Men"),
	THE_HEARTS_SERPENT("The Heart's Serpent"),
	THE_SLAVES_OF_THE_PROPHECY("The Slaves of the Prophecy"),
	SERPENTS_IN_THE_GIFT("Serpents in the Gift"), THE_SOCIETY_OF_PHASE("The Society of Phase"),
	BRAVE_TROLL("Brave Troll"), SUPER_FOUR("Super Four"), DEATH_UNIVERSE("Death Universe"),
	TWO_TOWERS_OF_THE_APOCALYPSE("Two Towers of the Apocalypse"),
	DISPLACED_REVENGE("Displaced Revenge"), NIGHT_TRIAL("Night Trial"), GIGA_SHIP("Giga Ship"),
	CHANGED_BURST("Changed Burst"), DISCOVER_BLADE("Discover Blade"),
	TRANSFORMING_SKY("Transforming Sky"), THE_HAUNTED_TRIAL("The Haunted Trial"),
	COLD_SCENT("Cold Scent"), THE_WHICH_DREAMS("The Which Dreams"),
	FLAMES_OF_EDGE("Flames of Edge"), THE_MISTYS_KISS("The Misty's Kiss"),
	THE_BOY_OF_THE_WILLOW("The Boy of the Willow"), FLAME_IN_THE_TIME("Flame in the Time"),
	RED_NOBODY("Red Nobody"), THE_ABSENT_THOUGHTS("The Absent Thoughts"),
	HEALER_OF_SNOW("Healer of Snow"), THE_RAINBOWS_DOORS("The Rainbow's Doors"),
	THE_SHADOW_OF_THE_WINDOWS("The Shadow of the Windows"),
	SHADOW_IN_THE_PRINCE("Shadow in the Prince"), DYING_SECRET("Dying Secret"),
	THE_BROKEN_TWINS("The Broken Twins"), MEMORY_OF_SPIRITS("Memory of Spirits"),
	THE_MALES_TRAINER("The Male's Trainer"), PETALS_IN_THE_SNAKE("Petals in the Snake"),
	THE_NOTHING_OF_THE_SERVANT("The Nothing of the Servant");
		
		private String nameAsString;
		
		private BookTitle(String nameAsString)
		{
			this.nameAsString = nameAsString;
		}
		
		@Override
		public String toString()
		{
			return this.nameAsString;
		}
}