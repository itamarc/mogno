package com.oktiva.mogno.teste;

import java.util.Hashtable;
import java.util.Map;

import com.oktiva.mogno.html.*;
import com.oktiva.mogno.*;
import java.io.*;

/**
 * @author ruoso
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Test1 {
	public static void main(String[] args)
	throws IOException, InitializeException, Exception {
		Page page1 = new Page();
		Hashtable h1 = new Hashtable();
		h1.put("name","page1");
		h1.put("xmlFileName","Test1.xml");
		h1.put("title","Test");
		page1.initialize(h1);
		A a1 = new A();
		h1.clear();
		h1.put("href","testing");
		h1.put("name","a1");
		h1.put("parent","page1");
		h1.put("top",new Integer(0));
		h1.put("left",new Integer(0));
		a1.initialize(h1);
		page1.registerChild(a1);
		System.out.print(page1.show());
		page1.store();
	}
}
