/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.shape;

import com.jme3.scene.Node;

/**
 *
 * @author hungcuong
 */
public interface ShapeUtilBuilder<T> {

    public T build();

    public String getName();

    public Node getRoot();

}
