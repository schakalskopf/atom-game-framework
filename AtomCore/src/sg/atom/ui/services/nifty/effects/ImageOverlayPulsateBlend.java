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
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import de.lessvoid.nifty.render.image.ImageModeFactory;
import de.lessvoid.nifty.render.image.ImageModeHelper;
import de.lessvoid.nifty.tools.pulsate.Pulsator;

/**
 * ImagePulsate - image color pulsate.
 *
 * @author cuong.nguyenmanh2
 */
public class ImageOverlayPulsateBlend implements EffectImpl {

    de.lessvoid.nifty.render.BlendMode blendMode = null;
    private NiftyImage image;
    private Pulsator pulsater;

    public void activate(final Nifty nifty, final Element element, final EffectProperties parameter) {
        image = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(), parameter.getProperty("filename"), true);

        String areaProviderProperty = ImageModeHelper.getAreaProviderProperty(parameter);
        String renderStrategyProperty = ImageModeHelper.getRenderStrategyProperty(parameter);

        String blendMode = parameter.getProperty("blendMode");
        if (blendMode != null) {
            if (blendMode.toLowerCase().equals("blend")) {
                this.blendMode = de.lessvoid.nifty.render.BlendMode.BLEND;
            } else if (blendMode.toLowerCase().equals("multiply")) {
                this.blendMode = de.lessvoid.nifty.render.BlendMode.MULIPLY;
            }
        }
        if ((areaProviderProperty != null) || (renderStrategyProperty != null)) {
            image.setImageMode(ImageModeFactory.getSharedInstance().createImageMode(areaProviderProperty,
                    renderStrategyProperty));
        }

        this.pulsater = new Pulsator(parameter, nifty.getTimeProvider());
    }

    public void execute(
            final Element element,
            final float normalizedTime,
            final Falloff falloff,
            final NiftyRenderEngine r) {
        r.saveState(null);
        float value = pulsater.update();
        r.setBlendMode(blendMode);
        r.setColorAlpha(value);
        r.renderImage(image, element.getX(), element.getY(), element.getWidth(), element.getHeight());
        r.restoreState();
    }

    public void deactivate() {
        image.dispose();
    }
}
