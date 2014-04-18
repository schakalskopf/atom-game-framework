package sg.atom.world.geometry.texture;

/**
 * Texture class handles management of non-existant textures. This class
 * actually does nothing, and is meant to be used when the texture bank does not
 * allow actually loading textures.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 * @author <a href="mailto:tvlehton@cc.hut.fi">Tuukka Lehtonen</a>
 */
public class TextureInfoDummy extends TextureInfo {

    /**
     * Constructor with given options.
     *
     * @param filename filename of the image to load
     */
    TextureInfoDummy() {
    }

    /**
     * Send this texture to the GPU.
     *
     * @return always true
     */
    public boolean assign() {
        return true;
    }

    /**
     * DummSelect this texture for drawing
     *
     * @return always true
     */
    public boolean bind() {
        return true;
    }

    /**
     * Convert this texture to string
     *
     * @return "TextureInfoDummy"
     */
    public String toString() {
        return "TextureInfoDummy";
    }
}
