package com.oktiva.mogno;

/**
 * This is the exception throwed when a Syntax Error is found
 * in some component.
 */
public class SyntaxErrorException extends Exception {
	public SyntaxErrorException() {
		super();
	}
	public SyntaxErrorException(String message) {
		super(message);
	}
}
