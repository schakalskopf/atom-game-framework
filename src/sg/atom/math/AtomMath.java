package sg.atom.math;

import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;

/**
 *
 * @author hungcuong
 */
public class AtomMath {

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
