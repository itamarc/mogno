package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.*;
import org.apache.log4j.Logger;
import java.util.List;
import java.io.InputStream;
import java.io.IOException;

/** INPUT tag (defined by W3C)<br>
 * @version $Id$
 * @see Form
 */
public class Input extends Visual {
	static Logger logger = Logger.getLogger(Input.class.getName());
	/** Event dispatched when this input is clicked. */
	public String evOnClick;
	public String getEvOnClick() {
		return evOnClick;
	}
	public void setEvOnClick(String evOnClick) {
		this.evOnClick = evOnClick;
	}
	
	public String getId() {
		if(super.getId() == null || super.getId().equals("")) {
			return getName();
		}
		return super.getId();
	}
	/** Event dispatched when the content of the input changes. */
	public String evOnChange;
	public String getEvOnChange() {
		return evOnChange;
	}
	public void setEvOnChange(String evOnChange) {
		this.evOnChange = evOnChange;
	}
	
	//core html attributes inherited from class com.oktiva.mogno.Visual
	//i18nHtmlAttributes
	/** Internationalization HTML attribute */
	public String lang;
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	/** Internationalization HTML attribute */
	public String dir;
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	//eventsHtmlAttributes
	/** Event HTML attribute */
	public String onmouseup;
	public String getOnmouseup() {
		return onmouseup;
	}
	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}
	
	/** Event HTML attribute */
	public String onmousedown;
	public String getOnmousedown() {
		return onmousedown;
	}
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}
	
	/** Event HTML attribute */
	public String onclick;
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
	/** Event HTML attribute */
	public String ondblclick;
	public String getOndblclick() {
		return ondblclick;
	}
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}
	
	/** Event HTML attribute */
	public String onmouseover;
	public String getOnmouseover() {
		return onmouseover;
	}
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}
	
	/** Event HTML attribute */
	public String onmousemove;
	public String getOnmousemove() {
		return onmousemove;
	}
	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}
	
	/** Event HTML attribute */
	public String onmouseout;
	public String getOnmouseout() {
		return onmouseout;
	}
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}
	
	/** Event HTML attribute */
	public String onkeypress;
	public String getOnkeypress() {
		return onkeypress;
	}
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}
	
	/** Event HTML attribute */
	public String onkeydown;
	public String getOnkeydown() {
		return onkeydown;
	}
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}
	
	/** Event HTML attribute */
	public String onkeyup;
	public String getOnkeyup() {
		return onkeyup;
	}
	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}
	
	//htmlAttributes
	//name is inherited from Component
	/** HTML attribute<br>
	 * Valid values: text|password|hidden|button|submit|reset|image|radio|file|checkbox<br>
	 * defaults to "text"
	 */
	public String type = "text";
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	/** HTML attribute */
	public String value;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	/** HTML attribute */
	public String checked;
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	/** HTML attribute */
	public String disabled;
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	/** HTML attribute */
	public String readonly;
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	
	/** HTML attribute */
	public String size;
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	/** HTML attribute */
	public String maxlength;
	public String getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}
	
	/** HTML attribute */
	public String src;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	
	/** HTML attribute */
	public String alt;
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	
	/** HTML attribute */
	public String usemap;
	public String getUsemap() {
		return usemap;
	}
	public void setUsemap(String usemap) {
		this.usemap = usemap;
	}
	
	/** HTML attribute */
	public String ismap;
	public String getIsmap() {
		return ismap;
	}
	public void setIsmap(String ismap) {
		this.ismap = ismap;
	}
	
	/** HTML attribute */
	public String tabindex;
	public String getTabindex() {
		return tabindex;
	}
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}
	
	/** HTML attribute */
	public String accesskey;
	public String getAccesskey() {
		return accesskey;
	}
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}
	
	/** HTML attribute */
	public String onfocus;
	public String getOnfocus() {
		return onfocus;
	}
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	
	/** HTML attribute */
	public String onblur;
	public String getOnblur() {
		return onblur;
	}
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}
	
	/** HTML attribute */
	public String onselect;
	public String getOnselect() {
		return onselect;
	}
	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}
	
	/** HTML attribute */
	public String onchange;
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	
	/** HTML attribute */
	public String accept;
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	
	/** Non-HTML attribute<br>
	 * This property defines which radiogroup this <b>radio</b> belongs.
	 */
	public String radiogroup;
	public String getRadiogroup() {
		return radiogroup;
	}
	public void setRadiogroup(String radiogroup) {
		this.radiogroup = radiogroup;
	}
	
	/** Non-HTML attribute<br>
	 * The text after the input <b>radio</b>.
	 */
	public String label;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String x;
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	
	public String y;
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	
	/** If this input has to be filled to be considered valid in checkSyntax(), defaults to false */
	public boolean required = false;
	public boolean getRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}

	public InputStream uploadInputStream;

	/**
	 * Get the UploadInputStream value.
	 * @return the UploadInputStream value.
	 */
	public InputStream getUploadInputStream() {
		return uploadInputStream;
	}
	
	/**
	 * Set the UploadInputStream value.
	 * @param newUploadInputStream The new UploadInputStream value.
	 */
	public void setUploadInputStream(InputStream newUploadInputStream) {
		this.uploadInputStream = newUploadInputStream;
	}

	public String uploadContentType;

	/**
	 * Get the UploadContentType value.
	 * @return the UploadContentType value.
	 */
	public String getUploadContentType() {
		return uploadContentType;
	}

	/**
	 * Set the UploadContentType value.
	 * @param newUploadContentType The new UploadContentType value.
	 */
	public void setUploadContentType(String newUploadContentType) {
		this.uploadContentType = newUploadContentType;
	}

	
