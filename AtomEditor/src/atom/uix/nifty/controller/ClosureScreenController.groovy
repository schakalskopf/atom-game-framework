package atom.uix.nifty.controller

import java.util.UUID
import de.lessvoid.nifty.screen.ScreenController
import de.lessvoid.nifty.Nifty
import de.lessvoid.nifty.screen.Screen
/**
 *
 * @author CuongNguyen
 */
public class ClosureScreenController implements ScreenController{
    def owner
    Nifty nifty
    Screen screen
    public ClosureScreenController(){
            
    }
    public ClosureScreenController(owner){
        this.owner = owner
        def emc =new ExpandoMetaClass(ClosureScreenController.class,true,true)
        ClosureScreenController.metaClass = emc
        emc.initialize()
    }
    public void bind(Nifty nifty, Screen screen) {

        this.nifty = nifty;
        this.screen = screen;
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
    
    public void sayHello(){
        println "Hello world!"
    }
    public String createMethod(def element,def interactName,def closure){
        String uuid = UUID.randomUUID() as String
        String methodName = "closure"+uuid.replace("-","")
        methodName="sayHelloX"
        ClosureScreenController.metaClass.'public'."${methodName}" = closure
        ClosureScreenController.metaClass.getMethods().each{
            println it
        }
        return methodName
    }
}

