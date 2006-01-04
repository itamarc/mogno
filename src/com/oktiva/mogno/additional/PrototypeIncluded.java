package com.oktiva.mogno.additional;
/*
 * vim:encoding=utf-8:fileencoding=utf-8
 */

import com.oktiva.mogno.*;
import javax.servlet.http.*;

/** Especialization of Included for fast prototyping.
 * <p>O <i>MognoApplication.xml</i> should have entries like:<br>
 * <tt>&lt;TopLevel name="Test1" class="com.oktiva.mogno.html.PrototypeIncluded" xml="Test1.xml"/&gt;</tt>
 * <p>A links destination should be passed in <b>mognoDestiny</b>.
 * Links for PrototypeIncluded should look like this:<br>
 * <tt>http://myhost/myapp/app?mognoOrigin=Test1&amp;mognoDestiny=Test3</tt>
 * @version $Id$
 */
public class PrototypeIncluded extends Included {
	/** Only get destination <tt>TopLevel</tt> from <i>mognoDestiny</i> and calls show().
	 */
	public void message(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String destiny = request.getParameter("mognoDestiny");
		TopLevel tl = application.getTopLevel(destiny);
		application.outHtml(tl.show());
	}
}
