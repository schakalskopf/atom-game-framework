package sg.atom.corex.scenegraph.shape;

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
