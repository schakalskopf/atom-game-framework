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
public class ColorUtils {

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
    
    
    /**
     * This method converts rgb values to hsv values.
     * 
     * @param r
     *            red value of the color
     * @param g
     *            green value of the color
     * @param b
     *            blue value of the color
     * @param hsv
     *            hsv values of a color (this table contains the result of the transformation)
     */
    public void rgbToHsv(float r, float g, float b, float[] hsv) {
        float cmax = r;
        float cmin = r;
        cmax = g > cmax ? g : cmax;
        cmin = g < cmin ? g : cmin;
        cmax = b > cmax ? b : cmax;
        cmin = b < cmin ? b : cmin;

        hsv[2] = cmax; /* value */
        if (cmax != 0.0) {
            hsv[1] = (cmax - cmin) / cmax;
        } else {
            hsv[1] = 0.0f;
            hsv[0] = 0.0f;
        }
        if (hsv[1] == 0.0) {
            hsv[0] = -1.0f;
        } else {
            float cdelta = cmax - cmin;
            float rc = (cmax - r) / cdelta;
            float gc = (cmax - g) / cdelta;
            float bc = (cmax - b) / cdelta;
            if (r == cmax) {
                hsv[0] = bc - gc;
            } else if (g == cmax) {
                hsv[0] = 2.0f + rc - bc;
            } else {
                hsv[0] = 4.0f + gc - rc;
            }
            hsv[0] *= 60.0f;
            if (hsv[0] < 0.0f) {
                hsv[0] += 360.0f;
            }
        }

        hsv[0] /= 360.0f;
        if (hsv[0] < 0.0f) {
            hsv[0] = 0.0f;
        }
    }

    /**
     * This method converts rgb values to hsv values.
     * 
     * @param h
     *            hue
     * @param s
     *            saturation
     * @param v
     *            value
     * @param rgb
     *            rgb result vector (should have 3 elements)
     */
    public void hsvToRgb(float h, float s, float v, float[] rgb) {
        h *= 360.0f;
        if (s == 0.0) {
            rgb[0] = rgb[1] = rgb[2] = v;
        } else {
            if (h == 360) {
                h = 0;
            } else {
                h /= 60;
            }
            int i = (int) Math.floor(h);
            float f = h - i;
            float p = v * (1.0f - s);
            float q = v * (1.0f - s * f);
            float t = v * (1.0f - s * (1.0f - f));
            switch (i) {
                case 0:
                    rgb[0] = v;
                    rgb[1] = t;
                    rgb[2] = p;
                    break;
                case 1:
                    rgb[0] = q;
                    rgb[1] = v;
                    rgb[2] = p;
                    break;
                case 2:
                    rgb[0] = p;
                    rgb[1] = v;
                    rgb[2] = t;
                    break;
                case 3:
                    rgb[0] = p;
                    rgb[1] = q;
                    rgb[2] = v;
                    break;
                case 4:
                    rgb[0] = t;
                    rgb[1] = p;
                    rgb[2] = v;
                    break;
                case 5:
                    rgb[0] = v;
                    rgb[1] = p;
                    rgb[2] = q;
                    break;
            }
        }
    }
}
