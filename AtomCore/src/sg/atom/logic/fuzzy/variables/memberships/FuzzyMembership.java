/**
// The MIT License - Copyright (c) 2007 Universitetet i Oslo

// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
*/ 
package sg.atom.logic.fuzzy.variables.memberships;

import static com.google.common.collect.Lists.newArrayList;
import static sg.atom.logic.fuzzy.variables.IllegalRangeException.checkRange;
import static sg.atom.logic.fuzzy.variables.memberships.LineMembership.newLine;
import static sg.atom.logic.fuzzy.variables.solvers.Solvers.DEFAULT;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.List;

import sg.atom.logic.fuzzy.variables.IllegalRangeException;

/**
 * Implementation of a Fuzzy membership function. A fuzzy membership is an
 * ordered sequence of lines whose Y values are within range [0,1].
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public class FuzzyMembership implements Membership {

    private final List<LineMembership> lines = newArrayList();
    private double min = POSITIVE_INFINITY, max = NEGATIVE_INFINITY,
            floor = POSITIVE_INFINITY, ceil = NEGATIVE_INFINITY;

    private FuzzyMembership(PointMembership... point) {
        PointMembership pred = null;
        for (PointMembership suc : point) {
            if (pred != null) {
                if (pred.x() > suc.x())
                    throw new IllegalMembershipException(
                            "Membership indexes should be ordered");
                lines.add(newLine(pred, suc));
            }
            min = min(min, suc.x());
            max = max(max, suc.x());
            floor = min(floor, suc.y());
            ceil = max(ceil, suc.y());
            pred = suc;
        }
    }

    public double fuzzy(double x) throws IllegalRangeException {
        checkRange(x, min, max);
        for (LineMembership l : lines)
            try {
                return l.fuzzy(x);
            } catch (IllegalRangeException e) {
                continue;
            }
        return NaN;
    }

    public double unfuzzy(double y) throws IllegalRangeException {
        checkRange(y, floor, ceil);
        return DEFAULT.solve(trunc(y)).x();
    }

    public List<PointMembership> trunc(double y) {
        List<PointMembership> set = newArrayList();
        for (LineMembership line : lines)
            for (PointMembership p : line.trunc(y))
                if (!set.contains(p))
                    set.add(p);
        return set;
    }

    public String toString() {
        return lines.toString();
    }
    
    public static final FuzzyMembership newFuzzyMembership(
            PointMembership... points) {
        return new FuzzyMembership(points);
    }
}