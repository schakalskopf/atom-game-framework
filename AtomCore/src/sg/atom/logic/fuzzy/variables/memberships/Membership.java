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
 * // The above copyright notice and this permission notice shall be included in
 * // all copies or substantial portions of the Software.
 *
 * // THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * // IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * // FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE // AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * // LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, // OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN // THE SOFTWARE.
 */
package sg.atom.logic.fuzzy.variables.memberships;

import sg.atom.logic.fuzzy.variables.IllegalRangeException;

/**
 * Implementation a membership function that supports fuzzy/unfuzzy operations.
 *
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public interface Membership {

    /**
     * Fuzzyfies an observed value into a degree of confidence associated to the
     * membership function.
     *
     * @param value the observed value.
     * @return the degree of confidence associated to this membership.
     * @throws IllegalRangeException thrown if the observed value is out of the
     * range of the membership.
     */
    double fuzzy(double value) throws IllegalRangeException;

    /**
     * Unfuzzyfies a degree of confidence into a concrete value associated to
     * the membership range.
     *
     * @param confidence the confidence associated to the membership.
     * @return the concrete value associated to the confidence.
     * @throws IllegalRangeException thrown if the degree of confidence is out
     * of the range of the membership.
     */
    double unfuzzy(double confidence) throws IllegalRangeException;
}