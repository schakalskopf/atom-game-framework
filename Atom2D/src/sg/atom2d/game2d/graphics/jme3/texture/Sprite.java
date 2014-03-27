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
import sg.atom2d.game2d.graphics.jme3.Simple2DApplication;

/**
 * A bridge from LibGDX to JME3 context.
 *
 * TODO: Replace completely.
 *
 * @author cuong.nguyenmanh2
 */
public class Sprite {
    
    private int width, height;
    protected Geometry geometry;
    protected int s_frames, s_rows, s_direction = 2, s_currentFrame = 0, s_frameWidth;
    protected Texture spritesheet;
    protected Quad quad;
    private Material material;
    private AssetManager assetManager;

    public Sprite() {
    }

    public Sprite(Texture loadTexture) {
        assetManager = Simple2DApplication.getInstance().getAssetManager();
        //Texture s_spritesheet = assetManager.loadTexture(imageLocation);
        spritesheet = loadTexture;
        this.s_frames = 1;
        this.s_rows = 1;
        width = spritesheet.getImage().getWidth() / s_frames;
        height = spritesheet.getImage().getHeight() / s_rows;
        
        
        quad = new Quad(width, height);
        geometry = new Geometry("sprite", quad);

        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setTexture("ColorMap", spritesheet);
    }

    private void updateUV() {
    }

    public Texture getTexture() {
        return this.spritesheet;
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
        geometry.rotate(0, rotation, 0);
    }

    public void setScale(float f) {
        geometry.scale(f);
    }

    public void translate(float velocityX, float velocityY) {
        geometry.setLocalTranslation(velocityX, velocityY, 0);
    }

    public void setColor(float r, float g, float b, float a) {
        material.setColor("Color", new ColorRGBA(r, g, b, a));
    }

    public void setBounds(float f, float f0, float spriteWidth, float spriteHeight) {
    }

    public void setTexture(Texture texture) {
        spritesheet = texture;
    }

    public void setOrigin(float originX, float originY) {
    }

    public static Sprite createSprite(TextureAtlas atlas, String imageName) {
        return new Sprite(atlas.getAtlasTexture(imageName));
    }
}
