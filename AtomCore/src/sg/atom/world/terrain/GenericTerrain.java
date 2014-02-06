/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.terrain;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.terrain.ProgressMonitor;
import com.jme3.terrain.Terrain;

/**
 * To replace the JME3's Terrain interface which is not really useful!
 *
 * @author cuong.nguyenmanh2
 */
public abstract class GenericTerrain<T extends Node> extends Node implements Terrain {

    public static enum TerrainMeshType {

        Static, LOD
    }

    public abstract TerrainMeshType getMeshType();

    public abstract T getGeometricData();

    /* Methods to help inspecting its own geometric and suface*/
    public abstract boolean isOutOfBorder(Vector2f pos);

    public abstract Vector2f getSize();

    public abstract Vector3f getNormal(float x, float z, Vector2f xz);

    @Override
    public void generateEntropy(ProgressMonitor monitor) {
        //FIXME: Do nothing
    }

    @Override
    public int getNumMajorSubdivisions() {
        //FIXME: Fake number
        return 1;
    }

    @Override
    public int getMaxLod() {
        //FIXME: Fake number
        return 1;
    }
}
