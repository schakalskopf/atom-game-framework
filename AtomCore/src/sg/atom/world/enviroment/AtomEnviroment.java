/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.enviroment;

import javolution.context.AbstractContext;

/**
 * Enviroment are context for game actor and action and effects.
 *
 * In this simple implementation, AtomEnviroment stands for the declarative of
 * scenery which effect the World,Screen and other Effects, Actions.
 *
 * <p>This concept is borrowed from Context ... Javolution, JScience EJB and
 * Spring.
 *
 * @author cuong.nguyenmanh2
 */
public class AtomEnviroment extends AbstractContext {

    @Override
    protected AbstractContext inner() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
