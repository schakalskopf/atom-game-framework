/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.map;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import sg.atom2d.geo.tile.Tile2D;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Map2DGeometryCreator {

    BatchNode batchNode;
    AssetManager assetManager;

    public void addCell(Cell2D cell) {
    }

    public void addCell(Tile2D tile) {
    }

    public void addCell(Quad quad) {
    }

    public Geometry getGeometry(){
        return new Geometry();
    }
    public BatchNode getBatchNode() {
        return batchNode;
    }

    public Material getTileMat() {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.setColor("Color", ColorRGBA.Blue);
        return mat;
    }

    public Material getTileMat(Texture tileTex) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mat.getAdditionalRenderState().setAlphaFallOff(0.1f);
        mat.getAdditionalRenderState().setPolyOffset(0.01f, 0.01f);
        mat.setTexture("ColorMap", tileTex);
        return mat;
    }
}
