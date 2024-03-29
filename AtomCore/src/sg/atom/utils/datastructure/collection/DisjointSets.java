/*
 *	Copyright (C) 2010 Visualization & Graphics Lab (VGL), Indian Institute of Science
 *
 *	This file is part of libRG, a library to compute Reeb graphs.
 *
 *	libRG is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU Lesser General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	libRG is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU Lesser General Public License for more details.
 *
 *	You should have received a copy of the GNU Lesser General Public License
 *	along with libRG.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Author(s):	Harish Doraiswamy
 *	Version	 :	1.0
 *
 *	Modified by : -- 
 *	Date : --
 *	Changes  : --
 */
package sg.atom.utils.datastructure.collection;

import java.util.HashMap;

/**
 * Disjoint set class, using union by rank and path compression.
 *
 * Adapted using the code by Mark Allen Weiss
 */
@Deprecated
public class DisjointSets {

    HashMap<Integer, Integer> set;

    public DisjointSets() {
        set = new HashMap<Integer, Integer>();
    }

    public void clear() {
        set.clear();
    }

    /**
     * Union two disjoint sets using the height heuristic. root1 and root2 are
     * distinct and represent set names.
     *
     * @param root1 the root of set 1.
     * @param root2 the root of set 2.
     */
    public void union(int root1, int root2) {
        if (root1 == root2) {
            return;
        }

        int r1 = set.get(root1);
        int r2 = set.get(root2);

        if (r2 < r1) {
            // root2 is deeper
            // Make root2 new root
            set.remove(root1);
            set.put(root1, root2);
        } else {
            if (r1 == r2) {
                // Update height if same
                r1--;
                set.remove(root1);
                set.put(root1, r1);
            }
            // Make root1 new root
            set.remove(root2);
            set.put(root2, root1);
        }
    }

    /**
     * Perform a find with path compression.
     *
     * @param x the element being searched for.
     * @return the set containing x.
     */
    public int find(int x) {
        Integer f = set.get(x);
        if (f == null) {
            f = -1;
            set.put(x, -1);
        }
        if (f < 0) {
            return x;
        } else {
            int xx = find(f);
            set.remove(x);
            set.put(x, xx);
            return xx;
        }
    }
}