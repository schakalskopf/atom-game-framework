/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.anim.spine.timeline;

import com.jme3.math.ColorRGBA;
import sg.atom.utils.collection.Array;
import sg.atom.utils.math.AtomFastMath;
import sg.atom2d.game2d.graphics.anim.spine.Animation;
import sg.atom2d.game2d.graphics.anim.spine.event.Event;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Skeleton;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ColorTimeline extends CurveTimeline {
    private static final int PREV_FRAME_TIME = -5;
    private static final int FRAME_R = 1;
    private static final int FRAME_G = 2;
    private static final int FRAME_B = 3;
    private static final int FRAME_A = 4;
    public int slotIndex;
    final float[] frames; // time, r, g, b, a, ...
    // time, r, g, b, a, ...

    public ColorTimeline(int frameCount) {
        super(frameCount);
        frames = new float[frameCount * 5];
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public float[] getFrames() {
        return frames;
    }

    /**
     * Sets the time and value of the specified keyframe.
     */
    public void setFrame(int frameIndex, float time, float r, float g, float b, float a) {
        frameIndex *= 5;
        frames[frameIndex] = time;
        frames[frameIndex + 1] = r;
        frames[frameIndex + 2] = g;
        frames[frameIndex + 3] = b;
        frames[frameIndex + 4] = a;
    }

    public void apply(Skeleton skeleton, float lastTime, float time, Array<Event> events, float alpha) {
        float[] frames = this.frames;
        if (time < frames[0]) {
            return; // Time is before first frame.
            // Time is before first frame.
        }
        ColorRGBA color = skeleton.slots.get(slotIndex).color;
        if (time >= frames[frames.length - 5]) {
            // Time is after last frame.
            int i = frames.length - 1;
            float r = frames[i - 3];
            float g = frames[i - 2];
            float b = frames[i - 1];
            float a = frames[i];
            color.set(r, g, b, a);
            return;
        }
        // Interpolate between the previous frame and the current frame.
        int frameIndex = Animation.binarySearch(frames, time, 5);
        float prevFrameR = frames[frameIndex - 4];
        float prevFrameG = frames[frameIndex - 3];
        float prevFrameB = frames[frameIndex - 2];
        float prevFrameA = frames[frameIndex - 1];
        float frameTime = frames[frameIndex];
        float percent = AtomFastMath.clamp(1 - (time - frameTime) / (frames[frameIndex + PREV_FRAME_TIME] - frameTime), 0, 1);
        percent = getCurvePercent(frameIndex / 5 - 1, percent);
        float r = prevFrameR + (frames[frameIndex + FRAME_R] - prevFrameR) * percent;
        float g = prevFrameG + (frames[frameIndex + FRAME_G] - prevFrameG) * percent;
        float b = prevFrameB + (frames[frameIndex + FRAME_B] - prevFrameB) * percent;
        float a = prevFrameA + (frames[frameIndex + FRAME_A] - prevFrameA) * percent;
        if (alpha < 1) {
            color.add(new ColorRGBA((r - color.r) * alpha, (g - color.g) * alpha, (b - color.b) * alpha, (a - color.a) * alpha));
        } else {
            color.set(r, g, b, a);
        }
    }
    
}
