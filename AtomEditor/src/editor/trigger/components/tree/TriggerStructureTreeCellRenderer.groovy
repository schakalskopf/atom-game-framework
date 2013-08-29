package rts.editor.trigger.components.tree

import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.JTree
import java.awt.Component
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.Icon
/**
 *
 * @author cuong.nguyenmanh2
 */
class TriggerStructureTreeCellRenderer  extends DefaultTreeCellRenderer {
    Icon actionIcon;
    Icon triggerIcon;
    Icon conditionIcon;
    Icon eventIcon;

    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {

        super.getTreeCellRendererComponent(
            tree, value, sel,
            expanded, leaf, row,
            hasFocus);
        if (getType(value).equals("Action")) {
            setIcon(actionIcon);
            setToolTipText("Action");
        } else if (getType(value).equals("Trigger")) {
            setIcon(triggerIcon);
            setToolTipText(null); //no tool tip
        } else if (getType(value).equals("Event")) {
            setIcon(eventIcon);
            setToolTipText("Event"); //no tool tip
        } else if (getType(value).equals("Condition")) {
            setIcon(conditionIcon);
            setToolTipText("Condition"); //no tool tip
        }
        return this;
    }

    protected String getType(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        def obj= node.getUserObject()
        if (obj.startsWith("A")) {
            return "Action"
        } else if (obj.startsWith("T")) {
            return "Trigger"
        } else if (obj.startsWith("E")) {
            return "Event"
        } else if (obj.startsWith("C")) {
            return "Condition"
        }
    }
}
