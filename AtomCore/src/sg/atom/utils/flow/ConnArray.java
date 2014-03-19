/*
 * Copyright (C) J.P. Morrison, Enterprises, Ltd. 2009, 2012 All Rights Reserved. 
 */
package sg.atom.utils.flow;

//import java.util.ArrayList;
public class ConnArray implements InputPort {

    boolean fixedSize;
    String name;
    Class type;

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#capacity()
     */
    // private int capacity() {
    //  return 0;
    // }

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#close()
     */
    public void close() {
        //
    }

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#count()
     */
    // private int count() {    
    //   return 0;
    //}

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#getName()
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#getReceiver()
     */
    //private Component getReceiver() {    
    //  return null;
    //}

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#receive()
     */
    public Packet receive() {
        return null;
    }

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#setReceiver(sg.atom.utils.flow.Component)
     */
    @SuppressWarnings("unused")
    private void setReceiver(final Component comp) {
        //
    }

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#setType(java.lang.Class)
     */
    @SuppressWarnings("unused")
    public void setType(final Class tp) {
        // 
    }

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#setName(java.lang.String)
     */
    @SuppressWarnings("unused")
    private void setName(final String n) {
        // 
    }

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#getPort()
     */
    public Port getPort() {
        return null;
    }

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#setPort(sg.atom.utils.flow.Port)
     */
    @SuppressWarnings("unused")
    public void setPort(final Port p) {
        // 
    }

    /* (non-Javadoc)
     * @see sg.atom.utils.flow.InputPort#isClosed()
     */
    public boolean isClosed() {

        return false;
    }
}
