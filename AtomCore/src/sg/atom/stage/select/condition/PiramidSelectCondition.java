/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select.condition;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import sg.atom.stage.select.AbstractSpatialSelectCondition;
import sg.atom.stage.select.ui.SelectRectUI;

/**
 *
 * @author atomix
 */
public class PiramidSelectCondition extends AbstractSpatialSelectCondition {

    Vector3f t;
    Vector3f b1;
    Vector3f b2;
    Vector3f b3;
    Vector3f b4;
    private Camera cam;

    public PiramidSelectCondition(Vector3f t, Vector3f b1, Vector3f b2, Vector3f b3, Vector3f b4) {
        this.t = t;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
    }

    public PiramidSelectCondition(Camera cam, SelectRectUI selectRectUI) {
        float x, y, x1, y1;
        float sx = selectRectUI.x;
        float sy = selectRectUI.y;

        float sx1 = selectRectUI.x + selectRectUI.width;
        float sy1 = selectRectUI.y + selectRectUI.height;

        x = Math.min(sx, sx1);
        x1 = Math.max(sx, sx1);
        y = Math.min(sy, sy1);
        y1 = Math.max(sy, sy1);

        Vector3f t = cam.getLocation();
        //Vector3f t = cam.getWorldCoordinates(new Vector2f(x1 / 2, y1 / 2), near/far);
        b1 = cam.getWorldCoordinates(new Vector2f(x, y), 1f);
        b2 = cam.getWorldCoordinates(new Vector2f(x1, y), 1f);
        b3 = cam.getWorldCoordinates(new Vector2f(x1, y1), 1f);
        b4 = cam.getWorldCoordinates(new Vector2f(x, y1), 1f);

        this.cam = cam;
        this.t = t;
    }

    @Override
    public boolean isSelected(Spatial spatial) {
        Vector3f centerPoint = spatial.getWorldTranslation();
        return isVectorInPyramid(centerPoint, t, b1, b2, b3, b4);

    }

    @Override
    public Spatial getDebugShape(AssetManager assetManager) {
        //rootNode.attachChild(createPiramid(t, b1, b2, b3, b4));
        //stageManager.getWorldManager().getWorldNode().setCullHint(Spatial.CullHint.Always);

        //createSphere(t, rootNode);
        //createSphere(b1, rootNode);
        //createSphere(b2, rootNode);
        //createSphere(b3, rootNode);
        //createSphere(b4, rootNode);
        return new Geometry();
    }

    public Spatial createPiramid(AssetManager assetManager, Vector3f t, Vector3f b1, Vector3f b2, Vector3f b3, Vector3f b4) {
        Mesh m = new Mesh();

        // Vertex positions in space
        Vector3f[] vertices = new Vector3f[5];
        vertices[0] = new Vector3f();
        vertices[1] = b1.clone().subtract(t);
        vertices[2] = b2.clone().subtract(t);
        vertices[3] = b3.clone().subtract(t);
        vertices[4] = b4.clone().subtract(t);

        // Texture coordinates
        Vector2f[] texCoord = new Vector2f[5];
        texCoord[0] = new Vector2f(0, 0);
        texCoord[1] = new Vector2f(1, 0);
        texCoord[2] = new Vector2f(0, 1);
        texCoord[3] = new Vector2f(1, 1);
        texCoord[4] = new Vector2f(1, 1);
        // Indexes. We define the order in which mesh should be constructed
        int[] indexes = {0, 2, 1, 0, 3, 2, 0, 4, 3, 0, 4, 1};

        // Setting buffers
        m.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        m.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(indexes));
        m.updateBound();

        Geometry geom = new Geometry("OurMesh", m);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        //mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Additive);
        //mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        //mat.getAdditionalRenderState().setDepthTest(false);
        geom.setMaterial(mat);
        geom.setQueueBucket(RenderQueue.Bucket.Transparent);
        geom.setLocalTranslation(t);
        System.out.println("Create piramid");
        return geom;

    }

    Vector3f rndVec() {
        return new Vector3f(FastMath.rand.nextFloat(), FastMath.rand.nextFloat(), FastMath.rand.nextFloat());
    }

    public boolean isVectorInPyramid(Vector3f v, Vector3f t, Vector3f b1, Vector3f b2, Vector3f b3, Vector3f b4) {
        int cnt = 0;

        Ray ray = new Ray(v, rndVec());


        if (ray.intersectWherePlanar(t, b1, b2, new Vector3f())) {
            cnt++;
        }
        //if (cnt>1) return false;
        if (ray.intersectWherePlanar(t, b2, b3, new Vector3f())) {
            cnt++;
        }
        if (cnt > 1) {
            return false;
        }
        if (ray.intersectWherePlanar(t, b3, b4, new Vector3f())) {
            cnt++;
        }
        if (cnt > 1) {
            return false;
        }
        if (ray.intersectWherePlanar(t, b4, b1, new Vector3f())) {
            cnt++;
        }
        if (cnt > 1) {
            return false;
        }

        if (ray.intersectWherePlanar(b1, b2, b3, new Vector3f())) {
            cnt++;
        }
        if (cnt > 1) {
            return false;
        }
        if (ray.intersectWherePlanar(b1, b3, b4, new Vector3f())) {
            cnt++;
        }
        if (cnt > 1) {
            return false;
        }

        if (cnt == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AbstractSpatialSelectCondition createCondition(Camera cam, SelectRectUI selectRectUI) {
        return new PiramidSelectCondition(cam, selectRectUI);
    }
}
