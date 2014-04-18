package sg.atom.world.geometry.texture;

import java.util.*;

/**
 * TextureBank makes handling multiple textures easier.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class TextureBank {

    public static final int TEXTURE_2D = 1;
    public static final int TEXTURE_CUBE = 2;
    /**
     * Indicates whether or not the gl context is available. The gl-handler will
     * inform the bank about this.
     */
    protected boolean hasContext = false;
    /**
     * Flag to indicate whether this texturebank actually physically loads the
     * textures the user seeks, into memory.
     */
    protected boolean reallyLoadTextures;
    /**
     * The map that contains the name->TextureInfo bindings.
     */
    protected TreeMap bank = new TreeMap();

    /**
     * Default constructor
     */
    public TextureBank() {
        this.reallyLoadTextures = true;
    }

    /**
     * Default constructor
     */
    public TextureBank(boolean reallyLoadTextures) {
        this.reallyLoadTextures = reallyLoadTextures;
    }

    /**
     * Returns the bank content as a collection.
     *
     * @return object collection
     */
    public Collection values() {
        return bank.values();
    }

    /**
     * Replace a texture in the bank
     *
     * @param bindName name to replace
     * @param filename filename of the new texture
     * @param type texture type, either 2D or CUBE (see finals)
     * @param options texture options given in TextureInfo
     * @return the new texture
     */
    public TextureInfo replace(String bindName, String filename, int type, int options) {
        bank.remove(bindName);
        return load(bindName, filename, type, options);
    }

    /**
     * Replace a texture in the bank
     *
     * @param bindName name to replace
     * @param filename filename of the new texture
     * @param type texture type, either 2D or CUBE (see finals)
     * @return the new texture
     */
    public TextureInfo replace(String bindName, String filename, int type) {
        return replace(bindName, filename, type, TextureInfo.BILINEAR);
    }

    /**
     * Replace a texture in the bank
     *
     * @param bindName name to replace
     * @param filename filename of the new texture
     * @return the new texture
     */
    public TextureInfo replace(String bindName, String filename) {
        return replace(bindName, filename, TEXTURE_2D);
    }

    /**
     * Find a texture in the bank. If a texture is not found and a filename is
     * specified, the method loads a new texture to the bank and returns it.
     *
     * @param bindName name of the texture to seek for
     * @param filename (optional) file to load the texture from, if it's not in
     * the bank
     * @param type texture type, either 2D or CUBE (see finals)
     * @param options texture options given in TextureInfo
     * @return texture that matches the name given, null if not found
     */
    public TextureInfo find(String bindName, String filename, int type, int options) {
        TextureInfo tex = (TextureInfo) bank.get(bindName);
        if (tex != null) {
            return tex;
        } else if (filename != null) {
            return load(bindName, filename, type, options);
        } else {
            return null;
        }
    }

    /**
     * Find a texture in the bank. If a texture is not found and a filename is
     * specified, the method loads a new texture to the bank and returns it.
     *
     * @param bindName name of the texture to seek for
     * @param filename (optional) file to load the texture from, if it's not in
     * the bank
     * @param type texture type, either 2D or CUBE (see finals)
     * @return texture that matches the name given, null if not found
     */
    public TextureInfo find(String bindName, String filename, int type) {
        return find(bindName, filename, type, TextureInfo.BILINEAR);
    }

    /**
     * Find a texture in the bank. If a texture is not found and a filename is
     * specified, the method loads a new texture to the bank and returns it.
     *
     * @param bindName name of the texture to seek for
     * @param filename (optional) file to load the texture from, if it's not in
     * the bank
     * @return texture that matches the name given, null if not found
     */
    public TextureInfo find(String bindName, String filename) {
        return find(bindName, filename, TEXTURE_2D);
    }

    /**
     * Load a new texture from file and add it to bank
     *
     * @param bindName name to bind the texture to
     * @param filename file to load the texture from
     * @param type texture type, either 2D or CUBE (see finals)
     * @param options texture options given in TextureInfo
     * @return the loaded texture
     */
    public TextureInfo load(String bindName, String filename, int type, int options) {
//        try {
            TextureInfo tex = null;

            if (reallyLoadTextures) {
//                if (type == TEXTURE_2D) {
//                    tex = new TextureInfo2D(filename, options);
//                } else if (type == TEXTURE_CUBE) {
//                    tex = new TextureInfoCube(filename);
//                }
            } else {
                tex = new TextureInfoDummy();
            }

            bank.put(bindName, tex);
            tex.setBindName(bindName);

            return tex;
//        } catch (TextureException e) {
//            System.out.println("TextureBank: " + e);
//            return null;
//        }
    }

    /**
     * Load a new texture from file and add it to bank
     *
     * @param bindName name to bind the texture to
     * @param filename file to load the texture from
     * @param type texture type, either 2D or CUBE (see finals)
     * @return the loaded texture
     */
    public TextureInfo load(String bindName, String filename, int type) {
        return load(bindName, filename, type, TextureInfo.BILINEAR);
    }

    /**
     * Load a new texture from file and add it to bank
     *
     * @param bindName name to bind the texture to
     * @param filename file to load the texture from
     * @return the loaded texture
     */
    public TextureInfo load(String bindName, String filename) {
        return load(bindName, filename, TEXTURE_2D);
    }

    /**
     * Send all textures to the GPU.
     *
     * @return amount of textures sent to gpu, -1 if no gl context was available
     */
    public int assignAll() {
        if (!hasContext) {
            return -1;
        }

        int amount = 0;
        for (Iterator it = values().iterator(); it.hasNext();) {
            TextureInfo t = (TextureInfo) it.next();
//       System.out.println("ASSIGNING: " + t);
            if (t.assign() == true) {
                amount++;
            }
//       System.out.println("ASSIGNED: " + t);
        }
        return amount;
    }

    /**
     * A simple method called just to tell the bank whether or not a a gl
     * context is ready for its disposal.
     */
    public void contextInform(boolean hasContext) {
        this.hasContext = hasContext;
    }

    /**
     * Get the texture bank loading state. This means whether or not the bank
     * actually loads textures.
     *
     * @return loading or not
     */
    public boolean getLoadState() {
        return reallyLoadTextures;
    }

    /**
     * Set the texture bank loading state. This means whether or not the bank
     * actually loads textures.
     *
     * @param reallyLoadTextures new value for the load state
     */
    public void setLoadState(boolean reallyLoadTextures) {
        this.reallyLoadTextures = reallyLoadTextures;
    }

    /**
     * List all texture's in this bank in a string
     *
     * @return string containing all the textures
     */
    public String toString() {
        Iterator i = bank.values().iterator();
        String result = "Texture Bank:\n\n";

        while (i.hasNext()) {
            TextureInfo tex = (TextureInfo) i.next();

            result += "Binding: " + tex.getBindName() + "\n" + tex.toString();
        }

        return result;
    }

//    /**
//     * Main method for testing
//     *
//     * @param args ignored
//     */
//    public static void main(String[] args) {
//        TextureBank bank = new TextureBank();
//
//        bank.find("test", "data/test.png");
//
//        System.out.println(bank.toString());
//    }
}
