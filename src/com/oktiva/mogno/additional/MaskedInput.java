/*
 * MaskedInput.java
 *
 * Created on sabado, 31 de maio de 2003 15:52
 * vim:encoding=utf-8:fileencoding=utf-8
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.SyntaxErrorException;
import com.oktiva.mogno.html.Input;
import java.util.GregorianCalendar;
import com.oktiva.util.DateUtils;
import javax.servlet.http.HttpServletRequest;

/** This component apply a regexp specified by the mask attribute in the checkSyntax method.
 * @version $Id$
 */
public class MaskedInput extends Input {
	/** String containing the regexp to be applied using java.lang.String.matches().
	 */
	public String mask = "";
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}

	
	/** Creates a new instance of MaskedInput */
	public MaskedInput() {
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
		if (!value.matches(mask)) {
			throw new SyntaxErrorException("Invalid value '"+value+"' don't fit the mask '"+mask+"'.");
		}
	}
}
