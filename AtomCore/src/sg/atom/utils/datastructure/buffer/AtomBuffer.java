/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.datastructure.buffer;

import com.jme3.util.BufferUtils;
import io.netty.buffer.ByteBuf;
import java.nio.Buffer;

/**
 * High level buffer. Concepts from flow programming.
 *
 * <p>Unit of a flow is a node that offer blackbox processing, usually refered
 * as Stage in a flow.
 *
 * <p>Reactive
 *
 * <p>A full filled ring buffer model!
 *
 * @author cuong.nguyenmanh2
 */
public class AtomBuffer {

    private ByteBuf byteBuf;

    public ByteBuf getBytes() {
        return byteBuf;
    }

    public Buffer toNIOBuffer() {
        return byteBuf.nioBuffer();
    }

    public void toOpenGLBuffer() {
    }
}
