/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.functional;

import com.google.common.base.Function;
import java.util.List;
import sg.atom.fx.tween.Interpolator;

/**
 * InterpolatorFunction.
 *
 * Function define how to a variable should change from one value to another.
 *
 * Inspired by:
 * http://blog.gemserk.com/2011/03/26/animation4j-interpolation-functions/
 *
 * @author cuong.nguyenmanh2
 */
public abstract class InterpolatorFunction<V> implements Function<Float, V>, Interpolator<V> {

    protected List<V> source;

    public InterpolatorFunction(List<V> source) {
        this.source = source;
    }

    public void setSource(List<V> source) {
        this.source = source;
    }

    public List<V> getSource() {
        return source;
    }

}
