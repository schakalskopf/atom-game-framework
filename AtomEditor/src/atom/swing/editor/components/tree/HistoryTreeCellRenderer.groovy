package atom.swing.editor.components.tree

import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.JTree
import java.awt.Component
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.Icon
/**
 *
 * @author cuong.nguyenmanh2
 */
public class HistoryTreeCellRenderer  extends DefaultTreeCellRenderer {
    def icons=[:]
    def app
    public  HistoryTreeCellRenderer(app){
        this.app = app
    }
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
        setIcon(getIcon(value));
        setToolTipText(getType(value)); //no tool tip
        return this;
    }

    def getIcon(value){
        if (app.actions[value]!=null){
            return app.actions[value].icon
        } else {
            return null
        }
    }
    protected String getType(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        def obj= node.getUserObject()
        return obj
    }
}
