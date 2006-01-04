package com.oktiva.mogno.additional;
/*
 * vim:encoding=utf-8:fileencoding=utf-8
 */

import com.oktiva.mogno.*;
import javax.servlet.http.*;

/** Especialization of Page for fast prototyping, with only XML for the pages.
 * <p>O <i>MognoApplication.xml</i> must have entries like:<br>
 * <tt>&lt;TopLevel name="Test1" class="com.oktiva.mogno.additional.PrototypeDerivedPage" xml="Test1.xml"/&gt;</tt>
 * <p>A link's topLevel destiny must be passed on a parameter <b>mognoDestiny</b>.
 * Links for a PrototypeDerivedPage should look like:<br>
 * <tt>http://myhost/myapp/app?mognoOrigin=Test1&amp;mognoDestiny=Test3</tt>
 * @version $Id$
 */
public class PrototypeDerivedPage extends DerivedPage {
	/** Only get destination <tt>TopLevel</tt> from <i>mognoDestiny</i> and calls show().
	 */
	public void message(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String destiny = request.getParameter("mognoDestiny");
		TopLevel tl = application.getTopLevel(destiny);
		application.outHtml(tl.show());
	}
}
