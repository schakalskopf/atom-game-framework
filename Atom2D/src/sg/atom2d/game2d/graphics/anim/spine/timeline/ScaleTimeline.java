/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.anim.spine.timeline;

import sg.atom.utils.collection.Array;
import sg.atom.utils.math.MathUtils;
import sg.atom2d.game2d.graphics.anim.spine.Animation;
import sg.atom2d.game2d.graphics.anim.spine.event.Event;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Bone;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Skeleton;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ScaleTimeline extends TranslateTimeline {

    public ScaleTimeline(int frameCount) {
        super(frameCount);
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
            bone.scaleX += (bone.data.scaleX - 1 + frames[frames.length - 2] - bone.scaleX) * alpha;
            bone.scaleY += (bone.data.scaleY - 1 + frames[frames.length - 1] - bone.scaleY) * alpha;
            return;
        }
        // Interpolate between the previous frame and the current frame.
        int frameIndex = Animation.binarySearch(frames, time, 3);
        float prevFrameX = frames[frameIndex - 2];
        float prevFrameY = frames[frameIndex - 1];
        float frameTime = frames[frameIndex];
        float percent = MathUtils.clamp(1 - (time - frameTime) / (frames[frameIndex + PREV_FRAME_TIME] - frameTime), 0, 1);
        percent = getCurvePercent(frameIndex / 3 - 1, percent);
        bone.scaleX += (bone.data.scaleX - 1 + prevFrameX + (frames[frameIndex + FRAME_X] - prevFrameX) * percent - bone.scaleX) * alpha;
        bone.scaleY += (bone.data.scaleY - 1 + prevFrameY + (frames[frameIndex + FRAME_Y] - prevFrameY) * percent - bone.scaleY) * alpha;
    }
    
}
