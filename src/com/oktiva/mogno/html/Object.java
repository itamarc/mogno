package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** OBJECT tag
 */
public class Object extends Container {
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
	public String declare;
	public String getDeclare() {
		return declare;
	}
	public void setDeclare(String declare) {
		this.declare = declare;
	}

	/** HTML attribute */
	public String classid;
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}

	/** HTML attribute */
	public String codebase;
	public String getCodebase() {
		return codebase;
	}
	public void setCodebase(String codebase) {
		this.codebase = codebase;
	}

	/** HTML attribute */
	public String data;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	/** HTML attribute */
	public String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	/** HTML attribute */
	public String codetype;
	public String getCodetype() {
		return codetype;
	}
	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	/** HTML attribute */
	public String archive;
	public String getArchive() {
		return archive;
	}
	public void setArchive(String archive) {
		this.archive = archive;
	}

	/** HTML attribute */
	public String standby;
	public String getStandby() {
		return standby;
	}
	public void setStandby(String standby) {
		this.standby = standby;
	}

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

	//name is inherited from Component
	/** HTML attribute */
	public String tabindex;
	public String getTabindex() {
		return tabindex;
	}
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	
	public Object() {
		super();
		tag = "OBJECT";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(coreHtmlAttributes());
		v.addAll(i18nHtmlAttributes());
		v.addAll(eventsHtmlAttributes());
		v.add("declare");
		v.add("classid");
		v.add("codebase");
		v.add("data");
		v.add("type");
		v.add("codetype");
		v.add("archive");
		v.add("standby");
		v.add("height");
		v.add("width");
		v.add("usemap");
		v.add("name");
		v.add("tabindex");
		return v;
	}
}
