/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

import com.google.common.base.Converter;
import com.google.common.collect.BiMap;
import com.google.common.reflect.TypeToken;
import com.jme3.math.Transform;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.LodControl;
import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.stage.StageManager;
import sg.atom.utils.proxy.IPresenter;
import sg.atom.utils.proxy.presentation.IProjection;
import sg.atom.world.EnviromentManager;
import sg.atom.world.MaterialManager;
import sg.atom.world.TerrainManager;
import sg.atom.world.WorldManager;

/**
 * Default implementation of WorldLODManager handle physical LOD and provide
 * slots indexing, usually see in terrain base games. This Manager also mange
 * all the LodControl in a specific rootNode;
 *
 * <h4>Features:</h4> <p>Geometric topic: the transistion of area, blending
 * between two presentor of Spatial just corvered in simplest form with Material
 * blending and adaptive mesh morphing.
 *
 * <p>Perceptual topic: <ul>
 *
 * <li>Events: solved by schedulers and event dispatcher which provided a
 * threshold from this manager. Logging and monitoring will be support in the
 * future.
 *
 * <li>Behaviours & Attributes: simple scenarios are supported by detail
 * tweaker, in which "group of dependent entities" {@link LODEntropy} (aka
 * Layer) is reduced, upgraded by hierarchical manners, for example: for a
 * request of a "reduced form" property, an appropriate response from the
 * "reduced" object can be achieved. Complex scenario, such as multi AI actor
 * (so call AIAgent) in complex enviroments is solved in AtomAI packages.
 *
 * <li> </ul>
 *
 * <p>Common tweaker: hierachical and probability are support
 *
 * <p>Error metrics:
 *
 * <p>Detail metrics:
 *
 * @author cuong.nguyenmanh2
 */
public class DefaultLODManager extends AbstractManager<Spatial> implements WorldLODManager {

    // Managers
    protected StageManager stageManager;
    protected WorldManager worldManager;
    protected EnviromentManager enviromentManager;
    protected MaterialManager materialManager;
    protected TerrainManager terrainManager;
    // Manage details
    protected int detailLevels;
    protected LODLevels levels;
    protected Node rootNode;
    
    protected BiMap<Converter, IPresenter> converters;
    protected BiMap<DetailTweaker, IPresenter> tweakers;
    protected BiMap<IProjection, TypeToken> projections;

    public DefaultLODManager(StageManager stageManager, WorldManager worldManager) {
        this.stageManager = stageManager;
        this.worldManager = worldManager;
    }

    public LODLevels getLevels() {
        return levels;
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }

    public void setLOD(Object object) {
        // If this object is not managed, then ignore it.
    }

    public void setLOD(Spatial spatial) {
        spatial.setUserData(LODEntropy.keyword, DetailMetrics.measure(spatial));
        LodControl lodControl = spatial.getControl(LodControl.class);
        //lodControl.
    }
    @Override
    public void tweakLOD(Object lodObject, int level) {
        throw new UnsupportedOperationException("Not supported yet.");
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
