package sg.atom.logic.fuzzy.rules.functions;

import java.util.List;


/**
 * Implementation of a fuzzy operator requiring exactly 1 parameter.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public abstract class FuzzyFunctionMultiple<N extends Number> implements
		FuzzyFunction {
	/* (non-Javadoc)
	 * @see funzy.operators.FuzzyOperator#evaluate(N[])
	 */
	public double evaluate(List<Double> values) {
		Double res = null;
		for (double value : values)
			if (res == null)
				res = value;
			else
				res = evaluate(res, value);
		return res;
	}

	protected abstract double evaluate(double value1, double value2);
}
