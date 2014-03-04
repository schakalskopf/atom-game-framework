/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.data;

import java.util.Map;
import sg.atom.core.config.IConfigurable;
import sg.atom.corex.asset.DataStructure;

/**
 * Viewer for Data editing framework. 
 * 
 * Concept borrow from Document based data as in swing.
 * 
 * @author cuong.nguyenmanh2
 */
public interface IDataViewer<T extends DataStructure> extends IConfigurable{

    public IDataViewPort<T> getViewPort();

    public void validate();

    public void update(float time);

    public void load();
}
