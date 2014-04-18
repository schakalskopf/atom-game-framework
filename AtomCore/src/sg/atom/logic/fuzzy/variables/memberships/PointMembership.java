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

import static sg.atom.logic.fuzzy.variables.IllegalRangeException.checkRange;
import sg.atom.logic.fuzzy.variables.IllegalRangeException;

/**
 * Implementation a membership point.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public class PointMembership implements Membership, Comparable<PointMembership> {
	private final double x, y;

	private PointMembership(double abs, double ord) {
		x = abs;
		y = ord;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public double unfuzzy(double value) throws IllegalRangeException {
		checkRange(value, y, y);
		return x ;
	}

	public double fuzzy(double value) throws IllegalRangeException {
		checkRange(value, x, x);
		return y;
	}

	public boolean equals(Object obj) {
		PointMembership p = (PointMembership) obj;
		return x == p.x && y == p.y;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}

    public int compareTo(PointMembership o) {
        if (x<o.x)
            return -1;
        if (x==o.x)
            return (int) (y-o.y);
        return 1;
    }
    
	public static final PointMembership newPoint(double x, double y) {
		return new PointMembership(x, y);
	}

}