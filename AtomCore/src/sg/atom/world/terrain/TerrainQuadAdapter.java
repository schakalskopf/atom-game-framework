/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.terrain;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.control.Control;
import com.jme3.terrain.ProgressMonitor;
import com.jme3.terrain.geomipmap.TerrainQuad;
import java.util.List;

/**
 * Act as proxy for the TerrainQuad to be an Atom's GenericTerrain
 *
 * @author cuong.nguyenmanh2
 */
public class TerrainQuadAdapter extends GenericTerrain<TerrainQuad> {

    public TerrainQuad terrainQuad;

    public TerrainQuadAdapter(TerrainQuad terrainQuad) {
        super("TerrainQuad");
        this.terrainQuad = terrainQuad;
    }

    //FIXME: Add all the constructor
    public TerrainQuadAdapter(String name, int patchSize, int totalSize, float[] heightMap) {
        super("TerrainQuad");
        terrainQuad = new TerrainQuad(name, patchSize, totalSize, heightMap);
    }

    @Override
    public void setMaterial(Material matTerrain) {
        terrainQuad.setMaterial(matTerrain);
    }

    @Override
    public void setShadowMode(ShadowMode shadowMode) {
        terrainQuad.setShadowMode(shadowMode);
    }

    @Override
    public TerrainMeshType getMeshType() {
        return TerrainMeshType.LOD;
    }

    @Override
    public boolean isOutOfBorder(Vector2f pos) {
        return false;
    }

    @Override
    public Vector2f getSize() {
        //return terrainQuad.getPatchSize();
        return new Vector2f(1, 1);
    }

    @Override
    public Vector3f getNormal(float x, float z, Vector2f xz) {
        return terrainQuad.getNormal(xz);
    }

    @Override
    public void setHeight(Vector2f xz, float height) {
        terrainQuad.setHeight(xz, height);
    }

    @Override
    public void adjustHeight(Vector2f xz, float delta) {
        terrainQuad.adjustHeight(xz, delta);
    }

    @Override
    public void setHeight(List<Vector2f> xz, List<Float> height) {
        terrainQuad.setHeight(xz, height);
    }

    @Override
    public void adjustHeight(List<Vector2f> xz, List<Float> height) {
        terrainQuad.adjustHeight(xz, height);
    }

    @Override
    public float getHeight(Vector2f pos2f) {
        return terrainQuad.getHeight(pos2f);
    }

    public TerrainQuad getTerrainQuad() {
        return terrainQuad;
    }

    //FIXME: Replicate transform functions
    @Override
    protected void updateWorldTransforms() {
        super.updateWorldTransforms();
        terrainQuad.setLocalTransform(localTransform);
    }

    @Override
    public TerrainQuad getGeometricData() {
        return terrainQuad;
    }

    @Override
    public Vector3f getNormal(Vector2f xz) {
        return terrainQuad.getNormal(xz);
    }

    @Override
    public float getHeightmapHeight(Vector2f xz) {
        return terrainQuad.getHeightmapHeight(xz);
    }

    @Override
    public float[] getHeightMap() {
        return terrainQuad.getHeightMap();
    }

    @Override
    public int getMaxLod() {
        return terrainQuad.getMaxLod();
    }

    @Override
    public void setLocked(boolean locked) {
        terrainQuad.setLocked(locked);
    }

    @Override
    public void generateEntropy(ProgressMonitor monitor) {
        terrainQuad.generateEntropy(monitor);
    }

    @Override
    public int getNumMajorSubdivisions() {
        return terrainQuad.getNumMajorSubdivisions();
    }

    @Override
    public Material getMaterial() {
        return terrainQuad.getMaterial();
    }

    @Override
    public Material getMaterial(Vector3f worldLocation) {
        return terrainQuad.getMaterial(worldLocation);
    }

    @Override
    public int getTerrainSize() {
        return terrainQuad.getTerrainSize();
    }

    @Override
    public <T extends Control> T getControl(Class<T> controlType) {
        return terrainQuad.getControl(controlType);
    }
    
    
}
