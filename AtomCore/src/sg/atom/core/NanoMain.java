/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core;

import sg.atom.core.context.NanoContext;

/**
 * This is where you built your universer from the zero point.
 *
 * <p>NanoMain and NanoContext are the two very first concepts of the Nano scope
 * of Atom framework. NanoMain is the implementation of NanoContext, which is a
 * specification of real-time application upon Java, openGL and other libraries
 * in which JME3 is built in. The different is, NanoMain tend to be distributed
 * and small as its phylosophy:<ul>
 *
 * <li>Its timing it's not rely in the ContextListener as Application does.
 *
 * <li>Its embed data are DObject, entities are Actor, communicate via Messages.
 *
 * <li>Its faul-tolerant! http://en.wikipedia.org/wiki/Fault_tolerance to be
 * used in very large base.</ul>
 *
 * @author cuong.nguyenmanh2
 */
public class NanoMain implements NanoContext {
}
