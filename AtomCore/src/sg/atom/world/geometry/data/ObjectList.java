package sg.atom.world.geometry.data;

import java.util.LinkedList;
import java.io.Serializable;

/**
 * A class for encapsulating a list of floating object names and positions
 */
public class ObjectList implements Serializable {
    // list of names

    public LinkedList objectNames = new LinkedList();
    // list ob positions
    public LinkedList objectPositions = new LinkedList();
}
