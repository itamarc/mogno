/*
 * TableList.java
 *
 * Created on Wednesday, June 04 2003 16:47
 */
package com.oktiva.mogno.additional;

import com.oktiva.mogno.Component;
import com.oktiva.mogno.html.A;
import com.oktiva.mogno.html.Table;
import com.oktiva.mogno.html.Td;
import com.oktiva.mogno.html.Th;
import com.oktiva.mogno.html.Tr;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/** Creates a list with a table (supporting a link with onClick).<br>
 * This component takes the data in the dataVector property and creates a table where the
 * lines are used as the items itself, with links in one or more columns.<br>
 * If this TableList is <i>active</i>, the id passed for each row's Vector will be used
 * as the identificator for the item in the link.
 * @version $Id$
 */
public class TableList extends Table {
	/** Vector with the item's ids.
	 */
	protected Vector idVector = null;
	/** Vector of Vectors with the table data.
	 */
	protected Vector dataVector = null;
	/** The HTML code with COLGROUPs and COLs.
	 */
	public String colgroups = "";
	/** Vector with the data for TBODYs, THEADs and TFOOTs.
	 */
	protected Vector groupVector = null;
	/** Vector with the groupId of each row.
	 */
	protected Vector groupIdVector = null;
	/** The header row group type.  A String that can be TBODY, THEAD or TFOOT.
	 */
	public String headerGroupType = "";
	/** The header row group id.  Any String that will identify all the rows inside de same group.
	 */
	public String headerGroupId = "";
	/** The total row group type.  A String that can be TBODY, THEAD or TFOOT.
	 */
	public String totalLineGroupType = "";
	/** The total row group id.  Any String that will identify all the rows inside de same group.
	 */
	public String totalLineGroupId = "";
	/** String with the comumn's names, in the following format:<br>
	 * name1&name2&name3...
	 * Limited to 64 columns, due to internal optimizations.
	 */
	public String columnIdentifiers = "";
	public String getColumnIdentifiers() {
		return columnIdentifiers;
	}
	public void setColumnIdentifiers(String columnIdentifiers) {
		this.columnIdentifiers = columnIdentifiers;
	}
	
	/** The String that will be displayed in the first column of the total line
	 */
	public String totalLineLabel = "";
	
	/** The number of the columns taht will be totalized, separated by <code>&amp;</code>.<br>
	 */
	public String totalColumns = "";
	
	/** The max number of fractionary digists given to DecimalFormat
	 */
	public int totalMaximumFractionDigits = 2;
	/** The minimum number of fractionary digists given to DecimalFormat
	 */
	public int totalMinimumFractionDigits = 2;
	
	/** The number of the columns where there will be the link, separated by <code>&amp;</code>.<br>
	 * Defaults to the first column.  Column numbers starts with 1.
	 */
	public String linkColumns = "1";
	
	/** Properties of the Tr of the table header, in the following format:<br>
	 * <code>attr1=val1|attr2=val2|attr3=val3...</code><br>
	 * Example: <code>style=color: #808080;|valign=top</code>
	 * @see Tr
	 */
	public String headerRowProperties = "";
	/** Properties of the Th's of the table header, in the following format:<br>
	 * <code>col1attr1=val1|col1attr2=val2&col2attr3=val3...</code><br>
	 * Example: <code>style=color: #808080; width=30%;|align=left&style=width=70%|align=right</code>
	 * @see Th
	 */
	public String thProperties = "";
	/** Properties of the Tr's of the table body, in the following format:<br>
	 * <code>attr1=val1|attr2=val2|attr3=val3...</code><br>
	 * Example: <code>styleClass=myRowStyle;</code>
	 * @see Tr
	 */
	public String itemRowProperties = "";
	/** Properties of the Td's of the table body, in the following format:<br>
	 * <code>col1attr1=val1|col1attr2=val2&col2attr3=val3...</code><br>
	 * Example: <code>style=background-color: #ffffff;|align=left&align=right</code>
	 * @see Td
	 */
	public String tdProperties = "";
	
