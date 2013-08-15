/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity;

/**
 *
 * @author hungcuong
 */
public class EntityFunction {
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
