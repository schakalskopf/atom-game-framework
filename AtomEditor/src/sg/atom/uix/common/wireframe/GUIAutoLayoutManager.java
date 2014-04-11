/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.uix.common.wireframe;

import com.google.common.reflect.TypeToken;
import sg.atom.ui.systems.GUIScreenInfo;
import sg.atom.ui.systems.GUISystemService;

/**
 * Wireframe layout manager.
 *
 * <p>It's will generate layout facilities for the gui system by external config
 * or by guessing the appropriate parameters to construct layout element via
 * reflection. By that blind folded methods, it can construct almost every common
 * 2D implementation of GUI layouting!
 *
 * @author CuongNguyen
 */
public class GUIAutoLayoutManager<T extends GUISystemService, S extends GUIScreenInfo> {

    public GUIAutoLayout extractLayout(S screen) {
        TypeToken boundaryModel = screen.getSystemService().getBoundaryModel();
        //boundaryModel.
        //Use paranamer to extract methods within a predicate
        
        // If no match was found. throw an exception of can not automatic extract layout.
        // In can be config to use a AbsoluteLayout by default, so no exception.
        
        
        return new GUIAutoLayout();
    }
}
