package com.oktiva.mogno.additional;

/*
 * vim:fileencoding=utf-8:encoding=utf-8
 */

import com.oktiva.mogno.html.*;
import javax.servlet.http.HttpServletRequest;

/** ActionLink - A link associated with an action
 * <p>A container that holds a link with a evOnClick event.
 * This link will be received and will dispatch an event in Mogno.
 * <p>The property application and the property params overrides
 * the href property.
 * <dl><dt><b>Events</b></dt>
 * <dd>evOnClick - This event is dispatched when this link is clicked.</dd></dl>
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt; and others.
 */
public class ActionLink extends A {
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

	/** The <i>mognoOrigin</i> param, defaults to <tt>owner.name</tt>. */
	public String origin = "";
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/** Event dispatched when this link is clicked. */
	public String evOnClick;
	public String getEvOnClick() {
		return evOnClick;
	}
	public void setEvOnClick(String evOnClick) {
		this.evOnClick = evOnClick;
	}


	public void receiveRequest(HttpServletRequest request) {
		if (request.getParameter(name) != null) {
			queue("evOnClick");
		}
	}
	public String startContainer() {
		if (application.equals("") && !designing) {
			HttpServletRequest request = getApplication().getServletRequest();
			application = request.getRequestURI();
		}
		String orig = origin;
		if (orig.equals("")) {
			orig = owner.name;
		}
		href = application+"?mognoOrigin="+orig+"&"+name+"=true&"+params;
		return super.startContainer();
	}
}
