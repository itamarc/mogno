package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** IMG tag (defined by W3C)<br>
 */
public class Img extends EmptyTag {
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
	public String src;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
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
	public String longdesc;
	public String getLongdesc() {
		return longdesc;
	}
	public void setLongdesc(String longdesc) {
		this.longdesc = longdesc;
	}

	//name is inherited from Component
	/** HTML attribute */
	public String height;
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}

	/** HTML attribute */
	public String width;
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}

	/** HTML attribute */
	public String usemap;
	public String getUsemap() {
		return usemap;
	}
	public void setUsemap(String usemap) {
		this.usemap = usemap;
	}

	/** HTML attribute */
	public String ismap;
	public String getIsmap() {
		return ismap;
	}
	public void setIsmap(String ismap) {
		this.ismap = ismap;
	}

	/** HTML attribute */
	public String border;
	public String getBorder() {
		return border;
	}
	public void setBorder(String border) {
		this.border = border;
	}


	public Img() {
		super();
		tag = "IMG";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(i18nHtmlAttributes());
		v.addAll(eventsHtmlAttributes());
		v.add("src");
		v.add("alt");
		v.add("longdesc");
		v.add("name");
		v.add("height");
		v.add("width");
		v.add("usemap");
		v.add("ismap");
		v.add("border");
		return v;
	}
}
