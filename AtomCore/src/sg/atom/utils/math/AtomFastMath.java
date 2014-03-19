package sg.atom.utils.math;

import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import java.util.Random;

/**
 * Utility and fast math functions.
 * 
 * <p>Merged with MathUtils. 
 * 
 * <p>FIXME: Replace with Common's Math
 * @author atomix
 */
public class AtomFastMath {

    public static Random random = new Random();
    public static float PI = 3.14159265358979323846f;
    public static float HALF_PI = (PI / 2);
    static public final float PI2 = PI * 2;
    static private final int SIN_BITS = 14; // 16KB. Adjust for accuracy.
    static private final int SIN_MASK = ~(-1 << SIN_BITS);
    static private final int SIN_COUNT = SIN_MASK + 1;
    static private final float radFull = PI * 2;
    static private final float degFull = 360;
    static private final float radToIndex = SIN_COUNT / radFull;
    static private final float degToIndex = SIN_COUNT / degFull;
    static public final float radiansToDegrees = 180f / PI;
    static public final float radDeg = radiansToDegrees;
    static public final float degreesToRadians = PI / 180;
    static public final float degRad = degreesToRadians;
    
    public static float Sq(float val) {
        return (val * val);
    }

    public static float Max(float a, float b) {
        return ((a) < (b) ? (b) : (a));
    }

    public static float Min(float a, float b) {
        return ((a) > (b) ? (b) : (a));
    }

    public static float RadToDeg(float rad) {
        return ((rad) * (180.0f / PI));
    }

    public static float DegToRad(float deg) {
        return ((deg) * (PI / 180.0f));
    }

    public static float Clamp(float val, float minVal, float maxVal) {
        return (Min(Max(minVal, val), maxVal));
    }

    public static float Lerp(float val1, float val2, float fraction) {
        return (val1 + ((val2 - val1) * fraction));
    }

    public static float Rescale(float val, float currMin, float currMax, float newMin, float newMax) {
        return (Lerp(newMin, newMax, ((val - currMin) / (currMax - currMin))));
    }
    /*
     public static float UnitRand() {return ( (float)rand()/(float)RAND_MAX );}
     */

    public static float UnitRand() {
        return random.nextFloat();
    }

    public static float RangeRand(float minVal, float maxVal) {
        return ((UnitRand() * (maxVal - minVal)) + minVal);
    }

    // aditional 3d maths
    public static Vector3f getPenVec(Vector3f vec) {
        //Plane p = new Plane(vec, point.dot(vec));
        Vector3f upVec = Vector3f.UNIT_Y;
        if (vec.normalize().angleBetween(upVec) < 0.0001f) {
            upVec = Vector3f.UNIT_X;
        } else {
            upVec = Vector3f.UNIT_Y;
        }
        Vector3f penVec = vec.cross(upVec);

        return penVec;
    }

    public static Vector3f normalCal(Vector3f a, Vector3f b, Vector3f c) {
        return a.subtract(b).cross(a.subtract(c)).normalize();
    }
    
    public static Transform inverseTrans(Transform t) {
        Vector3f iT = t.getTranslation().negate();
        Quaternion iQ = t.getRotation().inverse();
        Vector3f iS = Vector3f.UNIT_XYZ.divide(t.getScale());
        return new Transform(iS, iQ, iS);

    }

    public static Transform subtractTransform(Transform t1, Transform t2) {
        return t1.clone().combineWithParent(inverseTrans(t2));
    }
}
