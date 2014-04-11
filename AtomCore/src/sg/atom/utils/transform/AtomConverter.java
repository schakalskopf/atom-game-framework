/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.transform;

import com.google.common.base.Converter;

/**
 * Declerative version of Converter.
 * 
 * @author cuong.nguyenmanh2
 *
 * FIXME: Remove this when use Guava 16!
 */
public abstract class AtomConverter<F, T> extends Converter<F, T> {

    public abstract boolean isConvertable(F target);
}
