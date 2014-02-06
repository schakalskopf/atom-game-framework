/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.lifecycle;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import java.util.Properties;
import sg.atom.core.AbstractManager;

/**
 *
 * @author CuongNguyen
 */
public interface ManagableObject {

    public void init(Application app, AbstractManager... managers);

    public void load(AssetManager assetManager);

    public void config(Properties props);
    
    public void config(Object... params);

    public void update(float tpf);

    public void finish();
}
