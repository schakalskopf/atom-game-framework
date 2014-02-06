/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.anim.spine.timeline;

import sg.atom2d.game2d.graphics.anim.spine.Timeline;

/**
 * Base class for frames that use an interpolation bezier curve.
 */
public abstract class CurveTimeline implements Timeline {
    public static final float LINEAR = 0;
    public static final float STEPPED = -1;
    public static final float BEZIER = -2;
    private static final int BEZIER_SEGMENTS = 10;
    final float[] curves; // dfx, dfy, ddfx, ddfy, dddfx, dddfy, ...
    // dfx, dfy, ddfx, ddfy, dddfx, dddfy, ...

    public CurveTimeline(int frameCount) {
        curves = new float[(frameCount - 1) * 6];
    }

    public int getFrameCount() {
        return curves.length / 6 + 1;
    }

    public void setLinear(int frameIndex) {
        curves[frameIndex * 6] = LINEAR;
    }

    public void setStepped(int frameIndex) {
        curves[frameIndex * 6] = STEPPED;
    }

    public float getCurveType(int frameIndex) {
        int index = frameIndex * 6;
        if (index == curves.length) {
            return LINEAR;
        }
        float type = curves[index];
        if (type == LINEAR) {
            return LINEAR;
        }
        if (type == STEPPED) {
            return STEPPED;
        }
        return BEZIER;
    }

    /**
     * Sets the control handle positions for an interpolation bezier curve
     * used to transition from this keyframe to the next. cx1 and cx2 are
     * from 0 to 1, representing the percent of time between the two
     * keyframes. cy1 and cy2 are the percent of the difference between the
     * keyframe's values.
     */
    public void setCurve(int frameIndex, float cx1, float cy1, float cx2, float cy2) {
        float subdiv_step = 1f / BEZIER_SEGMENTS;
        float subdiv_step2 = subdiv_step * subdiv_step;
        float subdiv_step3 = subdiv_step2 * subdiv_step;
        float pre1 = 3 * subdiv_step;
        float pre2 = 3 * subdiv_step2;
        float pre4 = 6 * subdiv_step2;
        float pre5 = 6 * subdiv_step3;
        float tmp1x = -cx1 * 2 + cx2;
        float tmp1y = -cy1 * 2 + cy2;
        float tmp2x = (cx1 - cx2) * 3 + 1;
        float tmp2y = (cy1 - cy2) * 3 + 1;
        int i = frameIndex * 6;
        float[] curves = this.curves;
        curves[i] = cx1 * pre1 + tmp1x * pre2 + tmp2x * subdiv_step3;
        curves[i + 1] = cy1 * pre1 + tmp1y * pre2 + tmp2y * subdiv_step3;
        curves[i + 2] = tmp1x * pre4 + tmp2x * pre5;
        curves[i + 3] = tmp1y * pre4 + tmp2y * pre5;
        curves[i + 4] = tmp2x * pre5;
        curves[i + 5] = tmp2y * pre5;
    }

    public float getCurvePercent(int frameIndex, float percent) {
        int curveIndex = frameIndex * 6;
        float[] curves = this.curves;
        float dfx = curves[curveIndex];
        if (dfx == LINEAR) {
            return percent;
        }
        if (dfx == STEPPED) {
            return 0;
        }
        float dfy = curves[curveIndex + 1];
        float ddfx = curves[curveIndex + 2];
        float ddfy = curves[curveIndex + 3];
        float dddfx = curves[curveIndex + 4];
        float dddfy = curves[curveIndex + 5];
        float x = dfx;
        float y = dfy;
        int i = BEZIER_SEGMENTS - 2;
        while (true) {
            if (x >= percent) {
                float prevX = x - dfx;
                float prevY = y - dfy;
                return prevY + (y - prevY) * (percent - prevX) / (x - prevX);
            }
            if (i == 0) {
                break;
            }
            i--;
            dfx += ddfx;
            dfy += ddfy;
            ddfx += dddfx;
            ddfy += dddfy;
            x += dfx;
            y += dfy;
        }
        return y + (1 - y) * (percent - x) / (1 - x); // Last point is 1,1.
        // Last point is 1,1.
    }
    
}
