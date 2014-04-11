/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils._beta.functional;

import com.google.common.base.Function;

/**
 * Stand for the side effect just as in Jedi's Command<T> or Functional Javas's Effect<T>.
 * 
 * Read this:
 *  http://stackoverflow.com/questions/9525951/anything-in-guava-similar-to-functional-javas-effect
 * @author cuong.nguyenmanh2
 */
public interface IEffect<T> {
    public T execute(T target);
    
    public Function<T,T> getFunction();
}