	/** The seleeted itens
	 */
	public Vector selectedItens;
	public Vector getSelectedItens() {
		return selectedItens;
	}
	public void setSelectedItens(Vector newSelectedItens) {
		this.selectedItens = newSelectedItens;
	}
	public String getSelectedItem() {
		if (getSelectedItens().size() > 0) {
			return (String)selectedItens.get(0);
		} else {
			return null;
		}
	}
	public void setSelectedItem(String selectedItem) {
		Vector t = new Vector();
		t.add(selectedItem);
		setSelectedItens(t);
	}
	
	/** Activate/deactivate this TableList.
	 * An inactive TableList doesn't have a link.
	 */
	public boolean active = true;
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 *  if true and active, a checkbox will be shown for each line
	 */
	public boolean allowMultiple = false;
	public boolean isAllowMultiple() {
		return allowMultiple;
	}
	public void setAllowMultiple(boolean newAllowMultiple) {
		this.allowMultiple = newAllowMultiple;
	}
	
	/** The url of this web application */
	public String applicationUrl = "";
	public String getApplicationUrl() {
		return applicationUrl;
	}
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	
	/** Another params (in URL encoded format) to add to the link */
	public String params = "";
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	/** The <i>mognoOrigin</i> param, defaults to <tt>owner.name</tt>. */
	public String origin = "";
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	/** Event dispatched when a row's link is clicked. */
	public String evOnClick;
	public String getEvOnClick() {
		return evOnClick;
	}
	public void setEvOnClick(String evOnClick) {
		this.evOnClick = evOnClick;
	}
	
	public Component selectParentComponent() {
		return this;
	}
	
