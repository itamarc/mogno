package com.oktiva.mogno.additional;

import com.oktiva.mogno.*;
import javax.servlet.http.*;

/** Descendente de Included para cria��o de prot�tipos, somente com os XMLs das p�ginas.
 * <p>O <i>MognoApplication.xml</i> dever� ter entradas como:<br>
 * <tt>&lt;TopLevel name="Test1" class="com.oktiva.mogno.html.PrototypeIncluded" xml="Test1.xml"/&gt;</tt>
 * <p>O XML de destino de um link deve ser passado em um par�metro <b>mognoDestiny</b>.
 * Os links para uma p�gina deste tipo ficam no seguinte formato:<br>
 * <tt>http://myhost/myapp/app?mognoOrigin=Test1&amp;mognoDestiny=Test3</tt>
 * @version $Id$
 */
public class PrototypeIncluded extends Included {
	/** Somente obt�m o <tt>TopLevel</tt> de destino definido em <i>mognoDestiny</i> e faz show().
	 */
	public void message(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String destiny = request.getParameter("mognoDestiny");
		TopLevel tl = application.getTopLevel(destiny);
		application.outHtml(tl.show());
	}
}
