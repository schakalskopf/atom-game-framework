/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.ngon.hex;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Vector2f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import java.io.IOException;

/**
 * A Mesh represent a HexCell.
 * @author cuong.nguyenmanh2
 */
public class PointyHexagon extends Mesh {

    float a;
    float rt = (float) Math.sqrt(3) / 4;
    private Vector2f texOffset, texScale;

    /**
     *
     * @param a_value : Note see 30-60-90 triangle
     */
    public PointyHexagon(float a_value) {
        super();
        a = a_value;
        this.texOffset = new Vector2f();
        this.texScale = new Vector2f();
        //updateGeometry();
    }

    public void setTexcoordOffset(Vector2f offset, Vector2f scale) {
        this.texOffset.set(offset);
        this.texScale.set(scale);
    }

    public void updateGeometry() {
//        setVertexCount(6);
        //setTriangleCount(4);
        float[] vert = {
            0, 0, a * rt, //1
            a / 4, 0, 0, //2
            3 * a / 4, 0, 0, //3
            4 * a / 4, 0, a * rt, //4
            3 * a / 4, 0, 2 * a * rt, //5
            a / 4, 0, 2 * a * rt}; //6

        float[] norm = {
            0, 1, 0,
            0, 1, 0,
            0, 1, 0,
            0, 1, 0};

        int[] index = {
            0, 5, 1,
            2, 4, 3,
            1, 4, 2,
            4, 1, 5
        };
        float[] texcoords = {
            texOffset.x + 0, texOffset.y + texScale.y * rt, //1
            texOffset.x + texScale.x / 4, texOffset.y + 0, //2
            texOffset.x + 3 * texScale.x / 4, texOffset.y + 0, //3
            texOffset.x + 4 * texScale.x / 4, texOffset.y + texScale.y * rt, //4
            texOffset.x + 3 * texScale.x / 4, texOffset.y + 2 * texScale.y * rt, //5
            texOffset.x + texScale.x / 4, texOffset.y + 2 * texScale.y * rt}; //6
        setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vert));
        setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(norm));
        setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(index));
        setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texcoords));
        updateBound();
        updateCounts();

    }

    public void read(JmeImporter e) throws IOException {
        super.read(e);
        InputCapsule capsule = e.getCapsule(this);
        a = capsule.readFloat("a", 0);
        rt = capsule.readFloat("rt", 0);
    }

    public void write(JmeExporter e) throws IOException {
        super.write(e);
        OutputCapsule capsule = e.getCapsule(this);
        capsule.write(a, "a", 0);
        capsule.write(rt, "rt", 0);
    }
}
