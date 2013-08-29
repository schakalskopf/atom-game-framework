package rts.editor.trigger.components.tree

import javax.swing.JPanel
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane
import javax.swing.JTextField
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreePath
import org.jdesktop.swingx.JXPanel
/**
 *
 * @author cuong.nguyenmanh2
 */
class TriggerTreeComponent extends JXPanel {
    JTree tree;

    JTextField jtf;

    public TriggerTreeComponent(app) {
        setLayout(new BorderLayout());
        DefaultMutableTreeNode topTriggerNode = new DefaultMutableTreeNode("Trigger 1");
        DefaultMutableTreeNode eventRoot = new DefaultMutableTreeNode("Events");
        topTriggerNode.add(eventRoot);
    
        eventRoot.add(new DefaultMutableTreeNode("Event 1"));
        eventRoot.add(new DefaultMutableTreeNode("Event 2"));
        eventRoot.add(new DefaultMutableTreeNode("Event 3"));
        
        DefaultMutableTreeNode conditionRoot = new DefaultMutableTreeNode("Conditions");
        topTriggerNode.add(conditionRoot);
    
        conditionRoot.add(new DefaultMutableTreeNode("Condition 1"));
        conditionRoot.add(new DefaultMutableTreeNode("Condition 2"));

        DefaultMutableTreeNode actionRoot = new DefaultMutableTreeNode("Actions");
        topTriggerNode.add(actionRoot);
    
        actionRoot.add(new DefaultMutableTreeNode("Action 1"));
        actionRoot.add(new DefaultMutableTreeNode("Action 2"));

        tree = new JTree(topTriggerNode);
        tree.setCellRenderer(new TriggerStructureTreeCellRenderer(
                triggerIcon:app.createIcon("game/32/app/Actions-office-chart-ring-icon.png"),
                eventIcon:app.createIcon("game/32/flags/flag_red.png"),
                actionIcon:app.createIcon("game/32/app/clap-board.png"),
                conditionIcon:app.createIcon("game/32/app/Actions-go-last-icon.png")
            ));
        
        
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane jsp = new JScrollPane(tree, v, h);

        add(jsp, BorderLayout.CENTER);
/*
        jtf = new JTextField("", 20);
        add(jtf, BorderLayout.SOUTH);

        tree.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    doMouseClicked(me);
                }
            });
       */     
    }

    void doMouseClicked(MouseEvent me) {
        TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
        if (tp != null)
        jtf.setText(tp.toString());
        else
        jtf.setText("");
    }
}

