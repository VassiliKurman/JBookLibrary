package vkurman.jbooklibrary.demo.mock;

/**
 * <code>Enumerator</code> of Latin small letters.
 * 
 * <p>Date created: 2014.07.14
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public enum CharacterSmall {
	a("a"), b("b"), c("c"), d("d"), e("e"), f("f"), g("g"),
	h("h"), i("i"), j("j"), k("k"), l("l"), m("m"), n("n"),
	o("o"), p("p"), q("q"), r("r"), s("s"), t("t"), u("u"),
	v("v"), w("w"), x("x"), y("y"), z("z");
	
	private String nameAsString;
	
	private CharacterSmall(String nameAsString)
	{
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString()
	{
		return this.nameAsString;
	}
}