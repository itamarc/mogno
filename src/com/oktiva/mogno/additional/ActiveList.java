package com.oktiva.mogno.additional;

import com.oktiva.mogno.*;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/** Component to create a indented list.<br>
 * This components outputs an indented list, with optional <tt>ActionLink</tt>s
 * and parameters at <tt>LI</tt>s.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt; and others.
 */
public class ActiveList extends Visual {
	static Logger logger = Logger.getLogger(Include.class.getName());
	/** A string with the list of items. Each item in one line.<p>
	 * The indentation is made by the number of white spaces before the first character.<p>
	 * The lines can have up to 5 "fields" separated by a {@link #separator}:
	 * <ol>
	 * <li>The line item content</li>
	 * <li>The line item attributes</li>
	 * <li>The ActionLink name</li>
	 * <li>The ActionLink evOnClick value</li> (required if there is a name for the ActionLink)
	 * <li>The ActionLink params</li>
	 * </ol>
	 * Only the first field is required.  If there is no ActionLink data, then 
	 * @see #separator
	 * @see ActionLink
	 * @see ActionLink#params
	 * @see com.oktiva.mogno.html.Li
	 */
	public String list = "";
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}

	/** The field separator to be used at a regexp split.
	 * Default: <tt>|</tt>.<br>
	 * Note: remember to do regex scaping if you change this.
	 */
	public String separator = "\\|";
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}


	public String show()
	throws Exception {
		StringBuffer ret = new StringBuffer(128);
		ret.append(super.show());
		Vector lines = breakLines();
		if(lines.size() == 0 ||
		(lines.size() == 1 && (getString(lines,0,0) == null || "".equals(getString(lines,0,0))))) {
			return "";
		}
		int lastLevel = 0;
		ret.append("\n<UL>\n");
		for (int i = 0; i<lines.size(); i++) {
			int level = 0;
			while(getString(lines,i,0).matches("^\\s.*")) {
				setString(lines,i,0,getString(lines,i,0).replaceFirst("\\s", ""));
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
			String attribs = getString(lines,i,1);
			if(attribs == null) {
				attribs = "";
			} else if(!attribs.equals("")) {
				attribs = " "+attribs;
			}
			ret.append("<LI").append(attribs).append(">");
			// If there is no ActionLink
			if(getActionLink(lines,i) == null) {
				// Put only the content
				ret.append(getString(lines,i,0));
			} else {
				ret.append(getActionLink(lines,i).show());
			}
			ret.append("</LI>\n");
		}
		while(lastLevel>=0) {
			ret.append("</UL>\n");
			lastLevel--;
		}
		return ret.toString();
	}
	private ActionLink getActionLink(Vector v, int i) {
		return (ActionLink)((Vector)v.get(i)).get(2);
	}
	private String getString(Vector v, int i, int j) {
		return (String)((Vector)v.get(i)).get(j);
	}
	private String setString(Vector v, int i, int j, String str) {
		return (String)((Vector)v.get(i)).set(j,str);
	}
	private Vector breakLines() {
		String[] lines = list.split("\n");
		Vector ret = new Vector();
		for (int i = 0; i<lines.length; i++) {
			if(lines[0] == null || "".equals(lines[0].trim())) {
				continue;
			}
			Vector v = new Vector();
			v.setSize(3);
			String[] thisLine = lines[i].split(separator,4);
			// First field: LI content
			v.set(0,thisLine[0]);
			if(thisLine.length>1) {
				v.set(1,thisLine[1]);
			}
			// if there is data about ActionLinks
			if(thisLine.length>2) {
				ActionLink al = new ActionLink();
				al.name = thisLine[2];
				al.evOnClick = thisLine[3];
				// if there is ActionLink params
				if(thisLine[4] != null) {
					al.params = thisLine[4];
				}
				al.content = thisLine[0];
				v.set(2,al);
			}
			ret.add(v);
		}
		return ret;
	}
	public void receiveRequest(HttpServletRequest request) {
		Vector lines = breakLines();
		for(int i=0; i<lines.size(); i++) {
			if(getActionLink(lines,i) == null) {
				continue;
			}
			ActionLink al = getActionLink(lines,i);
			al.receiveRequest(request);
		}
	}
}
