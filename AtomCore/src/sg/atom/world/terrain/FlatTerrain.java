/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.terrain;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.List;
import sg.atom.stage.WorldManager;
import sg.atom.world.WorldTestHelper;

/**
 *
 * @author CuongNguyen
 */
public class FlatTerrain extends GenericTerrain<Node> {
    
    private WorldTestHelper worldTestHelper;
    private int size;
    
    public FlatTerrain(int size, WorldManager worldManager) {
        super("FlatTerrain");
        this.worldTestHelper = new WorldTestHelper(worldManager);
        this.size = size;
        worldTestHelper.createFlatGround(size);
        worldTestHelper.createGrid(size, size);
        worldTestHelper.ground.scale(10);
        worldTestHelper.gridGeo.scale(10);
        attachChild(worldTestHelper.ground);
        worldManager.getWorldNode().attachChild(this);
    }
    
    @Override
    public TerrainMeshType getMeshType() {
        return TerrainMeshType.Static;
    }
    
    @Override
    public Node getGeometricData() {
        return this;
    }
    
    @Override
    public boolean isOutOfBorder(Vector2f pos) {
        if (pos.x >= 0 && pos.x <= size && pos.y >= 0 && pos.y < size) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public Vector2f getSize() {
        return new Vector2f(size, size);
    }
    
    @Override
    public Vector3f getNormal(float x, float z, Vector2f xz) {
        return Vector3f.UNIT_Y;
    }
    
    public float getHeight(Vector2f xz) {
        return 0;
    }
    
    public Vector3f getNormal(Vector2f xz) {
        return Vector3f.UNIT_Y;
    }
    
    public float getHeightmapHeight(Vector2f xz) {
        return 0;
    }
    
    public void setHeight(Vector2f xzCoordinate, float height) {
    }
    
    public void setHeight(List<Vector2f> xz, List<Float> height) {
    }
    
    public void adjustHeight(Vector2f xzCoordinate, float delta) {
    }
    
    public void adjustHeight(List<Vector2f> xz, List<Float> height) {
    }
    
    public float[] getHeightMap() {
        return null;
    }
    
    public void setLocked(boolean locked) {
    }
    
    public Material getMaterial() {
        return worldTestHelper.getMatGround();
    }
    
    public Material getMaterial(Vector3f worldLocation) {
        return worldTestHelper.getMatGround();
    }
    
    public int getTerrainSize() {
        return size;
    }

    
    
}
