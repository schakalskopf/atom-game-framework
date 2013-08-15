/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity;

import java.util.ArrayList;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Entity {

    public Long id;
    public String name;
    public String type;
    public String group = "";
    public boolean isVisible;
    public boolean isActived;

    public Entity(Long id, String name, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.isActived = true;
        this.isVisible = true;
    }

    public Entity(Long id, String type) {
        this(id, "", type);
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
