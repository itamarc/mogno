package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** FRAME tag (defined by W3C)<br>
 * @version $Id$
 * @see Frameset
 * @see Iframe
 * @see Noframes
 */
public class Frame extends EmptyTag {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//htmlAttributes
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
	public String src;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}

	/** HTML attribute */
	public String marginheight;
	public String getMarginheight() {
		return marginheight;
	}
	public void setMarginheight(String marginheight) {
		this.marginheight = marginheight;
	}

	/** HTML attribute */
	public String marginwidth;
	public String getMarginwidth() {
		return marginwidth;
	}
	public void setMarginwidth(String marginwidth) {
		this.marginwidth = marginwidth;
	}

	/** HTML attribute */
	public String frameborder;
	public String getFrameborder() {
		return frameborder;
	}
	public void setFrameborder(String frameborder) {
		this.frameborder = frameborder;
	}

	/** HTML attribute */
	public String noresize;
	public String getNoresize() {
		return noresize;
	}
	public void setNoresize(String noresize) {
		this.noresize = noresize;
	}

	/** HTML attribute */
	public String scrolling;
	public String getScrolling() {
		return scrolling;
	}
	public void setScrolling(String scrolling) {
		this.scrolling = scrolling;
	}


	public Frame() {
		super();
		tag = "FRAME";
		afterEnd = "\n";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.add("longdesc");
		v.add("name");
		v.add("src");
		v.add("marginheight");
		v.add("marginwidth");
		v.add("frameborder");
		v.add("noresize");
		v.add("scrolling");
		return v;
	}
}
