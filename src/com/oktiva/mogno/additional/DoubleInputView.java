/*
 * DoubleInput.java
 *
 * Created on 31 de Maio de 2003 18:46
 * vim:encoding=utf-8:fileencoding=utf-8
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.SyntaxErrorException;
import com.oktiva.mogno.html.Input;
import com.oktiva.mogno.html.Span;
import com.oktiva.util.NumberUtils;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * TODO Add javascript to verify the syntax.
 */
public class DoubleInputView extends DoubleInput implements ComponentView {
	private static Logger log = Logger.getLogger("com.oktiva.mogno.additional");
	public boolean viewOnly = false;
	/** Creates a new instance of DateInput */
	public DoubleInputView() {
		super();
	}
	
	public String show()
	throws Exception {
		StringBuffer ret = new StringBuffer();
		if (viewOnly) {
			String spanContent=getValue();
			if(spanContent!=null) {
				Span myself = new Span();
				myself.setContent(spanContent);
				ret.append(myself.show());
			}
			String oldType = getType();
			setType("hidden");
			ret.append(super.show());
			setType(oldType);
		} else {
			ret.append(super.show());
		}
		return ret.toString();
	}
	
	public boolean isViewOnly() {
		return viewOnly;
	}
	
	public void setViewOnly(boolean viewOnly) {
		this.viewOnly=viewOnly;
	}
}
