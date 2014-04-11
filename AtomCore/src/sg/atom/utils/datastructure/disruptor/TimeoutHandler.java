package sg.atom.utils.datastructure.disruptor;

public interface TimeoutHandler
{
    void onTimeout(long sequence) throws Exception;
}
