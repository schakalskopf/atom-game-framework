/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.visibility;

/**
 * WorldVisibilityManager manage the visibility of Spatial and Entities with
 * some higher degree/resolution than WorldLODManager; it decide whenever a
 * spatial can be visible/culled/removed.
 *
 * <p>WorldVisibilityManager manages Zones and a few Trees and Graphs under its
 * curtain. Those data structure used to toogle visibility of Spatial by
 * index/hash/sweep/compare/filter and judge them under criteria.
 *
 * <p>In constrast with WorldLODManager, WorldVisibilityManager manage higher
 * degree/resolution and have higher piority. In common, their both work in
 * heavy pre-constructed data and manage to work real time.
 *
 * @author cuong.nguyenmanh2
 */
public interface WorldVisibilityManager {
}
