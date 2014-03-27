/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface FunctionMemeto<T> extends IMemeto<T> {

    public IMemeto<T> from(FunctionMemetoEdit<T> edit,IMemeto<T> wrapedMemeto);
}
