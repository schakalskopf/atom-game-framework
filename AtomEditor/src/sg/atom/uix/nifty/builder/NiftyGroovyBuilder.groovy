package sg.atom.uix.nifty.builder;

import org.codehaus.groovy.runtime.InvokerHelper;

import groovy.lang.Closure;
import groovy.lang.MissingMethodException;
import groovy.util.BuilderSupport;
import com.jme3.asset.AssetManager;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.screen.*;
import de.lessvoid.nifty.elements.*;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.dynamic.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder
/**
 * A helper class for creating Nifty childrenSpatials using GroovyMarkup
 * 
 */
public class NiftyGroovyBuilder extends BuilderSupport{

    //private Logger log = Logger.getLogger(getClass().getName());
    //private Map factories = new HashMap();
    //private Object constraints;
    //private Map passThroughNodes = new HashMap();
    private AssetManager assetManager;
    private Nifty nifty;
    
    public NiftyGroovyBuilder(AssetManager assetManager,Nifty nifty) {
        this.assetManager = assetManager;
        this.nifty = nifty;
        registerBuilders();
    }
    
    protected void setParent(Object parent, Object child){
        // Pass through
        if (child instanceof Map){
            parent.invokeMethod(child.methodName,child.args)
        }
        // Component
        if (parent==null){
            println("0 level ");  
        
        } else if (parent instanceof ScreenBuilder){
            println("1 level "+parent+" " + child);  
            if (child instanceof LayerBuilder){
                parent.layer(child);
            }
        } else if (parent instanceof LayerBuilder){
            //println("2 level "+parent+" " + child);  
            if (child instanceof String){
                parent."$child"()
            } else if (child instanceof PanelBuilder){
                parent.panel(child);
            }
        } else if (parent instanceof PanelBuilder){
            //println("3 level "+parent+" " + child);  
            if (child instanceof String){
                parent."$child"()
            } else if (child instanceof TextBuilder){
                parent.text(child);
            } else if (child instanceof ControlBuilder){
                parent.control(child);
            }
        } 
    }
    protected Object createNode(Object name){
        //println("CN 1"+name);
        switch (name){
        case "screen":

            break;
        case "layer":

            break;
        default:
            return name;
        }
    }
    protected Object createNode(Object name, Object value){
        //println("CN 2"+name+" " + value);  
        /*
        switch (name){
        case "screen":
        return new ScreenBuilder(value);
        break;
        case "layer":
        return new LayerBuilder(value);
        break;
        case "panel":
        return new PanelBuilder(value);
        break;
        case "text":
        case "font":
        case"width":
        case"height":
        println "call 2 "+name+" "+value
        return [methodName: name,args:value];
        break;
        case "control":
        return new ControlBuilder(value);
        break;
        }
         */
        return [methodName: name,args:value];
    }

    protected Object createNode(Object name, Map attributes){
        //println("CN 22"+name+" " + attributes);  
        switch (name){
        case "screen":
            return new ScreenBuilder(attributes.id);
            break;
        case "layer":
            return new LayerBuilder(attributes.id);
            break;
        case "panel":
            return new PanelBuilder(attributes.id);
            break;
        case "text":
            return new TextBuilder(attributes.id);
            break;
        case "control":
            return new ControlBuilder(attributes.id,attributes.name);
            break;
        case "button":
            return new ButtonBuilder(attributes.id);
            break;
        }
    }
    protected Object createNode(Object name, Map attributes, Object value){
        //println("CN 3"+name+" " + attributes);  
      
    }
    
    protected void nodeCompleted(Object parent, Object node) {

        if (node instanceof ScreenBuilder){
            println("Add a screen : "+parent+" " + node);  
            def aScreen =node.build(nifty);
            //println aScreen.id();
            
        } else if (parent instanceof ScreenBuilder){
            if (node instanceof LayerBuilder){
                /*
                println("Add a screen : "+parent+" " + node);  
                node.build(nifty);
                 */
            }
        }
    }
    
    public void start(screenName){
        nifty.gotoScreen(screenName);
    }
}


