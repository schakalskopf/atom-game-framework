package sg.atom.utils._commons;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Replace with Common ArrayUtils
 *
 * @author CuongNguyen
 * @deprecated
 */
@Deprecated
public class ArrayUtils {

    public static double[] convert(float[] f) {
        double[] d = new double[f.length];
        for (int i = 0; i < d.length; i++) {
            d[i] = f[i];
        }
        return d;
    }

    public static float[] convert(double[] d) {
        float[] f = new float[d.length];
        for (int i = 0; i < f.length; i++) {
            f[i] = (float) d[i];
        }
        return f;
    }

    public static double[][] convert(float[][] f) {
        double[][] d = new double[f.length][];
        for (int i = 0; i < d.length; i++) {
            d[i] = convert(f[i]);
        }
        return d;
    }

    public static float[][] convert(double[][] d) {
        float[][] f = new float[d.length][];
        for (int i = 0; i < f.length; i++) {
            f[i] = convert(d[i]);
        }
        return f;
    }

    public static <T> T[] convert(Class<T> cls, Object[] src) {
        if (src == null) {
            return null;
        }
        T[] dest = (T[]) Array.newInstance(cls, src.length);
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    public static double[][] clone(double[][] src) {
        if (src == null) {
            return null;
        }
        double[][] dest = new double[src.length][];
        for (int i = 0; i < src.length; i++) {
            dest[i] = Arrays.copyOf(src[i], src[i].length);
        }
        return dest;
    }

    public static float[][] clone(float[][] src) {
        if (src == null) {
            return null;
        }
        float[][] dest = new float[src.length][];
        for (int i = 0; i < src.length; i++) {
            dest[i] = Arrays.copyOf(src[i], src[i].length);
        }
        return dest;
    }

    public static double[] clone(double[] src) {
        if (src == null) {
            return null;
        }
        return Arrays.copyOf(src, src.length);
    }

    public static float[] clone(float[] src) {
        if (src == null) {
            return null;
        }
        return Arrays.copyOf(src, src.length);
    }

    /**
     * 범위를 넘어서면 마지막 index를 반환한다.
     *
     * @param arr
     * @param key
     * @return
     * @author mulova
     */
    public static int binarySearch(double[] arr, double key) {
        int i = Arrays.binarySearch(arr, key);
        if (i < 0) {
            i = -i - 1;
            if (i >= arr.length) {
                i = arr.length - 1;
            } else if (i <= 0) {
                i = 0;
            }
        }
        return i;
    }

    /**
     * 범위를 넘어서면 마지막 index를 반환한다.
     *
     * @param arr
     * @param key
     * @return
     * @author mulova
     */
    public static int binarySearch(float[] arr, float key) {
        int i = Arrays.binarySearch(arr, key);
        if (i < 0) {
            i = -i - 1;
            if (i >= arr.length) {
                i = arr.length - 1;
            } else if (i <= 0) {
                i = 0;
            }
        }
        return i;
    }

    /**
     * 범위를 넘어서면 마지막 index를 반환한다.
     *
     * @param arr
     * @param key
     * @return
     * @author mulova
     */
    public static int binarySearch(int[] arr, int key) {
        int i = Arrays.binarySearch(arr, key);
        if (i < 0) {
            i = -i - 1;
            if (i >= arr.length) {
                i = arr.length - 1;
            } else if (i <= 0) {
                i = 0;
            }
        }
        return i;
    }

    public static <T extends Enum<T>> T[] getValuesFrom1(Class<T> cls) {
        T[] values = cls.getEnumConstants();
        return Arrays.copyOfRange(values, 1, values.length);
    }

    public static void shift(float[] arr, int shift) {
        if (shift > 0) {
            shift = Math.min(shift, arr.length);
            for (int i = arr.length - 1 - shift; i >= 0; i--) {
                arr[i + shift] = arr[i];
            }
            for (int i = 0; i < shift; i++) {
                arr[i] = 0;
            }
        } else {
            shift = Math.max(shift, -arr.length);
            for (int i = 0; i < arr.length + shift; i++) {
                arr[i] = arr[i - shift];
            }
            for (int i = arr.length + shift; i < arr.length; i++) {
                arr[i] = 0;
            }
        }
    }

    public static float[] linearize(float[][] table) {
        int length = 0;
        for (int i = 0; i < table.length; i++) {
            length += table[i].length;
        }
        float[] arr = new float[length];
        int index = 0;
        for (int i = 0; i < table.length; i++) {
            System.arraycopy(table[i], 0, arr, index, table[i].length);
            index += table[i].length;
        }
        return arr;
    }
}
