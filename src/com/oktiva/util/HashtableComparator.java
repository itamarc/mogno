/*
 * HashtableComparator.java
 *
 * Created on 14 de Maio de 2003, 16:42
 */

package com.oktiva.util;

import java.util.Hashtable;

/**
 *
 */
public class HashtableComparator implements java.util.Comparator {
	private String key;
	private boolean inverse = false;
	/** Creates a new instance of HashtableComparator 
	 * @param key The key that should be used to compare the Hashtables
	 */
	public HashtableComparator(String key) {
		this.key=key;
	}

	/**
	 * Creates a new instance of HashtableComparator
	 * @param key The key that should be used to compare the Hashtables
	 * @param inverse The order should be inverse?
	 */
	public HashtableComparator(String key, boolean inverse) {
		this.key=key;
		this.inverse=inverse;
	}
	
	public int compare(Object o1, Object o2) {
		Hashtable h1,h2;
		Object a,b;
		h1=(Hashtable)o1;
		h2=(Hashtable)o2;
		if (inverse) {
			a = h2.get(key);
			b = h1.get(key);
		} else {
			a=h1.get(key);
			b=h2.get(key);
		}
		if(a!=null && b!=null) {
			return a.toString().compareToIgnoreCase(b.toString());
		} else if(a==null && b==null) {
			return 0;
		} else if(a==null) {
			return 1;
		}
		return -1;
	}
}
