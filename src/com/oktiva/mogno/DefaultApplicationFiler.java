/*
 * DefaultApplicationFiler.java
 *
 * Created on quinta, 06 de fevereiro de 2003, 10:46
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

/**
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 */
public class DefaultApplicationFiler implements ApplicationFiler {
	private File xmlFile = null;
	private final static char[] newline = {'\n'};
	
	/** Creates a new instance of DefaultApplicationFiler */
	public DefaultApplicationFiler() {
	}
	
	/** @param params Este filer só precisa de um parâmetro: um File onde se encontra o XML, sob a chave "xmlFile".
	 */
	public void setParams(Hashtable params) {
		xmlFile = (File)params.get("xmlFile");
	}
	
	public Hashtable load()
	throws IOException {
		try {
			InputSource is = new InputSource(new FileInputStream(xmlFile));
			SAXParser parser = new SAXParser();
			MognoApplicationHandler handler = new MognoApplicationHandler();
			parser.setContentHandler(handler);
			parser.parse(is);
			Hashtable hash = new Hashtable();
			hash.put("topLevelsData",handler.getTopLevelsData());
			hash.put("defaultTopLevel",handler.getDefaultTopLevel());
			return hash;
		} catch (SAXException e) {
			throw new IOException("SAX error: "+e.getMessage());
		}
	}
	
	/*Exemplo de mogno-application.xml
	 *<MognoApplication name="MyApp"/>
	 *	<TopLevel name="formLogin" class="com.oktiva.myapp.TopLevel1" xml="TopLevel1.xml"/>
	 *	<TopLevel name="cadastroUsuario" class="com.oktiva.myapp.TopLevel2" xml="TopLevel2.xml"/>
	 *	<TopLevel name="formBusca" class="com.oktiva.myapp.TopLevel3" xml="TopLevel3.xml"/>
	 *	<DefaultTopLevel name="formLogin"/>
	 *</MognoApplication>
	 */
	public void store(Hashtable topLevelsData, String defaultTopLevel)
	throws IOException {
		if (xmlFile == null) {
			throw new IOException("XML file not set.");
		}
		if (xmlFile.exists() && !xmlFile.canWrite()) {
			throw new IOException("Can't write XML file '"+xmlFile.getCanonicalPath()+"'");
		}
		FileOutputStream fos = new FileOutputStream(xmlFile);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		XMLWriter xml = new XMLWriter(osw);
		try {
			// Header
			xml.startDocument();
			AttributesImpl attrs = new AttributesImpl();
			attrs.addAttribute("","xmlns","","java.lang.String","http://www.oktiva.com.br/mogno");
			attrs.addAttribute("","xmlns:xsi","","java.lang.String","http://www.w3.org/2001/XMLSchema-instance");
			attrs.addAttribute("","xsi:schemaLocation","","java.lang.String","http://www.oktiva.com.br/mogno\nApplication.xsd");
			xml.startElement("","MognoApplication","",attrs);
			xml.characters(newline, 0, 1);
			// Body
			Object[] keys = topLevelsData.keySet().toArray();
			Arrays.sort(keys);
			for (int i=0; i<keys.length; i++) {		
				String name = (String)keys[i];
				String classe = (String)((Hashtable)topLevelsData.get(name)).get("class");
				String xmlFileName = (String)((Hashtable)topLevelsData.get(name)).get("xml");
				attrs = new AttributesImpl();
				attrs.addAttribute("","name","","java.lang.String",name);
				attrs.addAttribute("","class","","java.lang.String",classe);
				attrs.addAttribute("","xml","","java.lang.String",xmlFileName);
				xml.emptyElement("","TopLevel","",attrs);
				xml.characters(newline, 0, 1);
			}
			attrs = new AttributesImpl();
			attrs.addAttribute("","name","","java.lang.String",defaultTopLevel);
			xml.emptyElement("","DefaultTopLevel","",attrs);
			xml.characters(newline, 0, 1);
			// Footer
			xml.endElement("MognoApplication");
			xml.endDocument();
		} catch (SAXException e) {
			throw new IOException("SAX error: "+e.getMessage());
		} finally {
			osw.close();
			fos.close();
		}
	}
}
