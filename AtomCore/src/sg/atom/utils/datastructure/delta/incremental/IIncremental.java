/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.datastructure.delta.incremental;

/**
 * Mark for incremental data structure. Incremental offer different kind of
 * Obsevable + Comparable (Differental) + Buffered + Extractable.
 *
 * <p>Obsevable mean other can track changes from this data. java observable
 * tend to track every changes and direct notification the Observer. This
 * interface let the Observer inpect changes instead (not just via reflection).
 *
 * <p>Comparable in term of time and progressive operation is what is the
 * lastest updated and valid portion of informations and data the object can
 * offer compare to another instance of the class or its own "in-memory" hashed
 * copy.
 *
 * <p>Buffered mean the data can easily be streamed into other container and
 * fullfill other buffer. Thank to this the dataflow is watchable. If a buffer
 * is drained and it will be cleaned by the GC.
 *
 * <p>Extractable mean local information is also still valid at some rate. So an
 * object which have the "important" information extracted still meaningful
 * enough for the on-going operation.
 *
 * <p>Thank to the kind of data the manager of the dataflow (flows essential
 * form a graph) can answer the question: <ul>
 *
 * <li>What's updated and the workload, locally, globally?
 *
 * <li>How to compare and sync the different between the two version.
 *
 * <li>Where need more resource (CPU,time..) and how to reach that target.
 *
 * <li>What node/module/operation made the data drained. And when to clean
 * them.</ul>
 *
 *
 * <p>=========================================================================
 * <p><b>The fundamental idea:</b> of this kind of data is it will be used in
 * progressive operation… You may ask what kind of operation is not
 * progressive??? I mean the kind operation interest more in the newly appear
 * data but not the full view of the data its self. So, the operation is not
 * quite often require the full view (which require an end) of the data but just
 * ask for small portion of updated data and the nearby.
 *
 * <p>I’m not talking about an algorimth but here is java level. This kind of
 * data its self is suitable for a lot of application include
 * AI,networks,rendering... for real-time application. The AI in real-time
 * application and games are not very “sotiphicated” compare to other
 * application. Because they have to run in real-time and have to share resource
 * and computation cost with render, physics and others…
 *
 * <p>Incremental data is like streaming into AI pipeline with a buffer. The
 * different is, this buffer is “intelligent” and by sampling its internal data,
 * the buffer also offer extra information. Incremental data does not promise an
 * end of its progress, its always are continuing. One can find that the data
 * unchanged for a while and lose interest in it and abandon it. That’s the time
 * the GC come and clean it away. Here the implemented GC are also a helper for
 * the low level java GC.
 *
 * <p>The philosophy of the buffer-all things are buffer (even beyond my
 * imagination) :) is : there’s also a lot of buffers and running operations
 * exist out there. One fullfill another and then continue in the stream… If we
 * can control dataflow and flows management (monitoring, profiling and
 * optimizing…), this going to be a bright future. What Flow based programming
 * recently try to do, (what they call remap the programming world) is based on
 * this idea!
 *
 * <p>Also, incremental data is not suitable for every case. In case, the user
 * don’t like the idea waiting for thousand of buffer, he can use different kind
 * of data for his purpose.
 *
 * <p>That’s my short intro about the incremental data, details below.
 * <p>=========================================================================
 * <p><b>Implementation</b>The initial implementations of the data structures
 * (for main-memory storage) provide:
 *
 * <p><b>Pivoted data structure.</b> The interest (view, position, index) of
 * user are marked as pivot. The iterator is implemented to be "sensitive", it
 * to travel more efficient "near" the pivot.
 *
 * <ul><li>IncrementalObject|IncrementalPrimitive : Adapted from Delta concept
 * of Threerings. And object which tracks change in its propertly by hash. The
 * result of incremental data subtraction is primitive (IncrementalPrimitive) or
 * another IncrementalObject.
 *
 * <li>IncrementalSequential ~ List: Adapted from the MVCC model in :
 * High-Performance Concurrency Control Mechanisms for Main-Memory Databases
 *
 * <li>IncrementalBuffer and IncrementalStream are the two derived from the
 * above. Pivots is the embeded indexes over the stream.
 *
 * <li>IncrementalTree is the Tree which structure is changed overtime. It's can
 * be considered a branch from original tree from a root "pivot". Adaptive tree
 * also has pivot or "axis" to navigate and adapt agaisnt the original tree or
 * its derived version. This implementaion use Myer's diff algorithm as google
 * tree diff algorithm. Some additional methods are provided to make adaptive
 * (not just tree structure) more easily.
 *
 * <li>IncrementalGraph is a sub-graph of the graph which changed over time. Its
 * internal or structure can be osevered and it also can adapt against the
 * orignal graph or the topological graph of the same graph. The
 * IncrementalGraph's topology maintaining is quite overhead because topological
 * extraction and clustering for big complex graph is NOT a simple task.
 * However, the graph is easy to be partitioned and resolve conflicts against
 * its self. That's why topological extraction happens not frequent, allow the
 * data structure to work efficient enough and boost over operation over it.
 * IncrementalGraph is the main data structure for the dataflow manager who
 * manage all the operation of the system, eg: How to push resource toward a
 * target node...!
 *
 * <li>Operational transformation:
 * http://en.wikipedia.org/wiki/Operational_transformation
 * .OperationalTransformation is the interface corporate with incremental data,
 * provide operation over the data!</ul>
 *
 * <p>Outside of AtomCore also provide more Incremental data structure. In
 * AtomAI provide : IncrementalMarkovModel and IncrementalANN structure.
 * AtomCoEditor use "Incremental" - multi versioned xml database.
 *
 * @author cuong.nguyenmanh2
 */
public interface IIncremental {
}
