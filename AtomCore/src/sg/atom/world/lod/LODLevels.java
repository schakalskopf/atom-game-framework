/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Construct LOD levels by builder and composing. The original LODEntropy called
 * initial Entropy of each level.
 *
 * @author cuong.nguyenmanh2
 */
public class LODLevels extends Ordering<LODEntropy> implements Iterable<LODEntropy> {

    ArrayList<LODEntropy> originalEntropies;

    @Override
    public Iterator<LODEntropy> iterator() {
        return originalEntropies.iterator();
    }

    @Override
    public int compare(LODEntropy lod1, LODEntropy lod2) {
        return lod1.compareTo(lod2);
    }
}
