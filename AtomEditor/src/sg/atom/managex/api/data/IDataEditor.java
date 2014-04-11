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

    public void interact(Object... action);
    
    public T getDataStructure();

    public AbstractEditableData<T> getEditableData();

    public void releaseData(T data);

    public void validate();

    public void update(float time);

    public void load();

    //public String getDescription();
    //public String get
}
