package sg.atom.utils;

import java.util.EnumSet;

/**
 * Replace with Enums.
 *
 * @author cuong.nguyenmanh2
 * @deprecated
 */
@Deprecated
public class EnumUtil {

    /**
     * error Enum.valueOf a String within enum values.
     *
     * @param name
     * @return
     */
    public static <T extends Enum<T>> T valueOf(Class<T> cls, final String name) {
        EnumSet<T> set = EnumSet.allOf(cls);
        for (final T e : set) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }

    /**
     * ToString() Enum entry
     *
     * @param name
     * @return
     */
    public static <T extends Enum<T>> T valueOfToString(Class<T> cls, final String name) {
        EnumSet<T> set = EnumSet.allOf(cls);
        for (final T e : set) {
            if (e.toString().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public static <T extends Enum<T>> T guess(Class<T> cls, final String name) {
        for (final T e : EnumSet.allOf(cls)) {
            if (name.indexOf(e.name()) >= 0) {
                return e;
            }
        }
        for (final T e : EnumSet.allOf(cls)) {
            if (name.indexOf(e.name().toLowerCase()) >= 0) {
                return e;
            }
        }
        return null;
    }
}
