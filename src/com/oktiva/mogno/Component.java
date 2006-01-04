package com.oktiva.mogno;

import java.io.File;
import java.lang.reflect.*;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.IllegalAccessException;
import java.lang.IllegalArgumentException;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * This is the base class for all components.
 * It implements the knowledge for owning another components and to
 * save and load its data.
 *
 * @version $Id$
 */
public class Component implements Cloneable {
	static Logger logger = Logger.getLogger(Component.class.getName());
	/**
	 * This variable is used when loading the component.
	 * Specifies the location of the XML file with the data
	 * for this component.
	 */
	protected String xmlFileName;
	
	/**
	 * The name of the component, used to identify an owned
	 * component inside its owner.
	 */
	public String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Method to be called when the onCreate event is dispatched.
	 */
	public String evOnCreate;
	public String getEvOnCreate() {
		return evOnCreate;
	}
	public void setEvOnCreate(String evOnCreate) {
		this.evOnCreate = evOnCreate;
	}
	
	
	/**
	 * The owner of this component. The component that owns other
	 * components is always the listener for its owned component's
	 * events.
	 */
	protected Component owner;
	
	/**
	 * Controls the behavior of the component. If desiging, no event will
	 * be dispatched.
	 */
	protected boolean designing = false;
	
	/**
	 * A hash table with all the owned components.
	 * The owned components are that registered inside this one, that has
	 * this one as his listener for the events.  It has no relation with
	 * the <i>parent</i> attribute.
	 */
	protected Hashtable owned = new Hashtable();
	
	/**
	 * A hash table with all the owned component's classes .
	 */
	protected Hashtable ownedClasses = new Hashtable();
	
	protected String tag = null;
	/**
	 * Queue of events
	 */
	protected Vector eventQueue = new Vector();
	
	protected ComponentFiler componentFiler = new DefaultComponentFiler();
	protected Hashtable componentFilerParams = new Hashtable();
	
	/** A bag with extra data for the component.
	 *
	 * @see #setBag(Hashtable)
	 * @see #getBag()
	 * @see #getFromBag(Object)
	 * @see #putInBag(Object,Object)
	 */
	protected Hashtable bag = new Hashtable();
	
	/**
	 * Initialize this component passing the properties. Used
	 * when creating components at run-time.
	 * @param params A key-value hashtable with the properties.
	 * @see #initialize()
	 */
	public void initialize(Hashtable params)
	throws InitializeException {
		setProperties(params);
		try {
			dispatch("evOnCreate");
		} catch (Exception ex) {
			throw new InitializeException(ex);
		}
	} // end initialize
	
	/**
	 * Initialize this component passing the XML file to load.
	 * @param xmlFileName The path to the XML file to load.
	 * @see #initialize()
	 */
	public void initialize(String xmlFileName)
	throws InitializeException {
		this.xmlFileName = xmlFileName;
		componentFilerParams.put("xmlFile",new File(xmlFileName));
		initialize();
	}
	
	/**
	 * Initialize this component with a InputStream.
	 * If using DefaultComponentFiler, the component is read-only, can't perform a store().
	 * Useful for components with this own XML to read from a resource other then a file.
	 * @see #initialize()
	 */
	public void initialize(InputStream in)
	throws InitializeException {
		try {
			if (designing) {
				componentFilerParams.put("designing", "true");
			}
			componentFiler.setParams(componentFilerParams);
			Hashtable all = componentFiler.load(in);
			Hashtable mine = (Hashtable)all.get("mine");
			Hashtable owned = (Hashtable)all.get("owned");
			setProperties(mine);
			createOwnedComponents(owned);
			dispatch("evOnCreate");
		} catch (Exception ex) {
			throw new InitializeException(ex);
		}
	} // end initialize
	
