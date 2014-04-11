/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.uix.common.dock;

/**
 *
 * @author CuongNguyen
 */
public interface Dockable {
    public void dock();
    
    public DockingConfiguration getDockingConfiguration();
}
