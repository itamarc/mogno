/*
 * MognoApplicationHandler.java
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
 * Criado para arquivos que seguem a estrutura de Application.xsd
 */
public class MognoApplicationHandler implements ContentHandler {
	private Hashtable topLevelsData = new Hashtable();
	private String defaultTopLevel = null;
	
	/*Exemplo de mogno-config.xml
	 *<MognoApplication name="MyApp"/>
	 *	<TopLevel name="formLogin" class="com.oktiva.myapp.TopLevel1" xml="TopLevel1.xml"/>
	 *	<TopLevel name="cadastroUsuario" class="com.oktiva.myapp.TopLevel2" xml="TopLevel2.xml"/>
	 *	<TopLevel name="formBusca" class="com.oktiva.myapp.TopLevel3" xml="TopLevel3.xml"/>
	 *	<DefaultTopLevel name="formLogin"/>
	 *</MognoApplication>
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
	throws SAXException {
		if (localName.equals("TopLevel")) {
			String name = atts.getValue("name");
			if (topLevelsData.containsKey(name)) {
				throw new SAXException("Duplicate name '"+name+"'");
			}
			String classe = atts.getValue("class");
			String xml = atts.getValue("xml");
			if (xml == null || xml.equals("")) {
				xml = name+".xml";
			}
			Hashtable hash = new Hashtable();
			hash.put("class", classe);
			hash.put("xml", xml);
			topLevelsData.put(name,hash);
		} else if(localName.equals("DefaultTopLevel")) {
			String name = atts.getValue("name");
			defaultTopLevel = name;
		}
	}
	
	public Hashtable getTopLevelsData() {
		return topLevelsData;
	}
	
	public String getDefaultTopLevel() {
		return defaultTopLevel;
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
	}
	public void characters(char[] ch, int start, int length) throws SAXException {
	}
	public void endDocument() throws SAXException {
		if (topLevelsData.size()==0) {
			throw new SAXException("Top levels not found.");
		}
		if (defaultTopLevel == null) {
			throw new SAXException("Default top level not set.");
		}
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
