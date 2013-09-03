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
    public boolean detachOnFinish = true;
    public boolean autoExplode = false;
    public boolean started = false;
    Node parentNode;
    ParticleFactory factory;

    public ExplosionNodeControl(ParticleFactory factory, boolean autoExplode) {
        this.factory = factory;
        this.autoExplode = autoExplode;
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
        //ExplosionNodeControl control = new ExplosionNodeControl(false);
        //control.findElements(spatial);
        return this;
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
            if (autoExplode) {
                started = true;
            }
            if (!started) {
                shutEffects();
            }
        } else {
            throw new RuntimeException("Spatial to add " + this.getClass().getName() + " must be a Node");

        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (started) {
            time += tpf / speed;
            if (time > 1f && state == 0) {
                factory.onEffects(new ParticleEmitter[]{flash, spark, smoketrail, shockwave});
                /*
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
                 */
                state++;
            }
            // emit the other things
            if (time > 1f + .05f / speed && state == 1) {
                factory.onEffects(new ParticleEmitter[]{flame, roundspark});
                /*
                 if (flame != null) {
                 flame.emitAllParticles();
                 }
                 if (roundspark != null) {
                 roundspark.emitAllParticles();
                 }
                 */
                state++;
            }

            // effect finish
            if (time > maxTime / speed && state == 2) {
                if (!detachOnFinish) {
                    // rewind the effect
                    state = 0;
                    time = 0;
                    shutEffects();
                } else {
                    spatial.removeFromParent();
                }
            }
        } else {
            shutEffects();
        }
    }

    public void shutEffects() {
        /*
         if (flash != null) {
         flash.setEnabled(false);
         flash.killAllParticles();
         }
         if (spark != null) {
         spark.killAllParticles();
         spark.setEnabled(false);
         }
         if (smoketrail != null) {
         smoketrail.killAllParticles();
         smoketrail.setEnabled(false);
         }
         if (debris != null) {
         debris.killAllParticles();
         debris.setEnabled(false);
         }
         if (flame != null) {
         flame.killAllParticles();
         flame.setEnabled(false);
         }
         if (roundspark != null) {
         roundspark.killAllParticles();
         roundspark.setEnabled(false);
         }
         if (shockwave != null) {
         shockwave.killAllParticles();
         shockwave.setEnabled(false);
         }
         */
        factory.shutEffects(new ParticleEmitter[]{flash, spark, smoketrail, debris, flame, roundspark, shockwave});
    }

    public float getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }

    public void setAutoExplode(boolean autoExplode) {
        this.autoExplode = autoExplode;
    }

    public void setStarted(boolean started) {
        this.started = started;
        if (started) {
            state = 0;
            time = 0;
        } else {
        }
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
