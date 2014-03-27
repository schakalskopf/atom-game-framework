/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.fx.particle.values;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import sg.atom.utils._commons.CommonParser;
import sg.atom2d.game2d.graphics.fx.particle.ParticleEmitter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SpawnShapeValue extends EditableValue {
    public ParticleEmitter.SpawnShape shape = ParticleEmitter.SpawnShape.point;
    public boolean edges;
    public ParticleEmitter.SpawnEllipseSide side = ParticleEmitter.SpawnEllipseSide.both;

    public ParticleEmitter.SpawnShape getShape() {
        return shape;
    }

    public void setShape(ParticleEmitter.SpawnShape shape) {
        this.shape = shape;
    }

    public boolean isEdges() {
        return edges;
    }

    public void setEdges(boolean edges) {
        this.edges = edges;
    }

    public ParticleEmitter.SpawnEllipseSide getSide() {
        return side;
    }

    public void setSide(ParticleEmitter.SpawnEllipseSide side) {
        this.side = side;
    }

    public void save(Writer output) throws IOException {
        super.save(output);
        if (!active) {
            return;
        }
        output.write("shape: " + shape + "\n");
        if (shape == ParticleEmitter.SpawnShape.ellipse) {
            output.write("edges: " + edges + "\n");
            output.write("side: " + side + "\n");
        }
    }

    public void load(BufferedReader reader) throws IOException {
        super.load(reader);
        if (!active) {
            return;
        }
        shape = ParticleEmitter.SpawnShape.valueOf(CommonParser.readString(reader, "shape"));
        if (shape == ParticleEmitter.SpawnShape.ellipse) {
            edges = CommonParser.readBoolean(reader, "edges");
            side = ParticleEmitter.SpawnEllipseSide.valueOf(CommonParser.readString(reader, "side"));
        }
    }

    public void load(SpawnShapeValue value) {
        super.load(value);
        shape = value.shape;
        edges = value.edges;
        side = value.side;
    }
    
}
