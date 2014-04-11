/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

import com.google.common.base.Converter;
import com.google.common.base.Function;

/**
 * Edit as function, which is inversable. That mean by envolve the resulted
 * object with the memeto again in forward or backward direction, it return
 * original. http://en.wikipedia.org/wiki/Inverse_function
 *
 * @author CuongNguyen
 */
public abstract class FunctionMemetoEdit<T> extends Converter<T, T> implements IMemetoEdit<T> {

    protected FunctionMemeto<T> savedMemeto;

    @Override
    protected T doForward(T input) {
        //savedMemeto.from(this, generateMemeto(input));
        return input;
    }

    @Override
    protected T doBackward(T b) {
        return (T) savedMemeto.from(this, savedMemeto).getSavedInfo();
    }
}
