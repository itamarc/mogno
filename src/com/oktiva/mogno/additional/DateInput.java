/*
 * DateInput.java
 *
 * Created on 24 de Março de 2003, 18:31
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.SyntaxErrorException;
import com.oktiva.mogno.html.Input;
import java.util.GregorianCalendar;
import com.oktiva.util.DateUtils;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.text.DateFormat;
import java.util.Vector;

/**
 */
public class DateInput extends Input {
	/** The format of the date.<br>
	 * Valid values are "dmy" and "ymd".
	 */
	public String format = "dmy";
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	
	/** Creates a new instance of DateInput */
	public DateInput() {
		super();
		setType("text");
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
		boolean ok = false;
		if ("dmy".equals(format) && !"".equals(value)) {
			if(DateUtils.dataValida(value)) {
				ok = true;
			}
		} else if("ymd".equals(format) && !"".equals(value)) {
			if(DateUtils.dataIsoValida(value)) {
				ok = true;
			}
		} else if("".equals(getValue())) {
			ok=true;
		}
		if(!ok) {
			throw new SyntaxErrorException("Invalid date '"+value+"' for the format '"+format+"'.");
		}
	}
	
	/** Return the value as an GregorianCalendar object.
	 * @throws SyntaxErrorException If the content of the <code>value</code>
	 * attribute don't represent an valid GregorianCalendar.
	 */
	public GregorianCalendar getGregorianCalendarValue()
	throws SyntaxErrorException {
		GregorianCalendar dateValue = null;
		try {
			dateValue = DateUtils.getGregorianCalendar(value);
		} catch (Exception e) {
			throw new SyntaxErrorException("Invalid GregorianCalendar value: "+value);
		}
		if (dateValue == null) {
			throw new SyntaxErrorException("Invalid GregorianCalendar value: "+value);
		}
		return dateValue;
	}

	/** Return the value as an Date object.<br>
	 * Convenience method that uses <code>getGregorianCalendarValue().getTime()</code>
	 * @throws SyntaxErrorException If the content of the <code>value</code>
	 * attribute don't represent an valid GregorianCalendar and Date.
	 */
	public Date getDateValue()
	throws SyntaxErrorException {
		return getGregorianCalendarValue().getTime();
	}

	/** Set the value of the input passing a Date object
	 */
	public void setDateValue(Date value) {
		DateFormat df = DateFormat.getDateInstance();
		setValue(df.format(value));
	}

	public void setValue(Date value) {
		setDateValue(value);
	}
	
	public Vector nonAttributeGetters() {
		Vector v = super.nonAttributeGetters();
		v.add("getDateValue");
		v.add("getGregorianCalendarValue");
		return v;
	}
}
