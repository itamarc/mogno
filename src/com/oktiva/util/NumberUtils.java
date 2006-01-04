/*
 * NumberUtils.java
 *
 * Created on quinta, 07 de novembro de 2002 13:10
 */
package com.oktiva.util;

import java.util.*;
import java.text.*;
import java.text.ParseException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/** Classe com m&eacute;todos utilit&aacute;rios para tratar n&uacute;meros.
 *
 * @author Itamar Almeida de Carvalho <itamar@oktiva.com.br>
 * @version $Id$
 */
public class NumberUtils {
	/** Testa se a string recebida representa um inteiro v&aacute;lido.
	 * @param i String representando um suposto inteiro.
	 * @return <b>true</b> se a string representar um inteiro v&aacute;lido, <b>false</b> caso contr&aacute;rio.
	 */
	public static boolean intValido(String i) {
		try {
			Integer.parseInt(i);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	/** Testa se a string recebida representa um double v&aacute;lido, segundo o default locale.
	 * @param d String representando um suposto double.
	 * @return <b>true</b> se a string representar um inteiro v&aacute;lido, <b>false</b> caso contr&aacute;rio.
	 */
	public static boolean doubleValido(String d) {
		try {
			DecimalFormatSymbols dfs=new DecimalFormatSymbols();
			if(!d.matches("^\\d+"+new Character(dfs.getDecimalSeparator())+"{0,1}\\d*$")) {
				return false;
			}
			NumberFormat nf = DecimalFormat.getInstance();
			nf.setParseIntegerOnly(false);
			nf.parse(d);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	/** Testa se a string recebida representa um float v&aacute;lido, segundo o default locale.
	 * @param d String representando um suposto float.
	 * @return <b>true</b> se a string representar um float v&aacute;lido, <b>false</b> caso contr&aacute;rio.
	 */
	public static boolean floatValido(String f) {
		return doubleValido(f);
	}
	
	/** Testa se a string recebida representa um float v&aacute;lido, segundo uma localidade.
	 * @param d String representando um suposto float.
	 * @param l Locale representando uma localidade
	 * @return <b>true</b> se a string representar um float v&aacute;lido, <b>false</b> caso contr&aacute;rio.
	 */
	public static boolean floatValido(String f, Locale l) {
		NumberFormat nf = NumberFormat.getInstance(l);
		try {
			nf.parse(f);
		} catch(ParseException e) {
			return false;
		}
		return true;
	}
	
	/** Retorna um float baseado em uma String de entrada, um número de casas decimais e
	 * uma localidade.
	 * @param f String representando um float
	 * @param d int representando o n&uacute;mero de casas decimais
	 * @param l Locale representando uma localidade
	 * @return Um float, caso seja um float v&aacute;lido ou <b>NaN</b>(Not a Number)
	 */
	public static float retornaFloat(String f, int d, Locale l) {
		NumberFormat nf = NumberFormat.getInstance(l);
		nf.setMinimumFractionDigits(d);
		nf.setMaximumFractionDigits(d);
		float valor = Float.NaN;
		try {
			// Converte e arredonda!
			valor = roundFloat(nf.parse(f).floatValue(), d);
		} catch(ParseException e) {
			valor = Float.NaN;
		}
		return valor;
	}
	
	/** Retorna um float baseado em uma String de entrada e
	 * uma localidade.
	 * @param f String representando um float
	 * @param l Locale representando uma localidade
	 * @return Um float, caso seja um float v&aacute;lido ou <b>NaN</b>(Not a Number)
	 */
	public static float retornaFloat(String f, Locale l) {
		NumberFormat nf = NumberFormat.getInstance(l);
		float valor = Float.NaN;
		try {
			valor = nf.parse(f).floatValue();
		} catch(ParseException e) {
			valor = Float.NaN;
		}
		return valor;
	}
	
	/** Arredonda um float com o n&uacute;mero de casas decimais especificados.
	 */
	public static float roundFloat(float f, int casasDecimais) {
		double potencia = Math.pow(10d, (double)casasDecimais);
		float ff = f*(float)potencia;
		return (float)(Math.round(ff)/potencia);
	}
	
	/** Retorna um double baseado em uma String de entrada, um n&uacute;mero de casas decimais e
	 * uma localidade.
	 * @param r String representando um double
	 * @param d int representando o n&uacute;mero de casas decimais
	 * @param l Locale representando uma localidade
	 * @return Um double, caso seja um double v&aacute;lido ou <b>NaN</b>(Not a Number)
	 */
	public static double retornaDouble(String d, int casasDecimais, Locale l) {
		NumberFormat nf = NumberFormat.getInstance(l);
		nf.setMinimumFractionDigits(casasDecimais);
		nf.setMaximumFractionDigits(casasDecimais);
		double valor = Double.NaN;
		try {
			// Converte e arredonda!
			valor = roundDouble(nf.parse(d).doubleValue(), casasDecimais);
		} catch(ParseException e) {
			valor = Double.NaN;
		}
		return valor;
	}
	
	/** Retorna um double baseado em uma String de entrada e
	 * uma localidade.
	 * @param d String representando um double
	 * @param l Locale representando uma localidade
	 * @return Um double, caso seja um double v&aacute;lido ou <b>NaN</b>(Not a Number)
	 */
	public static double retornaDouble(String d, Locale l) {
		NumberFormat nf = NumberFormat.getInstance(l);
		double valor = Double.NaN;
		try {
			valor = nf.parse(d).doubleValue();
		} catch(ParseException e) {
			valor = Double.NaN;
		}
		return valor;
	}
	
	/** Arredonda um double com o n&uacute;mero de casas decimais especificados.
	 */
	public static double roundDouble(double d, int casasDecimais) {
		double potencia = Math.pow(10d, (double)casasDecimais);
		double dd = d*potencia;
		return (double)(Math.round(dd)/potencia);
	}
	
	/** Calcula um d&iacute;gito verificador para o n&uacute;mero recebido
	 * como par&acirc;metro.
	 */
	public static String obterDigitoVerificador(String codigo) {
		if(codigo.indexOf("-")==-1) {
			// N�o calcula se j� tiver...
			// o "-" � o identificador de digito verificador
			int soma = 0;
			for(int i=0;i<codigo.length();i++) {
				String c = codigo.substring(i,i+1);
				try {
					int alg = Integer.parseInt(c);
					if(i%2==0) {
						if(alg*2>9) {
							soma += alg*2/10+alg*2%10;
						} else {
							soma += alg*2;
						}
					} else {
						soma += alg;
					}
				} catch (NumberFormatException e) {
					// ignorar tudo que n�o for n�mero.
					continue;
				}
			}
			return codigo+"-"+String.valueOf(soma%10);
		} else {
			return codigo;
		}
	}
	
	/** Divide o valor total passado em um n�mero de parcelas determinado,
	 * ajustando os valores das parcelas para a soma das parcelas ser
	 * exatamente o valor total, com 2 casas decimais.
	 */
	public static Double[] parcelar(Double valorTotal, int numParcelas) {
		Double[] retorno = new Double[numParcelas];
		double[] valores = new double[numParcelas];
		double valor = valorTotal.doubleValue();
		// dividir o valor e arredondar
		double valorParcela = NumberUtils.roundDouble(valor/numParcelas,2);
		// somar parcelas e ver diferen�a
		
		double soma = 0d;
		for(int i=0; i<numParcelas; i++) {
			soma = NumberUtils.roundDouble(soma+valorParcela,2);
			valores[i] = valorParcela;
		}
		// ajustar diferen�a
		if(valor > soma) {
			double diferenca = NumberUtils.roundDouble(valor-soma,2);
			int parc = 0;
			while(diferenca>0) {
				valores[parc] += 0.01d;
				diferenca -= 0.01d;
				parc++;
			}
		} else if(valor < soma) {
			double diferenca = NumberUtils.roundDouble(soma-valor,2);
			int parc = numParcelas-1;
			while(diferenca>0) {
				valores[parc] -= 0.01d;
				diferenca -= 0.01d;
				parc--;
			}
		}
		// converter
		for(int i=0; i<numParcelas; i++) {
			retorno[i] = new Double(NumberUtils.roundDouble(valores[i],2));
		}
		return retorno;
	}
	
	/** Retorna um n�mero formato com virgulas implicitas.
	 * @param num
	 * @param numCasasDecimais
	 * @param numCasasInteira
	 * @return
	 */	
	public static String virgulaImplicita(double num, int numCasasDecimais, int numCasasInteira) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(numCasasDecimais);
		nf.setMaximumFractionDigits(numCasasDecimais);
		nf.setMinimumIntegerDigits(numCasasInteira);
		nf.setMaximumIntegerDigits(numCasasInteira);
		nf.setGroupingUsed(false);
		String val = nf.format(num);
		String vals[] = val.split("\\.,",2);
		return vals[0]+vals[1];
	}
}