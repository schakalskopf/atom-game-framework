/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.datastructure.stream;

import sg.atom.utils.datastructure.buffer.AtomBuffer;

/**
 * High level of sequencing.
 *
 * <p>Use Stream-lib or Netty undernearth.
 * 
 * <p>
 *
 * TODO: Is there a need of Streamable interface?
 *
 * @author cuong.nguyenmanh2
 */
public interface AtomStreaming {

    AtomBuffer getBuffer();

    AtomBuffer getSlice(StreamCursor cursor);
}
