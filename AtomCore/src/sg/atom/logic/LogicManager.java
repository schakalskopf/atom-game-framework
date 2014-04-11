/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic;


@Deprecated
/**
 * FIXME: Should be AppState and replaced with loose couple version with Guava
 * predicate and Eventbus.
 *
 * <p>As Logic Factory : create, compose, validate. Inference and validation of
 * big database can be a challange. RETE can be used to implement a small scale
 * Rule system. In next version, going to change to PHREAKY instead.
 * 
 * http://www.javacodegeeks.com/2013/11/r-i-p-rete-time-to-get-phreaky.html
 *
 * <p>As Logic Manager : manage list As Logic Event Handler : broadcast
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
