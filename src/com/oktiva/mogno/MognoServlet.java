/*
 * MognoServlet.java
 *
 * Created on 4 de Fevereiro de 2003, 20:49
 */
package com.oktiva.mogno;

import com.oktiva.mogno.Application;
import java.io.*;
import java.net.*;
import java.util.Hashtable;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author Itamar Carvalho &ltitamar@oktiva.com.br&gt
 * @version $Id$
 */
public class MognoServlet extends HttpServlet {
	Application app = null;
	static Logger logger = Logger.getLogger(MognoServlet.class.getName());
	/** Initializes the servlet.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String rootDir = config.getServletContext().getRealPath("/");
		initLogging(rootDir, config);
		initApplication(rootDir);
	}
	
	/** Initializes the Mogno Aplication.
	 */
	protected void initApplication(String rootDir)
	throws ServletException {
		try {
			if (app == null) {
				app = new Application();
				app.setRootDir(rootDir);
				app.initialize("MognoApplication.xml");
			}
		} catch (IOException e) {
			throw new ServletException(e);
		}
	}
	
	/** Initializes the Log4j environment.
	 */
	public void initLogging(String rootDir, ServletConfig config) {
		String configDir = config.getServletContext().getInitParameter("configDir");
		String file = config.getInitParameter("log4jInitFile");
		// if the log4j-init-file is not set, then no point in trying
		if(file != null) {
			String confFile = rootDir+configDir+file;
			String bar = File.separator;
			String logFile = rootDir+bar+"WEB-INF"+bar+"logs"+bar+"mogno.log";
			System.setProperty("logFile",logFile);
			DOMConfigurator.configure(confFile);
			logger.info("Log4j configured using init file '"+confFile+"' and log file '"+logFile+"'.");
		} else {
			System.out.println("Log4j initialization failed.");
		}
	}
	/** Destroys the servlet.
	 */
	public void destroy() {
	}
	
	/** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		app.getClone().run(request, response);
	}
	/** Handles the HTTP <code>GET</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);
	}
	
	/** Handles the HTTP <code>POST</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpServletRequest req = MognoServletRequestWrapper.getHttpServletRequest(request);
		processRequest(req, response);
	}
	
	/** Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Mogno-based Application Servlet";
	}
}
