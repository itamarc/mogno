package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** VAR (variable name) tag.<br>
 * The VAR tag is used to render the enclosed text in a special font to 
 * denote that it is a variable or another special type of text.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 */
public class Var extends PhraseElement {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18n html attributes inherited from class com.oktiva.mogno.html.PhraseElement
	//events html attributes inherited from class com.oktiva.mogno.html.PhraseElement
	//html html attributes inherited from class com.oktiva.mogno.html.PhraseElement
	public Var() {
		super();
		type = "VAR";
	}
}
