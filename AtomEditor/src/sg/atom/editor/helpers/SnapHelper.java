/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.helpers;

import com.jme3.math.Line;
import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 * Snap is a common function is CCD program.
 *
 * <p>Under a certain distance, the virtual point is snapped to target point.
 * The idea is the same with line to line, except in line, a rotation also
 * affected.
 *
 *
 * @author CuongNguyen
 */
public class SnapHelper {
    ArrayList<Vector3f> suggestedPoints;
    ArrayList suggestions;
    
    public void pointToPoint(Vector3f point, Vector3f target) {
    }

    public void lineToLine(Line line, Line targetLine) {
    }
}
