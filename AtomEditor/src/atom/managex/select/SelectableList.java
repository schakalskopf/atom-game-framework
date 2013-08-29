/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.managex.select;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hungcuong
 */
public class SelectableList {
    List <Selectable> list = new LinkedList<Selectable>();
    List <Selectable> includeList = new LinkedList<Selectable>();
    List <Selectable> excludeList = new LinkedList<Selectable>();
    
    public SelectableList() {
    }
    
    public void addIncludeList(Selectable includeObj){
        list.add(includeObj);
        includeList.add(includeObj);
    }
    
    public void addExcludeList(Selectable excludeObj){
        if (list.contains(excludeObj))
            list.remove(excludeObj);
        includeList.add(excludeObj);
    }    
    
    public void addIncludeList(SelectableList otherList){
        list.addAll(otherList.list);
        includeList.addAll(otherList.list);
    }
    
    public void addExcludeList(SelectableList otherList){
        list.removeAll(otherList.list);
        excludeList.addAll(otherList.list);
    }
    
    
}
