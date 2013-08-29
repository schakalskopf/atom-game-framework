/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.actor;

/**
 *
 * @author hungcuong
 */
public class ActorManager {

    private int totalId = -1;

    public void addFriend() {
    }

    public void addEnemy() {
    }
    public long getNewGlobalId() {
        return totalId++;
    }
}
