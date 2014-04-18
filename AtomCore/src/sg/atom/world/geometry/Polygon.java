package sg.atom.world.geometry;

import sg.atom.world.geometry.algebra.Vec3d;
import sg.atom.world.geometry.algebra.Vec2d;
import sg.atom.world.geometry.algebra.Quaternion;
import java.io.*;

import java.util.LinkedList;

/**
 * Polygon class used in bsp tree generation. The polygon vertices must be in
 * counter-clockwise order. Note that this class handles only planar polygons.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Polygon {

    public Vertex[] vertices;
    private String textureName1;
    private String textureName2;
    private boolean hasBeenUsed;

    public Polygon() {
    }

    /**
     * Construct a polygon (triangle) from three points and a primary texture
     * filename.
     *
     * @param a first point in polygon
     * @param b second point in polygon
     * @param c third point in polygon
     * @param texture texture filename
     */
    public Polygon(Vertex a, Vertex b, Vertex c, String textureName) {
        hasBeenUsed = false;

        vertices = new Vertex[3];
        vertices[0] = a;
        vertices[1] = b;
        vertices[2] = c;
        this.textureName1 = textureName;
        this.textureName2 = null;
    }

    /**
     * Construct a polygon (triangle) from three points and texture filenames.
     *
     * @param a first point in polygon
     * @param b second point in polygon
     * @param c third point in polygon
     * @param texture1 primary texture filename
     * @param texture2 secondary texture filename
     */
    public Polygon(Vertex a, Vertex b, Vertex c, String textureName1, String textureName2) {
        hasBeenUsed = false;
        vertices = new Vertex[3];
        vertices[0] = a;
        vertices[1] = b;
        vertices[2] = c;
        this.textureName1 = textureName1;
        this.textureName2 = textureName2;
    }

    /**
     * Construct a polygon (quad) from four points and a texture filename.
     *
     * @param a first point in polygon
     * @param b second point in polygon
     * @param c third point in polygon
     * @param d fourth point in polygon
     * @param textureName texture filename
     */
    public Polygon(Vertex a, Vertex b, Vertex c, Vertex d, String textureName) {
        hasBeenUsed = false;

        vertices = new Vertex[4];
        vertices[0] = a;
        vertices[1] = b;
        vertices[2] = c;
        vertices[3] = d;
        this.textureName1 = textureName;
        this.textureName2 = null;
    }

    /**
     * Construct a polygon (quad) from four points and texture filenames.
     *
     * @param a first point in polygon
     * @param b second point in polygon
     * @param c third point in polygon
     * @param d fourth point in polygon
     * @param textureName1 primary texture filename
     * @param textureName2 secondary texture filename
     */
    public Polygon(Vertex a, Vertex b, Vertex c, Vertex d,
            String textureName1, String textureName2) {
        hasBeenUsed = false;

        vertices = new Vertex[4];
        vertices[0] = a;
        vertices[1] = b;
        vertices[2] = c;
        vertices[3] = d;
        this.textureName1 = textureName1;
        this.textureName2 = textureName2;
    }

    /**
     * Construct a polygon from an array of vertices
     *
     * @param array array to construct the polygon from
     * @param texture primary texture filename
     */
    public Polygon(Vertex[] array, String texture) {
        hasBeenUsed = false;
        vertices = array;
        textureName1 = texture;
    }

    /**
     * Construct a polygon from an array of vertices
     *
     * @param array array to construct the polygon from
     * @param texture1 primary texture filename
     * @param texture2 secondary texture filename
     */
    public Polygon(Vertex[] array, String texture1, String texture2) {
        hasBeenUsed = false;
        vertices = array;
        textureName1 = texture1;
        textureName2 = texture2;
    }

    /**
     * Get the area of this polygon.
     *
     * @return a <code>float</code> value
     */
    public float area() {
        float result = 0;
        Vertex A = vertices[0];
        for (int i = 1; i <= vertices.length - 2; i++) {
            Vertex B = vertices[i];
            Vertex C = vertices[i + 1];
            result += (new Triangle(A, B, C, null)).area();
        }
        return result;
    }

    /**
     * Test whether a polygon is behind another polygon. This is a non-symmetric
     * comparison, ie. if polygon 1 is behind polygon 2 it doesn't mean that
     * polygon 2 is behind polygon 1.
     *
     * @param poly the polygon to test
     * @return true, if poly is behind this polygon
     */
    public boolean polygonInBack(Polygon poly, float epsilon) {
        Plane plane = this.toPlane();

        for (int i = 0; i < poly.getNumPoints(); i++) {
            if (plane.calculateSide(poly.getVertex(i).pos, epsilon) == Plane.BEHIND) {
                return true;
            }
        }

        return false;
    }

    /**
     * Compute the center of this polygon.
     *
     * @return the polygons center
     */
    public Vertex center() {
        Vertex result = new Vertex(vertices[0]);
        for (int i = 1; i < vertices.length; i++) {
            result.addTo(vertices[i]);
        }
        result.scaleTo(1.0f / (float) vertices.length);
        return result;
    }

    /**
     * Flip the polygon vertex order. Clockwise becomes counter-clockwise etc.
     *
     * @return a <code>Polygon</code> value
     */
    public Polygon flipped() {
        Vertex[] v = new Vertex[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            v[i] = vertices[vertices.length - 1 - i];
        }
        return new Polygon(v, textureName1, textureName2);
    }

    /**
     * Get the vertex specified by index.
     *
     * @param index index to vertex to return
     * @return vertex specified by index
     */
    public Vertex getVertex(int index) {
        return vertices[index];
    }

    /**
     * Getter.
     *
     * @return Primary texture name
     */
    public String getPrimaryTextureName() {
        return textureName1;
    }

    /**
     * Getter.
     *
     * @return Secondary texture name
     */
    public String getSecondaryTextureName() {
        return textureName2;
    }

    /**
     * Setter.
     *
     * @param value Primary texture name
     */
    public void setSecondaryTextureName(String value) {
        textureName2 = value;
    }

    /**
     * Getter.
     *
     * @return Used status
     */
    public boolean getHasBeenUsed() {
        return hasBeenUsed;
    }

    /**
     * Setter.
     *
     * @param v Used status
     */
    public void setHasBeenUsed(boolean v) {
        hasBeenUsed = v;
    }

    /**
     * Getter.
     *
     * @return Number of points
     */
    public int getNumPoints() {
        return vertices.length;
    }

    /**
     * Get a String descriptons of this polygon.
     *
     * @return string describing this polygon
     */
    public String toString() {
        String result = "";

        for (int i = 0; i < vertices.length; i++) {
            result += "Vertex " + i + ": " + vertices[i].toString() + "\n";
        }

        return result;
    }

    /**
     * Get the plane this polygon lies on.
     *
     * @return a <code>Plane</code> value
     */
    public Plane toPlane() {
        return new Plane(this);
    }

    /**
     * Split polygon by plane. Method returns an array of two polygons in which
     * the first polygon is in front of the dividing plane and the second
     * behind.
     *
     * @param divider the dividing plane
     * @return array of two polygons
     */
    public Polygon[] split(Plane divider, float epsilon) {
        LinkedList frontPoints = new LinkedList();
        LinkedList backPoints = new LinkedList();

        Vertex ptA = getVertex(getNumPoints() - 1), ptB;
        int sideA = divider.calculateSide(ptA.pos, epsilon);

        // Traverse the edges of the polygon, if two consecutive points 
        // are on different sides of the plane do ray-plane intersection test 
        // and add the intersection point to both sides.
        for (int i = 0; i < getNumPoints(); i++) {
            ptB = getVertex(i);
            int sideB = divider.calculateSide(ptB.pos, epsilon);

            if (sideB == Plane.COINCIDAL) {
                frontPoints.add(ptB);
                backPoints.add(ptB);
            } else {
                if (sideA != Plane.COINCIDAL && sideA != sideB) {
                    Vec3d v = ptB.pos.sub(ptA.pos);
                    float sect = -divider.classifyPoint(ptA.pos) / (divider.getNormal().dot(v));

                    // Find the intersection position
                    Vec3d intersectionPos = ptA.pos.add(v.scale(sect));

                    // Compute the vertex normal by interpolating
                    Quaternion q1 = new Quaternion(0, ptA.normal);
                    Quaternion q2 = new Quaternion(0, ptB.normal);
                    Quaternion q = new Quaternion();
                    q.interpolate(q1, q2, (float) Math.acos(sect));

                    Vec3d intersectionNormal = new Vec3d(q.i, q.j, q.k);
                    intersectionNormal.normalize();

                    // Interpolate texture coordinates
                    Vec2d vtex = ptB.tex.sub(ptA.tex);
                    Vec2d intersectionTexcoord = ptA.tex.add(vtex.scale(sect));

                    // Interpolate vertex color
                    Vec3d intersectionColor = new Vec3d(1, 1, 1);

                    Vertex intersectionVertex = new Vertex(intersectionPos, intersectionNormal,
                            intersectionTexcoord, intersectionColor);

                    frontPoints.add(intersectionVertex);
                    backPoints.add(intersectionVertex);
                }

                if (sideB == Plane.INFRONT) {
                    frontPoints.add(ptB);
                } else {
                    backPoints.add(ptB);
                }
            }

            ptA = ptB;
            sideA = sideB;
        }

        Vertex[] fP = new Vertex[frontPoints.size()];
        Vertex[] bP = new Vertex[backPoints.size()];

        frontPoints.toArray(fP);
        backPoints.toArray(bP);

        Polygon[] result = {new Polygon(fP, textureName1, textureName2),
            new Polygon(bP, textureName1, textureName2)};

        return result;
    }

    /**
     * Read the polygon from ObjectInput stream.
     *
     * @param in stream to read from
     * @exception IOException if an error occurs
     * @exception ClassNotFoundException if an error occurs
     */
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        int size = in.readInt();
        vertices = new Vertex[size];
        for (int i = 0; i < size; i++) {
            vertices[i] = new Vertex();
            vertices[i].readExternal(in);
        }
        textureName1 = in.readUTF();
    }

    /**
     * Write the polygon into ObjectOutput stream.
     *
     * @param out stream to write into
     * @exception IOException if an error occurs
     */
    public void writeExternal(ObjectOutput out)
            throws IOException {
        out.writeInt(vertices.length);
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].writeExternal(out);
        }
        out.writeUTF(textureName1);
    }
}
