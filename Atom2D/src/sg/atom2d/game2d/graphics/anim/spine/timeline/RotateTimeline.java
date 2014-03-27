/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.anim.spine.timeline;

import sg.atom.utils.collection.Array;
import sg.atom.utils.math.AtomFastMath;
import sg.atom2d.game2d.graphics.anim.spine.Animation;
import sg.atom2d.game2d.graphics.anim.spine.event.Event;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Bone;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Skeleton;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RotateTimeline extends CurveTimeline {
    private static final int PREV_FRAME_TIME = -2;
    private static final int FRAME_VALUE = 1;
    public int boneIndex;
    final float[] frames; // time, angle, ...
    // time, angle, ...

    public RotateTimeline(int frameCount) {
        super(frameCount);
        frames = new float[frameCount * 2];
    }

    public void setBoneIndex(int boneIndex) {
        this.boneIndex = boneIndex;
    }

    public int getBoneIndex() {
        return boneIndex;
    }

    public float[] getFrames() {
        return frames;
    }

    /**
     * Sets the time and angle of the specified keyframe.
     */
    public void setFrame(int frameIndex, float time, float angle) {
        frameIndex *= 2;
        frames[frameIndex] = time;
        frames[frameIndex + 1] = angle;
    }

    public void apply(Skeleton skeleton, float lastTime, float time, Array<Event> events, float alpha) {
        float[] frames = this.frames;
        if (time < frames[0]) {
            return; // Time is before first frame.
            // Time is before first frame.
        }
        Bone bone = skeleton.bones.get(boneIndex);
        if (time >= frames[frames.length - 2]) {
            // Time is after last frame.
            float amount = bone.data.rotation + frames[frames.length - 1] - bone.rotation;
            while (amount > 180) {
                amount -= 360;
            }
            while (amount < -180) {
                amount += 360;
            }
            bone.rotation += amount * alpha;
            return;
        }
        // Interpolate between the previous frame and the current frame.
        int frameIndex = Animation.binarySearch(frames, time, 2);
        float prevFrameValue = frames[frameIndex - 1];
        float frameTime = frames[frameIndex];
        float percent = AtomFastMath.clamp(1 - (time - frameTime) / (frames[frameIndex + PREV_FRAME_TIME] - frameTime), 0, 1);
        percent = getCurvePercent(frameIndex / 2 - 1, percent);
        float amount = frames[frameIndex + FRAME_VALUE] - prevFrameValue;
        while (amount > 180) {
            amount -= 360;
        }
        while (amount < -180) {
            amount += 360;
        }
        amount = bone.data.rotation + (prevFrameValue + amount * percent) - bone.rotation;
        while (amount > 180) {
            amount -= 360;
        }
        while (amount < -180) {
            amount += 360;
        }
        bone.rotation += amount * alpha;
    }
    
}
