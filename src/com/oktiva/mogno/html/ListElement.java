package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** Base for ListElements (defined by W3C)<br>
 * 
 * This component will use all the available properties following
 * the rules of HTML4.01.
 */
public class ListElement extends Container {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18nHtmlAttributes
	/** Internationalization HTML attribute */
	public String lang;
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

	/** Internationalization HTML attribute */
	public String dir;
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}

	//eventsHtmlAttributes
	/** Event HTML attribute */
	public String onmouseup;
	public String getOnmouseup() {
		return onmouseup;
	}
	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	/** Event HTML attribute */
	public String onmousedown;
	public String getOnmousedown() {
		return onmousedown;
	}
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/** Event HTML attribute */
	public String onclick;
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/** Event HTML attribute */
	public String ondblclick;
	public String getOndblclick() {
		return ondblclick;
	}
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	/** Event HTML attribute */
	public String onmouseover;
	public String getOnmouseover() {
		return onmouseover;
	}
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/** Event HTML attribute */
	public String onmousemove;
	public String getOnmousemove() {
		return onmousemove;
	}
	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	/** Event HTML attribute */
	public String onmouseout;
	public String getOnmouseout() {
		return onmouseout;
	}
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/** Event HTML attribute */
	public String onkeypress;
	public String getOnkeypress() {
		return onkeypress;
	}
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	/** Event HTML attribute */
	public String onkeydown;
	public String getOnkeydown() {
		return onkeydown;
	}
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	/** Event HTML attribute */
	public String onkeyup;
	public String getOnkeyup() {
		return onkeyup;
	}
	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}
	
	public ListElement() {
		super();
		afterEnd = "\n";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(coreHtmlAttributes());
		v.addAll(i18nHtmlAttributes());
		v.addAll(eventsHtmlAttributes());
		return v;
	}
}
