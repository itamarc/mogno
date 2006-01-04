/*
 * DoubleInput.java
 *
 * Created on 31 de Maio de 2003 18:46
 * vim:encoding=utf-8:fileencoding=utf-8
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.SyntaxErrorException;
import com.oktiva.mogno.html.Input;
import com.oktiva.util.NumberUtils;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import java.text.NumberFormat;
import java.util.Vector;

/**
 * TODO Add javascript to verify the syntax.
 */
public class DoubleInput extends Input {
	private static Logger log = Logger.getLogger("com.oktiva.mogno.additional");
	/** Attribute to be used when checking syntax. */
	public Double minValue;
	/** Attribute to be used when checking syntax. */
	public Double maxValue;
	/** NumberFormat attribute */
	public int minFractionDigits = 2;
	/** NumberFormat attribute */
	public int maxFractionDigits = 2;
	/** NumberFormat object. */
	private NumberFormat numberFormat = null;
	
	/** Creates a new instance of DoubleInput */
	public DoubleInput() {
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
		if("".equals(getValue())) {
			return;
		}
		if (!NumberUtils.doubleValido(getValue())) {
			throw new SyntaxErrorException("Invalid Double value: "+value);
		}
		double valor = NumberUtils.retornaDouble(getValue(), Locale.getDefault());
		if(minValue != null) {
			double min = minValue.doubleValue();
			if(valor < min) {
				throw new SyntaxErrorException("Value is out of range. "+value+" is less than "+min);
			}
		}
		if(maxValue != null) {
			double max = maxValue.doubleValue();
			if(valor > max) {
				throw new SyntaxErrorException("Value is out of range. "+value+" is greater than "+max);
			}
		}
	}
	
	public Double getMinValue() {
		return minValue;
	}
	
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
	
	public Double getMaxValue() {
		return maxValue;
	}
	
	public void setMaxValue(Double minValue) {
		this.maxValue = maxValue;
	}
	
	/** Return the value as an Double object.
	 * @throws SyntaxErrorException If the content of the <code>value</code>
	 * attribute don't represent an valid Double.
	 */
	public Double getDoubleValue()
	throws SyntaxErrorException {
		log.debug("Locale: "+Locale.getDefault().toString());
		double d = NumberUtils.retornaDouble(value,Locale.getDefault());
		if (d == Double.NaN) {
			throw new SyntaxErrorException("Invalid Double value: "+value);
		}
		return new Double(d);
	}
	
	/** Set the value of the input passing a double value */
	public void setDoubleValue(Double value) {
		if (numberFormat == null) {
			numberFormat = NumberFormat.getInstance(Locale.getDefault());
			numberFormat.setMinimumFractionDigits(getMinFractionDigits());
			numberFormat.setMaximumFractionDigits(getMaxFractionDigits());
		}
		setValue(numberFormat.format(value));
	}

	/** Get the MinFractionDigits value.
	 * @return the MinFractionDigits value.
	 */
	public int getMinFractionDigits() {
		return minFractionDigits;
	}

	/** Set the MinFractionDigits value.
	 * @param newMinFractionDigits The new MinFractionDigits value.
	 */
	public void setMinFractionDigits(int newMinFractionDigits) {
		this.minFractionDigits = newMinFractionDigits;
	}

	/** Get the MaxFractionDigits value.
	 * @return the MaxFractionDigits value.
	 */
	public int getMaxFractionDigits() {
		return maxFractionDigits;
	}

	/** Set the MaxFractionDigits value.
	 * @param newMaxFractionDigits The new MaxFractionDigits value.
	 */
	public void setMaxFractionDigits(int newMaxFractionDigits) {
		this.maxFractionDigits = newMaxFractionDigits;
	}

	public Vector nonAttributeGetters() {
		Vector v = super.nonAttributeGetters();
		v.add("getDoubleValue");
		return v;
	}
	
	/** Sets a different NumberFormat object to use when setting the value from a Double.<br>
	 * When using this method, maxFractionDigits and minFractionDigits are ignored.
	 * @param numberFormat New value of property numberFormat.
	 * @see #setDoubleValue
	 */
	public void setNumberFormat(NumberFormat numberFormat) {
		this.numberFormat = numberFormat;
	}
}
