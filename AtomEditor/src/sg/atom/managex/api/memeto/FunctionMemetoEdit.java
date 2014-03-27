/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

import com.google.common.base.Function;

/**
 * Edit as function, which is inversable. That mean by envolve the resulted
 * object with the memeto again in forward or backward direction, it return
 * original. http://en.wikipedia.org/wiki/Inverse_function
 *
 * @author CuongNguyen
 */
public abstract class FunctionMemetoEdit<T> implements IMemetoEdit<T>, Function<T, T> {

    protected FunctionMemeto<T> savedMemeto;

    public T apply(T input) {
        savedMemeto.from(this, generateMemeto(input));
        return input;
    }

    public abstract Function<T, T> getInverseFunction();
}
