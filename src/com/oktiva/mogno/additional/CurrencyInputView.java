package com.oktiva.mogno.additional;
/*
 * vim:fileencoding=utf-8:encoding=utf-8
 */
import java.util.Currency;
import java.text.NumberFormat;
import java.util.Locale;
import com.oktiva.mogno.html.Span;

public class CurrencyInputView extends DoubleInput implements ComponentView {

	public boolean viewOnly = false;
	public boolean isViewOnly() {
		return viewOnly;
	}
	public void setViewOnly(boolean viewOnly) {
		this.viewOnly=viewOnly;
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

		if (viewOnly) {
			Span myself = new Span();
			String spanContent = "";
			try {
				spanContent = nf.format(getDoubleValue());
			} catch (Exception e) {
				spanContent = nf.format(new Double(0));
			}
			myself.setContent(spanContent);
			ret.append(myself.show());
			String oldType = getType();
			setType("hidden");
			ret.append(super.show());
			setType(oldType);
		} else {
			ret.append("<LABEL");
			if (getId() != null && !"".equals(getId())) {
				ret.append(" for=\""+getId()+"\"");
			}
			ret.append(">");
			ret.append(cur.getSymbol(Locale.getDefault())+" ");
			ret.append("</LABEL> ");
			ret.append(super.show());
		}
		return ret.toString();
	}
	
}
