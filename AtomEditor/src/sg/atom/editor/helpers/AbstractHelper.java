/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.helpers;

import sg.atom.corex.scenegraph.spatial.SpatialList;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Helper is facilities of system which always there to help. It meant to be
 * "on-screen" facilities helping the designer work.
 *
 * <p>Helper is no contracted to be heavy lifted as Manager. Helper going to be
 * first class in Atom!
 *
 * @author hungcuong
 */
public abstract class AbstractHelper extends AbstractControl {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    AbstractHelper(String name) {
        this.name = name;

    }

    public abstract void initHelper();

    public abstract Node getSubNode();

    public abstract SpatialList getSelectableList();

    public boolean isHelperNode(Spatial sp) {
        return getSubNode().hasChild(sp);
    }
}
