package com.oktiva.mogno.additional;

import com.oktiva.mogno.Component;
import com.oktiva.mogno.html.Option;
import com.oktiva.mogno.additional.SelectView;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/** SELECT tag (defined by W3C) with the brazilian UFs.<br>
 * @version $Id$
 * @see com.oktiva.mogno.html.Form
 * @see com.oktiva.mogno.html.Option
 * @see com.oktiva.mogno.html.Select
 */
public class SelectUFBR extends SelectView {

	public boolean compact=false;
	public String show()
	throws Exception {
		String[][] ufs = {
			{"AC","Acre"},
			{"AL","Alagoas"},
			{"AP","Amap&aacute;"},
			{"AM","Amazonas"},
			{"BA","Bahia"},
			{"CE","Cear&aacute;"},
			{"DF","Distrito Federal"},
			{"ES","Esp&iacute;rito Santo"},
			{"GO","Goi&aacute;s"},
			{"MA","Maranh&atilde;o"},
			{"MT","Mato Grosso"},
			{"MS","Mato Grosso do Sul"},
			{"MG","Minas Gerais"},
			{"PA","Par&aacute;"},
			{"PB","Paraiba"},
			{"PR","Paran&aacute;"},
			{"PE","Pernambuco"},
			{"PI","Piau&iacute;"},
			{"RJ","Rio de Janeiro"},
			{"RN","Rio Grande do Norte"},
			{"RS","Rio Grande do Sul"},
			{"RO","Rond&ocirc;nia"},
			{"RR","Roraima"},
			{"SC","Santa Catarina"},
			{"SP","S&atilde;o Paulo"},
			{"SE","Sergipe"},
			{"TO","Tocantins"}
		};

		Arrays.sort(ufs,new AComp(isCompact()));
		
		if (!getRequired()) {
			Option o=new Option();
			o.setValue("");
			o.setContent(compact?"--":"Selecione");
			o.setParent(getName());
			o.setSelect(getName());
			o.setName(getName()+"_NENHUM");
			o.setTop(0);
			getOwner().registerChild(o);
		}
		
		for(int i=0;i<ufs.length;i++) {
			Option o=new Option();
			o.setValue(ufs[i][0]);
			o.setContent(compact?ufs[i][0]:ufs[i][1]);
			o.setParent(getName());
			o.setSelect(getName());
			o.setName(getName()+"_"+ufs[i][0]);
			o.setTop(i + 1);
			getOwner().registerChild(o);
		}
		return super.show();
	}

	/** If it should use an abreviated name for the option content.
	 * @return Value of property compact.
	 *
	 */
	public boolean isCompact() {
		return compact;
	}
	
	/** Setter for property compact.
	 * @param compact New value of property compact.
	 *
	 */
	public void setCompact(boolean compact) {
		this.compact = compact;
	} 

	
	public SelectUFBR() {
		super();
	}
	
	private class AComp implements java.util.Comparator {
		private boolean compact;
		public AComp(boolean compact) {
			this.compact = compact;
		}
		public int compare(Object a, Object b) {
			String[] o1 = (String[])a;
			String[] o2 = (String[])b;
			String d1 = o1[compact?0:1];
			String d2 = o1[compact?0:1];
			if (d1 != null && d2 != null) {
				return d1.compareTo(d2);
			} else if (d1 == null && d2 == null) {
				return 0;
			} else if (d1 == null) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}

