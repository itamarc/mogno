package com.oktiva.mogno.layout;

import com.oktiva.mogno.Visual;
import java.util.Hashtable;
import java.util.Vector;

/** This class is an optimized Table, that uses top and left to position this
 * children components in rows and cols.<p>
 * It permits you to set the attributes for his cols and rows, and to define wich of them
 * are not TDs, but THs.<br>
 * You can also define attributes for specific cells.<br>
 * This class uses the very ugly Component attributes <tt>gridHeight</tt>,
 * <tt>gridWidth</tt>, <tt>gridWeightX</tt> and <tt>gridWeightY</tt>.
 * I hope someday we will get rid of them...   :)
 */
public class TableLayout extends com.oktiva.mogno.html.Table {
	// Look up comments in the getters and setters...
	public String caption = "";
	public String captionProperties = "";
	public String headerRows = "";
	public String headerCols = "";
	public String headerCells = "";
	public String headerProperties = "";
	public String cellProperties = "";
	public String colProperties = "";
	public String rowProperties = "";
	private long isHeaderRow;
	private long isHeaderCol;
	private Vector isHeaderCell = new Vector();
	private String captionPropsHtml;
	private String headerPropsHtml;
	private Hashtable cellPropsHtml = new Hashtable();
	private Hashtable colPropsHtml = new Hashtable();
	private Hashtable rowPropsHtml = new Hashtable();
	
	/** Parse the strings with the various properties and call the <tt>super.show()</tt>
	 * method.
	 */
	public String show()
	throws Exception {
		//headerRows: 1,2
		String[] headerRowsArr = headerRows.split(",");
		for(int i=0;i<headerRowsArr.length;i++) {
			try {
				Integer ii=new Integer(headerRowsArr[i]);
				isHeaderRow |= (1 << (ii.intValue()-1));
			} catch (Exception e) {}
		}
		//headerCols: 2,5
		String[] headerColsArr = headerCols.split(",");
		for(int i=0;i<headerColsArr.length;i++) {
			try {
				Integer ii=new Integer(headerColsArr[i]);
				isHeaderCol |= (1 << (ii.intValue()-1));
			} catch (Exception e) {}
		}
		//headerCells: 1,2&&3,0
		String[] headerCellsArr = headerCells.split("\\&\\&");
		for (int i=0; i<headerCellsArr.length; i++) {
			isHeaderCell.add(headerCellsArr[i]);
		}
		//captionProperties: prop1=value1|prop2=value2
		captionPropsHtml = getPropertiesHtmlString(captionProperties);
		//headerProperties: prop1=value1|prop2=value2
		headerPropsHtml = getPropertiesHtmlString(headerProperties);
		//cellProperties: top,left:prop1=value1|prop2=value2&&top,left:prop1=value1|prop2=value2
		String[] cellPropsArr = cellProperties.split("\\&\\&");
		for (int i=0; i<cellPropsArr.length; i++) {
			String[] itens = cellPropsArr[i].split(":",2);
			if (itens.length==2) {
				cellPropsHtml.put(itens[0], getPropertiesHtmlString(itens[1]));
			}
		}
		//colProperties: left:prop1=value1|prop2=value2&&left:prop1=value1|prop2=value2
		String[] colPropsArr = colProperties.split("\\&\\&");
		for (int i=0; i<colPropsArr.length; i++) {
			String[] itens = colPropsArr[i].split(":",2);
			if (itens.length==2) {
				colPropsHtml.put(itens[0], getPropertiesHtmlString(itens[1]));
			}
		}
		//rowProperties: top:prop1=value1|prop2=value2&&top:prop1=value1|prop2=value2
		String[] rowPropsArr = rowProperties.split("\\&\\&");
		for (int i=0; i<rowPropsArr.length; i++) {
			String[] itens = rowPropsArr[i].split(":",2);
			if (itens.length==2) {
				rowPropsHtml.put(itens[0], getPropertiesHtmlString(itens[1]));
			}
		}
		return super.show();
	}
	
	public String startRow(int top) {
		return "<TR"+getRowProperties(top)+">";
	}
	
	public String endRow(int top) {
		return "</TR>\n";
	}
	
	public String startColumn(int top, int left, Visual comp) {
		int height = 1;
		int width = 1;
		int weightx = 0;
		int weighty = 0;
		if (comp != null) {
			height = comp.getGridHeight();
			width = comp.getGridWidth();
			weightx = comp.getGridWeightX();
			weighty = comp.getGridWeightY();
		}
		StringBuffer s = new StringBuffer(30);
		if ((isHeaderRow & (1 << top))!=0 ||
		(isHeaderCol & (1 << left))!=0 ||
		isHeaderCell.contains(""+top+","+left)) {
			s.append("<TH").append(headerPropsHtml);
		} else {
			s.append("<TD");
		}
		s.append(getColProperties(left)).append(getCellProperties(top,left));
		if (height != 1) {
			s.append(" rowspan='"+height+"'");
		}
		if (weightx != 0) {
			s.append(" width='"+weightx+"%'");
		}
		if (weighty != 0) {
			s.append(" height='"+weighty+"%'");
		}
		if (width != 1) {
			s.append(" colspan='"+width+"'");
		}
		s.append(">");
		return s.toString();
	}
	
	public String endColumn(int top, int left, Visual comp) {
		if ((isHeaderRow & (1 << top))!=0 ||
		(isHeaderCol & (1 << left))!=0 ||
		isHeaderCell.contains(""+top+","+left)) {
			return "</TH>";
		} else {
			return "</TD>";
		}
	}

