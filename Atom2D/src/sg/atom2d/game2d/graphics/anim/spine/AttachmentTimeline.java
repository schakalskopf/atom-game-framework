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
public class AttachmentTimeline implements Timeline {
    public int slotIndex;
    final float[] frames; // time, ...
    // time, ...
    private final String[] attachmentNames;

    public AttachmentTimeline(int frameCount) {
        frames = new float[frameCount];
        attachmentNames = new String[frameCount];
    }

    public int getFrameCount() {
        return frames.length;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public float[] getFrames() {
        return frames;
    }

    public String[] getAttachmentNames() {
        return attachmentNames;
    }

    /**
     * Sets the time and value of the specified keyframe.
     */
    public void setFrame(int frameIndex, float time, String attachmentName) {
        frames[frameIndex] = time;
        attachmentNames[frameIndex] = attachmentName;
    }

    public void apply(Skeleton skeleton, float lastTime, float time, Array<Event> events, float alpha) {
        float[] frames = this.frames;
        if (time < frames[0]) {
            return; // Time is before first frame.
            // Time is before first frame.
        }
        int frameIndex;
        if (time >= frames[frames.length - 1]) {
            frameIndex = frames.length - 1;
        } else {
            frameIndex = Animation.binarySearch(frames, time, 1) - 1;
        }
        String attachmentName = attachmentNames[frameIndex];
        skeleton.slots.get(slotIndex).setAttachment(attachmentName == null ? null : skeleton.getAttachment(slotIndex, attachmentName));
    }
    
}
