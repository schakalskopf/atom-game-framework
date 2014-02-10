package sg.atom.swing.editor.components

import groovy.swing.j2d.GraphicsBuilder
import groovy.swing.j2d.GraphicsPanel
/**
 *
 * @author cuong.nguyenmanh2
 */

class TexturePanel extends GraphicsPanel{
    
    def gb = new GraphicsBuilder()
    
    TexturePanel(){
        super()
        setGo(textureDraw())
    }
    def textureDraw(){ 
        return gb.group {
            image( id:"bg", classpath :"Interface/Logo/Monkey.png" )
            rect( id: 'rect', x: 10, y: 10, w: 48, h: 48)
        }
    }

}

