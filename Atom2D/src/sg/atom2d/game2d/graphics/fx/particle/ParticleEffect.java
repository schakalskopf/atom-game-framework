/**
 * *****************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * ****************************************************************************
 */
package sg.atom2d.game2d.graphics.fx.particle;

import com.jme3.asset.AssetManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import com.jme3.texture.Texture;
import jme3tools.optimize.TextureAtlas;
import sg.atom.core.asset.FileHandle;
import sg.atom.utils.collection.Array;
import sg.atom2d.game2d.graphics.jme3.texture.Sprite;
import sg.atom2d.game2d.graphics.jme3.texture.SpriteBatch;
import sg.atom2d.swing.SwingSimple2DApp;

/**
 * See <a
 * href="http://www.badlogicgames.com/wordpress/?p=1255">http://www.badlogicgames.com/wordpress/?p=1255</a>
 *
 * @author mzechner
 */
public class ParticleEffect {// implements Disposable {

    private final Array<ParticleEmitter> emitters;
    private AssetManager assetManager;
    
    public ParticleEffect() {
        assetManager = SwingSimple2DApp.getInstance().getAssetManager();
        emitters = new Array(8);
        
    }
    
    public ParticleEffect(ParticleEffect effect) {
        assetManager = SwingSimple2DApp.getInstance().getAssetManager();
        emitters = new Array(true, effect.emitters.size);
        for (int i = 0, n = effect.emitters.size; i < n; i++) {
            emitters.add(new ParticleEmitter(effect.emitters.get(i)));
        }
    }
    
    public void start() {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).start();
        }
    }
    
    public void reset() {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).reset();
        }
    }
    
    public void update(float delta) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).update(delta);
        }
    }
    
    public void draw(SpriteBatch spriteBatch) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).draw(spriteBatch);
        }
    }
    
    public void draw(SpriteBatch spriteBatch, float delta) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).draw(spriteBatch, delta);
        }
    }
    
    public void allowCompletion() {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).allowCompletion();
        }
    }
    
    public boolean isComplete() {
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            if (!emitter.isComplete()) {
                return false;
            }
        }
        return true;
    }
    
    public void setDuration(int duration) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            emitter.setContinuous(false);
            emitter.duration = duration;
            emitter.durationTimer = 0;
        }
    }
    
    public void setPosition(float x, float y) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).setPosition(x, y);
        }
    }
    
    public void setFlip(boolean flipX, boolean flipY) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).setFlip(flipX, flipY);
        }
    }
    
    public void flipY() {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).flipY();
        }
    }
    
    public Array<ParticleEmitter> getEmitters() {
        return emitters;
    }

    /**
     * Returns the emitter with the specified name, or null.
     */
    public ParticleEmitter findEmitter(String name) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            if (emitter.getName().equals(name)) {
                return emitter;
            }
        }
        return null;
    }
    
    public void save(File file) {
        Writer output = null;
        try {
            output = new FileWriter(file);
            int index = 0;
            for (int i = 0, n = emitters.size; i < n; i++) {
                ParticleEmitter emitter = emitters.get(i);
                if (index++ > 0) {
                    output.write("\n\n");
                }
                emitter.save(output);
                output.write("- Image Path -\n");
                output.write(emitter.getImagePath() + "\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error saving effect: " + file, ex);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ex) {
            }
        }
    }
    
    public void load(FileHandle effectFile, FileHandle imagesDir) {
        loadEmitters(effectFile);
        loadEmitterImages(imagesDir);
    }
    
    public void load(FileHandle effectFile, TextureAtlas atlas) {
        loadEmitters(effectFile);
        loadEmitterImages(atlas);
    }
    
    public void loadEmitters(FileHandle effectFile) {
        InputStream input = effectFile.read();
        emitters.clear();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(input), 512);
            while (true) {
                ParticleEmitter emitter = new ParticleEmitter(reader);
                reader.readLine();
                emitter.setImagePath(reader.readLine());
                emitters.add(emitter);
                if (reader.readLine() == null) {
                    break;
                }
                if (reader.readLine() == null) {
                    break;
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error loading effect: " + effectFile, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
            }
        }
    }
    
    public void loadEmitterImages(TextureAtlas atlas) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            String imagePath = emitter.getImagePath();
            if (imagePath == null) {
                continue;
            }
            String imageName = new File(imagePath.replace('\\', '/')).getName();
            int lastDotIndex = imageName.lastIndexOf('.');
            if (lastDotIndex != -1) {
                imageName = imageName.substring(0, lastDotIndex);
            }
            Sprite sprite = Sprite.createSprite(atlas, imageName);
            if (sprite == null) {
                throw new IllegalArgumentException("SpriteSheet missing image: " + imageName);
            }
            emitter.setSprite(sprite);
        }
    }
    
    public void loadEmitterImages(FileHandle imagesDir) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            String imagePath = emitter.getImagePath();
            if (imagePath == null) {
                continue;
            }
            String imageName = new File(imagePath.replace('\\', '/')).getName();
            Sprite sprite = new Sprite(loadTexture(imagesDir.child(imageName)));
            emitter.setSprite(sprite);
        }
    }
    
    protected Texture loadTexture(FileHandle file) {
        //FIXME: Use AssetManager instead.
        return assetManager.loadTexture(file.path());
    }

    /**
     * Disposes the texture for each sprite for each ParticleEmitter.
     */
    public void dispose() {
        /*
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            emitter.getSprite().getTexture();
        }
        */ 
    }
}
