package sg.atom.logic.input;

import java.util.Arrays;
import sg.atom.utils.datastructure.collection.IntArray;

/**
 * @deprecated use Guava packing method instead.
 * @author CuongNguyen
 */
@Deprecated
public final class KeyPacker {

    private static final int DATA_BYTES = Long.SIZE / 8;
    private static IntArray unpacked = new IntArray(DATA_BYTES);

    private KeyPacker() {
    }

    public static long pack(int[] keys) {
        if (keys.length > (DATA_BYTES - 1)) // 8th byte reserved for mouse/touch
        {
            throw new IllegalArgumentException("Maximum length of key array is 7, found " + keys.length);
        }

        Arrays.sort(keys);

        long packed = 0;
        for (int i = 0; keys.length > i; i++) {
            assert keys[i] == (keys[i] & 0xff) : "unexpected value " + keys[i];
            packed |= keys[i] << (8 * i);
        }

        return packed;
    }

    public static int[] unpack(long keys) {
        unpacked.clear();
        for (int i = 0; DATA_BYTES > i; i++) {
            int keycode = (int) (keys >> (8 * i)) & 0xff;
            if (keycode > 0) {
                unpacked.add(keycode);
            } else {
                break;
            }
        }

        return unpacked.toArray();
    }
}
