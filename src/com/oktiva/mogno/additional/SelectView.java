/*
 * SelectView.java
 *
 * Created on 29 de Junho de 2003, 17:19
 */

package com.oktiva.mogno.additional;

import com.oktiva.mogno.Component;
import com.oktiva.mogno.html.Li;
import com.oktiva.mogno.html.Optgroup;
import com.oktiva.mogno.html.Option;
import com.oktiva.mogno.html.Span;
import com.oktiva.mogno.html.Ul;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * @see ComponentView
 * @see FormView
 */
public class SelectView extends com.oktiva.mogno.html.Select implements ComponentView {
	private static Logger log = Logger.getLogger("com.oktiva.mogno.additional");
	public boolean viewOnly = false;
	
	public String show()
	throws Exception {
		if(isViewOnly()) {
			StringBuffer output=new StringBuffer();
			Hashtable contents = getContentsByValue();
			setStyle("display:none");
			Vector vals=getValues();
			if (vals.size()>0 && !(vals.size()==1 && "".equals((String)vals.get(0)))) {
				if(isMultiValued()) {
					Ul list = new Ul();
					//list.setParent(getParent());
					list.setTop(getTop());
					list.setLeft(getLeft());
					String listName = getName()+"_view";
					list.setName(listName);
					for(int i=0;i<vals.size();i++) {
						Li item=new Li();
						item.setParent(listName);
						item.setTop(i);
						item.setLeft(0);
						item.setName(listName+"_opt_"+i);
						if(contents.containsKey(vals.get(i))) {
							item.setContent((String)contents.get(vals.get(i)));
						} else {
							item.setContent((String)vals.get(i));
						}
						list.registerChild(item);
					}
					output.append(list.show());
				} else {
					Span s=new Span();
					s.setParent(getParent());
					s.setTop(getTop());
					s.setLeft(getLeft());
					s.setName(getName()+"_view");
					if(contents.containsKey(vals.get(0))) {
						s.setContent((String)contents.get(vals.get(0)));
					} else {
						s.setContent((String)vals.get(0));
					}
					registerChild(s);
					output.append(s.show());
				}
			}
			output.append(super.show());
			return output.toString();
		}
		return super.show();
	}
	
	protected Hashtable getContentsByValue() {
		Component owner = getOwner();
		if(owner==null) {
			owner=this;
		}
		Hashtable descriptions=new Hashtable();
		Enumeration e = owner.listChilds();
		while(e.hasMoreElements()) {
			String name=(String)e.nextElement();
			Component c = owner.getChild(name);
			if(c!=null && c.descendentOf(getName())) {
				if(c instanceof Optgroup) {
					/* TODO: Start a new list */
				} else if(c instanceof Option) {
					Option o = (Option)c;
					String optValue = o.getValue();
					if (optValue == null) {
						optValue = o.getContent();
					}
					if (o.getContent() != null) {
						descriptions.put(optValue,o.getContent());
					}
				}
			}
		}
		return descriptions;
	}
	/** Getter for property viewOnly.
	 * @return Value of property viewOnly.
	 *
	 */
	public boolean isViewOnly() {
		return viewOnly;
	}
	
	/** Setter for property viewOnly.
	 * @param viewOnly New value of property viewOnly.
	 *
	 */
	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}
	
	private boolean isMultiValued() {
		String multi = getMultiple();
		if((multi != null) && (multi.length()>0)) {
			return true;
		}
		return false;
	}
}