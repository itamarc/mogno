package com.oktiva.mogno;

import com.oktiva.mogno.*;
import java.util.*;

/** This is the super class for the empty tags<br>
 * @version $Id$
 */
public class EmptyTag extends Visual {
	/** Something to be put after the tag.  Default to emtpy string.
	 * It is intended primarily to put a new line after some tags.
	 */
	protected String afterEnd = "";
	//core html attributes inherited from class com.oktiva.mogno.Visual
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(coreHtmlAttributes());
		return v;
	}
	
	/** Creates the html code for the empty tag with this attributes.
	 */
	public String show()
	throws Exception {
		return super.show()+"<"+tag+showHtmlAttributes()+">"+afterEnd;
	}
}
