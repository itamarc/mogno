/*
 * DateUtils.java
 *
 * Created on terca, 05 de novembro de 2002 15:07
 */
package com.oktiva.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** Date util functions.
 */
public class DateUtils {
	public static String[] nomeMes = {"janeiro", "fevereiro", "mar&ccedil;o", "abril", "maio", "junho", "julho", "agosto",
	"setembro", "outubro", "novembro", "dezembro"};
	/** @return aaaa-mm-dd
	 */
	public static String gregorianCalendar2AMD(GregorianCalendar gc) {
		String data = gregorianCalendar2Iso(gc);
		return data.substring(0,4)+"-"+data.substring(4,6)+"-"+data.substring(6,8);
	}
	/** @return dd/mm/aaaa
	 */
	public static String gregorianCalendar2DMA(GregorianCalendar gc) {
		return gregorianCalendar2DMA(gc,"/");
	}
	/** @return dd+separador+mm+separador+aaaa
	 */
	public static String gregorianCalendar2DMA(GregorianCalendar gc,String separador) {
		String data = gregorianCalendar2Iso(gc);
		return data.substring(6,8)+separador+data.substring(4,6)+separador+data.substring(0,4);
	}
	
	/** Obtem um Gregorian Calendar de datas no formato Iso ou no formato
	 * dd-mm-aaaa. Aceita data com os separadores: -/
	 */
	public static GregorianCalendar getGregorianCalendar(String data) {
		int i[]=new int[3];
		String dataAConverter = data;
		
		if(dataValida(dataAConverter)) {
			dataAConverter = dma2Iso(dataAConverter);
		}
		StringTokenizer st=new StringTokenizer(dataAConverter,"-/");
		if(st.countTokens()!=3) {
			return null;
		} else {
			i[0]=Integer.parseInt(st.nextToken());
			i[1]=Integer.parseInt(st.nextToken());
			i[2]=Integer.parseInt(st.nextToken());
		}
		// Vou fazer a data retornada ter a hora, minuto e segundo
		// da data atual
		GregorianCalendar atual = new GregorianCalendar();
		return new GregorianCalendar(i[0],i[1]-1,i[2],atual.HOUR_OF_DAY,atual.MINUTE,atual.SECOND);
	}
	
	/** @return aaaammdd000000
	 */
	public static String gregorianCalendar2Iso(GregorianCalendar gc) {
		return gregorianCalendar2Iso(gc,false);
	}
	
