package com.oktiva.mogno;

/**
 * @version $Id$
 */
public class InitializeException extends java.lang.Exception {
	/**
	 * Creates a new instance of <code>InitializeException</code> without detail message.
	 */
	public InitializeException() {
	}
	
	/**
	 * Constructs an instance of <code>InitializeException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public InitializeException(String msg) {
		super(msg);
	}
	
	public InitializeException(Throwable cause) {
		super(cause);
	}
}
