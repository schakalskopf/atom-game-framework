/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import java.util.Properties;
import sg.atom.core.AbstractManager;

/**
 * Manager and Wrapper for useful functions for Enviroment. To make an earth like enviroment.
 *
 * Including Light, Skybox,.
 *
 * <ul> <li>Load configs about different Enviroment. Load asset attached to
 * specific enviroment description.</li>
 *
 * <li>Handle Transistion between Enviroment
 *
 * <li>Generator with Fractal, Voronoi, Tiling.
 *
 * <li>Simulation some aspects
 *
 * <li>
 *
 * <li>
 *
 * </ul>
 *
 * @author atomix
 */
public class EnviromentManager extends AbstractManager {

    public void setWeather(){
        
    }

    public void getCurrentWeather(){
        
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
