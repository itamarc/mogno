package com.oktiva.mogno.additional;
/*
 * vim:encoding=utf-8:fileencoding=utf-8
 */
import com.oktiva.mogno.*;
import java.io.File;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.gjt.itemplate.*;

/** Template - Component for adding free text
 * <p>This component will be used to insert free text inside your web page.
 * It will substitute text enclosed by [# and #] with the key setted
 * in the <tt>vars</tt> Hashtable.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt; and others.
 * @see <a href="http://www.gjt.org/pkgdoc/org/gjt/itemplate/index.html"> ITemplate</a>
 */
public class Template extends Visual {
	static Logger logger = Logger.getLogger(Template.class.getName());
	/** <tt>file</tt> or <tt>string</tt>. If <i>type</i> is <tt>file</tt>,
	 * it will try to open the <tt>source</tt> and use it.
	 * Else it will print the <tt>source</tt>.<br>
	 * Default is <tt>string</tt>.
	 */
	public String type = "string";
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	/** This property indicates the full path to the source file to be
	 * printed or if the <i>type</i> is <tt>string</tt>, the code itself.
	 */
	public String source = "";
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	/** This property contains the variables to be substituted.
	 */
	protected Hashtable vars = new Hashtable();
	/** Event dispatched when this frame is loaded. */
	public String evOnLoad;
	public String getEvOnLoad() {
		return evOnLoad;
	}
	public void setEvOnLoad(String evOnLoad) {
		this.evOnLoad = evOnLoad;
	}


	public void receiveRequest(HttpServletRequest request) {
		if (request.getParameter(name) != null) {
			queue("evOnLoad");
		}
	}
	/** Fills in the template and return it.
	 */
	public String show()
	throws Exception {
		StringBuffer str = new StringBuffer();
		str.append(super.show());
		try {
			ITemplate tmpl;
			if (type.equals("file")) {
				tmpl = new ITemplate(new File(source));
			} else {
				tmpl = new ITemplate(source,"string");
			}
			if (isDesigning()) {
				str.append(tmpl.getTemplate());
			} else {
				str.append(tmpl.fill(vars));
			}
		} catch (EmptyTemplateException ex) {
			// It's ok here, we can ignore
		} catch (ParameterException ex) {
			// Ignoring for now, not a big deal
		} catch (TokensDontMatchException ex) {
			logger.error("Tokens don't match at template '"+name+"': "+ex.getMessage());
		}
		return str.toString();
	}
	
	/** Define the variables that will be used for substitution in the template.
	 */
	public void setVars(Hashtable vars) {
		this.vars = vars;
	}
}
