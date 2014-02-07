/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.entity;

/**
 * Going to use Zay-ES
 * @author hungcuong
 */

@Deprecated
public class Entity {
    String entityName;
    int type;
    int level;
    int status;
    int display;

    public Entity(String entityName, int type, int level) {
        this.entityName = entityName;
        this.type = type;
        this.level = level;
    }
    
    
}
