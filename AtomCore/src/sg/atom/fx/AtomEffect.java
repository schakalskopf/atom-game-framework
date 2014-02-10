/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx;

import com.jme3.scene.Spatial;
import sg.atom.core.lifecycle.ManagableObject;

/**
 * AtomEffect. In general, an Effect's a visual activity but "transparent" activity - something
 * happen but not really affect the gameplay and the logic, to distinguish with
 * a GameAction, which also have 3 steps paradigm.
 *
 * <p>An effect can also trigger some more actitives as Trigger before and after
 * its finish. It also should has internal states which should be exposed as
 * behaviours and visualization. That's why it called Effect! </p>
 *
 * <p>Atom implementation of Effect made somes important assumsion about below
 * things for better usages:
 *
 * <ul> <li>Managable, Composable, Stylizable. Indexing and Ordering</li>
 *
 * <li>Inspectable. In Java term, an Effect should be a Bean!.</li> 
 * 
 * <li>Attend GameLoop. with update(tpf).</li></ul> </p>
 *
 * @author cuong.nguyenmanh2
 */
public interface AtomEffect extends ManagableObject{
    public int getIndex();
    
    public void active(Spatial target);

    //public void update(float tpf);

    public void deactive();
    
}
