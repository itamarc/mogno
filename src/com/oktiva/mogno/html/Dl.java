package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** DL tag.<br>
 * Definition list.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 * @see Dt
 * @see Dd
 */
public class Dl extends ListElement {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18n html attributes inherited from class com.oktiva.mogno.html.ListElement
	//events html attributes inherited from class com.oktiva.mogno.html.ListElement
	//html html attributes inherited from class com.oktiva.mogno.html.ListElement
	public Dl() {
		super();
		tag = "DL";
	}
}
