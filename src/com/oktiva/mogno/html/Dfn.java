package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** DFN tag.<br>
 * The DFN tag is used to render text in italics to imply the affected text is
 * the first and difining instance of a term in the document.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 */
public class Dfn extends PhraseElement {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18n html attributes inherited from class com.oktiva.mogno.html.PhraseElement
	//events html attributes inherited from class com.oktiva.mogno.html.PhraseElement
	//html html attributes inherited from class com.oktiva.mogno.html.PhraseElement
	public Dfn() {
		super();
		type = "DFN";
	}
}
