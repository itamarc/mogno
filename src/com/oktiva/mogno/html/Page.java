package com.oktiva.mogno.html;

import com.oktiva.mogno.*;

public class Page extends TopLevel {
	public String lang;
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

	public String dir;
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}

	public String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String meta;
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}

	public String linkrel;
	public String getLinkrel() {
		return linkrel;
	}
	public void setLinkrel(String linkrel) {
		this.linkrel = linkrel;
	}

	public String style;
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public String script;
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}

	public String startContainer() {
		StringBuffer ret = new StringBuffer(256);
		ret.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n");
		ret.append("  \"http://www.w3.org/TR/html4/strict.dtd\">\n");
		ret.append("<HTML");
		if (!(lang == null || lang.equals(""))) {
			ret.append(" lang=\"").append(lang).append("\"");
		}
		if (!(dir == null || dir.equals(""))) {
			ret.append(" dir=\"").append(dir).append("\"");
		}
		ret.append(">\n");
		ret.append("<HEAD>\n");
		if (meta != null) {
			ret.append("<").append(meta.replaceAll("><", ">\n<")).append(">\n");
		}
		if (linkrel != null) {
			//ret.append("<").append(linkrel).append(">");
			ret.append(linkrel.replaceAll("><", ">\n<")).append("\n");
		}
		if (style != null) {
			ret.append("<STYLE>\n").append(style).append("</STYLE>\n");
		}
		if (script != null) {
			ret.append("<SCRIPT>\n"+script+"</SCRIPT>\n");
		}
		if (title != null) {
			ret.append("<TITLE>").append(title).append("</TITLE>\n");
		}
		ret.append("</HEAD>\n");
		return ret.toString();
	}

	public String endContainer() {
		return "</HTML>\n";
	}
}
