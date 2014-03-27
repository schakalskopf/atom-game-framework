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
public class TranslateTimeline extends CurveTimeline {
    static final int PREV_FRAME_TIME = -3;
    static final int FRAME_X = 1;
    static final int FRAME_Y = 2;
    public int boneIndex;
    final float[] frames; // time, x, y, ...
    // time, x, y, ...

    public TranslateTimeline(int frameCount) {
        super(frameCount);
        frames = new float[frameCount * 3];
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
     * Sets the time and value of the specified keyframe.
     */
    public void setFrame(int frameIndex, float time, float x, float y) {
        frameIndex *= 3;
        frames[frameIndex] = time;
        frames[frameIndex + 1] = x;
        frames[frameIndex + 2] = y;
    }

    public void apply(Skeleton skeleton, float lastTime, float time, Array<Event> events, float alpha) {
        float[] frames = this.frames;
        if (time < frames[0]) {
            return; // Time is before first frame.
            // Time is before first frame.
        }
        Bone bone = skeleton.bones.get(boneIndex);
        if (time >= frames[frames.length - 3]) {
            // Time is after last frame.
            bone.x += (bone.data.x + frames[frames.length - 2] - bone.x) * alpha;
            bone.y += (bone.data.y + frames[frames.length - 1] - bone.y) * alpha;
            return;
        }
        // Interpolate between the previous frame and the current frame.
        int frameIndex = Animation.binarySearch(frames, time, 3);
        float prevFrameX = frames[frameIndex - 2];
        float prevFrameY = frames[frameIndex - 1];
        float frameTime = frames[frameIndex];
        float percent = AtomFastMath.clamp(1 - (time - frameTime) / (frames[frameIndex + PREV_FRAME_TIME] - frameTime), 0, 1);
        percent = getCurvePercent(frameIndex / 3 - 1, percent);
        bone.x += (bone.data.x + prevFrameX + (frames[frameIndex + FRAME_X] - prevFrameX) * percent - bone.x) * alpha;
        bone.y += (bone.data.y + prevFrameY + (frames[frameIndex + FRAME_Y] - prevFrameY) * percent - bone.y) * alpha;
    }
    
}
