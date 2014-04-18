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
package sg.atom.logic.fuzzy.variables.memberships;

import static com.google.common.collect.Lists.newArrayList;
import static sg.atom.logic.fuzzy.variables.IllegalRangeException.checkRange;
import static sg.atom.logic.fuzzy.variables.memberships.PointMembership.newPoint;
import static sg.atom.logic.fuzzy.variables.solvers.Solvers.LMM;
import static java.lang.Double.isNaN;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;

import java.util.List;

import sg.atom.logic.fuzzy.variables.IllegalRangeException;

/**
 * Implementation a membership line.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public class LineMembership implements Membership {
    private final PointMembership a, b;
    private final double delta;

    private LineMembership(PointMembership p1, PointMembership p2) {
        a = p1;
        b = p2;
        delta = (b.y() - a.y()) / (b.x() - a.x());
    }

    public PointMembership a() {
        return a;
    }

    public PointMembership b() {
        return b;
    }

    public double delta() {
        return delta;
    }

    public double fuzzy(double x) throws IllegalRangeException {
        checkRange(x, a.x(), b.x());
        double res = (x - a.x()) * delta + a.y();
        return isNaN(res) ? max(a.y(), b.y()) : res;
    }

    public double unfuzzy(double y) throws IllegalRangeException {
        checkRange(y, min(a.y(), b.y()), max(a.y(), b.y()));
        return LMM.solve(trunc(y)).x();
    }

    public List<PointMembership> trunc(double y) {
        if (y >= max(a.y(), b.y()))
            return asList(a, b);
        if (y > min(a.y(), b.y())) {
            double x = (y - a.y()) / delta + a.x();
            PointMembership p = newPoint(isNaN(x) ? a.x() : x, y);
            return delta >= 0 ? asList(a, p) : asList(p, b);
        }
        if (y == min(a.y(), b.y()))
            return delta >= 0 ? asList(a) : asList(b);
        return newArrayList();
    }

    public boolean equals(Object obj) {
        LineMembership p = (LineMembership) obj;
        return a.equals(p.a) && b.equals(p.b);
    }

    public String toString() {
        return "[" + a + ":" + b + "]";
    }

    public static LineMembership newLine(PointMembership a, PointMembership b) {
        return new LineMembership(a, b);
    }
}