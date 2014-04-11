/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.node;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Observer;

/**
 * Unit model of editor GUI, concept similar to Netbean's Node. But ...
 * reactive!!
 *
 * <p>First class citizen of a Reactive GUI framework based in RxJava.
 *
 * <h4>Similar features:</h4>
 *
 * <li> A node represents one element in a hierarchy of objects (beans).Node
 * also is a BeanProperties. It can also act as proxy.
 *
 * <li>It provides all methods that are needed for communication between an view
 * and the bean.
 *
 * <li>Nodes form tree hierachy by default... but not forced! So it can also be
 * use in a Graph.
 *
 * <li>Node is dynamic via decorator pattern with a Cookie-like mechanism named
 * Flavours builted via component injections, and Node Builder.
 *
 * <h4>Differencies: </h4><ul><li>OpenIDE's Node implements Lookup, which also
 * Based in observer pattern but it too opinioned compare with Reactive's
 * observer. Read: https://github.com/Netflix/RxJava/wiki . AtomEditorNode
 * provide observer subscription over its data just like Lookup. One can also
 * queue modification in a Node with Observable operations.
 *
 * <li>Second is not tied its self with AWT. So any platform and any GUIs
 * (Android for ex..) with be capatable to run. </ul>
 *
 * <p><b>Note : </b>Normal swing based mechanism its support via plugins.
 *
 * <p>
 *
 * @author CuongNguyen
 */
public abstract class AtomEditorNode<T extends Object> {

    protected ArrayList<AtomEditorNode> children;
    protected AtomicBoolean selected;
    protected ArrayList<NodeFlavour> flavours;

    public interface NodeFlavour {
    }

    public AtomEditorNode() {
        //Observable.
    }

    public AtomEditorNode(ArrayList<NodeFlavour> flavours) {
        this.flavours = flavours;
    }

    public ArrayList<NodeFlavour> getFlavours() {
        return flavours;
    }
    
    public static class NodeTrees{
        
    }
    
    public static class NodeGraphs{
        
    }
    
    public static class NodeCollections{
        
    }
    
    public static class NodeTaks{
        
    }
    
    public static class NodeConcurrents{
        
    }
}
