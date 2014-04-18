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
package sg.atom.logic.fuzzy.variables;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.HashBasedTable;
import static com.google.common.collect.Maps.newHashMap;
import com.google.common.collect.Table;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementation of the variable set.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public class VariableSet<T extends Object> {
    private Map<String, Variable> variables;

    private VariableSet() {
        variables = newHashMap();
    }

    public VariableSet add(String name, Variable var) {
        variables.put(name, var);
        return this;
    }

    public VariableSet add(Variable var) {
        return add(var.name(), var);
    }

    public Variable get(String name) {
        return checkNotNull(variables.get(name), "Variable " + name + " is unknown");
    }

    public Table<String, T, Double> fuzzy(Map<String, Double> input) {
        Table<String, T, Double> output = HashBasedTable.create();
        for (Entry<String, Double> val : input.entrySet()) {
            //FIXME: Change from MapOfMap to Table?
//            Map fuzzy = get(val.getKey()).fuzzy(val.getValue());
//            output.put(val.getKey(), );
        }
        return output;
    }

    public Map<String, Double> unfuzzy(Table<String, T, Double> input) {
        Map<String, Double> output = newHashMap();
        //FIXME: Change from MapOfMap to Table?
//        for (Entry<String, Map<T, Double>> val : input.entrySet())
//            output.put(val.getKey(), get(val.getKey()).unfuzzy(val.getValue()));
        return output;
    }

    public static final VariableSet<String> newVariableSet() {
        return new VariableSet<String>();
    }
}
