/*
 * Application.java
 *
 * Created on 4 de Fevereiro de 2003, 17:31
 */

package com.oktiva.mogno;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * The main Mogno application class.
 * It's the "runnable" class, called from the servlet.
 * <pre>
 * import com.oktiva.mogno.*;
 * ...
 * Application myApp = new Application();
 * myApp.addTopLevel("formLogin", "com.oktiva.myapp.TopLevel1", "TopLevel1.xml");
 * myApp.addTopLevel("cadastroUsuario", "com.oktiva.myapp.TopLevel2", "TopLevel2.xml");
 * myApp.addTopLevel("formBusca", "com.oktiva.myapp.TopLevel3", "TopLevel3.xml");
 * myApp.setDefaultTopLevel("formLogin");
 * </pre>
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 */
public class Application implements Cloneable {
	static Logger logger = Logger.getLogger(Application.class.getName());
	protected HttpServletRequest request=null;
	protected HttpServletResponse response=null;
	protected HttpSession session=null;
	
	protected String defaultTopLevel = null;
	protected Hashtable topLevels = new Hashtable();
	/**
	 * Hashtable of Hashtables.  The keys are the TopLevels names and the 
	 * inner Hashtables has the keys <i>class</i> and <i>xml</i>.
	 */
	protected Hashtable topLevelsData = new Hashtable();
	
	protected ApplicationFiler applicationFiler = new DefaultApplicationFiler();
	protected Hashtable applicationFilerParams = new Hashtable();
	
	String rootDir = ".";

	protected boolean designing = false;
	
	/** Creates a new instance of Application */
	public Application() {
	}

	/**
	 * Initialize this application passing the XML file to load.
	 * @param xmlFileName The path to the XML file to load.
	 */
	public void initialize(String xmlFileName)
	throws IOException {
		initialize(new File(rootDir+"/WEB-INF/xml/"+xmlFileName));
	}

	/**
	 * Initialize this application passing the XML file to load.
	 * @param xmlFile The File whith XML to load.
	 * @see #initialize(String)
	 */
	public void initialize(File xmlFile)
	throws IOException {
		applicationFilerParams.put("xmlFile",xmlFile);
		initialize();
	}
	
	/**
	 * Initialize this application with a custom filer.
	 * You need to use setApplicationFiler and setApplicationFilerParams.
	 */
	public void initialize()
	throws IOException {
		applicationFiler.setParams(applicationFilerParams);
		Hashtable all = applicationFiler.load();
		topLevelsData = (Hashtable)all.get("topLevelsData");
		defaultTopLevel = (String)all.get("defaultTopLevel");
	} // end initialize
	
	/**
	 * Set a custom ApplicationFiler to use instead of the DefaultApplicationFiler.
	 * @param filer The ApplicationFiler to use.
	 * @see #setApplicationFilerParams(Hashtable)
	 * @see #initialize()
	 */
	public void setApplicationFiler(ApplicationFiler filer) {
		applicationFiler = filer;
	}
	
	/**
	 * Set the params for the custom ApplicationFiler.
	 * @param params The params to pass to the filer.
	 * @see #setApplicationFiler(ApplicationFiler)
	 * @see #initialize()
	 */
	public void setApplicationFilerParams(Hashtable params) {
		applicationFilerParams = params;
	}

	/**
	 * Store the current application into the file defined by the xmlFileName
	 * property
	 */
	public void store()
	throws IOException {
		ApplicationFiler applicationFiler = new DefaultApplicationFiler();
		applicationFiler.setParams(applicationFilerParams);
		applicationFiler.store(topLevelsData, defaultTopLevel);
	} // end store

	/** Add the TopLevel data supplied to the topLevelsData Hashtable.
	 * The XML file name defaults to <tt><i>name</i>.xml</tt>.
	 * @param name Name of the TopLevel.  Must be unique.
	 * @param topLevelClass Fully qualified name of the TopLevel class.
	 * @throws DuplicatedTopLevelException If there is already a TopLevel with the specified <tt>name</tt>.
	 */
	public void addTopLevel(String name, String topLevelClass)
	throws DuplicatedTopLevelException {
		addTopLevel(name,topLevelClass,name+".xml");
	}
	
