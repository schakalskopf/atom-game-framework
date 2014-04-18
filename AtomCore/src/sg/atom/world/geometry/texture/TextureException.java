package sg.atom.world.geometry.texture;

/**
 * Texture exception for signaling failed load attempts, invalid texture sizes,
 * and stuff.
 *
 * @author <a href="mailto:tvlehton@cc.hut.fi">Tuukka Lehtonen</a>
 */
public class TextureException extends Exception {

    public TextureException(String s) {
        super(s);
    }
}
