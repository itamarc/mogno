package com.oktiva.mogno;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.*;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.ServletRequest;
import java.util.Iterator;
import java.util.Vector;
import java.util.List;

/**
 * Wrapper to handle multipart/form-data requests
 *
 * Created: Tue Sep  9 17:03:44 2003
 *
 * @author oktiva
 * @version 1.0
 */
public class MognoServletRequestWrapper extends HttpServletRequestWrapper {

	private Hashtable parameters;
	private Hashtable fileItems;

	/**
	 * Use this method to create the wrapper only if it is needed to. It will return the original object
	 * if method is not post or if content type is not multipart/form-data
	 * @param req Original request object
	 * @return a safe HttpServletRequest object
	 * @exception IllegalArgumentException if an error occours while trying to create the wrapper object
	 */
	public static HttpServletRequest getHttpServletRequest(HttpServletRequest req) throws IllegalArgumentException {
		if (isMultiPart(req)) {
			return new MognoServletRequestWrapper(req);
		} else {
			return req;
		}
	}
	
	/**
	 * Create a new wrapper object
	 *
	 * @param req a <code>HttpServletRequest</code> to wrap
	 * @exception IllegalArgumentException if an error occurs while trying to call setRequest(req)
	 */
	public MognoServletRequestWrapper(HttpServletRequest req) throws IllegalArgumentException {
		super(req);
		setRequest(req);
	}

	/**
	 * Set the request to wrap
	 * @param req the request object
	 * @exception IllegalArgumentException if an error occours
	 */
	public void setRequest(HttpServletRequest req) throws IllegalArgumentException {
		try {
			parameters = new Hashtable();
			fileItems = new Hashtable();
			DiskFileUpload dfu = new DiskFileUpload();
			List parts = dfu.parseRequest(req,1024,10485760,System.getProperty("java.io.tmpdir"));
			Iterator i = parts.iterator();
			while (i.hasNext()) {
				FileItem part = (FileItem)i.next();
				String field = part.getFieldName();
				if (part.isFormField()) {
					if (parameters.containsKey(field)) {
						Vector v = new Vector();
						if (!(parameters.get(field) instanceof Vector)) {
							v = new Vector();
							v.add(parameters.get(field));
						} else {
							v = (Vector)parameters.get(field);
						}
						v.add(part.getString());
						parameters.put(field,v);
					} else {
						parameters.put(field,part.getString());
					}
				} else {
					parameters.put(field,part.getName());
					fileItems.put(field,part);
				}
			}
			super.setRequest(req);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error while parsing request - "+e.getMessage());
		}
	}

	/**
	 * Get a part
	 * @param name The name of the part
	 * @return the Part
	 */
	public FileItem getFileItem(String name) {
		return (FileItem)fileItems.get(name);
	}

	/**
	 * Get a parameter value
	 * @param name The name of the parameter
	 * @return the value
	 */
	public String getParameter(String name) {
		if (isMultiPart()) {
			if (parameters.get(name) instanceof Vector) {
				return (String)((Vector)parameters.get(name)).get(0);
			} else {
				return (String)parameters.get(name);
			}
		} else {
			return getRequest().getParameter(name);
		}
	}

	/**
	 * Return the names of the parameters
	 * @return an Enumeration of Strings with the parameter names
	 */
	public Enumeration getParameterNames() {
		if (isMultiPart()) {
			return parameters.keys();
		} else {
			return getRequest().getParameterNames();
		}
	}

	/**
	 * Return the values of the parameters
	 * @return an array with the values
	 */
	public String[] getParameterValues(String field) {
		if (isMultiPart()) {
			if (parameters.get(field) instanceof Vector) {
				Vector v = (Vector)parameters.get(field);
				String[] ret = new String[v.size()];
				for (int i = 0; i < v.size(); i++) {
					ret[i] = (String)v.get(i);
				}
				return ret;
			} else {
				String[] ret = new String[1] ;
				ret[0] = getParameter(field);
				return ret;
			}
		} else {
			return getRequest().getParameterValues(field);
		}
	}

	public boolean isMultiPart() {
		return isMultiPart((HttpServletRequest)getRequest());
	}

	public static boolean isMultiPart(HttpServletRequest req) {
		if ("POST".equals(req.getMethod()) && req.getContentType().startsWith("multipart/form-data")) {
			return true;
		} else {
			return false;
		}
	}

}
