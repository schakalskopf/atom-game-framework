/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.lifecycle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ProcessInfo {

    enum ProcessType {
    }
    String name;
    public float currentProgressPercent;
    public float weight = 1.0f;
    public String currentProgressName;
    public boolean isLeaf = true;
    List<ProcessInfo> subProcesses;

    public ProcessInfo(String name) {
        this.name = name;
        this.subProcesses = new ArrayList<ProcessInfo>();
    }

    /*
    public float getCurrentProgressPercent() {
    if (currentProgressName != null) {
    if (currentProgressName.equals("World")) {
    return currentProgressPercent + 0.4f * worldManager.getCurrentProgressPercent();
    } else {
    return currentProgressPercent;
    }
    } else {
    return 0;
    }
    }
    
    public String getCurrentProgressName() {
    if (currentProgressName != null) {
    if (currentProgressName.equals("World")) {
    return currentProgressName + " : " + worldManager.getCurrentProgressName();
    } else if (currentProgressName.equals("Gameplay")) {
    return "Gameplay";
    } else {
    return currentProgressName;
    }
    } else {
    return null;
    }
    }
     */
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

    public List<ProcessInfo> getSubProcesses() {
        return subProcesses;
    }

    public void setSubProcesses(List<ProcessInfo> subProcesses) {
        this.subProcesses = subProcesses;
    }

    public void addSubProcess(ProcessInfo subProcess) {
        this.subProcesses.add(subProcess);
    }
}
