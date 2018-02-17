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
 * <code>Enumerator</code> of English surnames.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum Surname {
	SMITH("Smith"), JONES("Jones"), TAYLOR("Taylor"), WILLIAMS("Williams"),
	BROWN("Brown"), DAVIES("Davies"), EVANS("Evans"), WILSON("Wilson"),
	THOMAS("Thomas"), ROBERTS("Roberts"), JOHNSON("Johnson"), LEWIS("Lewis"),
	WALKER("Walker"), ROBINSON("Robinson"), WOOD("Wood"), THOMPSON("Thompson"),
	WHITE("White"), WATSON("Watson"), JACKSON("Jackson"), WRIGHT("Wright"),
	GREEN("Green"), HARRIS("Harris"), COOPER("Cooper"), KING("King"), LEE("Lee"),
	MARTIN("Martin"), CLARKE("Clarke"), JAMES("James"), MORGAN("Morgan"), HUGHES("Hughes"),
	EDWARDS("Edwards"), HILL("Hill"), MOORE("Moore"), CLARK("Clark"), HARRISON("Harrison"),
	SCOTT("Scott"), YOUNG("Young"), MORRIS("Morris"), HALL("Hall"), WARD("Ward"),
	TURNER("Turner"), CARTER("Carter"), PHILLIPS("Phillips"), MITCHEL("Mitchell"),
	PATEL("Patel"), ADAMS("Adams"), CAMPBELL("Campbell"), ANDERSON("Anderson"), ALLEN("Allen"),
	COOK("Cook"), BAILEY("Bailey"), PARKER("Parker"), MILLER("Miller"), DAVIS("Davis"),
	MURPHY("Murphy"), PRICE("Price"), BELL("Bell"), BAKER("Baker"), GRIFFITHS("Griffiths"),
	KELLY("Kelly"), SIMPSON("Simpson"), MARSHALL("Marshall"), COLLINS("Collins"),
	BENNETT("Bennett"), COX("Cox"), RICHARDSON("Richardson"), FOX("Fox"), GRAY("Gray"),
	ROSE("Rose"), CHAPMAN("Chapman"), HUNT("Hunt"), ROBERTSON("Robertson"), SHAW("Shaw"),
	REYNOLDS("Reynolds"), LLOYD("Lloyd"), ELLIS("Ellis"), RICHARDS("Richards"),
	RUSSELL("Russell"), WILKINSON("Wilkinson"), KHAN("Khan"), GRAHAM("Graham"),
	STEWART("Stewart"), REID("Reid"), MURRAY("Murray"), POWELL("Powell"), PALMER("Palmer"),
	HOLMES("Holmes"), ROGERS("Rogers"), STEVENS("Stevens"), WALSH("Walsh"), HUNTER("Hunter"),
	THOMSON("Thomson"), MATTHEWS("Matthews"), ROSS("Ross"), OWEN("Owen"), MASON("Mason"),
	KNIGHT("Knight"), KENNEDY("Kennedy"), BUTLER("Butler"), SAUNDERS("Saunders"), COLE("Cole"),
	PEARCE("Pearce"), DEAN("Dean"), FOSTER("Foster"), HARVEY("Harvey"), HUDSON("Hudson"),
	GIBSON("Gibson"), MILLS("Mills"), BERRY("Berry"), BARNES("Barnes"), PEARSON("Pearson"),
	KAUR("Kaur"), BOOTH("Booth"), DIXON("Dixon"), GRANT("Grant"), GORDON("Gordon"), LANE("Lane"),
	HARPER("Harper"), ALI("Ali"), HART("Hart"), MCDONALD("Mcdonald"), BROOKS("Brooks"),
	RYAN("Ryan"), CARR("Carr"), MACDONALD("Macdonald"), HAMILTON("Hamilton"),
	JOHNSTON("Johnston"), WEST("West"), GILL("Gill"), DAWSON("Dawson"), ARMSTRONG("Armstrong"),
	GARDNER("Gardner"), STONE("Stone"), ANDREWS("Andrews"), WILLIAMSON("Williamson"),
	BARKER("Barker"), GEORGE("George"), FISHER("Fisher"), CUNNINGHAM("Cunningham"),
	WATTS("Watts"), WEBB("Webb"), LAWRENCE("Lawrence"), BRADLEY("Bradley"), JENKINS("Jenkins"),
	WELLS("Wells"), CHAMBERS("Chambers"), SPENCER("Spencer"), POOLE("Poole"),
	ATKINSON("Atkinson"), LAWSON("Lawson"), DAY("Day"), WOODS("Woods"), REES("Rees8"),
	FRACER("Fraser"), BLACK("Black"), FLETCHER("Fletcher"), HUSSAIN("Hussain"), WILLIS("Willis"),
	MARSH("Marsh"), AHMED("Ahmed"), DOYLE("Doyle"), LOWE("Lowe"), BURNS("Burns"),
	HOPKINS("Hopkins"), NICHOLSON("Nicholson"), PARRY("Parry"), NEWMAN("Newman"),
	JORDAN("Jordan"), HENDERSON("Henderson"), HOWARD("Howard"), BARRETT("Barrett"),
	BURTON("Burton"), RILEY("Riley"), PORTER("Porter"), BYRNE("Byrne"), HOUGHTON("Houghton"),
	JOHN("John"), PERRY("Perry"), BAXTER("Baxter"), BALL("Ball"), MCCARTHY("Mccarthy"),
	ELLIOTT("Elliott"), BURKE("Burke"), GALLAGHER("Gallagher"), DUNCAN("Duncan"), COOKE("Cooke"),
	AUSTIN("Austin"), READ("Read"), WALLACE("Wallace"), HAWKINS("Hawkins"), HAYES("Hayes"),
	FRANCIS("Francis"), SUTTON("Sutton"), DAVIDSON("Davidson"), SHARP("Sharp"),
	HOLLAND("Holland"), MOSS("Moss"), MAY("May"), BATES("Bates"), MORRISON("Morrison"),
	BOB("Bob"), OLIVER("Oliver"), KEMP("Kemp"), PAGE("Page"), ARNOLD("Arnold"), SHAH("Shah"),
	STEVENSON("Stevenson"), FORD("Ford"), POTTER("Potter"), FLYNN("Flynn"), WARREN("Warren"),
	KENT("Kent"), ALEXANDER("Alexander"), FIELD("Field"), FREEMAN("Freeman"), BEGUM("Begum"),
	RHODES("Rhodes"), ONEILL("O'Neill"), MIDDLETON("Middleton"), PAYNE("Payne"),
	STEPHENSON("Stephenson"), PRITCHARD("Pritchard"), GREGORY("Gregory"), BOND("Bond"),
	WEBSTER("Webster"), DUNN("Dunn"), DONNELLY("Donnelly"), LUCAS("Lucas"), LONG("Long"),
	JARVIS("Jarvis"), CROSS("Cross"), STEPHENS("Stephens"), REED("Reed"), COLEMAN("Coleman"),
	NICHOLLS("Nicholls"), BULL("Bull"), BARTLETT("Bartlett"), OBRIEN("O'Brien"),
	CURTIS("Curtis"), BIRD("Bird"), PATTERSON("Patterson"), TUCKER("Tucker"), BRYANT("Bryant"),
	LYNCH("Lynch"), MACKENZIE("Mackenzie"), FERGUSON("Ferguson"), CAMERON("Cameron"),
	LOPEZ("Lopez"), HAYNES("Haynes"), BOLTON("Bolton"), HARDY("Hardy"), HEATH("Heath"),
	DAVEY("Davey"), RICE("Rice"), JACOBS("Jacobs"), PARSONS("Parsons"), ASHTON("Ashton"),
	ROBSON("Robson"), FRENCH("French"), FARRELL("Farrell"), WALTON("Walton"), GILBERT("Gilbert"),
	MCINTYRE("Mcintyre"), NEWTON("Newton"), NORMAN("Norman"), HIGGINS("Higgins"),
	HODGSON("Hodgson"), SUTHERLAND("Sutherland"), KAY("Kay"), BISHOP("Bishop"),
	BURGESS("Burgess"), SIMMONS("Simmons"), HUTCHINSON("Hutchinson"), MORAN("Moran"),
	FROST("Frost"), SHARMA("Sharma"), SLATER("Slater"), GREENWOOD("Greenwood"), KIRK("Kirk"),
	FERNANDEZ("Fernandez"), GARCIA("Garcia"), ATKINS("Atkins"), DANIEL("Daniel"),
	BEATTIE("Beattie"), MAXWELL("Maxwell"), TODD("Todd"), CHARLES("Charles"), PAUL("Paul"),
	CRAWFORD("Crawford"), OCONNOR("O'Connor"), PARK("Park"), FORREST("Forrest"), LOVE("Love"),
	ROWLAND("Rowland"), CONNOLLY("Connolly"), SHEPPARD("Sheppard"), HARDING("Harding"),
	BANKS("Banks"), ROWE("Rowe"), HUMPHREYS("Humphreys"), GARNER("Garner"), GLOVER("Glover"),
	SANDERSON("Sanderson"), JEFFERY("Jeffery"), GOODWIN("Goodwin"), HEWITT("Hewitt"),
	DANIELS("Daniels"), DAVID("David"), SULLIVAN("Sullivan"), YATES("Yates"), HOWE("Howe"),
	MACKAY("Mackay"), HAMMOND("Hammond"), CARPENTER("Carpenter"), MILES("Miles"),
	BRADY("Brady"), PRESTON("Preston"), MCLEOD("Mcleod"), LAMBERT("Lambert"),
	KNOWLES("Knowles"), LEIGH("Leigh"), HOPE("Hope"), ATHERTON("Atherton"), BARTON("Barton"),
	FINCH("Finch"), BLAKE("Blake"), FULLER("Fuller"), HENRY("Henry"), COATES("Coates"),
	HOBBS("Hobbs"), MORTON("Morton"), HOWELLS("Howells"), DAVISON("Davison"), OWENS("Owens"),
	GOUGH("Gough"), DENNIS("Dennis"), WILKINS("Wilkins"), DUFFY("Duffy"), WOODWARD("Woodward"),
	GRIFFIN("Griffin"), BLOGGS("Bloggs"), PATERSON("Paterson"), CHARLTON("Charlton"),
	VINCENT("Vincent"), WALL("Wall"), BOWEN("Bowen"), BROWNE("Browne"), DONALDSON("Donaldson"),
	RODGERS("Rodgers"), CHRISTIE("Christie"), GIBBONS("Gibbons"), WHEELER("Wheeler"),
	SMART("Smart"), STEELE("Steele"), BENTLEY("Bentley"), QUINN("Quinn"), HARTLEY("Hartley"),
	BARNETT("Barnett"), RANDALL("Randall"), SWEENEY("Sweeney"), FOWLER("Fowler"),
	ALLAN("Allan"), BRENNAN("Brennan"), DOUGLAS("Douglas"), HOLT("Holt"), HOWELL("Howell"),
	BOWDEN("Bowden"), CARTWRIGHT("Cartwright"), BAIRD("Baird"), WATKINS("Watkins"),
	KERR("Kerr"), DICKSON("Dickson"), BENSON("Benson"), GODDARD("Goddard"), MILLAR("Millar"),
	BROADHURST("Broadhurst"), DOHERTY("Doherty"), HOLDEN("Holden"), SINGH("Singh"),
	TAIT("Tait"), REILLY("Reilly"), THORNE("Thorne"), WYATT("Wyatt"), POWER("Power"),
	LEACH("Leach"), LORD("Lord"), NELSON("Nelson"), HILTON("Hilton"), ADAM("Adam"),
	MCGREGOR("Mcgregor"), MCLEAN("Mclean"), WALTERS("Walters"), JENNINGS("Jennings"),
	LINDSAY("Lindsay"), NASH("Nash"), HANCOCK("Hancock"), HOOPER("Hooper"), CARROLL("Carroll"),
	SILVA("Silva"), CHADWICK("Chadwick"), ABBOTT("Abbott"), STUART("Stuart"), MELLOR("Mellor"),
	SEYMOUR("Seymour"), BOYD("Boyd"), PERKINS("Perkins"), DALE("Dale"), MANN("Mann"),
	MAC("Mac"), HAINES("Haines"), WHELAN("Whelan"), PETERS("Peters"), SAVAGE("Savage"),
	BARLOW("Barlow"), SANDERS("Sanders"), MOHAMED("Mohamed"), KENNY("Kenny"),
	BALDWIN("Baldwin"), MCGRATH("Mcgrath"), THORNTON("Thornton"), JOYCE("Joyce"),
	BLAIR("Blair"), WHITEHOUSE("Whitehouse"), WEAVER("Weaver"), SHEPHERD("Shepherd"),
	WHITEHEAD("Whitehead"), LITTLE("Little"), CULLEN("Cullen"), BURROWS("Burrows"),
	MCFARLANE("Mcfarlane"), SINCLAIR("Sinclair"), SWIFT("Swift"), FLEMING("Fleming"),
	BUCKLEY("Buckley"), WELCH("Welch"), VAUGHAN("Vaughan"), BRADSHAW("Bradshaw"),
	NAYLOR("Naylor"), SUMMERS("Summers"), BRIGGS("Briggs"), SCHOFIELD("Schofield"),
	OSBORNE("Osborne"), COLES("Coles"), AKHTAR("Akhtar"), CASSIDY("Cassidy"), ROSSI("Rossi"),
	GILES("Giles"), WHITTAKER("Whittaker"), HERBERT("Herbert"), HICKS("Hicks"),
	BOURNE("Bourne"), FAULKNER("Faulkner"), WESTON("Weston"), BRAY("Bray"),
	HUMPHREY("Humphrey"), SPENCE("Spence"), PARTRIDGE("Partridge"), JOHNS("Johns"),
	MORLEY("Morley"), WELSH("Welsh"), KAYE("Kaye"), BUSH("Bush"), ROONEY("Rooney"),
	CRAIG("Craig"), FITZGERALD("Fitzgerald"), GARDINER("Gardiner"), WHITTLE("Whittle"),
	LAING("Laing"), POLLARD("Pollard"), MCCANN("Mccann"), WILKES("Wilkes"), DREW("Drew"),
	ARMITAGE("Armitage"), BRIGHT("Bright"), HILLS("Hills"), ENGLISH("English"),
	DEVLIN("Devlin"), WINTER("Winter"), HOWARTH("Howarth"), HORNE("Horne"),
	SINGLETON("Singleton"), LOVELL("Lovell"), BEST("Best"), KAVANAGH("Kavanagh"),
	APPLETON("Appleton"), GIBBS("Gibbs"), RAWLINGS("Rawlings"), MCKENNA("Mckenna"),
	TURNBULL("Turnbull"), BERNARD("Bernard"), STANTON("Stanton"), KIRBY("Kirby"),
	WILLS("Wills"), CAREY("Carey"), SAWYER("Sawyer"), CROSSLEY("Crossley"), PIPER("Piper"),
	JOSEPH("Joseph"), FENTON("Fenton"), BRUCE("Bruce"), CONNOR("Connor"), REEVES("Reeves"),
	NORRIS("Norris"), NEEDHAM("Needham"), FIRTH("Firth"), CLARKSON("Clarkson"), DYER("Dyer"),
	BROOKES("Brookes"), TOWNSEND("Townsend"), CAIRNS("Cairns"), GUEST("Guest"),
	WALLIS("Wallis"), THORPE("Thorpe"), PARKINSON("Parkinson"), SYKES("Sykes"), LEES("Lees"),
	GALE("Gale"), BLACKBURN("Blackburn"), HOLLOWAY("Holloway"), HURST("Hurst"),
	MCINTOSH("Mcintosh"), SMYTH("Smyth"), CONWAY("Conway"), BOWMAN("Bowman"),
	MCLAUGHLIN("Mclaughlin"), SENIOR("Senior"), BASSETT("Bassett"), COLLIER("Collier"),
	CORBETT("Corbett"), HEATON("Heaton"), CURRY("Curry"), HUMPHRIES("Humphries"),
	COPELAND("Copeland"), FITZPATRICK("Fitzpatrick"), SLOAN("Sloan"), PARR("Parr"),
	ARCHER("Archer"), HOUGH("Hough"), IRELAND("Ireland"), TOMLINSON("Tomlinson"), EDGE("Edge"),
	BURT("Burt"), STOKES("Stokes"), COPE("Cope"), TANNER("Tanner"), CHANDLER("Chandler"),
	TYLER("Tyler"), GOODMAN("Goodman"), MCKAY("Mckay"), WICKENS("Wickens"), HORTON("Horton"),
	STACEY("Stacey"), SKINNER("Skinner"), SHIELDS("Shields"), REEVE("Reeve"),
	MCCALLUM("Mccallum"), NOBLE("Noble"), WHYTE("Whyte"), BARR("Barr"), MUIR("Muir"),
	KANE("Kane"), SIMONS("Simons"), CANNON("Cannon"), POPE("Pope"), BARRY("Barry"),
	MCKENZIE("Mckenzie"), ENGLAND("England"), DALTON("Dalton"), HANSON("Hanson"),
	MCCORMACK("Mccormack"), RAHMAN("Rahman"), PHILIP("Philip"), MARRIOTT("Marriott"),
	BARCLAY("Barclay"), SIMON("Simon"), LOGAN("Logan"), FARROW("Farrow"), HOOD("Hood"),
	FLOWER("Flower"), MATTHAMS("Matthams"), WARDLE("Wardle"), SIMS("Sims"),
	WOODCOCK("Woodcock"), CROWTHER("Crowther"), WATERS("Waters"), MOONEY("Mooney"),
	GOULD("Gould"), MALONE("Malone"), PEACOCK("Peacock"), GUNN("Gunn"), MCNEILL("Mcneill"),
	DAVIE("Davie"), DONALD("Donald"), HEALY("Healy"), MCGILL("Mcgill"), HUTTON("Hutton"),
	NEAL("Neal");
	
	private String nameAsString;
	
	private Surname(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}