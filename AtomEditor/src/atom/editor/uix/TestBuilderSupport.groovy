package atom.editor.uix
import groovy.util.BuilderSupport;
class SpoofBuilder extends BuilderSupport{
    def log = []
    protected void setParent(Object parent, Object child){
        log << "sp"
        log << parent
        log << child
    }
    protected Object createNode(Object name){
        log << 'cn1'
        log <<  name
        return 'x'
    }
    protected Object createNode(Object name, Object value){
        log << 'cn2'
        log << name
        log << value
        return 'x'
    }
    protected Object createNode(Object name, Map attributes){
        log << 'cn3'
        log << name
        attributes.each{entry -> log << entry.key; log << entry.value}
        return 'x'
    }
    protected Object createNode(Object name, Map attributes, Object value){
        log << 'cn4'
        log << name
        attributes.each{entry -> log << entry.key; log << entry.value}
        log << value
        return 'x'
    }
    protected void nodeCompleted(Object parent, Object node) {
        log << 'nc'
        log << parent
        log << node
    }
}

// simple node
def b = new SpoofBuilder()
b.foo(i:"he"){
    bar(i:"so")
    af()
}
println b.log