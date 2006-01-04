package com.oktiva.mogno.additional;

/*
 * vim:fileencoding=utf-8:encoding=utf-8
 */

import com.oktiva.mogno.html.*;
import javax.servlet.http.HttpServletRequest;

/** ActionFrame - A frame associated with an action
 * <p>A frame that dispatch an evOnLoad.
 * <p>The property application and the property params overrides
 * the src property.
 * <dl><dt><b>Events</b></dt>
 * <dd>evOnLoad - This event is dispatched when this frame is loaded.</dd></dl>
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt; and others.
 */
public class ActionFrame extends Frame {
	/** The url of this web application */
	public String application = "";
	public String getApplicationUrl() {
		return application;
	}
	public void setApplicationUrl(String applicationUrl) {
		this.application = applicationUrl;
	}

	/** Another params (in URL encoded format) to add to the link */
	public String params = "";
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}

	/** Event dispatched when this frame is loaded. */
	public String evOnLoad;
	public String getEvOnLoad() {
		return evOnLoad;
	}
	public void setEvOnLoad(String evOnLoad) {
		this.evOnLoad = evOnLoad;
	}


	public void receiveRequest(HttpServletRequest request) {
		if (request.getParameter(name) != null) {
			queue("evOnLoad");
		}
	}
	public String show()
	throws Exception {
		src = application+"?mognoOrigin="+owner.name+"&"+name+"=true&"+params;
		return super.show();
	}
}
