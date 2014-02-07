/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.data;

import sg.atom.corex.asset.DataStructure;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface IDataEditor<T extends DataStructure> {

    public void load(T data);

    public void edit(T data, Object... action);

    public DataStructure getData();

    public void releaseData(T data);

    //public String getDescription();
    
    //public String get
}
