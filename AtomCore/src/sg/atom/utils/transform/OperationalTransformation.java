/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.transform;

/**
 * The basic idea of OT is to transform (or adjust) the parameters of an editing
 * operation according to the effects of previously executed concurrent
 * operations so that the transformed operation can achieve the correct effect
 * and maintain document consistency.
 *
 * <p>The result of the transformation so call the axis of action.
 *
 * <p>AOP method intercept leverage OT even more by automaticly adjust method in
 * Java level.
 *
 * @author cuong.nguyenmanh2
 */
public interface OperationalTransformation {
}
