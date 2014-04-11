package sg.atom.managex.api.system;

import com.jme3.app.SimpleApplication;
import sg.atom.managex.api.function.FunctionSystem;

/**
 * EditorSystem is the base system for the API. Any application has the
 * EditorSystem instance can control editing, project and modules facilities
 * that AtomEditor Managex API provide. <h4>How this system work?</h4>
 *
 * <p>It's formal: Mode/State/Perspective
 *
 * <p>It's reactive: Changes/observations/interactions are reactively noticed
 * and propagrated. The other event/messaging are also supported via EventBus
 * but its not main stream.
 *
 * <p>It's a giant tree (graph): Every components (modules) forming a tree (graph). Configs/Data/Nodes ... etc.
 *
 * <p>It's modulized: modules are loaded via osgi or built-in plugin mechanism.
 * 
 * <p>It's transactional: 
 *
 * @author CuongNguyen
 */
public class EditorSystem implements TransactionalEditingSystem {
    protected SimpleApplication app;
    protected FunctionSystem functionSystem;
    //protected 
    
    public void bootStrap() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public void loadModules(){
        
    }
    
    public void loadConfigs(){
        
    }
    
    public void checkUpdates(){
        
    }
    
    public void downloadUpdates(){
        //Use GetDown to download updates
    }
    public void request(){
        
    }
            
    //getInMemoryDatabase()
}
