package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** AREA tag
 */
public class Area extends EmptyTag {
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
	public String shape;
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}

	/** HTML attribute */
	public String coords;
	public String getCoords() {
		return coords;
	}
	public void setCoords(String coords) {
		this.coords = coords;
	}

	/** HTML attribute */
	public String href;
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}

	/** HTML attribute */
	public String nohref;
	public String getNohref() {
		return nohref;
	}
	public void setNohref(String nohref) {
		this.nohref = nohref;
	}

	/** HTML attribute */
	public String alt;
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}

	/** HTML attribute */
	public String tabindex;
	public String getTabindex() {
		return tabindex;
	}
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	/** HTML attribute */
	public String accesskey;
	public String getAccesskey() {
		return accesskey;
	}
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	/** HTML attribute */
	public String onfocus;
	public String getOnfocus() {
		return onfocus;
	}
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	/** HTML attribute */
	public String onblur;
	public String getOnblur() {
		return onblur;
	}
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	
	public Area() {
		super();
		tag = "AREA";
		afterEnd = "\n";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(coreHtmlAttributes());
		v.addAll(i18nHtmlAttributes());
		v.addAll(eventsHtmlAttributes());
		v.add("shape");
		v.add("coords");
		v.add("href");
		v.add("nohref");
		v.add("alt");
		v.add("tabindex");
		v.add("accesskey");
		v.add("onfocus");
		v.add("onblur");
		return v;
	}
}
