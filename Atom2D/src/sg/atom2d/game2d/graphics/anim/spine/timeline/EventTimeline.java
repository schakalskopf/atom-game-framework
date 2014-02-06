/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.anim.spine.timeline;

import sg.atom.utils.collection.Array;
import sg.atom2d.game2d.graphics.anim.spine.Animation;
import sg.atom2d.game2d.graphics.anim.spine.Timeline;
import sg.atom2d.game2d.graphics.anim.spine.event.Event;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Skeleton;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EventTimeline implements Timeline {
    final float[] frames; // time, ...
    // time, ...
    private final Event[] events;

    public EventTimeline(int frameCount) {
        frames = new float[frameCount];
        events = new Event[frameCount];
    }

    public int getFrameCount() {
        return frames.length;
    }

    public float[] getFrames() {
        return frames;
    }

    public Event[] getEvents() {
        return events;
    }

    /**
     * Sets the time of the specified keyframe.
     */
    public void setFrame(int frameIndex, float time, Event event) {
        frames[frameIndex] = time;
        events[frameIndex] = event;
    }

    /**
     * Fires events for frames > lastTime and <= time.
     */
    public void apply(Skeleton skeleton, float lastTime, float time, Array<Event> firedEvents, float alpha) {
        if (firedEvents == null) {
            return;
        }
        float[] frames = this.frames;
        int frameCount = frames.length;
        if (lastTime > time) {
            // Fire events after last time for looped animations.
            apply(skeleton, lastTime, Integer.MAX_VALUE, firedEvents, alpha);
            lastTime = -1f;
        } else if (lastTime >= frames[frameCount - 1]) {
            return;
        }
        if (time < frames[0]) {
            return; // Time is before first frame.
            // Time is before first frame.
        }
        int frameIndex;
        if (lastTime < frames[0]) {
            frameIndex = 0;
        } else {
            frameIndex = Animation.binarySearch(frames, lastTime, 1);
            float frame = frames[frameIndex];
            while (frameIndex > 0) {
                // Fire multiple events with the same frame.
                if (frames[frameIndex - 1] != frame) {
                    break;
                }
                frameIndex--;
            }
        }
        for (; frameIndex < frameCount && time >= frames[frameIndex]; frameIndex++) {
            firedEvents.add(events[frameIndex]);
        }
    }
    
}
