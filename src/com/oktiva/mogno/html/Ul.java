package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** UL tag.<br>
 * Unordered list.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 * @see Li
 * @see Ol
 */
public class Ul extends ListElement {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18n html attributes inherited from class com.oktiva.mogno.html.ListElement
	//events html attributes inherited from class com.oktiva.mogno.html.ListElement
	//html html attributes inherited from class com.oktiva.mogno.html.ListElement
	public Ul() {
		super();
		tag = "UL";
	}
}
