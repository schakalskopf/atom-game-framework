/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.material;

import com.jme3.texture.Texture;
import java.util.Properties;

/**
 * Texture description for common usage. Especially when using import/export
 * from CCD Program. Can be translated to Texture definition and virce vesa
 *
 * @author cuong.nguyenmanh2
 */
public class TextureDescription {

    public String path;
    public String type;
    public float scale;
    public Texture original;

    public Properties asProperties() {
        Properties result = new Properties();
        result.setProperty("Scale", Float.toString(scale));
        return result;
    }
}
