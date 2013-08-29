package atom.editor.uix.meta

/**
 * 1) MetaGUI is all about Mapping!
 * -- Type of object elements (Property,Component) -> UI element
 * -- Mapping can be handler if needed with various type of Tranform ,...
 * ---- Tranform in Introspector
 * ---- Tranform in GUIFactory
 * 2) Affect by Layout
 * --Layout can be custom
 * ...
 * 4) Awsomeness
 * It's just a little bit of awesomeness borrowed from MetaWidget architecture!
 * It's also an awesome combination of Groovy Guava in one short but nice package
 * @author cuong.nguyenmanh2
 */
// simple Model/ View / Control compostion in MVC. So call a Binding
class GenericMVC {
    // View can be share
    def view
    
    // binding to Model
    def model 
    
    // control
    def control
    
    // optional handy helper, swing style
    def renderer
    def convertor
    def setter
    def getter
    
    def builder
    
    def build(metaType){
        builder.build(metaType)
    }
    
    def layout(metaType){
        renderer.layout(metaType)
    }
}

class GenericLayout{
    def elements = []
    def uiList = []
    
    def doLayout(layoutElements){
        elements.addAll(layoutElements)
        int totalHeight = 0
        elements.each{el->
            println el.toString()
            el.with{
                height = 20
                totalHeight += height
            }
            
        }
        println "Do layout "+totalHeight
    }
}

class GenericLayoutElement{
    def x = 0,y=0,width=0,height=0
    def anchors = []
    def type =""
    def layout = new GenericLayout()
    
    public String toString(){
        return "x :"+x+" y: "+y+" width :" + width + " height: "+ height
    }
    
    public def getChildren(){
        layout.elements
    }
}

class GUIFactory{
    def defaultTypeUIMap = [:]
    def typeTransform
    
    def combine(typeList,layout){
        layoutElements = typeToLayoutElements(typeList)
        layout.doLayout(layoutElements)
    }
    
    def typeToLayoutElements(metaTypeList){
        // 3 steps transform
        // via Map mapping or via Closure
        if (typeTransform==null){
            // map tranform
            return metaTypeList.collect{metaType->
                def uic = cloneUI(defaultTypeUIMap[typeToKey(metaType)])
                uic.build(metaType)
                return uic.layout(metaType)
            }
        } else {
            return metaTypeList.collect(typeTransform)
        }
    }
    
    def typeToKey(metaType){
        metaType.type
    }
    def cloneUI(ui){

    }
    def mapTypeToUI(key,ui){
        defaultTypeUIMap[key]=ui
    }
}

class GUIGenerator{
    def instrospector
    def factory
    def rootElement
    
    GUIGenerator(){

    }
    
    def gen(obj,rootElement = null){
        if (rootElement==null){
            rootElement = new GenericLayoutElement()
            this.rootElement = rootElement
        }
        println (" Generate for " + obj)
        if (obj instanceof List){
            for (def subObj:obj){
                subElement = new GenericLayoutElement() 
                rootElement.getChildren() << subElement
                gen(subObj,subElement)
            }
        } else {
            factory.combine(instrospector.intropect(obj),rootElement.layout)
        }
        
    }
 
    void nifty(){
        showInfo(rootElement)
    }

    def showInfo(element){
        println element.toString()
        
        if (!rootElement.getChildren().isEmpty()){
            for (subElement in rootElement.getChildren()){
                showInfo(subElement)
            }
        }
    }
}

class Introspector{
    def groupDef
    def transform
    def ignore
    def include
    
    // dismiss Meta data cause groovy already have it!
    def intropect(obj){
        def propList = obj.class.fields
        
        propList.each{p->
            
            println p.type.name + " : " + p
        }
        /*
        def typeList=propList.collect(transform)
        return typeList.groupBy(groupDef).values()
         */
        return null;
    } 
    /*
    def ignore(name){
        
    }
    
    def groupBy(groupDef){
        
    }
    
    def withTranfrom(t){
        
    }
     */
}
class MetaType{
    def type
    def data
}


class TextRenderer{
    def layout(metaType){
        
        return new GenericLayoutElement(x:0,y:0,)
    }
}

class TableBuilder {
    def layout
    def childLayout
    def header
    def body
}