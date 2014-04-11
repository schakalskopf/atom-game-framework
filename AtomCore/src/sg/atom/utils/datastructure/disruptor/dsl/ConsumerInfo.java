package sg.atom.utils.datastructure.disruptor.dsl;

import sg.atom.utils.datastructure.disruptor.sequence.Sequence;
import sg.atom.utils.datastructure.disruptor.sequence.SequenceBarrier;

import java.util.concurrent.Executor;

interface ConsumerInfo
{
    Sequence[] getSequences();

    SequenceBarrier getBarrier();

    boolean isEndOfChain();

    void start(Executor executor);

    void halt();

    void markAsUsedInBarrier();

    boolean isRunning();
}
