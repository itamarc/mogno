package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** LINK tag (defined by W3C)<br>
 */
public class Link extends EmptyTag {
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
	public String media;
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}

	/** HTML attribute */
	public String charset;
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
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
	public String href;
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}

	/** HTML attribute */
	public String hreflang;
	public String getHreflang() {
		return hreflang;
	}
	public void setHreflang(String hreflang) {
		this.hreflang = hreflang;
	}

	/** HTML attribute */
	public String rel;
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}

	/** HTML attribute */
	public String rev;
	public String getRev() {
		return rev;
	}
	public void setRev(String rev) {
		this.rev = rev;
	}


	public Link() {
		super();
		tag = "LINK";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(i18nHtmlAttributes());
		v.addAll(eventsHtmlAttributes());
		v.add("media");
		v.add("charset");
		v.add("type");
		v.add("href");
		v.add("hreflang");
		v.add("rel");
		v.add("rev");
		return v;
	}
}
