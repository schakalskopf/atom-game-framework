/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.services.nifty.effects;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import de.lessvoid.nifty.spi.time.TimeProvider;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MotionAxis implements EffectImpl {

    private float distance;
    private boolean global;
    private String axis;
    private String type;
    private float startTime;
    private TimeProvider timer;

    public void activate(final Nifty nifty, final Element element, final EffectProperties parameter) {
        distance = Float.parseFloat(parameter.getProperty("distance", "10.0"));
        axis = parameter.getProperty("axis", "none");
        type = parameter.getProperty("motionType", "shake");
        global = "true".equals(parameter.getProperty("global", "true").toLowerCase());
        timer = nifty.getTimeProvider();
        startTime = timer.getMsTime();
    }

    public void execute(
            final Element element,
            final float normalizedTime,
            final Falloff falloff,
            final NiftyRenderEngine r) {

        float t = (timer.getMsTime() - startTime) / 1000;

        float x = 0, y = 0;


        float d = distance;

        if (type.equalsIgnoreCase("shake")) {

            if (axis.equalsIgnoreCase("X")) {
                x = -d + (float) Math.random() * 2 * d;
            } else if (axis.equalsIgnoreCase("Y")) {
                y = -d + (float) Math.random() * 2 * d;
            } else {
                x = -d + (float) Math.random() * 2 * d;
                y = -d + (float) Math.random() * 2 * d;
            }

        } else if (type.equalsIgnoreCase("sin")) {
            if (axis.equalsIgnoreCase("X")) {
                x = -d + (float) Math.sin(t) * 2 * d;
            } else if (axis.equalsIgnoreCase("Y")) {
                y = -d + (float) Math.sin(t) * 2 * d;
            } else {
                x = -d + (float) Math.sin(t) * 2 * d;
                y = -d + (float) Math.sin(t) * 2 * d;
            }
        } else if (type.equalsIgnoreCase("cos")) {
            if (axis.equalsIgnoreCase("X")) {
                x = -d + (float) Math.cos(t) * 2 * d;
            } else if (axis.equalsIgnoreCase("Y")) {
                y = -d + (float) Math.cos(t) * 2 * d;
            } else {
                x = -d + (float) Math.cos(t) * 2 * d;
                y = -d + (float) Math.cos(t) * 2 * d;
            }
        }

        /*
         if (t >= 0.99f) {
         x = 0;
         y = 0;
         }
         */
        System.out.println(" " + t);

        if (global) {
            r.setGlobalPosition(x, y);
        } else {
            r.moveTo(x, y);
        }

    }

    public void deactivate() {
    }
}