	public String show()
	throws Exception {
		NumberFormat nf = NumberFormat.getInstance();
		StringBuffer ret = new StringBuffer(4096);
		if ((dataVector != null && dataVector.size()>0) || isDesigning()) {
			ret.append("<TABLE").append(showHtmlAttributes()).append(">");
			// Put the colgroups
			if (colgroups != null) {
				ret.append(colgroups);
			}
			// Build the header row
			ret.append(buildRowGroupHtml(-1)); // -1 is the pseudo-row for the header line
			ret.append("<TR").append(getPropertiesHtmlString(headerRowProperties)).append(">");
			// Get the identifiers
			String[] colNames = columnIdentifiers.split("\\&");
			int cols = colNames.length;
			String[] colHeaderProperties = thProperties.split("\\&",cols);
			long hasColHeaderProperties = strArrToBitCache(colHeaderProperties);
			for (int i=0; i < cols; i++) {
				ret.append("<TH");
				if ((hasColHeaderProperties & (1 << i))!=0) {
					ret.append(getPropertiesHtmlString(colHeaderProperties[i]));
				}
				ret.append(">").append(colNames[i]).append("</TH>");
			}
			ret.append("</TR>\n");
			// Build the item's rows
			long hasLink=0;
			if (active) {
				String[] links = linkColumns.split("\\&",cols);
				for(int i=0;i<links.length;i++) {
					Integer ii=new Integer(links[i]);
					hasLink |= (1 << (ii.intValue()-1));
				}
			}
			
			String[] colProperties = tdProperties.split("\\&",cols);
			long hasColProperties = strArrToBitCache(colProperties);
			
			/* Setup totalization array */
			long isTotalized = 0;
			double totals[] = null;
			if(getTotalColumns() != null && !getTotalColumns().equals("")) {
				String totalized[] = totalColumns.split("\\&",cols);
				for(int i=0;i<totalized.length;i++) {
					Integer ii=new Integer(totalized[i]);
					isTotalized |= (1 << (ii.intValue()-1));
				}
				totals = new double[cols];
				for(int i=0;i<cols;i++) {
					totals[i]=0;
				}
			}
			
			if (!isDesigning()) {
				int rows = dataVector.size();
				for (int row=0; row < rows; row++) {
					ret.append(buildRowGroupHtml(row));
					ret.append("<TR").append(getPropertiesHtmlString(itemRowProperties)).append(">");
					for (int col=0; col < cols; col++) {
						ret.append("<TD");
						if((hasColProperties & (1 << col))!=0) {
							ret.append(getPropertiesHtmlString(colProperties[col]));
						}
						ret.append(">");
						
						String cellContent;
						try {
							cellContent = (String)((Vector)dataVector.get(row)).get(col);
						} catch(Exception e) {
							// We don't want to show any error with data
							cellContent = "&nbsp;";
						}
						
						try {
							if((isTotalized & (1 << col))!=0) {
								double val = nf.parse(cellContent).doubleValue();
								totals[col]+=val;
							}
						} catch (Exception e) {
							/* Just ignores this value */
						}
						
						if ((hasLink & (1 << col))!=0) {
							String itemId;
							try {
								itemId = (String)idVector.get(row);
							} catch(Exception e) {
								// We don't want to show any error with data
								itemId = "";
							}
							if (applicationUrl.equals("") && !designing) {
								HttpServletRequest request = getApplication().getServletRequest();
								applicationUrl = request.getRequestURI();
							}
							if (isAllowMultiple()) {
								ret.append("<INPUT TYPE=\"checkbox\" NAME=\"");
								ret.append(name);
								ret.append("__check__");
								ret.append(itemId);
								if (getSelectedItens() != null && getSelectedItens().contains(itemId))
									ret.append(" CHECKED");
								ret.append("\"/>");
								ret.append(cellContent);
							} else {
								ret.append("<A HREF=\"");
								String href = applicationUrl+"?mognoOrigin="+
								("".equals(origin)?owner.name:origin)+
								"&"+name+"="+itemId+"&"+params;
								ret.append(href);
								ret.append("\">");
								ret.append(cellContent);
								ret.append("</A>");
							}
						} else {
							ret.append(cellContent);
						}
						ret.append("</TD>");
					}
					ret.append("</TR>\n");
				}
			}
			
			/* Print line with totals */
			if (isTotalized != 0l) {
				ret.append(buildRowGroupHtml(-2)); // -2 is the pseudo-row for the total line
				ret.append("<TR>");
				nf.setMaximumFractionDigits(getTotalMaximumFractionDigits());
				nf.setMinimumFractionDigits(getTotalMinimumFractionDigits());
				
				ret.append("<TD");
				if((hasColProperties & 1)!=0) {
					ret.append(getPropertiesHtmlString(colProperties[0]));
				}
				ret.append(">").append(getTotalLineLabel()).append("</TD>");
				
				for(int col=1;col<cols;col++) {
					ret.append("<TD");
					if((hasColProperties & (1 << col))!=0) {
						ret.append(getPropertiesHtmlString(colProperties[col]));
					}
					ret.append(">");
					if((isTotalized & (1 << col))!=0) {
						ret.append(nf.format(totals[col]));
					} else {
						ret.append("&nbsp;");
					}
					ret.append("</TD>");
				}
				ret.append("</TR>\n");
			}
			ret.append("</TABLE>\n");
		}
		return ret.toString();
	}
	
	private String buildRowGroupHtml(int row) {
		String ret = "";
		if (row == 0) {
			if (isDesigning()) {
				ret = buildCloseOpenGroup(headerGroupId, headerGroupType, null, null);
			} else {
				ret = buildCloseOpenGroup(headerGroupId, headerGroupType,
				(String)groupIdVector.get(0), (String)groupVector.get(0));
			}
		} else if (row > 0) {
			ret = buildCloseOpenGroup((String)groupIdVector.get(row-1), (String)groupVector.get(row-1),
			(String)groupIdVector.get(row), (String)groupVector.get(row));
		} else if (row == -1 && headerGroupType != null &&
		("THEAD".equalsIgnoreCase(headerGroupType) ||
		"TBODY".equalsIgnoreCase(headerGroupType) ||
		"TFOOT".equalsIgnoreCase(headerGroupType))) {
			ret = "<"+headerGroupType+">";
		} else if (row == -2 && totalLineGroupType != null &&
		("THEAD".equalsIgnoreCase(totalLineGroupType) ||
		"TBODY".equalsIgnoreCase(totalLineGroupType) ||
		"TFOOT".equalsIgnoreCase(totalLineGroupType))) {
			if (isDesigning()) {
				ret = buildCloseOpenGroup(null, null, totalLineGroupId, totalLineGroupType);
			} else {
				ret = buildCloseOpenGroup((String)groupIdVector.lastElement(), (String)groupVector.lastElement(),
				totalLineGroupId, totalLineGroupType);
			}
		}
		return ret;
	}
	
