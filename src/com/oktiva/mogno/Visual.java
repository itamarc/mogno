package com.oktiva.mogno;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.reflect.*;
import org.apache.log4j.Logger;

/**
 * This is the base class for all the components that 
 * are visual components.
 * @version $Id$
 */
public class Visual extends Component {
	static Logger logger = Logger.getLogger(Visual.class.getName());
	// coreHtmlAttributes
	/** Core HTML attribute */
	public String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	/** <b>Mapped</b> core HTML attribute<br>
	 * Because class is a reserved word, the property
	 * <i>class</i> is mapped to <i>styleClass</i>.
	 * @see #showHtmlAttributes()
	 */
	public String styleClass;
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/** Core HTML attribute */
	public String style;
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	/** Core HTML attribute */
	public String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The component in which this component is inside
	 */
	public String parent;
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}

	
	/**
	 * Ordering variable
	 */
	public int top = 0;
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * Ordering variable
	 */
	public int left = 0;
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * Used by container to know how many rows this
	 * component will use
	 * defaults to 1
	 */
	public int gridHeight = 1;
	public int getGridHeight() {
		return gridHeight;
	}
	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	/**
	 * Used by container to know how many columns this
	 * component will use
	 * defaults to 1
	 */
	public int gridWidth = 1;
	public int getGridWidth() {
		return gridWidth;
	}
	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	/**
	 * Defines the weight of this column.
	 * defaults to 0
	 */
	public int gridWeightX = 0;
	public int getGridWeightX() {
		return gridWeightX;
	}
	public void setGridWeightX(int gridWeightX) {
		this.gridWeightX = gridWeightX;
	}

	/**
	 * Defines the weight of this column.
	 * defaults to 0
	 */
	public int gridWeightY = 0;
	public int getGridWeightY() {
		return gridWeightY;
	}
	public void setGridWeightY(int gridWeightY) {
		this.gridWeightY = gridWeightY;
	}


	/**
	 * Event dispatched when a visual component is shown
	 */
	public String evOnShow;
	public String getEvOnShow() {
		return evOnShow;
	}
	public void setEvOnShow(String evOnShow) {
		this.evOnShow = evOnShow;
	}

	
	/**
	 * Signalization if this component had a error
	 */
	protected boolean problematic = false;
	
	/**
	 * What was the last error in this component?
	 */
	public String lastError;
	public String getLastError() {
		return lastError;
	}
	public void setLastError(String lastError) {
		this.lastError = lastError;
	}

	
	/**
	 * Event dispatched when a syntax error occour
	 */
	public String evOnSyntaxError;
	public String getEvOnSyntaxError() {
		return evOnSyntaxError;
	}
	public void setEvOnSyntaxError(String evOnSyntaxError) {
		this.evOnSyntaxError = evOnSyntaxError;
	}

	
	/**
	 * Implements just the dispatching of the onShow event
	 */
	public String show()
	throws Exception {
		dispatch("evOnShow");
		return "";
	} // end show
	
	/**
	 * Receive the request and rebuild the properties using
	 * the data that the user has filled in. This is a stub method
	 * @param request HttpServletRequest
	 */
	public void receiveRequest(HttpServletRequest request) {
		// your code here
	} // end receiveRequest
	
	/**
	 * Check the syntax for this component.
	 * This is a stub method.
	 * @throws SyntaxErrorException When the syntax is wrong.
	 */
	public void checkSyntax()
	throws SyntaxErrorException {
		// your code here
	} // end checkSyntax
	
	/** Return the string with the name="value" pairs
	 * to be used inside html tags, for the current component.
	 * <p>
	 * <b>NOTE:</b>
	 * Because class is a reserved word, the property
	 * <i>styleClass</i> is mapped to <i>class</i> here, and for the same
	 * reason, <i>charOn</i> is mapped to <i>char</i> and <i>forId</i> is
	 * mapped to <i>for</i>.<br>
	 * Because <i>accept-charset</i> is a invalid identifier, the property
	 * <i>acceptCharset</i> is mapped to <i>accept-charset</i> here.
	 * @see #styleClass
	 * @see com.oktiva.mogno.html.TableElement#charOn
	 * @see com.oktiva.mogno.html.Label#forId
	 * @see com.oktiva.mogno.html.Form#acceptCharset
	 * @see #buildHtmlAttributes(Vector)
	 * @return the string
	 */
	public String showHtmlAttributes() {
		Vector att = htmlAttributes();
		return buildHtmlAttributes(att);
	} // end printHtmlAttributes

	/** Return the string with the name="value" pairs
	 * to be used inside html tags.
	 * <p>
	 * <b>NOTE:</b>
	 * Because class is a reserved word, the property
	 * <i>styleClass</i> is mapped to <i>class</i> here, and for the same
	 * reason, <i>charOn</i> is mapped to <i>char</i> and <i>forId</i> is
	 * mapped to <i>for</i>.<br>
	 * Because <i>accept-charset</i> is a invalid identifier, the property
	 * <i>acceptCharset</i> is mapped to <i>accept-charset</i> here.
	 * @see #styleClass
	 * @see com.oktiva.mogno.html.TableElement#charOn
	 * @see com.oktiva.mogno.html.Label#forId
	 * @see com.oktiva.mogno.html.Form#acceptCharset
	 * @see #showHtmlAttributes()
	 * @return the string
	 */
	public String buildHtmlAttributes(Vector att) {
		String ret = "";
		for(int i = 0; i < att.size(); i ++) {
			// I will implement internationalization later.
			try {
				String fieldName = (String)att.get(i);
				Field field = this.getClass().getField(fieldName);
				String value = (String)field.get(this);
				if (value != null) {
					if (fieldName.equals("styleClass")) {
						fieldName = "class";
					} else if (fieldName.equals("charOn")) {
						fieldName = "char";
					} else if (fieldName.equals("forId")) {
						fieldName = "for";
					} else if (fieldName.equals("acceptCharset")) {
						fieldName = "accept-charset";
					}
					ret += " "+fieldName+"=\""+value+"\"";
				}
			} catch (IllegalAccessException ex) {
				logger.warn("Exception ignored on '"+this.getClass().getName()+"': "+ex.getClass().getName()+" Message:"+ex.getMessage());
			} catch (NoSuchFieldException ex) {
				logger.warn("Exception ignored on '"+this.getClass().getName()+"': "+ex.getClass().getName()+" Message:"+ex.getMessage());
			}
		}
		return ret;
	}
	
	/** Valid HTML attributes for this component.<br>
	 * All this attributes must be properties of the class.
	 */
	public Vector htmlAttributes() {
		return new Vector();
	}
	
	/** Return the core elements as defined by W3C.<br>
	 * To be pasted on classes that uses this attributes:<pre>
	public String id;
	public String styleClass;
	public String style;
	public String title;
	 * </pre>
	 */
	public Vector coreHtmlAttributes() {
		Vector v = new Vector();
		v.add("id");
		v.add("styleClass"); // mapped to class
		v.add("style");
		v.add("title");
		return v;
	}
	
	/** Return the internationalization (i18n) elements as defined by W3C.<br>
	 * To be pasted on classes that uses this attributes:<pre>
	public String lang;
	public String dir;
	 * </pre>
	 */
	public Vector i18nHtmlAttributes() {
		Vector v = new Vector();
		v.add("lang");
		v.add("dir");
		return v;
	}
	
	/** Return the events elements as defined by W3C.<br>
	 * To be pasted on classes that uses this attributes:<pre>
	public String onmouseup;
	public String onmousedown;
	public String onclick;
	public String ondblclick;
	public String onmouseover;
	public String onmousemove;
	public String onmouseout;
	public String onkeypress;
	public String onkeydown;
	public String onkeyup;
	 * </pre>
	 */
	public Vector eventsHtmlAttributes() {
		Vector v = new Vector();
		v.add("onclick");
		v.add("ondblclick");
		v.add("onmousedown");
		v.add("onmouseup");
		v.add("onmouseover");
		v.add("onmousemove");
		v.add("onmouseout");
		v.add("onkeypress");
		v.add("onkeydown");
		v.add("onkeyup");
		return v;
	}

	/** Return the cellhalign elements as defined by W3C.<br>
	 * To be pasted on classes that uses this attributes:<pre>
	public String align;
	public String charOn;
	public String charoff;
	 * </pre>
	 */
	public Vector cellhalignHtmlAttributes() {
		Vector v = new Vector();
		v.add("align");
		v.add("charOn");
		v.add("charoff");
		return v;
	}

	/** Return the cellvalign elements as defined by W3C.<br>
	 * To be pasted on classes that uses this attributes:<pre>
	public String valign;
	 * </pre>
	 */
	public Vector cellvalignHtmlAttributes() {
		Vector v = new Vector();
		v.add("valign");
		return v;
	}
	
	/**
	 * Attributes that will be localized.
	 */
	public Vector localizableAttributes() {
		return new Vector();
	}

	/** Verify if this component has an ancestor named <i>name</i>
	 * @param name The name of the ancestor component
	 * @return boolean true if it is a descendent of name
	 */	
	public boolean descendentOf(String name) {
		if(parent.equals(name)) {
			return true;
		}
		Component myParent = owner.getChild(parent);
		if (myParent == null) {
			return false;
		} else {
			return myParent.descendentOf(name);
		}
	}
} // end Visual
