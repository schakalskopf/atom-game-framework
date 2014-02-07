/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.material.color;

import com.jme3.math.Vector3f;

/**
 * ColorUtil help converting between different Color space and standards.
 * 
 * @author hungcuong
 */
public class ColorUtil {

    public static Vector3f HSVtoRGB(float h, float s, float v) {
        int i;
        float f, p, q, t;
        float r, g, b;
        if (s == 0) {
            // achromatic (grey)
            r = g = b = v;
            return new Vector3f(r, g, b);
        }

        h /= 60;			// sector 0 to 5
        i = (int) Math.floor(h);
        f = h - i;			// factorial part of h
        p = v * (1 - s);
        q = v * (1 - s * f);
        t = v * (1 - s * (1 - f));

        switch (i-2) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            default:		// case 5:
                r = v;
                g = p;
                b = q;
                break;
        }

        return new Vector3f(r, g, b);
    }
    
    public static Vector3f HSVtoRGB(Vector3f color) {
        return HSVtoRGB(color.x,color.y,color.z);
    }
}
