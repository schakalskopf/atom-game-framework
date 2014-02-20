/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.jme3;

import com.jme3.app.SimpleApplication;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Simple2DApplication extends SimpleApplication{
    /**
     * Singleton reference of Object.
     */
    private static Simple2DApplication selfRef;

    /**
     * Constructs singleton instance of Object.
     */
    protected Simple2DApplication() {
        selfRef = this;
    }

    /**
     * Provides reference to singleton object of Object.
     *
     * @return Singleton instance of Object.
     */
    public static final Simple2DApplication getInstance() {
        if (selfRef == null) {
            selfRef = new Simple2DApplication();
        }
        return selfRef;
    }

    @Override
    public void simpleInitApp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
