package script.groovy.play2d
import static java.awt.Color.*

x=50
y=50
size =80
sx=1
sy=1

printOut = {add->
    println(x +","+ y +","+ size+" add "+add)
}

def foo(soo){
    println soo + "" + x
}
def update(width,height){
    if (x>width||x<0){
        sx= -sx
    }
    if (y>height||y<0){
        sy= -sy
    }
    x+=sx
    y+=sy
}
def onPaint(def g) {
    g.setColor(magenta)
    g.fillRect(x,y,size,size)
}

def onMouseOver(m){
    println "onMouseOver"
}

def onMouseOut(m2){
    println "onMouseOut dude"
}