	/** @return aaaammdd000000 se mostrarHora for <b>false</b>, aaaammddhh0000 se for <b>true</b>.
	 */
	public static String gregorianCalendar2Iso(GregorianCalendar gc, boolean mostrarHora) {
		if (gc == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(gc.get(gc.YEAR));
		String s = null;
		// Meses iniciam de 0 (janeiro)
		if ((s=String.valueOf(gc.get(gc.MONTH)+1)).length()==1) {
			sb.append("0"+s);
		} else {
			sb.append(s);
		}
		if ((s=String.valueOf(gc.get(gc.DAY_OF_MONTH))).length()==1) {
			sb.append("0"+s);
		} else {
			sb.append(s);
		}
		if (mostrarHora) {
			if ((s=String.valueOf(gc.get(gc.HOUR_OF_DAY))).length()==1) {
				sb.append("0"+s);
			} else {
				sb.append(s);
			}
			sb.append("0000");
		} else {
			sb.append("000000");
		}
		return sb.toString();
	}
	
	/** @param ano Ano da data a testar.
	 * @param mes M&ecirc;s da data a testar (1=Janeiro, ..., 12=Dezembro).
	 * @param dia Dia da data a testar.
	 * @return <b>true</b> se a data for v&aacute;lida, <b>false</b> caso contr&aacute;rio.
	 */
	public static boolean dataValida(int ano, int mes, int dia) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setLenient(false);
		try {
			gc.set(ano,mes-1,dia,0,0,0);
			gc.getTime();
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	/** @param Iso Data em um dos formatos: <ul><li>"aaaa-mm-dd". mm e dd não precisam ter 0 na frente.
	 * <li>"aaaammdd", podendo ter outros d&iacute;gitos ap�s "dd" (como hora, minuto e segundo).
	 * Estes d&iacute;gitos extras s&atilde;o ignorados.</ul>
	 * @return <b>true</b> se a data for v&aacute;lida, <b>false</b> caso contr&aacute;rio.
	 */
	public static boolean dataIsoValida(String iso) {
		if (iso == null || iso.equals("")) {
			return false;
		}
		if(iso.indexOf("-") > -1) {
			StringTokenizer st=new StringTokenizer(iso,"-");
			if(st.countTokens()==3) {
				int ano=0,mes=0,dia=0;
				ano=Integer.parseInt(st.nextToken());
				mes=Integer.parseInt(st.nextToken());
				dia=Integer.parseInt(st.nextToken());
				return dataValida(ano,mes,dia);
			}
		} else if (iso.length()>=8) {
			String ano,mes,dia;
			ano=iso.substring(1,4);
			mes=iso.substring(5,6);
			dia=iso.substring(7,8);
			return dataValida(Integer.parseInt(ano),Integer.parseInt(mes),Integer.parseInt(dia));
		}
		return false;
	}
	
	/** @deprecated Este m&eacute;todo n&atilde;o deve ser usado.  Use dataIsoValida().
	 * @see #dataIsoValida(String iso)
	 */
	public static boolean isoValida(String iso) {
		return dataIsoValida(iso);
	}
	
	/** @param data Data no formato "dd/mm/aaaa".
	 * @return <b>true</b> se a data for v&aacute;lida, <b>false</b> caso contr&aacute;rio.
	 */
	public static boolean dataValida(String data) {
		if (data == null) {
			return false;
		}
		String[] dma = data.split("/");
		if (dma.length!=3) {
			return false;
		}
		try {
			return dataValida(Integer.parseInt(dma[2]),Integer.parseInt(dma[1]),Integer.parseInt(dma[0]));
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	public static boolean dataHoraValida(String dataHora) {
		if (dataHora == null) {
			return false;
		}
		String[] dmahm = dataHora.split(" ");
		if (dmahm.length != 2) {
			return false;
		}
		try {
			String data = dmahm[0];
			String hora = dmahm[1];
			if (dataValida(data) && horaValida(hora)) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	/** @param data Data dd/mm/aaaa
	 * @return Data aaaa-mm-dd
	 */
	public static String dma2Iso(String data) {
		if (!dataValida(data)) {
			return null;
		}
		String[] dma = data.split("/");
		StringBuffer sb = new StringBuffer();
		
		// ano
		sb.append(dma[2]).append("-");
		
		// mes
		if (dma[1].length() == 1) {
			sb.append("0");
		}
		sb.append(dma[1]).append("-");
		
		// dia
		if (dma[0].length() == 1) {
			sb.append("0");
		}
		sb.append(dma[0]);
		
		return sb.toString();
	}
	
	public static Date dmahm2Data(String dmahm) throws ParseException {
		Locale l = Locale.getDefault();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", l);
		return formatter.parse(dmahm);
	}
	
	/** @param data Data aaaa-mm-dd
	 * @return Data dd/mm/aaaa
	 */
	public static String iso2dma(String data) {
		if (!isoValida(data)) {
			return null;
		}
		String[] dma = data.split("-");
		StringBuffer sb = new StringBuffer();
		
		// dia
		if (dma[2].length() == 1) {
			sb.append("0");
		}
		sb.append(dma[2]).append("/");
		
		// mes
		if (dma[1].length() == 1) {
			sb.append("0");
		}
		sb.append(dma[1]).append("/");
		
		// ano
		sb.append(dma[0]);
		
		return sb.toString();
	}
	
	/**
	 * @param data1 Data dd/mm/aaaa
	 * @param data2 Data dd/mm/aaaa
	 * @return N&uacute;mero de dias entre as datas.  Retorna -1 caso uma ou ambas as datas sejam inv&aacute;lidas.
	 * @see diferencaEntreDatasIso
	 */
	public static int diferencaEntreDatas(String data1, String data2) {
		return diferencaEntreDatasIso(dma2Iso(data1),dma2Iso(data2));
	}
	
	/** @param data1 Data aaaa-mm-dd
	 * @param data2 Data aaaa-mm-dd
	 * @return N&uacute;mero de dias entre as datas.  Retorna -1 caso uma ou ambas as datas sejam inv&aacute;lidas.
	 */
	public static int diferencaEntreDatasIso(String data1, String data2) {
		int dias=-1;
		if (!dataIsoValida(data1) || !dataIsoValida(data2)) {
			return dias;
		}
		GregorianCalendar gc1 = getGregorianCalendar(data1);
		GregorianCalendar gc2 = getGregorianCalendar(data2);
		dias = (int)((gc1.getTimeInMillis() - gc2.getTimeInMillis())/(1000*60*60*24));
		return Math.abs(dias);
	}
	
	/** Verifica se, para um dado vencimento, h� atraso.
	 * @param vencimento Data no formato aaaa-mm-dd
	 * @return <b>false</b> se o vencimento � hoje ou no futuro, <b>true</b> se foi at� ontem.
	 */
	public static boolean atrasado(String vencimento) {
		GregorianCalendar gcVenc = DateUtils.getGregorianCalendar(vencimento);
		GregorianCalendar gcHoje = new GregorianCalendar();
		gcHoje.set(gcHoje.get(gcHoje.YEAR),gcHoje.get(gcHoje.MONTH),gcHoje.get(gcHoje.DAY_OF_MONTH), 0, 0, 0);
		gcHoje.set(gcHoje.MILLISECOND, 0);
		if (gcVenc.before(gcHoje)) {
			return true;
		}
		return false;
	}
	
	public static boolean horaValida(String hora) {
		String[] horaSplit = hora.split(":");
		if(horaSplit.length<2 || horaSplit.length>3) {
			return false;
		} else {
			if(NumberUtils.intValido(horaSplit[0])) {
				if(Integer.parseInt(horaSplit[0])<0 || Integer.parseInt(horaSplit[0])>23) {
					return false;
				}
			} else {
				return false;
			}
			if(NumberUtils.intValido(horaSplit[1])) {
				if(Integer.parseInt(horaSplit[1])<0 || Integer.parseInt(horaSplit[1])>59) {
					return false;
				}
			} else {
				return false;
			}
			if(horaSplit.length==3) {
				if(NumberUtils.intValido(horaSplit[2])) {
					if(Integer.parseInt(horaSplit[2])<0 || Integer.parseInt(horaSplit[2])>59) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param hora Hora no formato hh:mm ou hh:mm:ss
	 * @return Hora no formato hh:mm:ss (incluindo zeros em ss se necess&aacute;rio).
	 * Null caso a hora passada n�o seja v&aacute;lida.
	 */
	public static String retornaHora(String hora) {
		if (!horaValida(hora)) {
			return null;
		}
		String[] horaSplit = hora.split(":");
		if(horaSplit.length==2) {
			return hora+":00";
		} else {
			return hora;
		}
	}
	/**
	 * @param hora Hora no formato hh:mm ou hh:mm:ss
	 * @return Hora no formato hh:mm
	 * Null caso a hora passada n�o seja v&aacute;lida.
	 */
	public static String retornaHoraHM(String hora) {
		if(!horaValida(hora)) {
			return null;
		}
		String[] horaSplit = hora.split(":");
		if(horaSplit.length==3) {
			return horaSplit[0]+":"+horaSplit[1];
		}
		return hora;
	}
	/**
	 * @param gc GregorianCalendar da data
	 * @return String no formato de uma data em extenso.
	 */
	public static String dataPorExtenso(GregorianCalendar gc) {
		return gc.get(gc.DAY_OF_MONTH)+" de "+nomeMes[gc.get(gc.MONTH)]+" de "+gc.get(gc.YEAR);
	}
	/**
	 * Constr�i uma String representando o dia de hoje no formato Iso.
	 * @return String representando o dia atual.
	 */
	public static String hoje() {
		return dma2Iso(gregorianCalendar2DMA(new GregorianCalendar()));
	}
	
	
	/**
	 * Verifica se data est� entre limiteInferior e limiteSuperior.
	 *@param String limite inferior
	 *@param String limite superior
	 *@param String data a ser testada
	 *@return boolean Retorna true caso esteja na faixa. Caso alguma das datas seja inv�lida
	 * ou a data testada n�o esteja nas faixa retorna false.
	 */
	public static boolean verificarFaixa(String limiteInferior, String limiteSuperior, String data) {
		// Verifica se as 3 datas s�o datas Iso ou dma v�lidas.
		if((DateUtils.dataValida(limiteSuperior)||DateUtils.dataIsoValida(limiteSuperior))&&(DateUtils.dataValida(limiteInferior)||DateUtils.dataIsoValida(limiteInferior))
		&&(DateUtils.dataValida(data)||DateUtils.dataIsoValida(data))) {
			GregorianCalendar li = getGregorianCalendar(limiteInferior);
			GregorianCalendar ls = getGregorianCalendar(limiteSuperior);
			GregorianCalendar dt = getGregorianCalendar(data);
			if((ls.after(li))&&(!dt.before(li) && !dt.after(ls))) {
				return true;
			}
		}
		return false;
		
	}
	
	/** Calcula as datas de vencimentos, a partir de uma data inicial (inclu�da
	 * no retorno), dos dias entre as parcelas, dos dias antes da primeira
	 * parcela (car�ncia) e do n�mero de parcelas.
	 */
	public static Date[] obterVencimentosParcelas(GregorianCalendar dataInicial, int diasEntreParcelas, int diasCarencia, int numParcelas) {
		Date[] vencs = new Date[numParcelas];
		if(diasCarencia>0) {
			dataInicial.add(GregorianCalendar.DAY_OF_MONTH, diasCarencia);
		}
		vencs[0] = dataInicial.getTime();
		for(int i=1; i<numParcelas; i++) {
			dataInicial.add(GregorianCalendar.DAY_OF_MONTH,diasEntreParcelas);
			vencs[i] = dataInicial.getTime();
		}
		return vencs;
	}
	
	/** Recebe um objeto Date e retorna a data no formato dd/mm/aaaa.
	 */
	public static String date2dma(Date data) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(data);
		return gregorianCalendar2DMA(gc);
	}
	
	public static String date2dmahm(Date data) {
		Locale l = Locale.getDefault();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", l);
		return formatter.format(data);
	}
	
	/** Recebe um objeto Date e retorna a data no formato ISO.
	 */
	public static String date2AMD(Date data) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(data);
		return gregorianCalendar2AMD(gc);
	}
	
	/** Pega um objeto Date e transforma numa string que representa o per�odo,
	 * dada uma certa periodicidade.<br>
	 * @param data Data a ser convertida.
	 * @param periodicidade String representando a periodicidade.
	 * <b>NOTA: atualmente somente suporta periodicidade "mensal" e "anual".</b>
	 * @return Uma string que representa um per�odo.  Por exemplo: <tt>02/2003</tt>
	 * null em caso de erro.
	 * @see #periodo2date
	 * @see java.text.SimpleDateFormat
	 */
	public static String date2periodo(Date data, String periodicidade) {
		String periodo = null;
		try {
			String pattern = "";
			if ("mensal".equals(periodicidade)) {
				pattern = "MM/yyyy";
			} else if ("anual".equals(periodicidade)) {
				pattern = "yyyy";
			}
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			periodo = df.format(data);
		} catch (Exception e) {
			// Vai retornar null somente.
		}
		return periodo;
	}
	
	/** Pega uma string representando um per�odo e transforma em um date.<br>
	 * Campos n�o pertinentes no per�odo s�o transformados em valores padr�o:<br>
	 * <tt>yyyy-01-01 00:00:00,0</tt><br>
	 * (O ano n�o cont�m valor padr�o, pois est� presente em qualquer periodicidade.)
	 * @param periodo String representando um per�odo.  Por exemplo: <tt>02/2003</tt>
	 * @param periodicidade String representando a periodicidade.
	 * <b>NOTA: atualmente somente suporta periodicidade "mensal" e "anual".</b>
	 * @return Um objeto Date relativo ao per�odo, ou null em caso de erro.
	 * @see #date2periodo
	 * @see java.text.SimpleDateFormat
	 */
	public static Date periodo2date(String periodo, String periodicidade) {
		Date data = null;
		try {
			if ("mensal".equals(periodicidade)) {
				SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
				df.setLenient(false);
				data = df.parse(periodo);
			} else if ("anual".equals(periodicidade)) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy");
				df.setLenient(false);
				data = df.parse(periodo);
			}
		} catch (Exception e) {
			// Vai retornar null somente.
		}
		return data;
	}
}
