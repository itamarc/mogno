package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/**
 * @version $Id$
 */
public class Style extends Container {
	/** The Cascading Style Sheet text. */
	public String css;
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}

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

	//htmlAttributes
	/** HTML attribute */
	public String media;
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
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
	public String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
	public Style() {
		super();
		tag = "STYLE";
		afterEnd = "\n";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(i18nHtmlAttributes());
		v.add("media");
		v.add("type");
		v.add("title");
		return v;
	}
	
	public String show()
	throws Exception {
		if (content == null) {
			content = "";
		} else {
			content += "\n";
		}
		if (css == null) {
			css = "";
		}
		content += css;
		return super.show();
	}
}
