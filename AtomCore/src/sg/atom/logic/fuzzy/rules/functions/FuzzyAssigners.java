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
package sg.atom.logic.fuzzy.rules.functions;

/**
 * Implementation of a Fuzzy assigner functions factory.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public final class FuzzyAssigners {
	private static final Double CONST_LITTLE = 1.3;
	private static final Double CONST_SLIGHTLY = 1.7;
	private static final Double CONST_VERY = 2.0;
	private static final Double CONST_EXTREMELY = 3.0;
	private static final Double CONST_VERY_VERY = 4.0;
	private static final Double CONST_SOMEWHAT = 0.5;

	private FuzzyAssigners() {
	}

	public static final FuzzyFunction not() {
		return new FuzzyFunctionNot();
	}

	public static final FuzzyFunction nop() {
		return new FuzzyFunctionNop();
	}
	
	public static final FuzzyFunction root(double exponent) {
		return new FuzzyFunctionRoot(exponent);
	}

	public static final FuzzyFunction little() {
		return root(CONST_LITTLE);
	}

	public static final FuzzyFunction slightly() {
		return root(CONST_SLIGHTLY);
	}

	public static final FuzzyFunction very() {
		return root(CONST_VERY);
	}

	public static final FuzzyFunction extremely() {
		return root(CONST_EXTREMELY);
	}

	public static final FuzzyFunction veryvery() {
		return root(CONST_VERY_VERY);
	}

	public static final FuzzyFunction somewhat() {
		return root(CONST_SOMEWHAT);
	}
	
	public static FuzzyFunction NOT = not();
	public static FuzzyFunction LITTLE = little();
	public static FuzzyFunction SLIHTLY = slightly();
	public static FuzzyFunction VERY = very();
	public static FuzzyFunction EXTREMELY = extremely();
	public static FuzzyFunction VERYVERY = veryvery();
	public static FuzzyFunction SOMEWHAT = somewhat();
}
