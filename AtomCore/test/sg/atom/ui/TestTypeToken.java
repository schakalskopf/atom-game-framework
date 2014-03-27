/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.reflect.TypeToken;
import java.math.BigInteger;
import java.util.Map;
import org.junit.Test;
import sg.atom.ui.systems.GUISystemService;
import sg.atom.ui.services.nifty.NiftyGUIService;
import sg.atom.ui.services.tonegodgui.TonegodGUIService;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TestTypeToken {

    static <K, V> TypeToken<Map<K, V>> incorrectMapToken() {
        return new TypeToken<Map<K, V>>() {
        };
    }

    @Test
    public void testEquals() {
        TypeToken<Map<?, ?>> token1 = new TypeToken<Map<?, ?>>() {
        };
        TypeToken<Map<?, ?>> token2 = new TypeToken<Map<?, ?>>() {
        };
        assert token1.equals(token2);
    }

    @Test
    public void testTokenInMap() {
        BiMap<TypeToken, GUISystemService> biMap = HashBiMap.create();

        NiftyGUIService service1 = new NiftyGUIService(null);
        TonegodGUIService service2 = new TonegodGUIService();

        biMap.put(service1.getTypeToken(), service1);
        biMap.put(service2.getTypeToken(), service2);

        assert biMap.get(service1.getTypeToken()).equals(service1);
        assert biMap.get(service2.getTypeToken()).equals(service2);
    }

    public static void main(String[] args) {
        System.out.println(TestTypeToken.<String, BigInteger>incorrectMapToken());
        // just prints out "java.util.Map<K, V>"
    }
}
