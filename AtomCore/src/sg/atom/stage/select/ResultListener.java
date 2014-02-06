/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface ResultListener {
    public abstract void update(CollisionResult result);
    
    public abstract void update(CollisionResults result);
}
