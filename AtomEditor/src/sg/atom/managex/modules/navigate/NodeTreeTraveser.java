/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.modules.navigate;

import com.google.common.collect.TreeTraverser;
import sg.atom.managex.api.node.AtomEditorNode;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NodeTreeTraveser extends TreeTraverser<AtomEditorNode> {

    @Override
    public Iterable<AtomEditorNode> children(AtomEditorNode root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
