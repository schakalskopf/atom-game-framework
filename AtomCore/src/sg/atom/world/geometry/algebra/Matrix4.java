package sg.atom.world.geometry.algebra;

/**
 * 4x4 matrix Contains C++ code from Hannu Niemist� ported to Java.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Matrix4 {

    public float c[] = new float[16];

    /**
     * Default constructor
     *
     */
    public Matrix4() {
    }

    /**
     * Copy constructor
     *
     * @param copy the matrix to copy
     */
    public Matrix4(Matrix4 copy) {
        c = new float[16];
        for (int i = 0; i < 16; i++) {
            c[i] = copy.c[i];
        }
    }

    /**
     * Constructor
     *
     * @param diag diagonal element to use
     */
    public Matrix4(float diag) {
        c[0] = c[5] = c[10] = c[15] = diag;
    }

    /**
     * Constructor
     *
     * @param array[] array to construct the matrix from
     */
    public Matrix4(float array[]) {
        for (int i = 0; i < 16; i++) {
            c[i] = array[i];
        }
    }

    /**
     * Constructor
     *
     * @param array[][] 2d array to construct the matrix from
     */
    public Matrix4(float array[][]) {
        for (int h = 0, i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++, h++) {
                c[h] = array[i][j];
            }
        }
    }

    /**
     * Add two matrices together
     *
     * @param m another matrix
     * @return sum of matrices
     */
    public Matrix4 add(Matrix4 m) {
        Matrix4 result = new Matrix4();

        for (int i = 0; i < 16; i++) {
            result.c[i] = this.c[i] + m.c[i];
        }

        return result;
    }

    /**
     * Subtract two matrices
     *
     * @param m another matrix
     * @return subtraction of matrices
     */
    public Matrix4 sub(Matrix4 m) {
        Matrix4 result = new Matrix4();

        for (int i = 0; i < 16; i++) {
            result.c[i] = this.c[i] - m.c[i];
        }

        return result;
    }

    /**
     * Multiply matrix by float
     *
     * @param s the float to multiply the matrix with
     * @return scaled matrix
     */
    public Matrix4 mul(float s) {
        Matrix4 result = new Matrix4();

        for (int i = 0; i < 16; i++) {
            result.c[i] = this.c[i] * s;
        }

        return result;
    }

    /**
     * Multiply two matrices together
     *
     * @param m another matrix
     * @return product of the matrices
     */
    public Matrix4 mul(Matrix4 m) {
        Matrix4 result = new Matrix4();

        result.c[0] = c[0] * m.c[0] + c[1] * m.c[4] + c[2] * m.c[8] + c[3] * m.c[12];
        result.c[4] = c[4] * m.c[0] + c[5] * m.c[4] + c[6] * m.c[8] + c[7] * m.c[12];
        result.c[8] = c[8] * m.c[0] + c[9] * m.c[4] + c[10] * m.c[8] + c[11] * m.c[12];
        result.c[12] = c[12] * m.c[0] + c[13] * m.c[4] + c[14] * m.c[8] + c[15] * m.c[12];

        result.c[1] = c[0] * m.c[1] + c[1] * m.c[5] + c[2] * m.c[9] + c[3] * m.c[13];
        result.c[5] = c[4] * m.c[1] + c[5] * m.c[5] + c[6] * m.c[9] + c[7] * m.c[13];
        result.c[9] = c[8] * m.c[1] + c[9] * m.c[5] + c[10] * m.c[9] + c[11] * m.c[13];
        result.c[13] = c[12] * m.c[1] + c[13] * m.c[5] + c[14] * m.c[9] + c[15] * m.c[13];

        result.c[2] = c[0] * m.c[2] + c[1] * m.c[6] + c[2] * m.c[10] + c[3] * m.c[14];
        result.c[6] = c[4] * m.c[2] + c[5] * m.c[6] + c[6] * m.c[10] + c[7] * m.c[14];
        result.c[10] = c[8] * m.c[2] + c[9] * m.c[6] + c[10] * m.c[10] + c[11] * m.c[14];
        result.c[14] = c[12] * m.c[2] + c[13] * m.c[6] + c[14] * m.c[10] + c[15] * m.c[14];

        result.c[3] = c[0] * m.c[3] + c[1] * m.c[7] + c[2] * m.c[11] + c[3] * m.c[15];
        result.c[7] = c[4] * m.c[3] + c[5] * m.c[7] + c[6] * m.c[11] + c[7] * m.c[15];
        result.c[11] = c[8] * m.c[3] + c[9] * m.c[7] + c[10] * m.c[11] + c[11] * m.c[15];
        result.c[15] = c[12] * m.c[3] + c[13] * m.c[7] + c[14] * m.c[11] + c[15] * m.c[15];

        return result;
    }

    /**
     * Get the transpose of the matrix
     *
     * @return the transpose matrix
     */
    public Matrix4 transpose() {
        Matrix4 result = new Matrix4();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.c[4 * j + i] = this.c[4 * i + j];
            }
        }

        return result;
    }

    /**
     * Puts this matrix into the given array
     *
     * @return an 1d array containing this matrix.
     */
    float[] arrayform() {
        float[] result = new float[4 * 4];

        for (int i = 0; i < 16; i++) {
            result[i] = this.c[i];
        }

        return result;
    }

    /**
     * Compute the determinant of the matrix
     *
     * @return the determinant
     */
    public float det() {
        float det =
                (c[0] * c[5] - c[1] * c[4]) * (c[10] * c[15] - c[11] * c[14])
                - (c[0] * c[6] - c[2] * c[4]) * (c[9] * c[15] - c[11] * c[13])
                + (c[0] * c[7] - c[3] * c[4]) * (c[9] * c[14] - c[10] * c[13])
                + (c[1] * c[6] - c[2] * c[5]) * (c[8] * c[15] - c[11] * c[12])
                - (c[1] * c[7] - c[3] * c[5]) * (c[8] * c[14] - c[10] * c[12])
                + (c[2] * c[7] - c[3] * c[6]) * (c[8] * c[13] - c[9] * c[12]);

        return det;
    }

    /**
     * Convert matrix to string
     *     
* @return the string representation
     */
    public String toString() {
        String result = "";

        result += "|" + c[0] + " " + c[1] + " " + c[2] + " " + c[3] + "|\n";
        result += "|" + c[4] + " " + c[5] + " " + c[6] + " " + c[7] + "|\n";
        result += "|" + c[8] + " " + c[9] + " " + c[10] + " " + c[11] + "|\n";
        result += "|" + c[12] + " " + c[13] + " " + c[14] + " " + c[15] + "|\n";

        return result;
    }

//    /**
//     * Method to test matrix code
//     *
//     */
//    public static void main(String[] args) {
//        float[][] identity = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
//        float[][] stuff = {{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 16}};
//
//        Matrix4 a = new Matrix4(identity);
//        Matrix4 b = new Matrix4(stuff);
//
//        System.out.println("Mat A:\n" + a.toString());
//        System.out.println("Mat B:\n" + b.toString());
//
//        System.out.println("Det A: " + a.det());
//        System.out.println("Det B: " + b.det());
//
//        System.out.println("AxB:\n" + a.mul(b).toString());
//        System.out.println("BxA:\n" + b.mul(a).toString());
//
//    }
}
