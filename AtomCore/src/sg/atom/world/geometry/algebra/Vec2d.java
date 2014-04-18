package sg.atom.world.geometry.algebra;

import java.io.*;

/**
 * Class Vec2d
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Vec2d implements Externalizable {

    public float c[] = new float[2];

    /**
     * Read vector from ObjectInput stream.
     *
     * @param in stream to read from
     */
    @Override
    public void readExternal(ObjectInput in) {
        try {
            c[0] = in.readFloat();
            c[1] = in.readFloat();
        } catch (Exception e) {
            System.out.println("Error de-externalizing Vec2d !");
        }
    }

    /**
     * Write vector into ObjectOutput stream.
     *
     * @param out an <code>ObjectOutput</code> value
     */
    public void writeExternal(ObjectOutput out) {
        try {
            out.writeFloat(c[0]);
            out.writeFloat(c[1]);
        } catch (Exception e) {
            System.out.println("Error externalizing Vec2d !");
        }
    }

    /**
     * Default constructor
     *
     */
    public Vec2d() {
    }

    /**
     * Constructor
     *
     * @param s x and y component of vector
     */
    public Vec2d(float s) {
        c[0] = s;
        c[1] = s;
    }

    /**
     * Constructor
     *
     * @param x x component of vector
     * @param y y component of vector
     */
    public Vec2d(float x, float y) {
        c[0] = x;
        c[1] = y;
    }

    /**
     * Copy constructor
     *
     * @param v vector to copy
     */
    public Vec2d(Vec2d v) {
        c[0] = v.c[0];
        c[1] = v.c[1];
    }

    /**
     * Checks if two vectors are equal.
     *
     * @param v vector to compare this to.
     * @return true if equal, false otherwise.
     */
    public boolean equals(Vec2d v) {
        if (c[0] == v.c[0]
                && c[1] == v.c[1]) {
            return true;
        }
        return false;
    }

    /**
     * Add two 2d vectors together
     *
     * @param v vector to add
     * @return sum of vectors
     */
    public Vec2d add(Vec2d v) {
        return new Vec2d(c[0] + v.c[0], c[1] + v.c[1]);
    }

    /**
     * Add vector to this vector
     *
     * @param v vector to add
     * @return this vector after addition
     */
    public Vec2d addTo(Vec2d v) {
        for (int i = 0; i < 2; i++) {
            c[i] += v.c[i];
        }

        return this;
    }

    /**
     * Subtract two vectors
     *
     * @param v vector two subtract
     * @return result of the subraction
     */
    public Vec2d sub(Vec2d v) {
        return new Vec2d(c[0] - v.c[0], c[1] - v.c[1]);
    }

    /**
     * Subtract vector from this vector
     *
     * @param v vector to subtract
     * @return this vector after subtaction
     */
    public Vec2d subFrom(Vec2d v) {
        for (int i = 0; i < 2; i++) {
            c[i] -= v.c[i];
        }

        return this;
    }

    /**
     * Multiply by scalar
     *
     * @param s scalar to multiply with
     * @return result of scaling
     */
    public Vec2d scale(float s) {
        return new Vec2d(c[0] * s, c[1] * s);
    }

    /**
     * Scale this vector
     *
     * @param s scalar to multiply with
     * @return this vector after scaling
     */
    public Vec2d scaleTo(float s) {
        for (int i = 0; i < 2; i++) {
            c[i] *= s;
        }

        return this;
    }

    /**
     * Compute the dot product of two vectors
     *
     * @param v second vector
     * @return the dot product
     */
    public float dot(Vec2d v) {
        return (c[0] * v.c[0] + c[1] * v.c[1]);
    }

    /**
     * Compute the squared length of the vector
     *
     * @return squared length
     */
    public float lensq() {
        return this.dot(this);
    }

    /**
     * Get the length of a vector
     *
     * @return vector length
     */
    public float len() {
        return (float) Math.sqrt(lensq());
    }

    /**
     * Return a normalized copy of a vector
     *
     * @param v vector to normalize
     * @return normalized vector
     */
    public static Vec2d normalized(Vec2d v) {
        float invLen = v.len();
        if (invLen > 0) {
            invLen = 1 / invLen;
        }

        return new Vec2d(v.c[0] * invLen, v.c[1] * invLen);
    }

    /**
     * Normalize a vector
     *
     * @return this vector
     */
    public Vec2d normalize() {
        float invLen = len();
        if (invLen > 0) {
            invLen = 1 / invLen;
        }

        return scaleTo(invLen);
    }

    /**
     * Convert Vec2d to String
     *
     * @return Vec2d as String
     */
    public String toString() {
        return "<" + c[0] + "," + c[1] + ">";
    }
}
