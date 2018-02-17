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