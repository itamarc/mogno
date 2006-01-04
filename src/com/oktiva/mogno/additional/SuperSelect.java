/*
 * SuperSelect.java
 *
 * Created on 29 de Dezembro de 2003, 20:43
 * vim:encoding=utf-8:fileencoding=utf-8
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.Component;
import com.oktiva.mogno.html.Option;
import com.oktiva.util.HashtableComparator;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

/** A select with options.
 */
public class SuperSelect extends SelectView {
	private Vector options = new Vector();
	public String optionsString;
	public boolean orderOptions = false;
	public String orderType = "label";
	public boolean orderInverse = false;
	
	/** Creates a new instance of SuperSelect */
	public SuperSelect() {
		super();
	}
	
	/** Starts the select and puts the options.
	 */
	public String startContainer() {
		StringBuffer ret = new StringBuffer();
		ret.append(super.startContainer());
		if (options.size() == 0 && optionsString != null && optionsString.length()>0) {
			setOptionsString(optionsString);
		}
		if (orderOptions) {
			Collections.sort(options, new HashtableComparator(orderType, orderInverse));
		}
		for (int i=0; i<options.size(); i++) {
			Hashtable item = (Hashtable)options.get(i);
			ret.append("<OPTION name=\"").append(getName()).append("_opt_"+i).append('"');
			String val = (String)item.get("value");
			String lbl = (String)item.get("label");
			ret.append(" value=\"");
			if (val == null) {
				ret.append(lbl);
			} else {
				ret.append(val);
			}
			ret.append('"');
			if ((val != null && getValues().contains(val)) || (val == null && getValues().contains(lbl))) {
				ret.append(" SELECTED");
			}
			ret.append(">").append(lbl).append("</OPTION>\n");
		}
		return ret.toString();
	}
	
	protected Hashtable getContentsByValue() {
		Hashtable ret = new Hashtable();
		for (int i=0; i<options.size(); i++) {
			Hashtable opt = (Hashtable)options.get(i);
			ret.put(opt.get("value"), opt.get("label"));
		}
		return ret;
	}
	
	/** Add an option to the select.
	 * @param option Hashtable with keys "value" and "label".
	 */
	public void addOption(Hashtable option) {
		options.add(option);
	}
	
	/** Add an option to the select.
	 */
	public void addOption(String value, String label) {
		Hashtable opt = new Hashtable();
		opt.put("value", value);
		opt.put("label", label);
		options.add(opt);
	}
	
	/** Set the options.  It's a Vector of Hashtables, with keys "value" and "label".
	 */
	public void setOptions(Vector options) {
		this.options = options;
	}

	/** String in the format <tt>value|label[&amp;&amp;value|label[&amp;&amp;...]]</tt>
	 */
	public String getOptionsString() {
		StringBuffer str = new StringBuffer();
		for (int i=0; i<options.size(); i++) {
			Hashtable item = (Hashtable)options.get(i);
			if (str.length()>0) {
				str.append("&&");
			}
			str.append(item.get("value")).append("|").append(item.get("label"));
		}
		return str.toString();
	}

	/** String in the format <tt>value|label[&amp;&amo;value|label[&amp;&amp;...]]</tt>
	 */
	public void setOptionsString(String optionsString) {
		this.optionsString = optionsString;
		String[] pares = optionsString.split("&&");
		for (int i=0; i<pares.length; i++) {
			String[] par = pares[i].split("|",2);
			if (par.length == 2) {
				addOption(par[0], par[1]);
			} else if (par.length == 1) {
				addOption(par[0], par[0]);
			}
		}
	}

	/** Getter for property orderOptions.
	 * Default: "true"
	 * @return Value of property orderOptions.
	 */
	public boolean isOrderOptions() {
		return orderOptions;
	}	
	/** Setter for property orderOptions.
	 * Default: "false"
	 * @param orderOptions New value of property orderOptions.
	 */
	public void setOrderOptions(boolean orderOptions) {
		this.orderOptions = orderOptions;
	}
	
	/** Getter for property orderType.
	 * Default: "label", can be also "value".
	 * @return Value of property orderType.
	 */
	public String getOrderType() {
		return orderType;
	}
	/** Setter for property orderType.
	 * Default: "label", can be also "value".
	 * @param orderType New value of property orderType.
	 */
	public void setOrderType(String orderType) {
		if (!"label".equals(orderType) && !"value".equals(orderType)) {
			throw new java.lang.IllegalArgumentException("orderType must be 'label' or 'value'");
		}
		this.orderType = orderType;
	}
	
	/** Getter for property orderInverse.
	 * Default: "false".
	 * @return Value of property orderInverse.
	 */
	public boolean isOrderInverse() {
		return orderInverse;
	}
	/** Setter for property orderInverse.
	 * Default: "false".
	 * @param orderInverse New value of property orderInverse.
	 */
	public void setOrderInverse(boolean orderInverse) {
		this.orderInverse = orderInverse;
	}
}
