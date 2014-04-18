/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.enviroment.light;

import sg.atom.world.geometry.algebra.Vec2d;
import sg.atom.world.geometry.algebra.Vec3d;
import sg.atom.world.visibility.bsp.Bsp;

/**
 * A class for managing lightmap creation. Area from lightmaps is allocated
 * using fixed-size blocks. A new texture is created, when the current texture
 * becomes full. Handles the writing of the lightmap images using ppm format.
 *
 * Last Modified: 23.04.2002
 *
 */
public class LightmapCreator {
    // The base filename

    private String fileName = null;
    // The current texture image
    private int fileCounter = 0;
    // The texture dimension (w and h)
    private int textureSize = 0;
    // The amount of elements vertically and horizontally
    private int elementsInLine = 0;
    // The current element readyt for allocation
    private int currentElement = 0;
    // The length of an element in texture coordinates.
    private float elementLength = 0.0f;
    // The name of the texture currently being processed
    private String currentName = "";
    // The current texture
    private Vec3d[] currentTexture = null;

    /**
     * Encapsulates pixel value summation with zero ignoring.
     *
     * @param sum The result.
     * @param data The data.
     * @param x The x coordinate in data.
     * @param y The t coordinate in data.
     * @param ignore The value to ignore in summation.
     * @return 1 if added , 0 if not added.
     */
    private int postProcessAdder(Vec3d sum, Vec3d[] data, int x, int y, Vec3d ignore) {
        if (x >= 0 && x < textureSize && y >= 0 && y < textureSize) {
            if (!data[y * textureSize + x].equals(ignore)) {
                sum.addTo(data[y * textureSize + x]);
                return 1;
            }
        }
        return 0;
    }

    /**
     * Filters the given data in the given direction. Configured to copy pixels
     * from a given direction into black areas.
     *
     * @param data The data to filter.
     * @param direction The direction of the filter pixels.
     * @return The resulting data.
     */
    private Vec3d[] filterData(Vec3d[] data, int direction) {
        // preprocess data to remove totally unwanted black pixel via kapula
        Vec3d zero = new Vec3d();
        Vec3d[] filtered = new Vec3d[textureSize * textureSize];
        for (int y = 0; y < textureSize; y++) {
            for (int x = 0; x < textureSize; x++) {
                if (data[y * textureSize + x].equals(zero)) {
                    int addends = 0;
                    Vec3d sum = new Vec3d();
                    if (direction == 1) {
                        addends += postProcessAdder(sum, data, x - 1, y, zero);
                    }
                    if (direction == 2) {
                        addends += postProcessAdder(sum, data, x + 1, y, zero);
                    }
                    if (direction == 3) {
                        addends += postProcessAdder(sum, data, x, y - 1, zero);
                    }
                    if (direction == 4) {
                        addends += postProcessAdder(sum, data, x, y + 1, zero);
                    }
                    if (addends > 0) {
                        sum.scaleTo(1f / (float) addends);
                        filtered[y * textureSize + x] = sum;
                    } else {
                        filtered[y * textureSize + x] = zero;
                    }
                } else {
                    filtered[y * textureSize + x] = data[y * textureSize + x];
                }
            }
        }
        return filtered;
    }
//FIXME: @atomix commented out!
//    /**
//     * Writes the data to the given file using ppm format. Ppm is later
//     * converted manually to png format. Filters the data to fill the guard
//     * areas between the lightmap elements.
//     *
//     * @param fileName The destination file name.
//     * @param data The data to write.
//     */
//    private void writeLightmapData(String fileName, Vec3d[] data) {
//        try {
//            Vec3d[] filtered = data;
//            filtered = filterData(filtered, 1);
//            filtered = filterData(filtered, 2);
//            filtered = filterData(filtered, 3);
//            filtered = filterData(filtered, 4);
//            LEDataOutputStream out = new LEDataOutputStream(new FileOutputStream(fileName));
//            // "binary" PPM
//            out.writeBytes("P6\n");
//            out.writeBytes(textureSize + " " + textureSize + "\n");
//            out.writeBytes(255 + "\n");
//            for (int i = 0; i < filtered.length; i++) {
//                out.writeByte((int) (255 * filtered[i].c[0]));
//                out.writeByte((int) (255 * filtered[i].c[1]));
//                out.writeByte((int) (255 * filtered[i].c[2]));
//            }
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            System.out.println("Could not write " + fileName);
//        }
//    }
//
//    /**
//     * Writes the current texture.
//     */
//    public void write() {
//        if (currentTexture != null) {
//            writeLightmapData(currentName + ".ppm", currentTexture);
//        }
//    }

    /**
     * Gets the element length.
     *
     * @return The element length
     */
    public float getElementLength() {
        return elementLength;
    }

    /**
     * Gets the current texture name.
     *
     * @return The current texture name.
     */
    public String getTextureName() {
        return fileName + ".lightmap" + fileCounter;
    }

    /**
     * Gets a texture by name.
     *
     * @return The texture.
     */
    public Vec3d[] getTexture(String name) {
        if (!name.equals(currentName)) {
//            write();
            currentName = name;
            currentTexture = new Vec3d[textureSize * textureSize];
            for (int i = 0; i < textureSize * textureSize; i++) {
                currentTexture[i] = new Vec3d(0, 0, 0);
            }
        }
        return currentTexture;
    }

    /**
     * Gets a new element from the current lightmap.
     *
     * @return A new lightmap element.
     */
    public Vec2d getElement() {
        if (currentElement == elementsInLine * elementsInLine) {
            fileCounter++;
            currentElement = 0;
            if (Bsp.verbose) {
                System.out.println("Allocating new texture " + fileCounter);
            }
        }
        int w = currentElement % elementsInLine;
        int h = currentElement / elementsInLine;
        currentElement++;
        return new Vec2d(1.0f / (float) (textureSize) + (float) w / (float) elementsInLine,
                1.0f / (float) (textureSize) + (float) h / (float) elementsInLine);
    }

    /**
     * Constructs the lightmap creator using the given dimensions and base
     * texture name.
     *
     * @param fileName The base name for textures.
     * @param textureSize The size of a texture image in pixels.
     * @param elementSize The size of one lightmap element.
     */
    public LightmapCreator(String fileName, int textureSize, int elementSize) {
        elementsInLine = (textureSize / elementSize);
        elementLength = (float) (elementSize - 2) / (float) textureSize;
        currentElement = 0;
        fileCounter = 0;
        this.textureSize = textureSize;
        this.fileName = fileName;
    }
}
