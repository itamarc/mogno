package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** TT (typewriter text - monospaced) tag.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 */
public class Tt extends PhraseElement {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18n html attributes inherited from class com.oktiva.mogno.html.PhraseElement
	//events html attributes inherited from class com.oktiva.mogno.html.PhraseElement
	//html html attributes inherited from class com.oktiva.mogno.html.PhraseElement
	public Tt() {
		super();
		type = "TT";
	}
}
