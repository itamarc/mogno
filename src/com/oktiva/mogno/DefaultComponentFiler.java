/*
 * DefaultComponentFiler.java
 *
 * Created on 3 de Fevereiro de 2003, 18:20
 */
package com.oktiva.mogno;

import java.io.*;
import java.util.*;
import com.oktiva.mogno.xml.*;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import com.megginson.sax.XMLWriter;

/** This is the DefaultComponentFiler, wich uses an XML file to store the component's params.
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 */
public class DefaultComponentFiler implements ComponentFiler {
	private File xmlFile = null;
	private boolean designing = false;
	private final char[] newline = {'\n'};
	private final char[] tabs = {'\t','\t','\t','\t'};

	private static Hashtable cache = new Hashtable();
	
	/** @param params This filer only needs one parameter: a File where is the XML, under the key "xmlFile".
	 * If there is a second parameter with key "designing", this filer don't use the cache.
	 */
	public void setParams(Hashtable params) {
		xmlFile = (File)params.get("xmlFile");
		designing = params.containsKey("designing");
	}
	
	/** Loads the component's params from a XML file.
	 */
	public Hashtable load()
	throws IOException {
		if (!cache.containsKey(xmlFile) || designing) {
			cache.put(xmlFile, load(new FileInputStream(xmlFile)));
		}
		return (Hashtable)cache.get(xmlFile);
	}
	
	/** Loads the component's params using an InputStream.
	 */
	public Hashtable load(InputStream in)
	throws IOException {
		try {
			InputSource is = new InputSource(in);
			SAXParser parser = new SAXParser();
			MognoComponentHandler handler = new MognoComponentHandler();
			parser.setContentHandler(handler);
			parser.parse(is);
			return handler.getData();
		} catch (SAXException e) {
			throw new IOException("SAX error: "+e.getMessage());
		}
	}
	
	/** Store the attributes to the XML file set.
	 */
	public void store(Hashtable mine, Hashtable owned) throws IOException {
		Vector v = new Vector();
		Object[] keys = owned.keySet().toArray();
		Arrays.sort(keys);
		for (int i=0; i<keys.length; i++) {
			v.add(owned.get(keys[i]));
		}
		store(mine,v);
	}
	
	/** Store the attributes to the XML file set.
	 */
	public void store(Hashtable mine, Vector owned)
	throws IOException {
		if (xmlFile == null) {
			throw new IOException("XML file not set.");
		}
		if (xmlFile.exists() && !xmlFile.canWrite()) {
			throw new IOException("Can't write XML file '"+xmlFile.getCanonicalPath()+"'");
		}
		if (cache.containsKey(xmlFile)) {
			cache.remove(xmlFile);
		}
		FileOutputStream fos = new FileOutputStream(xmlFile);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		XMLWriter xml = new XMLWriter(osw);
		try {
			xml.startDocument();
			AttributesImpl attrs = new AttributesImpl();
			attrs.addAttribute("","xmlns","","java.lang.String","http://www.oktiva.com.br/mogno");
			attrs.addAttribute("","xmlns:xsi","","java.lang.String","http://www.w3.org/2001/XMLSchema-instance");
			attrs.addAttribute("","xsi:schemaLocation","","java.lang.String","http://www.oktiva.com.br/mogno\nComponent.xsd");
			xml.startElement("","MognoComponent","",attrs);
			xml.characters(newline, 0, 1);
			mine.remove("xmlFileName");
			addProps(mine, xml, 1);
			for (int i=0; i<owned.size(); i++) {
				Hashtable ownedProps = (Hashtable)owned.get(i);
				String name = (String)ownedProps.get("name");
				ownedProps.remove("name");
				xml.characters(tabs, 0, 1);
				attrs = new AttributesImpl();
				attrs.addAttribute("","name","","java.lang.String",name);
				xml.startElement("","owned","",attrs);
				xml.characters(newline, 0, 1);
				// fazer os props
				addProps(ownedProps, xml, 2);
				xml.characters(tabs, 0, 1);
				xml.endElement("owned");
				xml.characters(newline, 0, 1);
			}
			xml.endElement("MognoComponent");
			xml.endDocument();
		} catch (SAXException e) {
			throw new IOException("SAX error: "+e.getMessage());
		} finally {
			osw.close();
			fos.close();
		}
	}

	private void addProps(Hashtable hash, XMLWriter xml, int level)
	throws SAXException {
		Object[] keys = hash.keySet().toArray();
		Arrays.sort(keys);
		for (int i=0; i<keys.length; i++) {		
			String prop = (String)keys[i];
			String value = (String)hash.get(prop);
			if (value == null || value.equals("")) {
				continue;
			}
			// Gambiarra maldita para bypassar a gambiarra feita em TableLayout
			if ((prop.equals("gridHeight") && value.equals("1")) ||
			(prop.equals("gridWidth") && value.equals("1")) ||
			(prop.equals("gridWeightX") && value.equals("0")) ||
			(prop.equals("gridWeightY") && value.equals("0"))) {
				continue;
			}
			// Fim da gambiarra maldita
			xml.characters(tabs, 0, level);
			AttributesImpl attrs = new AttributesImpl();
			attrs.addAttribute("","name","","java.lang.String",prop);
			attrs.addAttribute("","value","","java.lang.String",value);
			xml.emptyElement("","prop","",attrs);
			xml.characters(newline, 0, 1);
		}
	}
}
