/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.transform;

/**
 *
 * @author cuong.nguyenmanh2
 *
 * FIXME: Remove this when use Guava 16!
 */
public interface IConverter<F, T> {

    public T convertoTo(F from);

    public boolean isConvertable(F target);
}
