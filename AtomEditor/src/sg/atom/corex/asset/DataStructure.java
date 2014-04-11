/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.asset;

import com.google.common.io.ByteSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.nio.Buffer;
import java.util.Collection;
import org.codehaus.preon.Codec;

/**
 * Description or a view of a Data (normally an Asset in JME3 context). Also
 * support various methods as automaticly translated between various source and
 * destination via Morph.
 *
 * <p><b>Note:</B>Remember the object get through DataStructure is not Data but
 * just a view of Data! This require carefully design system to work with or
 * extend this class but enable a single data be viewed by several of
 * DataStructure.
 *
 * <p> DataStructure is the description of Data. With a data structure declared
 * upon the Data, where can set/get and travel to interested part of the data.
 * One common way is to see it as a Collection of its primitive Objects, its
 * underlying data.</p>
 *
 * <p>At low level, the associatd data can be "flatten" to bytes and then
 * "streaming" as ByteStream. It also can be "view" as a Java Buffer, via
 * getBuffer() method.</p>
 *
 * <p>At higher level, the associated data can be interprete via Codec and can
 * be "save/load" easily via Capsule. It can also be interprete via Protocol
 * (implemented codec), such as serialized to XML or ProtocolBuffer external but
 * those features belong to implementation. But the protocol and
 * MimeType(deprecated) are two simple indicator for that purpose.
 *
 * <p><b>Note:[DONE]</b> In the past the data most simple form are limited as
 * bytes. Recently in Atom 1.1 intergrated Preon in its core instead of Apache
 * codec.So it now also suitable for Bit manipulation.
 *
 * <p><b>Note:[DONE]</b>After intergrated with Guava, notice that Guava going to
 * discontinue support InputSupplier and go to ByteSource instead of Stream;
 * this class also going to move to ByteSource to complete hiding low level
 * operation.
 *
 * @author cuong.nguyenmanh2
 */
public abstract class DataStructure {

    /**
     * Not: Just save the soft reference!
     */
    private SoftReference<Object> data;
    
    public int uid;

    public void associateData(Object input) {
        this.data = new SoftReference(input);
    }

    public abstract Collection asCollection();

    public abstract Object getDescription();

    public abstract Codec getCodec();

    public abstract Object getData();

    public abstract Object getAt(Object location);

    public abstract byte[] getBytes();

    public abstract Buffer getBuffer();

    public abstract ByteSource getByteSource();

    public InputStream getInputStream() {
        return new ByteArrayInputStream(getBytes());
    }

    public OutputStream getOutputStream() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = getBytes();
        byteStream.write(bytes, 0, bytes.length);
        return byteStream;
    }

    public String getProtocol() {
        return null;
    }
//    public abstract Encoder getLegacyEncoder();
//
//    public abstract Decoder getLegacyDecoder();
//
//     public abstract InputCapsule getInputCapsule();
//
//     public abstract OutputCapsule getOutputCapsule();
//  
//    public abstract String getMimeType();    
}
