/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.modifier;

import com.google.common.base.Function;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * A functional Control.
 * 
 * @author cuong.nguyenmanh2
 */
public abstract class SpatialModifier extends AbstractControl implements Function<Spatial, Spatial> {

    public Spatial apply(Spatial input) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return spatial;
    }
}
