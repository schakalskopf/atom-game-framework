/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.asset;

import com.jme3.asset.AssetLoader;
import java.util.Map;
import org.codehaus.preon.CodecFactory;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface ManagedAssetLoader extends AssetLoader {

    public CodecFactory getCodecFactory();

    public Map<String, AbstractLoaderHelper> getHelpers();
}
