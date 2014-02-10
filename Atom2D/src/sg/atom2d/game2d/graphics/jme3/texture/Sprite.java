/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.jme3.texture;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import jme3tools.optimize.TextureAtlas;
import sg.atom2d.swing.SwingSimple2DApp;

/**
 * A bridge from LibGDX to JME3 context.
 *
 * TODO: Replace completely.
 *
 * @author cuong.nguyenmanh2
 */
public class Sprite {

    protected Geometry s_geometry;
    protected int s_frames, s_rows, s_direction = 2, s_currentFrame = 0, s_frameWidth;
    protected Texture s_spritesheet;
    protected Quad s_quad;
    private Material s_material;
    private AssetManager assetManager;
    private int width, height;

    public Sprite() {
    }

    public Sprite(Texture loadTexture) {
        assetManager = SwingSimple2DApp.getInstance().getAssetManager();
        //Texture s_spritesheet = assetManager.loadTexture(imageLocation);
        s_spritesheet = loadTexture;
        this.s_frames = 1;
        this.s_rows = 1;
        width = s_spritesheet.getImage().getWidth() / s_frames;
        height = s_spritesheet.getImage().getHeight() / s_rows;
        s_quad = new Quad(width, height);
        s_geometry = new Geometry("sprite", s_quad);

        s_material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        s_material.setTexture("ColorMap", s_spritesheet);
    }

    private void updateUV() {
    }

    public Texture getTexture() {
        return this.s_spritesheet;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getOriginX() {
        return 0;
    }

    public float getOriginY() {
        return 0;
    }

    public void flip(boolean flipX, boolean flipY) {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setRotation(float rotation) {
        s_geometry.rotate(0, rotation, 0);
    }

    public void setScale(float f) {
        s_geometry.scale(f);
    }

    public void translate(float velocityX, float velocityY) {
        s_geometry.setLocalTranslation(velocityX, velocityY, 0);
    }

    public void setColor(float r, float g, float b, float a) {
        s_material.setColor("Color", new ColorRGBA(r, g, b, a));
    }

    public void setBounds(float f, float f0, float spriteWidth, float spriteHeight) {
    }

    public void setTexture(Texture texture) {
        s_spritesheet = texture;
    }

    public void setOrigin(float originX, float originY) {
    }

    public static Sprite createSprite(TextureAtlas atlas, String imageName) {
        return new Sprite(atlas.getAtlasTexture(imageName));
    }
}
