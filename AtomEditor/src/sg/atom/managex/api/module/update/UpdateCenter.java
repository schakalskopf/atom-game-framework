/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.module.update;

/**
 * UpdateCenter borrow concepts from Netbean API, Maveb and Getdown for a simple
 * application update and validate application modules. The server side is on AtomEx!
 *
 * <p>How it work: <ol> 
 * 
 * <li>Compare two structrure of components tree
 *
 * <li>Find out out date component
 *
 * <li>Resolve dependencies
 *
 * <li>Get via Network with Netty </ol>
 *
 * <li>Install via osgi or dependency injection.
 * 
 * <p>Currently limitation:<ul>
 * <li>The temp directory of modules are configed by user
 * 
 * <li>The protocol is still quite simple
 * 
 * </ul>
 * @author cuong.nguyenmanh2
 */
public class UpdateCenter {
}
