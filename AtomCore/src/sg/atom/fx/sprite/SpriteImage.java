/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.sprite;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.jme3.asset.AssetKey;
import com.jme3.math.Vector2f;
import java.lang.reflect.Proxy;
import sg.atom.fx.anim.IAnimationState;
import sg.atom.utils.proxy.IPresenter;

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
public class SpriteImage implements IAnimationState<Sprite>, IPresenter<Sprite> {

    // The image path in SpriteSheet or in directory.
    public String imagePath;
    public String imageMode;
    // UV inside of a SpriteSheet. It can be similar to the sprite UV if the sprite only has one SpriteImage.
    public Vector2f uvTopLeft;
    public Vector2f uvSize;
    
    public AssetKey locate() {
        return new AssetKey();
    }
       
    @Override
    public Sprite from(Converter<Object, Sprite> convertor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Sprite from(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Proxy asProxy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sprite mix(Sprite... objects) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sprite as() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E extends Sprite> E as(Function<Sprite, E> convertor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E extends Sprite> E as(Class<E> clazz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
