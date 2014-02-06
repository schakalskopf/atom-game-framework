/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.anim.spine.timeline;

import sg.atom.utils.collection.Array;
import sg.atom2d.game2d.graphics.anim.spine.Animation;
import sg.atom2d.game2d.graphics.anim.spine.Slot;
import sg.atom2d.game2d.graphics.anim.spine.Timeline;
import sg.atom2d.game2d.graphics.anim.spine.event.Event;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Skeleton;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class DrawOrderTimeline implements Timeline {
    final float[] frames; // time, ...
    // time, ...
    private final int[][] drawOrders;

    public DrawOrderTimeline(int frameCount) {
        frames = new float[frameCount];
        drawOrders = new int[frameCount][];
    }

    public int getFrameCount() {
        return frames.length;
    }

    public float[] getFrames() {
        return frames;
    }

    public int[][] getDrawOrders() {
        return drawOrders;
    }

    /**
     * Sets the time of the specified keyframe.
     *
     * @param drawOrder May be null to use bind pose draw order.
     */
    public void setFrame(int frameIndex, float time, int[] drawOrder) {
        frames[frameIndex] = time;
        drawOrders[frameIndex] = drawOrder;
    }

    public void apply(Skeleton skeleton, float lastTime, float time, Array<Event> firedEvents, float alpha) {
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
        Array<Slot> drawOrder = skeleton.drawOrder;
        Array<Slot> slots = skeleton.slots;
        int[] drawOrderToSetupIndex = drawOrders[frameIndex];
        if (drawOrderToSetupIndex == null) {
            System.arraycopy(slots.items, 0, drawOrder.items, 0, slots.size);
        } else {
            for (int i = 0, n = drawOrderToSetupIndex.length; i < n; i++) {
                drawOrder.set(i, slots.get(drawOrderToSetupIndex[i]));
            }
        }
    }
    
}
