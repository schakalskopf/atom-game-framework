/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.shape;

/**
 *
 * @author hungcuong
 */
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.mesh.IndexBuffer;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import static com.jme3.util.BufferUtils.*;
import java.io.IOException;
import java.nio.FloatBuffer;

public class Pie extends Mesh {

    private int axisSamples;
    private int radialSamples;
    private float radius;
    private float radius2;
    private float height;
    private boolean closed;
    private boolean inverted;

    /**
     * Default constructor for serialization only. Do not use.
     */
    public Pie() {
    }

    /**
     * Creates a new Cylinder. By default its center is the origin. Usually, a
     * higher sample number creates a better looking cylinder, but at the cost
     * of more vertex information.
     *
     * @param axisSamples
     *            Number of triangle samples along the axis.
     * @param radialSamples
     *            Number of triangle samples along the radial.
     * @param radius
     *            The radius of the cylinder.
     * @param height
     *            The cylinder's height.
     */
    public Pie(int axisSamples, int radialSamples,
            float radius, float height) {
        this(axisSamples, radialSamples, radius, height, false);
    }

    /**
     * Creates a new Cylinder. By default its center is the origin. Usually, a
     * higher sample number creates a better looking cylinder, but at the cost
     * of more vertex information. <br>
     * If the cylinder is closed the texture is split into axisSamples parts:
     * top most and bottom most part is used for top and bottom of the cylinder,
     * rest of the texture for the cylinder wall. The middle of the top is
     * mapped to texture coordinates (0.5, 1), bottom to (0.5, 0). Thus you need
     * a suited distorted texture.
     *
     * @param axisSamples
     *            Number of triangle samples along the axis.
     * @param radialSamples
     *            Number of triangle samples along the radial.
     * @param radius
     *            The radius of the cylinder.
     * @param height
     *            The cylinder's height.
     * @param closed
     *            true to create a cylinder with top and bottom surface
     */
    public Pie(int axisSamples, int radialSamples,
            float radius, float height, boolean closed) {
        this(axisSamples, radialSamples, radius, height, closed, false);
    }

    /**
     * Creates a new Cylinder. By default its center is the origin. Usually, a
     * higher sample number creates a better looking cylinder, but at the cost
     * of more vertex information. <br>
     * If the cylinder is closed the texture is split into axisSamples parts:
     * top most and bottom most part is used for top and bottom of the cylinder,
     * rest of the texture for the cylinder wall. The middle of the top is
     * mapped to texture coordinates (0.5, 1), bottom to (0.5, 0). Thus you need
     * a suited distorted texture.
     *
     * @param axisSamples
     *            Number of triangle samples along the axis.
     * @param radialSamples
     *            Number of triangle samples along the radial.
     * @param radius
     *            The radius of the cylinder.
     * @param height
     *            The cylinder's height.
     * @param closed
     *            true to create a cylinder with top and bottom surface
     * @param inverted
     *            true to create a cylinder that is meant to be viewed from the
     *            interior.
     */
    public Pie(int axisSamples, int radialSamples,
            float radius, float height, boolean closed, boolean inverted) {
        this(axisSamples, radialSamples, radius, radius, height, closed, inverted);
    }

    public Pie(int axisSamples, int radialSamples,
            float radius, float radius2, float height, boolean closed, boolean inverted) {
        super();
        updateGeometry(axisSamples, radialSamples, radius, radius2, height, closed, inverted);
    }

    /**
     * @return the number of samples along the cylinder axis
     */
    public int getAxisSamples() {
        return axisSamples;
    }

    /**
     * @return Returns the height.
     */
    public float getHeight() {
        return height;
    }

    /**
     * @return number of samples around cylinder
     */
    public int getRadialSamples() {
        return radialSamples;
    }

    /**
     * @return Returns the radius.
     */
    public float getRadius() {
        return radius;
    }

    public float getRadius2() {
        return radius2;
    }

    /**
     * @return true if end caps are used.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * @return true if normals and uvs are created for interior use
     */
    public boolean isInverted() {
        return inverted;
    }

    /**
     * Set the half angle of the cone.
     *
     * @param radians
     */
    public void setHalfAngle(float radians) {
        updateGeometry(getAxisSamples(), getRadialSamples(), FastMath.tan(radians), getRadius2(), getHeight(), isClosed(), isInverted());
    }

    /**
     * Set the bottom radius of the 'cylinder' to differ from the top radius.
     * This makes the Geometry be a frustum of pyramid, or if set to 0, a cone.
     * <p>
     * <strong>Note:</strong> this method causes the tri-mesh geometry data
     * to be recalculated, see <a href="package-summary.html#mutator-methods">
     * the package description</a> for more information about this.
     *
     * @param radius the second radius to set.
     * @see {@link Cone}
     * @deprecated use {@link #recomputeGeometry(int, int, float, float, boolean, boolean)}.
     */
    public void setRadius2(float radius2) {
        updateGeometry(axisSamples, radialSamples, radius, radius2, height, closed, inverted);
    }

