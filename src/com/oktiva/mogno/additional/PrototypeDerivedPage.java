package com.oktiva.mogno.additional;

import com.oktiva.mogno.*;
import javax.servlet.http.*;

/** Descendente de Page para criação de protótipos, somente com os XMLs das páginas.
 * <p>O <i>MognoApplication.xml</i> deverá ter entradas como:<br>
 * <tt>&lt;TopLevel name="Test1" class="com.oktiva.mogno.additional.PrototypeDerivedPage" xml="Test1.xml"/&gt;</tt>
 * <p>O TopLevel de destino de um link deve ser passado em um parâmetro <b>mognoDestiny</b>.
 * Os links para uma página deste tipo ficam no seguinte formato:<br>
 * <tt>http://myhost/myapp/app?mognoOrigin=Test1&amp;mognoDestiny=Test3</tt>
 * @version $Id$
 */
public class PrototypeDerivedPage extends DerivedPage {
	/** Somente obtém o <tt>TopLevel</tt> de destino definido em <i>mognoDestiny</i> e faz show().
	 */
	public void message(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String destiny = request.getParameter("mognoDestiny");
		TopLevel tl = application.getTopLevel(destiny);
		application.outHtml(tl.show());
	}
}
