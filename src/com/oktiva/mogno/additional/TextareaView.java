/*
 * TextareaView.java
 *
 * Created on 19 de Julho de 2003, 16:51
 */

package com.oktiva.mogno.additional;

import com.oktiva.mogno.additional.ComponentView;
import com.oktiva.mogno.html.Textarea;
import com.oktiva.mogno.html.Tt;
import java.io.BufferedInputStream;

/**
 *
 * @author  mosca
 */
public class TextareaView extends Textarea implements ComponentView {
	public boolean viewOnly = false;
	/** Creates a new instance of TextareaView */
	public TextareaView() {
		super();
	}
	
	public boolean isViewOnly() {
		return this.viewOnly;
	}
	
	public void setViewOnly(boolean viewOnly) {
		this.viewOnly=viewOnly;
	}
	
	public String show()
	throws Exception {
		if(isViewOnly()) {
			StringBuffer ret=new StringBuffer(4096);
			String style=getStyle();
			Tt tt=new Tt();
			tt.setStyle(style);
			String cont = getContent();
			cont = cont.replaceAll("\n", "<br>");
			tt.setContent(cont);
			ret.append(tt.show());
			setStyle("display:none");
			ret.append(super.show());
			setStyle(style);
			return ret.toString();
		}
		return super.show();
	}
}