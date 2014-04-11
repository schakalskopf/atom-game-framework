/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.asset;

import com.jme3.asset.AssetKey;

/**
 * AssetManager for "design phase". Based in DesktopAssetManager but with more
 * features, it can also be used in Game which require a lot of designing
 * features (like in-game editor).
 *
 * <p>AtomExAssetManager provide dataflow facilities for Assets loading. It also
 * use GuavaIO to safely manage, open, close sources and sinks properly for Java
 * 6+.
 *
 * <p>Integrated with encode/decode/compress features.
 *
 * <p>It can resolve asset dendencies even before loading to reduce un-used or
 * unnessary loading. Loading and preloading can work in multi transparent
 * threads.
 *
 * <p>Configable which defaults JME3's AssetManager does not.
 *
 * <p>Use AssetPack and XML to locate assets.
 *
 * @author cuong.nguyenmanh2
 */
public class AtomExAssetManager {

    public void load(AssetKey assetKey) {
        //Resolve the dependencies graph first
        //Try to load.
        //If errors. Freeze some trouble some path. and try to continue or retry.
        //If failed more than settings times. Throw specific clean exceptions and problems.
    }
}
