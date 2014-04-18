/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import java.io.Serializable;

/**
 * LODEntropy will be attach to a spatial or entity to determinate the LOD of
 * it. This is the units and working data of the system.
 *
 * <p>Note that LODEntropy can also "contains" additional infos that need for
 * LOD operations. For example:<ul>
 *
 * <li>it can form a "graph", like when used in hierachical tweaker.
 *
 * <li>it can "link" to a simplized mesh that shared between serveral meshes.
 * Later the system may interest in this connections when it tweak lod of one or
 * serveral mesh...
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
