package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** PARAM tag (defined by W3C)<br>
 * @version $Id$
 * @see Form
 */
public class Param extends EmptyTag {
	//htmlAttributes
	//name is inherited from Component
	/** HTML attribute */
	public String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String valuetype;
	public String getValuetype() {
		return valuetype;
	}
	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}

	/** HTML attribute */
	public String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	public Param() {
		super();
		tag = "PARAM";
	}
	
	public Vector htmlAttributes() {
		Vector v = new Vector();
		v.add("name");
		v.add("id");
		v.add("value");
		v.add("valuetype");
		v.add("type");
		return v;
	}
}
