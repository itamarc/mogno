package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import com.oktiva.mogno.additional.DateInput;
import com.oktiva.mogno.additional.DoubleInput;
import java.util.*;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/** FORM tag (defined by W3C).<br>
 * @version $Id$
 * @see Button
 * @see Input
 * @see Param
 * @see Radiogroup
 * @see Select
 * @see Option
 * @see Textarea
 */
public class Form extends Container {
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

	public String getId() {
		if(this.id == null || this.id.equals("")) {
			return getName();
		}
		return getId();
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
	public String action;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	/** HTML attribute */
	public String method;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	/** HTML attribute */
	public String enctype;
	public String getEnctype() {
		return enctype;
	}
	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}
	
	/** HTML attribute */
	public String accept;
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	
	/** HTML attribute */
	public String onsubmit;
	public String getOnsubmit() {
		return onsubmit;
	}
	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}
	
	/** HTML attribute */
	public String onreset;
	public String getOnreset() {
		return onreset;
	}
	public void setOnreset(String onreset) {
		this.onreset = onreset;
	}
	
	/** <b>Mapped</b> HTML attribute<br>
	 * NOTE: Because <i>accept-charset</i> is a invalid identifier, the property
	 * <i>accept-charset</i> is mapped to <i>acceptCharset</i>.
	 * @see Visual#showHtmlAttributes()
	 */
	public String acceptCharset;
	public String getAcceptCharset() {
		return acceptCharset;
	}
	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}
	
	
	public Form() {
		super();
		tag = "FORM";
		setMethod("POST");
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(coreHtmlAttributes());
		v.addAll(i18nHtmlAttributes());
		v.addAll(eventsHtmlAttributes());
		v.add("name");
		v.add("action");
		v.add("method");
		v.add("enctype");
		v.add("accept");
		v.add("onsubmit");
		v.add("onreset");
		v.add("acceptCharset");
		return v;
	}
	
	public String startContainer() {
		if(!designing) {
			HttpServletRequest request = getApplication().getServletRequest();
			action = request.getRequestURI();
		}
		String ret = "<FORM"+showHtmlAttributes()+">\n"+
		"<INPUT TYPE='hidden' NAME='mognoOrigin' VALUE='"+owner.getName()+"'>\n";
		Hashtable ownerBag = owner.getBag();
		if (ownerBag != null && ownerBag.size()>0) {
			ret += "<INPUT TYPE='hidden' NAME='mognoSerializedBag_"+owner.getName()+"' VALUE='"+owner.serializedBag()+"'>\n";
		}
		return ret;
	}
	
	public String endContainer() {
		return "</FORM>\n";
	}
	
	/** Fetch the values of all <b>Inputs</b>, <b>Radiogroups</b>, <b>Selects</b>
	 * and <b>Textareas</b> inside the form and build a Hashtable with
	 * <tt>componentName => componentValue</tt>
	 * You should issue a checkSyntax before calling this function, it will
	 * ignore all syntax errors.
	 * DateInputs are stored as Date
	 * DoubleInputs are stored as Double
	 * @return A hashtable with the values of all components.
	 */
	public Hashtable getFormValues() {
		Hashtable values=new Hashtable();
		Component listener = this;
		if (owner != null) {
			listener = owner;
		}
		Enumeration children=listener.listChilds();
		while(children.hasMoreElements()) {
			String childName = (String) children.nextElement();
			Component c = listener.getChild(childName);
			if(c.descendentOf(name)) {
				java.lang.Object value=null;
				if(c instanceof Select) {
					value=(java.lang.Object)((Select)c).getValues();
				} else if (c instanceof Textarea) {
					value=(java.lang.Object)((Textarea)c).getContent();
				} else if(c instanceof DateInput) {
					DateInput i=(DateInput)c;
					try {
						value=(java.lang.Object)i.getDateValue();
					} catch (Exception e) {
						/* You should checksyntax before calling getFormValues */
					}
				} else if(c instanceof DoubleInput) {
					DoubleInput i=(DoubleInput)c;
					try {
						value=(java.lang.Object)i.getDoubleValue();
					} catch(Exception e) {
						/* You should checksyntax before calling getFormValues */
					}
				} else if(c instanceof Input) {
					Input i=(Input)c;
					/* Squip input type radio, since their value
					 * is retrieved by the Radiogroup component
					 */
					if(!i.getType().equalsIgnoreCase("radio")) {
						if(i.getType().equalsIgnoreCase("checkbox")) {
							value=(java.lang.Object)i.getChecked();
						} else {
							value=(java.lang.Object)i.getValue();
						}
					}
				} else if(c instanceof Radiogroup) {
					Radiogroup r=(Radiogroup)c;
					value=(java.lang.Object)r.getValue();
				}

				if(value!=null) {
					values.put(c.getName(),value);
				}

			}
		}
		return values;
	}
	
	/** Set the values of all <b>Inputs</b>, <b>Radiogroups</b>, <b>Selects</b>
	 * and <b>Textareas</b> inside the form from a Hashtable with
	 * <tt>componentName => componentValue</tt>
	 * DateInputs are stored as Date
	 * DoubleInputs are stored as Double
	 * @param values A hashtable with the values of all components.
	 */
	public void setFormValues(Hashtable values) {
		Component listener = this;
		if (owner != null) {
			listener = owner;
		}
		Enumeration children=listener.listChilds();
		while(children.hasMoreElements()) {
			String childName = (String) children.nextElement();
			Component c = listener.getChild(childName);
			if(values.containsKey(childName) && c.descendentOf(name)) {
				java.lang.Object value = values.get(childName);
				if (value == null) { continue; }
				if(c instanceof Select) {
					((Select)c).setValues((Vector)value);
				} else if (c instanceof Textarea) {
					((Textarea)c).setContent((String)value);
				} else if(c instanceof DateInput) {
					DateInput i=(DateInput)c;
					i.setDateValue((Date)value);
				} else if(c instanceof DoubleInput) {
					DoubleInput i=(DoubleInput)c;
					i.setDoubleValue((Double)value);
				} else if(c instanceof Input) {
					Input i=(Input)c;
					if(i.getType().equalsIgnoreCase("checkbox")) {
						i.setChecked((String)value);
					} else {
						i.setValue((String)value);
					}
				} else if(c instanceof Radiogroup) {
					Radiogroup r=(Radiogroup)c;
					r.setValue((String)value);
				}
			}
		}
	}

	public Vector nonAttributeGetters() {
		Vector v = super.nonAttributeGetters();
		v.add("getFormValues");
		return v;
	}
}
