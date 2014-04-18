/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.datastructure.state;

/**
 *
 * @author cuong.nguyenmanh2
 */

/* **********************************
 *            complete DFA 
 *          2004-04-20
 ************************************/
/**
 * This class implements deterministic complete finite automata. The transition
 * function is represented by a double array
 * <code>next[][]</code>. The set of terminal states is given by a partition of
 * the set of states (class {@link Partition Partition}). A state
 * <code>q</code> is terminal if
 * <code>terminal.blockName[p]=1</code> (the default value is
 * <code>0</code>).
 */
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class DFA {

    int[][] next;          // the nextstate function
    int initial;           // the initial state
    Partition terminal;    // the partition of terminal states
    Alphabet alphabet;     // the alphabet
    int nbStates;
    int nbLetters;
    //    int[] info;            // for the LR analysis
    int sink;              // the sink (default -1)

    /**
     * creates a DFA with
     * <code>n</code> states and
     * <code>k</code> letters.
     */
    public DFA(int n, int k) {
        this(n, new Alphabet(k));
    }

    /**
     * creates a DFA with n states and alphabet a.
     */
    public DFA(int n, Alphabet a) {
        nbStates = n;
        alphabet = a;
        nbLetters = alphabet.size();
        next = new int[nbStates][nbLetters];
        terminal = new Partition(nbStates);
        //info = new int[n];
        sink = -1;
    }

    /**
     * returns the state reached from state
     * <code>p</code> after reading the word
     * <code>w</code>.
     *
     * @param p starting state
     * @param w input word (w is not <code>null</code>
     * @return state reached
     */
    public int next(int p, String w) {
        return next(p, alphabet.toShort(w));
    }

    int next(int p, short[] w) {
        //transition by a word   w in a DFA
        for (int i = 0; i < w.length; i++) {
            p = next[p][w[i]];
            if (p == -1) {
                break;
            }
        }
        return p;
    }

    /**
     * Minimizes the automaton using the method m.
     */
    public static DFA minimize(DFA a, Minimizer m) throws Exception {
        return m.minimize(a);
    }

    public DFA minimize(Minimizer m) throws Exception {
        return m.minimize(this);
    }

    int index(int[] c) {
        int m = -1;
        for (int i = 0; i < c.length; i++) {
            m = Math.max(m, c[i]);
        }
        return 1 + m;
    }

    /**
     * Returns the quotient of the DFA by the partition
     * <code>c</code>
     *
     * @param c a partition of the state set compatible with the DFA
     * @return the new DFA.
     */
    public DFA quotient(int[] c) {
        int m = index(c);
        int[] t = new int[m];
        DFA s = new DFA(m, alphabet);
        s.initial = c[initial];
        for (int p = 0; p < nbStates; p++) {
            int q = c[p];
            for (int u = 0; u < nbLetters; u++) {
                s.next[q][u] = c[next[p][u]];
            }
            t[q] = terminal.blockName[p];
        }
        s.terminal = new Partition(t);
        return s;
    }

    public DFA quotient(Partition p) {
        return quotient(p.blockName);
    }

    static DFA makeit(int choix) {
        Alphabet al = new Alphabet(2);
        if (choix == 1) {
            DFA a = new DFA(7, al);
            int[] t = new int[]{0, 1, 1, 1, 1, 0, 1};
            a.next[0][0] = 0;
            a.next[0][1] = 0;
            a.next[1][0] = 2;
            a.next[1][1] = 5;
            a.next[2][0] = 3;
            a.next[2][1] = 0;
            a.next[3][0] = 3;
            a.next[3][1] = 4;
            a.next[4][0] = 3;
            a.next[4][1] = 0;
            a.next[5][0] = 6;
            a.next[5][1] = 0;
            a.next[6][0] = 0;
            a.next[6][1] = 5;
            //a.t[1]=a.t[2]=a.t[3]= a.t[4]= a.t[6]=1;
            a.terminal = new Partition(t);
            return a;
        } else {
            DFA a = new DFA(7, al);
            a.next[0][0] = 1;
            a.next[0][1] = 2;
            a.next[1][0] = 3;
            a.next[1][1] = 5;
            a.next[2][0] = 5;
            a.next[2][1] = 4;
            a.next[3][0] = 6;
            a.next[3][1] = 6;
            a.next[4][0] = 6;
            a.next[4][1] = 4;
            a.next[5][0] = 6;
            a.next[5][1] = 6;
            a.next[6][0] = 6;
            a.next[6][1] = 6;
            //a.t[1]=a.t[3]=a.t[5]= a.t[6]=1;
            return a;
        }
    }

    public String toString() {
        String s = "initial=" + initial + "\n";
        //s +="terminal="+terminal.toString()+"\n";
        s += "nbStates=" + nbStates + "\n";
        s += "    ";
        for (int c = 0; c < nbLetters; c++) {
            s += alphabet.toChar(c) + "  ";
        }
        s += "\n";
        StringBuffer u = new StringBuffer(s);
        for (int i = 0; i < nbStates; i++) {
            //s+=i+" ";
            u.append(i + ": " + (i > 9 ? "" : " "));
            for (int c = 0; c < nbLetters; c++) {
                //s+=next[i][c]+" ";System.out.print(s);
                u.append(next[i][c] + " " + (next[i][c] > 9 ? "" : " "));
            }
            //s+="\n";
            u.append("\n");
        }
        s = u.toString() + "terminals = " + terminal.blockList[1];
        return s;
    }

    /**
     * Viterbi simple spell checker, based on Hamming distance. For simple
     * intro: www.cut-the-knot.org/do_you_know/Strings.shtml.
     *
     * @author Dale Gerdemann (Based on Exercise 1.3.1, Berstel & Perrin,
     * Algorithms on Words)
     *
     * Note that this is just an exercise to familiarize students with the
     * Viterbi algorithm, which is very widely useful in CL. A spell checker
     * based on Hamming distance would not be terribly useful. Consider "dirty"
     * and "thirty" are not even comparable, since they are not the same length.
     * And if we reduce the length of "thirty" to "thirt", we end up with a
     * Hamming distance of 5; i.e., as distant as they possibly could be. The
     * good thing about Hamming distance, however, is that it is extremely
     * simple, so that students can learn the relatively difficult Viterbi
     * algorithm without other complications getting in the way.
     */
    // Return type changed
    public String viterbi(String w) {
        int[][] d = new int[nbStates][w.length() + 1];


        // ADDED for solution 3
        // We want a 2 dimensional array of back pointers
        // parallel the 2 dimensional array <code>d</code>. Then when a
        // minimum value is recorded at d[i][j], we can record at
        // bkPtrs[i][j] the previous state number and the label of
        // the transition taken to achieve this minimal value.
        BkPtrList[][] bkPtrs = new BkPtrList[nbStates][w.length() + 1];
        for (int i = 0; i < nbStates; i++) {
            for (int j = 0; j < w.length() + 1; j++) {
                bkPtrs[i][j] = new BkPtrList();
            }
        }

        // For each state p and string position i, we try to find the
        // minimum Hamming distance. To initialize, we set all distances
        // to the maximal value, so that whenever we find a shorter
        // distance, we can replace the higher value with the lower.
        for (int p = 0; p < nbStates; p++) {
            for (int i = 0; i < w.length() + 1; i++) {
                d[p][i] = Integer.MAX_VALUE;;
            }
        }

        // No cost to start in initial state. Otherwise maximal cost.
        d[initial][0] = 0;

        for (int i = 1; i <= w.length(); i++) {
            for (int p = 0; p < nbStates; p++) {
                if (d[p][i - 1] != Integer.MAX_VALUE) {
                    for (int label = 0; label < next[p].length; label++) {
                        int transitionCost;
                        if (alphabet.toChar(label) == w.charAt(i - 1)) {
                            transitionCost = 0;
                        } else {
                            transitionCost = 1;
                        }
                        int q = next[p][label];
                        int qiCost = d[q][i];
                        int pi_1Cost = d[p][i - 1];
                        int newCost = pi_1Cost + transitionCost;

                        if (newCost <= qiCost) {                // MODIFIED for solution 3
                            d[q][i] = newCost;
                            // ADDED for solution 3
                            if (newCost < qiCost) {   // found new smaller one,
                                bkPtrs[q][i].bkPtrs.clear();   // start over collecting
                            }
                            bkPtrs[q][i].bkPtrs.add(new BkPtr(p, alphabet.toChar(label)));
                        }
                    }
                }
            }
        }


        // Prints out the matrix d    
        System.out.println();
        for (int p = 0; p < nbStates; p++) {
            System.out.print(p + ": " + (p > 9 ? "" : " "));
            for (int i = 0; i < w.length() + 1; i++) {
                int val = (d[p][i] > 100 ? 9 : d[p][i]);
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println();

        // ADDED for Solution 3
        // Print out the matrix bkPtrs
        System.out.println("Pos |  0    |  1    |  2    |  3    |  4    |");
        System.out.println("---------------------------------------------");
        for (int p = 0; p < nbStates; p++) {
            System.out.print(p + ": " + (p > 9 ? "" : " "));
            List<Iterator> li = new ArrayList<Iterator>(w.length() + 1);
            boolean hasNext = false;
            for (int i = 0; i < w.length() + 1; i++) {
                Iterator it = bkPtrs[p][i].bkPtrs.iterator();
                li.add(it);
                if (it.hasNext()) {
                    System.out.print("|" + it.next() + " ");
                } else {
                    System.out.print("|       ");
                }
                if (it.hasNext()) {
                    hasNext = true;
                }
            }
            System.out.print("|");
            while (hasNext) {
                System.out.println();
                hasNext = false;
                System.out.print("    ");
                for (int i = 0; i < w.length() + 1; i++) {
                    Iterator it = li.get(i);
                    if (it.hasNext()) {
                        System.out.print("|" + it.next() + " ");
                    } else {
                        System.out.print("|       ");
                    }
                    if (it.hasNext()) {
                        hasNext = true;
                    }
                }
                System.out.print("|");

            }
            System.out.println("\n---------------------------------------------");
        }

        int minCost = Integer.MAX_VALUE;
        int minState = 0;                            // ADDED for solution 1
        for (int p = 0; p < nbStates; p++) {
            if (terminal.blockName[p] == 1 // Final State
                    && d[p][w.length()] < minCost) {
                minCost = d[p][w.length()];
                minState = p;                          // ADDED for soution 1
            }
        }

        // Solution 2
        System.out.println("\nSolution 2:");
        getClosest(d, minCost, w);


        // Solution 3
        System.out.println("\nSolution 3:");
        Set<String> nearest = getNearest(d, bkPtrs, minCost, w);
        System.out.println(nearest);


        System.out.println();

        // Solution 1: Solves the original problem. Returns one word
        // Trace back through the matrix d. Alternatively, we could
        // collect more info in the first place
        List<Character> result = new LinkedList<Character>();
        stringPosLoop:
        for (int i = w.length() - 1; i >= 0; i--) {
            for (int p = 0; p < nbStates; p++) {
                for (int j = 0; j < nbLetters; j++) {
                    if (d[p][i] == minCost - 1
                            && next[p][j] == minState
                            && alphabet.toChar(j) != w.charAt(i)) {
                        minState = p;
                        minCost--;
                        result.add(0, alphabet.toChar(j));
                        continue stringPosLoop;
                    }
                }
                if (d[p][i] == minCost
                        && next[p][alphabet.toShort(w.charAt(i))] == minState) {
                    minState = p;
                    result.add(0, w.charAt(i));
                    continue stringPosLoop;
                }
            }
        }

        // For solution 1. Result is found in List<Character>. Make a
        // String now, and return it.
        StringBuffer sb = new StringBuffer();
        for (Character c : result) {
            sb.append(c.charValue());
        }
        return sb.toString();
    }

    // Solution 2
    // getClosest is a recursive procedure for printing out all of the
    // closest. It could easily be modified to collect all solutions
    // instead of just printing them. We start at the final string
    // position and work our way backwards. At each recursive call we
    // have a suffix of one of the solutions. This suffix may be used
    // multiple times in several recursive calls. To allow sharing of
    // this suffix across recursive calls, we use a singly linked list:
    // see Node class below.
    public void getClosest(int[][] d, int minCost, String w) {
        for (int p = 0; p < nbStates; p++) {
            if (terminal.blockName[p] == 1 // Final State
                    && d[p][w.length()] == minCost) {
                getClosest(d, w.length(), w, p, minCost, null);
            }
        }
    }

    // getClosest: recursive part for solution 2
    // d is the matrix produced by the Viterbi algorithm. 
    // w is the test word and pos is a string position. String positions
    // are the positions between the characters in w. The character in w
    // occuring directly before string position pos is w.getChar(pos-1).
    // minState is not necessarily minimal at a given pos, but it is a
    // state that leads to a minimal value at the final string position.
    public void getClosest(int[][] d, int pos, String w,
            int minState, int minCost, Node suffix) {
        if (pos == 0) { // initial string position: base case
            System.out.println(suffix);
            return;
        }
        for (int p = 0; p < nbStates; p++) {
            // If the minCost at this position is greater than 0, we can
            // look for a mismatch here.
            if (minCost > 0) {
                for (int j = 0; j < nbLetters; j++) {
                    if (d[p][pos - 1] == minCost - 1 // cost at previous string pos
                            // was 1 less. A transition from
                            && // here must be a mismach
                            next[p][j] == minState) {
                        getClosest(d, pos - 1, w, p, minCost - 1,
                                new Node(alphabet.toChar(j), suffix));
                    }
                }
            }
            // We also look for matches from the previous string position
            if (d[p][pos - 1] == minCost
                    && next[p][alphabet.toShort(w.charAt(pos - 1))] == minState) {
                getClosest(d, pos - 1, w, p, minCost,
                        new Node(w.charAt(pos - 1), suffix));
            }
        }
    }

    //  For solution 3
    //  Wrapper class to store a List<BkPtr>. This allows the lists of
    //  back pointers to be stored in an ordinary array, which wouldn't
    //  be allowed for a parameterized type.
    class BkPtrList {

        List<BkPtr> bkPtrs;

        public BkPtrList() {
            bkPtrs = new LinkedList<BkPtr>();
        }
    }

    // Back pointer class for solution 3
    class BkPtr {

        int prevState;
        char label;

        public BkPtr(int prevState, char label) {
            this.prevState = prevState;
            this.label = label;
        }

        public String toString() {
            return "(" + prevState + "," + label + ")"
                    + (prevState < 10 ? " " : "");  // add a little padding
        }
    }

    // For solution 2. 
    // In solution 2, the Node class is used to make a singly linked list
    // for each output string. The Node class has the advantage of being
    // immutable. There can be no modification of  list of characters
    // collected in one recursive call, which would affect the list
    // collected in another recursive call. Singly linked lists also use
    // tail sharing: if <code>suffix</code> is a Node, then <code>new
    // Node('a',suffix)</code> and <code>new Node('b',suffix)</code>
    // create two lists which share a suffix.
    class Node {

        char c;
        Node next;

        Node(char c, Node next) {
            this.c = c;
            this.next = next;
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            Node n = this;
            while (n != null) {
                sb.append(n.c);
                n = n.next;
            }
            return sb.toString();
        }
    }

    // Solution 3 : Inspired by Danielle Williams
    // This top level method iterates through each minimal final state.
    // For each of these the recursive method is called.
    public Set<String> getNearest(int[][] d, BkPtrList[][] bkPtrs,
            int minCost, String w) {
        Set<String> result = new TreeSet<String>();
        for (int p = 0; p < nbStates; p++) {
            if (terminal.blockName[p] == 1 // Final State
                    && d[p][w.length()] == minCost) {
                result.addAll(getNearest(bkPtrs, p, w.length()));
            }
        }
        return result;
    }

    public Set<String> getNearest(BkPtrList[][] bkPtrs, int p, int i) {
        Set<String> result = new TreeSet<String>();
        if (i == 0) {
            result.add("");
            return result;
        }
        for (BkPtr bp : bkPtrs[p][i].bkPtrs) {
            Set<String> prefixes = getNearest(bkPtrs, bp.prevState, i - 1);
            for (String s : prefixes) {
                result.add(s + bp.label);
            }
        }
        return result;
    }

    /*
     public static void main(String[] args) throws Exception {
     DFA a,b,c,d;
     a = makeit(1);
     //a.show();
     System.out.println(a);
     b = minimize(a, new NMinimizer());
     System.out.println(b);

     }
     */
}
