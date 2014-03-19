package sg.atom.utils.space;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.List;
import java.util.Map;
//import java.util.TreeMap;
import java.util.Set;
import java.util.logging.Level;


import java.util.logging.Logger;
//import sg.atom.utils.space.TupleSpace;
import sg.atom.utils.space.extensions.XMLField;
import sg.atom.utils.space.interfaces.ITuple;
import sg.atom.utils.space.interfaces.ITupleSpace;
//import sg.atom.utils.space.interfaces.TupleSpaceException;

public class CleaningWorker extends Worker {

    public static Logger LOG;
    public static final String LOGGER = "sg.atom.utils.space.worker";
    private static final int DEF_TIME = 1000;
    /**
     * how long a tuple stays in the TS
     */
    private int tupletime;
    /**
     * lists the tuples to be removed with the time they were first seen
     */
    private Map<ITuple, Long> toRemove;
    //private List<ITuple> latestEntry;
    private static ITuple universalTemplate = new Tuple();

    public CleaningWorker(ITupleSpace ts) {
        this(ts, DEF_TIME);
    }

    public CleaningWorker(ITupleSpace ts, int delay) {
        super(ts);
        LOG = Logger.getLogger(LOGGER);
        tupletime = delay;
        toRemove = new HashMap<ITuple, Long>();
    }

    @Override
    public void work() {
        //input all the tuples matching the current template
        ITuple[] listuple;
        Long currentTime = new Long(System.currentTimeMillis());
        Long firstSeen;
        try {
            listuple = space.rdg(universalTemplate);
            if (listuple == null) {
                LOG.log(Level.INFO, "CLEANING AGENT:  TupleSpace empty.");
                space.rd(universalTemplate);
                LOG.log(Level.INFO, "CLEANING AGENT:  A job to do!!");
                listuple = space.rdg(universalTemplate);
            }
            //List<ITuple> newlist = new ArrayList<ITuple>();
            if (listuple != null) {
                LOG.log(Level.INFO, "CLEANING AGENT:  *****list********" + listuple.length);
                Set<ITuple> tupleset = new HashSet<ITuple>();
                for (ITuple it : listuple) {
                    tupleset.add(it);
                }
                LOG.log(Level.INFO, "CLEANING AGENT:  ******set*******" + tupleset.size());
                ////now we've only got one copy of each tuple to remove !
                for (ITuple it : tupleset) {
                    if (toRemove.containsKey(it)) {
                        firstSeen = toRemove.get(it);
                        if (currentTime - firstSeen > tupletime) {//if the tuple has been there more than the allowed time, we remove it.
                            LOG.log(Level.INFO, "CleaningAgent about to remove:" + it);
                            /*
                             * this short section is for testing purposes:
                             */
                            for (int i = 0; i < it.length(); i++) {
                                if (it.get(i) instanceof XMLField) {
                                    LOG.log(Level.INFO, "got an XMLF");
                                    break;
                                }
                            }


                            if (space.ing(it) == null) {
                                LOG.log(Level.INFO, "CleaningAgent could not remove:" + it);
                            } else {
                                LOG.log(Level.INFO, "CleaningAgent removes:" + it);
                            }
                            if (toRemove.remove(it) == null) {
                                LOG.log(Level.INFO, "Cleaning Agent could not remove " + it + "from its map"); // remove the tuple from the list
                            }
                        }
                    } else //the tuple has appeared recently!
                    {
                        toRemove.put(it, new Long(currentTime)); //insert the tuple with the time object (cloned just in case)
                        LOG.log(Level.INFO, "CleaningWorker found new tuple:" + it + " ; total number of tuples remembered: " + toRemove.size());

                    }

                }
                LOG.log(Level.INFO, "CLEANING AGENT:  DONE REMOVAL--------");
            }

            //add some sleep because the super class worker is just too fast
            Thread.sleep(500);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOG.log(Level.SEVERE, "CleaningWorker:" + e);
        }



    }
}
