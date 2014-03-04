/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.sprite;

import com.jme3.asset.AssetKey;
import com.jme3.math.Vector2f;
import sg.atom.fx.anim.IAnimationState;

/**
 * SpriteImage is a representor of Sprite with its animation in a specific of
 * time and context. Consider Sprite and SpriteImage is the two main model of
 * this Sprite framework (other implementations are collapsed them), they are
 * exchangable but in storing manner and displaying manner. As MVC enthusiast,
 * Sprite and SpriteImage can be seen as Model and View-Model next to each
 * other.
 *
 * <p>In this implementation, SpriteImage infos can be set and get in various
 * format ( beside of even use as a Sprite!):
 *
 * <ul> <li>A single JME's Texture or a Texture's Cell in TextureAtlas. The path
 * can be String or AssetKey.</li>
 *
 * <li>An AssetKey to the locate the original Sprite in the SpriteSheet.</li>
 *
 * <li>UV (Vector2f) to create new SpriteImage out of a SpriteSheet.</li>
 *
 * <li>A single frame Sprite consist on this specific SpriteImage as
 * initialImage.</li> </ul> </p>
 *
 * <p>
 *
 * </p>
 *
 * <p></p>
 *
 * @author CuongNguyen
 */
public class SpriteImage implements IAnimationState<Sprite> {

    // The image path in SpriteSheet or in directory.
    public String imagePath;
    public String imageMode;
    // UV inside of a SpriteSheet. It can be similar to the sprite UV if the sprite only has one SpriteImage.
    public Vector2f uvTopLeft;
    public Vector2f uvSize;

    public AssetKey locate() {
        return new AssetKey();
    }
}
