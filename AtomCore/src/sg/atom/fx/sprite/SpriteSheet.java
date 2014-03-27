/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.sprite;

import com.google.common.base.Function;
import java.util.Collection;
import sg.atom.utils.factory.IAtomFactory;
import sg.atom.utils.repository.IRepository;

/**
 * A SpriteSheet is place to store and get the "original" Sprite, a common
 * approach in games programming to store Sprite.
 *
 * <p>SpriteSheet is a data structure which can store Sprites. A lot of
 * implementation imply SpriteSheet is a TextureAtlas where a Sprite also be a
 * sub-images of the atlas. In this implementation, SpriteSheet is consider a
 * Repository of Sprite where Sprite are "lookup" to access. Note that, this
 * SpriteSheet also do Cache and Pool the way AtomReposity did for generic
 * objects, but specialize for Texture and Sprite to gain performance. Maybe we
 * will decide to move to AtomRepository later.</p>
 *
 * <p>SpriteSheet can be transtraled from/to other format via as and from
 * methods:
 *
 * <ul> <li>(in-memory) TextureAtlas, Map of Sprite, List of Sprite, Table view
 * of Sprite;</li>
 *
 * <li> straight forward json, xml format are supported via xstream;</li>
 *
 * <li> JME's Savable are also supported. </li></ul>In Atom2D package, Tiled,
 * Cocos, Spriter, SpriteMapper and a few popular format are also supported.</p>
 *
 * @author CuongNguyen
 */
public class SpriteSheet implements IRepository<Object, Sprite> {

    @Override
    public Sprite get(Object key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static enum SpriteState {

        Original, Instanced
    }
    String fileName;

    @Override
    public IAtomFactory getFactory(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sprite query(Object... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Sprite> search(Object... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Sprite> getAllEntries() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void store(Object key, Sprite value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object as(Function<Sprite, Object> convertor) {
        return null;
    }

    public Object as(Class clazz) {
        return null;
    }

    public SpriteSheet from(Function<Object, SpriteSheet> convertor) {
        return null;
    }

    public SpriteSheet from(Object clazz) {
        return null;
    }
}
