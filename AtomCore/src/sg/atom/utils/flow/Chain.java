package sg.atom.utils.flow;

import java.util.LinkedList;

/**
 * This is a package-private class which is just used to hold chains attached to
 * Packets. There are no methods here, since all the work is being done in class
 * Packet. This could have been an inner class of Packet.
 */
final class Chain {

    //private Packet head;
    final LinkedList<Packet> members;
    protected final String name;

    Chain(final String n) {
        name = n;
        members = new LinkedList<Packet>();
    }
}
