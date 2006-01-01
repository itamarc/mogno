package com.oktiva.mogno;

import java.util.Enumeration;
import java.util.Vector;

/**
 * This is the base class for container components.
 */
public class Container  extends Visual {
	public String content = "";
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	/** Something to be put after the tag.  Default to emtpy string.
	 * It is intended primarily to put a new line after some tags.
	 */
	protected String afterEnd = "";
	
	/** Builds the HTML from the ordered childs list.
	 *
	 * When a component has gridWidth or gridHeight, the number of
	 * startColumns, endColumns, startRow and endRows called will still
	 * depend of the last top and left.
	 *
	 * Ex.: If you have a component with left 0 and gridWidth 2 and
	 * you have another component with left 2, there will be three calls
	 * to startColumn
	 */
	public String show()
	throws Exception {
		StringBuffer ret = new StringBuffer(1024);
		ret.append(super.show());
		Component c = selectParentComponent();
		// discover which components are inside me
		Vector comps = orderChildsVector(getChildsVector(c));
		// show them
		ret.append(startContainer());
		ret.append(content);
		for (int y = 0; y < comps.size(); y++) {
			ret.append(startRow(y));
			Vector vVert = (Vector)comps.get(y);
			if (vVert != null) {
				for (int x = 0; x < vVert.size(); x++) {
					Visual thisComponent = ((Visual)vVert.get(x));
					ret.append(startColumn(y,x,thisComponent));
					if (thisComponent != null) {
						ret.append(thisComponent.show());
					}
					ret.append(endColumn(y,x,thisComponent));
					if (x < (vVert.size()-1)) {
						ret.append(betweenColumns());
					}
					if (thisComponent != null) {
						x += thisComponent.getGridWidth() - 1;
					}
				}
			}
			ret.append(endRow(y));
			if (y < comps.size() - 1) {
				ret.append(betweenRows());
			}
		}
		ret.append(endContainer()+afterEnd);
		return ret.toString();
	}
	
	/** The parent component: this one or his owner.
	 * @return <code>(Component)owner==null?this:owner</code>
	 */
	protected Component selectParentComponent() {
		return (Component)owner==null?this:owner;
	}
	
	/** Discover which components are inside obj.
	 * @param obj
	 * @return A vector with the components.
	 */
	protected Vector getChildsVector(Component obj) {
		Vector v = new Vector();
		Enumeration e = obj.listChilds();
		while (e.hasMoreElements()) {
			String cname = (String)e.nextElement();
			Visual visual = (Visual)obj.getChild(cname);
			if (visual == null) {
				continue;
			}
			if(visual.getParent().equals(name)){
				v.add(visual);
			}
		}
		return v;
	}
	
	// put it in order
	protected Vector orderChildsVector(Vector v) {
		Vector comps = new Vector();
		for (int i = 0; i < v.size(); i++) {
			Visual visual = (Visual)v.get(i);
			int vert = visual.getTop();
			int horiz = visual.getLeft();
			if ((comps.size() - 1) < vert) {
				comps.setSize(vert+1);
				comps.set(vert,new Vector());
			}
			if (comps.get(vert) == null) {
				comps.set(vert,new Vector());
			}
			Vector vVert = (Vector)comps.get(vert);
			if ((vVert.size() - 1) < horiz) {
				vVert.setSize(horiz+1);
				vVert.set(horiz,null);
			}
			while (vVert.get(horiz) != null) {
				horiz++;
				if (horiz == vVert.size()) {
					vVert.setSize(horiz+1);
					break;
				}
			}
			vVert.set(horiz, visual);
		}
		return comps;
	}
	
	/**
	 * Discover which components are inside me, ordered by <i>top</i> and <i>left</i>.
	 * @return A vector of vectors, where the first vector index is ordered
	 * by <i>top</i> and the second, by <i>left</i>.
	 * @see Visual#top
	 * @see Visual#left
	 */
	public Vector getOrderedChildsVector() {
		return orderChildsVector(getChildsVector((Component)owner==null?this:owner));
	}
	
	/**
	 * @return A vector with all components, starting with this container,
	 * ordered by hierarchy, top and left of each one.
	 */
	public Vector getFullComponentsVector() {
		Vector v = new Vector();
		addChildsVector(this,v);
		return v;
	}
	
	protected Vector getOrderedChildNames() {
		Vector v = new Vector();
		Vector comps = getFullComponentsVector();
		for(int i=1; i<comps.size(); i++) {
			Object o = comps.get(i);
			if(o != null) {
				v.add(((Component)o).name);
			}
		}
		return v;
	}
	
	protected void addChildsVector(Container c, Vector v) {
		v.add(c);
		Vector vec = c.getOrderedChildsVector();
		Enumeration ex = vec.elements();
		while (ex.hasMoreElements()) {
			Vector vy = (Vector)ex.nextElement();
			if(vy == null) {
				continue;
			}
			Enumeration ey = vy.elements();
			while(ey.hasMoreElements()) {
				Visual comp = (Visual)ey.nextElement();
				if(comp instanceof Container) {
					addChildsVector((Container)comp, v);
				} else {
					v.add(comp);
				}
			}
		}
	}
	
	public Vector nonAttributeGetters() {
		Vector v = super.nonAttributeGetters();
		v.add("getFullComponentsVector");
		v.add("getOrderedChildsVector");
		return v;
	}
	
	/**
	 * Method endRow.
	 * @return String
	 */
	public String endRow(int top) {
		return "";
	}
	
	/**
	 * Method betweenRows.
	 * @return String
	 */
	public String betweenRows() {
		return "";
	}
	
	/** Method endColumn.
	 * @return String
	 * @param top The top index.
	 * @param left The left index.
	 * @param comp the Visual for wich this column is
	 */
	public String endColumn(int top, int left, Visual comp) {
		return "";
	}
	
	/**
	 * Method betweenColumns.
	 * @return String
	 */
	public String betweenColumns() {
		return "";
	}
	
	/**
	 * Method startColumn.
	 * @param top The top index.
	 * @param left The left index.
	 * @param comp the Visual for wich this column is
	 * @return String
	 */
	public String startColumn(int top, int left, Visual comp) {
		return "";
	}
	
	/**
	 * Method startRow.
	 * @return String
	 */
	public String startRow(int top) {
		return "";
	}
	
	/**
	 * Starts container's tag.<br>
	 * If the tag attribute is not null, assumes the default behavior:<br>
	 * &lt; + tag + htmlAttributes + &gt;
	 * @return String
	 */
	public String startContainer() {
		if (tag == null) {
			return "";
		} else {
			return "<"+tag+showHtmlAttributes()+">";
		}
	}
	
	/**
	 * Ends container's tag.<br>
	 * If the tag attribute is not null, assumes the default behavior:<br>
	 * &lt; + tag + htmlAttributes + &gt;
	 * @return String
	 */
	public String endContainer() {
		if (tag == null) {
			return "";
		} else {
			return "</"+tag+">";
		}
	}
}
