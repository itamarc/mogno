package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** LI tag.<br>
 * List item.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 * @see Ol
 * @see Ul
 */
public class Li extends ListElement {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18n html attributes inherited from class com.oktiva.mogno.html.ListElement
	//events html attributes inherited from class com.oktiva.mogno.html.ListElement
	//html html attributes inherited from class com.oktiva.mogno.html.ListElement
	public Li() {
		super();
		tag = "LI";
	}
}