	private String getCellProperties(int top, int left) {
		String key = ""+top+","+left;
		if (cellPropsHtml.containsKey(key)) {
			return (String)cellPropsHtml.get(key);
		} else {
			return "";
		}
	}
	
	private String getRowProperties(int top) {
		String key = String.valueOf(top);
		if (rowPropsHtml.containsKey(key)) {
			return (String)rowPropsHtml.get(key);
		} else {
			return "";
		}
	}
	
	private String getColProperties(int left) {
		String key = String.valueOf(left);
		if (colPropsHtml.containsKey(key)) {
			return (String)colPropsHtml.get(key);
		} else {
			return "";
		}
	}
	
	public String startContainer() {
		String ret = "<"+tag+showHtmlAttributes()+">";
		if (!"".equals(caption)) {
			ret += "<CAPTION"+captionPropsHtml+">"+caption+"</CAPTION>";
		}
		return ret;
	}
	
	protected String getPropertiesHtmlString(String propString) {
		StringBuffer sb = new StringBuffer();
		String[] info = propString.split("\\|");
		for(int j=0; j<info.length; j++) {
			String[] prop = info[j].split("=",2);
			if (prop.length == 2 && prop[0] != null && !"".equals(prop[0]) && prop[1] != null && !"".equals(prop[1])) {
				sb.append(" ").append(prop[0]).append("=\"").append(prop[1]+"\"");
			}
		}
		return sb.toString();
	}
	
	/** Getter for the caption of the table.
	 * @return Value of property caption.
	 */
	public String getCaption() {
		return caption;
	}
	/** Setter for the caption of the table.
	 * @param caption New value of property caption.
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/** Getter for property captionProperties.<p>
	 * Format of captionProperties: prop1=value1|prop2=value2
	 * @return Value of property captionProperties.
	 */
	public String getCaptionProperties() {
		return captionProperties;
	}
	/** Setter for property captionProperties.
	 * Format of captionProperties: prop1=value1|prop2=value2
	 * @param captionProperties New value of property captionProperties.
	 */
	public void setCaptionProperties(String captionProperties) {
		this.captionProperties = captionProperties;
	}
	
	/** Getter for property headerRows.<p>
	 * Format of headerRows: 1,2
	 * @return Value of property headerRows.
	 */
	public String getHeaderRows() {
		return headerRows;
	}
	/** Setter for property headerRows.
	 * Format of headerRows: 1,2
	 * @param headerRows New value of property headerRows.
	 */
	public void setHeaderRows(String headerRows) {
		this.headerRows = headerRows;
	}
	
	/** Getter for property headerCols.<p>
	 * Format of headerCols: 2,5
	 * @return Value of property headerCols.
	 */
	public String getHeaderCols() {
		return headerCols;
	}
	/** Setter for property headerCols.
	 * Format of headerCols: 2,5
	 * @param headerCols New value of property headerCols.
	 */
	public void setHeaderCols(String headerCols) {
		this.headerCols = headerCols;
	}

	/** Getter for property headerCells.<p>
	 * Format of headerCells (top,left): 2,5&&3,0
	 * @return Value of property headerCells.
	 */
	public String getHeaderCells() {
		return headerCells;
	}
	/** Setter for property headerCells.
	 * Format of headerCells (top,left): 2,5&&3,0
	 * @param headerCells New value of property headerCells.
	 */
	public void setHeaderCells(String headerCells) {
		this.headerCells = headerCells;
	}

	/** Getter for property headerProperties.<p>
	 * Format of headerProperties: prop1=value1|prop2=value2
	 * @return Value of property headerProperties.
	 */
	public String getHeaderProperties() {
		return headerProperties;
	}
	/** Setter for property headerProperties.
	 * Format of headerProperties: prop1=value1|prop2=value2
	 * @param headerProperties New value of property headerProperties.
	 */
	public void setHeaderProperties(String headerProperties) {
		this.headerProperties = headerProperties;
	}
	
	/** Getter for property cellProperties.<p>
	 * Format of cellProperties: top,left:prop1=value1|prop2=value2&&top,left:prop1=value1|prop2=value2
	 * @return Value of property cellProperties.
	 */
	public String getCellProperties() {
		return cellProperties;
	}
	/** Setter for property cellProperties.
	 * Format of cellProperties: top,left:prop1=value1|prop2=value2&&top,left:prop1=value1|prop2=value2
	 * @param cellProperties New value of property cellProperties.
	 */
	public void setCellProperties(String cellProperties) {
		this.cellProperties = cellProperties;
	}
	
	/** Getter for property colProperties.<p>
	 * Format of colProperties: left:prop1=value1|prop2=value2&&left:prop1=value1|prop2=value2
	 * @return Value of property colProperties.
	 */
	public String getColProperties() {
		return colProperties;
	}
	/** Setter for property colProperties.
	 * Format of colProperties: left:prop1=value1|prop2=value2&&left:prop1=value1|prop2=value2
	 * @param colProperties New value of property colProperties.
	 */
	public void setColProperties(String colProperties) {
		this.colProperties = colProperties;
	}
	
	/** Getter for property rowProperties.<p>
	 * Format of rowProperties: top:prop1=value1|prop2=value2&&top:prop1=value1|prop2=value2
	 * @return Value of property rowProperties.
	 */
	public String getRowProperties() {
		return rowProperties;
	}
	/** Setter for property rowProperties.
	 * Format of rowProperties: top:prop1=value1|prop2=value2&&top:prop1=value1|prop2=value2
	 * @param rowProperties New value of property rowProperties.
	 */
	public void setRowProperties(String rowProperties) {
		this.rowProperties = rowProperties;
	}
}
