/*
 * DerivedPage.java
 *
 * Created on quarta, 19 de fevereiro de 2003 09:14
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.Component;
import com.oktiva.mogno.InitializeException;
import com.oktiva.mogno.TopLevel;
import com.oktiva.mogno.Visual;
import com.oktiva.mogno.html.Page;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** A Page derived from another.  It adopt the child components from a
 * base Page, abandon others, and have his own childs.
 * @version $Id$
 */
public class DerivedPage extends TopLevel {
	/** The name of the Page this one derives from.
	 */
	public String basePage;
	public String getBasePage() {
		return basePage;
	}
	public void setBasePage(String basePage) {
		this.basePage = basePage;
	}

	/** Show.
	 */
	public String show()
	throws Exception {
		if (getBasePage() != null && !"".equals(getBasePage()) && owner == null) {
			TopLevel t = application.getTopLevel(getBasePage());
			t.registerChild(this);
			try {
				String ret = t.show();
				return ret;
			} finally {
				t.freeChild(getName());
			}
		} else {
			Component oldOwner = owner;
			owner = null;
			String ret = super.show();
			owner = oldOwner;
			return ret;
		}
	}
}
