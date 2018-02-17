package vkurman.jbooklibrary.demo.mock;

/**
 * <code>Enumerator</code> of UK Counties.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum County {
	
	ABERDEENSHIRE("Aberdeenshire"), ANGLESEY("Anglesey"), ANGUS("Angus"), ANTRIM("Antrim"),
	ARGYLL("Argyll"), ARMAGH("Armagh"), AYRSHIRE("Ayrshire"), BANFFSHIRE("Banffshire"),
	BEDFORDSHIRE("Bedfordshire"), BERKSHIRE("Berkshire"), BERWICKSHIRE("Berwickshire"),
	BRECKNOCKSHIRE("Brecknockshire"), BUCKINGHAMSHIRE("Buckinghamshire"), BUTESHIRE("Buteshire"),
	CAERNARFONSHIRE("Caernarfonshire"), CAITHNESS("Caithness"), CAMBRIDGESHIRE("Cambridgeshire"),
	CARDIGANSHIRE("Cardiganshire"), CARMARTHENSHIRE("Carmarthenshire"), CHESHIRE("Cheshire"),
	CLACKMANNANSHIRE("Clackmannanshire"), CORNWALL("Cornwall"), CROMARTYSHIRE("Cromartyshire"),
	CUMBERLAND("Cumberland"), DENBIGHSHIRE("Denbighshire"), DERBYSHIRE("Derbyshire"),
	DEVON("Devon"), DORSET("Dorset"), DOWN("Down"), DUMBARTONSHIRE("Dumbartonshire"),
	DUMFRIESSHIRE("Dumfriesshire"), DURHAM("Durham"), EAST_LOTHIAN("East Lothian"),
	ESSEX("Essex"), FERMANAGH("Fermanagh"), FIFE("Fife"), FLINTSHIRE("Flintshire"),
	GLAMORGAN("Glamorgan"), GLOUCESTERSHIRE("Gloucestershire"), HAMPSHIRE("Hampshire"),
	HEREFORDSHIRE("Herefordshire"), HERTFORDSHIRE("Hertfordshire"),
	HUNTINGDONSHIRE("Huntingdonshire"), INVERNESS_SHIRE("Inverness-shire"), KENT("Kent"),
	KINCARDINESHIRE("Kincardineshire"), KINROSS_SHIRE("Kinross-shire"),
	KIRKCUDBRIGHTSHIRE("Kirkcudbrightshire"), LANARKSHIRE("Lanarkshire"),
	LANCASHIRE("Lancashire"), LEICESTERSHIRE("Leicestershire"), LINCOLNSHIRE("Lincolnshire"),
	LONDONDERRY("Londonderry"), MERIONETHSHIRE("Merionethshire"), MIDDLESEX("Middlesex"),
	MIDLOTHIAN("Midlothian"), MONMOUTHSHIRE("Monmouthshire"), MONTGOMERYSHIRE("Montgomeryshire"),
	MORAYSHIRE("Morayshire"), NAIRNSHIRE("Nairnshire"), NORFOLK("Norfolk"),
	NORTHHAMPTONSHIRE("Northamptonshire"), NORTHUMBERLAND("Northumberland"),
	NOTTINGHAMSHIRE("Nottinghamshire"), ORKNEY("Orkney"), OXFORDSHIRE("Oxfordshire"),
	PEEBLESSHIRE("Peeblesshire"), PEMBROKESHIRE("Pembrokeshire"), PERTHSHIRE("Perthshire"),
	RADNORSHIRE("Radnorshire"), RENFREWSHIRE("Renfrewshire"), ROSS_SHIRE("Ross-shire"),
	ROXBURGHSHIRE("Roxburghshire"), RUTLAND("Rutland"), SELKIRKSHIRE("Selkirkshire"),
	SHETLAND("Shetland"), SHROPSHIRE("Shropshire"), SOMERSET("Somerset"),
	STAFFORDSHIRE("Staffordshire"), STIRLINGSHIRE("Stirlingshire"), SUFFOLK("Suffolk"),
	SURREY("Surrey"), SUSSEX("Sussex"), SUTHERLAND("Sutherland"), TYRONE("Tyrone"),
	WARWICKSHIRE("Warwickshire"), WEST_LOTHIAN("West Lothian"), WESTMORLAND("Westmorland"),
	WIGTOWNSHIRE("Wigtownshire"), WILTSHIRE("Wiltshire"), WORCHESTERSHIRE("Worcestershire"),
	YORKSHIRE("Yorkshire");
	
	private String nameAsString;
	
	private County(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}