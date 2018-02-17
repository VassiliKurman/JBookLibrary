package vkurman.jbooklibrary.demo.mock;

/**
 * <code>Enumerator</code> of English female names.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum FirstnameFemale {
	SOPHIA("Sophia"), EMMA("Emma"), OLIVIA("Olivia"), ISABELLA("Isabella"), AVA("Ava"),
	LILY("Lily"), ZOE("Zoe"), CHLOE("Chloe"), MIA("Mia"), MADISON("Madison"),
	EMILY("Emily"), ELLA("Ella"), MADELYN("Madelyn"), ABIGAIL("Abigail"), AUBREY("Aubrey"),
	ADDISON("Addison"), AVERY("Avery"), LAYLA("Layla"), HAILEY("Hailey"), AMELIA("Amelia"),
	HANNAH("Hannah"), CHARLOTTE("Charlotte"), KAITLYN("Kaitlyn"), HARPER("Harper"),
	KAYLEE("Kaylee"), SOPHIE("Sophie"), MACKENZIE("Mackenzie"), PEYTON("Peyton"),
	RILEY("Riley"), GRACE("Grace"), BROOKLYN("Brooklyn"), SARAH("Sarah"), AALIYAH("Aaliyah"),
	ANNA("Anna"), ARIANNA("Arianna"), ELLIE("Ellie"), NATALIE("Natalie"), LILLIAN("Lillian"),
	EVELYN("Evelyn"), ELIZABETH("Elizabeth"), LYLA("Lyla"), LUCY("Lucy"), CLAIRE("Claire"),
	MAKAYLA("Makayla"), KYLIE("Kylie"), AUDREY("Audrey"), MAYA("Maya"), LEAH("Leah"),
	GABRIELLA("Gabriella"), ANNABELLE("Annabelle"), SAVANNAH("Savannah"), NORA("Nora"),
	REAGAN("Reagan"), SCARLETT("Scarlett"), SAMANTHA("Samantha"), ALYSSA("Alyssa"),
	ALLISON("Allison"), ELENA("Elena"), STELLA("Stella"), ALEXIS("Alexis"), VICTORIA("Victoria"),
	ARIA("Aria"), MOLLY("Molly"), MARIA("Maria"), BAILEY("Bailey"), SYDNEY("Sydney"),
	BELLA("Bella"), MILA("Mila"), TAYLOR("Taylor"), KAYLA("Kayla"), EVA("Eva"),
	JASMINE("Jasmine"), GIANNA("Gianna"), ALEXANDRA("Alexandra"), JULIA("Julia"),
	ELIANA("Eliana"), KENNEDY("Kennedy"), BRIANNA("Brianna"), RUBY("Ruby"), LAUREN("Lauren"),
	ALICE("Alice"), VIOLET("Violet"), KENDALL("Kendall"), MORGAN("Morgan"), CAROLINE("Caroline"),
	PIPER("Piper"), BROOKE("Brooke"), ELISE("Elise"), ALEXA("Alexa"), SIENNA("Sienna"),
	REESE("Reese"), CLARA("Clara"), PAIGE("Paige"), KATE("Kate"), NEVAEH("Nevaeh"),
	SADIE("Sadie"), QUINN("Quinn"), ISLA("Isla"), ELEANOR("Eleanor");
	
	private String nameAsString;
	
	private FirstnameFemale(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}