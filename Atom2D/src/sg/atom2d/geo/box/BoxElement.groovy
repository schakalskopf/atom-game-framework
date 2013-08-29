/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.atom2d.geo.panel

class BoxElement{
    float x,y;
    int index;
    int type;
    int textureType;
    int height,width;
    String name;
    //GridElement parent;
    //GridElement from;
    
    def split(value){
        
    }
    
    public String toString(){
        return " element "+ index +" "+ " pos: "+ x + " "+ y + " size : " + height + " " + width + " type " + type; 
    }

}
