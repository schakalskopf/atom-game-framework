/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.project;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.configuration.AbstractConfiguration;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.monitor.ProgressInfo;

/**
 * Project for Editor. This is the central of the Editing framework, beside of
 * the Editor.
 *
 * <p>Only one Project is actived at a time. The project is configed via
 * CommonConfig. Auth via Apache Shiro. IO Watch with Common IO.
 *
 * <p>Pre/post editing mechanism called ProjectBuildMechanism can be call upon
 * init/finish of the project.
 *
 * <p>This implementation use Ant/Graddle as default external build tools,
 * associated with Java and Groovy as you can imagine.
 *
 * <p>This implementation also depend in XML, it use XStream to serialize
 * non-structure XML of project properties, and use Sirix to save them in
 * versoning xml structure. This enable a scalable corporative architurecture,
 * in which serveral user can work upon the project at the same time.
 *
 * @author hungcuong
 */
public class AtomEditorProject implements IGameCycle {

    public String name;
    public String version;
    public String author;
    public String type;
    public Date startDate;
    public int status;
    public String projectPath;
    public String restAddress;
    private AbstractConfiguration projectConfiguration;

    public AbstractConfiguration getConfiguration() {
        return projectConfiguration;
    }
    
    public void loadFromPath(String path){
        
    }
    
    public void loadFromFile(File file){
        
    }
    
    public void closeProject(){
        
    }
    
//Cycle--------------------------------------------------------------

    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void config() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ProgressInfo getCurrentProcess() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isProcessWatcher() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isFinish(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
