package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/**
 * @version $Id$
 */
public class Script extends Container {
	/** The Javascript source. */
	public String script;
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}

	//htmlAttributes
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
	public String src;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}

	/** HTML attribute */
	public String defer;
	public String getDefer() {
		return defer;
	}
	public void setDefer(String defer) {
		this.defer = defer;
	}

	
	public Script() {
		super();
		tag = "SCRIPT";
		afterEnd = "\n";
	}
	
	public Vector htmlAttributes() {
		Vector v = new Vector();
		v.add("charset");
		v.add("type");
		v.add("src");
		v.add("defer");
		return v;
	}
	public String show()
	throws Exception {
		if (content == null) {
			content = "";
		} else {
			content += "\n";
		}
		if (script == null) {
			script = "";
		}
		content += script;
		return super.show();
	}
}
