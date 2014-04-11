/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface IMemetoOrginator<T> {

    public IMemeto<T> generateMemeto(T object);
}
