/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.spatial;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import sg.atom.utils.proxy.IPresenter;

/**
 * Proxy for Spatial.
 *
 * <p>Can be an semi transparent Proxy or full "reflection" Proxy.
 *
 * @author hungcuong
 */
public class SpatialPresentor implements IPresenter<Spatial> {

    public static final int NORMAL = 0;
    public static final int BOX = 1;
    public static final int VERTEX = 2;
    public static final int BILLBOARD = 3;
    protected int type;
    protected int currentDisplayType;
    protected Vector3f localTranslate;
    protected Spatial orginalSpatial;
    protected Transform localTransform;

    public SpatialPresentor(Spatial spatial, int type) {
        this.type = type;
        this.localTranslate = new Vector3f(Vector3f.ZERO);
        this.orginalSpatial = spatial;
        this.currentDisplayType = 0;
    }

    public Spatial getCurrentSpatial() {
        switch (currentDisplayType) {
            case NORMAL:
                return this.getOrginalSpatial();

        }
        return null;
    }

    public void initPresentor() {
        createBox();
        createBillboard();
        createPoint();
    }

    private void createBox() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createBillboard() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createPoint() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @return the currentDisplayType
     */
    public int getCurrentDisplayType() {
        return currentDisplayType;
    }

    /**
     * @param currentDisplayType the currentDisplayType to set
     */
    public void setCurrentDisplayType(int currentDisplayType) {
        this.currentDisplayType = currentDisplayType;
    }

    /**
     * @return the orginalSpatial
     */
    public Spatial getOrginalSpatial() {
        return orginalSpatial;
    }

    /**
     * @param orginalSpatial the orginalSpatial to set
     */
    public void setOrginalSpatial(Spatial orginalSpatial) {
        this.orginalSpatial = orginalSpatial;
    }

    public Spatial from(Converter<Object, Spatial> convertor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Spatial from(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Proxy asProxy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Spatial mix(Spatial... objects) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public <E extends Spatial> E as(Class<E> clazz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Spatial get() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public WeakReference<Spatial> getWeakReference() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <E> E as(Function<Spatial, E> convertor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
