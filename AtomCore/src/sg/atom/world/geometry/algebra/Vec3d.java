package sg.atom.world.geometry.algebra;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import sg.atom.fx.tween.Interpolatable;

/**
 * 3D vector.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Vec3d implements Externalizable{//, Interpolatable {

    public float c[] = new float[3];

    /**
     * Read vector from ObjectInput stream.
     *
     * @param in stream to read from
     */
    public void readExternal(ObjectInput in) {
        try {
            c[0] = in.readFloat();
            c[1] = in.readFloat();
            c[2] = in.readFloat();
        } catch (Exception e) {
            System.out.println("Error de-externalizing Vec3d !");
        }
    }

    /**
     * Write vector to ObjectOutput stream.
     *
     * @param out stream to write into
     */
    public void writeExternal(ObjectOutput out) {
        try {
            out.writeFloat(c[0]);
            out.writeFloat(c[1]);
            out.writeFloat(c[2]);
        } catch (Exception e) {
            System.out.println("Error externalizing Vec3d !");
        }
    }

    /**
     * Default constructor
     *
     */
    public Vec3d() {
    }

    /**
     * Constructor
     *
     * @param s x,y and z component of vector
     */
    public Vec3d(float s) {
        c[0] = s;
        c[1] = s;
        c[2] = s;
    }

    /**
     * Constructor
     *
     * @param x x component of vector
     * @param y y component of vector
     * @param z z component of vector
     */
    public Vec3d(float x, float y, float z) {
        c[0] = x;
        c[1] = y;
        c[2] = z;
    }

    /**
     * Copy constructor
     *
     * @param v vector to copy
     */
    public Vec3d(Vec3d v) {
        c[0] = v.c[0];
        c[1] = v.c[1];
        c[2] = v.c[2];
    }

    /**
     * Checks if two vectors are equal.
     *
     * @param v vector to compare this to.
     * @return true if equal, false otherwise.
     */
    public boolean equals(Vec3d v) {
        if (c[0] == v.c[0]
                && c[1] == v.c[1]
                && c[2] == v.c[2]) {
            return true;
        }
        return false;
    }

    /**
     * Checks if two vectors are equal to given presicion.
     *
     * @param v vector to compare this to.
     * @param epsilon the presicion
     * @return true if equal, false otherwise.
     */
    public boolean equalsPresicion(Vec3d v, float epsilon) {
        if ((c[0] - v.c[0]) < epsilon && (c[0] - v.c[0]) > -epsilon
                && (c[1] - v.c[1]) < epsilon && (c[1] - v.c[1]) > -epsilon
                && (c[2] - v.c[2]) < epsilon && (c[2] - v.c[2]) > -epsilon) {
            return true;
        }
        return false;
    }

    /**
     * Add to vectors
     *
     * @param v vector two add
     * @return sum of vectors
     */
    public Vec3d add(Vec3d v) {
        return new Vec3d(c[0] + v.c[0], c[1] + v.c[1], c[2] + v.c[2]);
    }

    /**
     * Add vector to this vector
     *
     * @param v vector to add
     * @return this vector after addition
     */
    public Vec3d addTo(Vec3d v) {
        for (int i = 0; i < 3; i++) {
            c[i] += v.c[i];
        }

        return this;
    }

    /**
     * Subtract two vectors
     *
     * @param v vector to subtract
     * @return subtraction of vectors
     */
    public Vec3d sub(Vec3d v) {
        return new Vec3d(c[0] - v.c[0], c[1] - v.c[1], c[2] - v.c[2]);
    }

    /**
     * Subtract vector from this vector
     *
     * @param v vector to subtract
     * @return this vector after subtaction
     */
    public Vec3d subFrom(Vec3d v) {
        for (int i = 0; i < 3; i++) {
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
    public Vec3d scale(float s) {
        return new Vec3d(c[0] * s, c[1] * s, c[2] * s);
    }

    /**
     * Scale this vector
     *
     * @param s scalar to multiply with
     * @return this vector after scaling
     */
    public Vec3d scaleTo(float s) {
        for (int i = 0; i < 3; i++) {
            c[i] *= s;
        }

        return this;
    }

    /**
     * Compute the dot product of two 3d vectors
     *
     * @param v another vector
     * @return dot product
     */
    public float dot(Vec3d v) {
        return (c[0] * v.c[0] + c[1] * v.c[1] + c[2] * v.c[2]);
    }

    /**
     * Compute the squared length of vector
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
     * Normalize a vector
     *
     * @return this vector
     */
    public Vec3d normalize() {
        float invLen = len();
        if (invLen > 0) {
            invLen = 1 / invLen;
        }

        return scaleTo(invLen);
    }

    /**
     * Return a normalized copy of a vector
     *
     * @param v vector to normalize
     * @return normalized vector
     */
    public static Vec3d normalized(Vec3d v) {
        float invLen = v.len();
        if (invLen > 0) {
            invLen = 1 / invLen;
        }

        return new Vec3d(v.c[0] * invLen, v.c[1] * invLen, v.c[2] * invLen);
    }

    /**
     * Compute the cross product
     *
     * @param v vector
     * @return cross product
     */
    public Vec3d cross(Vec3d v) {
        return new Vec3d(c[1] * v.c[2] - c[2] * v.c[1],
                c[2] * v.c[0] - c[0] * v.c[2],
                c[0] * v.c[1] - c[1] * v.c[0]);
    }

    /**
     * Convert Vec3d to String
     *
     * @return Vec3d as String
     */
    public String toString() {
        return "<" + c[0] + ", " + c[1] + ", " + c[2] + ">";
    }

    /**
     * Return the index of the greatest component
     *
     * @return the greatest component
     */
    public int getGreatestComponent() {
        int i = 0;

        for (int j = 0; j < 3; j++) {
            if (Math.abs(c[j]) > Math.abs(c[i])) {
                i = j;
            }
        }

        return i;
    }

    /**
     * If any of the components are very close to zero, make them zero.
     *
     */
    public void trim() {
        if (Math.abs(c[0]) < 1e-5f) {
            c[0] = 0;
        }
        if (Math.abs(c[1]) < 1e-5f) {
            c[1] = 0;
        }
        if (Math.abs(c[2]) < 1e-5f) {
            c[2] = 0;
        }
    }

    /**
     * Clamp all components to range [0,1]
     *
     */
    public void clamp() {
        for (int i = 0; i < 3; i++) {
            if (c[i] < 0) {
                c[i] = 0;
            } else if (c[i] > 1) {
                c[i] = 1;
            }
        }
    }
//
//    /**
//     * addition in the Interpolatable interface
//     *
//     * @param v Vec3d Interpolatable
//     * @return the sum
//     */
//    public Interpolatable iadd(Interpolatable v) {
//        return add((Vec3d) v);
//    }
//
//    /**
//     * addition in the Interpolatable interface result stored in this object
//     *
//     * @param v Vec3d Interpolatable
//     * @return the sum
//     */
//    public Interpolatable iaddTo(Interpolatable v) {
//        return addTo((Vec3d) v);
//    }
//
//    /**
//     * subtraction in the Interpolatable interface
//     *
//     * @param v Vec3d Interpolatable
//     * @return v subtracted from this
//     */
//    public Interpolatable isub(Interpolatable v) {
//        return sub((Vec3d) v);
//    }
//
//    /**
//     * subtraction in the Interpolatable interface result stored in this object
//     *
//     * @param v Vec3d Interpolatable
//     * @return v subtracted from this
//     */
//    public Interpolatable isubFrom(Interpolatable v) {
//        return subFrom((Vec3d) v);
//    }
//
//    /**
//     * scaling in the Interpolatable interface
//     *
//     * @param s scale factor
//     * @return this scaled by s
//     */
//    public Interpolatable iscale(float s) {
//        return scale(s);
//    }
//
//    /**
//     * scaling in the Interpolatable interface result stored in this object
//     *
//     * @param s scale factor
//     * @return this scaled by s
//     */
//    public Interpolatable iscaleTo(float s) {
//        return scaleTo(s);
//    }
}
