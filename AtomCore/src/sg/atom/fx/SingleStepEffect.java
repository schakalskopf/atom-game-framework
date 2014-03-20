/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx;

import com.jme3.animation.LoopMode;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.cinematic.Cinematic;
import com.jme3.cinematic.PlayState;
import com.jme3.cinematic.events.CinematicEvent;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.scene.Spatial;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.core.bean.EditableBean;

/**
 * The simplest form of effect which it onestep only! This implementation rework
 * to propose some abstract part and integrate with existed JME3 Cinematic and
 * Animation architecture.
 *
 * <p>Effects can be chain, discard...
 *
 * @author cuong.nguyenmanh2
 */
public abstract class SingleStepEffect implements AtomEffect, EditableBean, Serializable, Cloneable, Comparable<SingleStepEffect>, CinematicEvent {

    @Override
    public int getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Properties getProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void active(Spatial target) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deactive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(SingleStepEffect o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setProperty(String propName, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getProperty(String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T getProperty(Class<T> clazz, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void forceStop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getDuration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSpeed(float speed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getSpeed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PlayState getPlayState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setLoopMode(LoopMode loop) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LoopMode getLoopMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getInitialDuration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setInitialDuration(float initialDuration) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void internalUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void initEvent(Application app, Cinematic cinematic) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setTime(float time) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(Application app, AbstractManager... managers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void load(AssetManager assetManager) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void config(Object... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
