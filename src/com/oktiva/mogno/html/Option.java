package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/** OPTION tag (defined by W3C)<br>
 * @version $Id$
 * @see Form
 * @see Select
 */
public class Option extends Container {
	/** Event dispatched when this option is selected. */
	public String evOnSelect;
	public String getEvOnSelect() {
		return evOnSelect;
	}
	public void setEvOnSelect(String evOnSelect) {
		this.evOnSelect = evOnSelect;
	}

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

	//htmlAttributes
	/** HTML attribute */
	public String selected;
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}

	/** HTML attribute */
	public String label;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	/** HTML attribute */
	public String value;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	/** HTML attribute */
	public String disabled;
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}


	/** Non-HTML attribute */
	public String select;
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}


	public Option() {
		super();
		tag = "OPTION";
		afterEnd = "\n";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(coreHtmlAttributes());
		v.addAll(i18nHtmlAttributes());
		v.addAll(eventsHtmlAttributes());
		v.add("selected");
		v.add("label");
		v.add("value");
		v.add("disabled");
		return v;
	}
	
	public void receiveRequest(HttpServletRequest request) {
		if (((Select)owner.getChild(select)).isSelected(value)) {
			queue("evOnSelect");
		}
	}
	
	public String startContainer() {
		String sel = "";
		if (select != null && ((Select)owner.getChild(select)).isSelected(value)) {
			sel = " SELECTED";
		}
		return "<OPTION"+showHtmlAttributes()+sel+">";
	}
}
