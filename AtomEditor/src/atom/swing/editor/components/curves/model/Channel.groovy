package atom.swing.editor.ui.curves.model

import groovy.swing.SwingBuilder
import groovy.swing.j2d.*
import java.awt.BorderLayout as BL
import groovy.swing.j2d.*
import java.awt.*;
import javax.swing.*
import com.jme3.math.*

public class Channel{
    def random = new Random()
    def curves=[]
    def points=[]
    def controlPoints=[]
    Channel(){
        newRandomPoints()
    }
    def newRandomPoints(){
        def minstepX =20
        def maxstepX =20
        def numOfPoints=20
        def currentX =0
        def newPoint = null
        def lastPoint = null
        /*
        def xi=Math.round((float)random.nextFloat())
        def yi=Math.round((float)random.nextFloat())
         */
        (0..numOfPoints).each(){i->
            def xi=minstepX+random.nextInt(maxstepX)
            def yi=random.nextInt(600)
            currentX += xi
            newPoint = new Vector2f(currentX,yi)
            points<< newPoint
        
            if (i!=0){
            
                def m = (int)Math.round(lastPoint.distance(newPoint))
            
                Vector2f mid = new Vector2f((float)(lastPoint.x + newPoint.x)/2,(float)(lastPoint.y + newPoint.y)/2 )
                Vector2f mid1 = new Vector2f((float)(mid.x-lastPoint.x) ,(float)(mid.y -lastPoint.y)).mult(0.2f)
                Vector2f mid2 = new Vector2f((float)(mid.x-lastPoint.x) ,(float)(mid.y -lastPoint.y)).negate().mult(0.2f)
            
                /*
                def dcx1=random.nextInt(m)
                def dcy1=random.nextInt(m)
                def dcx2=random.nextInt(m)
                def dcy2=random.nextInt(m)
            
                controlPoints << new Vector2f(lastPoint.x+dcx1,lastPoint.y+dcy1)
                controlPoints << new Vector2f(newPoint.x-dcx2,newPoint.y-dcy2)
                 */
                mid1.rotateAroundOrigin((float)random.nextFloat()*FastMath.PI,true)
                mid2.rotateAroundOrigin((float)random.nextFloat()*FastMath.PI,false)
                controlPoints <<lastPoint.add(mid1)
                controlPoints <<newPoint.add(mid2)
            }
            lastPoint = newPoint
        }
    
        points.each(){point->
            println( "x:"+ point.x+" y: "+point.y )
        }
    
        controlPoints.each(){point->
            println( "x:"+ point.x+" y: "+point.y )
        }
    }

    def getDrawCurves(def gb,def channelColor){
        return gb.group(id:'curves'){

            transformations {
                //scale( x: 4, y: 4 )
            }
   

            if (channelColor==null){
                channelColor = "green"
            } else {
                
            }
            /*
            path( borderColor: 'darkRed', fill: false, borderWidth: 1 ){
            moveTo( x: 0, y: 0 )
            points.each(){point->
            lineTo( x: point.x, y: point.y )
            }
            //close()
            basicStroke( width: 1, color: 'gray', dash:[10.0f],dashphase:1 )
            }    
             */
            
            points.eachWithIndex(){point,i->
                if (i==0){
                    //moveTo( x: point.x, y: point.y )
                }else{
                    def cp1 = controlPoints.get((i-1)*2)
                    def cp2 = controlPoints.get((i-1)*2+1)
                
                    def p1 = points.get(i-1)
                    def p2 = points.get(i)
                    //quadTo( x1: cp1.x, y1: cp1.y, x2: point.x, y2: point.y )
                    cubicCurve( 
                        x1: p1.x, y1: p1.y, 
                        ctrlx1: cp1.x, ctrly1:cp1.y, 
                        ctrlx2: cp2.x, ctrly2: cp2.y, 
                        x2: p2.x, y2: p2.y,
                        borderColor: channelColor, borderWidth: 1 , 
                        mouseMoved:{
                            borderWidth=4
                        },
                        mouseExited:{
                            borderWidth= 1
                        }){
                    
                    }
                    
                    group(id:"controls",opacity:0.5f){
                        line(                    
                            x1: p1.x, y1: p1.y, 
                            x2: cp1.x, y2:cp1.y,  
                            borderColor: 'yellow', borderWidth: 1 )
                        line(                    
                            x1: p2.x, y1: p2.y, 
                            x2: cp2.x, y2:cp2.y,  
                            borderColor: 'darkOrange', borderWidth: 1 )
                        [cp1,cp2].each(){cp->
                            circle( cx: cp.x, cy: cp.y, radius: 3, borderColor: 'yellow', borderWidth:1, fill: 'yellow' ,
                                mouseMoved:{
                                    borderWidth=4
                                })
                        }
                        rect( x: point.x - 3, y: point.y - 3, width: 6, height:6, borderColor: 'green', borderWidth:1, fill: 'yellow' ,
                            mouseMoved:{
                                borderWidth=4
                            })
                    }
                
                }
            }       

        }
    
    }
}


