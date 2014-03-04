/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.filters;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Matrix4f;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;

/**
 *
 * @author kwando
 */
public class ColorCorrectionFilter extends Filter {

    private static Matrix4f MONOCHROME = new Matrix4f(0.299f, 0.587f, 0.184f, 0,
            0.299f, 0.587f, 0.184f, 0,
            0.299f, 0.587f, 0.184f, 0,
            0, 0, 0, 1);
    private static Matrix4f BROWN_SEPIA = new Matrix4f(0.299f, 0.587f, 0.184f, 0.1f,
            0.299f, 0.587f, 0.184f, 0.018f,
            0.299f, 0.587f, 0.184f, -0.090f,
            0, 0, 0, 1);
    private static Matrix4f BLUE_SEPIA = new Matrix4f(0.299f, 0.587f, 0.184f, -0.090f,
            0.299f, 0.587f, 0.184f, 0.018f,
            0.299f, 0.587f, 0.184f, 0.1f,
            0, 0, 0, 1);
    private static Matrix4f GREEN_SEPIA = new Matrix4f(0.299f, 0.f, 0.184f, -0.090f,
            0.299f, 0.587f, 0.184f, 0.1f,
            0.299f, 0.587f, 0.184f, 0.1f,
            0, 0, 0, 1);
    private float monochrome = 0f;
    private float brownSepia = 0;
    private float blueSepia = 0f;
    private float greenSepia = 0;
    private float color = 1f;
    private float contrast = 1;
    private float brightness = 0;
    private float gamma = 1;
    private Matrix4f colorCorrectionMatrix = new Matrix4f();
    private Matrix4f tmp = new Matrix4f();
    private boolean changed = true;

    @Override
    protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort vp, int w, int h) {
        material = new Material(manager, "MatDefs/ColorCorrection.j3md");
    }

    private void computeMatrix() {
        MONOCHROME.mult(monochrome, colorCorrectionMatrix);
        colorCorrectionMatrix.addLocal(BROWN_SEPIA.mult(brownSepia, tmp));
        colorCorrectionMatrix.addLocal(BLUE_SEPIA.mult(blueSepia, tmp));
        colorCorrectionMatrix.addLocal(GREEN_SEPIA.mult(greenSepia, tmp));
        colorCorrectionMatrix.addLocal(Matrix4f.IDENTITY.mult(color, tmp));

        float total = monochrome + brownSepia + blueSepia + greenSepia + color;
        colorCorrectionMatrix = colorCorrectionMatrix.mult(1.0f / total);
    }

    public void setColor(float amount) {
        this.color = amount;
        changed = true;
    }

    public void setBlueSepia(float amount) {
        this.blueSepia = amount;
        changed = true;
    }

    public void setGreenSepia(float amount) {
        this.greenSepia = amount;
        changed = true;
    }

    public void setMonochrome(float amount) {
        this.monochrome = amount;
        changed = true;
    }

    public void setMonochromatic() {
        color = 0;
        monochrome = 1;
        blueSepia = 0;
        brownSepia = 0;
        greenSepia = 0;
    }

    @Override
    protected Material getMaterial() {
        if (changed) {
            computeMatrix();
            material.setMatrix4("ColorCorrection", colorCorrectionMatrix);
            changed = false;
        }
        material.setFloat("Contrast", contrast);
        material.setFloat("Brightness", brightness);
        material.setFloat("Gamma", gamma);
        return material;
    }

    private void setGamma(float gamma) {
        this.gamma = gamma;
    }

    public void setBrownSepia(float amount) {
        this.brownSepia = amount;
        changed = true;
    }
}