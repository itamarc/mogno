package com.oktiva.mogno.additional;

import com.oktiva.mogno.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/** Includes another TopLevel inside.
 * <p>This component allows you to include another TopLevel component inside a
 * container (i.e.: a {@link com.oktiva.mogno.html.Page Page}).</p>
 * <p>The included component is created and destroyed in the show method.
 * If the included component has any action, it must be declared at the 
 * {@link com.oktiva.mogno.Application Application} XML.
 * @see Included
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt; and others.
 */
public class Include extends Visual {
	static Logger logger = Logger.getLogger(Include.class.getName());
	/** The name of the included TopLevel. */
	public String topLevelName;
	public String getTopLevelName() {
		return topLevelName;
	}
	public void setTopLevelName(String topLevelName) {
		this.topLevelName = topLevelName;
	}

	/** The classname of the component. */
	public String className;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}


	public String show()
	throws Exception {
		TopLevel tl = getApplication().getTopLevel(topLevelName);
		return super.show()+tl.show();
	}
}
