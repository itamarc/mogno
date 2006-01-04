/*
 * ComponentFiler.java
 *
 * Created on 3 de Fevereiro de 2003, 18:13
 */

package com.oktiva.mogno;

import com.oktiva.mogno.Component;
import java.io.*;
import java.util.*;

/**
 * @version $Id$
 * @author Itamar Carvalho &lt;itamar@oktiva.com.br&gt;
 */
public interface ComponentFiler {
	/** 
	 * @param params Hashtable contendo os par&acirc;metros para o filer.
	 */
	public void setParams(Hashtable params);
	/**
	 * @return Hashtable contendo dois outros sob as chaves "mine" e "owned".
	 * @throws IOException
	 */
	public Hashtable load() throws IOException;
	/**
	 * @return Hashtable contendo dois outros sob as chaves "mine" e "owned".
	 * @param in InputStream from where the data will be read.
	 * @throws IOException
	 */
	public Hashtable load(InputStream in) throws IOException;
	/**
	 * @param mine Hashtable contendo os atributos do compomente a ser salvo.
	 * @param owned Hashtable de hashtables contendo as propriedades dos 
	 *              componentes possu&iacute;dos pelo componente a ser salvo.
	 *		Os componentes ser&atilde;o salvos na ordem das chaves deste hash.
	 */
	public void store(Hashtable mine, Hashtable owned) throws IOException;
	/**
	 * @param mine Hashtable contendo os atributos do compomente a ser salvo.
	 * @param owned Vector de hashtables contendo as propriedades dos 
	 *              componentes possu&iacute;dos pelo componente a ser salvo, na ordem desejada.
	 */
	public void store(Hashtable mine, Vector owned) throws IOException;
}
