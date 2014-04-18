/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.stage.StageManager;
import sg.atom.world.enviroment.EnviromentWeather;

/**
 * Manager and Wrapper for useful functions for Enviroment. To make an earth
 * like enviroment. Merged with WeatherManager.
 *
 * Including Light, Skybox,.
 *
 * <ul> <li>Load configs about different Enviroment. Load asset attached to
 * specific enviroment description.</li>
 *
 * <li>Handle Transistion between Enviroment by Fuzzy state machine.
 *
 * <li>Generator with Fractal, Voronoi, Tiling.
 *
 * <li>Simulation some aspects: Enviromental Time, Lighting, Sounds..
 *
 * <li>
 *
 * </ul>
 *
 * @author atomix
 */
public class EnviromentManager extends AbstractManager {
    // Managers

    protected StageManager stageManager;
    protected WorldManager worldManager;
    protected EnviromentManager enviromentManager;
    protected MaterialManager materialManager;
    protected TerrainManager terrainManager;
    private EnviromentWeather currentWeather;

    public EnviromentManager(StageManager stageManager, WorldManager worldManager) {
        this.stageManager = stageManager;
        this.worldManager = worldManager;
    }

    //Weather management--------------------------------------------------------
    public void setWeather(EnviromentWeather weather) {
        this.currentWeather = weather;
    }

    public EnviromentWeather getCurrentWeather() {
        return null;
    }

    //Cycle---------------------------------------------------------------------
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
