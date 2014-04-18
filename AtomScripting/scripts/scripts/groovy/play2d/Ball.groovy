package script.groovy.play2d

import static java.awt.Color.*

x=50
y=150
size =180
sx=40
sy=4
sprite = gutil.loadSprite("src/res/sprites/smurf_sprite.png",4,4)
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
    sprite.next()
}
def onPaint(def g) {
    //g.setColor(red)
    //g.fillOval(x,y,size+10,size+20)
    //g.setColor(green)
    //g.fillOval(x,y,size,size)
    g.drawImage(sprite.current(),x,y,size,size,null)
}

def onMouseOver(m){
    println "onMouseOver"
}

def onMouseOut(m2){
    println "onMouseOut dude"
}

me={}