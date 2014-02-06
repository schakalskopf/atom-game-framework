package test.uix.nifty;

import com.jme3.system.AppSettings
import java.util.concurrent.Callable
import com.jme3.asset.AssetManager
import com.jme3.app.SimpleApplication
import com.jme3.niftygui.NiftyJmeDisplay
import de.lessvoid.nifty.Nifty
import atom.uix.nifty.builder.NiftyGroovyBuilder
import atom.uix.nifty.controller.ClosureScreenController

SimpleApplication app = new SimpleApplication(){
    
    Nifty nifty;
    
    public void simpleInitApp(){
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
            inputManager,
            getAudioRenderer(),
            getGuiViewPort());
        nifty = niftyDisplay.getNifty();
        getGuiViewPort().addProcessor(niftyDisplay);
        setupCam(this)
        testNiftyBuilder(this)
    }
    
    Nifty getNifty(){
        return nifty;
    }
};

AppSettings settings = new AppSettings(true);
settings.setWidth(800);
settings.setHeight(800);
app.setPauseOnLostFocus(true);
app.setShowSettings(false);
app.setSettings(settings);
app.start();

def setupCam(app){
    app.flyCam.moveSpeed=20;
    app.flyCam.dragToRotate = true;
    app.inputManager.mouseVisible =true;
}

def testNiftyBuilder(app){
    nifty = app.nifty;
        
    def niftyBuilder = new NiftyGroovyBuilder(app.assetManager,nifty);
    nifty.loadStyleFile("nifty-default-styles.xml");
    nifty.loadControlFile("nifty-default-controls.xml");
    
    niftyBuilder.screen(id:"screen1"){
        controller(new ClosureScreenController(this))
        layer(id:"layer1"){
            childLayoutVertical();
            (1..5).each{panelIndex->
                panel(id:"panel"+"_"+panelIndex){
                    childLayoutHorizontal();
                    width("100%");
                    height("20%");
                    //backgroundImage("panel/nifty-panel.png")
                        
                    text(id:"text"+"_"+panelIndex){
                        style("base-font")
                        //font("aurulent-sans-16.fnt")
                        text("Hello"+"_"+panelIndex)
                    };
                         
                    button(id:"txt"+"_"+panelIndex,name:"button"){
                        style("nifty-button")
                        label("Click me!")
                        interactOnClick("sayHello()");
                        interactX(onClick:{
                            println "Hello world X!"
                        })
                    };
                    control(id:"slider1",name:"horizontalSlider"){
                        width("20%")
                    }
                    control(id:"slider2",name:"horizontalSlider"){
                        width("20%")
                    }
                    text(id:"text"+"_"+panelIndex){
                        style("base-font")
                        //font("aurulent-sans-16.fnt")
                        text("Hello"+"_"+panelIndex)
                    };
                }
            }
                
        }
    }
    niftyBuilder.start("screen1");
}

app.enqueue({     
        
        //nifty.setDebugOptionPanelColors(true);
        //nifty.gotoScreen("screen1");
    } as Callable)



	


