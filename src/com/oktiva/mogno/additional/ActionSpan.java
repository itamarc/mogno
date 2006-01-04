package com.oktiva.mogno.additional;

/*
 * vim:fileencoding=utf-8:encoding=utf-8
 */
import com.oktiva.mogno.html.*;
import javax.servlet.http.HttpServletRequest;

/** ActionSpan - A span associated with an action
 * <p>A container that holds a span with a evOnClick event.
 * This span will be received and will dispatch an event in Mogno.
 * <p>The property application and the property params composes
 * the destination URL.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt; and others.
 */
public class ActionSpan extends Span {
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

	/** Event dispatched when this span is clicked. */
	public String evOnClick;
	public String getEvOnClick() {
		return evOnClick;
	}
	public void setEvOnClick(String evOnClick) {
		this.evOnClick = evOnClick;
	}

	/** Result javascript code:
	 * <dl><dt><tt>window</tt></dt><dd>do a "window.location=url"</dd>
	 * <dt><tt>top</tt></dt><dd>do a "window.top.location=url"</dd>
	 * <dt><tt>opener</tt></dt><dd>do a "window.opener.location=url"</dd>
	 * <dt><tt>open</tt></dt><dd>do a "open(url)".</dd>
	 * <dt><tt>reload</tt></dt><dd>do a "open(url)".</dd>
	 * </dl>
	 * If there is a param "targetName", uses it as the window target name.
	 */
	public String mode = "window";
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}

	/** The target window for the action of this span "onclick".
	 * Default is the current <tt>window</tt>
	 */
	public String targetName = "window";
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}


	public void receiveRequest(HttpServletRequest request) {
		if(request.getParameter(name) != null) {
			queue("evOnClick");
		}
		String target = request.getParameter("targetWindow");
		if(target != null && !target.equals("")) {
			targetName = target;
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
		String url = application+"?mognoOrigin="+orig+"&"+name+"=true&"+params;
		onclick=buildOnclickCode(url);
		return super.startContainer();
	}
	/*
	 * <dl><dt><tt>window</tt></dt><dd>do a "window.location=url"</dd>
	 * <dt><tt>top</tt></dt><dd>do a "window.top.location=url"</dd>
	 * <dt><tt>opener</tt></dt><dd>do a "window.opener.location=url"</dd>
	 * <dt><tt>open</tt></dt><dd>do a "open(url)".</dd>
	 * <dt><tt>reload</tt></dt><dd>do a "open(url)".</dd>
	 */
	protected String buildOnclickCode(String url) {
		if(mode.equals("top")) {
			return targetName+".top.location='"+url+"';";
		} else if(mode.equals("opener")) {
			return targetName+".opener.location='"+url+"';";
		} else if(mode.equals("open")) {
			String target = "";
			if (!targetName.equals("window")) {
				target = targetName;
			}
			return "open('"+url+"','"+target+"');";
		} else if(mode.equals("reload")) {
			return targetName+".opener.location='"+url+"';";
		} else { // Default mode: window
			return targetName+".location='"+url+"';";
		}
	}
}
