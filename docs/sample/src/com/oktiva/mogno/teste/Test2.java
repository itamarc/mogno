package com.oktiva.mogno.teste;

import java.util.Hashtable;
import java.util.Map;

import com.oktiva.mogno.html.*;
import com.oktiva.mogno.*;
import java.io.*;

/**
 * @author ruoso
 */
public class Test2 {
	public static void main(String[] args)
	throws IOException, InitializeException, Exception {
		Page page1 = new Page();
		page1.initialize("/home/itamar/devel/oktiva/mogno/build/Test1.xml");
		System.out.print(page1.show());
	}
}
