package sg.atom.world.geometry.algebra;

/**
 * 3x3 Matrix Contains C++ code from Hannu Niemist� ported to Java.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Matrix3 {

    public float c[][] = new float[3][3];

    /**
     * Default constructor
     *
     */
    public Matrix3() {
    }

    /**
     * Constructor
     *
     * @param diag diagonal element to use
     */
    public Matrix3(float diag) {
        for (int i = 0; i < 3; i++) {
            c[i][i] = diag;
        }
    }

    /**
     * Constructor
     *
     * @param array[][] a 3x3 array to generate the matrix from
     */
    public Matrix3(float[][] array) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.c[i][j] = array[i][j];
            }
        }
    }

    /**
     * Add two matrices together
     *
     * @param m another matrix
     * @return sum matrix
     */
    public Matrix3 sum(Matrix3 m) {
        Matrix3 result = new Matrix3();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.c[i][j] = this.c[i][j] + m.c[i][j];
            }
        }

        return result;
    }

    /**
     * Subtract two matrices
     *
     * @param m another matrix
     * @return subtracted matrix
     */
    public Matrix3 sub(Matrix3 m) {
        Matrix3 result = new Matrix3();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.c[i][j] = this.c[i][j] - m.c[i][j];
            }
        }

        return result;
    }

    /**
     * Multiply by scalar
     *
     * @param s scalar
     * @return scaled matrix
     */
    public Matrix3 mul(float s) {
        Matrix3 result = new Matrix3();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.c[i][j] = this.c[i][j] * s;
            }
        }

        return result;
    }

    /**
     * Multiply by vector from right
     *
     * @param m multiplier vector
     * @return result vector of multiplication
     */
    public Vec3d mul(Vec3d v) {
        Vec3d result = new Vec3d();

        for (int i = 0; i < 3; i++) {
            result.c[i] = (this.c[i][0] * v.c[0]
                    + this.c[i][1] * v.c[1]
                    + this.c[i][2] * v.c[2]);
        }

        return result;
    }

    /**
     * Multiply two 3x3 matrices
     *
     * @param m another matrix
     * @return result of multiplication
     */
    public Matrix3 mul(Matrix3 m) {
        Matrix3 result = new Matrix3();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // FIXME: turha? alustaako new muistin?
                result.c[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    result.c[i][j] += this.c[i][k] * m.c[k][j];
                }
            }
        }

        return result;
    }

    /**
     * Transpose matrix
     *
     * @return scaled matrix
     */
    public Matrix3 transpose() {
        Matrix3 result = new Matrix3();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.c[i][j] = this.c[j][i];
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
        float[] result = new float[3 * 3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[3 * i + j] = this.c[i][j];
            }
        }

        return result;
    }

    /**
     * Calculate the determinant of 3x3 matrix
     *
     * @return the determinant
     */
    public float det() {
        float det =
                c[0][0] * (c[1][1] * c[2][2] - c[1][2] * c[2][1])
                - c[0][1] * (c[1][0] * c[2][2] - c[1][2] * c[2][0])
                + c[0][2] * (c[1][0] * c[2][1] - c[1][1] * c[2][0]);

        return det;
    }

    /**
     * Calculate the inverse of 3x3 matrix
     *
     * @param the matrix to invert
     * @return the inverse
     */
    public static Matrix3 inverse(Matrix3 m) {
        int i, j, k, best;
        float best_scale, scale, swap_temp;

        // Set inverse to identity

        Matrix3 inv = new Matrix3(1);

        // Gauss-Jordan

        for (j = 0; j < 3; j++) {

            // Pivoting

            for (i = j + 1, best = j, best_scale = Math.abs(m.c[best][j]); i < 3; i++) {
                if (Math.abs(m.c[i][j]) > best_scale) {
                    best = i;
                    best_scale = Math.abs(m.c[i][j]);
                }
            }

            if (best_scale == 0) // Matrix is singular
            {
                return null;
            }

            scale = 1.0f / m.c[best][j];

            if (best != j) {
                for (i = j + 1; i < 3; i++) {
                    swap_temp = m.c[j][i];
                    m.c[j][i] = m.c[best][i] * scale;
                    m.c[best][i] = swap_temp;
                }
                for (i = 0; i < 3; i++) {
                    swap_temp = inv.c[j][i];
                    inv.c[j][i] = inv.c[best][i] * scale;
                    inv.c[best][i] = swap_temp;
                }
                m.c[best][j] = m.c[j][j];
            } else {
                for (i = j + 1; i < 3; i++) {
                    m.c[j][i] *= scale;
                }
                for (i = 0; i < 3; i++) {
                    inv.c[j][i] *= scale;
                }
            }

            // Substracting

            for (i = 0; i < j; i++) {
                scale = m.c[i][j];
                for (k = j + 1; k < 3; k++) {
                    m.c[i][k] -= scale * m.c[j][k];
                }
                for (k = 0; k < 3; k++) {
                    inv.c[i][k] -= scale * inv.c[j][k];
                }
            }

            for (i = j + 1; i < 3; i++) {
                scale = m.c[i][j];
                for (k = j + 1; k < 3; k++) {
                    m.c[i][k] -= scale * m.c[j][k];
                }
                for (k = 0; k < 3; k++) {
                    inv.c[i][k] -= scale * inv.c[j][k];
                }
            }
        }
        return inv;
    }

    /**
     * Convert matrix to string
     *
     * @return matrix as string
     */
    public String toString() {
        String result = "";

        result += "|" + c[0][0] + " " + c[1][0] + " " + c[2][0] + "|\n";
        result += "|" + c[0][1] + " " + c[1][1] + " " + c[2][1] + "|\n";
        result += "|" + c[0][2] + " " + c[1][2] + " " + c[2][2] + "|\n";

        return result;
    }

//    /**
//     * Method to test the matrix code.
//     */
//    public static void main(String[] args) {
//        float[][] identity = {{1, 0, 0}, {0, 1, 0}, {1, 1, 1}};
//        float[][] stuff = {{1, 4, 7}, {2, 5, 8}, {3, 7, 9}};
//
//        Matrix3 a = new Matrix3(identity);
//        Matrix3 b = new Matrix3(stuff);
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
//        System.out.println("A-1:\n" + Matrix3.inverse(a));
//        System.out.println("B-1:\n" + Matrix3.inverse(b));
//    }
}
