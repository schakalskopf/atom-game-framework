/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.managers;

import java.util.HashMap;
import sg.atom.gameplay.GameItem;

/**
 *
 * @author atomix
 */
public class ItemManager {

    private HashMap<Long, GameItem> entityList = new HashMap<Long, GameItem>();
    private HashMap<Long, String> types = new HashMap<Long, String>();
    long totalId=-1;
    
    public ItemManager() {
        //entityList.put(new Long(1), new Weapon(Long.valueOf(1), "Sword1","Sword"));
        //types.put(Long.valueOf(1), "Sword");
    }
    
    public long getNewGlobalId(){
        return totalId++;
    }
}
