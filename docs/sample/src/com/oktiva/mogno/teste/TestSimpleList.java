/*
 * TestSimpleList.java
 *
 * Created on 17 de Fevereiro de 2003, 14:55
 */
package com.oktiva.mogno.teste;

import com.oktiva.mogno.*;
import com.oktiva.mogno.html.*;
import com.oktiva.mogno.additional.*;

/**
 * @version $Id$
 */
public class TestSimpleList extends com.oktiva.mogno.html.PrototypePage {
	public void onPageShow(TestSimpleList sender) {
		StringBuffer l = new StringBuffer();
		SimpleList sl = (SimpleList)this.getChild("simpleList1");
		sl.list = "Item1\nItem 2\n Item 2.1\n  Item 2.1.1\n Item 2.2\nItem 3\n Item 3.1";
	}
}
