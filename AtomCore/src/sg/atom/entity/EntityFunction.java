/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity;

import sg.atom.entity.general.AbstractEntityFunction;

/**
 * Facade and Description (Common implementation) about the functioning of an
 * Entity, can be shared by multi Entities.
 *
 * <p>It share the same abstract level with Component. You can look at it as
 * "Lightweight System" with single Aspect embed inside of Entity.</p>
 *
 *
 * @author atomix
 */
public class EntityFunction implements AbstractEntityFunction {
    // Basic properties. FIXME: Remove them!
    int id;
    String name;
    String title;
    String info;

    public EntityFunction(String name, String title, String info) {
        this.name = name;
        this.title = title;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }
}
