/*
 * ApplicationFiler.java
 *
 * Created on quinta, 06 de fevereiro de 2003, 10:46
 */
package com.oktiva.mogno;

import java.io.*;
import java.util.*;

/**
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 */
public interface ApplicationFiler {
	/** 
	 * @param params Hashtable contendo os parâmetros para o filer.
	 */
	public void setParams(Hashtable params);
	/**
	 * @return Hashtable contendo: um Hashtable sob a chave "topLevelsData"
	 * e um String sob a chave "defaultTopLevel".  O hash terá as chaves 
	 * "class" e "xml" dentro dele, sob chaves com os nomes dos topLevels.
	 */
	public Hashtable load() throws IOException;
	/**
	 * @param topLevelsData Hashtable contendo os atributos dos TopLevels da aplicação a ser salva.
	 * @param defaultTopLevel String com o nome do TopLevel default.
	 */
	public void store(Hashtable topLevelsData, String defaultTopLevel) throws IOException;
}
