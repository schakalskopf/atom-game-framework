/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

import com.google.common.collect.Range;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

/**
 * Some common Detail metrics: Visual, priorty, enviromental, perceptual..
 *
 * @author cuong.nguyenmanh2
 */
public class DetailMetrics {

    public static float DEFAULT_METRIC_SCALE = 1;
    public static float DEFAULT_METRIC_EXP = 1;

    public abstract class DetailMetric {

        public abstract void scale(float newScale);

        public abstract void exponent(float exp);

        public abstract float continous();

        public abstract int discrete();

        public abstract Range view(Camera cam);
    }

    public class DistanceAspect extends DetailMetric {

        public float distanceFrom(Vector3f vec1, Vector3f vec2) {
            return vec1.distance(vec2);
        }

        public float distanceFrom(Spatial spatial1, Spatial spatial2) {
            return distanceFrom(spatial1.getWorldTranslation(), spatial2.getWorldTranslation());
        }

        public float distanceFrom(Camera cam, Spatial spatial) {
            return distanceFrom(cam.getLocation(), spatial.getWorldTranslation());
        }

        @Override
        public float continous() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int discrete() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Range view(Camera cam) {
            return null;
        }

        @Override
        public void scale(float newScale) {
            //no op
        }

        public void exponent(float exp) {
        }
    }

    public class SizeAspect extends DetailMetric {

        public float sizeFromAABB(Spatial spatial) {
            return 1;
        }

        public float sizeFromScreen(Spatial spatial) {
            return 1;
        }

        @Override
        public float continous() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int discrete() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Range view(Camera cam) {
            return null;
        }

        @Override
        public void scale(float newScale) {
            //no op
        }

        @Override
        public void exponent(float exp) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    public class FuzzyAspect extends DetailMetric{

        @Override
        public void scale(float newScale) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void exponent(float exp) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public float continous() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int discrete() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Range view(Camera cam) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }

    public class PiorityAspect extends DetailMetric{

        @Override
        public void scale(float newScale) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void exponent(float exp) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public float continous() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int discrete() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Range view(Camera cam) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    public static LODEntropy measure(Object object) {
        return null;
    }

    public static LODEntropy measure(Spatial object) {
        return null;
    }
}
