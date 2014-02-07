/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.fx.particle;

import sg.atom2d.game2d.graphics.texture.Sprite;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Particle extends Sprite {

    protected int life, currentLife;
    protected float scale, scaleDiff;
    protected float rotation, rotationDiff;
    protected float velocity, velocityDiff;
    protected float angle, angleDiff;
    protected float angleCos, angleSin;
    protected float transparency, transparencyDiff;
    protected float wind, windDiff;
    protected float gravity, gravityDiff;
    protected float[] tint;

    public Particle(Sprite sprite) {
        super(sprite);
    }
}