	/** Add the TopLevel data supplied to the topLevelsData Hashtable.
	 * @param name Name of the TopLevel.  Must be unique.
	 * @param topLevelClass Fully qualified name of the TopLevel class.
	 * @param xmlFileName Name of the XML file for this TopLevel, without path.
	 * @throws DuplicatedTopLevelException If there is already a TopLevel with the specified <tt>name</tt>.
	 */
	public void addTopLevel(String name, String topLevelClass, String xmlFileName)
	throws DuplicatedTopLevelException {
		if (topLevelsData.containsKey(name)) {
			throw new DuplicatedTopLevelException("Top level "+name+" already added.");
		}
		Hashtable hash = new Hashtable();
		hash.put("class", topLevelClass);
		hash.put("xml", xmlFileName);
		topLevelsData.put(name,hash);
	}

	/** Remove from the application the TopLevel registered under the <i>name</i> supplied.
	 * Does not change the XML, you must call {@link #store()} if you want this.
	 * @param name Name of the TopLevel to remove.
	 * @throws TopLevelNotFoundException If the name is not the name of a registered TopLevel.
	 */
	public void removeTopLevel(String name)
	throws TopLevelNotFoundException {
		if (!topLevelsData.containsKey(name)) {
			throw new TopLevelNotFoundException("TopLevel unknown: "+name);
		}
		topLevelsData.remove(name);
		if (!topLevels.containsKey(name)) {
			topLevels.remove(name);
		}
	}
	
	/**
	 * Get the Hashtable with the data of this application's TopLevels.
	 * @see #topLevelsData
	 */
	public Hashtable getTopLevelsData() {
		return topLevelsData;
	}
	
	/** Set the TopLevel that will be shown when this application is called
	 * without <i>mognoOrigin</i> parameter.
	 * @param name Name of the default TopLevel.
	 * @throws TopLevelNotFoundException If the name is not the name of a registered TopLevel.
	 */
	public void setDefaultTopLevel(String name)
	throws TopLevelNotFoundException {
		if (!topLevelsData.containsKey(name)) {
			throw new TopLevelNotFoundException("TopLevel unknown: "+name);
		}
		defaultTopLevel=name;
	}

	/**
	 * @return The name of the default TopLevel.
	 */
	public String getDefaultTopLevel() {
		return defaultTopLevel;
	}
	
	/** Get the TopLevel registered under the <i>name</i> supplied.
	 * @param name Name of the TopLevel to get.
	 * @return The TopLevel associated with the <i>name</i> or <b>null</b> if the <i>name</i> is not found.
	 * @throws ClassNotFoundException If the class of the TopLevel can not be found in the CLASSPATH.
	 * @throws InstantiationException If the reflection system can't instantiate the TopLevel's class.
	 * @throws IllegalAccessException If the class or it's constructor is not acessible.
	 * @throws InitializeException If the initialization proccess of the TopLevel fails.
	 */
	public TopLevel getTopLevel(String name)
	throws ClassNotFoundException, InstantiationException, IllegalAccessException, InitializeException, TopLevelNotFoundException {
		if (!topLevelsData.containsKey(name)) {
			throw new TopLevelNotFoundException("Unknown top level: "+name);
		}
		TopLevel tl = null;
		if (topLevels.containsKey(name)) {
			tl = (TopLevel)topLevels.get(name);
		} else {
			String className = getTopLevelClassname(name);
			String xmlFileName = rootDir+"/WEB-INF/xml/"+getTopLevelXmlFileName(name);
			tl = (TopLevel)Class.forName(className).newInstance();
			tl.setApplication(this);
			tl.setDesigning(designing);
			tl.initialize(xmlFileName);
			topLevels.put(name, tl);
		}
		return tl;
	}
	/** Set the root dir for this application.
	 */
	public void setRootDir(String dir) {
		rootDir = dir;
	}
	/** Get the root dir for this application.
	 */
	public String getRootDir() {
		return rootDir;
	}
	
