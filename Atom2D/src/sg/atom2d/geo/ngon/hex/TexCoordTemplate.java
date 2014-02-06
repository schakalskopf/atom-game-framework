/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.ngon.hex;

import com.jme3.math.Vector2f;
import java.util.ArrayList;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TexCoordTemplate {

    int index;
    float[] values;
    int num;
    int type;

    public TexCoordTemplate(int index, float[] values) {
        this.index = index;
        this.values = values;
        this.num = values.length;
    }

    public TexCoordTemplate(int index, int num) {
        this.values = new float[num];
        this.num = num;
    }

    public void addCoord(float... fValues) {
        //Arrays.
    }

    public void transform(int verticleIndex) {
        ArrayList<Vector2f> result;

    }
}
