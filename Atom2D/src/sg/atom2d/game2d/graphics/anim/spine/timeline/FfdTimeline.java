/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.anim.spine.timeline;

import sg.atom.utils.collection.Array;
import sg.atom.utils.collection.FloatArray;
import sg.atom.utils.math.MathUtils;
import sg.atom2d.game2d.graphics.anim.spine.Animation;
import sg.atom2d.game2d.graphics.anim.spine.Slot;
import sg.atom2d.game2d.graphics.anim.spine.attachments.MeshAttachment;
import sg.atom2d.game2d.graphics.anim.spine.event.Event;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Skeleton;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FfdTimeline extends CurveTimeline {
    final float[] frames; // time, ...
    // time, ...
    final float[][] frameVertices;
    public int slotIndex;
    public MeshAttachment meshAttachment;

    public FfdTimeline(int frameCount) {
        super(frameCount);
        frames = new float[frameCount];
        frameVertices = new float[frameCount][];
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setMeshAttachment(MeshAttachment attachment) {
        this.meshAttachment = attachment;
    }

    public MeshAttachment getMeshAttachment() {
        return meshAttachment;
    }

    public float[] getFrames() {
        return frames;
    }

    public float[][] getVertices() {
        return frameVertices;
    }

    /**
     * Sets the time of the specified keyframe.
     */
    public void setFrame(int frameIndex, float time, float[] vertices) {
        frames[frameIndex] = time;
        frameVertices[frameIndex] = vertices;
    }

    public void apply(Skeleton skeleton, float lastTime, float time, Array<Event> firedEvents, float alpha) {
        Slot slot = skeleton.slots.get(slotIndex);
        if (slot.getAttachment() != meshAttachment) {
            return;
        }
        FloatArray verticesArray = slot.getAttachmentVertices();
        verticesArray.size = 0;
        float[] frames = this.frames;
        if (time < frames[0]) {
            return; // Time is before first frame.
            // Time is before first frame.
        }
        float[][] frameVertices = this.frameVertices;
        int vertexCount = frameVertices[0].length;
        verticesArray.ensureCapacity(vertexCount);
        verticesArray.size = vertexCount;
        float[] vertices = verticesArray.items;
        if (time >= frames[frames.length - 1]) {
            // Time is after last frame.
            System.arraycopy(frameVertices[frames.length - 1], 0, vertices, 0, vertexCount);
            return;
        }
        // Interpolate between the previous frame and the current frame.
        int frameIndex = Animation.binarySearch(frames, time, 1);
        float frameTime = frames[frameIndex];
        float percent = MathUtils.clamp(1 - (time - frameTime) / (frames[frameIndex - 1] - frameTime), 0, 1);
        percent = getCurvePercent(frameIndex - 1, percent);
        float[] prevVertices = frameVertices[frameIndex - 1];
        float[] nextVertices = frameVertices[frameIndex];
        // BOZO - FFD, use alpha for mixing?
        for (int i = 0; i < vertexCount; i++) {
            float prev = prevVertices[i];
            vertices[i] = prev + (nextVertices[i] - prev) * percent;
        }
    }
    
}
