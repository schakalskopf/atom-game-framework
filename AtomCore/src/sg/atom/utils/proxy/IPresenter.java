/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.proxy;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import java.lang.reflect.Proxy;

/**
 * Presenter object, talking advantage of functional facilities in Guava.
 *
 * <p>Presenter object are mixed of proxy pattern and decorator pattern. It's a
 * special pattern for object that heavily decorated, but conceptional similar
 * to another. Decorator concept in Graphics usually refered as Style or Filter,
 * contracted to preserve the basis of the affected. The proxy is "changed
 * version" of the orginal object.
 *
 * <p>This interface assume that this class can be translated to another form
 * without any futher information but a generic convertor or a destination
 * class. It also ask the class to be acted like a proxy to admit the
 * information of the original class. Can be an semi transparent Proxy or full
 * "reflection" Proxy.
 *
 * <p>This also assume that a mixing between few Presenter can procedure a new
 * Presentor.
 *
 * @author cuong.nguyenmanh2
 */
public interface IPresenter<T> {

    public Object as(Function<T, Object> convertor);

    public Object as(Class clazz);

    public T from(Converter<Object, T> convertor);

    public T from(Object object);

    public Proxy asProxy();

    public T mix(T... objects);
}
