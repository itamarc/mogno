/*
 * XmlContainer.java
 *
 * Created on 14 de Agosto de 2003, 19:11
 */
package com.oktiva.mogno;

import com.oktiva.mogno.Component;
import com.oktiva.mogno.Container;
import com.oktiva.mogno.InitializeException;
import com.oktiva.mogno.Visual;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/** The superclass for containers that knows how to get the XML from some resource, with nice defaults.
 */
public abstract class XmlContainer extends Container {
	/** Event dispatched when some exception is not caught in the
	 * inner components.
	 */
	public String evOnUncaughtError = "";
	
	/** Initializes this components.  Changes the childs names from the XML for
	 * <tt>[thisComponentName]__[originalChildName]</tt>
	 * @param params This instance's attributes.
	 * @throws InitializeException If the resource is not found or the initialization fails.
	 */
	public void initialize(Hashtable params)
	throws InitializeException {
		
		if (designing) {
			componentFilerParams.put("designing", "true");
		}
		componentFiler.setParams(componentFilerParams);
		InputStream in = getXmlInputStream();
		if (in == null) {
			throw new InitializeException("Resource not found!");
		}
		Hashtable all;
		try {
			all = componentFiler.load(in);
		} catch (java.io.IOException e) {
			throw new InitializeException(e);
		}
		Hashtable mine = (Hashtable)all.get("mine");
		Hashtable owned = (Hashtable)all.get("owned");
		
		mine.putAll(params);
		
		setName((String)mine.get("name"));
		
		Enumeration enum = owned.keys();
		while (enum.hasMoreElements()) {
			Hashtable hash = (Hashtable)owned.get(enum.nextElement());
			if ("__OWNER__".equals(hash.get("parent"))) {
				hash.put("parent",getName());
			} else {
				hash.put("parent",getName()+"__"+hash.get("parent"));
			}
			hash.put("name",getName()+"__"+hash.get("name"));
		}
		
		setProperties(mine);
		createOwnedComponents(owned);
		try {
			dispatch("evOnCreate");
		} catch (Exception ex) {
			throw new InitializeException(ex);
		}		
	}

	/** Convenience method equivalent to getChild.  Do the name translation
	 * automatically.
	 * @see #initialize(Hashtable)
	 * @see #getChild(String)
	 */
	public Component getChildComponent(String name) {
		return super.getChild(getName()+"__"+name);
	}
	
	/** Convenience method equivalent to getChildClass.  Do the name translation
	 * automatically.
	 * @see #initialize(Hashtable)
	 * @see #getChildClass(String)
	 */
	public Class getChildComponentClass(String name) {
		return super.getChildClass(getName()+"__"+name);
	}
	
	/** Convenience method equivalent to freeChild.  Do the name translation
	 * automatically.
	 * @see #initialize(Hashtable)
	 * @see #freeChild(String)
	 */
	public void freeChildComponent(String name) {
		super.freeChild(getName()+"__"+name);
	}
	
	/** Obtain a InputStream from wich we will get the XML.
	 * For default, it gets it from the class resource using the name from <code>getXmlResourceName()</code>
	 * @see #getXmlResourceName()
	 */
	protected InputStream getXmlInputStream() {
		return getClass().getResourceAsStream(getXmlResourceName());
	}
	
	/** Gets the ResourceName that will be used at <code>getXmlResourceName()</code>
	 */
	protected String getXmlResourceName() {
		return getClass().getName().replaceAll("^.+\\.","")+".xml";
	}
	
	public Vector nonAttributeGetters() {
		Vector v = super.nonAttributeGetters();
		v.add("getXmlResourceName");
		v.add("getXmlInputStream");
		return v;
	}

	protected Component selectParentComponent() {
		return this;
	}
	
	/** Receive the request and rebuild the properties using the data that the user has filled in.
	 * @param request The HttpServletRequest object.
	 */	
	public void receiveRequest(HttpServletRequest request) {
		// See if there is a serialized bag in this submit
		String serialized = request.getParameter("mognoSerializedBag_"+name);
		if (serialized != null && !"___Unserializable___".equals(serialized) && !"".equals(serialized)) {
			unserializeBag(serialized);
		}
		onReceiveRequest();
		// Saying to my children to receive the request
		Enumeration enum = listChilds();
		while (enum.hasMoreElements()) {
			Component c = getChild((String)enum.nextElement());
			if (c instanceof Visual) {
				((Visual)c).receiveRequest(request);
			}
		}
	}
	
	/** Dispatch all the events in the event queue in all child components
	 * and in this one.
	 */
	public void dispatchAll()
	throws Exception {
		try {
			Enumeration e = listChilds();
			while (e.hasMoreElements()) {
				Component component = (Component)getChild((String)e.nextElement());
				if (component != null) {
					if (component instanceof Visual) {
						Visual visual = (Visual)component;
						visual.dispatchAll();
					}
				}
			}
		} catch (Exception ex) {
			if (evOnUncaughtError == null || evOnUncaughtError.equals("")) {
				throw ex;
			} else {
				dispatch("evOnUncaughtError");
			}
		}
		super.dispatchAll();
	}
	
	/** Event dispatched when some exception is not caught in the
	 * inner components.
	 */
	public String getEvOnUncaughtError() {
		return evOnUncaughtError;
	}
	/** Event dispatched when some exception is not caught in the
	 * inner components.
	 */
	public void setEvOnUncaughtError(String evOnUncaughtError) {
		this.evOnUncaughtError = evOnUncaughtError;
	}

	/** Check the syntax for this component.<p>
	 * For default, call checkSyntax of the components listed in
	 * <code>checkSyntaxFields()</code>.
	 * @throws SyntaxErrorException if any of the components don't pass the check.
	 */
	public void checkSyntax()
	throws SyntaxErrorException {
		checkSyntax(checkSyntaxFields());
	}
	
	/** List the components that need to be checked for syntax errors.<p>
	 * The default value is the list of all children of this component.
	 * @return A Vector with the name of the components to be tested.
	 */
	protected Vector checkSyntaxFields() {
		Vector v = new Vector();
		Enumeration enum = listChilds();
		while (enum.hasMoreElements()) {
			v.add(enum.nextElement());
		}
		return v;
	}
	
	/** This function is overrided to check the syntax of owned components.
	 * @param fields A Vector with the name of the components to be tested
	 * @throws SyntaxErrorException if any of the components don't pass the check.
	 */
	protected void checkSyntax(Vector fields)
	throws SyntaxErrorException {
		boolean err = false;
		for(int i=0;i<fields.size();i++) {
			Component component = getChild((String)fields.get(i));
			if (component instanceof Visual) {
				Visual visual = (Visual)component;
				try {
					visual.checkSyntax();
				} catch (SyntaxErrorException ex) {
					err = true;
					visual.problematic = true;
					visual.lastError = "syntaxError";
					try {
						visual.dispatch("evOnSyntaxError");
					} catch (Exception exc) {
						throw new SyntaxErrorException(exc.getMessage());
					}
				}
			}
		}
		if (err) {
			throw new SyntaxErrorException();
		}
	}

	/** Write down my serialized bag if there is elements there, then call <code><b>super</b>.show();</code>
	 */
	public String show()
	throws Exception {
		StringBuffer buf = new StringBuffer();
		if (bag != null && bag.size()>0) {
			buf.append("<INPUT TYPE='hidden' NAME='mognoSerializedBag_").append(name);
			buf.append("' VALUE='").append(serializedBag()).append("'>\n");
		}
		buf.append(super.show());
		return buf.toString();
	}
	
	/** Event dispatched when the request is received.
	 */
	public void onReceiveRequest() {
	}
}
