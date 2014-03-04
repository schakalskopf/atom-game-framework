/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.asset;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import org.apache.commons.codec.Decoder;
import org.apache.commons.codec.Encoder;
import org.codehaus.preon.Codec;

/**
 * Description or a view of a Data (normally an Asset in JME3 context). Also
 * support various methods as automaticly translated between various source and
 * destination via Morph.
 *
 * <p> DataStructure is the description of Data. With a data structure declared
 * upon the Data, where can set/get and travel to interested part of the data.
 * One common way is to see it as a Collection of Objects, its underlying
 * data.</p>
 *
 * <p>The associatd data can be flatten to bytes and then streaming as
 * ByteStream.</p><p>
 *
 * <b>Note:</b> In the past the data most simple form are limited as bytes.
 * Recently in Atom 1.1 intergrated Preon in its core instead of Apache codec.
 * So it also suitable for Bit manipulation.</p>
 *
 * @author cuong.nguyenmanh2
 */
public abstract class DataStructure {

    Object data;

    public void associateData(Object input) {
        this.data = input;
    }

    public abstract Collection asCollection();

    public abstract Object getDescription();

    public abstract Codec getCodec();

    public abstract Encoder getLegacyEncoder();
    
    public abstract Decoder getLegacyDecoder();
    
    public abstract String getMimeType();

    public abstract Object getAt(Object location);

    public abstract byte[] getBytes();

    public InputStream getInputStream() {
        return new ByteArrayInputStream(getBytes());
    }

    public OutputStream getOutputStream() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = getBytes();
        byteStream.write(bytes, 0, bytes.length);
        return byteStream;
    }
    /*
     public abstract InputCapsule getInputCapsule();

     public abstract OutputCapsule getOutputCapsule();
     */
}
