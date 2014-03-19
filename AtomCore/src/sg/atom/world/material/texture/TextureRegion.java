/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.material.texture;

import com.jme3.math.Vector2f;
import java.nio.FloatBuffer;

/**
 * The same with TextureAtlasTile but can be used outside the TextureAtlas. This
 * one used for a single Texture to write/read from Texture data.
 *
 * @author cuong.nguyenmanh2
 */
public class TextureRegion {

    private int x;
    private int y;
    private int width;
    private int height;
    int atlasWidth, atlasHeight;

    public TextureRegion(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get the transformed texture coordinate for a given input location.
     *
     * @param previousLocation The old texture coordinate.
     * @return The new texture coordinate inside the atlas.
     */
    public Vector2f getLocation(Vector2f previousLocation) {
        float rx = (float) getX() / (float) atlasWidth;
        float ry = (float) getY() / (float) atlasHeight;
        float w = (float) getWidth() / (float) atlasWidth;
        float h = (float) getHeight() / (float) atlasHeight;
        Vector2f location = new Vector2f(rx, ry);
        float prevX = previousLocation.x;
        float prevY = previousLocation.y;
        location.addLocal(prevX * w, prevY * h);
        return location;
    }

    /**
     * Transforms a whole texture coordinates buffer.
     *
     * @param inBuf The input texture buffer.
     * @param offset The offset in the output buffer
     * @param outBuf The output buffer.
     */
    public void transformTextureCoords(FloatBuffer inBuf, int offset, FloatBuffer outBuf) {
        Vector2f tex = new Vector2f();

        // offset is given in element units
        // convert to be in component units
        offset *= 2;

        for (int i = 0; i < inBuf.limit() / 2; i++) {
            tex.x = inBuf.get(i * 2 + 0);
            tex.y = inBuf.get(i * 2 + 1);
            Vector2f location = getLocation(tex);
            //TODO: add proper texture wrapping for atlases..
            outBuf.put(offset + i * 2 + 0, location.x);
            outBuf.put(offset + i * 2 + 1, location.y);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
