/*
 * FormView.java
 *
 * Created on June 02 2003 21:16
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.Application;
import com.oktiva.mogno.Component;
import com.oktiva.mogno.additional.InputView;
import com.oktiva.mogno.html.Form;
import com.oktiva.mogno.html.Select;
import java.util.Enumeration;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * @version $Id$
 */
public class FormView extends Form implements ComponentView {
	static Logger logger = Logger.getLogger("com.oktiva.mogno.additional");
	/** If true, show this component as a SPAN, with an hidden.  Else, as a INPUT.<br>
	 * Defaults to false.
	 */
	public boolean viewOnly = false;

	/** Creates a new instance of FormView */
	public FormView() {
		super();
	}

	/** Getter for property viewOnly.
	 * @return Value of property viewOnly.
	 */
	public boolean isViewOnly() {
		return viewOnly;
	}
	
	/** Setter for property viewOnly. Thiss will set this form, and <b>all</b> its InputViews to viewOnly.
	 * It will also issue a <b>setDisabled(viewOnly)</b> to <b>all</b< Selects in the form.
	 * @param viewOnly New value of property viewOnly.
	 */
	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
		Component listener = this;
		if (owner != null) {
			listener = owner;
		}
		Enumeration chlds=listener.listChilds();
		while(chlds.hasMoreElements()) {
			String childName = (String) chlds.nextElement();
			Component c = listener.getChild(childName);
			if(c instanceof ComponentView && c.descendentOf(name)) {
				ComponentView cv = (ComponentView)c;
				cv.setViewOnly(viewOnly);
			} else if(c instanceof Select && c.descendentOf(name)) {
				Select s=(Select)c;
				s.setDisabled(viewOnly?"true":null);
			}
		}
	}
}
