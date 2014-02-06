/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.timing;

/**
 * Interface for real-time object who change values overtime.
 *
 * @author cuong.nguyenmanh2
 */
public interface TimeValueProvider<T>{

    public T getValueAtTime(float t);
}
