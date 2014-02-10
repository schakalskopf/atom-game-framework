package sg.atom.swing.editor.components.timeline.ui

import groovy.swing.SwingXBuilder
import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.*

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitLayout.Divider;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Node;
import org.jdesktop.swingx.MultiSplitLayout.Split;

def swing = new SwingXBuilder()
def frame = swing.frame(title:'Frame', defaultCloseOperation:JFrame.EXIT_ON_CLOSE, pack:true, show:true, size:[800,600]) {
    
    String layoutDef ="(ROW left middle right)";
    MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);

    JXMultiSplitPane multiSplitPane1 = new JXMultiSplitPane();
    multiSplitPane1.getMultiSplitLayout().setModel(modelRoot);
    
    /*
    def children =[ 
    new Leaf("left"),
    new Divider(), 
    new Leaf("right")];
    Split modelRoot = new Split();
    modelRoot.setChildren(children);

    JXMultiSplitPane multiSplitPane1 = new JXMultiSplitPane();
    multiSplitPane1.getMultiSplitLayout().setModel(modelRoot);
     */
    multiSplitPane(multiSplitPane1){
        
        button(text:"Left Button", constraints:"left")
        button(text:"Right Button", constraints:"right")
        button(text:"Center Button", constraints:"middle")
    }
    

}

