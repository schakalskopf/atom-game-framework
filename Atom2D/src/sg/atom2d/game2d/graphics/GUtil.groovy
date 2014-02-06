package sg.testbed.game2d.graphics

import java.awt.*
import java.awt.image.*
import javax.imageio.*
import sg.atom2d.geo.sprite.*

public class GUtil {
    
    public GUtil(){
        
    }
    public Sprite loadSprite(String path,int cols,int rows){
        
        try{
            BufferedImage bigImg = ImageIO.read(new File(path));
            int width = bigImg.width / cols
            int height = bigImg.height / rows
            
            Sprite aSprite = new Sprite("A",width,height,cols,rows)
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    aSprite.sprites[(i * cols) + j] = bigImg.getSubimage(
                        j * width,
                        i * height,
                        width,
                        height
                    );
                }
            }
            return aSprite
        } catch (FileNotFoundException e){
            println ("The file" + path + " not found ");
        }
        return null
    }
	
}

