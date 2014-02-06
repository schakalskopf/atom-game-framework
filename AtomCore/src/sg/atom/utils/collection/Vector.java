package sg.atom.utils.collection;

import java.nio.FloatBuffer;

/**
 * Helper class for SubdivisionBatch to do vector math with any size on the
 * vectors
 *
 * @author Tobias (tobbe.a removethisoryourclientgoesape gmail.com)
 */
public class Vector {

    public float[] elem;
    public int size;

    public Vector(int size) {
        elem = new float[size];
        this.size = size;
        for (int i = 0; i < size; i++) {
            elem[i] = 0f;
        }
    }

    public Vector addLocal(Vector vec) {
        for (int i = 0; i < size; i++) {
            this.elem[i] += vec.elem[i];
        }
        return this;
    }

    public Vector multLocal(float factor) {
        for (int i = 0; i < size; i++) {
            this.elem[i] *= factor;
        }
        return this;
    }

    public Vector interpolate(Vector vec1, Vector vec2, float amount) {
        for (int i = 0; i < size; i++) {
            this.elem[i] = vec1.elem[i] * (1f - amount) + vec2.elem[i] * amount;
        }
        return this;
    }

    public Vector interpolate(Vector vec1, Vector vec2) {
        for (int i = 0; i < size; i++) {
            this.elem[i] = (vec1.elem[i] + vec2.elem[i]) * 0.5f;
        }
        return this;
    }

    public Vector populateFromBuffer(FloatBuffer buf, int index) {
        for (int i = 0; i < size; i++) {
            elem[i] = buf.get(index * size + i);
        }
        return this;
    }

    public FloatBuffer putInBuffer(FloatBuffer buf, int index) {
        for (int i = 0; i < size; i++) {
            buf.put(index * size + i, elem[i]);
        }
        return buf;

    }
}
