/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.sprite;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Map;
import sg.atom.core.config.IConfigurable;
import sg.atom.utils.proxy.IPresenter;

/**
 * Sprite is a flexible graphics model can be used in a wide range of games and
 * applications. Sprite beside of Spatial is a good candidate for
 * cross-dimentional model in Atom framework. It was moved from Atom2D!
 *
 * <p>A Sprite can be a single frame or multi/animated frames. Each frame
 * contain a SpriteImage as its representor. It also be called "original" and
 * "instanced" accordingly to the context; as if it's storing in spritesheet or
 * displaying/manipulating outside of spritesheet. An "Original" single sprite
 * can be translated into a SpriteImage! Usages may also require translating an
 * "Instanced" Sprite to SpriteImage but this is not recommended.</p>
 *
 * <p>This implementation wrap 2d and 3d sprite in one united model and support
 * useful functions. It also provide the most common form of Sprite usage in
 * JME3 enviroment, as UV/Mesh/Geometry/Node. The positional infos
 * (transforming, translating, ordering ..) are represented as float. A local
 * Transform also stored and sync with those attributes to help use in JME's
 * context easier. Through Transform, this Sprite implementation also support
 * scaling and rotating. </p>
 *
 * <p> If use as Spatial (Node,Geometry) the Spatial is associate with custom
 * user data under name "__sprite_{attribute}". Through Spatial, Sprite can be
 * nested as an element of the scenegraph. If as in Mesh/Quad level, the vertex
 * indexes/uv/position can be retransformed. In Material/Shader level, Texture
 * or TextureCell are pushed down to JME3 system.</p>
 *
 * <p> An internal SpriteControl are in charge to various "View" mataining for
 * Spatial accoringly if changes are made in the model. Interaction and event
 * propagation are also be handled in SpriteControl. </p>
 *
 * <p><b>Custom usage:</b> <ul><li>Sprite in UI for example can composed
 * attributes in various format, such as Nifty's GUI Sprite-ImageMode.. Also the
 * as(Class) method and as(Convertor) imply that this model can be convert into
 * various different formats, indeed. </li>
 *
 * <li>Sprite in external Sprite editor program can be translated/loaded to this
 * model via AssetKey and AssetLoader. </li></ul>
 *
 * @author CuongNguyen
 */
public class Sprite implements IPresenter<Spatial>,IConfigurable {

    public static enum SpriteStatus {

        STATUS_ORIGINAL, STATUS_INSTANCED, STATUS_UNLINK
    }
    
    public static enum SpriteDisplay{
        
    }
    
    public static enum SpriteStyle{
        
    }
    // Four float positional attributes for common usecases
    public float x, y, width, height;
    // Another float for ordering purpose
    public float order;
    public Transform localTransform;
    public SpriteImage initialImage;
    public ArrayList<SpriteAnimation> animations;
    private Material material;
    private Texture spritesheet;
    private AssetManager assetManager;
    private SpriteStatus status;

    public Sprite(AssetManager assetManager, float x, float y, float width, float height) {
        this.assetManager = assetManager;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setAnimation(SpriteAnimation anim) {
    }

    public void setSpriteImage(SpriteImage image) {
    }

    public Vector2f asUV() {
        return null;
    }

    public Vector3f asUV3() {
        return null;
    }

    public Mesh asMesh() {
        return new Quad(width, height);
    }

    public void setColor(float r, float g, float b, float a) {
        material.setColor("Color", new ColorRGBA(r, g, b, a));
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        if (material == null) {
            material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            material.setTexture("ColorMap", spritesheet);
        }
        return material;
    }

    public Geometry asGeometry() {
        Mesh quad = asMesh();
        Geometry geometry = new Geometry("sprite", quad);

        geometry.setMaterial(material);
        return geometry;
    }

    public Node asNode() {
        return null;
    }
    
    @Override
    public void config(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getConfig(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T getConfig(String key, Class<T> clazz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, Object> getConfigs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Spatial from(Converter<Object, Spatial> convertor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Spatial from(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E extends Spatial> E as(Function<Spatial, E> convertor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E extends Spatial> E as(Class<E> clazz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Proxy asProxy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Spatial mix(Spatial... objects) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Spatial as() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
