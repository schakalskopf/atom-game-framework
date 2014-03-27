/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.timeline;

import sg.atom.fx.tween.Interpolator;

/**
 * Represents a single key frame. This class encapsulates a value and the time
 * fraction in the range [0,1] when that value should occur. In addition, the
 * class holds the interpolator that should be used between the previous key
 * frame and this one. <p> Client code should <i>never</i> mutate values that
 * have been passed into a key frame.
 *
 * @author Tim Halloran
 *
 * @param <T> the type of the values at each frame.
 */
public class Frame<T> {

    /**
     * Indicates that the time fraction when this fame occurs should be
     * calculated, linearly, from the previous and next specified time
     * fractions. <p> {@link KeyFrames.Builder} uses the predicate
     * {@link #getTimeFraction()} <tt>&lt; 0</tt> to note that the user-provided
     * frame does not specify a time fraction.
     */
    private static final double NOT_SET = -1;
    /**
     * The non-{@code null} value of this key frame.
     */
    private final T f_value;
    /**
     * The time fraction in the range [0,1] when that key value defined by this
     * frame should occur, or a negative number.
     */
    private final double f_timeFraction;
    /**
     * The interpolator that should be used between the previous key frame and
     * this one, or {@link null}.
     */
    private final Interpolator f_interpolator;

    /**
     * Constructs a new key frame.
     *
     * @param value the value of this key frame. Client code should <i>never</i>
     * mutate values that have been passed into an instance.
     * @param atTimeFraction the time fraction in the range [0,1] when the value
     * should occur. A negative value indicates, to the
     * {@link KeyFrames.Builder} that this instance is passed to, that the time
     * fraction when this fame occurs should be calculated, linearly, from the
     * previous and next specified time fractions.
     * @param interpolator the interpolator that should be used between the
     * previous key frame and this one. A {@code null} value indicates, to the
     * {@link KeyFrames.Builder} that this instance is passed to, that either
     * the interpolator set with
     * {@link KeyFrames.Builder#setInterpolator(Interpolator)} or the default
     * {@link LinearInterpolator} should be used for this key frame.
     *
     * @throws IllegalArgumentException if <tt>value</tt> is {@code null}.
     */
    public Frame(T value, double atTimeFraction, Interpolator interpolator) {
        if (value == null) {
            throw new IllegalArgumentException("");
        }
        f_value = value;
        f_timeFraction = atTimeFraction;
        f_interpolator = interpolator;
    }

    /**
     * Constructs a new key frame. <p> The {@link KeyFrames.Builder} that this
     * instance is passed to will use either the interpolator set with
     * {@link KeyFrames.Builder#setInterpolator(Interpolator)} or the default
     * {@link LinearInterpolator} to interpolate between the previous key frame
     * and this one.
     *
     * @param value the value of this key frame. Client code should <i>never</i>
     * mutate values that have been passed into an instance.
     * @param atTimeFraction the time fraction in the range [0,1] when the value
     * should occur. A negative value indicates, to the
     * {@link KeyFrames.Builder} that this instance is passed to, that the time
     * fraction when this fame occurs should be calculated, linearly, from the
     * previous and next specified time fractions.
     *
     * @throws IllegalArgumentException if <tt>value</tt> is {@code null}.
     */
    public Frame(T value, double atTimeFraction) {
        this(value, atTimeFraction, null);
    }

    /**
     * Constructs a new key frame. <p> The {@link KeyFrames.Builder} that this
     * instance is passed to will calculate the time fraction when this fame
     * occurs, linearly, from the previous and next specified time fractions.
     *
     * @param value the value of this key frame. Client code should <i>never</i>
     * mutate values that have been passed into an instance.
     * @param interpolator the interpolator that should be used between the
     * previous key frame and this one. A {@code null} value indicates, to the
     * {@link KeyFrames.Builder} that this instance is passed to, that either
     * the interpolator set with
     * {@link KeyFrames.Builder#setInterpolator(Interpolator)} or the default
     * {@link LinearInterpolator} should be used for this key frame.
     *
     * @throws IllegalArgumentException if <tt>value</tt> is {@code null}.
     */
    public Frame(T value, Interpolator interpolator) {
        this(value, NOT_SET, interpolator);
    }

    /**
     * Constructs a new key frame. <p> The {@link KeyFrames.Builder} that this
     * instance is passed to will calculate the time fraction when this fame
     * occurs, linearly, from the previous and next specified time fractions.
     * <p> The {@link KeyFrames.Builder} that this instance is passed to will
     * use either the interpolator set with
     * {@link KeyFrames.Builder#setInterpolator(Interpolator)} or the default
     * {@link LinearInterpolator} to interpolate between the previous key frame
     * and this one.
     *
     * @param value the value of this key frame.
     *
     * @throws IllegalArgumentException if <tt>value</tt> is {@code null}.
     */
    public Frame(T value) {
        this(value, NOT_SET, null);
    }

    /**
     * The value of this key frame. <p> The returned value will never be
     * {@code null}.
     *
     * @return a value.
     */
    public T getValue() {
        return f_value;
    }

    /**
     * The time fraction in the range [0,1] when that key value defined by this
     * frame should occur. <p> The time fraction can be negative in the case
     * that this instance was created by client code to be passed to
     * {@link KeyFrames.Builder#addFrame(KeyFrames.Frame)} and a constructor
     * that does not set the time fraction was called. A negative value
     * indicates to the {@link KeyFrames.Builder} that the time fraction should
     * be calculated, linearly, from the previous and next specified time
     * fractions.
     *
     * @return a time fraction in the range [0,1], or a negative number.
     */
    public double getTimeFraction() {
        return f_timeFraction;
    }

    /**
     * Gets the interpolator that should be used between the previous key frame
     * and this one. <p> The return value may be {@code null} in two cases: <ul>
     * <li>If this instance was obtained from a {@link KeyFrames} instance,
     * either through iteration or {@link KeyFrames#getFrame(int)}, then a
     * {@code null} value indicates that this is the first frame. Because the
     * interpolator is used to interpolate between the previous frame and this
     * one&mdash;the first frame has no intepolator.</li> <li>If this instance
     * was created by client code to be passed to
     * {@link KeyFrames.Builder#addFrame(KeyFrames.Frame)} then a {@code null}
     * value indicates that either the interpolator set with
     * {@link KeyFrames.Builder#setInterpolator(Interpolator)} or the default
     * {@link LinearInterpolator} should be used for this key frame.</li> </ul>
     *
     * @return an interpolator, or {@code null}.
     */
    public Interpolator getInterpolator() {
        return f_interpolator;
    }
}
