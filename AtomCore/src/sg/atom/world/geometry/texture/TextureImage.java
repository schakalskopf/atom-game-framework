package sg.atom.world.geometry.texture;

//import gl4java.utils.textures.*;
//import gl4java.GLContext;
//import gl4java.*;
//
//import gl.GL;

/**
 * Contains a single texture image and stores its properties.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 * @author <a href="mailto:tvlehton@cc.hut.fi">Tuukka Lehtonen</a>
 */
public class TextureImage {

    byte[] data;
    int width;
    int height;
    int bpp;
    int format;
    int internalFormat;
    String filename;

    /**
     * Default constructor.
     */
    public TextureImage() {
    }

    /**
     * Constructor
     *
     * @param filename filename of the image to load
     * @exception TextureException if an error occurs
     */
    public TextureImage(String filename) throws TextureException {
        load(filename);
    }

    /**
     * Checks if an integer is a power of two.
     *
     * @param x an integet
     * @return true if it is power of two, false otherwise
     */
    protected static boolean isPowerOfTwo(int x) {
        int i, powerOfTwo;

        for (i = 1; i < 16; i++) {
            powerOfTwo = (1 << i);

            if (x == powerOfTwo) {
                return true;
            }
        }

        return false;
    }

    /**
     * Load an image to use as a texture. The method checks the appropriate
     * loader based on the file extension.
     *
     * @param filename filename of the image
     * @return true, if succeeded
     * @exception TextureException if an error occurs
     */
    public boolean load(String filename) throws TextureException {
        this.filename = filename;
        boolean result = false;

        if (filename.indexOf(".png") != -1) {
            result = loadPNG(filename);
        } else {
            throw new TextureException("Trying to load unsupported file format: " + filename);
        }

        if (result && (!isPowerOfTwo(width) || !isPowerOfTwo(height))) {
            throw new TextureException("loaded texture has bad dimensions (must be power of two)!");
        }

        return result;
    }

    /**
     * Load a PNG image. The OpenGL context must be created before calling this
     * method.
     *
     * @param filename file to load
     * @return true if succeeded
     * @exception TextureException if an error occurs
     */
    protected boolean loadPNG(String filename){// throws TextureException {
//        if (GL.gl == null) {
//            throw new TextureException("WARNING: OpenGL context not yet created!");
//        }
//
//        System.out.println("Loading PNG texture from " + filename);
//
//        GLFunc gl = GL.gl;
//        GLUFunc glu = GL.glu;
//
//        PngTextureLoader loader = new PngTextureLoader(gl, glu);
//
//        loader.readTexture(filename);
//
//        if (loader.isOk()) {
//            data = loader.getTexture();
//            width = loader.getImageWidth();
//            height = loader.getImageHeight();
//            bpp = loader.getComponents();
//            format = loader.getGLFormat();
//            internalFormat = bpp;
//
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }

    /**
     * Get the number of bits per pixel
     *
     * @return bits per pixel
     */
    public int getBitsPerPixel() {
        return bpp * 8;
    }

    /**
     * Get the number of bytes per pixel
     *
     * @return bytes per pixel
     */
    public int getBytesPerPixel() {
        return bpp;
    }

    /**
     * Get texture width
     *
     * @return width of texture
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get texture height
     *
     * @return height of texture
     */
    public int getHeight() {
        return height;
    }

    /**
     * Convert this texture to string
     *
     * @return texture's info in a string
     */
    public String toString() {
        String result = "Filename: " + filename + "\nSize: " + width + "x" + height + "x" + getBitsPerPixel();

        return result;
    }
}
