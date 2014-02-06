/**
 * ****************************************************************************
 * Spine Runtimes Software License Version 2
 *
 * Copyright (c) 2013, Esoteric Software All rights reserved.
 *
 * You are granted a perpetual, non-exclusive, non-sublicensable and
 * non-transferable license to install, execute and perform the Spine Runtimes
 * Software (the "Software") solely for internal use. Without the written
 * permission of Esoteric Software, you may not (a) modify, translate, adapt or
 * otherwise create derivative works, improvements of the Software or develop
 * new applications using the Software or (b) remove, delete, alter or obscure
 * any trademarks or any copyright, trademark, patent or other intellectual
 * property or proprietary rights notices on or in the Software, including any
 * copy thereof. Redistributions in binary or source form must include this
 * license and terms. THIS SOFTWARE IS PROVIDED BY ESOTERIC SOFTWARE "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ESOTERIC SOFTARE BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ****************************************************************************
 */
package sg.atom2d.game2d.graphics.anim.spine;

import sg.atom2d.game2d.graphics.anim.spine.event.Event;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Skeleton;

import sg.atom.utils.collection.Array;

public class Animation {

    public final String name;
    private final Array<Timeline> timelines;
    private float duration;

    public Animation(String name, Array<Timeline> timelines, float duration) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }
        if (timelines == null) {
            throw new IllegalArgumentException("timelines cannot be null.");
        }
        this.name = name;
        this.timelines = timelines;
        this.duration = duration;
    }

    public Array<Timeline> getTimelines() {
        return timelines;
    }

    /**
     * Returns the duration of the animation in seconds.
     */
    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * Poses the skeleton at the specified time for this animation.
     *
     * @param lastTime The last time the animation was applied.
     * @param events Any triggered events are added.
     */
    public void apply(Skeleton skeleton, float lastTime, float time, boolean loop, Array<Event> events) {
        if (skeleton == null) {
            throw new IllegalArgumentException("skeleton cannot be null.");
        }

        if (loop && duration != 0) {
            time %= duration;
            lastTime %= duration;
        }

        Array<Timeline> timelines = this.timelines;
        for (int i = 0, n = timelines.size; i < n; i++) {
            timelines.get(i).apply(skeleton, lastTime, time, events, 1);
        }
    }

    /**
     * Poses the skeleton at the specified time for this animation mixed with
     * the current pose.
     *
     * @param lastTime The last time the animation was applied.
     * @param events Any triggered events are added.
     * @param alpha The amount of this animation that affects the current pose.
     */
    public void mix(Skeleton skeleton, float lastTime, float time, boolean loop, Array<Event> events, float alpha) {
        if (skeleton == null) {
            throw new IllegalArgumentException("skeleton cannot be null.");
        }

        if (loop && duration != 0) {
            lastTime %= duration;
            time %= duration;
        }

        Array<Timeline> timelines = this.timelines;
        for (int i = 0, n = timelines.size; i < n; i++) {
            timelines.get(i).apply(skeleton, lastTime, time, events, alpha);
        }
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    /**
     * @param target After the first and before the last value.
     * @return index of first value greater than the target.
     */
    public static int binarySearch(float[] values, float target, int step) {
        int low = 0;
        int high = values.length / step - 2;
        if (high == 0) {
            return step;
        }
        int current = high >>> 1;
        while (true) {
            if (values[(current + 1) * step] <= target) {
                low = current + 1;
            } else {
                high = current;
            }
            if (low == high) {
                return (low + 1) * step;
            }
            current = (low + high) >>> 1;
        }
    }

    public static int linearSearch(float[] values, float target, int step) {
        for (int i = 0, last = values.length - step; i <= last; i += step) {
            if (values[i] > target) {
                return i;
            }
        }
        return -1;
    }
}
