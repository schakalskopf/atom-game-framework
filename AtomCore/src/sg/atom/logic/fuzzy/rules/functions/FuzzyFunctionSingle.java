package sg.atom.logic.fuzzy.rules.functions;

import static com.google.common.collect.Iterables.getOnlyElement;

import java.util.List;

/**
 * Implementation of a fuzzy operator requiring exactly 1 parameter.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public abstract class FuzzyFunctionSingle implements FuzzyFunction {
    /* (non-Javadoc)
     * @see funzy.operators.FuzzyOperator#evaluate(N[])
     */
    public double evaluate(List<Double> values) {
        return evaluate(getOnlyElement(values));
    }

    protected abstract double evaluate(double value);
}