	private String buildCloseOpenGroup(String idAnt, String groupTypeAnt, String id, String groupType) {
		String ret = "";
		// If ant is null and this is set
		if (idAnt == null && id != null) {
			ret = "<"+groupType+">";
		} else if (idAnt != null && id == null) { // If ant isn't null and this is
			ret = "</"+groupTypeAnt+">\n";
		} else if ((idAnt != null && id != null) && !idAnt.equals(id)) { // If both not null
			ret = "</"+groupTypeAnt+">\n<"+groupType+">";
		}
		return ret;
	}
	
	protected void setPropertiesFromString(Component c, String propString) {
		c.setProperties(getPropertiesHash(propString));
	}
	
	protected Hashtable getPropertiesHash(String propString) {
		Hashtable props = new Hashtable();
		String[] info = propString.split("\\|");
		for(int j=0; j<info.length; j++) {
			String[] prop = info[j].split("=",2);
			if (prop.length == 2 && prop[0] != null && !"".equals(prop[0]) && prop[1] != null && !"".equals(prop[1])) {
				props.put(prop[0],prop[1]);
			}
		}
		return props;
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
	
	public String endContainer() {
		Enumeration e = listChilds();
		while (e.hasMoreElements()) {
			String s = (String)e.nextElement();
			freeChild(s);
		}
		return super.endContainer();
	}
	
	public void receiveRequest(HttpServletRequest request) {
		selectedItens = new Vector();
		if (isAllowMultiple()) {
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String param = (String)e.nextElement();
				if (param.startsWith(name+"__check__")) {
					String id = param.replaceAll(name+"__check__", "");
					selectedItens.add(id);
				}
			}
		} else {
			if (request.getParameter(name) != null) {
				selectedItens.add(request.getParameter(name));
				queue("evOnClick");
			}
		}
	}
	
	/** Add a row data whithout an associated id.<br>
	 * NOTE: Do not use if this TableList is active!
	 * @param rowData A Vector with the row data.
	 * @see #active
	 */
	public void addRowData(Vector rowData) {
		if(dataVector == null) {
			dataVector = new Vector();
			groupVector = new Vector();
			groupIdVector = new Vector();
		}
		dataVector.add(rowData);
		groupVector.add(null);
		groupIdVector.add(null);
	}
	
	/** Add a row data and the associated id.
	 * If this TableList is not active, the id will be ignored.
	 * @param rowData A Vector with the row data.
	 * @param id The associated id.
	 * @see #active
	 */
	public void addRowData(Vector rowData, String id) {
		if(dataVector == null) {
			dataVector = new Vector();
			idVector = new Vector();
			groupVector = new Vector();
			groupIdVector = new Vector();
		}
		dataVector.add(rowData);
		idVector.add(id);
		groupVector.add(null);
		groupIdVector.add(null);
	}
	
	/** Add a row data with the data of the "group" it is in, whithout an associated id.<br>
	 * NOTE: Do not use if this TableList is active!
	 * @see #active
	 * @param groupType A String that can be TBODY, THEAD or TFOOT.
	 * @param groupId Any String that will identify all the rows inside de same group.
	 * @param rowData A Vector with the row data.
	 */
	public void addRowData(Vector rowData, String groupType, String groupId) {
		if(dataVector == null) {
			dataVector = new Vector();
			groupVector = new Vector();
			groupIdVector = new Vector();
		}
		dataVector.add(rowData);
		if (groupType != null &&
		("THEAD".equalsIgnoreCase(groupType) ||
		"TBODY".equalsIgnoreCase(groupType) ||
		"TFOOT".equalsIgnoreCase(groupType))) {
			groupVector.add(groupType);
			groupIdVector.add(groupId);
		} else {
			groupVector.add(null);
			groupIdVector.add(null);
		}
	}
	
