/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.datastructure.state.automata;

import java.util.List;
import java.util.Map;

/**
 *
 * @author cuong.nguyenmanh2
 */
// Regular expressions ----------------------------------------------
//
// Abstract syntax of regular expressions
//    r ::= A | r1 r2 | (r1|r2) | r*
//
public abstract class Regex {

    abstract public Nfa mkNfa(Nfa.NameSource names);

    class Sym extends Regex {

        String sym;

        public Sym(String sym) {
            this.sym = sym;
        }

        // The resulting nfa0 has form s0s -sym-> s0e
        public Nfa mkNfa(Nfa.NameSource names) {
            Integer s0s = names.next();
            Integer s0e = names.next();
            Nfa nfa0 = new Nfa(s0s, s0e);
            nfa0.addTrans(s0s, sym, s0e);
            return nfa0;
        }
    }

    class Seq extends Regex {

        Regex r1, r2;

        public Seq(Regex r1, Regex r2) {
            this.r1 = r1;
            this.r2 = r2;
        }

        // If   nfa1 has form s1s ----> s1e 
        // and  nfa2 has form s2s ----> s2e 
        // then nfa0 has form s1s ----> s1e -eps-> s2s ----> s2e
        public Nfa mkNfa(Nfa.NameSource names) {
            Nfa nfa1 = r1.mkNfa(names);
            Nfa nfa2 = r2.mkNfa(names);
            Nfa nfa0 = new Nfa(nfa1.getStart(), nfa2.getExit());
            for (Map.Entry<Integer, List<Nfa.Transition>> entry : nfa1.getTrans().entrySet()) {
                nfa0.addTrans(entry);
            }
            for (Map.Entry<Integer, List<Nfa.Transition>> entry : nfa2.getTrans().entrySet()) {
                nfa0.addTrans(entry);
            }
            nfa0.addTrans(nfa1.getExit(), null, nfa2.getStart());
            return nfa0;
        }
    }

    class Alt extends Regex {

        Regex r1, r2;

        public Alt(Regex r1, Regex r2) {
            this.r1 = r1;
            this.r2 = r2;
        }

        // If   nfa1 has form s1s ----> s1e 
        // and  nfa2 has form s2s ----> s2e 
        // then nfa0 has form s0s -eps-> s1s ----> s1e -eps-> s0e
        //                    s0s -eps-> s2s ----> s2e -eps-> s0e
        public Nfa mkNfa(Nfa.NameSource names) {
            Nfa nfa1 = r1.mkNfa(names);
            Nfa nfa2 = r2.mkNfa(names);
            Integer s0s = names.next();
            Integer s0e = names.next();
            Nfa nfa0 = new Nfa(s0s, s0e);
            for (Map.Entry<Integer, List<Nfa.Transition>> entry : nfa1.getTrans().entrySet()) {
                nfa0.addTrans(entry);
            }
            for (Map.Entry<Integer, List<Nfa.Transition>> entry : nfa2.getTrans().entrySet()) {
                nfa0.addTrans(entry);
            }
            nfa0.addTrans(s0s, null, nfa1.getStart());
            nfa0.addTrans(s0s, null, nfa2.getStart());
            nfa0.addTrans(nfa1.getExit(), null, s0e);
            nfa0.addTrans(nfa2.getExit(), null, s0e);
            return nfa0;
        }
    }

    class Star extends Regex {

        Regex r;

        public Star(Regex r) {
            this.r = r;
        }

        // If   nfa1 has form s1s ----> s1e 
        // then nfa0 has form s0s ----> s0s
        //                    s0s -eps-> s1s
        //                    s1e -eps-> s0s
        public Nfa mkNfa(Nfa.NameSource names) {
            Nfa nfa1 = r.mkNfa(names);
            Integer s0s = names.next();
            Nfa nfa0 = new Nfa(s0s, s0s);
            for (Map.Entry<Integer, List<Nfa.Transition>> entry : nfa1.getTrans().entrySet()) {
                nfa0.addTrans(entry);
            }
            nfa0.addTrans(s0s, null, nfa1.getStart());
            nfa0.addTrans(nfa1.getExit(), null, s0s);
            return nfa0;
        }
    }
// Trying the RE->NFA->DFA translation on three regular expressions
//
//class Example139 {
//  public static void main(String[] args) 
//    throws IOException {
//    Regex a = new Sym("A");
//    Regex b = new Sym("B");
//    Regex r = new Seq(new Star(new Alt(a, b)), new Seq(a, b));
//    // The regular expression (a|b)*ab
//    buildAndShow("dfa1.dot", r);
//    // The regular expression ((a|b)*ab)*
//    buildAndShow("dfa2.dot", new Star(r));
//    // The regular expression ((a|b)*ab)((a|b)*ab)
//    buildAndShow("dfa3.dot", new Seq(r, r));
//  }
//
//  public static void buildAndShow(String filename, Regex r) 
//    throws IOException {
//    Nfa nfa = r.mkNfa(new Nfa.NameSource());
//    System.out.println(nfa);
//    Dfa dfa = nfa.toDfa();
//    System.out.println(dfa);
//    System.out.println("Writing DFA graph to file " + filename);
//    dfa.writeDot(filename);
//    System.out.println();
//  }
}
