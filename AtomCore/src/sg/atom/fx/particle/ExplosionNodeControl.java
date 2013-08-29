package sg.atom.fx.particle;

import com.jme3.effect.ParticleEmitter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class ExplosionNodeControl extends AbstractControl {

    private float speed = 1;
    private int state = 0;
    private float time = 0;
    private ParticleEmitter flame, flash, spark, roundspark, smoketrail, debris,
            shockwave;
    private float maxTime = 5f;
    boolean detachOnFinish = true;

    public ExplosionNodeControl() {
    }
    /*
     public ExplosionNodeControl(ParticleEmitter flame, ParticleEmitter flash, ParticleEmitter spark, ParticleEmitter roundspark, ParticleEmitter smoketrail, ParticleEmitter debris, ParticleEmitter shockwave) {
     this.flame = flame;
     this.flash = flash;
     this.spark = spark;
     this.roundspark = roundspark;
     this.smoketrail = smoketrail;
     this.debris = debris;
     this.shockwave = shockwave;
     }
     */

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        ExplosionNodeControl control = new ExplosionNodeControl();
        //control.findElements(spatial);
        return control;
    }

    void findElements(Node spatial) {
        this.flame = (ParticleEmitter) spatial.getChild("Flame");
        this.flash = (ParticleEmitter) spatial.getChild("Flash");
        this.spark = (ParticleEmitter) spatial.getChild("Spark");
        this.roundspark = (ParticleEmitter) spatial.getChild("Roundspark");
        this.smoketrail = (ParticleEmitter) spatial.getChild("SmokeTrail");
        this.debris = (ParticleEmitter) spatial.getChild("Debris");
        this.shockwave = (ParticleEmitter) spatial.getChild("Shockwave");
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        if (spatial instanceof Node) {
            findElements((Node) spatial);
        } else {
            throw new RuntimeException("Spatial to add " + this.getClass().getName() + " must be a Node");

        }
    }

    @Override
    protected void controlUpdate(float tpf) {

        time += tpf / speed;
        if (time > 1f && state == 0) {
            if (flash != null) {
                flash.emitAllParticles();
            }
            if (spark != null) {
                spark.emitAllParticles();
            }
            if (smoketrail != null) {
                smoketrail.emitAllParticles();
            }
            if (shockwave != null) {
                shockwave.emitAllParticles();
            }
            state++;
        }
        // emit the other things
        if (time > 1f + .05f / speed && state == 1) {
            if (flame != null) {
                flame.emitAllParticles();
            }
            if (roundspark != null) {
                roundspark.emitAllParticles();
            }
            state++;
        }

        // effect finish
        if (time > maxTime / speed && state == 2) {
            if (!detachOnFinish) {
                // rewind the effect
                state = 0;
                time = 0;
                if (flash != null) {
                    flash.killAllParticles();
                }
                if (spark != null) {
                    spark.killAllParticles();
                }
                if (smoketrail != null) {
                    smoketrail.killAllParticles();
                }
                if (debris != null) {
                    debris.killAllParticles();
                }
                if (flame != null) {
                    flame.killAllParticles();
                }
                if (roundspark != null) {
                    roundspark.killAllParticles();
                }
                if (shockwave != null) {
                    shockwave.killAllParticles();
                }
            } else {
                spatial.removeFromParent();
            }
        }
    }

    public float getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }

    public void setDetachOnFinish(boolean detachOnFinish) {
        this.detachOnFinish = detachOnFinish;
    }

    public boolean isDetachOnFinish() {
        return detachOnFinish;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
