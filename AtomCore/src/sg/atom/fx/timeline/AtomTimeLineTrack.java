/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.timeline;

import com.google.common.base.Function;
import com.jme3.animation.LoopMode;
import com.jme3.cinematic.PlayState;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * A TimeLineTrack is the facace of AtomValueTrack. 
 *
 * With name, position, layering, status, properties and modifiers.
 *
 * Support composing and hierachy form.
 *
 * @author CuongNguyen
 */
public class AtomTimeLineTrack implements Savable{
    
    protected PlayState playStatus;
    protected LoopMode loopType;
    public String name;
    public AtomValueTrack track;
    public int order;
    public int id;
    public int status;
    public Properties properties;
    public ArrayList<Function> modifiers;

    @Override
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
