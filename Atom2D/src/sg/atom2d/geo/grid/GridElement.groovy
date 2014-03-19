package sg.atom2d.geo.grid

class GridElement{
    float x,y;
    float height,width;
    
    int index;
    int type;
    int textureType;
    String name;
    //GridElement parent;
    //GridElement from;
    
    def split(value){
        
    }
    
    public String toString(){
        return " element "+ index +" "+ " pos: "+ x + " "+ y + " size : " + height + " " + width + " type " + type; 
    }

}
