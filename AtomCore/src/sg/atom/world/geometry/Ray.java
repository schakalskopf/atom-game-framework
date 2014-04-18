package sg.atom.world.geometry;

import sg.atom.world.geometry.algebra.Vec3d;
import java.io.*;

/**
 * A line segment in 3D space.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Ray {

    public static float EPSILON = 1e-3f;
    public Vec3d start;
    public Vec3d end;
    public Vec3d dir;

    /**
     * Create a ray between two points
     *
     * @param point1 start point
     * @param point2 end point
     */
    public Ray(Vec3d point1, Vec3d point2) {
        start = new Vec3d(point1);
        end = new Vec3d(point2);
        dir = point2.sub(point1);
    }

    /**
     * Extend this ray forward the given amount.
     *
     * @param value The amount to extend.
     */
    public void forwardExtend(float value) {
        end = end.add(dir.scale(value));
        dir = dir.scale(1 + value);
    }

    /**
     * Extend this ray backward the given amount.
     *
     * @param value The amount to extend.
     */
    public void backwardExtend(float value) {
        start = start.add(dir.scale(-value));
        dir = dir.scale(1 + value);
    }

    /**
     * Extend this ray both directions the given amount.
     *
     * @param value The amount to extend.
     */
    public void extend(float value) {
        start = start.add(dir.scale(-value));
        end = end.add(dir.scale(value));
        dir = dir.scale(1 + 2 * value);
    }

    /**
     * The string description of this Ray.
     *
     * @return The string description of this Ray.
     */
    public String toString() {
        return new String(start + " -> " + end + " : " + dir);
    }

    /**
     * Computes a distance from the given point to this Ray.
     *
     * @param point The given point.
     * @return The distance
     */
    public float distanceTo(Vec3d point) {
        float length = end.sub(start).len();
        Vec3d v = point.sub(start);
        Vec3d dirn = Vec3d.normalized(dir);
        float value = v.dot(dirn);
        if (value > length) {
            return point.sub(end).len();
        } else if (value < 0) {
            return point.sub(start).len();
        } else {
            Vec3d pos = start.add(dirn.scale(value));
            return point.sub(pos).len();
        }
    }

    /**
     * Test whether a ray intersects a triangle.
     *
     * The algorithm used:
     *
     * Point T(u,v) on a triangle is given by
     *
     * T(u,v)=(1-u-v)A+uB+vC
     *
     * where (u,v) are barycentric coordinates which must fullfill u >= 0, v >=
     * 0 and u+v<=1. Computing intersection between ray, R(t), and triangle,
     * T(u,v), is equivivalent to R(t)=T(u,v).
     *

     *
     * @param tri triangle to test
     * @return true, if intersection occurs
     */
    public boolean intersects(Triangle tri) {
        Vec3d edge1 = tri.b.pos.sub(tri.a.pos);
        Vec3d edge2 = tri.c.pos.sub(tri.a.pos);

        /* Begin calculating determinant */
        Vec3d pvec = dir.cross(edge2);

        /* If determinant is near zero, ray lies in plane of triangle */
        float det = edge1.dot(pvec);

        if (det > -EPSILON && det < EPSILON) {
            return false;
        }

        float invDet = 1 / det;

        // Calculate distance from vert a to ray origin
        Vec3d tvec = start.sub(tri.a.pos);

        // Calculate u parameter and test bounds
        float u = tvec.dot(pvec) * invDet;
        if (u < 0 || u > 1) {
            return false;
        }

        // Prepare to test v
        Vec3d qvec = tvec.cross(edge1);

        // Calculate v and test bounds
        float v = dir.dot(qvec) * invDet;
        if (v < 0 || u + v > 1) {
            return false;
        }

        float t = edge2.dot(qvec) * invDet;

        if (t < 0 || t > 1.0) {
            return false;
        }

        return true;
    }
}
