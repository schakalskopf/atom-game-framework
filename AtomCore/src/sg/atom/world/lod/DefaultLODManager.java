/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

/**
 * Default implementation of WorldLODManager handle physical LOD and provide
 * slots indexing, usually see in terrain base games.
 *
 * <p>Perceptual topic such as the transistion of area, blending between two
 * presentor of Spatial just corvered in simplese form with Material blending
 * and adaptive mesh morphing.
 *
 * @author cuong.nguyenmanh2
 */
public class DefaultLODManager implements WorldLODManager {
    public int detailLevel;
    
}
