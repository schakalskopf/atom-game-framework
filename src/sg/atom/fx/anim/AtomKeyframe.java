/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.anim;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class AtomKeyframe<V> {

    V value;
    boolean mark;
    int id;
    float time;
    
    public AtomKeyframe(V value) {
        this.value = value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public boolean isMark() {
        return mark;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
    
    
}
