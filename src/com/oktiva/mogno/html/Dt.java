package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** DT tag.<br>
 * Definition term.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 * @see Dl
 * @see Dd
 */
public class Dt extends ListElement {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18n html attributes inherited from class com.oktiva.mogno.html.ListElement
	//events html attributes inherited from class com.oktiva.mogno.html.ListElement
	//html html attributes inherited from class com.oktiva.mogno.html.ListElement
	public Dt() {
		super();
		tag = "DT";
	}
}
