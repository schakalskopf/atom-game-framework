package sg.atom2d.geo.panel.tools


import java.awt.Rectangle;
import java.awt.Color;


class GeoHelper{
    def random = new Random()
    
    def newRanRect(){
        /*
        def xi=Math.round((float)random.nextFloat())
        def yi=Math.round((float)random.nextFloat())
        def wi=Math.round((float)random.nextFloat())
        def hi=Math.round((float)random.nextFloat())
         */
  
        def xi=random.nextInt(800)
        def yi=random.nextInt(600)
        def wi=random.nextInt(150)
        def hi=random.nextInt(150)
    
        return new Rectangle(xi,yi,wi,hi)
    }

    def newRect(x,y,w,h){
        def xi=Math.round((float)x)
        def yi=Math.round((float)y)
        def wi=Math.round((float)w)
        def hi=Math.round((float)h)
   
        return new Rectangle(xi,yi,wi,hi)
    }
    def randomColor(){
        Random random = new Random()
        return new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
  
    }
    
    
    def isInside(x,y,rect){
        def x1=rect.x
        def y1=rect.y
        def x2=rect.x+rect.width
        def y2=rect.y+rect.height
        if ((x>x1&&x<x2)&&(y>y1&&y<y2)){
            return true;
        } else {
            return false;
        }
    }
}