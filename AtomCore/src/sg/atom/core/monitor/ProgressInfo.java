/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.monitor;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hierachy named task description.
 *
 * @author atomix
 */
public class ProgressInfo implements Cloneable, Savable {

    public static enum ProgressType {

        Task, Action, Service, Thread
    }
    public String name;
    public ProgressType type;
    public float currentProgressPercent;
    public String currentProgressName;
    public boolean isLeaf = true;
    public PiorityInfo piorityInfo;
    private List<ProgressInfo> subProgresses;
    private AtomicProgress wrapedProgress;

    public ProgressInfo(String name) {
        this.name = name;
        this.subProgresses = new ArrayList<ProgressInfo>();
    }

    public String getCurrentProgressName() {
        return currentProgressName;
    }

    public void setCurrentProgressName(String currentProgressName) {
        this.currentProgressName = currentProgressName;
    }

    public float getCurrentProgressPercent() {
        return currentProgressPercent;
    }

    public void setCurrentProgressPercent(float currentProgressPercent) {
        this.currentProgressPercent = currentProgressPercent;
    }
    /* Sub progress management*/

    public List<ProgressInfo> getSubProgresses() {
        return subProgresses;
    }

    public void setSubProgresses(List<ProgressInfo> subProgresses) {
        this.subProgresses = subProgresses;
    }

    public void addSubProgress(ProgressInfo subProgress) {
        this.subProgresses.add(subProgress);
    }

    // SAVE LOAD
    @Override
    public void write(JmeExporter ex) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void notifyProgress(){
        
    }
}
