package vkurman.jbooklibrary.demo.mock;

/**
 * <code>Enumerator</code> of popular English house names.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum HouseName {
	
	ABERDEEN("Aberdeen"), AVALON("Avalon"), AVONLEAN("Avonlea"), AVONDALE("Avondale"),
	BALMORAL("Balmoral"), BAY_VIEW("Bay View"), BELLEVUE("Bellevue"), BELMONT("Belmont"),
	BLUEGUMS("Bluegums"), BONNIE_DOON("Bonnie Doon"), BRAESIDE("Braeside"),
	CHERRYWOOD("Cherrywood"), CLAREMONT("Claremont"), CLEARVIEW("Clearview"),
	FAIRVIEW("Fairview"), GLENFIELD("Glenfield"), GRANDVIEW("Grandview"), GRASMERE("Grasmere"),
	GREENDALE("Greendale"), HIGHVIEW("Highview"), HILLCREST("Hillcrest"), HILLSIDE("Hillside"),
	HILLTOP("Hilltop"), INVERNESS("Inverness"), IONA("Iona"), IRIS_COTTAGE("Iris Cottage"),
	KIA_ORA("Kia-Ora"), KILLARNEY("Killarney"), LAKESIDE("Lakeside"),
	LAVENDER_COTTAGE("Lavender Cottage"), LONE_PINE("Lone Pine"), MAYFIELD("Mayfield"),
	MEADOWLEA("Meadowlea"), MELROSE("Melrose"), MOUNT_PLEASANT("Mount Pleasant"),
	MOUNTAINVIEW("Mountainview"), NIOKA("Nioka"), OAKLANDS("Oaklands"), OAKVALE("Oakvale"),
	PINEWOODS("Pinewoods"), RIVER_COTTAGE("River Cottage"), RIVER_VIEW("River View"),
	RIVERBEND("Riverbend"), RIVERSIDE("Riverside"), RIVERVIEW("Riverview"),
	RIVERWOOD("Riverwood"), ROCKLEIGH("Rockleigh"), ROCKVIEW("Rockview"),
	ROSE_COTTAGE("Rose Cottage"), ROSEBANK("Rosebank"), ROSEDALE("Rosedale"),
	ROSEHILL("Rosehill"), ROSEWOOD("Rosewood"), SEASIDE_VIEW("Seaside View"),
	SOMERSET("Somerset"), SPRINGDALE("Springdale"), SPRINGFIELD("Springfield"),
	SPRINGVALE("Springvale"), SPRINGVALLEY("Springvalley"), STONEY_CREEK("Stoney Creek"),
	SUMMER_HILL("Summer Hill"), SUNNYSIDE("Sunnyside"), TARA("Tara"), THE_BARN("The Barn"),
	THE_BEACH_HOUSE("The Beach House"), THE_CHATEAU("The Chateau"),
	THE_COACH_HOUSE("The Coach House"), THE_COTTAGE("The Cottage"),
	THE_COUNTRY_HOUSE("The Country House"), THE_LAKE_HOUSE("The Lake House"),
	THE_LAURELS("The Laurels"), THE_LODGE("The Lodge"), THE_MANOR("The Manor"),
	THE_MEADOWS("The Meadows"), THE_OLD_BAKERY("The Old Bakery"),
	THE_OLD_CHURCH("The Old Church"), THE_OLD_PRIORY("The Old Priory"),
	THE_OLD_VICARAGE("The Old Vicarage"), THE_PINES("The Pines"), THE_POPLARS("The Poplars"),
	THE_WILLOWS("The Willows"), TREETOPS("Treetops"), TWIN_OAKS("Twin Oaks"),
	VALLEYVIEW("Valleyview"), WATERVIEW("Waterview"), WEOWNA("Weowna"), WINTERVALE("Wintervale"),
	WISTERIA_COTTAGE("Wisteria Cottage"), WITSEND("Witsend"), WOODLANDS("Woodlands");
	
	private String nameAsString;
	
	private HouseName(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}