    /**
     * Rebuilds the cylinder based on a new set of parameters.
     *
     * @param axisSamples the number of samples along the axis.
     * @param radialSamples the number of samples around the radial.
     * @param radius the radius of the bottom of the cylinder.
     * @param radius2 the radius of the top of the cylinder.
     * @param height the cylinder's height.
     * @param closed should the cylinder have top and bottom surfaces.
     * @param inverted is the cylinder is meant to be viewed from the inside.
     */
    public void updateGeometry(int axisSamples, int radialSamples,
            float radius, float radius2, float height, boolean closed, boolean inverted) {
        this.axisSamples = axisSamples + (closed ? 2 : 0);
        this.radialSamples = radialSamples;
        this.radius = radius;
        this.radius2 = radius2;
        this.height = height;
        this.closed = closed;
        this.inverted = inverted;

//        VertexBuffer pvb = getBuffer(Type.Position);
//        VertexBuffer nvb = getBuffer(Type.Normal);
//        VertexBuffer tvb = getBuffer(Type.TexCoord);

        // Vertices
        int vertexCount =(axisSamples * (radialSamples + 1) + (closed ? 2 : 0) + axisSamples - 4);

        setBuffer(Type.Position, 3, createVector3Buffer(getFloatBuffer(Type.Position), vertexCount));

        // Normals
        setBuffer(Type.Normal, 3, createVector3Buffer(getFloatBuffer(Type.Normal), vertexCount));

        // Texture co-ordinates
        setBuffer(Type.TexCoord, 2, createVector2Buffer(vertexCount));

        int triCount = (((closed ? 2 : 0) + 2 * (axisSamples - 1)) * radialSamples);


        setBuffer(Type.Index, 3, createShortBuffer(getShortBuffer(Type.Index), 3 * triCount));

        // generate geometry
        float slice = (360f - 145f) / 360f;

        float inverseRadial = slice / radialSamples;
        float inverseAxisLess = 1.0f / (closed ? axisSamples - 3 : axisSamples - 1);
        float inverseAxisLessTexture = 1.0f / (axisSamples - 1);
        float halfHeight = 0.5f * height;

        // Generate points on the unit circle to be used in computing the mesh
        // points on a cylinder slice.
        float[] sin = new float[radialSamples + 1];
        float[] cos = new float[radialSamples + 1];

        for (int radialCount = 0; radialCount < radialSamples; radialCount++) {
            float angle = FastMath.TWO_PI * inverseRadial * radialCount;
            cos[radialCount] = FastMath.cos(angle);
            sin[radialCount] = FastMath.sin(angle);
        }
        sin[radialSamples] = sin[0];
        cos[radialSamples] = cos[0];

        FloatBuffer nb = getFloatBuffer(Type.Normal);
        FloatBuffer pb = getFloatBuffer(Type.Position);
        FloatBuffer tb = getFloatBuffer(Type.TexCoord);

        // generate the cylinder itself
        Vector3f tempNormal = new Vector3f();
        for (int axisCount = 0, i = 0; axisCount < axisSamples; axisCount++, i++) {
            float axisFraction;
            float axisFractionTexture;
            int topBottom = 0;
            if (!closed) {
                axisFraction = axisCount * inverseAxisLess; // in [0,1]
                axisFractionTexture = axisFraction;
            } else {
                if (axisCount == 0) {
                    topBottom = -1; // bottom
                    axisFraction = 0;
                    axisFractionTexture = inverseAxisLessTexture;
                } else if (axisCount == axisSamples - 1) {
                    topBottom = 1; // top
                    axisFraction = 1;
                    axisFractionTexture = 1 - inverseAxisLessTexture;
                } else {
                    axisFraction = (axisCount - 1) * inverseAxisLess;
                    axisFractionTexture = axisCount * inverseAxisLessTexture;
                }
            }
            float z = -halfHeight + height * axisFraction;

            // compute center of slice
            Vector3f sliceCenter = new Vector3f(0, 0, z);

            // compute slice vertices with duplication at end point
            int save = i;
            for (int radialCount = 0; radialCount < radialSamples; radialCount++, i++) {
                float radialFraction = radialCount * inverseRadial; // in [0,1)
                tempNormal.set(cos[radialCount], sin[radialCount], 0);
                if (topBottom == 0) {
                    if (!inverted) {
                        nb.put(tempNormal.x).put(tempNormal.y).put(tempNormal.z);
                    } else {
                        nb.put(-tempNormal.x).put(-tempNormal.y).put(-tempNormal.z);
                    }
                } else {
                    nb.put(0).put(0).put(topBottom * (inverted ? -1 : 1));
                }

                tempNormal.multLocal((radius - radius2) * axisFraction + radius2).addLocal(sliceCenter);
                pb.put(tempNormal.x).put(tempNormal.y).put(tempNormal.z);

                tb.put((inverted ? 1 - radialFraction : radialFraction)).put(axisFractionTexture);
            }

            BufferUtils.copyInternalVector3(pb, save, i);
            BufferUtils.copyInternalVector3(nb, save, i);

            tb.put((inverted ? 0.0f : 1.0f)).put(axisFractionTexture);
        }

        for (int axisCount = 0; axisCount < axisSamples - 2; axisCount++) {
            float axisFraction = axisCount * inverseAxisLess;
            pb.put(0).put(0).put(-halfHeight + axisFraction); // bottom center
            nb.put(0).put(0).put(-1 * (inverted ? -1 : 1));
            tb.put(0.5f).put(0);
        }
        /*
        if (closed) {
        pb.put(0).put(0).put(-halfHeight); // bottom center
        nb.put(0).put(0).put(-1 * (inverted ? -1 : 1));
        tb.put(0.5f).put(0);
        pb.put(0).put(0).put(halfHeight); // top center
        nb.put(0).put(0).put(1 * (inverted ? -1 : 1));
        tb.put(0.5f).put(1);
        }
         */

        IndexBuffer ib = getIndexBuffer();
        int index = 0;
        // Connectivity
        for (int axisCount = 0, axisStart = 0; axisCount < axisSamples - 1; axisCount++) {
            int i0 = axisStart;
            int i1 = i0 + 1;
            axisStart += radialSamples + 1;
            int i2 = axisStart;
            int i3 = i2 + 1;
            for (int i = 0; i < radialSamples; i++) {
                //System.out.println(" i : " + i + " i1 : " + i1 + " i2 : " + i2 + " i3 : " + i3 );
                if (closed && axisCount == 0) {
                    if (i != radialSamples - 1) {
                        if (!inverted) {
                            ib.put(index++, i0++);
                            ib.put(index++, vertexCount - (axisSamples - 2));
                            ib.put(index++, i1++);
                        } else {
                            ib.put(index++, i0++);
                            ib.put(index++, i1++);
                            ib.put(index++, vertexCount - 2);
                        }
                    } else {
                    }
                } else if (closed && axisCount == axisSamples - (axisSamples - 2)) {
                    if (i != radialSamples - 1) {
                        ib.put(index++, i2++);
                        ib.put(index++, inverted ? vertexCount - 1 : i3++);
                        ib.put(index++, inverted ? i3++ : vertexCount - 1);
                    } else {
                    }
                } else {
                    if (i != radialSamples - 1) {
                        ib.put(index++, i0++);
                        ib.put(index++, inverted ? i2 : i1);
                        ib.put(index++, inverted ? i1 : i2);
                        ib.put(index++, i1++);
                        ib.put(index++, inverted ? i2++ : i3++);
                        ib.put(index++, inverted ? i3++ : i2++);
                    } else {
                        ib.put(index++, i0);
                        ib.put(index++, vertexCount - (axisSamples - 2) + axisCount - 1);
                        ib.put(index++, i0 + radialSamples );

                        ib.put(index++, vertexCount - (axisSamples - 2) + axisCount - 1);
                        ib.put(index++, vertexCount - (axisSamples - 2) + axisCount - 2);
                        ib.put(index++, i0 + radialSamples );


                    }
                }
            }
        }

        updateBound();
        updateCounts();
    }

    public void read(JmeImporter e) throws IOException {
        super.read(e);
        InputCapsule capsule = e.getCapsule(this);
        axisSamples = capsule.readInt("axisSamples", 0);
        radialSamples = capsule.readInt("radialSamples", 0);
        radius = capsule.readFloat("radius", 0);
        radius2 = capsule.readFloat("radius2", 0);
        height = capsule.readFloat("height", 0);
        closed = capsule.readBoolean("closed", false);
        inverted = capsule.readBoolean("inverted", false);
    }

    public void write(JmeExporter e) throws IOException {
        super.write(e);
        OutputCapsule capsule = e.getCapsule(this);
        capsule.write(axisSamples, "axisSamples", 0);
        capsule.write(radialSamples, "radialSamples", 0);
        capsule.write(radius, "radius", 0);
        capsule.write(radius2, "radius2", 0);
        capsule.write(height, "height", 0);
        capsule.write(closed, "closed", false);
        capsule.write(inverted, "inverted", false);
    }
}
