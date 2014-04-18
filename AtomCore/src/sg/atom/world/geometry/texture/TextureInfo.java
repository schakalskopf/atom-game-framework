package sg.atom.world.geometry.texture;

//import gl4java.utils.textures.*;
//import gl4java.GLContext;
//import gl4java.*;
//
//import gl.GL;

/**
 * Texture class handles management of individual textures. This is an abstract
 * base for different types of textures, namely 2D and CUBE_MAP.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 * @author <a href="mailto:tvlehton@cc.hut.fi">Tuukka Lehtonen</a>
 */
public abstract class TextureInfo {

    /**
     * Use bilinear filtering.
     */
    public static final int BILINEAR = 1;
    /**
     * Not used at this time.
     */
    public static final int COMPRESSION = 2;
    /**
     * Use bilinear mipmapping.
     */
    public static final int MIPMAP = 4;
    /**
     * Use trilinear filtering, ie. filtering between mipmaps.
     */
    public static final int TRILINEAR = 8;
    /**
     * The original images in their own structures. TEXTURE_2D's have only one
     * image, but TEXTURE_CUBE_MAP_EXT's have six.
     */
    protected TextureImage[] images;
    /**
     * The gl generated texture name (glGenTextures) for this texture. -1 means
     * not initialized.
     */
    protected int name = -1;
    /**
     * Texture options in bits. BILINEAR, COMPRESSION, MIPMAP.
     */
    protected int options;
    /**
     * Wrapping to use.
     */
    protected int wrapMode;
    /**
     * Name this texture is bound to in the texture bank.
     */
    protected String bindName;

    /**
     * Default constructor
     */
    TextureInfo() {
        options = BILINEAR;
//        wrapMode = GLEnum.GL_REPEAT;
        bindName = "Not bound";
    }

    /**
     * Constructor with default options.
     */
    TextureInfo(int options) {
        this.options = options;
//        wrapMode = GLEnum.GL_REPEAT;
        bindName = "Not bound";
    }

    /**
     * Creates a new unique name for a texture. The OpenGL context must be
     * created before calling this method. Name 0 is a silently ignored by GL.
     *
     * @return a unique texture name
     */
    protected int createNewName() {
//        if (GL.gl == null) {
//            System.err.println("WARNING: OpenGL context not yet created!");
//            return -1;
//        }

        int[] tmp = new int[1];

//        GL.gl.glGenTextures(1, tmp);
        return tmp[0];
    }

    /**
     * Deletes the texture from GL.
     */
    void release() {
//        if (GL.gl == null) {
//            System.err.println("WARNING: OpenGL context not yet created!");
//            return;
//        }

        int[] tmp = {name};
//        GL.gl.glDeleteTextures(1, tmp);
    }

    /**
     * Convert this texture to string
     *
     * @return texture's info in a string
     */
    public String toString() {
        return "Name: " + name + " bound to " + bindName + "\n" + images[0];
    }

    /**
     * Set the name this texture was bound in a texture bank. This method is for
     * ease of debugging.
     *
     * @param newBindName the name this texture was bound
     */
    void setBindName(String newBindName) {
        bindName = newBindName;
    }

    /**
     * Get the name this texture was bound in a texture bank. This method is for
     * ease of debugging.
     *
     * @return the name this texture was bound
     */
    String getBindName() {
        return bindName;
    }

    /**
     * Get the array of images contained in this texture.
     *
     * @return the image array for this texture
     */
    TextureImage[] getImages() {
        return images;
    }

    /**
     * Sets a new texture wrapping mode.
     *
     * @param mode one of the following GL enumerations: GL_CLAMP,
     * GL_CLAMP_TO_EDGE, GL_REPEAT, GL_CLAMP_TO_BORDER
     */
    public void setWrapMode(int wrapMode) {
        this.wrapMode = wrapMode;
    }

    /**
     * Set the texture options to use for this texture. Its up to the texture
     * type derived from this class to reset the texture paramteres even after
     * assigning the texture. This method should be overridden by
     *
     * @param options a bitmask of the constants in this class
     */
    public void setOptions(int options) {
        this.options = options;
    }

    /**
     * Send this texture to the GPU. Only the TextureBank is allowed to do this.
     *
     * @return true if succeeded
     */
    abstract boolean assign();

    /**
     * Select this texture for drawing
     *
     * @return true, if succeeded
     */
    public abstract boolean bind();
}
