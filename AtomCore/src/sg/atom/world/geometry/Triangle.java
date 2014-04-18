package sg.atom.world.geometry;

import sg.atom.world.geometry.algebra.Vec3d;
import java.io.*;

/**
 * Triangle is a polygon of three points.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Triangle {

    /**
     * Triangle vertices
     */
    public Vertex a, b, c;
    /**
     * Name of the texture used to map this triangle
     */
    private String texture1;
    /**
     * Name of the texture used to map this triangle
     */
    private String texture2;

    public Triangle() {
        a = new Vertex();
        b = new Vertex();
        c = new Vertex();
    }

    public Triangle(Vertex a, Vertex b, Vertex c, String texture) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.texture1 = texture;
        this.texture2 = texture;
    }

    public Triangle(Vertex a, Vertex b, Vertex c, String texture1, String texture2) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.texture1 = texture1;
        this.texture2 = texture2;
    }

    /**
     * Get the primary texture name.
     *
     * @return The primary texture name.
     */
    public String getPrimaryTextureName() {
        return texture1;
    }

    /**
     * Get the secondary texture name.
     *
     * @return The secondary texture name.
     */
    public String getSecondaryTextureName() {
        return texture2;
    }

    /**
     * Get the plane this triangle lies on.
     *
     * @return plane this triangle lies on
     */
    public Plane toPlane() {
        return new Plane(this);
    }

    /**
     * Compute the center of this triangle.
     *
     * @return a <code>Vec3d</code> value
     */
    public Vec3d center() {
        return (a.pos.add(b.pos.add(c.pos))).scale(1.0f / 3.0f);
    }

    /**
     * Get the area of this triangle.
     *
     * @return the area of this tringle
     */
    public float area() {
        Vec3d v1 = b.pos.sub(a.pos);
        Vec3d v2 = c.pos.sub(a.pos);
        return (v1.cross(v2)).len() / 2;
    }

    /**
     * Flip the vertex order
     *
     * @return Triangle with flipped vertex order
     */
    public Triangle flipped() {
        return new Triangle(c, b, a, texture1, texture2);
    }

    /**
     * Get the edge specified by index.
     *
     * @param index number of the edge to get
     * @return edge of a triangle
     */
    public Edge getEdge(int index) {
        if (index == 0) {
            return new Edge(a, b);
        } else if (index == 1) {
            return new Edge(b, c);
        } else {
            return new Edge(c, a);
        }
    }

    /**
     * Check if this triangle contains the given edge.
     *
     * @param edge edge to check
     * @return true, if this triangle contains the edge
     */
    public boolean contains(Edge edge) {
        if (edge.equals(getEdge(0))
                || edge.equals(getEdge(1))
                || edge.equals(getEdge(2))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean sameSide(Vec3d p1, Vec3d p2, Vec3d a, Vec3d b) {
        Vec3d cp1 = (b.sub(a)).cross(p1.sub(a));
        Vec3d cp2 = (b.sub(a)).cross(p2.sub(a));

        if (cp1.dot(cp2) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The string description of this Triangle.
     *
     * @return The string description of this Triangle.
     */
    public String toString() {
        return "" + a.pos + " " + b.pos + " " + c.pos;
    }

    /**
     * Check wether a given point is inside the triangle.
     *
     * @param point point to check
     * @return true if the point is inside
     */
    public boolean pointInside(Vec3d point) {
        if (sameSide(point, a.pos, b.pos, c.pos)
                && sameSide(point, b.pos, a.pos, c.pos)
                && sameSide(point, c.pos, a.pos, b.pos)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check wether a given point is inside the triangle.
     *
     * @param point point to check
     * @param epsilon error-threshold to use
     * @return true, if the point is inside
     */
    public boolean pointInside2(Vec3d point, float epsilon) {
        Vec3d tmp1 = (a.pos.sub(b.pos)).normalize().scale(epsilon);
        Vec3d tmp2 = (a.pos.sub(c.pos)).normalize().scale(epsilon);

        Vec3d A = a.pos.add(tmp1).add(tmp2);

        tmp1 = (b.pos.sub(a.pos)).normalize().scale(epsilon);
        tmp2 = (b.pos.sub(c.pos)).normalize().scale(epsilon);

        Vec3d B = b.pos.add(tmp1).add(tmp2);

        tmp1 = (c.pos.sub(a.pos)).normalize().scale(epsilon);
        tmp2 = (c.pos.sub(b.pos)).normalize().scale(epsilon);

        Vec3d C = c.pos.add(tmp1).add(tmp2);

        Triangle scaledTri = new Triangle(new Vertex(A), new Vertex(B), new Vertex(C), null);

        return scaledTri.pointInside(point);
    }

    /**
     * Get the distance between a point and a triangle.
     *
     * @param point point to measure to
     * @return distance between this triangle and point
     */
    public float distanceTo(Vec3d point) {
        Vec3d origin = a.pos;
        Vec3d edge1 = b.pos.sub(a.pos);
        Vec3d edge2 = c.pos.sub(a.pos);

        Vec3d diff = origin.sub(point);

        float A00 = edge1.lensq();
        float A01 = edge1.dot(edge2);
        float A11 = edge2.lensq();

        float B0 = diff.dot(edge1);
        float B1 = diff.dot(edge2);

        float C = diff.lensq();

        float det = Math.abs(A00 * A11 - A01 * A01);

        float s = A01 * B1 - A11 * B0;
        float t = A01 * B0 - A00 * B1;

        float sqDist;

        if (s + t <= det) {
            if (s < 0) {
                // Region 4
                if (t < 0) {
                    if (B0 < 0) {
                        t = 0;
                        if (-B0 >= A00) {
                            s = 1;
                            sqDist = A00 + 2 * B0 + C;
                        } else {
                            s = -B0 / A00;
                            sqDist = B0 * s + C;
                        }
                    } else {
                        s = 0;
                        if (B1 >= 0) {
                            t = 0;
                            sqDist = C;
                        } else if (-B1 >= A11) {
                            t = 1;
                            sqDist = A11 + 2 * B1 + C;
                        } else {
                            t = -B1 / A11;
                            sqDist = B1 * t + C;
                        }
                    }
                } // Region 3
                else {
                    s = 0;
                    if (B1 >= 0) {
                        t = 0;
                        sqDist = C;
                    } else if (-B1 >= A11) {
                        t = 1;
                        sqDist = A11 + 2 * B1 + C;
                    } else {
                        t = -B1 / A11;
                        sqDist = B1 * t + C;
                    }
                }
            } // Region 5
            else if (t < 0) {
                t = 0;
                if (B0 >= 0) {
                    s = 0;
                    sqDist = C;
                } else if (-B0 >= A00) {
                    s = 1;
                    sqDist = A00 + 2 * B0 + C;
                } else {
                    s = -B0 / A00;
                    sqDist = B0 * s + C;
                }
            } // Region 0
            else {
                float invDet = 1 / det;
                s *= invDet;
                t *= invDet;
                sqDist = s * (A00 * s + A01 * t + 2 * B0)
                        + t * (A01 * s + A11 * t + 2 * B1) + C;
            }
        } else {
            float tmp0, tmp1, numer, denom;

            // Region 2
            if (s < 0) {
                tmp0 = A01 + B0;
                tmp1 = A11 + B1;

                if (tmp1 > tmp0) {
                    numer = tmp1 - tmp0;
                    denom = A00 - 2 * A01 + A11;

                    if (numer >= denom) {
                        s = 1;
                        t = 1;
                        sqDist = A00 + 2 * B0 + C;
                    } else {
                        s = numer / denom;
                        t = 1 - s;
                        sqDist = s * (A00 * s + A01 * t + 2 * B0)
                                + t * (A01 + s + A11 * t + 2 * B1) + C;
                    }
                } else {
                    s = 0;

                    if (tmp1 <= 0) {
                        t = 1;
                        sqDist = A11 + 2 * B1 + C;
                    } else if (B1 >= 0) {
                        t = 0;
                        sqDist = C;
                    } else {
                        t = -B1 / A11;
                        sqDist = B1 * t + C;
                    }
                }
            } // Region 6
            else if (t < 0) {
                tmp0 = A01 + B1;
                tmp1 = A00 + B0;

                if (tmp1 > tmp0) {
                    numer = tmp1 - tmp0;
                    denom = A00 - 2 * A01 + A11;

                    if (numer >= denom) {
                        t = 1;
                        s = 0;
                        sqDist = A11 + 2 * B1 + C;
                    } else {
                        t = numer / denom;
                        s = 1 - t;
                        sqDist = s * (A00 * s + A01 * t + 2 * B0)
                                + t * (A01 * s + A11 * t + 2 * B1) + C;
                    }
                } else {
                    t = 0;
                    if (tmp1 <= 0) {
                        s = 1;
                        sqDist = A00 + 2 * B0 + C;
                    } else if (B0 >= 0) {
                        s = 0;
                        sqDist = C;
                    } else {
                        s = -B0 / A00;
                        sqDist = B0 * s + C;
                    }
                }
            } // Region 1
            else {
                numer = A11 + B1 - A01 - B0;

                if (numer <= 0) {
                    s = 0;
                    t = 1;
                    sqDist = A11 + 2 * B1 + C;
                } else {
                    denom = A00 - 2 * A01 + A11;

                    if (numer >= denom) {
                        s = 1;
                        t = 0;
                        sqDist = A00 + 2 * B0 + C;
                    } else {
                        s = numer / denom;
                        t = 1 - s;
                        sqDist = s * (A00 * s + A01 * t + 2 * B0)
                                + t * (A01 * s + A11 * t + 2 * B1) + C;
                    }
                }
            }
        }

        return (float) Math.sqrt(sqDist);
    }

    /**
     * Read a triangle from ObjectInput stream.
     *
     * @param in stream to read from
     * @exception IOException if an error occurs
     * @exception ClassNotFoundException if an error occurs
     */
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        a.readExternal(in);
        b.readExternal(in);
        c.readExternal(in);
    }

    /**
     * Write a triangle into ObjectOutput stream.
     *
     * @param out stream to write into
     * @exception IOException if an error occurs
     */
    public void writeExternal(ObjectOutput out)
            throws IOException {
        a.writeExternal(out);
        b.writeExternal(out);
        c.writeExternal(out);
    }

//    /**
//     * For testing.
//     *
//     * @param args ignored
//     */
//    public static void main(String[] args) {
//        Vertex A = new Vertex(new Vec3d(0, -1, 1));
//        Vertex B = new Vertex(new Vec3d(0, -1, -1));
//        Vertex C = new Vertex(new Vec3d(0, 1, 0));
//
//        Triangle tri = new Triangle(A, B, C, null);
//        Ray ray = new Ray(new Vec3d(-2, 0, 0), new Vec3d(-1, 0, 0));
//
//        System.out.println("Intersects: " + ray.intersects(tri));
//
//        System.out.println("Inside: " + tri.pointInside2(new Vec3d(0, 0.5f, 0.5f), 0));
//
//        System.out.println("Distance: " + tri.distanceTo(new Vec3d(0, -2, 0)));
//    }
}