	/** Add a row data with the data of the "group" it is in, and the associated id.
	 * If this TableList is not active, the id will be ignored.
	 * @see #active
	 * @param groupType A String that can be TBODY, THEAD or TFOOT.
	 * @param groupId Any String that will identify all the rows inside de same group.
	 * @param rowData A Vector with the row data.
	 * @param id The associated id.
	 */
	public void addRowData(Vector rowData, String groupType, String groupId, String id) {
		if(dataVector == null) {
			dataVector = new Vector();
			idVector = new Vector();
			groupVector = new Vector();
			groupIdVector = new Vector();
		}
		dataVector.add(rowData);
		idVector.add(id);
		if (groupType != null &&
		("THEAD".equalsIgnoreCase(groupType) ||
		"TBODY".equalsIgnoreCase(groupType) ||
		"TFOOT".equalsIgnoreCase(groupType))) {
			groupVector.add(groupType);
			groupIdVector.add(groupId);
		} else {
			groupVector.add(null);
			groupIdVector.add(null);
		}
	}
	
	/** Getter for property dataVector.
	 * @return Value of property dataVector.
	 */
	public Vector getDataVector() {
		return dataVector;
	}
	/** Setter for property dataVector.
	 * @param dataVector New value of property dataVector.
	 */
	public void setDataVector(Vector dataVector) {
		this.dataVector = dataVector;
	}
	
	/** Getter for property headerRowProperties.
	 * @return Value of property headerRowProperties.
	 */
	public String getHeaderRowProperties() {
		return headerRowProperties;
	}
	/** Setter for property headerRowProperties.
	 * @param headerRowProperties New value of property headerRowProperties.
	 */
	public void setHeaderRowProperties(String headerRowProperties) {
		this.headerRowProperties = headerRowProperties;
	}
	
	/** Getter for property thProperties.
	 * @return Value of property thProperties.
	 */
	public String getThProperties() {
		return thProperties;
	}
	/** Setter for property thProperties.
	 * @param thProperties New value of property thProperties.
	 */
	public void setThProperties(String thProperties) {
		this.thProperties = thProperties;
	}
	
	/** Getter for property itemRowProperties.
	 * @return Value of property itemRowProperties.
	 */
	public String getItemRowProperties() {
		return itemRowProperties;
	}
	/** Setter for property itemRowProperties.
	 * @param itemRowProperties New value of property itemRowProperties.
	 */
	public void setItemRowProperties(String itemRowProperties) {
		this.itemRowProperties = itemRowProperties;
	}
	
	/** Getter for property tdProperties.
	 * @return Value of property tdProperties.
	 */
	public String getTdProperties() {
		return tdProperties;
	}
	/** Setter for property tdProperties.
	 * @param tdProperties New value of property tdProperties.
	 */
	public void setTdProperties(String tdProperties) {
		this.tdProperties = tdProperties;
	}
	
	/** Getter for property linkColumns.
	 * @return Value of property linkColumns.
	 */
	public String getLinkColumns() {
		return linkColumns;
	}
	/** Setter for property linkColumns.
	 * @param linkColumns New value of property linkColumns.
	 */
	public void setLinkColumns(String linkColumns) {
		this.linkColumns = linkColumns;
	}
	
	/**
	 * @param str
	 * @return
	 */
	private long strArrToBitCache(String []str) {
		long ret=0;
		for(int i=0;i<str.length;i++) {
			if(!"".equals(str[i])) {
				ret |= (1 << i);
			}
		}
		return ret;
	}
	
