/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.visibility;

import sg.atom.world.AtomZone;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import sg.atom.stage.StageManager;
import sg.atom.world.EnviromentManager;
import sg.atom.world.MaterialManager;
import sg.atom.world.TerrainManager;
import sg.atom.world.WorldManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class DefaultWorldVisibilityManager implements WorldVisibilityManager {
    // Managers

    protected StageManager stageManager;
    protected WorldManager worldManager;
    protected EnviromentManager enviromentManager;
    protected MaterialManager materialManager;
    protected TerrainManager terrainManager;

    public DefaultWorldVisibilityManager(StageManager stageManager, WorldManager worldManager) {
        this.stageManager = stageManager;
        this.worldManager = worldManager;
    }

    @Override
    public ArrayList<AtomZone> getZones() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void enterZone(AtomZone zone) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void exitZone(AtomZone zone) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toogle(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mark(Spatial spatial, Object mark) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
