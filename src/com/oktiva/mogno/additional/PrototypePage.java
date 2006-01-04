package com.oktiva.mogno.additional;
/*
 * vim:encoding=utf-8:fileencoding=utf-8
 */

import com.oktiva.mogno.*;
import com.oktiva.mogno.html.*;
import javax.servlet.http.*;

/** Especialization of Page for fast prototyping.
 * <p>O <i>MognoApplication.xml</i> dever&aacute; ter entradas como:<br>
 * <tt>&lt;TopLevel name="Test1" class="com.oktiva.mogno.html.PrototypePage" xml="Test1.xml"/&gt;</tt>
 * <p>O XML de destino de um link deve ser passado em um par&acirc;metro <b>mognoDestiny</b>.
 * Os links para uma p&aacute;gina deste tipo ficam no seguinte formato:<br>
 * <tt>http://myhost/myapp/app?mognoOrigin=Test1&amp;mognoDestiny=Test3</tt>
 * @version $Id$
 */
public class PrototypePage extends Page {
	/** Somente obt&eacute;m o <tt>TopLevel</tt> de destino definido em <i>mognoDestiny</i> e faz show().
	 */
	public void message(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String destiny = request.getParameter("mognoDestiny");
		TopLevel tl = application.getTopLevel(destiny);
		application.outHtml(tl.show());
	}
}
