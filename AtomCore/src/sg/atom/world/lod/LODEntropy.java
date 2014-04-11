/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import java.io.Serializable;

/**
 * LODEntropy will be attach to spatial or entity to determinate the LOD of it.
 *
 * @author cuong.nguyenmanh2
 */
public abstract class LODEntropy implements Comparable<LODEntropy>, Serializable {

    public static String keyword = "_lodEntropy";
    TIntArrayList discreteValues;
    TFloatArrayList continousValues;

    public TFloatArrayList getContinousValues() {
        return continousValues;
    }

    public TIntArrayList getDiscreteValues() {
        return discreteValues;
    }
}
