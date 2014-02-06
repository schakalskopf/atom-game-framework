/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.scenegraph.shape;

/**
 *
 * @author hungcuong
 */
 
import com.jme3.animation.Skeleton;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.SkeletonPoints;
import com.jme3.scene.debug.SkeletonWire;

public class SkeletonDebugger extends Node {

    private SkeletonWire wires;
    private SkeletonPoints points;
    private Skeleton skeleton;

    public SkeletonDebugger(String name, Skeleton skeleton){
        super(name);

        this.skeleton = skeleton;
        wires = new SkeletonWire(skeleton);
        points = new SkeletonPoints(skeleton);

        attachChild(new Geometry(name+"_wires", wires));
        attachChild(new Geometry(name+"_points", points));

        setQueueBucket(Bucket.Transparent);
    }

    public SkeletonDebugger(){
    }

    @Override
    public void updateLogicalState(float tpf){
        super.updateLogicalState(tpf);

//        skeleton.resetAndUpdate();
        wires.updateGeometry();
        points.updateGeometry();
    }
   
}
