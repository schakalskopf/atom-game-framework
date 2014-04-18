/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

import com.jme3.math.Transform;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * WorldLODManager manage the morphing and increase/decrease qualitiy of Spatial
 * over distance (physical and Perceptual). Cursor is a concept that define the
 * interest of the system in the moment, can be inside a boundary or over a
 * distance, depend on the implementation.
 *
 * @author atomix
 */
public interface WorldLODManager {

    public static enum LODType {

        Discrete, Continous, ViewDependent
    }

    Node getRootNode();

    void tweakLOD(Object object, int level);

    void tweakLOD(Spatial spatial, int level);

    void blend(Spatial spatial, float fpf);

    Transform getCursor();

    Camera getCamera();
}
