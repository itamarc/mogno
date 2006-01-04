/*
 * InputView.java
 *
 * Created on June 02 2003 21:16
 * vim:encoding=utf-8:fileencoding=utf-8
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.html.Input;
import com.oktiva.mogno.html.Span;

/**
 * @version $Id$
 */
public class InputView extends Input implements ComponentView {
	/** If true, show this component as a SPAN, with an hidden.  Else, as a INPUT.<br>
	 * Defaults to false.
	 */
	public boolean viewOnly = false;
	
	/** Creates a new instance of InputView */
	public InputView() {
		super();
	}
	
	public String show()
	throws Exception {
		StringBuffer ret = new StringBuffer();
		if (viewOnly) {
			if ("radio".equalsIgnoreCase(getType()) || "checkbox".equalsIgnoreCase(getType())) {
				String oldDisabled = getDisabled();
				setDisabled("true");
				ret.append(super.show());
				setDisabled(oldDisabled);
			} else {
				String spanContent=getValue();
				if(spanContent!=null) {
					Span myself = new Span();
					myself.setContent(getValue());
					ret.append(myself.show());
				}
				String oldType = getType();
				setType("hidden");
				ret.append(super.show());
				setType(oldType);
			}
		} else {
			ret.append(super.show());
		}
		return ret.toString();
	}
	
	/** Getter for property viewOnly.
	 * @return Value of property viewOnly.
	 */
	public boolean isViewOnly() {
		return viewOnly;
	}
	
	/** Setter for property viewOnly.
	 * @param viewOnly New value of property viewOnly.
	 */
	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}
}
