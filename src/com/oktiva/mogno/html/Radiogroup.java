package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/** A virtual component to hold radio inputs.
 * @version $Id$
 * @see Form
 * @see Input
 */
public class Radiogroup extends Visual {
	/** Event dispatched when the content of the input changes. */
	public String evOnChange;
	public String getEvOnChange() {
		return evOnChange;
	}
	public void setEvOnChange(String evOnChange) {
		this.evOnChange = evOnChange;
	}

	/** The value of the radiogroup */
	public String value;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public void receiveRequest(HttpServletRequest request) {
		String oldValue = value;
		value = request.getParameter(name);
		if ((oldValue == null && value != null) || (oldValue != null && !oldValue.equals(value))) {
			queue("evOnChange");
		}
	}
}
