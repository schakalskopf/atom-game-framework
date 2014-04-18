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
package sg.atom.logic.fuzzy.rules.conditions;

import static com.google.common.collect.Lists.newArrayList;
import com.google.common.collect.Table;

import java.util.List;

import sg.atom.logic.fuzzy.rules.functions.FuzzyFunction;

/**
 * Implementation of a fuzzy operator.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public class FuzzyOperator<K, V> implements FuzzyCondition<K, V> {
    private final FuzzyFunction function;
    private final FuzzyCondition<K, V>[] operators;

    private FuzzyOperator(FuzzyFunction f, FuzzyCondition<K, V>[] ops) {
        function = f;
        operators = ops;
    }

    public Double evaluate(Table<K, V, Double> provider) {
        List<Double> parameters = newArrayList();
        for (FuzzyCondition<K, V> child : operators)
            parameters.add(child.evaluate(provider));
        return function.evaluate(parameters);
    }

    public static final <K, V> FuzzyCondition<K, V> test(
            FuzzyFunction function, FuzzyCondition<K, V>... operators) {
        return new FuzzyOperator(function, operators);
    }
}
