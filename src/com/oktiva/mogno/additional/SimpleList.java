package com.oktiva.mogno.additional;

import com.oktiva.mogno.*;
import java.lang.StringBuffer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/** Component to create a simple indented list.<br>
 * This components outputs an indented list.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt; and others.
 * vim:encoding=utf-8:fileencoding=utf-8
 */
public class SimpleList extends Visual {
	public String evOnClick = "";
	private Vector ids = new Vector();
	private String selectedItem = null;
	public boolean active = false;
	
	public String applicationUrl = "";
	
	/** Other params (in URL encoded format) to add to the link */
	public String params = "";
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	/** A string with the list of items. Each item in one line. The indentation
	 * is made by the number of white spaces before the first character.
	 */
	public StringBuffer list = new StringBuffer();
	/**
	 * @deprecated
	 */
	public String getList() {
		return list.toString();
	}
	
	/**
	 * @deprecated
	 */
	public void setList(String list) {
		this.list = new StringBuffer(list);
	}

	/** The <i>mognoOrigin</i> param, defaults to <tt>owner.name</tt>. */
	public String origin = "";
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void reset() {
		this.list= new StringBuffer("");
	}
	
	private String multiplyStr(int howMany, String str) {
		StringBuffer buf=new StringBuffer(howMany * str.length());
		for(int i=0; i<howMany; i++) {
			buf.append(str);
		}
		return buf.toString();
	}
	
	public void add(String item, int level) {
		list.append(multiplyStr(level," ")).append(item).append("\n");
	}
	
	public void add(String item, int level, String id) {
		add(item,level);
		this.ids.add(id);
	}
	
	public String show()
	throws Exception {
		StringBuffer ret = new StringBuffer(128);
		ret.append(super.show());
		String[] lines = getList().split("\n");
		if(lines.length == 1 && (lines[0] == null || "".equals(lines[0]))) {
			return "";
		}
		int lastLevel = 0;
		ret.append("\n<UL>\n");
		for (int i = 0; i<lines.length; i++) {
			int level = 0;
			while(lines[i].matches("^\\s.*")) {
				lines[i] = lines[i].replaceFirst("\\s", "");
				level++;
			}
			while (level > lastLevel) {
				ret.append("<UL>\n");
				lastLevel++;
			}
			while (level < lastLevel) {
				ret.append("</UL>\n");
				lastLevel--;
			}
			ret.append("<LI>");
			if(isActive()) {
				
				if(applicationUrl==null || applicationUrl.length()==0) {
					HttpServletRequest request = getApplication().getServletRequest();
					applicationUrl = request.getRequestURI();
				}
				ret.append("<a href=\"");
				String href = applicationUrl+"?mognoOrigin="+
				("".equals(origin)?owner.name:origin)+
				"&"+getName()+"="+ids.get(i)+"&"+params;
				ret.append(href);
				ret.append("\">");
				ret.append(lines[i]);
				ret.append("</a>");
			} else {
				ret.append(lines[i]);
			}
			ret.append("</LI>\n");
		}
		while(lastLevel>=0) {
			ret.append("</UL>\n");
			lastLevel--;
		}
		return ret.toString();
	}
	
	/** Getter for property active.
	 * @return Value of property active.
	 *
	 */
	public boolean isActive() {
		return active;
	}
	
	/** Setter for property active.
	 * @param active New value of property active.
	 *
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/** Getter for property applicationUrl.
	 * @return Value of property applicationUrl.
	 *
	 */
	public java.lang.String getApplicationUrl() {
		return applicationUrl;
	}
	
	/** Setter for property applicationUrl.
	 * @param applicationUrl New value of property applicationUrl.
	 *
	 */
	public void setApplicationUrl(java.lang.String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	
	public void receiveRequest(HttpServletRequest request) {
		if (request.getParameter(name) != null) {
			selectedItem=request.getParameter(name);
			queue("evOnClick");
		}
	}
	
	/** Getter for property selectedItem.
	 * @return Value of property selectedItem.
	 *
	 */
	public java.lang.String getSelectedItem() {
		return selectedItem;
	}
	
	/** Setter for property selectedItem.
	 * @param selectedItem New value of property selectedItem.
	 *
	 */
	public void setSelectedItem(java.lang.String selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	/** Getter for property evOnClick.
	 * @return Value of property evOnClick.
	 *
	 */
	public java.lang.String getEvOnClick() {
		return evOnClick;
	}
	
	/** Setter for property evOnClick.
	 * @param evOnClick New value of property evOnClick.
	 *
	 */
	public void setEvOnClick(java.lang.String evOnClick) {
		this.evOnClick = evOnClick;
	}
	
}
