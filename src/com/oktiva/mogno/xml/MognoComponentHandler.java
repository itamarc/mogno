/*
 * MognoComponentHandler.java
 *
 * Created on 3 de Fevereiro de 2003, 18:20
 */
package com.oktiva.mogno.xml;

import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import com.megginson.sax.*;
import org.apache.xerces.parsers.*;

/**
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 */

/** Classe que entende a estrutura do XML para fazer o parse.
 * Criado para arquivos que seguem a estrutura de Component.xsd
 */
public class MognoComponentHandler implements ContentHandler {
	private String ownedName = null;
	private Hashtable owned = new Hashtable();
	private Hashtable mine = new Hashtable();
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
	throws SAXException {
		if (localName.equals("prop")) {
			String name = atts.getValue("name");
			String value = atts.getValue("value");
			if (ownedName == null) {
				mine.put(name,value);
			} else {
				((Hashtable)owned.get(ownedName)).put(name, value);
			}
		} else if(localName.equals("owned")) {
			ownedName = atts.getValue("name");
			if (!owned.containsKey(ownedName)) {
				owned.put(ownedName, new Hashtable());
			}
			((Hashtable)owned.get(ownedName)).put("name", ownedName);
		}
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (localName.equals("owned")) {
			ownedName = null;
		}
	}

	public Hashtable getData() {
		Hashtable hash = new Hashtable();
		hash.put("mine",mine);
		hash.put("owned",owned);
		return hash;
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
	}
	public void endDocument() throws SAXException {
	}
	public void endPrefixMapping(String prefix) throws SAXException {
	}
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
	}
	public void processingInstruction(String target, String data) throws SAXException {
	}
	public void setDocumentLocator(Locator locator) {
	}
	public void skippedEntity(String name) throws SAXException {
	}
	public void startDocument() throws SAXException {
	}
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
	}
}
