package sg.atom.swing.editor.ui.curves.ui

import groovy.swing.j2d.*
import java.awt.*;
import com.jme3.math.*
import sg.atom.swing.editor.ui.curves.model.*
class CurvesUI{

    // UI
    def gb = new GraphicsBuilder()
    def getDrawGrid(){
        return gb.group{
            vieww= 800
            viewh= 640
            playPos = 100
            rect(x:0,y:0,width:vieww,height:viewh,fill:'darkGray')
            // horizontal grid line
            def gh = 0
            while(gh<viewh){
                gh+=40
                line(x1:0,y1:gh,x2:vieww,y2:gh,borderColor: 'gray',borderWidth:1)
            }
            // vertical grid line
            def gw = 0
            while(gw<vieww){
                gw+=100
                line(x1:gw,y1:0,,x2:gw,y2:viewh,borderColor: 'gray',borderWidth:1)
            }
        
            line(id:"playCue",y1:0,x1:playPos,y2:vieww,x2:playPos,borderColor: 'red',borderWidth:2)
            group(new Channel().getDrawCurves(gb,null))
            group(new Channel().getDrawCurves(gb,"red"))
            group(new Channel().getDrawCurves(gb,"blue"))
            group(new Channel().getDrawCurves(gb,"yellow"))
            group(getDrawTrack())
        }
    }
    def getDrawScroolBar(){
    
    }

    def getDrawTimeRuler(){
    
    }

    def getDrawTrack(){
        return gb.group(){
            //rect( x: 10, y: 10, width: 290, height: 80, arcWidth: 20, arcHeight: 20 )
            //circle( cx: 90, cy: 80, radius: 50, borderColor: 'darkRed', fill: 'red' )
            //polygon(points: [175, 38, 229, 69, 229, 131, 175, 162, 121, 131, 121, 69])
        }
    }

}


