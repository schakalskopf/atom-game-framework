/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

import com.google.common.base.Converter;
import com.google.common.collect.BiMap;
import com.jme3.math.Transform;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.LodControl;
import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.utils.proxy.IPresenter;

/**
 * Default implementation of WorldLODManager handle physical LOD and provide
 * slots indexing, usually see in terrain base games. This Manager also mange
 * all the LodControl in a specific rootNode;
 *
 * <p>Perceptual topic such as the transistion of area, blending between two
 * presentor of Spatial just corvered in simplest form with Material blending
 * and adaptive mesh morphing.
 *
 * @author cuong.nguyenmanh2
 */
public class DefaultLODManager extends AbstractManager<Spatial> implements WorldLODManager {

    protected int detailLevels;
    protected LODLevels levels;
    protected Node rootNode;
    protected BiMap<Converter, IPresenter> converters;
    protected BiMap<DetailTweaker, IPresenter> tweaker;
    
    public LODLevels getLevels() {
        return levels;
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }

    public void setLOD(Object object){
        // If this object is not managed, then ignore it.
        
    }
    public void setLOD(Spatial spatial){
        spatial.setUserData(LODEntropy.keyword, DetailMetrics.measure(spatial));
        LodControl lodControl = spatial.getControl(LodControl.class);
        //lodControl.
    }
    @Override
    public void tweakLOD(Spatial spatial, int level) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void blend(Spatial spatial, float fpf) {
        //Compute the blend factor of spatial at the time
        
        //Use interpolators
        
        //
    }

    @Override
    public Transform getCursor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Camera getCamera() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
//Cycle-------------------------------------------------------------------------

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
