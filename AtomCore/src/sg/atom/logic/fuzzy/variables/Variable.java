/**
 * // The MIT License - Copyright (c) 2007 Universitetet i Oslo
 *
 * // Permission is hereby granted, free of charge, to any person obtaining a
 * copy // of this software and associated documentation files (the "Software"),
 * to deal // in the Software without restriction, including without limitation
 * the rights // to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell // copies of the Software, and to permit persons to whom the
 * Software is // furnished to do so, subject to the following conditions:
 *
 * // The above copyright notice and this permission notice shall be included
 * in // all copies or substantial portions of the Software.
 *
 * // THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR // IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, // FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE // AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER // LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, // OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN // THE SOFTWARE.
 */
package sg.atom.logic.fuzzy.variables;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableSortedSet;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static sg.atom.logic.fuzzy.Configuration.LOG;
import static sg.atom.logic.fuzzy.variables.IllegalRangeException.checkRange;
import static sg.atom.logic.fuzzy.variables.memberships.FuzzyMembership.newFuzzyMembership;
import static sg.atom.logic.fuzzy.variables.memberships.PointMembership.newPoint;
import static sg.atom.logic.fuzzy.variables.solvers.Solvers.DEFAULT;
import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getLogger;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Logger;

import sg.atom.logic.fuzzy.rules.conflicts.ConflictHandler;
import sg.atom.logic.fuzzy.rules.conflicts.ConflictHandlerException;
import sg.atom.logic.fuzzy.variables.memberships.Membership;
import sg.atom.logic.fuzzy.variables.memberships.PointMembership;

/**
 * Implementation of a literal variable in fuzzy logic.
 *
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public class Variable<L> {

    private final Logger log = getLogger("funzy.variable");
    private final String name;
    private final double min, max, fl, ce;
    protected final Map<L, Membership> members;
    private ConflictHandler conflict = new ConflictHandlerException();

    protected Variable(String identifier, double minimum, double maximum,
            double floor, double ceil, Map<L, Membership> memberships) {
        checkRange(minimum, maximum);
        name = identifier;
        min = minimum;
        max = maximum;
        fl = floor;
        ce = ceil;
        members = memberships;
    }

    public final double min() {
        return min;
    }

    public final double max() {
        return max;
    }

    public final double floor() {
        return fl;
    }

    public final double ceil() {
        return ce;
    }

    public final String name() {
        return name;
    }

    public Variable conflictHandler(ConflictHandler handler) {
        conflict = handler;
        return this;
    }

    public ConflictHandler conflictHandler() {
        return conflict;
    }

    public Variable<L> add(L key, Membership value) {
        if (LOG && log.isLoggable(WARNING) && members.get(key) != null) {
            log.warning("A membership " + key
                    + " is already defined for variable " + name);
        }
        members.put(key, value);
        return this;
    }

    public Variable<L> triangle(L key, double a, double b, double c) {
        return add(key, newFuzzyMembership(newPoint(a, fl), newPoint(b, ce),
                newPoint(c, fl)));
    }

    public Variable<L> trapezoid(L key, double a, double b, double c,
            double d) {
        return add(key, newFuzzyMembership(newPoint(a, fl), newPoint(b, ce),
                newPoint(c, ce), newPoint(d, fl)));
    }

    public Variable<L> increase(L key, double a, double b) {
        return b == max ? triangle(key, a, b, max) : trapezoid(key, a, b,
                max, max);
    }

    public Variable<L> decrease(L key, double a, double b) {
        return a == min ? triangle(key, min, a, b) : trapezoid(key, min,
                min, a, b);
    }

    public Variable<L> addAfter(L key, double a) {
        return increase(key, a, a);
    }

    public Variable<L> addBefore(L key, double a) {
        return decrease(key, a, a);
    }

    public Map<L, Double> fuzzy(double value) throws IllegalRangeException {
        final Map<L, Double> memberships = newHashMap();
        for (Entry<L, Membership> m : members.entrySet()) {
            try {
                memberships.put(m.getKey(), m.getValue().fuzzy(value));
            } catch (IllegalRangeException e) {
                memberships.put(m.getKey(), floor());
                continue;
            }
        }
        return memberships;
    }

    public double unfuzzy(Map<L, Double> confidences)
            throws IllegalRangeException {
        Set<PointMembership> points = ImmutableSortedSet.of();

        for (Entry<L, Double> confidence : checkNotNull(confidences,
                "Confidence values cannot be null").entrySet()) {
            points.add(newPoint(members.get(confidence.getKey()).unfuzzy(
                    confidence.getValue()), confidence.getValue()));
        }
        return DEFAULT.solve(newArrayList(points)).x();
    }

    public static final <L extends Enum<L>> Variable variable(
            Class<L> literals, String name, double min, double max) {
        return new Variable<L>(name, min, max, 0, 1,
                new EnumMap<L, Membership>(literals));
    }

    public static final <L extends Enum<L>> Variable variable(
            Class<L> literals, double min, double max) {
        return variable(literals, literals.getSimpleName(), min, max);
    }

    public static final <L> Variable variable(String name, double min,
            double max) {
        return new Variable<L>(name, min, max, 0, 1,
                new HashMap<L, Membership>());
    }
}