/* If the input is of the file type, another property is used:
 * filehandle: returns the filehandle to the uploaded file
 */
	
	public Input() {
		super();
		tag = "INPUT";
	}
	
	public Vector htmlAttributes() {
		Vector v = super.htmlAttributes();
		v.addAll(coreHtmlAttributes());
		v.addAll(i18nHtmlAttributes());
		v.addAll(eventsHtmlAttributes());
		v.add("name");
		v.add("type");
		v.add("value");
		v.add("disabled");
		v.add("readonly");
		v.add("size");
		v.add("maxlength");
		v.add("src");
		v.add("alt");
		v.add("usemap");
		v.add("ismap");
		v.add("tabindex");
		v.add("accesskey");
		v.add("onfocus");
		v.add("onblur");
		v.add("onselect");
		v.add("onchange");
		v.add("accept");
		return v;
	}
/*
evOnChange: When the content of the input changes
evOnClick: When this input was used to submit (if type eq button, submit, reset, image)
P.S.: When type eq image and the input was clicked, Mogno automatically set x and y with the
data coming from cgi (server side image map)
P.S.2: When type eq file, the properties uploadInputStream, uploadContentType and uploadSize will
be setted. The value will be equals to the file name on the client side.
 */
	public void receiveRequest(HttpServletRequest request) {
		String oldValue = value;
		if (type == null || "text".equals(type) || "password".equals(type) || "hidden".equals(type)) {
			value = request.getParameter(name);
			if (oldValue != null && !oldValue.equals(value)) {
				queue("evOnChange");
			}
		} else if ("file".equals(type)) {
			if (request instanceof MognoServletRequestWrapper) {
				FileItem file = ((MognoServletRequestWrapper)request).getFileItem(getName());
				try {
					InputStream is = file.getInputStream();
					setUploadInputStream(is);
					setValue(file.getName());
					setUploadContentType(file.getContentType());
				} catch (Exception e) {}
			}
		} else if ("image".equals(type)) {
			if (request.getParameter(name+".x") != null) {
				queue("evOnClick");
				x = request.getParameter(name+".x");
				y = request.getParameter(name+".y");
			}
		} else if ("checkbox".equals(type)) {
			if (request.getParameter(name) != null) {
				checked = "true";
			} else {
				checked = null;
			}
		} else {
			if (request.getParameter(name) != null) {
				queue("evOnClick");
			}
		}
	}
	
	/**
	 * @return The html code for this component.
	 */
	public String show()
	throws Exception {
		String oldName = null;
		String myValue = value;
		String checked = "";
		String radioLabel = "";
		if (type == null || type.equals("")) {
			logger.info("Input's attribute type not defined at '"+name+"'");
		}
		if ("radio".equalsIgnoreCase(type)) {
			oldName = name;
			name = radiogroup;
			// Por que isto estava aqui? Isto nao faz sentido.
			//value = oldName;
			// O value do radio deve ser o value do radio, nao o nome dele
			String sel = ((Radiogroup)owner.getChild(radiogroup)).value;
			if (sel != null && sel.equals(myValue)) {
				checked = " CHECKED";
			}
			if (label != null && !label.equals("")) {
				radioLabel = " "+label;
			}
		}
		if ("checkbox".equalsIgnoreCase(type)) {
			if (this.checked != null && !this.checked.equals("")) {
				checked = " CHECKED";
			}
		}
		String ret = super.show()+"<INPUT"+showHtmlAttributes()+checked+">"+radioLabel;
		if ("radio".equalsIgnoreCase(type)) {
			name = oldName;
		}
		return ret;
	}
	
	public void checkSyntax()
	throws SyntaxErrorException {
		if(getRequired() && (value==null || "".equals(value))) {
			throw new SyntaxErrorException("Required field "+name+" is empty.");
		}
	}

	public Vector nonAttributeGetters() {
		Vector v = super.nonAttributeGetters();
		v.add("getUploadInputStream");
		return v;
	}
}
