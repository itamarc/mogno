package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** BASE tag (defined by W3C)<br>
 * @version $Id$
 */
public class Base extends EmptyTag {
	//htmlAttributes
	/** HTML attribute */
	public String href;
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}


	public Base() {
		super();
		tag = "BASE";
		afterEnd = "\n";
	}
	
	/**
	 * @return a vector with this class only HTML attribute: <i>href</i>
	 */
	public Vector htmlAttributes() {
		Vector v = new Vector();
		v.add("href");
		return v;
	}
}
