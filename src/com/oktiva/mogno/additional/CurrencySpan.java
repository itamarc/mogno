package com.oktiva.mogno.additional;

import java.util.Currency;
import java.text.NumberFormat;
import java.util.Locale;
import com.oktiva.mogno.html.Span;

public class CurrencySpan extends Span {

	/** NumberFormat attribute */
	public int minFractionDigits = 2;
	/** NumberFormat attribute */
	public int maxFractionDigits = 2;

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

	public Double DoubleValue = new Double(0);
	/**
	 * Get the DoubleValue value.
	 * @return the DoubleValue value.
	 */
	public Double getDoubleValue() {
		return DoubleValue;
	}
	/**
	 * Set the DoubleValue value.
	 * @param newDoubleValue The new DoubleValue value.
	 */
	public void setDoubleValue(Double newDoubleValue) {
		this.DoubleValue = newDoubleValue;
	}

	public String ISO4217 = "BRL";
	/**
	 * Get the ISO4217 code of this input value.
	 * defaults to "BRL" (Brazillian Real).
	 * @return the ISO4217 value.
	 */
	public String getISO4217() {
		return ISO4217;
	}

	/**
	 * Set the ISO4217 value.
	 * defaults to "BRL" (Brazillian Real).
	 * @param newISO4217 The new ISO4217 code.
	 */
	public void setISO4217(String newISO4217) {
		this.ISO4217 = newISO4217;
	}

	/**
	 * Overwritten to return the currency formatted
	 * string when is in view-only mode.
	 */
	public String show()
	throws Exception {
		StringBuffer ret = new StringBuffer();
		Currency cur = Currency.getInstance(getISO4217());
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
		nf.setMinimumFractionDigits(getMinFractionDigits());
		nf.setMaximumFractionDigits(getMaxFractionDigits());
		nf.setCurrency(cur);
		setContent(nf.format(getDoubleValue()));
		return super.show();
	}
	
}