	/**
	 * Initialize this component with a custom filer.
	 * You need to use setComponentFiler and setComponentFilerParams,
	 * if you want to use something different from DefaultComponentFiler.
	 * @see #initialize(String)
	 * @see #initialize(Hashtable)
	 * @see #initialize(InputStream)
	 * @see DefaultComponentFiler
	 */
	public void initialize()
	throws InitializeException {
		try {
			if (designing) {
				componentFilerParams.put("designing", "true");
			}
			componentFiler.setParams(componentFilerParams);
			Hashtable all = componentFiler.load();
			Hashtable mine = (Hashtable)all.get("mine");
			Hashtable owned = (Hashtable)all.get("owned");
			setProperties(mine);
			createOwnedComponents(owned);
			dispatch("evOnCreate");
		} catch (Exception ex) {
			throw new InitializeException(ex);
		}
	} // end initialize
	
	public void setProperties(Hashtable mine) {
		Enumeration keys = mine.keys();
		Hashtable h=getAttrsTypes();
		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			if (key.equals("__CLASSNAME__")) {
				continue;
			}
			try{
				char[] ch = key.toCharArray();
				ch[0] = Character.toUpperCase(ch[0]);
				String atr = new String(ch);
				Class[] attrs = new Class[1];
				attrs[0] = (Class)h.get(atr);
				Method met = this.getClass().getMethod("set"+atr, attrs);
				if(met==null) {
					logger.warn(this.getClass().getName()+": Error setting property '"+key+"', there is no setter");
					continue;
				}
				Object[] args = new Object[1];
				
				if("int".equals(attrs[0].getName())) {
					String value = (String)mine.get(key);
					if(value == null || "".equals(value)) {
						value = "0";
					}
					args[0]=new Integer(value);
				} else if("long".equals(attrs[0].getName())) {
					String value = (String)mine.get(key);
					if(value == null || "".equals(value)) {
						value = "0";
					}
					args[0]=new Long(value);
				} else if ("boolean".equals(attrs[0].getName())) {
					args[0]=(Object)new Boolean((String)mine.get(key));
				} else if("java.util.Vector".equals(attrs[0].getName())) {
					Vector v = new Vector();
					Object o = mine.get(key);
					if (o instanceof Vector) {
						v.addAll((Vector)o);
					} else if (o instanceof String) {
						String val = (String)o;
						val = val.replaceAll("\\A\\[\"?", "");
						val = val.replaceAll("\"?\\]\\Z", "");
						String[] vals = val.split("\",\"");
						for(int i=0; i<vals.length; i++) {
							v.add(vals[i]);
						}
						args[0]=v;
					}
				} else {
					Class []constructorArgsClasses={String.class};
					Constructor constr=attrs[0].getConstructor(constructorArgsClasses);
					Object []constructorArgs={mine.get(key)};
					args[0]=constr.newInstance(constructorArgs);
				}
				met.invoke(this, args);
			} catch (Exception ex) {
				logger.warn("Error setting property '"+key+"': "+ex.getClass().getName()+" - "+ex.getMessage());
				logger.debug("Stack trace: "+ex);
				ex.printStackTrace(System.err);
			}
		}
	}
	
	/** Get the Class of the returns of the getters. This is usefull in finding the setters.
	 * @return Hashtable with its key being the name of the atribute with the first letter upercase,
	 * and the value being the class of the return type.
	 */
	public Hashtable getAttrsTypes() {
		Hashtable h = new Hashtable();
		Method[] m = this.getClass().getMethods();
		Vector v = nonAttributeGetters();
		for(int i = 0; i < m.length; i++) {
			String nomeMetodo = m[i].getName();
			if(m[i].getParameterTypes().length!=0 ||
				v.contains(nomeMetodo)) {
				continue;
			}
			if(nomeMetodo.startsWith("get") || nomeMetodo.startsWith("is")) {
				nomeMetodo = nomeMetodo.replaceFirst("get|is", "");
				h.put(nomeMetodo,m[i].getReturnType());
			}
		}
		return h;
	}
	
	/** Method used to define what methods started with "get" or "is" are not
	 * component attributes getter methods.<br>
	 * This method in all subclasses of Component must start with the following line:<br>
	 * <code>Vector v = super.nonAttributeGetters();</code><br>
	 * and end with the following:<br>
	 * <code>return v;</code>
	 * @return Vector with the name of the methods that starts with "get" or "is",
	 * but shoud not be used to set or get attributes automatically for this component.
	 * @see #setProperties(Hashtable)
	 * @see #attribsHash()
	 * @see #store()
	 */
	public Vector nonAttributeGetters() {
		Vector v = new Vector();
		v.add("isDesigning");
		v.add("getClone");
		v.add("getClass");
		v.add("getAttrsTypes");
		v.add("getBag");
		v.add("getChild");
		v.add("getChildClass");
		v.add("getFromBag");
		v.add("getApplication");
		v.add("getOrderedChildNames");
		return v;
	}
	
	protected void createOwnedComponents(Hashtable owned)
	throws InitializeException {
		Enumeration keys = owned.keys();
		while (keys.hasMoreElements()) {
			try {
				String name = (String)keys.nextElement();
				logger.debug("Creating '"+this.name+"' owned element '"+name+"'");
				Class ownedClass = Class.forName((String)((Hashtable)owned.get(name)).get("__CLASSNAME__"));
				Component comp = (Component)ownedClass.newInstance();
				comp.setDesigning(isDesigning());
				comp.initialize((Hashtable)owned.get(name));
				registerChild(comp);
			} catch (ClassNotFoundException ex) {
				logger.warn("Exception ignored: "+ex.getClass().getName()+" Message:"+ex.getMessage());
			} catch (InstantiationException ex) {
				logger.warn("Exception ignored: "+ex.getClass().getName()+" Message:"+ex.getMessage());
			} catch (IllegalAccessException ex) {
				logger.warn("Exception ignored: "+ex.getClass().getName()+" Message:"+ex.getMessage());
			}
		}
	}
	
	/**
	 * Set a custom ComponentFiler to use instead of the DefaultComponentFiler.
	 * @param filer The ComponentFiler to use.
	 * @see #setComponentFilerParams(Hashtable)
	 * @see #initialize()
	 */
	public void setComponentFiler(ComponentFiler filer) {
		componentFiler = filer;
	}
	
	/**
	 * Set the params for the custom ComponentFiler.
	 * @param params The params to pass to the filer.
	 * @see #setComponentFiler(ComponentFiler)
	 * @see #initialize()
	 */
	public void setComponentFilerParams(Hashtable params) {
		componentFilerParams = params;
	}
	
	/**
	 * Store the current component into the file defined by the xmlFileName
	 * property
	 */
	public void store()
	throws IOException {
		Hashtable mineProps = propsHash(this);
		Vector ownedProps = new Vector();
		Vector v = getOrderedChildNames();
		for (int i=0; i<v.size(); i++) {
			String name = (String)v.get(i);
			Hashtable props = propsHash((Component)owned.get(name));
			props.put("__CLASSNAME__",owned.get(name).getClass().getName());
			ownedProps.add(props);
		}
		ComponentFiler componentFiler = new DefaultComponentFiler();
		Hashtable param = new Hashtable();
		param.put("xmlFile",new File(xmlFileName));
		componentFiler.setParams(param);
		componentFiler.store(mineProps,ownedProps);
	} // end store
	
	// for store()
	protected Vector getOrderedChildNames() {
		Vector v = new Vector();
		Object[] keys = owned.keySet().toArray();
		Arrays.sort(keys);
		for (int i=0; i<keys.length; i++) {
			v.add(((Component)owned.get(keys[i])).name);
		}
		return v;
	}
	
	/**
	 * @param c com.oktiva.mogno.Component
	 * @return Hashtable with all properties of Component c
	 */
	private Hashtable propsHash(Component c) {
		Hashtable props=c.getAttrsTypes();
		Hashtable hash = new Hashtable();
		Enumeration e=props.keys();
		
		Class args[]={ }; /* empty args array */
		
		while(e.hasMoreElements()) {
			String mname=(String)e.nextElement();
			char[] cha=mname.toCharArray();
			cha[0]=Character.toLowerCase(cha[0]);
			String key=new String(cha);
			
			Method met=null;
			try {
				met=c.getClass().getMethod("get"+mname,args);
			} catch (NoSuchMethodException ne) {
				met=null;
				try {
					met=c.getClass().getMethod("is"+mname,args);
				} catch (NoSuchMethodException ee) {
					met=null;
				}
			}
			Object ret=null;
			if(met!=null) {
				try {
					ret=met.invoke(c, args);
					if(ret!=null) {
						hash.put(key, ret.toString());
					}
				} catch (IllegalAccessException ex) {
					/* do something here */
					//System.out.println("Exception ignored: "+ex.getClass().getName()+" (name="+key+", value="+ret+") Message:"+ex.getMessage());
					logger.info("Exception ignored: "+ex.getClass().getName()+" (name="+key+", value="+ret+") Message:"+ex.getMessage());
				} catch (IllegalArgumentException ex) {
					/* do something here */
					//System.out.println("Exception ignored: "+ex.getClass().getName()+" (name="+key+", value="+ret+") Message:"+ex.getMessage());
					logger.info("Exception ignored: "+ex.getClass().getName()+" (name="+key+", value="+ret+") Message:"+ex.getMessage());
				} catch (InvocationTargetException ex) {
					/* do something here */
					//System.out.println("Exception ignored: "+ex.getClass().getName()+" (name="+key+", value="+ret+") Message:"+ex.getMessage());
					logger.info("Exception ignored: "+ex.getClass().getName()+" (name="+key+", value="+ret+") Message:"+ex.getMessage());
				}
			} else {
				logger.info("Didn't find method named: (is|get)"+mname);
			}
		}
		return hash;
	}
	
	/** Return all this component's attributes in a hash.
	 * @return Hashtable with this component's attributes and this values as Strings.
	 */
	public Hashtable attribsHash() {
		return propsHash(this);
	}
	
	/**
	 * Register a component as owned of this component
	 * @param obj The object itself
	 */
	public void registerChild(Component obj) {
		ownedClasses.put(obj.name,obj.getClass());
		owned.put(obj.name,obj);
		obj.setDesigning(designing);
		obj.setOwner(this);
	} // end registerChild
	
	/**
	 * Removes the component
	 * @param name The name of the component
	 */
	public void freeChild(String name) {
		owned.remove(name);
		ownedClasses.remove(name);
	} // end freeChild
	
	/**
	 * Get the component with name "name"
	 * @param name the name of the component to get
	 * @return the component. You must typecast it.
	 */
	public Component getChild(String name) {
		if (owned.containsKey(name)) {
			return (Component)owned.get(name);
		} else {
			return null;
		}
	} // end getChild
	
	/**
	 * Get the class of the component with name "name"
	 * @param name The name of the component to get
	 * @return The class of the component or <b>null</b> if a child with this name is not found.
	 */
	public Class getChildClass(String name) {
		if (owned.containsKey(name)) {
			return (Class)ownedClasses.get(name);
		} else {
			return null;
		}
	}
	
	/**
	 * @return An enumeration with the name of the owned children of this component.
	 */
	public Enumeration listChilds() {
		return owned.keys();
	}
	
	/**
	 * Dispatch an event from this object.
	 * Does nothing if the component is designing.
	 * @param eventName
	 * @see #designing
	 */
	public void dispatch(String eventName)
	throws Exception {
		if (designing) {
			return;
		}
		try {
			Field f = this.getClass().getField(eventName);
			String m = (String)f.get(this);
			if (m == null || m.equals("")) {
				return;
			} else {
				Class[] paramTypes = eventParamTypes(eventName);
				Object[] values = eventParamValues(eventName);
				dispatchFunction(m,paramTypes,values);
			}
		} catch (InvocationTargetException ex) {
			// Como isto faz o stacktrace se perder,
			// vou fazer printstacktrace...
			ex.getCause().printStackTrace();
			if (ex.getCause() instanceof Exception) {
				throw (Exception)ex.getCause();
			} else {
				throw new Exception(ex.getCause().getClass().getName()+": "+ex.getCause().getMessage(), ex.getCause());
			}
		} catch (NoSuchMethodException ex) {
			logger.warn("Exception ignored: "+ex.getClass().getName()+" Message:"+ex.getMessage());
		} catch (IllegalAccessException ex) {
			logger.warn("Exception ignored: "+ex.getClass().getName()+" Message:"+ex.getMessage());
		} catch (NoSuchFieldException ex) {
			logger.warn("Exception ignored: "+ex.getClass().getName()+" Message:"+ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw e;
		}
	} // end dispatch
	
	public Class[] eventParamTypes(String ev) {
		Class[] ret = {this.getClass()};
		return ret;
	}
	
	public Object[] eventParamValues(String ev) {
		Object[] ret = {this};
		return ret;
	}
	
	public void dispatchFunction(String function, Class[] paramTypes, Object[] values)
	throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
		Class c = null;
		Object o = null;
		if (owner == null) {
			c = this.getClass();
			o = this;
		} else {
			c = owner.getClass();
			o = owner;
		}
		Method m = c.getMethod(function,paramTypes);
		m.invoke(o,values);
	}
	/**
	 * queue an event
	 * @param ev the name of the event
	 */
	public void queue(String ev) {
		eventQueue.add(ev);
	}
	
	/**
	 * dispatch all the events in the event queue
	 */
	public void dispatchAll()
	throws Exception {
		while(eventQueue.size() > 0) {
			String ev = (String)eventQueue.remove(0);
			dispatch(ev);
		}
	}
	
	/** Get the Application object from the TopLevel.
	 * @see TopLevel#getApplication()
	 */
	public Application getApplication() {
		logger.debug("Getting application from "+this.name);
		return owner.getApplication();
	}
	
	public Component getClone()
	throws CloneNotSupportedException {
		return (Component)this.clone();
	}
	
	/** Getter for property designing.
	 * @return Value of property designing.
	 * @see #designing
	 */
	public boolean isDesigning() {
		return designing;
	}
	
	/** Setter for property designing.
	 * @param designing New value of property designing.
	 * @see #designing
	 */
	public void setDesigning(boolean designing) {
		this.designing = designing;
	}
	
	/** Getter for property bag.
	 * @return The bag of data.
	 * @see #bag
	 * @see #setBag(Hashtable)
	 */
	public Hashtable getBag() {
		return bag;
	}
	
	/** Setter for property bag.
	 * @param bag The new bag to overwrite the current one.
	 * @see #bag
	 * @see #getBag()
	 */
	public void setBag(Hashtable bag) {
		this.bag = bag;
	}
	
	/** Put the object in this component's bag.
	 * @param key The key.
	 * @param value The object to put.
	 * @see #bag
	 * @see #getFromBag(Object)
	 */
	public void putInBag(Object key, Object value) {
		bag.put(key, value);
	}
	
	/** Put the object in this component's bag.
	 * @param key The key.
	 * @return The object in the bag.
	 * @see #bag
	 * @see #putInBag(Object,Object)
	 */
	public Object getFromBag(Object key) {
		return bag.get(key);
	}
	
	/** Remove the object from this component's bag.
	 * @param key The key.
	 * @return The value in which the key had been mapped in the bag or <tt>null</tt> if the key did not have a mapping.
	 * @see #bag
	 * @see #putInBag(Object,Object)
	 */
	public Object removeFromBag(Object key) {
		return bag.remove(key);
	}
	
	/** Verify if this component has an ancestor named <i>name</i><br>
	 * <b>In Component, this is always false, because <i>there is no parent</i>.</b>
	 * @param name The name of the ancestor component
	 * @return boolean always false
	 */
	public boolean descendentOf(String name) {
		return false;
	}
	
	/** Getter for property owner.
	 * @return Value of property owner.
	 */
	protected Component getOwner() {
		return owner;
	}
	
	/** Setter for property owner.
	 * @param owner New value of property owner.
	 */
	protected void setOwner(Component owner) {
		this.owner = owner;
	}
	
	public String serializedBag() {
		Hashtable serializable = new Hashtable();
		if (getBag() != null) {
			serializable = getBag();
		} else {
			return "___Unserializable___";
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream objout = new ObjectOutputStream(out);
			objout.writeObject(serializable);
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
	
	protected void unserializeBag(String data) {
		try {
			String[] strbytes = data.split("%");
			byte[] bytes = new byte[strbytes.length];
			for (int i = 0; i < strbytes.length; i++) {
				bytes[i] = (byte)(Integer.parseInt(strbytes[i]));
			}
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream objin = new ObjectInputStream(in);
			setBag((Hashtable)objin.readObject());
		} catch (Exception e) {
			logger.warn("Error while unserializing bag... "+e);
		}
	}
	
	public String htmlize(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		return str;
	}
	
} // end Component
