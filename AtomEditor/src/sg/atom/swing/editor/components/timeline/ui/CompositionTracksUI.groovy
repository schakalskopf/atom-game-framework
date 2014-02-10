package sg.atom.swing.editor.components.timeline.ui

import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import java.awt.*;
import javax.swing.*
import com.jme3.math.*
import groovy.swing.j2d.*

import sg.atom.swing.editor.components.timeline.model.*

def jFrame;
random = new Random()

// UI
gb = new GraphicsBuilder()

tracks=[]
getRandomTracks()



def getRandomTracks(){
    (1..10).each{i->
        
        def newTrack =new Track(name:"Track"+i,color:randomColor())
        def st=random.nextInt(500)
        def et=st+ 40+ random.nextInt(400)
        newTrack.timeBars<< new TimeBar(name:"Timebar 1",startTime:st,endTime:et)
        tracks<<newTrack
    }
}

def getDrawGrid(def parent){
    
    return gb.group(id:"background"){
        println(parent.width + " "+ parent.height);
        vieww= parent.width
        viewh= parent.height
        playPos = 200
        rect(id:"backgroundRect",x:0,y:0,width:vieww,height:viewh,fill:'gray')
        // horizontal grid line
        group(id:"grid"){
            def gh = 0
            while(gh<viewh){
                gh+=40
                line(id:"",x1:0,y1:gh,x2:vieww,y2:gh,borderColor: 'darkGray',borderWidth:1)
            }
        }

        
    }
}
def updateGrid(def parent){
    if (parent!=null){
        //println "Resized"
        
        if (gb.hasVariable("trackUI")){
            /*
            gb."background".width = parent.width
            gb."background".height = parent.height
             */
            //gb."trackUI".removeOperation(gb."background")
            gb."trackUI".getOps().clear()
            gb."trackUI".addOperation(getDrawUIAll(parent))
        }
        
    }
    
}
def getDrawUIAll(def parent){
    return gb.group(){
        group(getDrawGrid(parent))
        group(getDrawTrack())
        
        vieww= parent.width
        viewh= parent.height
        line(id:"playCue",y1:0,x1:playPos,y2:viewh,x2:playPos,borderColor: 'red',borderWidth:2)
        //group(new TrackUI().getDrawTrack(gb))
    }
}
def getDrawUI(def parent){
    return gb.group(id:"trackUI"){
        group(getDrawUIAll(parent))
    }
}

def getDrawScroolBar(){
    
}

def getDrawTimeRuler(){
    
}
def randomColor(){
    return new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
  
}

def getLighterColor(def c,def s){
    def r,g,b
    if (c.red + s > 255) {
        r=255
    } else if (c.red + s < 0) {
        r=0
    } else {
        r=c.red + s
    }
    
    if (c.green + s > 255) {
        g=255
    } else if (c.green + s < 0) {
        g=0
    } else {
        g=c.green + s
    }
    
    if (c.blue + s > 255) {
        b=255
    } else if (c.blue + s < 0) {
        b=0
    } else {
        b=c.blue + s
    }
    return new Color(r,g,b)
}

def getDrawTrack(){
    return gb.group{
        //texturePaint( id: 'p', x: 0, y: 0, file:"../images/movieframe.jpg" )
        tracks.eachWithIndex(){ track , i->
            def timeBar = track.timeBars[0]
            def start = timeBar.startTime
            def end = timeBar.endTime
            //println(start + "  " + end)
            
            rect( x: start, y: i * 40, width: end-start, height: 40 , fill:track.color,borderColor:"darkGray",borderWidth:1)
            //
            rect( x: start, y: i * 40+15, width: end-start, height: 25 , fill:"lightgray",borderColor:"darkGray",borderWidth:0)
            line(x1:start, y1:i*40+1,x2:end,y2:i*40+1,borderColor:getLighterColor(track.color,40),borderWidth:1)
            line(x1:start, y1:i*40+39,x2:end,y2:i*40+39,borderColor:getLighterColor(track.color,-40),borderWidth:1)
            font( face: "Helvetica", style: Font.ITALIC, size: 12)
            text( x: (end + start) /2 - 20, y : i * 40,text:track.name,fill:"black",borderColor:false)
            
        }
    }
}
gp = null
swing = new SwingBuilder()

def createIcon(String path){
    return swing.imageIcon(resource:"../images/"+path,class:CompositionTracksUI.class)
}
//frameimg=createIcon("movie-frame64.jpg").image

jFrame = swing.frame( title: 'Curves UI', size: [800,640],
    locationRelativeTo: null, show: true ,defaultCloseOperation: JFrame.EXIT_ON_CLOSE){
            
    gp = panel(new GraphicsPanel(), background:Color.gray, componentResized  :{ updateGrid(gp)})

            
    panel( constraints:BL.NORTH  ){
        button(text:"Run",actionPerformed:{
            })
    }
}
    

gp.graphicsOperation= getDrawUI(gp)



