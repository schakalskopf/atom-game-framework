/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.actor;

/**
 * ActorManager retain a list of Actors and provide a concurent workspace
 * enviroment.
 *
 * FIXME: ServiceManager or ActorManager?
 *
 * @author atomix
 */
public class ActorManager {

    private int totalId = -1;

    public long getNewGlobalId() {
        return totalId++;
    }
}
