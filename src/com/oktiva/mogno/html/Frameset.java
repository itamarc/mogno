package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/**
 * @version $Id$
 * @see Frame
 * @see Iframe
 * @see Noframes
 */
public class Frameset extends Container {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//htmlAttributes
	//name is inherited from Component
	/** HTML attribute */
	public String longdesc;
	public String getLongdesc() {
		return longdesc;
	}
	public void setLongdesc(String longdesc) {
		this.longdesc = longdesc;
	}

	/** HTML attribute */
	public String src;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
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
	public String marginwidth;
	public String getMarginwidth() {
		return marginwidth;
	}
	public void setMarginwidth(String marginwidth) {
		this.marginwidth = marginwidth;
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
	public String noresize;
	public String getNoresize() {
		return noresize;
	}
	public void setNoresize(String noresize) {
		this.noresize = noresize;
	}

	/** HTML attribute */
	public String rows;
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}

	/** HTML attribute */
	public String cols;
	public String getCols() {
		return cols;
	}
	public void setCols(String cols) {
		this.cols = cols;
	}

	/** HTML attribute */
	public String scrolling;
	public String getScrolling() {
		return scrolling;
	}
	public void setScrolling(String scrolling) {
		this.scrolling = scrolling;
	}

	
	public Frameset() {
		super();
		tag = "FRAMESET";
		afterEnd = "\n";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(coreHtmlAttributes());
		v.add("name");
		v.add("longdesc");
		v.add("src");
		v.add("frameborder");
		v.add("marginwidth");
		v.add("marginheight");
		v.add("noresize");
		v.add("rows");
		v.add("cols");
		v.add("scrolling");
		return v;
	}
}
