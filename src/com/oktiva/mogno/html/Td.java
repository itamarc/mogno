package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

public class Td extends TableElement {
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18nHtmlAttributes inherited from class com.oktiva.mogno.html.TableElement
	//eventsHtmlAttributes inherited from class com.oktiva.mogno.html.TableElement
	//cellhalignHtmlAttributes inherited from class com.oktiva.mogno.html.TableElement
	//cellvalignHtmlAttributes inherited from class com.oktiva.mogno.html.TableElement
	//htmlAttributes
	/** HTML attribute */
	public String abbr;
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/** HTML attribute */
	public String axis;
	public String getAxis() {
		return axis;
	}
	public void setAxis(String axis) {
		this.axis = axis;
	}

	/** HTML attribute */
	public String headers;
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}

	/** HTML attribute */
	public String scope;
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

	/** HTML attribute */
	public String rowspan;
	public String getRowspan() {
		return rowspan;
	}
	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	/** HTML attribute */
	public String colspan;
	public String getColspan() {
		return colspan;
	}
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	
	public Td() {
		super();
		tag = "TD";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.add("abbr");
		v.add("axis");
		v.add("headers");
		v.add("scope");
		v.add("rowspan");
		v.add("colspan");
		return v;
	}
}
