package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/** SELECT tag (defined by W3C)<br>
 * @version $Id$
 * @see Form
 * @see Option
 */
public class Select extends Container {
	/** Event dispatched when the content of the select changes. */
	public String evOnChange = null;
	public String getEvOnChange() {
		return evOnChange;
	}
	
	public void setEvOnChange(String evOnChange) {
		this.evOnChange = evOnChange;
	}
	
	public String getId() {
		if(this.id == null || this.id.equals("")) {
			return this.name;
		} else {
			return this.id;
		}
	}
	
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
	//name is inherited from Component
	/** HTML attribute */
	public String size;
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	/** HTML attribute */
	public String multiple;
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	
	/** HTML attribute */
	public String disabled;
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	/** HTML attribute */
	public String tabindex;
	public String getTabindex() {
		return tabindex;
	}
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}
	
	/** HTML attribute */
	public String onfocus;
	public String getOnfocus() {
		return onfocus;
	}
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	
	/** HTML attribute */
	public String onblur;
	public String getOnblur() {
		return onblur;
	}
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}
	
	/** HTML attribute */
	public String onchange;
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	
	
	/** Non-HTML attribute */
	public Vector values = new Vector();
	public Vector getValues() {
		return values;
	}
	public void setValues(Vector values) {
		this.values = values;
	}
	
	/** If this input has to be filled to be considered valid in checkSyntax(), defaults to false */
	public boolean required = false;
	public boolean getRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public Select() {
		super();
		tag = "SELECT";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(coreHtmlAttributes());
		v.addAll(i18nHtmlAttributes());
		v.addAll(eventsHtmlAttributes());
		v.add("name");
		v.add("size");
		v.add("multiple");
		v.add("disabled");
		v.add("tabindex");
		v.add("onfocus");
		v.add("onblur");
		v.add("onchange");
		return v;
	}
	
	public void receiveRequest(HttpServletRequest request) {
		if (getEvOnChange() != null) {
			String serializedValues = request.getParameter("__"+getName()+"_serializedValues__");
			if (serializedValues != null && !"___Unserializable___".equals(serializedValues) && !"".equals(serializedValues)) {
				unserializeValues(serializedValues);
			}
		}
		Vector oldValue = values;
		String[] selected = request.getParameterValues(name);
		values = new Vector();
		if (selected != null) {
			for (int i=0; i<selected.length; i++) {
				values.add(selected[i]);
			}
		}
		if ((oldValue == null && values != null) || (oldValue != null && !oldValue.equals(values))) {
			queue("evOnChange");
		}
	}
	
	/** Check if this value is selected. */
	public boolean isSelected(String value) {
		return values.contains(value);
	}
	
	public void checkSyntax()
	throws SyntaxErrorException {
		if (required &&
		(values.size()==0 ||
		(values.size()==1 && (values.get(0) == null || "".equals(values.get(0)))))) {
			throw new SyntaxErrorException("Required field "+name+" is empty.");
		}
	}
	
	/** Return the value of this Select. Should only be used in non-multiple
	 * selects, since it will only return <b>one</b> value.
	 * @return String value
	 */
	public String getValue() {
		Vector v = getValues();
		if (v.size() > 0) {
			return (String)v.get(0);
		} else {
			return null;
		}
	}
	
	/** Sets the value of this Select. Should only be used in non-multiple
	 * selects, since it can only set <b>one</b> value.
	 * @param value
	 */
	public void setValue(String value) {
		Vector v=new Vector();
		v.add(value);
		setValues(v);
	}
	
	public String startContainer() {
		if (getEvOnChange() != null) {
			String serializedValues = serializeValues();
			if ("___Unserializable___".equals(serializedValues)) {
				return super.startContainer();
			} else {
				return "<INPUT type='hidden' name='__"+getName()+"_serializedValues__' value='"+serializedValues+"'>"+super.startContainer();
			}
		} else {
			return super.startContainer();
		}
	}
	
	public String serializeValues() {
		Vector values = getValues();
		if (values == null || values.size() == 0) {
			return "___Unserializable___";
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream objout = new ObjectOutputStream(out);
			objout.writeObject(values);
			byte[] data = out.toByteArray();
			StringBuffer serialized = new StringBuffer();
			for (int i = 0; i < data.length; i++) {
				serialized.append(data[i]);
				if (i < data.length - 1) {
					serialized.append("%");
				}
			}
			return serialized.toString();
		} catch (Exception e) {
			return "___Unserializable___";
		}
	}
	
	protected void unserializeValues(String data) {
		try {
			String[] strbytes = data.split("%");
			byte[] bytes = new byte[strbytes.length];
			for (int i = 0; i < strbytes.length; i++) {
				bytes[i] = (byte)(Integer.parseInt(strbytes[i]));
			}
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream objin = new ObjectInputStream(in);
			setValues((Vector)objin.readObject());
		} catch (Exception e) {
			System.err.println("Error while unserializing Select values... "+e);
		}
	}
}
