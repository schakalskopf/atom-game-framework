package sg.atom2d.geo.sprite

import java.awt.*
import java.awt.image.*
import javax.imageio.*

public class Sprite{
    String name;
    int width = 10;
    int height = 10;
    int rows = 5;
    int cols = 5;
    BufferedImage[] sprites 
    int index=0;
    
    public Sprite(String name,int width,int height,int cols,int rows) {
        this.name = name
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        sprites = new BufferedImage[rows * cols];
    }
    
    boolean debug
    
    def next(){
        if (index < rows * cols -1){
            index++;
        } else {
            index = 0;
        }
    }
    def current(){
        sprites[index]
    }
}

