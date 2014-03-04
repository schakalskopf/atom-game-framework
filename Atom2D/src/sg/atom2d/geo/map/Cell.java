/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.map;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

/**
 * A Simple Cell implemented to be element of primary GridMap of the game.
 *
 * Support geographics, spatial indexing, path finding etc...
 *
 * @author cuong.nguyenmanh2
 */
public class Cell {

    int type;
    //int height;
    int texType;
    int status;
    int index;

    public Vector2f getLocation2D() {
        return new Vector2f();
    }

    public Vector3f getLocation3D() {
        return new Vector3f();
    }
}
