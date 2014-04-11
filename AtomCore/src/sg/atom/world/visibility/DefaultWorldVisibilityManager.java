/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.visibility;

import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class DefaultWorldVisibilityManager implements WorldVisibilityManager{

    @Override
    public ArrayList<AtomZone> getZones() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void enterZone(AtomZone zone) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void exitZone(AtomZone zone) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toogle(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mark(Spatial spatial, Object mark) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
