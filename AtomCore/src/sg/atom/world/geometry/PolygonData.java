package sg.atom.world.geometry;

import java.util.Vector;

/**
 * PolygonData is used as an intermediate storage of 3D geometry data when
 * importing it from file. It provides handy helper methods to convert data.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class PolygonData {
    // NSE chunks

    private static final int CHUNK_GEOMETRY = 0x0001;
    private static final int CHUNK_LIGHT = 0x0002;
    private static final int CHUNK_DUMMY = 0x0016;
    private static final int CHUNK_EOF = 0xffff;
    // Verbose printing
    private static final boolean verbose = false;
    // Polygons sorted by their textures
    private Polygon[][] polygonsByTexture;
    // Array of textures
    private String[] textures;
    // The lights
    private Vector lights;

    /**
     * Empty constructor.
     */
    public PolygonData() {
        polygonsByTexture = null;
        textures = null;
        lights = null;
    }

    /**
     * Constructs and load the given file.
     *
     * @param filename The name of the file to load
     */
    public PolygonData(String filename) {
        lights = new Vector();
//        load(filename);
    }
//
//    /**
//     * Reads a node transformation matrix from the input.
//     *
//     * @param in The input stream.
//     * @return The position part of the transform.
//     */
//    private static Vec3d readNodeTM(LEDataInputStream in) throws IOException {
//        Vec3d result = new Vec3d(255, 255, 255);
//
//        for (int i = 0; i < 4; i++) {
//            float a = in.readFloat();
//            float b = in.readFloat();
//            float c = in.readFloat();
//
//            result = new Vec3d(a, b, c);
//
//            if (verbose) {
//                System.out.println(result);
//            }
//        }
//
//        return result;
//    }
//
//    /**
//     * Reads and discards an ASCIIZ name from the stream.
//     *
//     * @param in The input stream.
//     */
//    private static void readName(LEDataInputStream in) throws IOException {
//        byte c = in.readByte();
//        while (c != 0) {
//            c = in.readByte();
//        }
//    }
//
//    /**
//     * Reads a color value from the input.
//     *
//     * @param in The input stream.
//     * @return A color value.
//     */
//    private static Vec3d readColor(LEDataInputStream in) throws IOException {
//        Vec3d result = new Vec3d();
//
//        result.c[0] = in.readFloat();
//        result.c[1] = in.readFloat();
//        result.c[2] = in.readFloat();
//
//        return result;
//    }
//
//    /**
//     * Load 3D data from file.
//     *
//     * @param filename filename to load from
//     * @return true if no errors occured
//     */
//    public boolean load(String filename) {
//        try {
//            LEDataInputStream in = new LEDataInputStream(new FileInputStream(filename));
//
//            boolean incomplete = true;
//
//            while (incomplete) {
//                int chunk = in.readInt();
//
//                switch (chunk) {
//                    case CHUNK_DUMMY:
//                        // ignore floating object chunks
//                        readName(in);
//                        in.skipBytes(12);
//                        break;
//                    case CHUNK_GEOMETRY:
//                        if (verbose) {
//                            System.out.println("Found chunk GEOMETRY");
//                        }
//                        readNodeTM(in);
//
//                        short numVertices = in.readShort();
//                        if (verbose) {
//                            System.out.println(numVertices + " vertices");
//                        }
//                        Vertex[] vertices = new Vertex[numVertices];
//
//                        boolean vertexColors = in.readBoolean();
//                        boolean texCoords = in.readBoolean();
//
//                        if (verbose) {
//                            System.out.println("vertex colors: " + vertexColors);
//                        }
//                        if (verbose) {
//                            System.out.println("texture coordinates: " + texCoords);
//                        }
//
//                        for (int i = 0; i < numVertices; i++) {
//                            float x = in.readFloat();
//                            float y = in.readFloat();
//                            float z = in.readFloat();
//
//                            float nx = in.readFloat();
//                            float ny = in.readFloat();
//                            float nz = in.readFloat();
//
//                            if (verbose) {
//                                System.out.println("vertex " + i + ": " + new Vec3d(x, y, z).toString());
//                            }
//                            if (verbose) {
//                                System.out.println("normal " + i + ": " + new Vec3d(nx, ny, nz).toString());
//                            }
//
//                            Vec3d color = new Vec3d(1, 1, 1);
//
//                            if (vertexColors) {
//                                color = readColor(in);
//                                if (verbose) {
//                                    System.out.println("color " + color);
//                                }
//                            }
//
//                            float u = 0;
//                            float v = 0;
//
//                            if (texCoords) {
//                                u = in.readFloat();
//                                v = in.readFloat();
//
//                                if (verbose) {
//                                    System.out.println("texcoord " + i + ": " + new Vec2d(u, v).toString());
//                                }
//                            }
//
//                            vertices[i] = new Vertex(new Vec3d(x, y, z), new Vec3d(nx, ny, nz), new Vec2d(u, v), color);
//                        }
//
//                        int numTextures = in.readInt();
//                        if (verbose) {
//                            System.out.println(numTextures + " textures");
//                        }
//
//                        textures = new String[numTextures];
//
//                        byte[] filenameBytes = new byte[256];
//
//                        if (numTextures == 0) {
//                            polygonsByTexture = new Polygon[1][];
//                        } else {
//                            polygonsByTexture = new Polygon[numTextures][];
//                        }
//
//                        int totalFaces = 0;
//
//                        for (int i = 0; i < numTextures; i++) {
//                            in.read(filenameBytes, 0, 256);
//                            int faces = in.readUnsignedShort();
//                            totalFaces += faces;
//
//                            polygonsByTexture[i] = new Polygon[faces];
//
//                            String textureName = new String(filenameBytes, "ISO-8859-1");
//                            textureName = textureName.substring(0, textureName.indexOf('\0'));
//
//                            textures[i] = "data/" + textureName;
//
//                            if (verbose) {
//                                System.out.println(faces + " faces textured with " + textures[i]);
//                            }
//
//                            for (int j = 0; j < faces; j++) {
//                                int a = in.readUnsignedShort();
//                                int b = in.readUnsignedShort();
//                                int c = in.readUnsignedShort();
//
//                                polygonsByTexture[i][j] = new Polygon(vertices[a], vertices[b], vertices[c], textures[i]);
//
//                                if (verbose) {
//                                    System.out.println("face " + j + ": <" + a + "," + b + "," + c);
//                                }
//                            }
//                        }
//
//                        if (numTextures == 0) {
//                            System.out.println("ERROR: No textures specified!");
//                            System.exit(-1);
//
//                            /*
//            
//                             int faces = in.readUnsignedShort();
//
//                             polygonsByTexture[0] = new Polygon [faces];
//
//                             for(int j = 0; j < faces; j++)
//                             {
//                             int a = in.readUnsignedShort();
//                             int b = in.readUnsignedShort();
//                             int c = in.readUnsignedShort();
//              
//                             polygonsByTexture[0][j] = new Polygon(vertices[a], vertices[b], vertices[c], textureName);
//              
//                             if(verbose) System.out.println("face " + j + ": <" + a + "," + b + "," + c);
//                             }
//                             */
//                        }
//                        break;
//                    case CHUNK_LIGHT:
//                        if (verbose) {
//                            System.out.println("Found chunk LIGHT");
//                        }
//
//                        in.skipBytes(4);
//
//                        Vec3d pos = readNodeTM(in);
//
//                        Vec3d color = readColor(in);
//                        if (verbose) {
//                            System.out.println("Color " + color);
//                        }
//
//                        float nearAttenStart = in.readFloat();
//                        float nearAttenEnd = in.readFloat();
//                        float farAttenStart = in.readFloat();
//                        float farAttenEnd = in.readFloat();
//
//                        if (verbose) {
//                            System.out.println("Near attenuation start: " + nearAttenStart);
//                        }
//                        if (verbose) {
//                            System.out.println("Near attenuation end: " + nearAttenEnd);
//                        }
//                        if (verbose) {
//                            System.out.println("Far attenuation start: " + farAttenStart);
//                        }
//                        if (verbose) {
//                            System.out.println("Far attenuation end: " + farAttenEnd);
//                        }
//
//                        lights.add(new Light(pos, color, nearAttenStart, nearAttenEnd, farAttenStart, farAttenEnd));
//                        break;
//                    case CHUNK_EOF:
//                        if (verbose) {
//                            System.out.println("Found chunk EOF");
//                        }
//                        incomplete = false;
//                        break;
//                    default:
//                        System.out.println("WARNING: unknown chunk (" + chunk + "), aborted.");
//                        in.close();
//                        return false;
//                }
//            }
//
//            in.close();
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    /**
     * Pack loaded data into a single array of Polygons.
     *
     * @return array of polygons
     */
    public Polygon[] toSingleArray() {
        // Copy polygons into single array
        Polygon[] result = new Polygon[getTotalNumPolys()];

        if (textures.length == 0) {
            return polygonsByTexture[0];
        } else {
            for (int k = 0, i = 0; i < textures.length; i++) {
                for (int j = 0; j < polygonsByTexture[i].length; j++, k++) {
                    result[k] = polygonsByTexture[i][j];
                }
            }
        }

        return result;
    }

    /**
     * Get the total number of polygons in the loaded data.
     *
     * @return number of polygons
     */
    public int getTotalNumPolys() {
        if (textures.length == 0) {
            return polygonsByTexture[0].length;
        }

        int result = 0;

        for (int i = 0; i < textures.length; i++) {
            result += polygonsByTexture[i].length;
        }

        return result;
    }

    /**
     * Get a polygon by its texture
     *
     * @param texture the texture number of the polygon
     * @param n index to the polygon
     * @return the n'th polygon mapped with texture
     */
    public Polygon getPolygon(int texture, int n) {
        return polygonsByTexture[texture][n];
    }

    /**
     * Get all polygons mapped texture
     *
     * @param texture index to texture
     * @return array of polygons mapped with texture
     */
    public Polygon[] getPolygons(int texture) {
        return polygonsByTexture[texture];
    }

    /**
     * Get total number of vertices in polygons mapped with n'th texture.
     *
     * @param n index to texture
     * @return number of vertices
     */
    public int getNumVertices(int n) {
        return (getNumPolys(n) * 3);
    }

    /**
     * Get number of unique textures in loaded data.
     *
     * @return number of textures
     */
    public int getNumTextures() {
        return textures.length;
    }

    /**
     * Get number of polygons mapped with texture
     *
     * @param texture index to texture
     * @return number of polygons
     */
    public int getNumPolys(int texture) {
        return polygonsByTexture[texture].length;
    }

    /**
     * Get the name of the i'th texture
     *
     * @param i index to texture
     * @return name of the texture (filename)
     */
    public String getTexture(int i) {
        return textures[i];
    }

    /**
     * Return the number of lights in loaded data.
     *
     * @return an <code>int</code> value
     */
    public int getNumLights() {
        return lights.size();
    }

    /**
     * Return n'th light
     *
     * @param n index to light
     * @return light #n
     */
    public Light getLight(int n) {
        return (Light) lights.get(n);
    }

    /**
     * Get all lights in loaded data in a Vector.
     *
     * @return vector of Lights
     */
    public Vector getLights() {
        return lights;
    }
}
