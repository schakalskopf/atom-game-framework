/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import rx.Observable;
import sg.atom.gameplay.player.Player;

/**
 * A named group (Collection) of players, ultilty for using player management.
 * Search,filters etc. 
 * 
 * <p>This use guava immutable collection to have defensive view of player.
 * 
 * <p>Since Atom 1.1, A GameGroup is reactive by default.
 *
 * @author atomix
 */
public class GameGroup<PLAYER extends Player> implements Iterable<PLAYER>, Cloneable, Collection<PLAYER> {

    public Integer id;
    public String name;
    public ArrayList<PLAYER> members;

    @Override
    public Iterator<PLAYER> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean add(PLAYER e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addAll(Collection<? extends PLAYER> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<PLAYER> getMembers() {
        return members;
    }
    
    
}