	public String getTopLevelClassname(String topLevelName)
	throws TopLevelNotFoundException {
		if (!topLevelsData.containsKey(topLevelName)) {
			throw new TopLevelNotFoundException("Unknown top level: "+topLevelName);
		}
		return (String)((Hashtable)topLevelsData.get(topLevelName)).get("class");
	}
	public String getTopLevelXmlFileName(String topLevelName)
	throws TopLevelNotFoundException {
		if (!topLevelsData.containsKey(topLevelName)) {
			throw new TopLevelNotFoundException("Unknown top level: "+topLevelName);
		}
		return (String)((Hashtable)topLevelsData.get(topLevelName)).get("xml");
	}
	
	/**
	 * Run this application, with the data in the request.
	 */
	public void run(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		this.request=request;
		this.response=response;
		String origin = request.getParameter("mognoOrigin");
		try {
			if (origin != null && !origin.equals("")) {
				TopLevel tl = getTopLevel(origin);
				tl.message(request, response);
			} else {
				TopLevel tl = getTopLevel(defaultTopLevel);
				outHtml(tl.show());
			}
		} catch (Throwable e) {
			emergency(e);
		}
	}
	/** Prints the stack trace for an exception to the user, in <tt>text/plain</tt> format.
	 */
	public void emergency(Throwable e)
	throws IOException {
		logger.error("Emergency: "+e.getClass().getName()+" - msg:"+e.getMessage());
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.println("FATAL EXCEPTION: "+e.getClass().getName());
		out.println("Message: "+e.getMessage());
		out.println("------------------------------\nStack trace:\n");
		e.printStackTrace(out);
		out.println("------------------------------\nParameter values:\n");
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()) {
			String name = (String)en.nextElement();
			String value = request.getParameter(name);
			out.println(name+"="+value);
		}
		out.close();
	}
	/**
	 * Outputs the string received as text/html content to the user.
	 */
	public void outHtml(String html)
	throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println(html);
	}
	
	/**
	 * Outputs the string received without setting any header. You must get and configure the
	 * response object to do what you want.
	 */
	public void out(String str)
	throws IOException {
		PrintWriter out = response.getWriter();
		out.print(str);
	}

	/** 
	 * @return the servlet request passed to run()
	 */
	public HttpServletRequest getServletRequest() {
		return request;
	}
	
	/** 
	 * @return the servlet response passed to run()
	 */
	public HttpServletResponse getServletResponse() {
		return response;
	}

	/** 
	 * @return the HTTP Session
	 */
	public HttpSession getSession() {
		if (session == null) {
			session = request.getSession();
		}
		return session;
	}
	
	public Application getClone() {
		Application app = new Application();
		app.setRootDir(rootDir);
		try {
			Enumeration e = topLevelsData.keys();
			while(e.hasMoreElements()) {
				String name = (String)e.nextElement();
				Hashtable data = (Hashtable)topLevelsData.get(name);
				app.addTopLevel(name, (String)data.get("class"), (String)data.get("xml"));
			}
			app.setDefaultTopLevel(defaultTopLevel);
		} catch (TopLevelNotFoundException ex) {
			// safely ignore
			logger.debug("Ignoring exception: "+ex.getClass().getName()+" - msg:"+ex.getMessage());
		} catch (DuplicatedTopLevelException ex) {
			// safely ignore
			logger.debug("Ignoring exception: "+ex.getClass().getName()+" - msg:"+ex.getMessage());
		}
		return app;
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
}

/** Mogno Application internal exception.
 */
class TopLevelNotFoundException extends Exception {
	public TopLevelNotFoundException() {
		super();
	}
	public TopLevelNotFoundException(String message) {
		super(message);
	}
}

/** Mogno Application internal exception.
 */
class DuplicatedTopLevelException extends Exception {
	public DuplicatedTopLevelException() {
		super();
	}
	public DuplicatedTopLevelException(String message) {
		super(message);
	}
}
