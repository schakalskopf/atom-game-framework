package sg.atom.core.monitor;

/**
 * Liner progress ultilities. Provide basis for Monitoring framework.
 *
 * <p>start -> end As the progress of ongoing value. addProgress () is called,
 * if the value is not applied immediately update () is called as Depending fast
 * initially, increases slowly when it reaches the target. The end value is
 * equal to ground targets, maxSpeed ​​proceed to.
 *
 * FIXME: This class attributes should be replaced with Atomic* classes. And
 * Guava RateLimiter
 *
 * @author mulova, atomix
 */
public class AtomicProgress implements IProgress {

    public static enum ProgressState {

        NOT_YET, IN_PROGRESS, DONE, ERROR
    }
    // Replace with Atomic*
    private static float TOLERANCE = 0.0001f;
    private float speed = 0.2f;
    private float start = 0f; // start value
    private float end = 1f; // middle value
    private float current; // update the value advanced. not rise more than progress.
    private float progress = 0f; // start-> end
    private float maxSpeed = 1f;
    private String activity;
    private float goal = 1f; // target value

    public AtomicProgress() {
    }

    @Override
    public void addProgress(float toAdd) {
        addProgress(toAdd, null);
    }

    @Override
    public void addProgress(float rate, final String activity) {
        this.progress += rate;
        this.activity = activity;
        if (progress > end) {
            progress = end;
        }
    }

    public boolean isDone() {
        return current >= end;
    }

    /**
     * getDelta
     *
     * @return
     * @author mulova
     */
    public float getDelta() {
        return progress - current;
    }

    /**
     * get current overal progress
     */
    public float getCurrent() {
        return current;
    }

    /**
     */
    public float getEnd() {
        return end;
    }

    /**
     *
     * @return
     */
    @Override
    public float getProgress() {
        return progress;
    }

    @Override
    public float increment() {
        return 0;
    }

    @Override
    public float increment(final int steps) {
        return 0;
    }

    @Override
    public float increment(final int steps, final String activity) {
        return 0;
    }

    @Override
    public float increment(final String activity) {
        return 0;
    }

    public void reset() {
        this.progress = start;
        this.current = start;
    }

    /**
     */
    public void setEnd(float max) {
        if (max < 0) {
            return;
        }
        if (max < current) {
            max = current;
        }
        start = current;
        end = max;
    }

    public void setRange(float start, float end) {
        this.start = start;
        this.end = end;
        reset();
    }

    /**
     *
     * @param value
     */
    public void setNow(float value) {
        this.current = value;
        this.progress = value;
    }

    /**
     */
    @Override
    public void setProgress(float rate) {
        setProgress(rate, null);
    }

    /**
     *
     * @see com.jmex.game.state.load.Loader#setProgress(float, java.lang.String)
     */
    @Override
    public void setProgress(float rate, final String activity) {
        rate = (end - start) * rate + start;
        if (rate > progress) {
            progress = rate;
        }
    }

    /**
     *
     * @param speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
        if (maxSpeed < speed) {
            maxSpeed = speed;
        }
    }

    /**
     */
    public void update(float time) {
        if (current >= progress) {
            return;
        }

        if (progress >= goal) {
            current += (goal - start) * time * maxSpeed;
        } else {
            float delta = getDelta() * speed;
            current += delta * time;
        }

        if (end - current <= TOLERANCE) {
            current = end;
            progress = end;
        }
    }

    /**
     * @author mulova
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
