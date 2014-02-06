/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic;

@Deprecated
/**
 * FIXME: Should be AppState and replaced with loose couple version with Guava
 * predicate and Eventbus
 *
 * As Logic Factory : create, compose, validate
 *
 * As Logic Manager : manage list As Logic Event Handler : broadcast
 *
 * @author atomix
 *
 */
public class LogicManager {

    public boolean create() {
        return true;
    }

    public boolean compose() {
        return true;
    }
}
