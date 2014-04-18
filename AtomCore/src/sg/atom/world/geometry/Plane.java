package sg.atom.world.geometry;

import sg.atom.world.geometry.algebra.Vec3d;
import java.io.*;

/**
 * A plane class used in bsp tree generation.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Plane {

    /**
     * Possible return values for calculateSide()
     */
    public static final int BEHIND = -1;
    public static final int INFRONT = 1;
    public static final int COINCIDAL = 0;
    public static final int SPANNING = 2;
    private float a, b, c, d;

    /**
     * Default constructor
     *
     */
    public Plane() {
    }

    /**
     * Constructor
     *
     * @param A coefficient of x in plane equation
     * @param B coefficient of y in plane equation
     * @param C coefficient of z in plane equation
     * @param D constant in plane equation
     */
    public Plane(float A, float B, float C, float D) {
        a = A;
        b = B;
        c = C;
        d = D;
    }

    /**
     * Construct plane from polygon
     *
     * @param poly polygon to construct the plane from
     */
    public Plane(Polygon poly) {
        Vec3d p1 = poly.getVertex(0).pos;
        Vec3d p2 = poly.getVertex(1).pos;
        Vec3d p3 = poly.getVertex(2).pos;

        Vec3d u = p2.sub(p1);
        Vec3d v = p3.sub(p1);

        Vec3d normal = Vec3d.normalized(u.cross(v));

        a = normal.c[0];
        b = normal.c[1];
        c = normal.c[2];
        d = normal.dot(p1);
    }

    /**
     * Construct a plane from triangle
     *
     * @param tri triangle to construct from
     */
    public Plane(Triangle tri) {
        Vec3d p1 = tri.a.pos;
        Vec3d p2 = tri.b.pos;
        Vec3d p3 = tri.c.pos;

        Vec3d u = p2.sub(p1);
        Vec3d v = p3.sub(p1);

        Vec3d normal = Vec3d.normalized(u.cross(v));
        normal.trim();

        a = normal.c[0];
        b = normal.c[1];
        c = normal.c[2];
        d = normal.dot(p1);
    }

    /**
     * Return the distance between point and a plane along the normal of the
     * plane.
     *
     * @param point point to classify
     * @return distance between the point and the plane along the plane normal
     */
    public float classifyPoint(Vec3d point) {
        return (a * point.c[0] + b * point.c[1] + c * point.c[2] - d);
    }

    /**
     * Does the plane contain the given point.
     *
     * @param point 3D point to test
     * @param epsilon The Epsilon.
     * @return true if contains , false if not
     */
    public boolean containsPoint(Vec3d point, float epsilon) {
        float dist = classifyPoint(point);
        if (dist < epsilon && dist > -epsilon) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Does the plane contain the given edge.
     *
     * @param point1 first end of the edge
     * @param point2 second end of the edge
     * @param epsilon The Epsilon.
     * @return true if contains , false if not
     */
    public boolean containsEdge(Edge edge, float epsilon) {
        if (containsPoint(edge.a.pos, epsilon) && containsPoint(edge.b.pos, epsilon)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the constant part from the plane equation
     *
     * @return the constant
     */
    public float getConstant() {
        return d;
    }

    /**
     * Return the plane normal
     *
     * @return the normal of the plane
     */
    public Vec3d getNormal() {
        return new Vec3d(a, b, c);
    }

    /**
     * Check which side a given point lies in respect to the plane
     *
     * @param point point to check
     * @param epsilon error-threshold to use
     * @return one of Plane.INFRONT, Plane.BEHIND or Plane.COINCIDAL
     */
    public int calculateSide(Vec3d point, float epsilon) {
        float side = classifyPoint(point);

        if (side > epsilon) {
            return INFRONT;
        } else if (side < -epsilon) {
            return BEHIND;
        } else {
            return COINCIDAL;
        }
    }

    /**
     * Check which side a given point lies in respect to the plane
     *
     * @param point point to check
     * @return one of Plane.INFRONT, Plane.BEHIND or Plane.COINCIDAL
     */
    public int calculateSide(Vec3d point) {
        float side = classifyPoint(point);

        if (side > 0) {
            return INFRONT;
        } else if (side < 0) {
            return BEHIND;
        } else {
            return COINCIDAL;
        }
    }

    /**
     * Check how a polygon is oriented in respect to the plane
     *
     * @param poly polygon to check
     * @param epsilon error-threshold to use
     * @return Plane.INFRONT, Plane.BEHIND, Plane.COINCIDAL or Plane.S
     */
    public int calculateSide(Polygon poly, float epsilon) {
        int numPositive = 0, numNegative = 0;

        for (int i = 0; i < poly.getNumPoints(); i++) {
            int side = calculateSide(poly.getVertex(i).pos, epsilon);

            if (side == BEHIND) {
                numNegative++;
            } else if (side == INFRONT) {
                numPositive++;
            }
        }

        if (numPositive > 0 && numNegative == 0) {
            return INFRONT;
        } else if (numPositive == 0 && numNegative > 0) {
            return BEHIND;
        } else if (numPositive == 0 && numNegative == 0) {
            return COINCIDAL;
        } else {
            return SPANNING;
        }
    }

    /**
     * Convert plane to String
     *
     * @return string representation of the plane
     */
    public String toString() {
        return new String("<" + a + ", " + b + ", " + c + ", " + d + ">");
    }

    /**
     * Read the plane from an ObjectInput stream.
     *
     * @param in stream to read from
     * @exception IOException if an error occurs
     */
    public void readExternal(ObjectInput in)
            throws IOException {
        a = in.readFloat();
        b = in.readFloat();
        c = in.readFloat();
        d = in.readFloat();
    }

    /**
     * Write the plane into an ObjectOutput stream.
     *
     * @param out stream to write into
     * @exception IOException if an error occurs
     */
    public void writeExternal(ObjectOutput out)
            throws IOException {
        out.writeFloat(a);
        out.writeFloat(b);
        out.writeFloat(c);
        out.writeFloat(d);
    }
}
