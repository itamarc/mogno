package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

public class Colgroup extends TableElement {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18nHtmlAttributes inherited from class com.oktiva.mogno.html.TableElement
	//eventsHtmlAttributes inherited from class com.oktiva.mogno.html.TableElement
	//cellhalignHtmlAttributes inherited from class com.oktiva.mogno.html.TableElement
	//cellvalignHtmlAttributes inherited from class com.oktiva.mogno.html.TableElement
	//htmlAttributes
	/** HTML attribute */
	public String span;
	public String getSpan() {
		return span;
	}
	public void setSpan(String span) {
		this.span = span;
	}

	/** HTML attribute */
	public String width;
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}

	
	public Colgroup() {
		super();
		tag = "COLGROUP";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.add("span");
		v.add("width");
		return v;
	}
}
