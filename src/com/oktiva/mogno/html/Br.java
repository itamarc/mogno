package com.oktiva.mogno.html;

import com.oktiva.mogno.*;
import java.util.*;

/** BR tag (defined by W3C)<br>
 * @version $Id$
 */
public class Br extends EmptyTag {
	//core html attributes inherited from class com.oktiva.mogno.Visual

	public Br() {
		super();
		tag = "BR";
		afterEnd = "\n";
	}
}
