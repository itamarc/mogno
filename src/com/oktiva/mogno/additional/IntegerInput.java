/*
 * IntegerInput.java
 *
 * Created on 31 de Maio de 2003 18:46
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.SyntaxErrorException;
import com.oktiva.mogno.html.Input;
import com.oktiva.util.NumberUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * TODO Add javascript to verify the syntax.
 * @version $Id$
 */
public class IntegerInput extends Input {
	/** Creates a new instance of DateInput */
	public IntegerInput() {
		super();
		type = "text";
	}

	public void receiveRequest(HttpServletRequest request) {
		String oldValue = value;
		value = request.getParameter(name);
		if(oldValue != null && !oldValue.equals(value)) {
			queue("evOnChange");
		}
	}
	
	public void checkSyntax()
	throws SyntaxErrorException {
		super.checkSyntax();
		if (!NumberUtils.intValido(value)) {
			throw new SyntaxErrorException("Invalid Integer value: "+value);
		}
	}
	
	/** Return the value as an Integer object.
	 * @throws SyntaxErrorException If the content of the <code>value</code>
	 * attribute don't represent an valid Integer.
	 */
	public Integer getIntegerValue()
	throws SyntaxErrorException {
		try {
			return new Integer(value);
		} catch (NumberFormatException e) {
			throw new SyntaxErrorException("Invalid Integer value: "+value);
		}
	}
}
