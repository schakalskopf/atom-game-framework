/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.test.tile;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Node;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import sg.atom2d.assets.tiled.LayerDefinition;
import sg.atom2d.assets.tiled.TMXLoader;
import sg.atom2d.assets.tiled.MapStructure;
import sg.atom2d.assets.tiled.TileSetDefinition;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TiledLoader3D {
    ArrayList<Texture> textures;
    MapStructure map;
    BatchNode batchNode;
    AssetManager assetManager;
    Node rootNode;

    public TiledLoader3D(AssetManager assetManager, Node rootNode) {
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        textures = new ArrayList<Texture>(10);
        //texturesDebug();
    }

    public void alignCam(Camera cam) {
        // Align the camera
        cam.setLocation(new Vector3f(map.width / 2, -map.height / 2, 80));
        cam.lookAt(new Vector3f(map.width / 2, -map.height / 2, 0), Vector3f.UNIT_Z);
    }

    public void loadAll(String mapFileName) throws Exception {
        batchNode = new BatchNode("Batch");
        assetManager.registerLoader(TMXLoader.class, "tmx");
        map = null;
        map = (MapStructure) assetManager.loadAsset(mapFileName);
        map.build();

        System.out.printf("MAP W: %d H:%d\n", map.width, map.height);

        List<TileSetDefinition> tilesets = map.getTilesets();
        String mapDir = mapFileName.substring(0, mapFileName.lastIndexOf("/"));

        //assetManager.registerLocator(mapDir, FileLocator.class);
        for (TileSetDefinition set : tilesets) {
            //System.out.printf("TILESET firstGID: '%d' name: '%s' %d %d %d\n",set.getFirstGid(), set.getSource(), set.numX, set.numY, set.numOfTiles);
            String source = set.getSource();
            //source = source.substring(source.lastIndexOf("..") + 1);
            source = FilenameUtils.normalize(mapDir + "/" + source);
            source = source.replace("\\", "/");
            Texture loadTexture = assetManager.loadTexture(source);
            //loadTexture.setWrap(Texture.WrapMode.Repeat);
            textures.add(loadTexture);
        }

        List<LayerDefinition> layers = map.getLayers();

        for (final LayerDefinition layer : layers) {
            //System.out.printf("LAYER name: %s\n", layer.getName());
            final int w = layer.getWidth();
            final int h = layer.getHeight();

            if (layer.getName().equalsIgnoreCase("protection")
                    || layer.getName().equalsIgnoreCase("collision")
                    || layer.getName().equalsIgnoreCase("blend_roof")
                    || layer.getName().equalsIgnoreCase("blend_ground")) {
                continue;
            }
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    //FIXME: Tile is indicate by an Integer index!
                    final int gid = layer.getTileAt(x, y);
                    /*
                     if (x == w - 1) {
                     System.out.print(gid);
                     } else {
                     System.out.print(gid + ",");
                     }
                     */
                    if (gid != 0) {
                        TileSetDefinition set = findTileSet(gid);

                        if (set != null) {
                            
                            Vector3f pos = transformToPosition(x, y, layer);
                            Vector4f uv = transformToUV(set, gid);
                            Material mat = transformToMaterial(set);
                            Spatial tileGeo = transformToSpatial(mat, pos, uv);
                            
                            batchNode.attachChild(tileGeo);
                        } else {
                            //System.out.println("Can not find tild id:" + gid);
                            //mat = getTileMat();
                        }
                    }
                }
                //System.out.println();
            }

            // Batch it

            batchNode.setQueueBucket(RenderQueue.Bucket.Transparent);
            batchNode.batch();
            rootNode.attachChild(batchNode);
        }
    }

    // Transform : index -> TileSetDefinition
    public TileSetDefinition findTileSet(int index) {

        final List<TileSetDefinition> tilesets = map.getTilesets();
        for (final TileSetDefinition set : tilesets) {

            int first = set.getFirstGid();

            if (index >= first && index <= first + set.numOfTiles) {
                return set;
            }
        }
        return null;
    }

    public Vector3f transformToPosition(int x, int y, LayerDefinition layer) {
        List<LayerDefinition> layers = map.getLayers();
        int orderOfLayer = layers.lastIndexOf(layer);
        return new Vector3f(x, y, 0.01f * orderOfLayer);
    }
    // Mapping: TileSetDefinition + Position -> Spatial

    public Vector4f transformToUV(TileSetDefinition set, int gid) {
        int lgid = (gid - set.getFirstGid());
        int cx = lgid % set.numX;
        int cy = lgid / set.numX;

        float u = 1f / set.numX * cx;
        float v = 1 - 1f / set.numY * (cy + 1);
        float u1 = 1f / set.numX * (cx + 1);
        float v1 = 1 - 1f / set.numY * cy;

        return new Vector4f(u, v, u1, v1);
    }

    public Material transformToMaterial(TileSetDefinition set) {
        List<TileSetDefinition> tilesets = map.getTilesets();

        Material mat;
        int tileSetIndex = tilesets.indexOf(set);
        Texture tileTex = textures.get(tileSetIndex);
        mat = getTileMat(tileTex);
        return mat;
    }

    public Spatial transformToSpatial(Material mat, Vector3f pos, Vector4f uv) {

        Quad quad = new Quad(1, 1);

        float u = uv.x;
        float v = uv.y;
        float u1 = uv.z;
        float v1 = uv.w;

        //System.out.println(lgid + " Num:" + set.numX + " " + set.numY);
        //System.out.println(" cx:" + cx + " cy:" + cy + " u: " + u + " v:" + v);
        // Set the UV
        FloatBuffer floatBuffer;
        boolean flipCoords = false;
        if (flipCoords) {
            floatBuffer = BufferUtils.createFloatBuffer(u, v1, u1, v1, u1, v, u, v);
        } else {
            floatBuffer = BufferUtils.createFloatBuffer(u, v, u1, v, u1, v1, u, v1);

        }
        quad.setBuffer(VertexBuffer.Type.TexCoord, 2, floatBuffer);

        Geometry tileGeo = new Geometry("Tile" + pos.y + " " + pos.x, quad);
        //tileGeo.setQueueBucket(RenderQueue.Bucket.Transparent);
        tileGeo.setMaterial(mat);

        tileGeo.setLocalTranslation(pos.x, -pos.y, pos.z);

        return tileGeo;
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

    public void texturesDebug() {

        Box b = new Box(1, 1, 1);

        for (Texture tex : textures) {
            Geometry geom = new Geometry("Box", b);
            Material mat = getTileMat(tex);
            geom.setMaterial(mat);

            geom.setLocalTranslation(textures.indexOf(tex) * 3, 0, 0);
            rootNode.attachChild(geom);
        }
    }

    public void createTiledMesh() {
    }

    public void createTiledNode() {
    }
}
