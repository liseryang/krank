/**
 * 
 */
package org.codegen.gui
import javax.swing.event.TreeModelEvent
import javax.swing.event.TreeModelListener
import javax.swing.tree.TreeModel
import javax.swing.tree.TreePath
import org.codegen.model.Table
import org.codegen.gui.ListHolder
import org.codegen.model.*

class TableHolder {
	Table table
	ListHolder primaryKeys
	ListHolder columns
	List<ListHolder> lists

	void setTable (Table table) {
		this.table = table
		primaryKeys = new ListHolder(name: "Primary Keys", list: table.primaryKeys)
		columns = new ListHolder(name: "Columns", list: table.columns)
		lists = [primaryKeys, columns]
	}
	public String toString() {
		table.name
	}
}



/**
 * @author richardhightower
 *
 */
public class DBTableTreeModel implements TreeModel{
	List<TableHolder> tableHolders = []
	
    private Vector<TreeModelListener> treeModelListeners =
    new Vector<TreeModelListener>();

    /**
     * The only event raised by this model is TreeStructureChanged with the
     * root as path, i.e. the whole tree has changed.
     */
    protected void setTables(List<Table> tables) {
    	tableHolders.clear()
    	for (Table table : tables) {
    		TableHolder holder = new TableHolder(table:table)
    		tableHolders << holder
    	}
        int len = treeModelListeners.size();
        TreeModelEvent e = new TreeModelEvent(this, [this] as Object[]);
        for (TreeModelListener tml : treeModelListeners) {
            tml.treeStructureChanged(e);
        }
    }

	
    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     */
    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.addElement(l);
    }

    /**
     * Returns the child of parent at index index in the parent's child array.
     */
    public Object getChild(Object parent, int index) {
    	if (parent instanceof DBTableTreeModel) {
    		return tableHolders[index]
    	} else if (parent instanceof TableHolder) {
    		TableHolder table = (TableHolder)parent;
    		return table.lists[index]
    	} else if (parent instanceof ListHolder) {
    		ListHolder listHolder = (ListHolder) parent
    		return listHolder.list[index]
    	}
    }
    
    /**
     * Returns the number of children of parent.
     */
    public int getChildCount(Object parent) {
    	if (parent instanceof DBTableTreeModel) {
    		return tableHolders==null ? 0 : tableHolders.size()
    	} else if (parent instanceof TableHolder) {
    		TableHolder table = (TableHolder) parent;
    		return table.lists.size()
    	} else if (parent instanceof ListHolder) {
    		ListHolder listHolder = (ListHolder) parent
    		return listHolder.list == null ? 0 : listHolder.list.size()
    	} 
    }

    /**
     * Returns the index of child in parent.
     */
    public int getIndexOfChild(Object parent, Object child) {
    	if (parent instanceof DBTableTreeModel) {
    		return tableHolders.indexOf(child)
    	} else if (parent instanceof TableHolder) {
    		TableHolder table = (TableHolder) parent;
    		return table.lists.indexOf(child)
    	}  else if (parent instanceof ListHolder) {
    		ListHolder listHolder = (ListHolder) parent
    		return listHolder.list.indexOf(child)
    	}
    }
    
    /**
     * Returns the root of the tree.
     */
    public Object getRoot() {
        return this;
    }
    
    /**
     * Returns true if node is a leaf.
     */
    public boolean isLeaf(Object node) {
        node instanceof Column || node instanceof String
    }

    /**
     * Removes a listener previously added with addTreeModelListener().
     */
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.removeElement(l);
    }
    
    /**
     * Messaged when the user has altered the value for the item
     * identified by path to newValue.  Not used by this model.
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("*** valueForPathChanged : "
            + path + " --> " + newValue);
    }


    public String toString() {
    	return "tables";
    }
}