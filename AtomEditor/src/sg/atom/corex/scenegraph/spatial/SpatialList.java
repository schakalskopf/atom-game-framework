/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.spatial;

import sg.atom.managex.api.select.Selectable;
import com.jme3.scene.Spatial;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * SpatialList is a List of SpatialInfo to make grouping of SpatialInfo much
 * more easy.
 *
 * <p>It also support List,Set functions.
 *
 * @author hungcuong
 */
public class SpatialList implements Selectable, List<Spatial>, Set<Spatial> {

    private List<EditorSpatialInfo> list = new LinkedList<EditorSpatialInfo>();

    /**
     * @return the list
     */
    public List<EditorSpatialInfo> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<EditorSpatialInfo> list) {
        this.list = list;
    }

    public void add(EditorSpatialInfo spatialInfo) {
        list.add(spatialInfo);
    }

    public void addList(SpatialList otherList) {
        list.addAll(otherList.getList());
    }

    public Spatial getRoot() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SpatialList getSelectList() {
        return this;
    }

    public boolean isSelected() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isSelectable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void highlight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator<Spatial> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(Spatial e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(Collection<? extends Spatial> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(int index, Collection<? extends Spatial> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Spatial get(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Spatial set(int index, Spatial element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(int index, Spatial element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Spatial remove(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<Spatial> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<Spatial> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Spatial> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
