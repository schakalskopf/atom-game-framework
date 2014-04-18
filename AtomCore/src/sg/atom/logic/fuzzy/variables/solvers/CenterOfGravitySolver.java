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
package sg.atom.logic.fuzzy.variables.solvers;

import static sg.atom.logic.fuzzy.variables.memberships.PointMembership.newPoint;
import static sg.atom.logic.fuzzy.variables.solvers.Solvers.LMM;

import java.util.List;

import sg.atom.logic.fuzzy.variables.memberships.PointMembership;

/**
 * Implementation of solver that computes the center of gravity (CoG) (also
 * known as center of mass) of a list of points.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public class CenterOfGravitySolver implements Solver {

    public PointMembership solve(List<PointMembership> points) {
        double x = 0, y = 0;
        for (PointMembership p : points) {
            x += p.x();
            y += p.y();
        }
        return y == 0 ? LMM.solve(points) : newPoint(x / points.size(), y
                / points.size());
    }
}