	/** Getter for property totalLineLabel.
	 * @return Value of property totalLineLabel.
	 *
	 */
	public String getTotalLineLabel() {
		return totalLineLabel;
	}
	
	/** Setter for property totalLineLabel.
	 * @param totalLineLabel New value of property totalLineLabel.
	 *
	 */
	public void setTotalLineLabel(String totalLineLabel) {
		this.totalLineLabel = totalLineLabel;
	}
	
	/** Getter for property totalColumns.
	 * @return Value of property totalColumns.
	 *
	 */
	public String getTotalColumns() {
		return totalColumns;
	}
	
	/** Setter for property totalColumns.
	 * @param totalColumns New value of property totalColumns.
	 *
	 */
	public void setTotalColumns(String totalColumns) {
		this.totalColumns = totalColumns;
	}
	
	/** Getter for property totalMaximumFractionDigits.
	 * @return Value of property totalMaximumFractionDigits.
	 *
	 */
	public int getTotalMaximumFractionDigits() {
		return totalMaximumFractionDigits;
	}
	
	/** Setter for property totalMaximumFractionDigits.
	 * @param totalMaximumFractionDigits New value of property totalMaximumFractionDigits.
	 *
	 */
	public void setTotalMaximumFractionDigits(int totalMaximumFractionDigits) {
		this.totalMaximumFractionDigits = totalMaximumFractionDigits;
	}
	
	/** Getter for property totalMinimumFractionDigits.
	 * @return Value of property totalMinimumFractionDigits.
	 *
	 */
	public int getTotalMinimumFractionDigits() {
		return totalMinimumFractionDigits;
	}
	
	/** Setter for property totalMinimumFractionDigits.
	 * @param totalMinimumFractionDigits New value of property totalMinimumFractionDigits.
	 *
	 */
	public void setTotalMinimumFractionDigits(int totalMinimumFractionDigits) {
		this.totalMinimumFractionDigits = totalMinimumFractionDigits;
	}
	
	/** Getter for property colgroups.
	 * @return Value of property colgroups.
	 *
	 */
	public String getColgroups() {
		return colgroups;
	}
	
	/** Setter for property colgroups.
	 * @param colgroups New value of property colgroups.
	 *
	 */
	public void setColgroups(String colgroups) {
		this.colgroups = colgroups;
	}
	
	/** Getter for property headerGroupType.
	 * @return Value of property headerGroupType.
	 *
	 */
	public String getHeaderGroupType() {
		return headerGroupType;
	}
	
	/** Setter for property headerGroupType.
	 * @param headerGroupType New value of property headerGroupType.
	 *
	 */
	public void setHeaderGroupType(String headerGroupType) {
		this.headerGroupType = headerGroupType;
	}
	
	/** Getter for property headerGroupId.
	 * @return Value of property headerGroupId.
	 *
	 */
	public String getHeaderGroupId() {
		return headerGroupId;
	}
	
	/** Setter for property headerGroupId.
	 * @param headerGroupId New value of property headerGroupId.
	 *
	 */
	public void setHeaderGroupId(String headerGroupId) {
		this.headerGroupId = headerGroupId;
	}
	
	/** Getter for property totalLineGroupType.
	 * @return Value of property totalLineGroupType.
	 *
	 */
	public String getTotalLineGroupType() {
		return totalLineGroupType;
	}
	/** Setter for property totalLineGroupType.
	 * @param totalLineGroupType New value of property totalLineGroupType.
	 *
	 */
	public void setTotalLineGroupType(String totalLineGroupType) {
		this.totalLineGroupType = totalLineGroupType;
	}
	
	/** Getter for property totalLineGroupId.
	 * @return Value of property totalLineGroupId.
	 *
	 */
	public String getTotalLineGroupId() {
		return totalLineGroupId;
	}
	/** Setter for property totalLineGroupId.
	 * @param totalLineGroupId New value of property totalLineGroupId.
	 *
	 */
	public void setTotalLineGroupId(String totalLineGroupId) {
		this.totalLineGroupId = totalLineGroupId;
	}
}
